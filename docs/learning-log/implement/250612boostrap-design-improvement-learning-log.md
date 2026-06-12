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

### 所感

今回のデザイン改善は非常に限定的な内容だったため、CSSやBootstrapに関する知識がほとんどない状態でも容易に実装できた。

特にBootstrapは、あらかじめ用意されたクラス名を指定するだけで見栄えの良いデザインを適用できるため、自前でCSSを書く手間を大きく削減できることを実感した。

当初はCSSを中心に学習する必要があると考えていたが、今回のような小規模なアプリケーションであれば、Bootstrapだけでも十分な見た目を実現できることが分かった。

今回は最低限のデザイン変更に留めたが、Bootstrapにはグリッドレイアウトやモーダルウィンドウなどの高度な機能も存在する。今後アプリケーションの機能が増えてきた際には、それらも活用しながらユーザーにとって使いやすい画面設計を目指したい。