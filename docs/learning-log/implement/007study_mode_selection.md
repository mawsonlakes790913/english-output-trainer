## 学習モード選択機能の実装

### 実装内容

- 学習開始時に「順番に出題する」と「ランダムに出題する」の2つの学習モードを選択できるようにした。

- 「順番に出題する」を選択した場合は、

```java
studyService.getQuestion();
```

を実行し、データベースから取得した問題を順番通りに出題する。

- 「ランダムに出題する」を選択した場合は、

```java
studyService.getRandomQuestion();
```

を実行し、シャッフル済みの問題一覧を出題する。

- 学習モードの判定には URL パラメータを利用した。

```java
@GetMapping("/study/start")
public String startStudy(
        HttpSession session,
        @RequestParam String mode)
```

- Controller 側では受け取った `mode` の値によって取得する問題一覧を切り替えるようにした。

```java
if ("sequential".equals(mode)) {
    questions = studyService.getQuestion();
} else {
    questions = studyService.getRandomQuestion();
}
```

### 工夫した点

- 学習モード選択ボタンを常時表示するのではなく、「新規開始」ボタン押下後に表示するようにした。

- これにより Home 画面が必要以上に複雑になることを防ぎ、

```text
新規開始
↓
学習モード選択
↓
学習開始
```

という自然な操作フローを実現できた。

- また、将来的に学習モードが増えた場合も、この領域へボタンを追加するだけで拡張できる構成になった。

### 勉強になった点

- 当初は HTML で選択した学習モードをどのように Controller へ渡すのかが分からなかった。

- 実装を通して、

```html
<input type="hidden"
       name="mode"
       value="random">
```

のように HTML 側でパラメータを定義しておくことで、

```java
@RequestParam String mode
```

として Controller 側で受け取れることを学んだ。

- また、Thymeleaf と Spring MVC を組み合わせることで、

```text
HTML
↓
URLパラメータ
↓
Controller
```

というデータの受け渡しが自然に行われる仕組みも理解できた。

### 所感

- 前回の中断・再開機能の実装時と比較すると、今回は最初から実装方針の見当がついていた。

- 学習開始処理と学習画面表示処理を事前に分離していたため、学習モードの追加も最小限の修正で対応することができた。

- 改めて、責務を分離しておくことで後からの機能追加が容易になることを実感した。

### 次にやること

- 各ボタン押下時に確認ダイアログを表示する。

```text
本当に中断しますか？
本当に終了しますか？
本当に新しい学習を開始しますか？
```

のような確認メッセージを表示し、誤操作を防止する。