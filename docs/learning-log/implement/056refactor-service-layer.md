# リファクタリング（仕上げ） Service編

当初の予定よりもアプリケーションのボリュームが大きくなったため、内部設計の見直しが必要になった。

今回は **Serviceクラス全体** を対象にリファクタリングを行う。:contentReference[oaicite:0]{index=0}

主な目的は以下のとおり。

- 共通処理の整理
- Serviceの責務の明確化
- クラス名・依存関係の整理
- 重複コードの削除

---

# 1. 不要なインタフェースの削除とクラス名変更

## 背景

`StudyService`インタフェースは空であり、実装クラスも1つしか存在しない。

また、

- `StudyServiceImpl`
- `UserServiceImpl`

という命名になっていたが、

- `StudyService`はインタフェースが存在する
- `UserService`はインタフェースが存在しない

という状態で統一性がなく、可読性も低かった。:contentReference[oaicite:1]{index=1}

---

## 修正内容

### StudyService

```java
public class StudyServiceImpl implements StudyService
```

↓

```java
public class StudyService
```

- `StudyService`インタフェースを削除
- `implements StudyService`を削除
- クラス名を`StudyService`へ変更

**commit**

```text
refactor: remove StudyService interface and rename StudyServiceImpl
```

---

### UserService

```java
public class UserServiceImpl
```

↓

```java
public class UserService
```

インタフェースが存在しないため、`Impl`を外して命名を統一した。

---

## リファクタリング前の準備

Service同士が多くDIされていたため、影響範囲を把握する目的で

- 各Serviceのメソッド
- 他Serviceへの依存

を一覧化した。

これはリファクタリング前に依存関係を可視化するための実務でもよく行われる作業である。![](../../images/056.png)

---

## 共通処理の洗い出し

| メソッド | 共通化 | 新しい配置先 |
|----------|:------:|--------------|
| `createPagination()` | ○ | `PaginationService` または `PaginationUtil` |
| `getAllConditions()` | △ | `QuestionService` |
| `convertDifficulty()` | ○ | `SearchConditionConverter` |
| `convertEvaluation()` | ○ | `SearchConditionConverter` |
| `convertFavoriteCondition()` | ○ | `SearchConditionConverter` |
| `convertStudyCondition()` | ○ | `SearchConditionConverter` |
| `getUserOne()` | × | `UserService`のまま |

---

## 共通化方針

### ① createPagination()

QuestionやReviewとは関係のない完全に独立した処理であるため、

- `PaginationService`
- `PaginationUtil`

へ移行する。

---

### ② getAllConditions()

内部では

```java
questionRepository.findDistinctConditions()
```

を呼び出すだけであり、

Questionに属する処理なので

```text
QuestionService
```

へ移行する。

---

### ③ convert系メソッド

以下4つを

- convertDifficulty()
- convertEvaluation()
- convertFavoriteCondition()
- convertStudyCondition()

すべて

```text
SearchConditionConverter
```

へ集約する。

理由はすべて

> Enum → String

への変換という共通責務だからである。

例えば、

```java
public class SearchConditionConverter {

    public List<String> convertDifficulty(...)

    public List<String> convertEvaluation(...)

    public String convertFavoriteCondition(...)

    public String convertStudyCondition(...)
}
```

という構成になる。:contentReference[oaicite:3]{index=3}

---

### ④ getUserOne()

これはUser取得というUserService本来の責務なので、

共通クラスへ移さず

```text
UserService
```

に残す。

以下から呼ばれることも自然である。

- FavoritesService
- EvaluationService
- UserDetailsService

:contentReference[oaicite:4]{index=4}

---

## 変更に伴う修正

### UserServiceへ変更

対象クラス

- UserMenuController
- EvaluationService
- FavoritesService
- UserDetailsServiceImpl
- AdminController
- FavoritesController
- ReviewController
- SignupController

変更内容

```java
private final UserServiceImpl userServiceImpl;
```

↓

```java
private final UserService userService;
```

---

### StudyServiceへ変更

対象クラス

- SignupController

変更内容

```java
private final StudyServiceImpl studyServiceImpl;
```

↓

```java
private final StudyService studyService;
```

# 2. Serviceクラスの一部メソッドを独立させる

## 目的

Serviceクラスの中に存在する汎用的な処理を、それぞれ責務に応じたクラスへ移動する。

---

## AdminService.createPagination()を移行

### PaginationServiceを作成しcreatePagination()を移行

`AdminService`に存在していた

```java
createPagination()
```

はQuestionやReviewとは無関係であり、ページネーション生成という独立した責務を持つ。

そのため、

```text
PaginationService
```

へそのまま移行する。

**commit**

```text
refactor: extract pagination logic into PaginationService
```

---

### AdminServiceからcreatePagination()を削除

不要になった

```java
AdminService.createPagination()
```

を削除する。

**commit**

```text
refactor: remove createPagination from AdminService
```

---

### 参照先を修正

#### AdminController

```java
private final AdminService adminService;
```

↓

```java
private final PaginationService paginationService;
```

---

#### UserMenuController

```java
private final AdminService adminService;
```

↓

```java
private final PaginationService paginationService;
```

**commit**

```text
refactor: update pagination service references
```

---

## AdminService.getAllConditions()を移行

### QuestionServiceを作成

`AdminService`に存在していた

```java
getAllConditions()
```

は内部で

```java
questionRepository.findDistinctConditions()
```

を呼び出しているだけであり、

Questionに属する責務である。

そのため、

```text
QuestionService
```

を新規作成し移行する。

**commit**

```text
refactor: extract question condition logic into QuestionService
```

---

### AdminServiceから削除

不要になった

```java
getAllConditions()
```

を削除する。

**commit**

```text
refactor: remove getAllConditions from AdminService
```

---

### 呼び出し側を修正

#### AdminService

```java
if (conditions == null || conditions.isEmpty()) {
    conditions = getAllConditions();
}
```

↓

```java
private final QuestionService questionService;

if (conditions == null || conditions.isEmpty()) {
    conditions = questionService.getAllConditions();
}
```

---

#### UserService

```java
if (conditions == null || conditions.isEmpty()) {
    conditions = getAllConditions();
}
```

↓

```java
private final QuestionService questionService;

if (conditions == null || conditions.isEmpty()) {
    conditions = questionService.getAllConditions();
}
```

---

#### AdminController

```java
model.addAttribute(
    "conditions",
    adminService.getAllConditions());
```

↓

```java
private final QuestionService questionService;

model.addAttribute(
    "conditions",
    questionService.getAllConditions());
```

---

#### UserMenuController

```java
model.addAttribute(
    "conditions",
    adminService.getAllConditions());
```

↓

```java
private final QuestionService questionService;

model.addAttribute(
    "conditions",
    questionService.getAllConditions());
```

**commit**

```text
refactor: update question service references
```

---

## convert系メソッドを移行

### SearchConditionConverterを作成

`ReviewService`に存在していた

- convertDifficulty()
- convertEvaluation()
- convertFavoriteCondition()

および

`UserService`の

- convertStudyCondition()

を

```text
SearchConditionConverter
```

へ移行する。

**commit**

```text
refactor: move search condition conversion logic to SearchConditionConverter
```

---

### ReviewService・UserServiceから削除

不要になったConvert系メソッドを削除する。

**commit**

```text
refactor: remove converter methods from ReviewService and UserService
```

---

### 呼び出し側を修正

#### AdminService

```java
reviewService.convertDifficulty(difficulties);
```

↓

```java
private final SearchConditionConverter searchConditionConverter;

searchConditionConverter.convertDifficulty(difficulties);
```

---

#### ReviewService

```java
convertEvaluation(evaluations),
convertDifficulty(difficulties),
convertFavoriteCondition(favoriteCondition)
```

↓

```java
private final SearchConditionConverter searchConditionConverter;

searchConditionConverter.convertEvaluation(evaluations),
searchConditionConverter.convertDifficulty(difficulties),
searchConditionConverter.convertFavoriteCondition(favoriteCondition)
```

---

#### StudyService

```java
reviewService.convertDifficulty(difficulty)
```

↓

```java
private final SearchConditionConverter searchConditionConverter;

searchConditionConverter.convertDifficulty(difficulty)
```

---

#### UserService

```java
List<String> convertedDifficulties =
        reviewService.convertDifficulty(difficulties);

List<String> convertedEvaluations =
        reviewService.convertEvaluation(evaluations);

String convertedStudyCondition =
        convertStudyCondition(studyCondition);

String convertedFavoriteCondition =
        reviewService.convertFavoriteCondition(favoriteCondition);
```

↓

```java
private final SearchConditionConverter searchConditionConverter;

List<String> convertedDifficulties =
        searchConditionConverter.convertDifficulty(difficulties);

List<String> convertedEvaluations =
        searchConditionConverter.convertEvaluation(evaluations);

String convertedStudyCondition =
        searchConditionConverter.convertStudyCondition(studyCondition);

String convertedFavoriteCondition =
        searchConditionConverter.convertFavoriteCondition(favoriteCondition);
```

**commit**

```text
refactor: update search condition converter references
```

# 3. Serviceクラス内で重複している処理をまとめる

## 目的

Serviceクラス内に存在する重複コードを整理し、責務を明確にする。

---

## AdminService

### 問題点

`addQuestion()`と`updateOneQuestion()`で、

```java
question.setJapaneseText(form.getJapaneseText());
question.setEnglishText(form.getEnglishText());
question.setAlternativeAnswer(form.getAlternativeAnswer());
question.setDifficulty(form.getDifficulty());
question.setCondition(form.getCondition());
```

が重複していた。:contentReference[oaicite:0]{index=0}

---

### 修正

#### QuestionFormコピー用のprivateメソッドを作成

```java
private void copyQuestionForm(Question question, QuestionForm form) {

    question.setJapaneseText(form.getJapaneseText());
    question.setEnglishText(form.getEnglishText());
    question.setAlternativeAnswer(form.getAlternativeAnswer());
    question.setDifficulty(form.getDifficulty());
    question.setCondition(form.getCondition());

}
```

---

#### addQuestion()を修正

```java
public void addQuestion(QuestionForm form) {

    Question question = new Question();

    copyQuestionForm(question, form);

    Question savedQuestion = questionRepository.save(question);

    log.info("問題登録完了 questionId={}", savedQuestion.getQuestionId());

}
```

---

#### updateOneQuestion()を修正

```java
public void updateOneQuestion(long questionId, QuestionForm form) {

    Question question = questionRepository.findById(questionId)
            .orElseThrow(() ->
                    new IllegalArgumentException("Question not found."));

    // 更新前ログ
    log.info("Before: {}", question);

    copyQuestionForm(question, form);

    questionRepository.save(question);

    // 更新後ログ
    log.info("After : {}", question);

}
```

**commit**

```text
refactor(admin): extract QuestionForm mapping into private method
```

---

## EvaluationService

### 検討した内容

`EvaluationService`では、

複合キー生成処理が`FavoritesService`の

```java
createFavoritesKey()
```

と似た実装になっていた。:contentReference[oaicite:1]{index=1}

例えば、

```java
Users user = userService.getUserOne(loginUser);

StudyHistoryKey key = new StudyHistoryKey();
key.setUserId(user.getId());
key.setQuestionId(questionId);
```

を、

```java
FavoritesKey key = favoritesService.createFavoritesKey(loginUser, questionId);
```

のように共通化することも検討した。

---

### 結論

今回は共通化しない。

理由は、

- `FavoritesKey`
- `StudyHistoryKey`

は似ているようで**別の型**であるためである。

今回の規模では、

多少の重複よりも

**責務を分離したまま維持することを優先**した。

---

## FavoritesService

### 問題点

`toggleFavorite()`では、

```java
Users user = userService.getUserOne(loginUser);

FavoritesKey key = createFavoritesKey(loginUser, questionId);
```

としているにもかかわらず、

`createFavoritesKey()`の内部で再び

```java
Users user = userService.getUserOne(loginUser);
```

を実行していた。

つまり、

同じユーザー取得を二度行っていた。:contentReference[oaicite:2]{index=2}

---

### 修正

#### isFavorite()

変更前

```java
public boolean isFavorite(String loginUser, long questionId)
```

↓

```java
public boolean isFavorite(Users user, long questionId) {

    FavoritesKey key = createFavoritesKey(user, questionId);

    return favoritesRepository.existsById(key);

}
```

---

#### createFavoritesKey()

変更前

```java
private FavoritesKey createFavoritesKey(String loginUser, long questionId)
```

↓

```java
private FavoritesKey createFavoritesKey(Users user, long questionId) {

    FavoritesKey key = new FavoritesKey();

    key.setUserId(user.getId());
    key.setQuestionId(questionId);

    return key;

}
```

これにより、

`Users`取得は呼び出し元で一度だけ行えばよくなった。

---

### 呼び出し側を修正

対象

- StudyController
- ReviewController

変更前

```java
favoritesService.isFavorite(
        loginUser.getUsername(),
        question.getQuestionId());
```

↓

```java
favoritesService.isFavorite(
        userService.getUserOne(loginUser.getUsername()),
        question.getQuestionId());
```

**commit**

```text
refactor(favorites): pass Users object instead of username
```

---

## StudyService

### 不要メソッドの削除

以前使用していた

```java
getRandomQuestion()
```

は、

アプリケーションの仕様変更により現在は使用されていない。

そのため削除した。:contentReference[oaicite:3]{index=3}

**commit**

```text
refactor: remove unused getRandomQuestion method from StudyService
```

---

# 所感

今回のリファクタリングでは、

- Service間の依存関係を整理
- 共通処理の切り出し
- クラス名の統一
- 重複コードの削除

を行った。

アプリケーションの動作は変更せず、内部設計のみを改善したため、今後の機能追加や保守が容易な構成となった。

---

# 次やること

- Controllerクラスのリファクタリング