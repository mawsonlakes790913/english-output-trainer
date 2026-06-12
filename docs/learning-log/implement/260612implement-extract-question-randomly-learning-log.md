# 学習画面のページング実装とランダム出題における課題整理

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

# まとめ

現在の実装は `Page<Question>` を利用した一般的なページングであり、ページ移動のたびに

```java
repository.findAll(pageable);
```

が実行される。

そのためランダム出題を導入すると、ページ遷移のたびに再抽選が行われ、同じ問題が再び出題される可能性がある。

これを防ぐためには、学習開始時に問題一覧を取得・シャッフルし、Session に保存して管理する構成へ変更する必要がある。