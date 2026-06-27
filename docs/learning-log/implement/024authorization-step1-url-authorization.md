# 認可の実装 Step1 - URL認可

## 認可とは

Spring Securityには、大きく分けて**認証（Authentication）**と**認可（Authorization）**という2つの仕組みがある。

### 認証とは

認証とは、「そのユーザーが誰であるか」を確認する仕組みである。

具体的には、ユーザーIDとパスワードを用いてログインを行い、ログインに成功したユーザー情報をSpring Securityが管理する。

### 認可とは

ログインできたからといって、すべての機能を自由に利用できるわけではない。

例えば、

- 管理者だけが利用できる画面
- 一般ユーザーには実行させたくない処理

など、ユーザーの権限に応じて利用できる機能を制御したい場面がある。

このように、

> **「誰が、どの機能を利用できるか」を制御する仕組み**

を**認可**という。

認証が**本人確認**であるのに対し、認可は**利用許可の判定**と考えると分かりやすい。

---

## Spring Securityにおける認可

Spring Securityでは、認可を主に次の3段階で実装する。

1. **Step1 URL認可**
   - 特定のURLへアクセスできるユーザーを制御する。

2. **Step2 画面表示の認可**
   - ログインユーザーの権限によって画面の表示・非表示を切り替える。

3. **Step3 メソッド認可**
   - メソッドの実行可否を権限によって制御する。

今回は **Step1「URL認可」** を実装する。

---

# Step1 URL認可

今回は、管理者権限（`ROLE_ADMIN`）を持つユーザーだけが利用できる**アドミン専用画面**を作成する。

まずはログイン後に表示するユーザーメニュー画面を用意する。

---

## 準備① ログイン成功後の画面を作成

ログイン後に表示する画面として **userMenu.html** を作成する。

```html
<div layout:fragment="content" class="container pt-3">
    <h2>ユーザーメニュー</h2>

    <div class="list-group mt-3">
        <a th:href="@{/favorite}"
           class="list-group-item list-group-item-action">
            お気に入り登録一覧
        </a>

        <a th:href="@{/admin}"
           class="list-group-item list-group-item-action">
            アドミン権限専用画面
        </a>
    </div>
</div>
```

現時点では最低限の画面でよく、デザインや遷移先の詳細は今後実装していく。

---

## 準備② Controllerを追加

**UserMenuController.java**

```java
@Controller
public class UserMenuController {

    @GetMapping("/menu")
    public String getUserMenu() {
        return "userMenu";
    }

}
```

---

## 準備③ SecurityConfigを修正

修正箇所は2つある。

1. ログイン成功後の遷移先を変更する。
2. ログイン必須ページを設定する。

---

### ① ログイン成功後の遷移先を変更

変更前

```java
.formLogin(login -> login
    .loginPage("/login")
    .usernameParameter("userId")
    .passwordParameter("password")
    .defaultSuccessUrl("/")
    .failureUrl("/login?error")
    .permitAll()
)
```

変更後

```java
.defaultSuccessUrl("/menu")
```

#### 第2引数にtrueを指定しない理由

`defaultSuccessUrl("/menu", true)` とすると、ログイン成功後は必ず `/menu` に遷移してしまう。

一方、第2引数を省略すると、未認証状態で認証が必要なURLへアクセスした場合、ログイン成功後に**元のURLへ戻る**ことができる。

例えば、

```text
study.html
      ↓
お気に入り登録ボタンを押す
      ↓
ログイン画面へリダイレクト
      ↓
ログイン成功
      ↓
study.htmlへ戻る
      ↓
お気に入り登録を続行
```

このような動作が可能になるため、将来的な機能拡張にも対応しやすい。

---

### ② ログイン必須ページを設定

変更前

```java
http.authorizeHttpRequests(authorize -> authorize
    .anyRequest().permitAll()
)
```

変更後

```java
http.authorizeHttpRequests(authorize -> authorize
        .requestMatchers(
                PathRequest.toStaticResources().atCommonLocations()
        ).permitAll()
        .requestMatchers("/").permitAll()
        .requestMatchers("/login").permitAll()
        .requestMatchers("/study", "/study/**").permitAll()
        .requestMatchers("/signup", "/signup/**").permitAll()
        .requestMatchers("/complete").permitAll()
        .anyRequest().authenticated()
)
```

今まではすべてのページが認証不要だった。

しかし今後は、

- 誰でも閲覧できるページ
- ログインが必要なページ

を明確に分ける。

公開ページは `permitAll()` を設定し、それ以外は

```java
.anyRequest().authenticated()
```

によってログイン済みユーザーのみアクセス可能とする。

---

#### requestMatchers()とは？

認可ルールを適用する**対象URL**を指定するメソッドである。

例えば、

```java
.requestMatchers("/login").permitAll()
```

と書くことで、

> `/login` は誰でもアクセス可能

というルールになる。

---

#### PathRequest.toStaticResources().atCommonLocations()とは？

Spring Bootで使用する静的リソースをまとめて指定するための便利なメソッドである。

対象となる主なリソースは次のとおり。

- CSS
- JavaScript
- 画像
- favicon
- WebJars

これらを認証不要にしないと、CSSやJavaScriptまでログインが必要になり、画面レイアウトが崩れてしまう。

---

#### atCommonLocations()とは？

Spring Bootが標準で認識する静的リソース配置場所を表す。

```text
/static
/public
/resources
/META-INF/resources
```

つまり、

```java
PathRequest.toStaticResources().atCommonLocations()
```

は

> Spring Boot標準の静的リソースすべて

を意味する。

---

## アドミン専用画面の作成

**admin.html**

```html
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org"
      xmlns:layout="http://www.ultraq.net.nz/thymeleaf/layout"
      layout:decorate="~{layout/layout}">
<head>
    <meta charset="UTF-8">
    <title>アドミン専用画面</title>
</head>

<body>
    <h2>アドミン専用画面</h2>
</body>

</html>
```

現時点では最低限の画面だけ用意しておけばよい。

---

## AdminControllerを作成

```java
@Controller
public class AdminController {

    @GetMapping("/admin")
    public String getAdmin() {
        return "admin";
    }

}
```

---

## URL認可を設定

管理者権限（`ROLE_ADMIN`）を持つユーザーだけが `/admin` にアクセスできるよう設定する。

```java
http.authorizeHttpRequests(authorize -> authorize
        .requestMatchers(
                PathRequest.toStaticResources().atCommonLocations()
        ).permitAll()
        .requestMatchers("/").permitAll()
        .requestMatchers("/login").permitAll()
        .requestMatchers("/study", "/study/**").permitAll()
        .requestMatchers("/signup", "/signup/**").permitAll()
        .requestMatchers("/complete").permitAll()
        .requestMatchers("/admin").hasAuthority("ROLE_ADMIN")
        .anyRequest().authenticated()
)
```

`requestMatchers()`で対象URLを指定し、その後に`hasAuthority()`などの認可メソッドをつなげることで、そのURLへアクセスできるユーザーを制限できる。

---

### 認可メソッド

Spring Securityではロールを判定するためのメソッドが複数用意されている。

特に注意すべき点は、**`ROLE_`を自動補完するものとしないものがある**ことである。

| メソッド | 使用例 | 説明 |
| --- | --- | --- |
| `hasRole(String role)` | `hasRole("ADMIN")` | `ROLE_`が付いていない場合は自動的に付加される。 |
| `hasAnyRole(String... roles)` | `hasAnyRole("ADMIN", "GENERAL")` | 複数ロールを指定できる。`ROLE_`は自動付加される。 |
| `hasAuthority(String authority)` | `hasAuthority("ROLE_ADMIN")` | 指定した文字列をそのまま権限として判定する。 |
| `hasAnyAuthority(String... authorities)` | `hasAnyAuthority("ROLE_ADMIN", "ROLE_GENERAL")` | 複数権限を指定できる。 |

どちらを使用しても機能的な差はないが、プロジェクト内では書き方を統一した方が分かりやすい。

なお、これらの認可メソッドはThymeleafの表示制御でも利用できる。

---

## 動作確認

ROLEごとにログインし、アドミン専用画面へアクセスした。

| ロール | 結果 |
| --- | --- |
| ROLE_ADMIN | アドミン専用画面へアクセス成功 |
| ROLE_GENERAL | 403 Forbidden（アクセス拒否） |

期待どおり、管理者だけがアドミン専用画面へアクセスできることを確認した。

---

# 所感

認証と認可は混同しやすいが、それぞれ役割が異なることを理解できた。

また、URL単位でアクセス権限を制御できるため、新しい機能を追加する際もセキュリティルールを一元管理できる点がSpring Securityの大きな利点だと感じた。

特に、`anyRequest().authenticated()`を最後に配置し、公開するページだけを`permitAll()`で許可する「デフォルト拒否」の考え方は、安全性が高く、実務でも広く採用されている設計であることを学んだ。

---

# 次やること

## Step2 画面表示の認可

ログインユーザーの権限に応じて、画面上の表示内容を切り替える。