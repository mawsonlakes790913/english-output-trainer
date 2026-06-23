# AOPによるServiceメソッドの処理時間計測（@Around）

## 概要

AOPを利用して、Serviceクラスのメソッド実行時間を計測する機能を実装した。

処理時間を計測するには、

```text
開始時刻を取得
↓
対象メソッド実行
↓
終了時刻を取得
↓
差分計算
```

という流れが必要になる。

そのため、メソッドの実行前後の両方に処理を差し込める `@Around` アノテーションを利用した。

イメージとしては、

```java
userService.signup();
```

が呼ばれた際に、

```text
開始時刻取得
↓
signup()実行
↓
終了時刻取得
↓
実行時間ログ出力
```

をAOPが自動的に挟み込む。

概念的には以下のような構造になる。

```text
[Aspect]
    ↓
signup()
    ↓
[Aspect]
```

---

## 実装内容

### PerformanceAspect.java

```java
@Aspect
@Component
@Slf4j
public class PerformanceAspect {

    /** 対象：[Service]をクラス名に含んでいること */
    @Pointcut("execution(* com.example.demo.service.*.*(..))")
    public void serviceMethods(){}

    @Around("serviceMethods()")
    public Object measure(ProceedingJoinPoint jp)
            throws Throwable {

        long start = System.currentTimeMillis();

        Object result = jp.proceed();

        long end = System.currentTimeMillis();

        log.info(
            "{} : {}ms",
            jp.getSignature().getName(),
            end - start
        );

        return result;
    }
}
```

---

# コード解説

## Pointcut定義

```java
@Pointcut("execution(* com.example.demo.service.*.*(..))")
public void serviceMethods(){}
```

Serviceパッケージ配下の全メソッドをAOPの対象とするPointcutを定義している。

以降の `@Around` では、

```java
@Around("serviceMethods()")
```

のように再利用できるため、同じ式を何度も記述する必要がなくなる。

---

## @AroundではJoinPointではなくProceedingJoinPointを使用する

```java
public Object measure(ProceedingJoinPoint jp)
```

`@Before` や `@AfterThrowing` では `JoinPoint` を使用していたが、`@Around` では `ProceedingJoinPoint` を使用する。

これは `ProceedingJoinPoint` が、

```java
jp.proceed();
```

というメソッドを持っており、

```text
対象メソッドを実行する
```

という役割を持っているためである。

---

## ProceedingJoinPointが持つ情報

`ProceedingJoinPoint` には、

- どのメソッドが呼ばれたか
- どのクラスのメソッドか
- 引数は何か

などの情報が格納されている。

そのため、

```java
jp.getSignature()
```

のようにして実行中のメソッド情報を取得できる。

---

## 処理開始時刻の取得

```java
long start = System.currentTimeMillis();
```

現在時刻をミリ秒単位で取得している。

これを処理開始時刻として保存する。

---

## なぜintではなくlongなのか

現在時刻は、

```text
1970年1月1日 00:00:00 UTC
```

からの経過ミリ秒数で管理されている。

そのため、

```text
1780000000000
```

のような非常に大きな値になる。

`int` では格納できないため、

```java
long
```

を使用する。

---

## jp.proceed()で対象メソッドを実行する

```java
Object result = jp.proceed();
```

ここで実際に対象のServiceメソッドが実行される。

例えば、

```java
userService.signup();
```

が呼ばれていた場合、

この `proceed()` の位置で実際の `signup()` が実行される。

---

## なぜObject resultに格納するのか

Serviceメソッドの戻り値は統一されていない。

例えば、

```java
public void signup()
```

もあれば、

```java
public User findById()
```

も存在する。

AOPはどのメソッドにも適用されるため、

事前に戻り値の型を決めることができない。

そのため、

```java
Object result
```

で受け取っている。

なお、

```java
void
```

メソッドの場合は内部的に `null` が返される。

---

## jp.proceed();だけではダメな理由

例えば、

```java
jp.proceed();
```

だけにしてしまうと、

対象メソッドが返した戻り値を受け取れない。

その結果、

```java
User user = userService.findUser();
```

のような処理で本来返されるべき値が失われてしまう。

そのため、

```java
Object result = jp.proceed();
```

として戻り値を保持する必要がある。

---

## throws Throwableが必要な理由

`jp.proceed()` は内部的に

```java
Object proceed() throws Throwable
```

として定義されている。

そのため、

```java
jp.proceed();
```

を呼び出す側も

```java
throws Throwable
```

を書く必要がある。

---

## なぜExceptionではなくThrowableなのか

Javaの例外階層は、

```text
Throwable
├─ Error
└─ Exception
```

となっている。

AOPはどのメソッドに対しても適用されるため、

どの種類の例外が発生するか事前に分からない。

そのため、

最上位クラスである

```java
Throwable
```

で受ける設計になっている。

---

## なぜtry-catchしないのか

今回の実装では、

```java
throws Throwable
```

として例外を呼び出し元へそのまま再スローしている。

もし、

```java
try {
    jp.proceed();
} catch (Exception e) {
    log.error("エラー");
    return null;
}
```

のようにしてしまうと、

例外が握りつぶされる。

その結果、

本来発生した例外の原因が分かりにくくなり、

後続処理で別のエラーが発生する可能性もある。

---

## ErrorAspectとの関係

前回実装した

```java
@AfterThrowing
```

を利用したErrorAspectは、

例外が外部へ投げられたときに動作する。

そのため、

```java
throws Throwable
```

で例外を再スローする設計との相性が良い。

処理の流れは以下の通り。

```text
Controller
↓
Service
↓
PerformanceAspect
↓
例外発生
↓
throws Throwable
↓
ErrorAspect
↓
エラーログ出力
```

---

## 処理終了時刻の取得

```java
long end = System.currentTimeMillis();
```

対象メソッドの処理終了後の時刻を取得している。

---

## 実行時間の計算

```java
end - start
```

によって、

対象メソッドが何ミリ秒かかったかを算出している。

---

## jp.getSignature().getName()とは

```java
jp.getSignature()
```

はメソッド情報全体を取得する。

例えば、

```java
signup()
```

や

```java
getQuestion()
```

などの情報を保持している。

さらに、

```java
jp.getSignature().getName()
```

とすることで、

```text
signup
```

や

```text
getQuestion
```

といったメソッド名だけを取得できる。

---

## ログ出力

```java
log.info(
    "{} : {}ms",
    jp.getSignature().getName(),
    end - start
);
```

これにより、

```text
signup : 465ms
```

のようなログが出力される。

どのメソッドが何ミリ秒かかったかを確認できる。

---

## 戻り値を返却する

```java
return result;
```

AOPが取得した戻り値を呼び出し元へ返却する。

これを書かないと、

対象メソッドの戻り値が失われてしまう。

そのため、

```java
Object result = jp.proceed();
return result;
```

は `@Around` の定型文とも言える重要な記述である。

---

# 実行結果

以下のログが出力された。

```text
c.example.demo.aspect.PerformanceAspect getRandomQuestion : 17ms
c.example.demo.aspect.PerformanceAspect getQuestion : 6ms
c.example.demo.aspect.PerformanceAspect getRandomQuestion : 7ms
c.example.demo.aspect.PerformanceAspect getQuestion : 6ms
c.example.demo.aspect.PerformanceAspect signup : 465ms
```

やはり単純に問題を取得するだけの `getQuestion()` が最も高速であり、

`repository.save(...)` やパスワードハッシュ化処理を含む `signup()` は比較的時間がかかっていることが確認できた。

今回の結果から、

- `@Around` が正常に動作している
- `ProceedingJoinPoint.proceed()` が正常に実行されている
- 実行時間計測が正しく行われている

ことが確認できた。

AOPによる処理時間計測は成功である。

---

# 所感

今回の `@Around` は、これまで学習した `@Before` や `@AfterThrowing` と比較して新しい概念が多かった。

特に、

- `ProceedingJoinPoint`
- `Object result`
- `throws Throwable`
- `return result`

などは初見では理解しにくい内容だった。

しかし、

なぜ対象メソッドの戻り値を保持する必要があるのか、

なぜ例外を再スローする設計になっているのか、

といった背景まで調べながら学習することで、それぞれの仕組みには明確な理由があることが理解できた。

最初は複雑に見えたが、仕組みを理解すると非常に納得感のある実装であった。

---

# 次回やること

- Spring Securityを導入する
- ログイン機能を実装する
- 認証・認可の仕組みを学習する
- アプリケーションへセキュリティを適用する