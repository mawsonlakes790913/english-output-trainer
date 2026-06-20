# Formクラスとデータバインドの実装

## 実装内容

前回までの実装で、ログイン画面およびユーザー登録画面のフォーム自体は作成できていた。しかし、入力された値をサーバー側へ受け渡す仕組み（Formクラス）については未実装だったため、今回はFormクラスの作成とデータバインドの実装を行った。

なお、本実装段階ではバリデーションやカスタムエラーチェックは導入しておらず、ログイン機能そのものも未実装である。

今回の目的は以下の流れを実現することである。

1. ユーザー登録画面で値を入力する
2. 入力値をFormクラスへ受け渡す
3. 処理完了後にログイン画面へリダイレクトする

Controller側では `@ModelAttribute` を使用し、FormクラスのオブジェクトをModelとしてHTMLへ受け渡した。

HTML側では、

```html
th:object="${signupForm}"
th:field="*{userId}"
```

のように `th:object` と `th:field` を利用することで、各入力項目とFormクラスのフィールドを紐付けた。

これにより、ユーザーが入力した値が自動的にFormクラスへ格納される仕組みを実装できた。

---

## 学習したこと・考察

### GET時にModelとFormクラスが必要な理由

ユーザー登録画面を表示する `GetMapping` では、

```java
@GetMapping("/signup")
public String getSignup(
        Model model,
        @ModelAttribute SignupForm form)
```

のように `Model` と `Formクラス` の両方を引数として受け取っている。

これは、画面表示時に空のFormオブジェクトを生成し、そのオブジェクトをHTMLへ渡す必要があるためである。

Thymeleaf側の

```html
th:object="${signupForm}"
```

は、Controllerから渡されたFormオブジェクトを参照しているため、画面表示時点でModelに登録されていなければならない。

### POST時にFormクラスだけで良い理由

一方、フォーム送信後の `PostMapping` では、

```java
@PostMapping("/signup")
public String postSignup(
        @ModelAttribute SignupForm form)
```

のようにFormクラスのみを受け取っている。

これは今回の実装では、入力値を受け取った後にログイン画面へリダイレクトするだけであり、画面へ値を渡して表示する処理が存在しないためである。

ModelはControllerからViewへ値を渡すための仕組みであるため、リダイレクトのみを行う場合は不要となる。

### SignupForm と signupForm の違い

実装中に気になった点として、Java側のクラス名は

```java
SignupForm
```

であるのに対し、Thymeleaf側では

```html
th:object="${signupForm}"
```

と記述されている点があった。

一見すると名前が一致していないように見えるが、Springでは `@ModelAttribute` を使用した際、クラス名の先頭文字を小文字にした名前をModel属性名として自動登録する。

つまり、

```java
SignupForm
```

というクラスは、

```text
signupForm
```

という名前でModelへ登録される。

そのため、HTML側では

```html
${signupForm}
```

という記述で正しく参照できる。

---

## 所感

今回は入力項目がまだ少なく、バリデーションやカスタムエラーチェックも実装していなかったため、比較的スムーズに実装を進めることができた。

一方で、FormクラスとThymeleafのデータバインドの仕組みを理解したことで、Spring Bootにおけるフォーム処理の基本的な流れを把握できたことは大きな収穫だった。

また、GETとPOSTでの `@ModelAttribute` の役割の違いや、Modelが必要となる場面・不要となる場面についても理解を深めることができた。

---

## 次回やること

- バリデーションの導入
- カスタムエラーチェックの導入
- エラーメッセージの画面表示
- 入力値保持の確認