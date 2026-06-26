# CSRF対策

## 概要

CSRF（Cross-Site Request Forgery）とは、悪意のあるサイトからユーザーになりすましてリクエストを送信させる攻撃である。

Spring Securityでは、この攻撃を防ぐために**CSRFトークン**を利用する。CSRFトークンとは、サーバーが生成する一時的な認証用のランダムな文字列である。

フォーム送信時にこのトークンを一緒に送信し、サーバー側に保存されているトークンと一致した場合のみリクエストを受け付ける。

なお、CSRFトークンが必要になるのは、主にデータを更新するHTTPメソッド（POST、PUT、DELETEなど）であり、画面表示のみを行うGETリクエストでは通常不要である。

---

# 準備

今回の動作確認では、Spring Securityによる自動処理ではなく、CSRFトークンの仕組みを学習するため、一時的に`signup.html`の`form`タグを修正した。

```html
th:action="@{/signup}"
```

↓

```html
action="/signup"
```

通常は`th:action`を使用するとSpring SecurityがCSRFトークンを自動的に追加するため、今回はその動作を無効にし、手動でCSRFトークンを実装する。

---

# CSRF対策の有効化

Spring SecurityではCSRF対策はデフォルトで有効になっている。

これまでは学習しやすいように無効化していたため、`SecurityConfig.java`を修正してデフォルト設定へ戻した。

```java
// CSRFを無効化
// http.csrf(csrf -> csrf.disable());
```

上記をコメントアウト（または削除）することで、CSRF対策が有効になる。

---

# HTTPリクエストへCSRFトークンを追加

フォーム送信時にCSRFトークンを送るため、`signup.html`へhiddenフィールドを追加した。

```html
<form method="post"
      action="/signup"
      th:object="${signupForm}">

    <input type="hidden"
           th:name="${_csrf.parameterName}"
           th:value="${_csrf.token}" />

    ...
</form>
```

---

# CSRFトークンとは

CSRFトークンとは、サーバーと画面との間でやり取りされる認証用のランダムな文字列である。

フォーム送信時にこのトークンを一緒に送信し、サーバー側で保持しているトークンと一致した場合のみ処理が続行される。

ユーザーが入力する値ではないため、`type="hidden"`の隠しフィールドとして送信する。

---

# `th:name="${_csrf.parameterName}"`

これは**CSRFトークンを送信するパラメータ名**を取得している。

通常は

```text
_csrf
```

が返されるため、

```html
th:name="${_csrf.parameterName}"
```

は実質

```html
name="_csrf"
```

と同じ意味になる。

将来、

```java
.parameterName("token")
```

のように設定を変更した場合でも、

```html
name="token"
```

へ自動的に切り替わるため、HTMLを書き換える必要がない。

---

# `th:value="${_csrf.token}"`

こちらは実際のCSRFトークンを取得している。

例えば

```text
8df1b8c0-3d81-46d4-a4b7-f86a2dca1234
```

のようなランダムな文字列が生成され、hiddenフィールドへ埋め込まれる。

---

# サーバー側の動き

## ① ユーザーが新規登録画面へアクセスする

ブラウザから

```http
GET /signup
```

というリクエストが送信される。

```
ブラウザ
    │
    │ GET /signup
    ▼
Spring Boot
```

---

## ② Spring SecurityがCSRFトークンを生成する

Spring Securityは、このユーザー専用のランダムな文字列を生成する。

例えば

```text
8df1b8c0-3d81-46d4-a4b7-f86a2dca1234
```

のような文字列である。

生成されたトークンは`CsrfToken`オブジェクトとして管理される。

---

## ③ サーバーがセッションへ保存する

生成したトークンは`HttpSession`へ保存される。

```
Session

JSESSIONID = A1B2C3

保存内容

_csrf = 8df1b8c0-3d81-46d4-a4b7-f86a2dca1234
```

つまり、

> このブラウザで使用する正しいCSRFトークン

をサーバー側だけが保持している。

---

## ④ Spring SecurityがThymeleafへ渡す

Spring Securityは内部で

```java
model.addAttribute("_csrf", csrfToken);
```

と同等の処理を自動で実行している。

そのため、コントローラーで

```java
model.addAttribute("_csrf", ...);
```

を書く必要はなく、Thymeleafから

```html
${_csrf}
```

を利用できる。

---

## ⑤ ThymeleafがHTMLを生成する

Spring Securityから渡された

```text
parameterName = "_csrf"

token = "8df1b8c0-3d81..."
```

を利用して、

```html
<input
    type="hidden"
    name="_csrf"
    value="8df1b8c0-3d81-46d4-a4b7-f86a2dca1234">
```

というHTMLが生成され、ブラウザへ送られる。

```
Spring Boot
        │
        │ HTML生成
        ▼
ブラウザ

<input
    type="hidden"
    name="_csrf"
    value="8df1b8c0-3d81-46d4-a4b7-f86a2dca1234">
```

---

## ⑥ ユーザーが登録ボタンを押す

ブラウザはフォーム内の全データを送信する。

```http
POST /signup

userId=taro
password=1234
_csrf=8df1b8c0-3d81-46d4-a4b7-f86a2dca1234
```

---

## ⑦ Spring Securityが照合する

Spring Securityは

- セッションへ保存されているCSRFトークン
- POSTされたCSRFトークン

を比較する。

```
Session

8df1b8c0-3d81...

↓

POST

8df1b8c0-3d81...
```

一致した場合は

> 正規の画面から送信されたリクエスト

と判断し、処理を続行する。

一致しない場合は

> 第三者による不正なリクエスト

と判断し、

```text
403 Forbidden
```

を返して処理を拒否する。

---

# なぜ悪意あるサイトでは突破できないのか

悪意あるサイトは、ターゲットとなるサーバーが生成したHTMLではないため、サーバーが発行したCSRFトークンを取得できない。

そのため、正しいCSRFトークンを付けてPOSTすることができず、サーバー側で照合に失敗する。

---

# 悪意あるサイトもSpring Boot + Spring Securityだったら？

仮に悪意あるサイトもSpring Boot・Spring Security・Thymeleafを利用していたとしても問題はない。

その場合、

```html
<input
    type="hidden"
    th:name="${_csrf.parameterName}"
    th:value="${_csrf.token}">
```

を書くことはできる。

しかし生成されるのは

**悪意あるサイト自身が発行したCSRFトークン**

であり、

**ターゲットサイトが発行したCSRFトークンとは全く異なる。**

つまり、

```
ターゲットサイト

CSRF = ABC123
```

```
悪意あるサイト

CSRF = XYZ999
```

となるため、

```
ABC123 ≠ XYZ999
```

となり照合に失敗する。

CSRFトークンはサーバー（オリジン）ごとに管理されているため、他サイトが発行したトークンは利用できない。

---

# 補足

通常は

```html
<form method="post"
      th:action="@{/signup}">
```

と記述する。

`th:action`を利用すると、Spring SecurityがCSRFトークンのhiddenフィールドを自動的に追加してくれるため、

```html
<input
    type="hidden"
    th:name="${_csrf.parameterName}"
    th:value="${_csrf.token}">
```

を自分で実装する必要はない。

今回の学習では、

- CSRFトークンとは何か
- hiddenフィールドでどのように送信されるのか

を理解するため、一時的に`action`へ変更して手動実装を行った。

動作確認後は、再び`th:action`へ戻している。

---

# 所感

CSRF対策は単にhiddenフィールドを追加するだけではなく、サーバーが生成したトークンをセッションへ保存し、フォーム送信時に照合することで不正なリクエストを防いでいることが理解できた。

また、`th:name`や`th:value`が単なるThymeleafの文法ではなく、Spring Securityが内部で保持している`CsrfToken`オブジェクトと連携して動作していることを学んだ。

さらに、悪意あるサイトがSpring BootとSpring Securityを利用していたとしても、CSRFトークンはサーバーごとに管理されているため、ターゲットサイトと同じトークンを生成・取得することはできず、CSRF攻撃を防げる仕組みになっていることを理解できた。

---

# 次やること

- Remember-Me認証機能の追加