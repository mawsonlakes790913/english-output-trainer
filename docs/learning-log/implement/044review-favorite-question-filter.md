# 復習の問題セット取得の条件にお気に入り登録の有無も含める

043で`review/menu.html`が、お気に入り登録の有無を考慮した正しい出題件数を表示できるようになった。

次は、その条件に一致する問題だけを実際に取得できるよう、Repository・Service・Controllerを修正した。

---

## StudyHistoryRepositoryを修正（feat: add favorite condition to review question query）

### 修正内容

復習問題取得用のSQLに、お気に入り登録状況による絞り込みを追加した。

従来は、

- 評価
- 難易度

のみで検索していたが、これに加えて

- 全件
- お気に入り登録済み
- お気に入り未登録

の3種類を切り替えられるようにした。

### 修正前

```java
@Query(value = """
        SELECT q.*
        FROM study_history sh
        JOIN question q
          ON sh.question_id = q.question_id
        WHERE sh.user_id = :userId
          AND sh.evaluation IN (:evaluations)
          AND q.difficulty IN (:difficulties)
        ORDER BY sh.evaluation_updated_at ASC
        """, nativeQuery = true)
List<Question> getQuestions(
        @Param("userId") Long userId,
        @Param("evaluations") List<String> evaluations,
        @Param("difficulties") List<String> difficulties
);
```

### 修正後

```java
@Query(value = """
        SELECT q.*
        FROM study_history sh
        JOIN question q
          ON sh.question_id = q.question_id
        LEFT JOIN favorites f
          ON sh.user_id = f.user_id
         AND sh.question_id = f.question_id
        WHERE sh.user_id = :userId
          AND sh.evaluation IN (:evaluations)
          AND q.difficulty IN (:difficulties)
          AND (
                 :favoriteCondition = 'ALL'
              OR (:favoriteCondition = 'FAVORITED'
                  AND f.question_id IS NOT NULL)
              OR (:favoriteCondition = 'NOT_FAVORITED'
                  AND f.question_id IS NULL)
          )
        """, nativeQuery = true)
List<Question> getQuestions(
        @Param("userId") Long userId,
        @Param("evaluations") List<String> evaluations,
        @Param("difficulties") List<String> difficulties,
        @Param("favoriteCondition") String favoriteCondition
);
```

### ポイント

- `favorites`テーブルを`LEFT JOIN`で結合
- `FavoriteCondition`によって取得条件を切り替えられるようにした
- `favoriteCondition`をRepositoryメソッドの引数に追加した

---

## ReviewServiceを修正（feat: support favorite condition in review service）

### getQuestionメソッド

Repositoryへお気に入り条件を渡せるように修正した。

### 修正内容

```java
public List<Question> getQuestion(
        Long userId,
        List<Evaluation> evaluations,
        List<Difficulty> difficulties,
        FavoriteCondition favoriteCondition,
        boolean random) {

    List<Question> extractedQuestions =
            studyHistoryRepository.getQuestions(
                    userId,
                    convertEvaluation(evaluations),
                    convertDifficulty(difficulties),
                    convertFavoriteCondition(favoriteCondition));

    // シャッフルする
    if (random) {
        Collections.shuffle(extractedQuestions);
    }

    return extractedQuestions;
}
```

### ポイント

- `FavoriteCondition`を引数に追加
- Repositoryへ渡す際に`convertFavoriteCondition()`を利用するようにした

---

## ReviewControllerを修正（feat: accept favorite condition in review controller）

### getReviewStartメソッド

画面で選択されたお気に入り条件を受け取り、Serviceへ渡すように修正した。

### 修正内容

```java
@GetMapping("/review/start")
public String getReviewStart(
        Model model,
        HttpSession session,
        @AuthenticationPrincipal UserDetails loginUser,
        @RequestParam(name = "evaluations", required = false)
                List<Evaluation> evaluations,
        @RequestParam(name = "difficulties", required = false)
                List<Difficulty> difficulties,
        @RequestParam(name = "favoriteCondition", required = false)
                FavoriteCondition favoriteCondition,
        @RequestParam(name = "random", required = false)
                boolean random) {

    // 既存の学習状態を破棄
    session.removeAttribute("reviewQuestions");
    session.removeAttribute("reviewCurrentPage");

    // user_id(文字列)からUsersを取得
    Users user = userServiceImpl.getUserOne(loginUser.getUsername());
    Long userId = user.getId();

    // 新しい問題セットを作成
    List<Question> questions =
            reviewService.getQuestion(
                    userId,
                    evaluations,
                    difficulties,
                    favoriteCondition,
                    random);

    session.setAttribute("reviewQuestions", questions);
    session.setAttribute("reviewCurrentPage", 0);

    return "redirect:/review/question";
}
```

### ポイント

- `favoriteCondition`を`@RequestParam`として受け取るようにした
- Serviceへそのまま渡すように修正した

---

## 所感

今回の修正は、前回実装した出題件数取得処理とほぼ同じ流れだったため、実装自体は比較的スムーズに進められた。

Repository・Service・Controllerへ順番に引数を追加していくだけだったため、大きく設計を変更する必要もなく、実装難易度は高くなかった。

前回までに処理の流れを整理できていたことで、各レイヤーへ機能を横展開する感覚も少し身についたと感じた。

---

## 次にやること

- 通常学習のフィルターに「未学習（まだ一度も閲覧していない問題）」を追加する