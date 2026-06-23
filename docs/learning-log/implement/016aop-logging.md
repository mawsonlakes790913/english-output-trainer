# AOP実装

## 概要

Spring Bootの学習の一環としてAOP（Aspect Oriented Programming：アスペクト指向プログラミング）を導入した。

AOPは「何でもかんでも共通化するための機能」ではなく、

- どの処理を共通化するか
- どの層に適用するか
- 導入コストに見合うメリットがあるか

を考えながら導入する必要がある。

今回作成している English Output Trainer は比較的小規模な学習用アプリケーションであり、複雑な業務システムではないため、過度なAOP導入はかえって可読性を下げてしまう。

そのため、まずは以下の4つの候補を検討した。

---

## 導入候補

### ① Service層の開始・終了ログ

例えば

```java
public void signup(Users user)
```

が呼ばれた場合、

```text
UserService.signup() 開始
UserService.signup() 終了
```

というログを出力する。

一般的には Service 層に適用することが多く、

- Controller
- Repository

まで全てログ出力すると逆にログが大量になり、重要な情報が埋もれてしまう。

---

### ② 例外を記録する

AOPですべての例外を処理するのではなく、

> 「例外が発生した事実をログに残す」

ことだけを担当させる。

例外そのものは

- Service
- Repository
- Controller
- Spring Framework
- 外部ライブラリ

など様々な場所で発生する可能性がある。

しかし、発生した例外は最終的にどこかで受け取られ、対処される。

今回のアプリでは、

```text
Controller
↓
Service
↓
DuplicateKeyException発生
↓
AOPがログ出力
↓
Controllerのcatch
↓
画面にエラー表示
```

という流れになる。

つまりAOPは

```text
例外を処理する
```

のではなく、

```text
例外発生を記録する
```

だけである。

---

### try-catch と @ExceptionHandler の違い

例外を処理する方法として最も一般的なのは

```java
try {
    ...
} catch (...) {
    ...
}
```

である。

一方、Springでは

```java
@ExceptionHandler
```

を利用して例外処理を別メソッドへ分離することもできる。

例えば、

```java
@PostMapping("/signup")
public String postSignup(...) {

    try {
        userServiceImpl.signup(users);

    } catch (DuplicateKeyException e) {
        ...
    }
}
```

を

```java
@PostMapping("/signup")
public String postSignup(...) {

    userServiceImpl.signup(users);

    return "redirect:/signup/complete";
}
```

と

```java
@ExceptionHandler(DuplicateKeyException.class)
public String handleDuplicateKeyException(
        DuplicateKeyException e) {

    return "error";
}
```

に分離できる。

---

### 今回 @ExceptionHandler を採用しなかった理由

今回のユーザー登録画面では、

```java
bindingResult.rejectValue(...)
```

を利用して、

```text
ユーザーID入力欄の直下
```

へエラーメッセージを表示したい。

そのためには、

```java
BindingResult
```

へエラー情報を追加する必要がある。

現在の

```java
try {
    ...
} catch (...) {
    bindingResult.rejectValue(...);
}
```

の方が自然で分かりやすく、

わざわざ

```java
@ExceptionHandler
```

へ分離するメリットが少ない。

したがって今回は Controller の try-catch を採用した。

---

### ③ Serviceクラスの実行時間計測

Serviceメソッドの開始から終了までの実行時間を計測する。

実際の流れは

```text
AOP開始
↓
Serviceメソッド実行
↓
AOP終了
```

である。

Controllerはほとんど画面制御しか行っておらず、

業務処理の大部分は Service が担当しているため、

このアプリでは Service 層だけ計測すれば十分と判断した。

なお、この実装では

```java
@Around
```

を利用するため、他のAOPより少し複雑になる。

---

### ④ @Transactional の導入

@Transactional は

```text
途中で失敗したら全部なかったことにする
```

ための機能である。

例えば

```text
ユーザー登録
↓
権限登録
↓
途中でエラー
```

となった場合、

@Transactional がなければ

```text
ユーザーだけ登録された
```

という中途半端な状態になる可能性がある。

@Transactional を付けると

```text
全部成功
または
全部取り消し
```

になる。

現時点では更新系機能が少ないため恩恵は小さいが、

今後予定している

- 登録機能
- 更新機能
- 削除機能
- お気に入り管理

などでは重要になる。

---

### 今回実装する内容

今回は

- ① Service層の開始・終了ログ
- ② 例外ログ

のみ実装する。

③は別の学習回に回し、

④は更新系機能が増えてから導入する。

なお、

①〜③は自分で作るAOPであり、

④の@TransactionalはSpringが内部でAOPを利用して提供している機能である。

---

# 実装前に知っておくこと

## AOP関連用語

| 用語 | 説明 |
|--------|--------|
| Advice | AOPで実行する処理内容 |
| JoinPoint | Adviceを挿入できる対象 |
| Pointcut | Adviceを適用するJoinPointを指定する式 |

---

## Adviceとは

Adviceとは、

> AOPで実際に何をするか

を定義する処理である。

例えば、

### 開始・終了ログ

```text
開始時にログ出力
終了時にログ出力
```

---

### 例外ログ

```text
例外発生時に
例外クラス名
例外メッセージ
をログ出力
```

---

### 実行時間計測

```text
開始時刻取得
↓
対象メソッド実行
↓
終了時刻取得
↓
実行時間出力
```

---

## Adviceの実行タイミング

Adviceは「何をするか」だけではなく、

> いつ実行するか

も指定する必要がある。

| 実行タイミング | 説明 |
|--------|--------|
| @Before | 実行前 |
| @After | 実行後（正常・異常問わず） |
| @AfterReturning | 正常終了時のみ |
| @Around | 実行前後 |
| @AfterThrowing | 例外発生時のみ |

---

## JoinPointとは

Adviceを挿入できる場所である。

例えば

- メソッド
- コンストラクタ
- フィールドアクセス

などが存在する。

ただし Spring AOP では

```text
メソッド実行
```

のみが JoinPoint である。

---

## Pointcutとは

どの JoinPoint に Advice を適用するかを指定する式である。

例えば、

開始・終了ログの場合

### Advice

```text
開始ログを出す
終了ログを出す
```

### Pointcut

```text
Service層に適用する
Controller層に適用する
```

などを指定する。

---

## AOP設計時に考えること

今回検討した

- 開始・終了ログ
- 例外ログ
- 実行時間計測

については、

それぞれ

```text
Advice
JoinPoint
Pointcut
```

を定義する必要がある。

一方、

@Transactional は Spring が内部でAOPを利用しているため、

利用者側がそこまで深く意識する必要はない。

---

# なぜAdviceを割り込ませられるのか？

例えば、

```java
@Controller
@RequiredArgsConstructor
public class SampleController {

    private final SampleService service;

    @GetMapping("/hoge")
    public void get() {
        service.someMethod();
    }
}
```

を考える。

普通に考えると

```text
Controller
↓
Service
```

が直接呼ばれそうである。

しかし実際には

```text
Controller
↓
Proxy
↓
Service
```

という構造になっている。

SpringはIoCコンテナでBeanを管理しているため、

Proxy（代理人）を間に挟むことができる。

その結果、

```text
Proxy
↓
AOP処理
↓
本来のメソッド
```

という流れを実現している。

これがSpring AOPの基本的な仕組みである。

---

# 準備

AOPを利用するために pom.xml へ以下を追加した。

```xml
<!-- Spring AOP -->
<dependency>
    <groupId>org.springframework</groupId>
    <artifactId>spring-aop</artifactId>
</dependency>

<!-- AspectJ -->
<dependency>
    <groupId>org.aspectj</groupId>
    <artifactId>aspectjweaver</artifactId>
</dependency>
```

## spring-aop

SpringでAOPを利用するためのライブラリ。

## aspectjweaver

Pointcut式を解析するためのライブラリ。

例えば

```java
execution(* com.example.demo.service.*.*(..))
```

のようなPointcut式を解釈しているのは AspectJ である。

---

# ① Service層の開始・終了ログ

（以下、実装コードと詳細解説）

※ここから先は今回作成した LogAspect.java のコードと説明を記載

---

# ② 例外を記録する

（以下、実装コードと詳細解説）

※ここから先は今回作成した ErrorLogAspect.java のコードと説明を記載

---

## 動作確認① DuplicateKeyException

既に登録済みのユーザーIDを再登録したところ、

```text
com.example.demo.aspect.ErrorLogAspect :
例外発生 [DuplicateKeyException] 既に存在するユーザーです
```

と出力された。

Serviceで発生した例外をAOPが正常に検知できていることを確認できた。

---

## 動作確認② RuntimeException

AOPの動作確認のため、

### UserServiceImpl

```java
public void testException() {
    throw new RuntimeException("AOP動作確認用の例外");
}
```

を追加した。

### SignupController

```java
@GetMapping("/test-error")
public String testError() {

    userServiceImpl.testException();

    return "home";
}
```

を追加し、

```text
http://localhost:8080/test-error
```

へアクセスした。

結果、

```text
ERROR
com.example.demo.aspect.ErrorLogAspect
例外発生 [RuntimeException] AOP動作確認用の例外
```

と出力された。

このことから、

- Pointcut
- Advice
- Proxy

が正しく機能しており、

AOPが正常に動作していることを確認できた。

---

# 所感

実際に実装するまでは、

- LogAspect と ErrorLogAspect の違い
- try-catch と @ExceptionHandler の違い

が曖昧だった。

しかし実装と動作確認を行ったことで、

それぞれの役割を明確に理解できた。

また、AOPは単にログを出すだけでなく、

- 共通処理の集約
- 例外監視
- 実行時間計測

など様々な用途で利用できることも理解できた。

今回のアプリでは @ExceptionHandler を採用しなかったが、

今後アプリケーションが大規模化した場合には導入を検討する価値がある。

また、LogAspectについても今後

- 実行時間計測
- 性能監視

などへ発展させていく予定である。

実際に手を動かして実装したことで、AOPの仕組みへの理解が大きく深まった。

---

# 次にやること

- Serviceクラスの「メソッドの開始から終了まで」を計測する（@Around）