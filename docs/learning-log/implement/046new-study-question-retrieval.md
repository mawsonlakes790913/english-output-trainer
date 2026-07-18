# 「未学習問題」のトレーニングを追加②
# 未学習問題セット取得の実装

前回（045）では、未学習問題数を取得できるようになった。

今回は実際に**未学習問題の一覧（問題セット）を取得し、学習できる機能**を実装する。

---

## QuestionRepositoryに未学習問題取得用クエリを追加

（git commit: `feat: add query to fetch new study questions`）

未学習問題のみ取得するクエリを追加した。

```java
@Query(value = """
        SELECT q.*
        FROM question q
        LEFT JOIN study_history sh
          ON q.question_id = sh.question_id
         AND sh.user_id = :userId
        WHERE q.difficulty IN (:difficulties)
          AND sh.question_id IS NULL
        """, nativeQuery = true)
List<Question> getNewQuestions(
        @Param("userId") Long userId,
        @Param("difficulties") List<String> difficulties
);
```

### 処理内容

`study_history`をLEFT JOINし、

```sql
sh.question_id IS NULL
```

となる問題のみ取得する。

つまり、

- 一度も学習したことがない問題
- 指定した難易度に一致する問題

だけを取得できる。

---

## StudyServiceImplに未学習問題取得メソッドを追加

（git commit: `feat: add service for retrieving new study questions`）

Repositoryから取得した未学習問題を返すサービスメソッドを追加した。

```java
public List<Question> getNewQuestions(
        long userId,
        List<Difficulty> difficulty) {

    List<Question> extractedNewQuestions =
            questionRepository.getNewQuestions(
                    userId,
                    reviewService.convertDifficulty(difficulty));

    return extractedNewQuestions;
}
```

---

## StudyControllerに未学習問題開始用メソッドを追加

（git commit: `feat: add new question study start endpoint`）

未学習問題専用の開始処理を追加した。

```java
@GetMapping("/study/new/start")
public String getStudyNewStart(
        HttpSession session,
        @AuthenticationPrincipal UserDetails loginUser,
        @RequestParam(name = "difficulties", required = false)
        List<Difficulty> difficulty) {

    session.removeAttribute("studyQuestions");
    session.removeAttribute("studyCurrentPage");

    Users user =
            userServiceImpl.getUserOne(loginUser.getUsername());

    Long userId = user.getId();

    List<Question> questions =
            studyService.getNewQuestions(userId, difficulty);

    session.setAttribute("studyQuestions", questions);
    session.setAttribute("studyCurrentPage", 0);

    return "redirect:/study/question?page=0";
}
```

### 新しいControllerメソッドを追加した理由

既存の通常学習は

- 問題範囲
- ランダム出題

などを考慮して問題を取得している。

一方、未学習問題トレーニングでは

- 問題範囲を指定しない
- 未学習問題のみ取得する

という取得条件そのものが異なる。

そのため、既存の`getStudyStart()`へ条件分岐を追加するよりも、未学習問題専用のエンドポイントを用意した方が責務が明確になり、保守性も高くなる。

---

## 「次の問題へ」ボタンを削除

（git commit: `refactor: remove next question button from study screen`）

未学習問題トレーニングでは、「次の問題へ」ボタンを削除した。

```html
<!-- 前へ・次へ -->
<div class="mb-5 d-flex justify-content-center gap-4"
     style="margin-top:60px;">

    <a th:if="${hasPrevious}"
       th:href="@{/study/question(page=${nextPageIndex - 2})}"
       class="btn btn-outline-primary">
        前の問題へ
    </a>

    <!--
    <a th:if="${hasNext}"
       th:href="@{/study/question(page=${nextPageIndex})}"
       class="btn btn-outline-primary">
        次の問題へ
    </a>
    -->

    <a th:if="${!hasNext}"
       th:href="@{/study/complete}"
       class="btn btn-info">
        Complete
    </a>

</div>
```

### 削除した理由

未学習問題トレーニングでは、問題を評価するとその問題は「未学習」ではなくなる。

そのため、評価を行わずに「次の問題へ」で先送りできてしまうと、未学習問題という概念が崩れてしまう。

この学習モードでは評価を前提とした学習フローとするため、「次の問題へ」ボタンを廃止した。

---

## 動作確認

ログイン後、

```
/study/menu
```

へアクセスし、

1. 未学習トレーニングを選択
2. 難易度を選択
3. 「出題開始」をクリック

すると、未学習問題のみが正常に出題されることを確認した。

---

# 議論の余地

## 問題範囲を区切る機能は必要か

通常学習では問題数が多いため、

- 1〜100
- 101〜200

のように100問単位で問題範囲を区切って出題している。

一方、未学習問題トレーニングでは、条件に一致する未学習問題をすべて取得して出題するため、問題範囲を指定する仕組みは存在しない。

そのため、ユーザーによっては未学習問題が数百〜数千問となり、一度に取得する問題数が多くなる可能性がある。

しかし、この学習モードでは問題を評価すると、その問題は未学習問題ではなくなる。

つまり、

- 学習を進めるたびに未学習問題数は減少する
- 出題数も自然に少なくなっていく

という特徴がある。

また、このモードの目的は、

> まだ一度も学習していない問題を一通り消化すること

であり、通常学習のように問題範囲を細かく指定する必要性は高くない。

以上の理由から、現時点では問題範囲を区切る機能は追加せず、シンプルな仕様を採用することにした。

---

## ランダム出題機能は必要か

通常学習では、同じ問題範囲を何度も学習することがあるため、ランダム出題には一定のメリットがある。

しかし、未学習問題トレーニングでは、一度評価した問題は未学習問題から除外される。

つまり、同じ問題が繰り返し出題されることを前提とした学習モードではないため、通常学習ほどランダム出題の必要性は高くない。

また、ランダム出題を追加するには、

- メニュー画面へ「順番」「ランダム」の選択項目を追加する
- Controller・Serviceの引数を増やす
- 問題取得後にシャッフル処理を追加する

など、実装や画面構成が複雑になる。

一方で、得られるメリットはそれほど大きくない。

以上のことから、現時点ではランダム出題機能は追加せず、問題番号順に出題するシンプルな仕様を採用することにした。

将来的にユーザーから要望があった場合や、利用状況によって必要性が高まった場合に、改めて実装を検討する。

---

## ランダム出題を実装する場合の例

### QuestionRepository

問題番号順を明示する。

```java
@Query(value = """
        SELECT q.*
        FROM question q
        LEFT JOIN study_history sh
          ON q.question_id = sh.question_id
         AND sh.user_id = :userId
        WHERE q.difficulty IN (:difficulties)
          AND sh.question_id IS NULL
        ORDER BY q.question_id
        """, nativeQuery = true)
List<Question> getNewQuestions(
        @Param("userId") Long userId,
        @Param("difficulties") List<String> difficulties
);
```

---

### StudyServiceImpl

取得後にシャッフルする。

```java
public List<Question> getNewQuestions(
        long userId,
        List<Difficulty> difficulty,
        boolean random) {

    List<Question> extractedNewQuestions =
            questionRepository.getNewQuestions(
                    userId,
                    reviewService.convertDifficulty(difficulty));

    if (random) {
        Collections.shuffle(extractedNewQuestions);
    }

    return extractedNewQuestions;
}
```

---

### StudyController

ランダム出題フラグを受け取る。

```java
@GetMapping("/study/new/start")
public String getStudyNewStart(
        HttpSession session,
        @AuthenticationPrincipal UserDetails loginUser,
        @RequestParam(name = "difficulties", required = false)
        List<Difficulty> difficulty,
        @RequestParam(name = "random") boolean random) {

    ...

    questions =
            studyService.getNewQuestions(
                    userId,
                    difficulty,
                    random);

    ...

}
```

---

### study/menu.html

未学習トレーニングにも通常学習と同様の出題方法カードを追加する。

```html
<!-- 出題方法 -->
<div class="card mb-4">

    <div class="card-header">
        出題方法
    </div>

    <div class="card-body">

        <div class="form-check">
            <input class="form-check-input"
                   type="radio"
                   name="random"
                   value="false"
                   checked>

            <label class="form-check-label">
                順番に出題
            </label>
        </div>

        <div class="form-check">
            <input class="form-check-input"
                   type="radio"
                   name="random"
                   value="true">

            <label class="form-check-label">
                ランダムに出題
            </label>
        </div>

    </div>

</div>
```

---

## 通常学習とセッションを共有する設計について

現在の未学習問題トレーニングでは、通常学習と同じセッションを利用している。

具体的には、以下の2つのセッションを両方の学習モードで共有している。

- `studyQuestions`
- `studyCurrentPage`

一方で、未学習問題トレーニング専用のセッションを用意する設計も考えられる。

例えば、

- `newStudyQuestions`
- `newStudyCurrentPage`

といったセッションを新設すれば、通常学習とは完全に独立した状態管理が可能となる。

しかし、今回のアプリケーションでは通常学習と未学習問題トレーニングを同時に実行することは想定していない。

また、どちらの学習モードも、

- 問題一覧をセッションへ保存する
- 現在のページ番号を保持する
- `study/question.html` を利用して問題を表示する

という処理の流れは共通している。

そのため、セッションを分離したとしても得られるメリットは少ない一方で、

- セッション管理が複雑になる
- Controllerの分岐が増える
- 保守対象が増える

というデメリットが生じる。

以上のことから、現時点では通常学習と未学習問題トレーニングで同じセッションを共有する設計を採用することにした。

将来的に、

- 複数の学習モードを同時に保持したい
- モードごとに異なる情報をセッションへ保存する必要がある

といった要件が発生した場合には、セッションを分離することを検討する。

---

# 所感

今回の実装では、045で取得した「未学習問題数」を実際の学習機能へとつなげることができた。

未学習問題トレーニングは通常学習と似た処理の流れを持つものの、問題の取得条件や学習の目的が異なるため、専用のControllerメソッドを用意することで責務を明確に分離できた。

また、実装中には

- 問題範囲を指定するべきか
- ランダム出題を追加するべきか
- セッションを分離するべきか

といった設計についても検討した。

いずれも実装自体は難しくないが、現時点では機能追加によるメリットよりも画面構成やコードの複雑化によるデメリットの方が大きいと判断し、シンプルな仕様を採用した。

単に機能を追加するだけではなく、「本当に必要な機能か」を考えながら設計することの重要性を改めて実感した。

---

# 次にやること

- 学習メニュー（`study/menu.html`）のUIを改善する