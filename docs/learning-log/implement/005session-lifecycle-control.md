## セッションライフサイクルの制御とランダム出題の改善

### 実装内容

- 前回までの実装では、シャッフル済みの問題リストをセッションに保持していたため、Spring Boot を再起動しない限り同じ出題順が維持され続けていた。
- この現象は「やめる」ボタンで Home 画面へ戻った場合でも、問題を最後まで解いて Complete 画面へ遷移した場合でも同様に発生していた。

- 動作確認のため、問題をシャッフルして Controller に返す Service クラスへ以下のログ出力を追加した。

```java
for (Question q : extractedQuestions) {
    System.out.print(q.getQuestionId() + " ");
}
System.out.println();
```

- その状態で学習開始 → 「やめる」 → 学習開始を繰り返したところ、以下のようなログが出力された。

```text
4 5 3 1 2
Quit

4 5 3 1 2
Quit

4 5 3 1 2
Quit
```

- ここから、「やめる」ボタンを押して Home 画面へ戻ってもセッションが生き残っているため、再度 `/study` へアクセスした際に以下の処理が実行されていないことが分かった。

```java
if (session.getAttribute("questions") == null) {
    List<Question> questions = studyService.getRandomQuestion();
    session.setAttribute("questions", questions);
}
```

- その結果、前回セッションに保存された問題リストが再利用され、学習開始位置だけが先頭に戻り、同じ順番の問題が繰り返し出題されていた。

### 改善内容

- 今回は Question を保持するセッションのライフサイクルを学習単位に限定し、Home 画面から再度学習を開始した際には毎回新しくシャッフルされた問題が出題されるよう改善した。

- まず、`study.html` の「Complete」ボタンおよび「やめる」ボタンについて、直接 CompleteController や HomeController へ遷移するのではなく、一度 StudyController の専用 GetMapping へアクセスするよう変更した。

- その GetMapping 内で以下の処理を実行し、問題一覧を保持しているセッションのみを削除するようにした。

```java
session.removeAttribute("questions");
```

- ここでは `session.invalidate();` を使用する方法も考えられたが、このメソッドはセッション内の情報をすべて破棄してしまう。

- 将来的にはログイン情報やお気に入り情報などもセッションへ保存する可能性があるため、今回は Question 一覧のみを削除する `removeAttribute()` を採用した。

### 動作確認

- Spring Boot を再起動した後、「やめる」と「Complete」を何度か繰り返して動作確認を行った。

```text
1 3 5 2 4
Complete

2 3 5 1 4
Quit

4 5 3 1 2
Complete

3 4 2 5 1
Quit
```

- 以前とは異なり、学習を開始するたびに問題が再シャッフルされていることが確認できた。

### なぜ @SessionAttributes ではなく HttpSession を使用したのか

- 今回はセッションの生成・取得・削除のタイミングを明示的に制御したかったため、`HttpSession` を使用した。

- `@SessionAttributes` は Controller と Model を連携させてセッションへ自動保存してくれる便利な機能である一方、セッションへの保存や削除の流れがフレームワーク内部に隠蔽される。

- 一方、`HttpSession` を使用すると、

```java
session.setAttribute(...)
session.getAttribute(...)
session.removeAttribute(...)
```

のようにセッションの状態変化を明示的に記述できるため、今回のようにセッションのライフサイクルを学習目的で理解するには適していると感じた。

### 所感

- セッションのライフサイクル制御そのものは複雑な仕組みではなく、「やめる」ボタンや「Complete」ボタンを一度専用の GetMapping に誘導し、その中でセッションを破棄してからリダイレクトすればよい、という方法を自力で思いつき実装することができた。
- 今回は実装に伴って変更する箇所も少なく、比較的スムーズに実装を進めることができた。
- 実際に実装して動作を確認したことで、セッションのライフサイクルやセッション情報の保持期間について、本を読んでいるだけでは得られなかった理解を深めることができた。

### 次にやること

- 「やめる」ボタンとは別に「中断」ボタンを実装する。
- 中断時にはシャッフル済み問題リストと現在の問題位置を保持したまま Home 画面へ戻る。
- 再開時には最初からではなく、中断した問題から学習を再開できるようにする。