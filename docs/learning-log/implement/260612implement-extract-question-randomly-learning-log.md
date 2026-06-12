# 1. 学習画面のページング実装とランダム出題における課題整理

## 現在の実装構成

### Controller

```java
@GetMapping("/study")
public String getStudy(Model model,
        @PageableDefault(page = 0, size = 1) Pageable pageable) {

    Page<Question> questionPage = studyService.getQuestion(pageable);

    model.addAttribute(
            "question",
            questionPage.getContent());
    model.addAttribute("page", questionPage);

    return "study";
}
```

### Service

```java
public Page<Question> getQuestion(Pageable pageable){
    return repository.findAll(pageable);
}
```

### View（study.html）

現在は Spring Data JPA の `Page<Question>` を利用して、1ページにつき1問表示する構成になっている。

```html
<a th:if="${page.hasPrevious()}"
   th:href="@{/study(page=${page.number - 1},size=1)}"
   class="btn btn-primary">
    前の問題へ
</a>

<a th:if="${page.hasNext()}"
   th:href="@{/study(page=${page.number + 1},size=1)}"
   class="btn btn-primary">
    次の問題へ
</a>
```

---

# 現在のページ遷移の流れ

## 初回アクセス

ホーム画面から

```text
/study
```

へアクセスする。

Controller の

```java
@GetMapping("/study")
```

が実行される。

`@PageableDefault`

```java
page = 0
size = 1
```

が設定された `Pageable` が生成される。

---

Service が呼び出される。

```java
studyService.getQuestion(pageable);
```

↓

```java
repository.findAll(pageable);
```

↓

SQL実行

```sql
SELECT *
FROM question
LIMIT 1 OFFSET 0;
```

---

## 2問目へ移動

HTML

```html
<a th:href="@{/study(page=${page.number + 1},size=1)}">
```

現在

```text
page.number = 0
```

なので

```html
<a href="/study?page=1&size=1">
```

が生成される。

---

クリックすると

```text
GET /study?page=1&size=1
```

が送信される。

---

再び

```java
@GetMapping("/study")
```

が最初から実行される。

今度は

```text
page = 1
size = 1
```

の Pageable が生成される。

---

再び

```java
repository.findAll(pageable);
```

が呼ばれる。

SQL

```sql
SELECT *
FROM question
LIMIT 1 OFFSET 1;
```

が発行される。

---

## 3問目へ移動

同様に

```text
GET /study?page=2&size=1
```

が送信される。

---

再度

```java
repository.findAll(pageable);
```

が呼ばれる。

SQL

```sql
SELECT *
FROM question
LIMIT 1 OFFSET 2;
```

が発行される。

---

# なぜ毎回SQLが実行されるのか

現在の実装では

- HttpSession を使用していない
- @SessionAttributes を使用していない
- List<Question> を保持していない

という構成になっている。

そのため毎回

```text
HTTPリクエスト
↓
Controller実行
↓
Service実行
↓
Repository実行
↓
SQL実行
↓
HTML生成
↓
レスポンス返却
```

を繰り返している。

---

取得した問題一覧はどこにも保存されていない。

つまり

```java
repository.findAll(pageable);
```

が

```java
@GetMapping("/study")
```

のたびに必ず実行される構造になっている。

---

# ランダム出題時に発生する問題

通常のページングでは問題ない。

しかしランダム出題を行いたい場合は問題が発生する。

例えば毎回

```sql
SELECT *
FROM question
ORDER BY RANDOM()
LIMIT 1;
```

のようなSQLを発行するとする。

---

1回目

```text
問題5
```

---

2回目

```text
問題2
```

---

3回目

```text
問題5
```

---

のように同じ問題が再び出題される可能性がある。

---

これはSQLの重複ではない。

毎回新しいランダム抽選を行っているためである。

つまり

```text
問題一覧の順番
```

を保持していないことが原因である。

---

# セッションを利用した場合

学習開始時のみ問題一覧を取得する。

```java
List<Question> questions =
        repository.findAll();
```

---

取得後にシャッフルする。

```java
Collections.shuffle(questions);
```

---

例

```text
[問題3, 問題1, 問題5, 問題2, 問題4]
```

---

これを Session に保存する。

```java
session.setAttribute(
        "questions",
        questions);
```

---

以降は DB を参照しない。

```java
questions.get(0);
questions.get(1);
questions.get(2);
```

のように取り出すだけになる。

---

結果

```text
/study?page=0
↓
SQL実行

/study?page=1
↓
SQLなし

/study?page=2
↓
SQLなし
```

となる。

---

# セッション方式で必要となる変更点

## Controller側

### ① セッション管理

初回アクセス時のみDBから取得する仕組みが必要

```java
if (session.getAttribute("questions") == null)
```

などの判定が必要になる。

---

### ② Sessionへの保存

```java
session.setAttribute(
        "questions",
        questions);
```

によって問題一覧を保持する。

---

### ③ Sessionから取得

```java
List<Question> questions =
    (List<Question>)
    session.getAttribute("questions");
```

を利用する。

---

### ④ Page<Question> を使わない構成への変更

現在は

```java
model.addAttribute("page", page);
```

によって Thymeleaf 側へページ情報を渡している。

しかし Session方式では Page オブジェクトが存在しない。

そのため必要な情報を自前で作成して渡す必要がある。

---

例

```java
model.addAttribute(
        "currentPage",
        page + 1);

model.addAttribute(
        "totalPages",
        questions.size());

model.addAttribute(
        "hasPrevious",
        page > 0);

model.addAttribute(
        "hasNext",
        page < questions.size() - 1);
```

---

## HTML側

現在

```html
${page.number}
${page.totalPages}
${page.hasNext()}
${page.hasPrevious()}
```

を使用している。

---

Session方式では利用できなくなるため

例えば

```html
${currentPage}
${totalPages}
${hasNext}
${hasPrevious}
```

へ置き換える必要がある。

---

# セッション方式導入後のメリット

- ランダム出題でも重複が発生しない
- 学習開始時のみSQL実行
- ページ移動時にDBアクセス不要
- 出題順を固定できる
- 同一セッション中は必ず同じ問題順になる

---

# 課題まとめ

現在の実装は `Page<Question>` を利用した一般的なページングであり、ページ移動のたびに

```java
repository.findAll(pageable);
```

が実行される。

そのためランダム出題を導入すると、ページ遷移のたびに再抽選が行われ、同じ問題が再び出題される可能性がある。

これを防ぐためには、学習開始時に問題一覧を取得・シャッフルし、Session に保存して管理する構成へ変更する必要がある。

---

# 2. StudyServiceImpl 改修内容

## 改修前

以前は Spring Data JPA のページング機能を利用していた。

```java
public Page<Question> getQuestion(Pageable pageable){
    return repository.findAll(pageable);
}
```

Controller から渡された `Pageable` をそのまま Repository に渡し、1ページ分のデータのみ取得していた。

---

### 実行イメージ

1問目

```sql
SELECT *
FROM question
LIMIT 1 OFFSET 0;
```

---

2問目

```sql
SELECT *
FROM question
LIMIT 1 OFFSET 1;
```

---

3問目

```sql
SELECT *
FROM question
LIMIT 1 OFFSET 2;
```

---

この方式ではページ遷移のたびに SQL が実行される。

また、ランダム出題を実装しようとしても毎回再抽選になるため、同じ問題が複数回出題される可能性がある。

---

## 改修後

### 問題一覧取得メソッド

```java
public List<Question> getQuestion(){
    return repository.findAll();
}
```

ページングを廃止し、問題一覧をすべて取得するように変更した。

戻り値も

```java
Page<Question>
```

から

```java
List<Question>
```

へ変更している。

---

### 処理内容

```java
repository.findAll();
```

によって question テーブルの全件を取得する。

概念的には次のような SQL が発行される。

```sql
SELECT *
FROM question;
```

---

取得結果例

```text
問題1
問題2
問題3
問題4
問題5
```

---

## ランダム出題メソッド

今回新たに追加したメソッド。

```java
public List<Question> getRandomQuestion(){

    List<Question> extractedQuestions =
            repository.findAll();

    Collections.shuffle(extractedQuestions);

    return extractedQuestions;
}
```

---

### ① 問題一覧取得

まず全件取得する。

```java
List<Question> extractedQuestions =
        repository.findAll();
```

例

```text
[問題1, 問題2, 問題3, 問題4, 問題5]
```

---

### ② シャッフル

```java
Collections.shuffle(extractedQuestions);
```

を実行する。

---

例えば

```text
[問題1, 問題2, 問題3, 問題4, 問題5]
```

が

```text
[問題3, 問題1, 問題5, 問題2, 問題4]
```

のようにランダムな順番へ並び替えられる。

---

### ③ シャッフル済みリスト返却

```java
return extractedQuestions;
```

によってランダム化された問題一覧を返す。

---

## Collections.shuffle() の特徴

### 重複は発生しない

シャッフルは「順番を入れ替える処理」である。

例えば

```text
[1,2,3,4,5]
```

が

```text
[3,1,5,2,4]
```

になるだけであり、

```text
[3,1,5,2,3]
```

のように要素が重複することはない。

---

### 件数も変わらない

元が

```text
100件
```

なら

```text
100件
```

のままである。

---

### 全要素が必ず1回ずつ登場する

例えば

```text
[問題3, 問題1, 問題5, 問題2, 問題4]
```

となった場合、

セッション内で

```java
questions.get(0)
questions.get(1)
questions.get(2)
questions.get(3)
questions.get(4)
```

と順番に取り出せば、

全問題を1回ずつ出題できる。

---

## 今回の設計での役割

Service の責務は

- 問題一覧取得
- ランダム化

までである。

---

Sessionへの保存は行わない。

例えば

```java
session.setAttribute(...)
```

のような処理は Controller の責務となる。

---

そのため Service は

```text
DB
↓
問題一覧取得
↓
シャッフル
↓
Controllerへ返却
```

だけを担当している。

---

## この改修によって得られる効果

### 改修前

```text
ページ移動
↓
毎回SQL実行
↓
毎回問題取得
```

---

### 改修後

```text
学習開始
↓
全問題取得
↓
シャッフル
↓
Session保存
↓
以降はListから取得
```

---

結果として、

- ランダム出題が可能
- 同じ問題の重複出題を防げる
- 学習中の不要なDBアクセスを削減できる
- 出題順を固定できる

というメリットが得られる。

Service の改修は、そのための土台となる処理である。

---

# 3. StudyController 改修内容

## この改修で何が変わったのか

以前は Spring Data JPA の `Page<Question>` を利用していた。

```java
Page<Question> questionPage =
        studyService.getQuestion(pageable);
```

ページ番号が変わるたびに SQL を実行し、

```java
repository.findAll(pageable);
```

で必要な1件だけ取得していた。

---

今回は方針を変更し、

```java
List<Question>
```

を Session に保存し、

```java
questions.get(page)
```

で問題を取り出す構成になった。

---

## クラス全体

```java
@Controller
@RequiredArgsConstructor
@SessionAttributes("questions")
public class StudyController
```

---

### @Controller

```java
@Controller
```

Spring MVC の Controller であることを示す。

```text
GET /study
↓
StudyController
↓
study.html
```

を繋ぐ役割。

---

### @RequiredArgsConstructor

```java
@RequiredArgsConstructor
```

Lombok によりコンストラクタを自動生成する。

---

例えば

```java
private final StudyServiceImpl studyService;
```

があるので、

実際には内部的に

```java
public StudyController(
        StudyServiceImpl studyService){
    this.studyService = studyService;
}
```

が生成される。

---

### @SessionAttributes("questions")

```java
@SessionAttributes("questions")
```

Controller内で

```java
"questions"
```

という名前のデータを Session と連携するための設定。

---

今回実際には

```java
session.setAttribute(
        "questions",
        questions);
```

を使用しているため必須ではない。

将来的に

```java
@ModelAttribute
```

方式へ移行するなら活用できる。

現状では実質的な効果はほぼない。

---

# フィールド

```java
private final StudyServiceImpl studyService;
```

Service を呼び出すためのオブジェクト。

---

利用箇所

```java
studyService.getRandomQuestion();
```

---

# getStudy()

```java
@GetMapping("/study")
public String getStudy(
        Model model,
        HttpSession session,
        @RequestParam(defaultValue = "0") int page)
```

---

## 引数① Model

```java
Model model
```

Controller から HTML へ値を渡す箱。

---

例えば

```java
model.addAttribute(
        "question",
        question);
```

と書くと

HTML側で

```html
<h2 th:text="${question.japaneseText}">
```

が利用できる。

---

## 引数② HttpSession

```java
HttpSession session
```

ユーザーごとの一時保存領域。

---

例

```java
session.setAttribute(
        "questions",
        questions);
```

で保存すると、

同じブラウザである限り

```java
session.getAttribute(
        "questions");
```

で再取得できる。

---

## 引数③ page

```java
@RequestParam(defaultValue = "0")
int page
```

URLパラメータを取得する。

---

例えば

```text
/study?page=0
```

なら

```java
page = 0
```

---

```text
/study?page=5
```

なら

```java
page = 5
```

---

指定が無い場合

```text
/study
```

なら

```java
page = 0
```

になる。

---

# ① Sessionになければ取得して保存

```java
if (session.getAttribute("questions") == null)
```

---

まず Session の中を確認する。

```java
session.getAttribute("questions")
```

とは

```text
Sessionの中に保存されている
"questions"
という名前のデータ
```

を意味する。

---

例えば

```java
session.setAttribute(
        "questions",
        questions);
```

で保存していた場合

取得できる。

---

初回アクセス時はまだ存在しない。

```text
null
```

になる。

---

そこで

```java
List<Question> questions =
        studyService.getRandomQuestion();
```

を実行する。

---

Service

```java
repository.findAll();
Collections.shuffle(...);
```

が呼ばれる。

---

結果

```text
[問題3, 問題1, 問題5, 問題2, 問題4]
```

のようなリストが生成される。

---

Sessionへ保存

```java
session.setAttribute(
        "questions",
        questions);
```

---

ここで重要なのは

```java
"questions"
```

という文字列。

---

これは

```text
Session内の保存名
```

である。

---

イメージ

```text
Session

questions
↓
[問題3, 問題1, 問題5, 問題2, 問題4]
```

---

# ② Sessionからquestions取得

```java
List<Question> questions =
    (List<Question>)
    session.getAttribute("questions");
```

---

先ほど保存した

```text
questions
↓
[問題3, 問題1, 問題5, 問題2, 問題4]
```

を取り出している。

---

ここでの

```java
questions
```

は

```java
List<Question> questions
```

というローカル変数。

---

つまり

```java
"questions"
```

とは別物。

---

整理すると

```java
session.getAttribute("questions");
```

の

```java
"questions"
```

は Session の保存名。

---

一方

```java
List<Question> questions
```

の

```java
questions
```

は Java の変数名。

---

偶然同じ名前なだけで役割は違う。

---

# ③ page番目の問題を取得

```java
Question question =
        questions.get(page);
```

---

例えば

```text
questions

0 → 問題3
1 → 問題1
2 → 問題5
3 → 問題2
4 → 問題4
```

だったとする。

---

URL

```text
/study?page=2
```

なら

```java
page = 2
```

なので

```java
questions.get(2);
```

になる。

---

結果

```text
問題5
```

が取得される。

---

取得結果を

```java
Question question
```

へ格納する。

---

# ④ HTMLへ渡す

## question

```java
model.addAttribute(
        "question",
        question);
```

---

左側

```java
"question"
```

は

HTMLで使う名前。

---

右側

```java
question
```

は

Java変数。

---

つまり

```java
model.addAttribute(
        "question",
        question);
```

は

```text
HTML側では
questionという名前で使えるようにする
```

という意味。

---

イメージ

```text
Java

Question question
↓
問題5
```

↓

```java
model.addAttribute(
        "question",
        question);
```

↓

```html
${question}
```

として利用可能になる。

---

HTML

```html
<h2 th:text="${question.japaneseText}">
```

は

実質

```java
question.getJapaneseText()
```

を表示している。

---

## currentPage

```java
model.addAttribute(
        "currentPage",
        page + 1);
```

---

現在何問目か。

---

例えば

```text
page = 0
```

なら

```text
1問目
```

なので

```java
page + 1
```

している。

---

## totalPages

```java
model.addAttribute(
        "totalPages",
        questions.size());
```

---

総問題数。

---

例

```text
questions.size()
↓
100
```

---

HTML

```html
1 / 100
```

表示用。

---

## hasPrevious

```java
model.addAttribute(
        "hasPrevious",
        page > 0);
```

---

1問目以外なら

```text
true
```

になる。

---

HTML

```html
th:if="${hasPrevious}"
```

で利用する。

---

## hasNext

```java
model.addAttribute(
        "hasNext",
        page < questions.size() - 1);
```

---

最後の問題でなければ

```text
true
```

になる。

---

HTML

```html
th:if="${hasNext}"
```

で利用する。

---

# ⑤ study.htmlを返却

```java
return "study";
```

---

Spring は

```text
templates/study.html
```

を探す。

---

最終的な流れ

```text
GET /study
↓
Session確認
↓
なければDB取得
↓
シャッフル
↓
Session保存
↓
page番目の問題取得
↓
Modelへ格納
↓
study.html表示
```

---

# この改修の最大のポイント

以前

```text
ページ移動
↓
毎回SQL実行
```

だった。

---

現在

```text
初回アクセス
↓
SQL実行
↓
Session保存

2問目
↓
SQLなし

3問目
↓
SQLなし

4問目
↓
SQLなし
```

になった。

---

その結果、

- ランダム出題が可能
- 同じ問題が再抽選されない
- 学習中のDBアクセス削減
- 出題順固定

を実現できるようになった。