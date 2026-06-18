# 学習ログ：Layout Dialectによるレイアウト分離とBootstrap導入

## 概要

画面共通部分を管理しやすくするため、Thymeleaf Layout Dialectを導入し、レイアウト画面とコンテンツ画面を分離した。

これにより、共通部分はレイアウト側で一元管理し、各画面ではコンテンツ部分の実装に集中できる構成となった。

---

## 実装内容

### Layout Dialectの導入

Thymeleaf Layout Dialectを利用するため、pom.xmlに依存関係を追加した。

```xml
<dependency>
    <groupId>nz.net.ultraq.thymeleaf</groupId>
    <artifactId>thymeleaf-layout-dialect</artifactId>
</dependency>
```

これにより、レイアウトテンプレートとコンテンツテンプレートを組み合わせる仕組みが利用可能になった。

### レイアウト画面とコンテンツ画面の分離

レイアウト用のHTMLを作成し、共通ヘッダーを配置した。

今回の学習アプリは構成がシンプルなため、多数のフラグメントは作成せず、以下のみを実装した。

- ヘッダー
- コンテンツ領域

ヘッダーには以下のみを配置した。

- アプリケーションタイトル
- ログアウトボタン

なお、ログアウトボタンは現時点では画面表示のみであり、押下しても何も処理は行われない。

### Bootstrapの導入

CSSの記述を最小限に抑えるため、Bootstrapを導入した。

pom.xmlへ以下を追加した。

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

Bootstrapのクラスを利用することで、

- 背景色
- 余白
- ボタンデザイン
- Flexレイアウト

などを独自CSSを書かずに実現できた。

そのため、CSSファイルへの記述は最低限に抑えられた。

---

## 発生した問題

### ヘッダーが表示されない問題

レイアウト実装中、アプリケーション起動時にエラーは発生しないにも関わらず、ヘッダーがブラウザ上に表示されない問題が発生した。

当初は以下のような構成になっていた。

レイアウト側

```html
<nav layout:replace="~{layout/header :: header-contents}"></nav>
```

ヘッダー側

```html
<header th:fragment="header-contents">
```

しかし、`layout:replace` で読み込む場合は、読み込み先も `layout:fragment` で定義する必要があった。

誤って `th:fragment` を使用していたため、ヘッダーが正しく読み込まれず、画面に表示されなかった。

エラーが発生しないため原因の特定が難しく、修正まで約30分を要した。

最終的に、

```html
<header layout:fragment="header-contents">
```

へ修正することで正常に表示されるようになった。

---

## 所感

Layout Dialectによって共通部分と画面固有部分を分離できるようになり、今後画面数が増えた際の保守性向上が期待できる。

また、Bootstrapを導入したことで最低限のデザインを短時間で実装できた。

今回は非常にシンプルな画面構成であったため多くのBootstrapクラスは使用しなかったが、今後業務アプリケーションのような複雑な画面を作成する場合は、

- フォーム
- テーブル
- ナビゲーション
- グリッドレイアウト

など、多くのBootstrapクラスを利用することになると思われる。

今後はBootstrapをより使いこなせるよう、使用できるクラスの種類を増やしていきたい。

---

## 次回やること

- 入力フォームの作成
- ログイン機能はまだ実装しない
- フォーム入力画面のレイアウト作成
- Bootstrapのフォーム関連クラスの学習