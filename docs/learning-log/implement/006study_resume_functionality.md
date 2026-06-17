## 学習中断・再開機能の実装

### 実装内容

- 学習ページに「中断する」ボタンを追加した。
- 「中断する」ボタン押下時は、新設した `GET /study/suspend` へ遷移する。
- `/study/suspend` では現在の問題位置（ページ情報）をセッションへ保存し、その後 Home 画面へリダイレクトする。
- Home 画面には「途中から始める」ボタンを追加した。
- 「途中から始める」ボタン押下時は、新設した `GET /study/resume` へ遷移する。
- `/study/resume` では、セッションに保存されている問題一覧と中断時のページ情報を取得し、中断した位置から学習を再開する。

### 実装中に悩んだ点

- 最も悩んだのは、中断時に `session.setAttribute()` で何を保存すべきかという点だった。

- 当初は `getStudy()` と同様に、

```java
session.setAttribute("questions", questions);
```

のような実装を考えた。

- しかし、`questions` は `getStudy()` メソッド内のローカル変数であり、`suspendStudy()` から参照することができない。

- また、`questions` をクラス変数として保持する方法も試したが、ブラウザ上の挙動が不安定になったため採用しなかった。

### 解決方法

- 実際には中断時に保存すべきなのは問題一覧ではなく、現在どこまで進んだかというページ情報のみだった。

```java
session.setAttribute("currentPage", page);
```

- なぜなら、問題一覧は学習開始時点で既に

```java
session.setAttribute("questions", questions);
```

によってセッションへ保存されているためである。

- 中断機能に必要なのは、

```text
① シャッフル済み問題一覧
② 現在何問目まで進んだか
```

の2つの情報であり、このうち①は既にセッション内へ保存済みである。

- そのため、中断時には②のページ情報のみを追加で保存すればよい。

- 再開時は、セッションからページ情報を取得し、

```java
List<Question> questions =
        (List<Question>) session.getAttribute("questions");

Question question =
        questions.get(page);
```

を実行することで、中断した問題から学習を再開できるようになった。

### 注意点

- セッションが存在しない状態、またはセッションが破棄された状態で「途中から始める」を押すとエラーが発生する可能性がある。

- 理由は、セッションが存在しない場合に問題一覧を新規取得する処理は `getStudy()` のみに実装されており、`resumeStudy()` には存在しないためである。

- その結果、問題一覧が存在しない状態で続きの処理を実行しようとしてしまう。

- この問題を防ぐため、セッションに問題一覧が存在しない場合は再開処理を実行せず、そのまま Home 画面へ戻るようにした。

```java
if (session.getAttribute("questions") == null) {
    return "redirect:/";
}
```

### リファクタリング

- `getStudy()` と `resumeStudy()` には共通する処理が多数存在していた。

- 特に以下の処理は両メソッドで重複していた。

```java
Question question = questions.get(page);

model.addAttribute("question", question);
model.addAttribute("currentPage", page + 1);
model.addAttribute("totalPages", questions.size());
model.addAttribute("hasPrevious", page > 0);
model.addAttribute(
        "hasNext",
        page < questions.size() - 1);
```

- 可読性向上と保守性向上のため、これらの共通処理を private メソッドへ切り出した。

### 追加対応

- セッションが存在しない状態で「途中から始める」を押した場合の制御を追加した。
- 当初は `resumeStudy()` 側で null チェックを行い Home 画面へリダイレクトする実装としていた。
- その上で、JavaScriptを組み合わせて警告ウインドウを表示する設計を考えていた。
- しかしユーザー視点では再開できないことが事前に分かる方が親切であるため、Home画面表示時にセッションの有無を判定し、再開ボタンの表示を制御するようにした。
- 実装自体は想像していたよりも簡単で、セッションの有無を判定した結果を Model 経由で HTML に渡し、`th:if` を利用して表示制御を行うだけで実現できた。

### リファクタリング（学習開始と再開処理の責務分離）

- 中断からの再開機能は正常に動作していたが、新たな問題が見つかった。

- それは、

```text
学習開始
↓
中断
↓
学習開始
```

を実行した場合、本来であれば新しい問題セットが生成されるべきにもかかわらず、

```text
学習開始
↓
中断
↓
再開
```

と同じ挙動になり、中断位置から学習が再開されてしまうことである。

- 原因は、`getStudy()` が

```java
if (session.getAttribute("questions") == null) {
    List<Question> questions =
            studyService.getRandomQuestion();
    session.setAttribute("questions", questions);
}
```

によって、

```text
① 新規学習開始
② 学習画面表示
```

という異なる責務を同時に担当していたためである。

- 一度中断すると、セッション内には問題一覧が残り続ける。

- その結果、再度「学習開始」を押しても問題一覧が新規生成されず、前回の問題一覧がそのまま利用されてしまっていた。

- そこで責務分離を徹底し、

```text
新規開始 → startStudy()
再開     → resumeStudy()
表示     → getStudy()
```

という構成へリファクタリングした。

#### 新規開始処理

- `startStudy()` を新設した。

- 新規開始時は既存の学習状態を破棄するため、

```java
session.removeAttribute("questions");
session.removeAttribute("currentPage");
```

を実行する。

- その後、

```java
List<Question> questions =
        studyService.getRandomQuestion();
```

によって新しい問題一覧を取得し、セッションへ保存する。

- 最後に `redirect:/study` を実行し、学習画面へ遷移する。

#### 再開処理

- `resumeStudy()` は新しい問題を取得しない。

- セッションから中断時のページ番号を取得し、

```java
Integer page =
        (Integer) session.getAttribute("currentPage");
```

を用いて、

```java
return "redirect:/study?page=" + page;
```

を実行する。

- これにより、中断した位置を基準として `getStudy()` を呼び出せるようになった。

#### 表示処理

- `getStudy()` は問題取得を行わず、セッションに保存された問題一覧を取得して表示する役割のみを担当する。

- これにより、

```text
startStudy()  → 問題生成
resumeStudy() → 再開位置決定
getStudy()    → 画面表示
```

という明確な責務分離が実現できた。

### 効果

- 中断後に「学習開始」を押した場合は、必ず新しい問題セットから学習が開始されるようになった。

- 中断後に「途中から始める」を押した場合は、保存されていたページ位置から正しく学習を再開できるようになった。

- また、学習開始処理・再開処理・画面表示処理の責務が明確になり、コードの可読性と保守性も向上した。

### 所感

- 今回の機能は仕様自体のイメージは比較的掴みやすかったが、実際にコードへ落とし込み、正しく動作させるのは想像以上に難しかった。

- AIからヒントを得る中で、「セッションに保存されているシャッフル済み問題一覧」と「現在何問目まで進んだか」は別の情報であることに気付くことができた。

- この考え方に気付いてからは実装方針が明確になり、無事に中断・再開機能を完成させることができた。

- また、今回の実装を通して、セッションには単にデータを保存するだけではなく、複数の情報を目的ごとに分けて管理するという考え方も学ぶことができた。

### 次にやること

- 順番に出題する学習モードを復活させる。