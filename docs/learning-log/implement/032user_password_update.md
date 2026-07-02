# パスワード変更機能の実装

前章で実装したユーザーID変更機能と同様に、ログイン中のユーザーがパスワードを変更できる機能を実装する。

---

# 準備

ユーザーID変更とパスワード変更はどちらも「会員情報編集」の機能であり、同じ階層に位置付けられる。

そのため、これまでユーザーID変更専用でありながら汎用的な名前を付けていたクラス名やファイル名、メソッド名を見直し、役割が分かるようにリファクタリングを行った。

## 1. Formクラス名の変更

```
EditForm
```

↓

```
EditUserIdForm
```

## 2. Controllerのメソッド名変更

```
getUserEdit
```

↓

```
getEditUserId
```

```
postUserEdit
```

↓

```
postEditUserId
```

## 3. HTMLファイル・パスの変更

```
user/edit.html
```

↓

```
user/edit/userId.html
user/edit/password.html
```

ユーザーID変更画面とパスワード変更画面を分離した。

## 4. user/profile.htmlを修正

プロフィール画面からユーザーID変更・パスワード変更へ遷移できるように修正した。

```html
<table class="table table-striped table-bordered table-hover text-center align-items-center">

    <thead>
        <tr>
            <th>ユーザーID</th>
            <th></th>
        </tr>
    </thead>

    <tbody>
        <tr>
            <td>
                <input type="text"
                       th:field="*{userId}"
                       th:errorclass="is-invalid"
                       class="form-control">

                <div class="invalid-feedback"
                     th:errors="*{userId}">
                </div>
            </td>

            <td class="text-center">
                <button type="submit"
                        class="btn btn-primary">
                    更新
                </button>
            </td>
        </tr>
    </tbody>

</table>
```

---

# 実装

## EditPasswordFormクラスを作成

```java
@PasswordMatch(
        passwordFieldName = "newPassword",
        passwordConfirmFieldName = "newPasswordConfirm"
)
@Data
public class EditPasswordForm {

    @NotBlank
    private String currentPassword;

    @NotBlank
    @Length(min = 12, max = 30)
    @Pattern(regexp = "^[a-zA-Z0-9]+$")
    private String newPassword;

    private String newPasswordConfirm;
}
```

### `@PasswordMatch`の再利用

`@PasswordMatch`は比較対象となるフィールド名を外部から指定できるように設計されている。

新規登録画面では

```java
password
passwordConfirm
```

を比較していたが、

今回は

```java
newPassword
newPasswordConfirm
```

を指定するだけで同じバリデーションをそのまま利用できた。

---

## UserMenuControllerにGET処理を追加

パスワード変更画面を表示するためのGETメソッドを追加した。

```java
@GetMapping("/user/edit/password")
public String getEditPassword(
        Model model,
        @ModelAttribute EditPasswordForm form) {

    return "user/edit/password";
}
```

---

## UserServiceImplにupdateUserPasswordメソッドを追加

```java
@Transactional
public void updateUserPassword(
        String userId,
        String currentPassword,
        String newPassword) {

    // 現在のユーザーを取得
    Users user = getUserOne(userId);

    if (user == null) {
        throw new IllegalArgumentException("ユーザーが存在しません");
    }

    // 現在のパスワードが一致するか確認
    boolean isMatch =
            passwordEncoder.matches(currentPassword, user.getPassword());

    if (!isMatch) {
        throw new IllegalArgumentException("現在のパスワードが正しくありません");
    }

    // パスワードをハッシュ化して更新
    user.setPassword(passwordEncoder.encode(newPassword));

    // 更新
    repository.save(user);
}
```

### パスワード重複チェックを行わない理由

ユーザーIDはシステム内で一意である必要があるため重複チェックを行っている。

一方、パスワードは他のユーザーと同じ値であっても問題ないため、重複チェックは不要である。

### PasswordEncoderを利用する理由

データベースにはパスワードを平文ではなくハッシュ化した状態で保存している。

そのため、

```java
passwordEncoder.matches(...)
```

で現在のパスワードを照合し、

```java
passwordEncoder.encode(...)
```

で新しいパスワードをハッシュ化して保存している。

---

## UserMenuControllerにPOST処理を追加

```java
@PostMapping("/user/edit/password")
public String postEditPassword(
        @AuthenticationPrincipal UserDetails loginUser,
        HttpSession session,
        Model model,
        @ModelAttribute @Validated EditPasswordForm form,
        BindingResult bindingResult) {

    // バリデーションエラー確認
    if (bindingResult.hasErrors()) {
        return getEditPassword(model, form);
    }

    try {

        userServiceImpl.updateUserPassword(
                loginUser.getUsername(),
                form.getCurrentPassword(),
                form.getNewPassword());

    } catch (IllegalArgumentException e) {

        bindingResult.rejectValue(
                "currentPassword",
                "invalid",
                e.getMessage());

        return getEditPassword(model, form);
    }

    // ログアウト状態にする
    SecurityContextHolder.clearContext();
    session.invalidate();

    return "redirect:/login";
}
```

### ログアウトさせる理由

パスワードを変更すると認証情報が変更されるため、現在のログイン状態を破棄し、新しいパスワードで再ログインしてもらう。

ユーザーID変更時と同様に

```java
SecurityContextHolder.clearContext();
session.invalidate();
```

を実行し、ログイン画面へリダイレクトする。

---

## user/edit/password.htmlを作成

```html
<form method="post"
      th:action="@{/user/edit/password}"
      th:object="${editPasswordForm}">

    <h2 class="text-center mb-4">パスワード変更</h2>

    <!-- グローバルエラー -->
    <div th:if="${#fields.hasGlobalErrors()}"
         class="alert alert-danger">

        <p th:each="error : ${#fields.globalErrors()}"
           th:text="${error}"
           class="mb-0">
        </p>

    </div>

    <!-- 現在のパスワード -->
    <div class="form-group mb-3">

        <label for="currentPassword">現在のパスワード</label>

        <input type="password"
               id="currentPassword"
               class="form-control"
               th:field="*{currentPassword}"
               th:errorclass="is-invalid">

        <div class="invalid-feedback"
             th:errors="*{currentPassword}">
        </div>

    </div>

    <!-- 新しいパスワード -->
    <div class="form-group mb-3">

        <label for="newPassword">新しいパスワード</label>

        <input type="password"
               id="newPassword"
               class="form-control"
               th:field="*{newPassword}"
               th:errorclass="is-invalid">

        <div class="invalid-feedback"
             th:errors="*{newPassword}">
        </div>

    </div>

    <!-- 新しいパスワード（確認） -->
    <div class="form-group mb-3">

        <label for="newPasswordConfirm">
            新しいパスワード（確認）
        </label>

        <input type="password"
               id="newPasswordConfirm"
               class="form-control"
               th:field="*{newPasswordConfirm}"
               th:errorclass="is-invalid">

        <div class="invalid-feedback"
             th:errors="*{newPasswordConfirm}">
        </div>

    </div>

    <!-- 確定ボタン -->
    <div class="text-center mt-3">

        <input type="submit"
               value="確定"
               class="btn btn-primary">

    </div>

</form>
```

---

# 実行

Spring Bootを再起動し、ログイン中のアカウントのパスワードを変更した。

変更後にログアウトされ、新しいパスワードでのみログインできることを確認した。

これにより、パスワード変更処理が正常に動作していることを確認できた。

---

# 所感

ユーザーID変更機能と実装内容は似ていたため、処理そのものは比較的スムーズに実装できた。

しかし、ユーザーID変更機能を実装した当初は、クラス名・ファイル名・メソッド名などを汎用的な名前にしていたため、そのままではパスワード変更機能を追加しにくいことが判明した。

結果として、ユーザーID変更に関係するFormクラス、HTMLファイル、Controllerメソッド、URLなどを役割に合わせた名前へ変更し、設計を見直すことになった。

実際にはパスワード変更機能そのものを実装するよりも、この設計の見直しに多くの時間を費やした。

今回の実装を通して、要件定義や設計の段階で将来の拡張性まで考慮しておくことの重要性を改めて実感した。実務でも、初期設計が不十分だと後から大きな修正が発生し、その分だけ開発コストが増加することを体感できた。

---

# 次やること

お気に入り登録機能の実装