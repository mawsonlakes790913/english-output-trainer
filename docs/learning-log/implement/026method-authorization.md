# 認可の実装 Step 3 メソッドの認可

## メソッド認可とは

すべてのURLに対して認可を設定できれば問題はないが、ロール数が増えるなどしてURL認可の設定が複雑になると、設定漏れが発生する可能性がある。

そのような場合に有効なのが**メソッド認可**である。

メソッド認可では、サービスクラスのメソッドごとに認可を設定できるため、SecurityConfigでURL認可の設定漏れがあったとしても、不正な処理の実行を防ぐことができる。

一方で、

- URL認可
- メソッド認可

の両方を設定すると、仕様変更時に両方を修正する必要があり、管理が煩雑になるというデメリットもある。

そのため、アプリケーションの規模や構成に応じて、どの認可方式を採用するかを検討する必要がある。

今回は、**ROLE_ADMIN**を持つユーザー（`system@example.co.jp`）だけがユーザー削除を実行できるように実装する。

---

# 準備：アドミン専用画面の実装

前回実装したアドミン専用画面は、ROLE_ADMINしかアクセスできない状態ではあるものの、中身は何も存在していない。

そこで今回は以下の機能を追加する。

- アドミン専用メニュー（`admin/menu.html`）の作成
- ユーザー一覧画面（`userList.html`）の作成
- ユーザー一覧から削除できる機能の実装

---

## 準備① アドミン専用メニュー画面へ遷移する

### AdminController.java

```java
@GetMapping("/admin/menu")
public String getAdminMenu() {
    return "admin/menu";
}
```

`/admin/menu`へアクセスすると、`admin/menu.html`を表示する。

---

## 準備② admin/menu.htmlの作成

管理者メニューを作成する。

今回は「ユーザー一覧」だけ選択できればよい。

```html
<body>
    <div layout:fragment="content" class="container pt-3">

        <h2>アドミン専用画面</h2>

        <div class="list-group mt-3">
            <a th:href="@{/admin/list}"
               class="list-group-item list-group-item-action">

                <i class="bi bi-person-fill me-2"></i>
                ユーザー一覧

            </a>
        </div>

    </div>
</body>
```

リンク先は

```
/admin/list
```

であるため、対応するControllerを作成する必要がある。

---

## 準備③ ユーザー一覧表示機能の実装

### UserServiceImpl.java

```java
// ユーザー一覧取得
public List<Users> getUsers() {

    List<Users> users = repository.findAll();

    return users;
}
```

`findAll()`はJpaRepositoryが標準で提供しているメソッドであり、usersテーブルの全件取得を行う。

### AdminController.java

```java
@GetMapping("/admin/list")
public String getUserList(Model model) {

    List<Users> userList = userServiceImpl.getUsers();

    model.addAttribute("userList", userList);

    return "userList";
}
```

取得したユーザー一覧をModelへ格納し、`userList.html`へ渡す。

### userList.html

```html
<table class="table table-striped table-bordered table-hover text-center">

    <thead>
        <tr>
            <th>ユーザーID</th>
            <th>Role</th>
            <th></th>
        </tr>
    </thead>

    <tbody>

        <tr th:each="item : ${userList}">

            <td th:text="${item.userId}"></td>
            <td th:text="${item.role}"></td>

            <td>
                （ここに削除ボタンを配置する）
            </td>

        </tr>

    </tbody>

</table>
```

`userList`は`List<Users>`であるため、`th:each`を利用してUsersを1件ずつ取り出して表示している。

---

## 準備④ ユーザー削除機能の実装

### UserServiceImpl.java

```java
// 指定したユーザー削除
public void deleteUserOne(String userId) {

    repository.deleteById(userId);

    log.info("削除対象={}", userId);
}
```

`deleteById()`はJpaRepositoryが標準で提供している削除メソッドである。

### AdminController.java

```java
@PostMapping("/admin/delete")
public String deleteUser(
        @RequestParam String userId,
        Model model) {

    userServiceImpl.deleteUserOne(userId);

    return "redirect:/admin/list";
}
```

削除対象はユーザーIDだけで十分なため、`@RequestParam`で受け取る。

### userList.html

```html
<tbody>

    <tr th:each="item : ${userList}">

        <form th:action="@{/admin/delete}" method="post">

            <td th:text="${item.userId}"></td>
            <td th:text="${item.role}"></td>

            <td>

                <input type="hidden"
                       name="userId"
                       th:value="${item.userId}">

                <button
                    type="submit"
                    class="btn btn-danger">

                    削除

                </button>

            </td>

        </form>

    </tr>

</tbody>
```

各行にフォームを配置することで、削除ボタンを押した行の`userId`だけがControllerへ送信される。

![完成予想1](../../images/026-1.png)
![完成予想1](../../images/026-2.png)

---

# ユーザー削除メソッドの認可

## UserServiceImpl.java

```java
@Transactional
@PreAuthorize("hasRole('ROLE_ADMIN')")
public void deleteUserOne(String userId) {

    repository.deleteById(userId);

    log.info("削除対象={}", userId);
}
```

---

## @PreAuthorize

メソッド単位で認可を行う場合は、`@PreAuthorize`アノテーションをクラスまたはメソッドに付与する。

```java
@PreAuthorize("hasRole('ROLE_ADMIN')")
```

と指定すると、

- メソッド実行前にログインユーザーのロールを確認する
- ROLE_ADMINを持っていれば処理を実行する
- 持っていなければ`AccessDeniedException`を送出する

という流れになる。

通常はServiceメソッドへ付与する。

理由は、Service層で例外を発生させることで、Controller側で

- エラーメッセージ表示
- 遷移先画面の変更
- ログ出力

などの制御を行えるからである。

また、同じServiceメソッドを別のControllerやREST APIなどから呼び出した場合でも、一貫して認可を適用できる。

クラスへ付与すると、そのクラス内のすべてのpublicメソッドが認可対象となる。

---

## @Transactional

削除処理はDB更新処理である。

例えば、

```java
repository.deleteById(userId);
```

の途中で例外が発生した場合、中途半端な状態でDB更新が終わってしまう可能性がある。

`@Transactional`を付与すると、

```text
正常終了
    ↓
 COMMIT

例外発生
    ↓
ROLLBACK
```

が自動的に行われる。

つまり、このメソッド全体を**1つのトランザクション**として扱うことを意味する。

---

# メソッド認可の有効化

デフォルトでは、`@PreAuthorize`によるメソッド認可は無効である。

そのため、`SecurityConfig`で有効化する。

### SecurityConfig.java

```java
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

...

@EnableMethodSecurity
public class SecurityConfig {
```

---

## @EnableMethodSecurity

`@PreAuthorize`などのメソッド認可を利用するには、

```java
@EnableMethodSecurity
```

を`@Configuration`が付いたクラスへ付与する。

これにより、Spring Securityがメソッド実行前に認可判定を行うようになる。

---

# 補足

今回実装した`deleteUserOne()`は、実際にはROLE_ADMINしかアクセスできない

```
/admin/**
```

配下からしか呼び出されない。

つまり、URL認可だけでも十分に保護されているため、

```java
@PreAuthorize("hasRole('ROLE_ADMIN')")
```

を付与しても、このアプリケーションでは効果は限定的である。

メソッド認可の本来の役割は、

**「そのメソッドがどこから呼び出されても認可を保証すること」**

にある。

例えば、将来的に一般ユーザーもアクセスできるControllerやREST APIを追加し、その中から誤って`deleteUserOne()`を呼び出してしまった場合でも、`@PreAuthorize`が付いていればROLE_ADMINを持つユーザー以外は処理を実行できない。

今回は教材の構成上、メソッド認可を学習するために、URL認可で保護された処理へ実装した。

---

# 所感

今回もStep2同様、実装自体は比較的容易であった。

今回のアプリケーションでは、URL認可によって管理者画面全体が保護されているため、メソッド認可を追加しても実用上の効果はそれほど大きくない。

しかし、実際のWebアプリケーションでは、同じServiceを複数のControllerやREST APIなどから利用することも多い。そのような場合には、メソッド認可を実装しておくことで、URL認可の設定漏れや将来的な機能追加によるセキュリティリスクを低減できる。

今回の実装を通して、URL認可とメソッド認可はどちらか一方ではなく、それぞれの役割を理解した上で適切に使い分けることが重要であると理解できた。

---

# 次にやること

- エラー画面の実装