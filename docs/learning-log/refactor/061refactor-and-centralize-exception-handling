# 0061 例外処理

## 概要

以前例外処理を実装した際と比較して、

- 機能数が増えた
- Controller・Serviceの数が増えた
- 当時実装した例外処理は教科書どおりの最低限の実装であり、現在のアプリケーションには不十分

という状況になった。

そのため、アプリケーション全体の例外処理を見直し、設計を再整理することにした。

今回見直す対象は主に以下である。

- `@ControllerAdvice` による共通例外処理
- 404・500エラーページ
- `IllegalArgumentException`
- `DuplicateKeyException`
- Controllerごとに分散している例外処理

---

# AdminController

| クラス | メソッド | 現在の例外処理 | 必要な例外処理 | 共通化候補 | 理由 |
|---------|----------|----------------|----------------|------------|------|
| `AdminController` | `getAdminMenu()` | なし | - | - | 画面を表示するだけであり、例外が発生する処理はない。 |

**結論**

追加の例外処理は不要。

---

# AdminQuestionController

| クラス | メソッド | 現在の例外処理 | 必要な例外処理 | 共通化候補 | 理由 |
|--------|----------|---------------|----------------|------------|------|
| `AdminQuestionController` | `getQuestionAdd()` | なし | - | - | FormをModelへ設定して画面を表示するだけ。 |
| `AdminQuestionController` | `postQuestionAdd()` | `BindingResult`によるバリデーション | - | ×（Controllerに残す） | バリデーションエラーは入力画面へ戻す必要があるため。 |
| `AdminQuestionController` | `getAdminQuestionSearch()` | なし | - | - | 例外処理はService側へ委ねている。 |
| `AdminQuestionController` | `getAdminQuestionEdit()` | なし（Serviceからそのまま受ける） | `IllegalArgumentException` | ○ | Serviceが送出する例外はControllerAdviceで共通化できる。 |
| `AdminQuestionController` | `postAdminQuestionEdit()` | `BindingResult`によるバリデーション | - | ×（Controllerに残す） | 入力エラーは元画面へ戻す必要があるため。 |
| `AdminQuestionController` | `postAdminQuestionEdit()` | なし（Serviceからそのまま受ける） | `IllegalArgumentException` | ○ | Serviceが送出する例外はControllerAdviceで共通化できる。 |
| `AdminQuestionController` | `postAdminQuestionDelete()` | なし | - | - | 存在しないIDでも削除処理は空振りで終了する。 |

---

## BindingResultは例外ではない

`BindingResult` はユーザー入力の誤りを保持する仕組みであり、例外ではない。

例えば

- 日本語文必須
- 英語200文字以内
- 条件20文字以内

などのバリデーションに失敗すると、

```java
bindingResult.hasErrors()
```

が `true` になる。

これはシステム異常ではなく、

- 入力
- バリデーション
- 入力画面へ戻す

という正常な処理である。

そのため、

```java
@ControllerAdvice
```

で共通処理するのではなく、Controllerで処理する。

---

## getAdminQuestionEdit()

Serviceでは

```java
questionRepository.findById(questionId)
        .orElseThrow(() ->
                new IllegalArgumentException("Question not found."));
```

としている。

存在しない `questionId` を指定すると、

```
IllegalArgumentException
```

が送出される。

現在は

```
Controller
    ↓
Service
    ↓
IllegalArgumentException
    ↓
500エラー
```

となる。

これを

```
Controller
    ↓
Service
    ↓
IllegalArgumentException
    ↓
GlobalControllerAdvice
    ↓
エラーページ
```

という構成へ変更する。

---

## postAdminQuestionDelete()

### 検証①

存在しない `questionId = 999999`

#### 結果

- 例外なし
- 削除は行われない
- 「削除しました」のメッセージだけ表示される

つまり削除処理は空振りで終了する。

---

### 検証②

Service側で

```java
questionId = 999999L;
```

を代入して実行。

#### 結果

検証①と同じ。

---

### 検証③

Service側で

```java
questionId = null;
```

を代入。

#### 結果

```
InvalidDataAccessApiUsageException
```

が送出された。

---

### 検証結果

| Repositoryメソッド | 引数 | 結果 |
|--------------------|------|------|
| `deleteById()` | 存在しないID | 空振り（例外なし） |
| `deleteById()` | `null` | `InvalidDataAccessApiUsageException` |

通常運用では

```java
@RequestParam long questionId
```

で受け取るため `null` は渡らない。

そのため、新たな例外処理は不要と判断した。

---

## postAdminQuestionEdit()

このメソッドには

- BindingResultによる入力チェック
- Serviceが送出するIllegalArgumentException

の2種類が存在する。

入力エラーはControllerで処理し、

存在しない `questionId` による `IllegalArgumentException` はControllerAdviceへ共通化する。

---

# GlobalControllerAdvice

## IllegalArgumentExceptionの共通処理

対象メソッド

- `getAdminQuestionEdit()`
- `postAdminQuestionEdit()`

実装

```java
@ExceptionHandler(IllegalArgumentException.class)
public String handleIllegalArgumentException(
        IllegalArgumentException e,
        Model model) {

    model.addAttribute("errorMessage", e.getMessage());

    return "error/error";
}
```

### Commit

```text
feat: add global IllegalArgumentException handler
```

# AdminUserController

| クラス | メソッド | 現在の例外処理 | 必要な例外処理 | 共通化候補 | 理由 |
|---------|----------|----------------|----------------|------------|------|
| `AdminUserController` | `getAdminUserList()` | なし | - | - | ユーザー一覧を取得して表示するだけであり、例外となる処理はない。 |
| `AdminUserController` | `postAdminUserDelete()` | なし | - | - | 存在しないユーザーIDでも削除処理は空振りで終了するため。 |

---

## postAdminUserDelete()

### 検証①

存在しないユーザーIDを指定。

#### 結果

- 例外なし
- 削除されない
- 正常終了

---

### 検証②

Service側で

```java
userId = null;
```

とした。

#### 結果

```
InvalidDataAccessApiUsageException
```

が発生。

---

### 結論

通常運用ではControllerから`null`は渡らないため、追加の例外処理は不要。

---

# FavoritesController

| クラス | メソッド | 現在の例外処理 | 必要な例外処理 | 共通化候補 | 理由 |
|---------|----------|----------------|----------------|------------|------|
| `FavoritesController` | `toggleFavorite()` | なし | なし | - | 通常運用ではログインユーザー・問題IDとも保証されており、例外となるケースは確認できなかった。 |

---

## toggleFavorite()

### 呼び出しているService

```java
favoritesService.toggleFavorite(...)
```

内部では

- お気に入り存在確認
- 登録
- 削除

のみを行っている。

---

### 検証

#### user = null

通常運用では発生しない。

Spring Securityによりログイン済みユーザーが保証される。

---

#### questionId = 999999

お気に入り登録は行われない。

例外も発生しない。

---

#### questionId = null

通常運用ではControllerから渡らない。

---

### 結論

追加の例外処理は不要。

---

# LoginController

| クラス | メソッド | 現在の例外処理 | 必要な例外処理 | 共通化候補 | 理由 |
|---------|----------|----------------|----------------|------------|------|
| `LoginController` | `getLogin()` | なし | - | - | ログイン画面を表示するだけである。 |

追加の例外処理は不要。

---

# HomeController

| クラス | メソッド | 現在の例外処理 | 必要な例外処理 | 共通化候補 | 理由 |
|---------|----------|----------------|----------------|------------|------|
| `HomeController` | `getHome()` | なし | - | - | ホーム画面を表示するだけであり、例外となる処理は存在しない。 |

追加の例外処理は不要。

---

# ReviewController

ReviewControllerは今回最も検証項目が多かったControllerの一つである。

主に以下について検証した。

- 問題が0件の場合
- 範囲外ページアクセス
- セッション切れ
- 存在しないユーザー
- 存在しない問題

その結果、

- ControllerAdviceへ送るべき例外
- Controllerで事前判定すべきケース

を明確に整理できた。

次章ではReviewControllerについて詳細にまとめる。

# ReviewController

ReviewControllerでは、セッション管理・ページ遷移・問題取得など複数の処理を行っているため、例外処理の確認項目が最も多かった。

## ReviewController一覧

| クラス | メソッド | 現在の例外処理 | 必要な例外処理 | 共通化候補 | 理由 |
|---------|----------|----------------|----------------|------------|------|
| `ReviewController` | `getReviewMenu()` | なし | なし | - | Service側で通常運用の範囲では例外となる要因がない。 |
| `ReviewController` | `getReviewStart()` | なし | 空リスト対策 | ×（Controllerで対応） | 問題が0件の場合、そのまま画面遷移すると`IndexOutOfBoundsException`となるため。 |
| `ReviewController` | `getReviewQuestion()` | `questions == null` の場合は `/review/menu` へリダイレクト | 範囲外ページ対策 | ×（Controllerで対応） | `page = -1` や `999999` で `IndexOutOfBoundsException` が発生するため。 |
| `ReviewController` | `getReviewResume()` | `reviewQuestions == null` の場合は `/review/menu` へリダイレクト | なし | - | セッション切れ対策済み。 |
| `ReviewController` | `getReviewComplete()` | なし | なし | - | セッション削除のみ。 |
| `ReviewController` | `getReviewSuspend()` | なし | なし | - | セッション保存のみ。 |
| `ReviewController` | `getReviewQuit()` | なし | なし | - | セッション削除のみ。 |
| `ReviewController` | `postEvaluation()` | なし | なし | - | EvaluationService側の検証が完了しており、通常運用では追加不要。 |

---

# ReviewController.getReviewStart()

## 検証

### ReviewService.getQuestion()

```java
questions.clear();
```

として意図的に空リストを返す。

### 結果

```
500 Internal Server Error

Index 0 out of bounds for length 0
```

原因は

```
/review/question?page=0
```

へ遷移後、

```java
questions.get(0)
```

が実行されるため。

---

## 修正

```java
if (questions.isEmpty()) {
    return "redirect:/review/menu";
}
```

---

### 修正後

- review/menuへ戻る
- 500エラーなし

---

### Commit

```text
fix: prevent starting review with empty question list
```

---

# ReviewController.getReviewQuestion()

既に

```java
if (questions == null) {
    return "redirect:/review/menu";
}
```

は実装済み。

追加で検証したのは

```
page=-1
page=999999
```

---

## 結果

どちらも

```
IndexOutOfBoundsException
```

となった。

---

## 修正

```java
if (page < 0 || page >= questions.size()) {
    return "redirect:/review/menu";
}
```

---

### 修正後

範囲外ページでも

```
review/menu
```

へ戻るようになった。

---

### Commit

```text
fix: validate page parameter in review question
```

---

# ReviewController.getReviewResume()

```java
if (session.getAttribute("reviewQuestions") == null) {
    return "redirect:/review/menu";
}
```

が既に実装されている。

通常運用では

```
reviewQuestions
reviewCurrentPage
```

は同時にセッションへ保存されるため、

追加の例外処理は不要と判断した。

---

# ReviewController.postEvaluation()

呼び出しているServiceは

```java
evaluationService.updateEvaluation()
```

のみである。

このServiceについては、

- user = null
- questionId = null
- questionId = 999999

などの異常系を既に検証済みであり、

ReviewController側で追加すべき例外処理は存在しなかった。

---

# ReviewControllerまとめ

今回追加したのは以下の2点のみである。

- 問題が0件なら開始しない
- 範囲外ページへのアクセスを禁止する

それ以外のメソッドについては、既存実装またはService側の検証で十分と判断した。

# SignupController

SignupControllerでは、ユーザー登録時の入力チェックおよび重複ユーザーIDの例外処理について確認した。

## SignupController一覧

| クラス | メソッド | 現在の例外処理 | 必要な例外処理 | 共通化候補 | 理由 |
|---------|----------|----------------|----------------|------------|------|
| `SignupController` | `getSignup()` | なし | - | - | 画面表示のみであり、例外が発生する処理はない。 |
| `SignupController` | `getSignupComplete()` | なし | - | - | 完了画面を表示するだけである。 |
| `SignupController` | `postSignup()` | `BindingResult`、`DuplicateKeyException` | なし | ×（Controllerに残す） | バリデーションエラーおよび重複ユーザーIDは入力エラーであり、入力画面へ戻す必要があるため。 |

---

## postSignup()

Serviceでは

```java
if (isExists) {
    throw new DuplicateKeyException("既に存在するユーザーです");
}
```

としている。

Controllerでは

```java
catch (DuplicateKeyException e)
```

で受け取り、

```java
bindingResult.rejectValue(...)
```

によって入力画面へ戻している。

これは

- システム異常ではない
- ユーザー入力の誤り

であるため、

```
ControllerAdvice
```

へ共通化するものではない。

---

## 結論

SignupControllerについては追加修正は不要。

---

# StudyController

ReviewControllerと並び、最も検証項目が多かったControllerである。

## StudyController一覧

| クラス | メソッド | 現在の例外処理 | 必要な例外処理 | 共通化候補 | 理由 |
|---------|----------|----------------|----------------|------------|------|
| `StudyController` | `getStudyMenu()` | なし | なし | - | Service・セッション取得とも通常運用では例外となる要因がない。 |
| `StudyController` | `getStudyStart()` | 出題範囲未選択時はエラーメッセージを表示 | `IndexOutOfBoundsException` | ×（Controllerで対応） | 問題が0件の場合、そのまま開始すると `questions.get(0)` が実行されるため。 |
| `StudyController` | `getStudyNewStart()` | なし | `IndexOutOfBoundsException` | ×（Controllerで対応） | 未学習問題が0件の場合、空リストで画面遷移してしまうため。 |
| `StudyController` | `getStudyQuestion()` | `questions == null` の場合は `/study/menu` へリダイレクト | `IndexOutOfBoundsException` | ×（Controllerで対応） | `page=-1` や `999999` により `questions.get(page)` が失敗するため。 |
| `StudyController` | `getStudyResume()` | `studyQuestions == null` の場合は `/study/menu` へリダイレクト | なし | - | 中断データが存在しない場合は既に対応済み。 |
| `StudyController` | `getStudyComplete()` | なし | なし | - | セッション削除のみ。 |
| `StudyController` | `getStudySuspend()` | なし | なし | - | セッション保存のみ。 |
| `StudyController` | `getStudyQuit()` | なし | なし | - | セッション削除のみ。 |
| `StudyController` | `postEvaluation()` | なし | なし | - | EvaluationService側の検証済み。 |

---

# StudyController.getStudyStart()

## 検証

StudyServiceで

```java
start = 999999;
```

として存在しない範囲を取得する。

取得件数は

```java
取得件数=0
```

となった。

そのまま

```
/study/question?page=0
```

へ遷移すると

```
500 Internal Server Error

Index 0 out of bounds for length 0
```

となった。

---

## 修正

```java
if (questions.isEmpty()) {
    return "redirect:/study/menu";
}
```

---

## 修正後

- study/menuへ戻る
- 500エラーなし

必要に応じてエラーメッセージを追加する余地はある。

---

### Commit

```text
fix: prevent starting study with empty question list
```

---

# StudyController.getStudyNewStart()

## 検証

StudyServiceで

```java
extractedNewQuestions.clear();
```

を追加し、

意図的に空リストを返した。

---

## 結果

```
500 Internal Server Error

Index 0 out of bounds for length 0
```

---

## 修正

```java
if (questions.isEmpty()) {
    return "redirect:/study/menu";
}
```

---

## 修正後

- study/menuへ戻る
- 500エラーなし
- コンソールエラーも発生しなくなった

こちらも必要であればエラーメッセージを表示する改善が考えられる。

---

### Commit

```text
fix: prevent starting new study with empty question list
```

---

# StudyController.getStudyQuestion()

既に

```java
if (questions == null) {
    return "redirect:/study/menu";
}
```

は実装済みである。

追加で検証したのは

- `page = -1`
- `page = 999999`

の2ケースである。

---

## 結果

どちらも

```
IndexOutOfBoundsException
```

が発生した。

---

## 修正

```java
if (page < 0 || page >= questions.size()) {
    return "redirect:/study/menu";
}
```

---

## 修正後

範囲外ページへのアクセス時も

```
study/menu
```

へ戻るようになった。

---

### Commit

```text
fix: validate page parameter in study question
```

---

# StudyControllerまとめ

今回追加した例外回避処理は以下の3点である。

- 問題が0件なら開始しない
- 未学習問題が0件なら開始しない
- 範囲外ページへのアクセスを禁止する

その他のメソッドについては既存実装またはService側の検証で十分と判断した。

# UserProfileController

UserProfileControllerでは、

- ユーザー情報表示
- ユーザーID変更
- パスワード変更
- 退会

について例外処理を確認した。

## UserProfileController一覧

| クラス | メソッド | 現在の例外処理 | 必要な例外処理 | 共通化候補 | 理由 |
|---------|----------|----------------|----------------|------------|------|
| `UserProfileController` | `getUserProfile()` | なし | `IllegalArgumentException` | ○ | `getUserOne()` が `null` を返す可能性があるため。Serviceが送出する例外はControllerAdviceで共通化する。 |
| `UserProfileController` | `getEditUserId()` | なし | `IllegalArgumentException` | ○ | `getUserOne()` が `null` を返す可能性があるため。Serviceが送出する例外はControllerAdviceで共通化する。 |
| `UserProfileController` | `postEditUserId()` | `BindingResult`、`DuplicateKeyException` | `IllegalArgumentException` | ○ | 入力エラーはControllerで処理し、存在しないユーザーはControllerAdviceで共通化する。 |
| `UserProfileController` | `cancelMembership()` | なし | なし | - | 通常運用ではログインユーザーのみが実行するため。 |
| `UserProfileController` | `getCanceled()` | なし | なし | - | 完了画面を表示するだけである。 |
| `UserProfileController` | `getEditPassword()` | なし | なし | - | 画面表示のみ。 |
| `UserProfileController` | `postEditPassword()` | `BindingResult`、`IllegalArgumentException` | `CurrentPasswordMismatchException`、`IllegalArgumentException` | ○（一部） | 「現在のパスワードが正しくありません」は入力エラーとしてControllerで処理し、「ユーザーが存在しません」はControllerAdviceで共通化する。 |

---

# postEditUserId()

Serviceでは

```java
if (isExists) {
    throw new DuplicateKeyException(...);
}

Users user = getUserOne(currentUserId);

if (user == null) {
    throw new IllegalArgumentException("ユーザーが存在しません");
}
```

となっている。

Controllerでは

```java
DuplicateKeyException
```

のみを捕捉している。

つまり、

```
IllegalArgumentException
```

については既にControllerでは処理しておらず、そのまま上位へ送られている。

したがって、

```
GlobalControllerAdvice
```

へ共通化するだけでよく、

Controller側の修正は不要である。

---

# postEditPassword()

当初は

```java
throw new IllegalArgumentException(...)
```

を2か所で使用していた。

```java
if (user == null)
```

↓

```
ユーザーが存在しません
```

```java
if (!passwordEncoder.matches(...))
```

↓

```
現在のパスワードが正しくありません
```

しかし、この2つは性質が異なる。

---

## ユーザーが存在しません

これはシステム異常である。

```
Controller
        ↓
ControllerAdvice
```

で共通処理する。

---

## 現在のパスワードが正しくありません

こちらは入力ミスであり、

```
BindingResult
```

へエラーメッセージを追加して入力画面へ戻す必要がある。

そこで、

専用例外

```java
CurrentPasswordMismatchException
```

を作成することにした。

---

## 修正

### 例外クラス作成

```java
public class CurrentPasswordMismatchException
        extends RuntimeException {

    public CurrentPasswordMismatchException(String message) {
        super(message);
    }
}
```

---

### UserAccountService

```java
if (user == null) {
    throw new IllegalArgumentException("ユーザーが存在しません");
}

if (!passwordEncoder.matches(
        currentPassword,
        user.getPassword())) {

    throw new CurrentPasswordMismatchException(
            "現在のパスワードが正しくありません");
}
```

---

### UserProfileController

```java
catch (CurrentPasswordMismatchException e) {

    bindingResult.rejectValue(
            "currentPassword",
            "invalid",
            e.getMessage());

    return getEditPassword(model, form);
}
```

---

### Commit

```text
refactor: separate password mismatch exception
```

---

# UserQuestionController

UserQuestionControllerでは、

検索条件の変換やページネーションを行っている。

## UserQuestionController一覧

| クラス | メソッド | 現在の例外処理 | 必要な例外処理 | 共通化候補 | 理由 |
|---------|----------|----------------|----------------|------------|------|
| `UserQuestionController` | `getUserQuestionSearch()` | なし | `IllegalArgumentException` | ○ | `getUserOne()` が `null` を返した場合はServiceから例外を送出し、ControllerAdviceで共通化する。 |

---

# getUserQuestionSearch()

Controllerから呼び出しているのは

- `UserAccountService`
- `UserQuestionService`
- `PaginationService`
- `QuestionService`

である。

---

## UserQuestionService

Service側では

- 難易度
- 理解度
- 学習条件
- お気に入り条件
- 条件
- キーワード

について、

すべて

```
null
```

を許容する実装となっている。

そのため、

追加の例外処理は不要である。

---

## PaginationService

`Page<?>`

からページネーション情報を生成するのみであり、

例外となるケースは確認できなかった。

---

## QuestionService

条件一覧を取得するだけであり、

例外処理は不要。

---

## 結論

UserQuestionControllerでは、

唯一考慮すべき例外は

```
getUserOne()
```

による

```
IllegalArgumentException
```

のみである。

これは他Controllerと同様に

```
GlobalControllerAdvice
```

へ共通化する。

# Controllerまとめ

今回、ControllerおよびServiceの例外処理を見直した結果、例外処理は大きく以下の3パターンに分類できた。

| パターン | 対応方法 |
|----------|----------|
| システム異常 | `GlobalControllerAdvice`で共通処理 |
| ユーザー入力ミス | Controllerで`BindingResult`などを用いて処理 |
| 事前に防げる異常 | Controllerで事前判定し、例外自体を発生させない |

---

## 今回共通化したケース

以下のメソッドでは、Serviceが送出する`IllegalArgumentException`をControllerで処理するのではなく、`GlobalControllerAdvice`で一括処理する方針とした。

| Controller | メソッド | 共通化する例外 |
|------------|----------|----------------|
| `AdminQuestionController` | `getAdminQuestionEdit()` | `IllegalArgumentException` |
| `AdminQuestionController` | `postAdminQuestionEdit()` | `IllegalArgumentException` |
| `UserProfileController` | `postEditUserId()` | `IllegalArgumentException` |
| `UserProfileController` | `postEditPassword()` | 「ユーザーが存在しません」(`IllegalArgumentException`) |

---

## Controllerで例外を回避したケース

例外を発生させてから処理するのではなく、Controllerで事前に判定する方が適切と判断したケースである。

### 範囲外ページへのアクセスを禁止

| Controller | メソッド | 対応 |
|------------|----------|------|
| `ReviewController` | `getReviewQuestion()` | `page < 0` または `page >= questions.size()` を判定 |
| `StudyController` | `getStudyQuestion()` | `page < 0` または `page >= questions.size()` を判定 |

---

### 問題が1件もない場合は開始しない

| Controller | メソッド | 対応 |
|------------|----------|------|
| `ReviewController` | `getReviewStart()` | `questions.isEmpty()` を判定 |
| `StudyController` | `getStudyStart()` | `questions.isEmpty()` を判定 |
| `StudyController` | `getStudyNewStart()` | `questions.isEmpty()` を判定 |

---

## 専用例外クラスを作成したケース

`UserProfileController.postEditPassword()`では、これまで`IllegalArgumentException`で扱っていた2種類の異常を分離した。

| 内容 | 対応方法 |
|------|----------|
| ユーザーが存在しません | `IllegalArgumentException` → `GlobalControllerAdvice` |
| 現在のパスワードが正しくありません | `CurrentPasswordMismatchException` → Controllerで入力エラーとして処理 |

そのため、

```java
CurrentPasswordMismatchException
```

を新規作成し、

入力ミスとシステム異常を明確に区別した。

---

# まとめ

今回の見直しにより、Controllerごとにばらついていた例外処理を整理し、例外の種類ごとに役割を統一できた。

最終的な方針は以下のとおりである。

| 例外の種類 | 処理方法 |
|------------|----------|
| システム異常 | `GlobalControllerAdvice` |
| 入力ミス | Controller（BindingResult・専用例外） |
| 空リスト・範囲外など事前に判定できる異常 | Controllerで事前判定し、例外を発生させない |

これにより、例外処理の責務が明確になり、今後ControllerやServiceを追加する際にも、一貫した基準で実装できるようになった。

---

# 所感

今回の例外処理の見直しでは、Controllerの全メソッドを一覧化し、

- 現在どのような例外処理を行っているのか
- 本当に例外処理が必要なのか
- Controllerに残すべきか、それともGlobalControllerAdviceへ共通化すべきか

を一つずつ確認した。

その結果、例外処理を以下の3つに整理できた。

- **業務上あり得ない異常**（存在しないデータなど）
  - `GlobalControllerAdvice`へ共通化
- **事前に判定できる異常**（空リスト・範囲外ページなど）
  - Controllerで事前チェックし、例外自体を発生させない
- **ユーザー入力ミス**
  - `BindingResult`や専用例外でController側が処理

これにより、Controllerごとにばらついていた例外処理の役割を明確に整理できた。

---

一方で、作業を進める中で最も苦労したのは、**どこから調査を始めるべきか**という点である。

今回はControllerの全メソッドを表にまとめて可視化したが、実際に例外を発生させるのはService以下の処理である。

そのため、

- Controllerから呼び出されるServiceを一つずつ追い掛ける
- Serviceの処理を確認してControllerへ戻る

という作業を何度も繰り返すことになった。

逆にServiceだけを起点に調査すると、

- このServiceを呼び出しているControllerはどこか
- Controllerではどのような画面遷移やエラーメッセージにすべきか

まで考慮しなければならず、今度はController側の修正箇所を探す手間が増える。

結局、

- Controllerから見る方法
- Serviceから見る方法

のどちらが効率的なのかは最後まで判断できなかった。

---

また、今回は比較的小規模なアプリケーションにもかかわらず、例外処理の状況を整理・可視化するだけでも想像以上に時間を要した。

その原因としては、次の3点が挙げられる。

- **設計意図を記録していなかったこと**
  - 「なぜこのControllerだけ例外処理を書いているのか」
  - 「なぜA Controller・B Serviceではこの設計なのに、C Controller・D Serviceでは異なるのか」
  - といった設計判断を記録していなかったため、その都度コードを読み直して理由を調査する必要があった。
  - 仮に記録していたとしても、必要な箇所を探し出す手間は発生したと思われる。

- **実際に例外処理が必要なのかを検証するため、ケーススタディを繰り返したこと**
  - `null`
  - 存在しないID
  - 範囲外のページ番号
  - 空リスト
  - 未学習問題が0件
  - 存在しないユーザー
  - などのケースを意図的に作り、デバッグモードや検証コードを用いて何度も例外を発生させ、その挙動を確認した。

- **例外処理以外の修正も増えたこと**
  - 検証の結果、「例外は発生しないがユーザー体験として好ましくない」ケースも複数見つかった。
  - 例えば、空の問題セットのまま学習画面へ遷移してしまうケースなどである。
  - このようなケースでは例外処理を追加するのではなく、事前判定によって画面遷移を制御するコードを追加する必要があり、結果として例外処理の見直し以上に修正範囲が広がった。

---

今回の作業を通して感じたのは、**例外処理は実装時よりも後から整理・統一する方がはるかに大変**だということである。

画面やServiceが増えるにつれて例外処理も分散しやすくなるため、開発初期の段階で

- Controllerで扱う例外
- `GlobalControllerAdvice`へ集約する例外
- 入力エラーとして扱う例外

といったルールを明文化しておけば、今回ほど大規模な調査や検証は不要だった可能性が高い。

一方で、実際に一つひとつのケースを検証したことで、「本当に例外処理が必要な箇所」と「例外を発生させない設計にすべき箇所」を明確に区別できた点は大きな収穫だった。

また、今回整理した方針は、今後新たなControllerやServiceを実装する際の判断基準として利用できるため、アプリケーション全体の保守性や設計の一貫性向上にもつながると感じた。

---

# 次にやること

今回の見直しにより、ControllerおよびServiceの例外処理については一通り整理できた。

次回は、ログ出力について見直す予定である。

現在はログレベルや出力内容が統一されておらず、デバッグ用の出力も一部残っているため、

- ログレベル（INFO・WARN・ERROR）の整理
- `System.out.println()` の削除
- ログメッセージの統一
- 開発用ログと本番用ログの整理

などを行い、保守しやすいログ設計へ改善していく。