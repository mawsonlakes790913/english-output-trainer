# リファクタリング・改善メモ

## 1. 初回表示時のURLを統一

### 問題点

`study/question`および`review/question`の初回表示だけ

```
/study/question
/review/question
```

となり、2問目以降は

```
/study/question?page=1
/review/question?page=1
```

となっていた。

### 修正

開始画面からのリダイレクト先を`page=0`付きに変更した。

#### StudyController

```java
// return "redirect:/study/question";
return "redirect:/study/question?page=0";
```

#### ReviewController

```java
// return "redirect:/review/question";
return "redirect:/review/question?page=0";
```

### 修正後

初回からURLが

```
/study/question?page=0
/review/question?page=0
```

となり、ページ番号の表現を統一できた。

---

## 2. ログイン時にもログインボタンが表示される

### 問題点

ログイン後も「ログイン」ボタンが表示されたままとなっており、現在の認証状態が分かりにくいUIになっていた。

![](../../images/refactoring-note1-1.png)

### 修正（git commit: `fix: show login/logout buttons based on authentication status`）

Spring SecurityのThymeleaf拡張を利用し、認証状態に応じて表示するボタンを切り替えるように修正した。

#### `layout/header.html`

##### Spring Securityの名前空間を追加

```html
<html xmlns:th="http://www.thymeleaf.org"
      xmlns:layout="http://www.ultraq.net.nz/thymeleaf/layout"
      xmlns:sec="http://www.thymeleaf.org/extras/spring-security">
```

##### ログイン・ログアウトボタンの表示を認証状態で切り替え

```html
<!-- ログインしていないときだけ表示 -->
<a sec:authorize="isAnonymous()"
   class="btn btn-primary"
   href="/login">
    ログイン
</a>

<!-- ログインしているときだけ表示 -->
<form sec:authorize="isAuthenticated()"
      method="post"
      th:action="@{/logout}"
      class="m-0">

    <button type="submit"
            class="btn btn-outline-light btn-sm">
        ログアウト
    </button>

</form>
```

### 修正後

認証状態に応じて表示されるボタンが切り替わるようになった。

- **未ログイン時**：ログインボタンのみ表示
- **ログイン中**：ログアウトボタンのみ表示

これにより、現在のログイン状態が分かりやすいUIになった。

![](../../images/refactoring-note1-2.png)
![](../../images/refactoring-note1-3.png)

---

## 3. Home画面に一時的なメニューを作る

### 問題点

もともとのHome画面には、テスト用に作成した`/study/start`へ遷移するボタンしか配置されていなかった。

しかし現在はこのURLを直接利用することはなくなっており、

- ログイン
- 新規登録
- 通常学習
- 復習
- ユーザーメニュー

などへ遷移する導線も存在しなかった。

そのため、Home画面をトップページとして利用できるよう再構成することにした。

---

### 修正
（git commit: `"feat: redesign home page navigation"`）

`home.html`のコンテンツ部分を全面的に作り直した。

#### 変更前

Home画面には

- 「新規開始」
- 「途中から始める」

のみが表示され、学習開始方法を選択するための画面となっていた。

またJavaScriptによって

- 順番に出題
- ランダムに出題

を表示するだけの構成になっていた。

#### 変更後

Bootstrapを利用し、トップページとして利用しやすいレイアウトへ変更した。

表示するメニューは以下のとおり。

| ボタン | 表示条件 |
|--------|----------|
| 通常学習 | 常に表示 |
| 復習 | ログイン時のみ |
| メニュー | ログイン時のみ |
| ログイン | 未ログイン時のみ |
| 新規登録 | 未ログイン時のみ |

Spring Securityの

```html
sec:authorize="isAuthenticated()"
```

および

```html
sec:authorize="isAnonymous()"
```

を利用することで、ログイン状態に応じて表示するボタンを切り替えた。

---

### 修正後

Home画面から主要な機能へ直接アクセスできるようになった。

また、開発中も各画面へ素早く遷移できるようになり、ページ遷移の確認が容易になった。

![](../../images/refactoring-note3-1.png)
![](../../images/refactoring-note3-2.png)

---

## 4. ログイン成功後の遷移先をHome画面へ変更

### 問題点

ログイン成功後の遷移先が

```
/menu
```

（ユーザーメニュー）

になっていた。

しかし、Home画面をトップページとして再構成したため、Home画面を起点としたほうが各機能へアクセスしやすくなる。

---

### 修正
（git commit: `"fix: redirect users to saved request after login"`）

`SecurityConfig`の設定を以下のように変更した。

#### 変更前

```java
.defaultSuccessUrl("/menu", true)
```

#### 変更後

```java
.defaultSuccessUrl("/", false)
```

---

### `false`を採用した理由

当初は

```java
.defaultSuccessUrl("/", true)
```

として、ログイン成功後は必ずHome画面へ戻すことを考えていた。

しかし、今後の機能追加を考えると、**ログイン前にアクセスしようとしていたページへ戻るほうが自然**であると判断した。

例えば、

- 復習画面(`/review/menu`)へアクセスしようとしてログインを求められた場合
- 問題画面で「ログインするとお気に入り機能が利用できます」という案内からログインした場合

などでは、ログイン後にHome画面へ戻されるよりも、元の画面へ戻ったほうが操作を継続しやすい。

そのため、

```java
.defaultSuccessUrl("/", false)
```

を採用し、**Saved Request（保存されたリクエスト）が存在する場合はそのページへ戻り、存在しない場合のみHome画面へ遷移する**設定へ変更した。

この設定により、将来的にログイン導線をさまざまな画面へ追加しても、ユーザーは本来行おうとしていた操作をそのまま続行できるようになった。

---

### 修正後

- Home画面からログインした場合はHome画面へ戻る
- ログインが必要な画面から遷移した場合は、その画面へ戻る

という、より自然な画面遷移となった。

---

## 5. お気に入り登録ボタン（ハートマーク）の表示を制御する

### 問題点

非ログイン状態でも、`study/question.html`にお気に入り登録ボタン（ハートマーク）が表示されてしまっていた。

お気に入り機能自体はログインユーザーしか利用できないため、ボタンを表示していてもクリックして利用することはできない。しかし、ユーザーから見ると「何のためのボタンなのか」が分かりづらく、UIとしても不自然である。

なお、`review/question.html`はログインユーザーしかアクセスできない画面であるため、こちらは修正する必要がない。

---

### 修正
（git commit: `"fix: hide favorite button for anonymous users"`）

#### `study/question.html`

##### htmlタグにSpring Securityの名前空間を追加

`sec:authorize`を利用できるように、`html`タグへSpring Securityの名前空間を追加した。

```html
<html xmlns:th="http://www.thymeleaf.org"
      xmlns:layout="http://www.ultraq.net.nz/thymeleaf/layout"
      xmlns:sec="http://www.thymeleaf.org/extras/spring-security"
      layout:decorate="~{layout/layout}">
```

---

##### お気に入り登録ボタンをログインユーザーのみに表示

お気に入り登録ボタン全体を

```html
sec:authorize="isAuthenticated()"
```

で囲むことで、ログインしているユーザーのみ表示されるようにした。

```html
<div sec:authorize="isAuthenticated()">

    <button id="favoriteButton"
            type="button"
            class="btn p-0 border-0 bg-transparent"
            th:data-question-id="${question.questionId}">

        <i id="favoriteIcon"
           th:class="${isFavorite}
                ? 'bi bi-heart-fill fs-2 text-danger'
                : 'bi bi-heart fs-2 text-secondary'">
        </i>

    </button>

</div>
```

---

### 修正後

非ログイン状態で通常学習画面へアクセスすると、お気に入り登録ボタン（ハートマーク）が表示されないことを確認した。

これにより、ログインユーザーだけが利用できる機能を適切に隠すことができ、画面が分かりやすくなった。

![](../../images/refactoring-note5.png)

---

## 6. Headerのサービス名をクリックするとホーム画面へ戻れるようにする

### 問題点

大きな問題ではないが、多くのWebサイトではヘッダーに表示されているサービス名やロゴをクリックするとホーム画面へ戻れるようになっている。

本アプリではその機能が実装されていなかったため、利便性向上のため追加することにした。

---

### 修正
（git commit: `feat: add home link to header title`）

#### 変更前

```html
<h1 class="h4 m-0">
    English Output Trainer
</h1>
```

#### 変更後

```html
<h1 class="h4 m-0">
    <a th:href="@{/}"
       class="text-white text-decoration-none">
        English Output Trainer
    </a>
</h1>
```

サービス名全体をリンク化し、クリックするとホーム画面（`/`）へ遷移するようにした。

また、

- `text-white`
- `text-decoration-none`

を指定することで、従来と同じ見た目を維持している。

---

### 修正後

サービス名をクリックするとホーム画面へ遷移することを確認した。

これにより、どの画面からでもヘッダーのサービス名をクリックするだけでホーム画面へ戻れるようになり、操作性が向上した。

---

## 7. ログアウト後、ログイン画面ではなくHome画面へ戻す

### 問題点

ログアウトボタンを押すと、ユーザーIDとパスワードを入力するログイン画面へ遷移していた。

しかし、ログアウト直後に再びログインを試みるケースは少なく、多くのWebサイトではトップページやHome画面へ戻るようになっている。

そのため、本アプリでもログアウト後はHome画面へ戻す仕様に変更することにした。

![](../../images/refactoring-note7-1.png)

---

## 修正
（git commit: `fix: redirect users to home after logout`）

### `SecurityConfig.java`

ログアウト成功後のリダイレクト先を変更した。

#### 変更前

```java
.logoutSuccessUrl("/login?logout")
```

#### 変更後

```java
.logoutSuccessUrl("/?logout")
```

これにより、ログアウト後はログイン画面ではなく、Home画面へリダイレクトされるようになった。

---

### `home.html`

ログアウト後のみメッセージを表示するようにした。

```html
<div th:if="${param.logout}"
     class="text-danger">
    ログアウトしました
</div>
```

---

## 修正後

ログアウトボタンを押すとHome画面へ戻り、「ログアウトしました」というメッセージが表示されるようになった。

これにより、ログアウト後もそのまま

- 通常学習
- ログイン
- 新規登録

などの操作へスムーズに移れるようになった。

![](../../images/refactoring-note7-2.png)

---

## HomeControllerを修正しない理由

`SecurityConfig`で

```java
.logout(logout -> logout
    .logoutSuccessUrl("/?logout")
)
```

と設定すると、ログアウト後は

```text
/
```

ではなく

```text
/?logout
```

へリダイレクトされる。

そのため、

```java
@GetMapping("/")
public String home(
        @RequestParam(required = false)
        String logout) {
    return "home";
}
```

のように、`logout`パラメータをControllerで受け取る必要があるようにも思える。

しかし、実際には

```java
@GetMapping("/")
public String home() {
    return "home";
}
```

のままでも問題なく動作する。

その理由は、**`?logout`はURLのクエリパラメータであり、Thymeleafから直接参照できるため**である。

Thymeleafにはリクエストパラメータを取得するための`param`オブジェクトが用意されており、

```html
<div th:if="${param.logout}"
     class="text-danger">
    ログアウトしました
</div>
```

と記述するだけで、

- `/?logout` の場合はメッセージを表示
- `/` の場合は表示しない

という制御を行うことができる。

そのため、`logout`パラメータをControllerで受け取って`Model`へ渡す必要はない。

このように、**画面表示だけに利用するリクエストパラメータは、Thymeleafから直接参照した方がシンプルな実装となる。**

---

## 8. `/user/profile`アクセス時のエラーを解消する

### 問題点

ログインメニューから「会員情報確認・編集」をクリックして
`/user/profile`へ遷移しようとすると、**500 Internal Server Error** が発生した。

---

### 原因

スタックトレースを見ると、`Users.toString()` と `Favorites.toString()` が互いに呼び出され続け、最終的に `StackOverflowError` が発生していた。

```
Users.toString()
    ↓
favorites(List<Favorites>)
    ↓
Favorites.toString()
    ↓
user
    ↓
Users.toString()
    ↓
・・・
```

原因を調査するため、`UserMenuController` の `getUserProfile()` メソッドを確認すると、デバッグ用のコードが残ったままになっていた。

```java
System.out.println(loginUser.getUsername());
System.out.println(user);
```

問題は後者の

```java
System.out.println(user);
```

である。

`user` は `Users` エンティティであり、`@Data` が付与されている。

```java
@Data
@Entity
public class Users {

    @OneToMany(mappedBy = "user")
    private List<Favorites> favorites;
}
```

一方、`Favorites` エンティティも `Users` を保持している。

```java
@Data
@Entity
public class Favorites {

    @ManyToOne
    private Users user;
}
```

つまり、

- `Users` は `Favorites` を持つ
- `Favorites` は `Users` を持つ

という**双方向関連**になっている。

さらに、Lombok の `@Data` は `toString()` を自動生成するため、

```java
System.out.println(user);
```

を実行すると、

```java
user.toString();
```

が呼び出される。

---

#### ① `Users.toString()`

自動生成される `toString()` は概ね次のような内容になる。

```java
public String toString() {
    return "Users("
        + id
        + ", "
        + userId
        + ", "
        + favorites
        + ")";
}
```

つまり、

```
Users
├ id = 1
├ userId = "abc"
└ favorites ← これも表示しよう
```

となる。

---

#### ② `favorites` を表示する

`favorites` は

```java
List<Favorites>
```

である。

Java は List の中身も文字列化しようとするため、

```java
Favorites.toString();
```

を呼び出す。

---

#### ③ `Favorites.toString()`

こちらも `@Data` により自動生成されるため、

```java
public String toString() {
    return "Favorites("
        + question
        + ", "
        + user
        + ")";
}
```

のようになる。

つまり、

```
Favorites
├ question = ...
└ user ← これも表示しよう
```

となる。

---

#### ④ `user` を表示する

しかし `user` は再び `Users` オブジェクトである。

そのため、

```java
Users.toString();
```

が再び呼び出され、

```
Users.toString()
    ↓
Favorites.toString()
    ↓
Users.toString()
    ↓
Favorites.toString()
    ↓
・・・
```

と永遠に繰り返され、最終的に `StackOverflowError` が発生していた。

---

### 修正（`refactor: remove temporary debug output`）

デバッグ用の出力は不要だったため削除した。

```java
System.out.println(loginUser.getUsername());
System.out.println(user);
```

#### 削除した理由

- `System.out.println()` は開発中の一時的なデバッグ用途である。
- 本番環境では標準出力ではなく、必要に応じて `log.info()` や `log.debug()` を使用する。
- 今回は `Users` オブジェクト全体を出力する必要がないため、ログ自体が不要である。

---

### デバッグする場合

エンティティ全体ではなく、必要な項目だけを出力する。

```java
log.debug("userId={}", user.getUserId());
```

または

```java
log.debug(
    "id={}, role={}",
    user.getId(),
    user.getRole()
);
```

このように個別のプロパティだけを出力すれば、`toString()` の再帰呼び出しを防ぐことができる。

---

### 修正後

不要なデバッグコードを削除したことで、`/user/profile` に正常にアクセスできるようになり、会員情報確認画面が正しく表示されるようになった。


---

# 文法条件検索の見直し(git commit -m "fix: correct condition filtering in question search")

## 背景

問題一覧画面では、文法条件（condition）による絞り込み検索を実装している。

しかし、特定の文法条件を選択して検索すると、本来その条件に該当する問題だけが表示されるべきところ、条件未設定（NULL）の問題まで検索結果に含まれてしまっていた。

例えば、

- 全件：751件
- as...as：424件
- enough to：420件

となり、文法条件ごとの件数としては明らかに不自然な結果となっていた。

---

## 原因

Repositoryの検索条件を確認したところ、以下のSQLとなっていた。

```sql
AND (
    q.condition IS NULL
    OR q.condition IN (:conditions)
)
```

この条件では、

- 選択した文法条件
- condition が NULL の問題

の両方が検索結果へ含まれてしまう。

そのため、

```
NULL の問題 + 選択した文法条件
```

という件数になっていた。

---

## 改善方針

管理者側の問題検索と同じ考え方を採用し、

- 「すべて」の場合は条件検索を行わない
- 文法条件を選択した場合のみ条件検索を行う

という仕様へ変更した。

---

## Serviceの修正

従来は、条件未指定の場合に全条件リストへ置き換えていた。

```java
if (conditions == null || conditions.isEmpty()) {
    conditions = questionService.getAllConditions();
}
```

これを以下のように変更した。

```java
boolean includeAllConditions =
        (conditions == null || conditions.isEmpty());

if (includeAllConditions) {
    conditions = List.of("");
}
```

これにより、

- 条件未指定かどうか
- 実際の検索条件

を別々に管理できるようになった。

---

## Repositoryの修正

Repositoryへ

```java
boolean includeAllConditions
```

を追加した。

SQLも以下のように修正した。

修正前

```sql
AND (
    q.condition IS NULL
    OR q.condition IN (:conditions)
)
```

修正後

```sql
AND (
    :includeAllConditions = true
    OR q.condition IN (:conditions)
)
```

これにより、

- 「すべて」の場合は文法条件を無視
- 条件選択時は選択した文法のみ検索

という挙動になった。

---

## 動作確認

以下のケースで動作を確認した。

- 「すべて」で全751件表示されること
- 各文法条件で対象の問題のみ表示されること
- ページネーション後も検索条件が維持されること

期待どおり、文法条件検索が正しく機能することを確認した。

---

## 学んだこと

検索条件に「すべて」を持つ場合は、

SQL側で

- 検索を行う場合
- 検索を行わない場合

を明確に分岐させる設計が重要である。

検索条件そのものを書き換えるのではなく、

検索を有効にするかどうかを表すフラグ（`includeAllConditions`）を別途持たせることで、SQL・Serviceともに責務が明確になり、保守性も向上した。

---

# 管理者画面の文法条件検索の修正(git commit -m "fix: correct condition parameter in admin question search")

## 背景

管理者画面の問題検索では、文法条件を選択しても検索結果が常に全751件となり、条件による絞り込みが機能していなかった。

「すべて」を選択した場合だけでなく、任意の文法条件を選択した場合も同じ結果となっていたため、検索条件がRepositoryまで正しく渡されていないことが考えられた。

---

## 原因

Controllerでは文法条件を

```java
@RequestParam(required = false)
String condition
```

として受け取っていた。

一方、検索フォームでは

```html
<select name="conditions">
```

となっていた。

パラメータ名が一致していなかったため、

```
conditions
```

という名前で送信された値をControllerが受け取ることができず、

```java
condition == null
```

となっていた。

その結果、

```java
boolean includeAllConditions =
        condition == null || condition.isBlank();
```

が常に `true` となり、Repositoryでは常に「すべて検索」として処理されていた。

---

## 修正内容

### 検索フォーム

以下のようにパラメータ名を修正した。

修正前

```html
<select name="conditions">
```

修正後

```html
<select name="condition">
```

---

### ページネーション

ページ送り時も同じ誤りがあったため、検索条件の引き継ぎも修正した。

修正前

```html
conditions=${selectedConditions}
```

修正後

```html
condition=${selectedConditions}
```

対象は以下のページリンクである。

- 前へ
- 1ページ目
- 中央ページ
- 最終ページ
- 次へ

---

## 動作確認

以下の内容を確認した。

- 「すべて」で全751件表示されること
- 任意の文法条件を選択すると、その条件に一致する問題のみ表示されること
- ページ送り後も検索条件が保持されること

期待どおり、管理者画面でも文法条件検索が正しく機能することを確認した。

---

## 学んだこと

Controllerの `@RequestParam` とHTMLの `name` 属性は完全に一致している必要がある。

検索ロジックやSQLに問題がなくても、リクエストパラメータ名が一致していなければ値はControllerへ渡らず、検索条件が常に未指定として扱われる。

検索機能を実装・リファクタリングする際は、

- HTML
- Controller
- Service
- Repository

のパラメータ名がすべて一致していることを確認することが重要である。