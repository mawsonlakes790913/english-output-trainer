# 0063 AOP（Aspect Oriented Programming）の見直し

## 概要

これまで実装してきたAOPを見直し、現在のアプリケーションに適した構成へ整理する。

AOPによって、このアプリケーションで実現できそうなことは次の3つである。

1. Controller・Serviceの開始・終了ログの共通化
2. 例外ログの共通化
3. 実行時間の計測

---

# AOPで実現できること

## ① Controller・Serviceの開始・終了ログの共通化

例えば、

```java
log.info("問題登録開始");
log.info("問題登録終了");
```

のような定型ログを各メソッドへ記述する代わりに、AOPで一括出力する。

---

## ② 例外ログの共通化

`@AfterThrowing` を利用し、

- 発生した例外
- 発生したクラス
- 発生したメソッド

などを共通的にログ出力する。

---

## ③ 実行時間の計測

`@Around` を利用し、

```
検索処理：350ms
```

のような実行時間を計測する。

---

# 今回のAOPの目的

今回の見直しでは、

- Controller・Serviceに散在している開始・終了ログを共通化する
- PerformanceAspectの計測対象をControllerまで拡張する

ことを目的とする。

---

## 開始・終了ログを共通化する理由

開始・終了ログは、

> 「どのメソッドが実行されたか」

という共通情報であり、AOPだけで十分取得できる。

例えば、

```
StudyService.getQuestions() 開始
StudyService.getQuestions() 終了
```

のようなログは、どのメソッドでも同じ形式で出力できる。

---

## 業務イベントログはServiceへ残す

一方、

- 問題登録
- 問題更新
- 問題削除
- ユーザー登録
- パスワード変更

などは業務イベントである。

AOPから取得できる情報は、

- クラス名
- メソッド名
- 引数
- 戻り値

程度であり、

> 「問題を登録した」
>
> 「評価をUPDATEした」

という業務上の意味までは判断できない。

そのため、

業務イベントログは引き続きService側で出力する。

---

## 今回例外ログを見直さない理由

今回は例外ログについては対象外とする。

理由は以下のとおりである。

- 現在のアプリケーションでは例外ログを出力する運用を採用していない。
- 前チャプターの例外処理見直しでも対象外としている。
- AOPだけ例外ログを追加すると、ログ設計の方針が統一されなくなる。

---

# 開始・終了ログの共通化

## 方針

対象は、

- Controller
- Service

の **すべてのpublicメソッド** とする。

理由は次の3つである。

### ① AOPは共通処理を一括適用する仕組みだから

開始・終了ログは特定メソッドではなく、

- Controller
- Service

というレイヤー全体で共通の処理である。

そのため、

```
com.example.demo.controller
com.example.demo.service
```

全体へ適用する方がAOPの考え方に適している。

---

### ② 将来追加されるメソッドも自動で対象になる

例えば、

```
QuestionService
├── addQuestion()
├── updateQuestion()
└── copyQuestion()
```

というメソッドが追加された場合でも、

```
copyQuestion() 開始
copyQuestion() 終了
```

が自動的に出力される。

---

### ③ ログルールが統一される

一部のメソッドだけ開始・終了ログがあると、

```
なぜこのメソッドだけログがないのか？
```

という疑問が生まれる。

レイヤー全体へ適用することで、

> Controller・Serviceはすべて開始・終了ログを出力する

という統一したルールになる。

---

# チャプター16のLogAspect

チャプター16では既にService用のLogAspectを実装していた。

対象は

```java
@Pointcut("execution(* com.example.demo.service.*.*(..))")
```

であり、

- Serviceのpublicメソッド
- 実行前（`@Before`）
- 実行後（`@After`）

のみを対象としていた。

なお、

`@After` は

- 正常終了
- 例外終了

の両方で実行される。

一方、

Controllerは対象外であった。

そのため今回は、

Controllerも対象となるようにPointcutを追加する。

```java
@Pointcut("execution(* com.example.demo.controller.*.*(..))")
public void controllerMethods() {}
```

そして、

```java
@Before("serviceMethods() || controllerMethods()")
```

```java
@After("serviceMethods() || controllerMethods()")
```

とすることで、

Controller・Serviceの両方へ開始・終了ログを適用する。

# 実装の前に…

## チャプター62では一部のControllerとServiceだけに開始・終了ログを追加したが？

結論から言うと、チャプター62で追加した開始・終了ログをそのまま全て残すわけではない。

今回AOPを導入するのであれば、

- 開始・終了を表すだけのログはAOPへ移行する。
- Serviceには業務イベントログだけを残す。

という構成にする。

| メソッド | 現在のログ | AOP導入後 |
|----------|-----------|-----------|
| `ReviewController.getReviewSuspend()` | 復習中断開始 / 完了 | 削除（AOPが開始・終了を出力） |
| `ReviewController.getReviewQuit()` | 復習終了開始 / 完了 | 削除（AOPが開始・終了を出力） |
| `StudyController.getStudySuspend()` | 学習中断開始 / 完了 | 削除（AOPが開始・終了を出力） |
| `StudyController.getStudyQuit()` | 学習終了開始 / 完了 | 削除（AOPが開始・終了を出力） |
| `AdminService.deleteOneUser()` | ユーザー削除開始 / 完了 | 業務イベントログへ変更して残す |
| `AdminService.addQuestion()` | 問題登録完了 | 残す |
| `AdminService.updateOneQuestion()` | 問題更新前 / 更新後 | 残す |
| `AdminService.deleteOneQuestion()` | 問題削除開始 / 完了 | 業務イベントログへ変更して残す |
| `EvaluationService.updateEvaluation()` | 評価更新開始 / 完了 | 完了ログのみ残す |
| `FavoritesService.toggleFavorite()` | お気に入り追加・解除開始 / 完了 | 完了ログのみ残す |
| `UserAccountService.updateUserId()` | ユーザーID変更開始 / 完了 | 完了ログのみ残す |
| `UserAccountService.updateUserPassword()` | パスワード変更開始 / 完了 | 完了ログのみ残す |
| `UserAccountService.cancelMembership()` | 退会開始 / 完了 | 完了ログのみ残す |

---

# 削除してよいケース

## Controllerの開始・終了ログ

対象

- `ReviewController.getReviewSuspend()`
- `ReviewController.getReviewQuit()`
- `StudyController.getStudySuspend()`
- `StudyController.getStudyQuit()`

これらは

```java
log.info("○○開始");
log.info("○○完了");
```

というメソッド開始・終了を表すだけのログである。

今回AOPが

```
ReviewController.getReviewSuspend() 開始
ReviewController.getReviewSuspend() 終了
```

を出力するため、役割が重複する。

そのためController側の開始・終了ログは削除する。

コミット

```text
refactor: remove redundant controller logs for AOP
```

---

# Serviceの開始・完了ログをどう扱うか

Serviceでは、

開始・完了ログが必ずしも

「メソッド開始・終了」

だけを意味しているわけではない。

多くは、

> 業務イベント

を記録している。

例えば、

```java
log.info("問題削除開始 questionId={}", questionId);
```

は、

単なるメソッド開始ではなく、

> 問題削除という業務イベント

を意味している。

一方、

AOPが出力する

```
AdminService.deleteOneQuestion() 開始
```

は、

メソッド開始を示しているだけであり、

意味が異なる。

そのため、

Serviceでは業務イベントログだけを残す。

---

# そのまま残すログ

## AdminService.addQuestion()

```java
Question savedQuestion = questionRepository.save(question);

log.info(
    "問題登録完了 questionId={}",
    savedQuestion.getQuestionId());
```

このログから分かることは、

- 問題登録に成功したこと
- 登録された問題ID

である。

これはAOPでは取得できない情報であるため、そのまま残す。

---

## AdminService.updateOneQuestion()

```java
log.info("問題更新前 {}", question);

...

log.info("問題更新後 {}", question);
```

このログは

- 更新前
- 更新後

という業務データを記録している。

開始・終了ログではないため、そのまま残す。

---

# 形を変えて残すログ

## AdminService.deleteOneUser()

現在

```java
log.info("ユーザー削除開始 userId={}", userId);

userRepository.deleteByUserId(userId);

log.info("ユーザー削除完了 userId={}", userId);
```

開始・完了ではなく、

```java
userRepository.deleteByUserId(userId);

log.info("ユーザー削除 userId={}", userId);
```

とする。

これにより、

- AOP
    - メソッド開始・終了
- Service
    - ユーザー削除という業務イベント

という役割分担になる。

---

## AdminService.deleteOneQuestion()

考え方は同じである。

現在

```java
問題削除開始
...
問題削除完了
```

となっているものを、

```java
問題削除 questionId={}
```

という業務イベントログへ変更する。

---

## EvaluationService.updateEvaluation()

開始ログ

```java
log.info("評価更新開始 ...");
```

はAOPと重複するため削除する。

一方、

```java
log.info("評価更新完了(UPDATE)");
```

および

```java
log.info("評価更新完了(INSERT)");
```

は、

- INSERTだったのか
- UPDATEだったのか

という業務結果を表している。

これはAOPでは判別できないため残す。

---

## FavoritesService.toggleFavorite()

同様に、

```
お気に入り追加開始
お気に入り解除開始
```

は削除し、

```
お気に入り追加
お気に入り解除
```

という業務イベントログのみ残す。

---

## UserAccountService

対象

- updateUserId()
- updateUserPassword()
- cancelMembership()

いずれも、

```
○○開始
```

はAOPへ移行する。

一方、

```
ユーザーID変更
パスワード変更
退会
```

という業務イベントはServiceへ残す。

コミット

```text
refactor: remove redundant start/end logs from services
```

# 実装

## LogAspect修正

コミット

```text
refactor: extend AOP logging to controllers
```

Controllerも対象にするため、Pointcutを追加する。

```java
/** 対象：[Service]をクラス名に含んでいること */
@Pointcut("execution(* com.example.demo.service.*.*(..))")
public void serviceMethods() {}

/** 対象：[Controller]をクラス名に含んでいること */
@Pointcut("execution(* com.example.demo.controller.*.*(..))")
public void controllerMethods() {}
```

そして、

```java
@Before("serviceMethods() || controllerMethods()")
public void startLog(JoinPoint jp) {
    ...
}
```

```java
@After("serviceMethods() || controllerMethods()")
public void endLog(JoinPoint jp) {
    ...
}
```

とすることで、

- Controller
- Service

の両方で開始・終了ログを出力できるようになった。

---

# 実行時間計測（PerformanceAspect）

実行時間計測もチャプター16で既に実装していた。

しかし、

```java
public class PerformanceAspect {

    @Pointcut("execution(* com.example.demo.service.*.*(..))")
    public void serviceMethods(){}

    @Around("serviceMethods()")
    public Object measure(ProceedingJoinPoint jp) throws Throwable {

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

となっており、

- Serviceのみが対象
- ログレベルがINFO

という状態だった。

---

## 修正方針

### ① 対象をControllerまで広げる

理由は2つある。

#### 1. 既に実装済みだから

新しく実装する必要はなく、

Pointcutを追加するだけで済む。

---

#### 2. ログの粒度が揃う

LogAspectが

- Controller
- Service

の両方を対象にしたのであれば、

PerformanceAspectも同じ対象にする方が設計として統一感がある。

例えば、

```
StudyController.getQuestion() 開始
StudyController.getQuestion() : 12ms
StudyController.getQuestion() 終了

StudyService.getQuestion() 開始
StudyService.getQuestion() : 10ms
StudyService.getQuestion() 終了
```

のように、

開始・実行時間・終了

という流れがController・Serviceで揃う。

---

### ② INFOからDEBUGへ変更する

実行時間ログは、

普段の運用では頻繁に確認する情報ではない。

そのため、

```java
log.debug(
    "{} : {}ms",
    jp.getSignature().getName(),
    end - start
);
```

とする。

これにより、

- 通常運用ではログのノイズにならない
- パフォーマンス調査時だけDEBUGレベルで確認できる

という運用が可能になる。

---

# PerformanceAspect修正

コミット

```text
refactor: update PerformanceAspect logging
```

```java
@Aspect
@Component
@Slf4j
public class PerformanceAspect {

    /** Service */
    @Pointcut("execution(* com.example.demo.service.*.*(..))")
    public void serviceMethods(){}

    /** Controller */
    @Pointcut("execution(* com.example.demo.controller.*.*(..))")
    public void controllerMethods(){}

    @Around("serviceMethods() || controllerMethods()")
    public Object measure(ProceedingJoinPoint jp) throws Throwable {

        long start = System.currentTimeMillis();

        Object result = jp.proceed();

        long end = System.currentTimeMillis();

        log.debug(
            "{} : {}ms",
            jp.getSignature().getName(),
            end - start
        );

        return result;
    }
}
```

---

# 完成したAOP構成

今回の見直しにより、AOPは次の構成となった。

| Aspect | 内容 |
|---------|------|
| `LogAspect` | Controller・Serviceの開始・終了ログ |
| `PerformanceAspect` | Controller・Serviceの実行時間計測（DEBUG） |
| `ErrorLogAspect` | （既存）例外ログ |

また、

- Controllerの開始・終了ログを削除
- Serviceは業務イベントログのみ保持

という役割分担に整理したことで、

- **AOP**：技術的な共通ログ
- **Service**：業務イベントログ

という責務を明確に分離できた。

---

# 所感

今回の見直しでは、新しくAOPを実装するというよりも、既存の実装を現在のアプリケーション構成に合わせて整理・統一する作業が中心となった。

特に印象的だったのは、「開始・終了ログ」と「業務イベントログ」は一見似ているようで役割が異なるという点である。

当初はServiceの開始・終了ログもすべてAOPへ置き換えることを考えていたが、検討を進める中で、「問題登録」「問題削除」「評価更新」「お気に入り追加」といったログは、単なるメソッド開始・終了ではなく、業務上何が起きたのかを記録するログであることを改めて整理できた。

その結果、

- AOPは共通処理を担当する
- Serviceは業務イベントを担当する

という役割分担を明確にできたことは、大きな収穫だった。

また、チャプター16で実装していたLogAspectやPerformanceAspectを見直したことで、過去に実装した機能を単に使うだけでなく、現在の設計方針に合わせて改善していくことの重要性も学ぶことができた。

---

# 次やること

## 4. セキュリティ

次回はセキュリティについて最終確認を行う。

確認項目は以下のとおり。

- URL権限の最終確認
- CSRF設定の確認
- 管理画面へ一般ユーザーがアクセスできないことの確認
- 二重送信・不正アクセスの確認
- パスワード入力欄やログアウト処理などの最終確認