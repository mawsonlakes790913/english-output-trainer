## Bootstrapによる画面デザインの改善

### 実装内容

学習画面（study.html）の見た目が非常に簡素だったため、Bootstrapを導入して最低限のデザイン改善を行った。

まず、独自CSSを利用できるようにするため、CSSファイルを作成し、HTML側で以下のように読み込む設定を追加した。

```html
<link rel="stylesheet"
      th:href="@{パス名}">
```

CSSファイル自体の読み込みは特に問題なく行えた。

次にBootstrapを利用できるようにするため、pom.xmlへWebJarsの依存関係を追加した。

```xml
<dependency>
    <groupId>org.webjars</groupId>
    <artifactId>bootstrap</artifactId>
    <version>5.3.3</version>
</dependency>

<dependency>
    <groupId>org.webjars</groupId>
    <artifactId>webjars-locator</artifactId>
    <version>0.52</version>
</dependency>
```

BootstrapはCSSファイルを配置するだけでは利用できず、まずMavenの依存関係として登録する必要がある。登録後は以下のようにHTMLから読み込めるようになる。

```html
<link rel="stylesheet"
      th:href="@{/webjars/bootstrap/css/bootstrap.min.css}">
```

また、`webjars-locator` を追加したことで、

```html
/webjars/bootstrap/css/bootstrap.min.css
```

のような記述が可能となり、バージョン番号を明示する必要がなくなった。

今回適用したデザイン変更は以下の2点のみである。

- 画面全体の中央寄せ
- リンクをBootstrapのボタンとして表示

例えば、

```html
<a class="btn btn-primary">
```

のようなクラス指定を行うことで、独自CSSを書かなくてもボタンらしい見た目を実現できた。

### 実装内容２

- 当初は「解答を見る」ボタンをクリックすると、ボタンはそのまま残り、その下に解答が表示される仕様としていた。
- しかしこの実装では、解答表示時に「解答を見る」ボタンと「前の問題へ」「次の問題へ」ボタンの間へ解答が挿入される形となり、それ以降の要素がすべて下方向へ押し出されてしまった。

![消えないボタン](../docs/images/remaining-button.png)

- 学習中に何度もクリックを繰り返すと、画面上のボタンやテキストの位置が変化し続け、視認性や操作性が悪くなるため、クリック後は「解答を見る」ボタンを非表示にし、その位置に解答を表示する仕様へ変更した。

```javascript
function showAnswer() {
    document.getElementById("answerButton").style.display = "none";
    document.getElementById("answerArea").style.display = "block";
}
```

- しかし、この変更後も「解答を見る」ボタンと解答表示エリアの高さが一致していなかったため、解答表示時に「前の問題へ」「次の問題へ」ボタン以降の要素がわずかに移動する問題が残っていた。

![ズレる高さ](../docs/images/height-mismatch.png)

- そこで、CSSでボタン表示時と解答表示時の高さを揃えるよう調整し、解答の表示・非表示を切り替えてもレイアウトが変化しないよう改善した。

```css
.answer-container {
    min-height: 80px;
}
```

- これにより、解答表示時のレイアウトのずれが解消され、学習中にボタンやテキストが不要に移動しない安定した画面表示を実現できた。
```

### 所感

今回のデザイン改善は非常に限定的な内容だったため、CSSやBootstrapに関する知識がほとんどない状態でも容易に実装できた。

特にBootstrapは、あらかじめ用意されたクラス名を指定するだけで見栄えの良いデザインを適用できるため、自前でCSSを書く手間を大きく削減できることを実感した。

当初はCSSを中心に学習する必要があると考えていたが、今回のような小規模なアプリケーションであれば、Bootstrapだけでも十分な見た目を実現できることが分かった。

今回は最低限のデザイン変更に留めたが、Bootstrapにはグリッドレイアウトやモーダルウィンドウなどの高度な機能も存在する。今後アプリケーションの機能が増えてきた際には、それらも活用しながらユーザーにとって使いやすい画面設計を目指したい。