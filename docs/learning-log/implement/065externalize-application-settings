# 0065 設定の外部化

設定の外部化とは、**環境によって変わる設定値や構成を、アプリケーションの外部から切り替えられるようにすること**である。

今回は、「設定の外部化」をテーマに、Spring Bootが提供する仕組みを利用して、設定値や実装クラス、ログ出力を環境に応じて切り替える方法を学習する。

---

# 設定の外部化が必要な理由

アプリケーションは、開発中に使用する**開発環境**や、利用者が実際に使用する**本番環境**など、複数の環境で動作する。

環境が変わると、

- データベースの接続先
- ログの出力先
- 外部サービスの接続先

などの設定も変更しなければならない。

設定をプログラムの中へ直接記述してしまうと、環境を切り替えるたびにコードを書き換え、アプリケーションを再ビルドする必要がある。

そこで、設定をアプリケーションの外へ出しておけば、コードを書き換えることなく、環境に応じて設定だけを切り替えられるようになる。

---

# 設定の外部化の考え方

Webアプリケーションでは、一般的に次のような環境を用意する。

| 環境 | 使用者 | 用途 |
|------|--------|------|
| 開発環境（ローカルPC） | 開発者 | アプリケーションの開発・単体テスト |
| テスト環境 | 開発者 | 本番と同じ構成で動作確認を行う |
| ステージング環境 | 発注者 | 本番と同じデータで最終確認を行う |
| 本番環境 | エンドユーザー | 実際に運用される環境 |

複数の環境を用意することで、

- 開発環境では自由に開発できる
- 本番に近い環境で動作確認できる
- 本番へ影響を与えずにテストできる

といったメリットがある。

一方で、複数環境を運用すると次のような課題も生じる。

- 環境ごとに設定値を変更する必要がある
- 環境によって使用する実装クラスを切り替える必要がある

---

## 設定値の変更が必要

環境によって異なる代表的な設定は、

- データベース接続先
- パスワード
- ログの出力先
- OSごとのファイルパス

などである。

Spring Bootでは、これらの設定を`application.yml`へ記述する。

しかし、`application.yml`はjarファイルへ含まれるため、環境ごとに異なる設定を書いたjarファイルを複数作成すると、

- 配布ミス
- 設定ミス

などの原因となる。

そのため、設定値はアプリケーションの外から切り替えられるようにすることが望ましい。

---

## 実装クラスの変更が必要

アプリケーションによっては、外部APIを利用することがある。

例えば、

- 開発環境ではスタブ
- テスト・本番環境では実際のAPI

を使用したいケースがある。

もしコードを書き換えて切り替える運用にすると、

```java
// 開発環境
WeatherService service = new WeatherServiceStub();

// テスト環境
// WeatherService service = new WeatherServiceImpl();
```

のように、環境を切り替えるたびにコードを書き換え、jarファイルを作り直さなければならない。

このような運用は手間がかかるだけでなく、切り替え忘れなどの原因にもなる。

---

## プロファイル（Profile）

こうした問題を解決するため、Spring Bootでは**プロファイル（Profile）**という仕組みが用意されている。

プロファイルとは、

- `local`
- `dev`
- `prod`

など、動作する環境を識別するための名前である。

どのプロファイルを有効にするかは、

```
SPRING_PROFILES_ACTIVE
```

という環境変数で指定する。

これにより、

- 環境ごとの設定ファイル（YAML）
- 環境ごとのBean定義

を自動で切り替えられる。

同じjarファイルを利用したまま、環境だけを変更できることが大きなメリットである。

065-1.png

Springでは、この環境変数の値に応じて、

- 環境ごとの設定ファイル（YAML）
- JavaConfigによるBean定義

を自動で切り替えることができる。

設定値をアプリケーションの外へ持たせることで、作成するjarファイルを1つに統一でき、リリース作業も簡単になる。

---

### 補足：JavaConfig

JavaConfigとは、XMLの代わりにJavaクラスを利用してSpringのBeanを定義する仕組みである。

クラスへ`@Configuration`を付与して作成する。

本章では、環境ごとに「本物のサービス」と「スタブ」のどちらをBeanとして登録するかを、このJavaConfigで制御する。

---

# 設定値を環境ごとに切り替える

ここからは、実際に設定の外部化を実装する。

今回は、

- **開発環境（local）**
- **テスト環境（dev）**

の2つの環境を対象とする。

本番環境（prod）も一般的には利用されるが、このアプリケーションでは仕組みの理解を目的としているため、今回は対象外とする。

設定が正しく切り替わったかどうかは、Spring Boot起動時のログで確認する。

```
applicationName=english-output-trainer-local
environment=local
```

テスト環境へ切り替えた場合は、

```
applicationName=english-output-trainer-dev
environment=dev
```

と表示されれば成功である。

---

## 実装（feat: implement profile-based configuration switching）

### application-local.ymlの作成

開発環境用の設定ファイルを作成する。

### application-dev.ymlの作成

テスト環境用の設定ファイルを作成する。

環境ごとの設定ファイルは、

```
application-XXX.yml
```

という名前で作成する。

`XXX`はプロファイル名であり、

- local
- dev

など、`spring.profiles.active`へ指定する値と一致させる必要がある。

---

### application.yml

共通設定ファイルへ、

```yaml
spring:
  profiles:
    active: ${SPRING_PROFILES_ACTIVE:local}
```

を追加する。

#### spring.profiles.active

`spring.profiles.active`は、アプリケーションがどの環境で動作するかを指定するプロパティである。

この値に応じて、

- 環境ごとの設定ファイル（YAML）
- JavaConfig

が自動的に切り替わる。

今回は、

```yaml
${SPRING_PROFILES_ACTIVE:local}
```

と設定した。

これは、

```
${変数名:初期値}
```

というEL式であり、

- 環境変数が設定されていればその値
- 設定されていなければ`local`

を使用するという意味である。

---

### 環境ごとの設定ファイル

#### application-local.yml

```yaml
spring:
  application:
    name: english-output-trainer-local

app:
  environment: local
```

#### application-dev.yml

```yaml
spring:
  application:
    name: english-output-trainer-dev

app:
  environment: dev
```

---

### ポイント① spring.application.name

`spring.application.name`では、アプリケーション名を設定できる。

---

### ポイント② 任意のプロパティ

設定ファイルでは、Spring Bootが標準で用意しているプロパティ以外にも、独自のプロパティを定義できる。

このアプリケーションでは、

```yaml
app:
  environment: local
```

のように、

`app.environment`

という独自プロパティを定義し、現在の環境を保持するために利用した。

---

### 設定値確認用クラス

設定が正しく切り替わっていることを確認するため、

```
ApplicationConfigLogger
```

を作成した。

```java
@Component
@Slf4j
public class ApplicationConfigLogger {

    @Value("${spring.application.name:N/A}")
    private String applicationName;

    @Value("${app.environment:N/A}")
    private String environment;

    @PostConstruct
    private void postConstruct() {

        log.info(
                "applicationName={} environment={}",
                applicationName,
                environment);
    }
}
```

#### @Value

`@Value`を利用すると、

- YAML
- 環境変数
- 起動引数

などから値を取得できる。

このクラスでは、

- `spring.application.name`
- `app.environment`

を取得している。

値が存在しない場合は、

```
N/A
```

を初期値として利用する。

#### @PostConstruct

`@PostConstruct`を付与したメソッドは、Bean生成直後に1度だけ実行される。

そのため、Spring Boot起動時に現在の設定値をログへ出力できる。

---

## 実行

まず、環境変数を設定せずにSpring Bootを起動する。

```
applicationName=english-output-trainer-local
environment=local
```

と表示され、

`application-local.yml`

が読み込まれていることを確認した。

---

### 環境変数の設定

続いて、

```
SPRING_PROFILES_ACTIVE=dev
```

を設定して起動すると、

```
applicationName=english-output-trainer-dev
environment=dev
```

と表示され、

`application-dev.yml`

が読み込まれていることを確認した。

なお、

起動引数でも`spring.profiles.active`を指定できる。

その場合は、

**環境変数よりも起動引数が優先される**。

# 処理の実装を環境ごとに切り替える

`spring.profiles.active`の値によって、環境ごとに異なる実装クラス（Bean）へ切り替えることもできる。

例えば、

- 開発環境ではスタブ（ダミー実装）
- 本番環境では実際のAPIを呼び出す実装

のように、`@Profile`を利用して自動的に切り替えられる。

しかし、このアプリケーションでは各Serviceの実装クラスが1つしか存在しないため、環境によって切り替える対象がない。

そのため、この機能は実装しなかった。

---

# ログの出力先を環境ごとに切り替える

環境によって、ログの出力先を変更したい場合がある。

例えば、

- 開発環境ではプロジェクト直下へログファイルを出力する
- テスト環境では共有ディレクトリへ出力する

など、環境ごとに適切な出力先を設定したいケースがある。

このアプリケーションでは、`spring.profiles.active`の値に応じて、ログファイルの出力先を切り替えられるようにする。

これにより、同じ`logback-spring.xml`を利用したまま、環境ごとに異なるログ出力先を使用できるようになる。

---

## 実装（feat: implement profile-based log file switching）

### ① ログ設定の変更

これまでは、ログの出力先を`logback-spring.xml`へ直接記述していた。

```xml
<property name="file.path"
          value="./english-output-trainer.log" />

<property name="file.path.pattern"
          value="${file.path}-%d{yyyy-MM-dd}.log.zip" />
```

ここから分かることは、以下のとおりである。

- ログファイル名は`english-output-trainer.log`
- 出力先はカレントディレクトリ（`./`）
- ログローテーション時は

```
english-output-trainer.log-2026-08-06.log.zip
```

のような名前で圧縮保存される。

また、ログの出力先が`logback-spring.xml`へ直接書かれているため、環境ごとに出力先を変更したい場合は、このXMLを書き換える必要がある。

そのため、柔軟性や運用性はあまり高くない構成となっていた。

そこで、

```xml
<springProperty
        name="file.path"
        source="log.file.path"/>

<springProperty
        name="file.path.pattern"
        source="log.file.pattern"/>
```

へ変更した。

これにより、

- `application-local.yml`
- `application-dev.yml`

などからログ設定を取得できるようになった。

---

## springPropertyとは？

`springProperty`タグは、Spring Bootの設定ファイル（`application.yml`など）に定義した値を、Logbackから利用するためのタグである。

### propertyタグとの違い

従来使用していた

```xml
<property
    name="file.path"
    value="./english-output-trainer.log"/>
```

は、

Logback内部で直接変数を定義していた。

つまり、

```
file.path
    ↓
./english-output-trainer.log
```

という値をLogback自身が保持している。

そのため、

```xml
<file>${file.path}</file>
```

は、

```xml
<file>./english-output-trainer.log</file>
```

として扱われる。

ログの出力先はXMLへ固定されるため、環境ごとに変更するには`logback-spring.xml`を書き換えなければならない。

---

### springPropertyタグ

一方、

```xml
<springProperty
    name="file.path"
    source="logging.file.path"/>
```

は、

Spring Bootの設定ファイルから値を取得し、Logbackへ渡す。

例えば、

```yaml
logging:
  file:
    path: ./logs/app.log
```

と設定されている場合、

```
logging.file.path
        ↓
./logs/app.log
```

を取得し、

Logback内部では

```
file.path
        ↓
./logs/app.log
```

という変数として利用できる。

そのため、

```xml
<file>${file.path}</file>
```

は、

```xml
<file>./logs/app.log</file>
```

として扱われる。

---

### name属性とsource属性

例えば、

```xml
<springProperty
    name="file.path"
    source="logging.file.path"/>
```

の場合、

#### name属性

```
file.path
```

Logback内部で利用する変数名である。

#### source属性

```
logging.file.path
```

`application.yml`などから取得するプロパティ名である。

つまり、

```
application.yml
──────────────────────
logging.file.path
        ↓
./logs/app.log
        │
        │ 読み込む
        ▼
springProperty
──────────────────────
name="file.path"
source="logging.file.path"
        │
        ▼
Logback内部

file.path
        ↓
./logs/app.log
```

という流れになる。

---

### springPropertyタグを利用するメリット

例えば、

#### application-local.yml

```yaml
logging:
  file:
    path: ./logs/local.log
```

#### application-prod.yml

```yaml
logging:
  file:
    path: /var/log/app.log
```

のように環境ごとに設定しておけば、

`logback-spring.xml`は

```xml
<file>${file.path}</file>
```

のままでよい。

Spring Bootが起動時に現在の環境を判定し、適切な設定値をLogbackへ自動で渡してくれるためである。

これにより、ログの出力先を設定ファイル側だけで管理できるようになる。

---

## ② local環境の設定

環境ごとの設定ファイルへ、ログの出力先を追加する。

### application-local.yml

```yaml
log:
  file:
    path: ./english-output-trainer.log
    pattern: ${log.file.path}-%d{yyyy-MM-dd}.log.zip
```

でエラーが発生したため、

```yaml
log:
  file:
    path: ${LOG_FILE_PATH:./english-output-trainer-local.log}
    pattern: ./english-output-trainer-local.log-%d{yyyy-MM-dd}.log.zip
```

としていた。

---

### application-dev.yml

```yaml
log:
  file:
    path: ./english-output-trainer-dev.log
    pattern: ${log.file.path}-%d{yyyy-MM-dd}.log.zip
```

でも同様のエラーが発生したため、

```yaml
log:
  file:
    path: ${LOG_FILE_PATH:./english-output-trainer-dev.log}
    pattern: ./english-output-trainer-dev.log-%d{yyyy-MM-dd}.log.zip
```

としていた。

これにより、

```
local
    ↓
application-local.yml
    ↓
./english-output-trainer.log

dev
    ↓
application-dev.yml
    ↓
./english-output-trainer-dev.log
```

というように、`spring.profiles.active`に応じてログファイルを切り替えられるようになった。

---

### 修正（fix: restore log file pattern property reference）

原因を調査したところ、エラーは

```
path
```

を

```
${LOG_FILE_PATH:...}
```

で囲んでいなかったことによる設定ミスであった。

修正後は、

#### application-local.yml

```yaml
log:
  file:
    path: ${LOG_FILE_PATH:./english-output-trainer.log}
    pattern: ${log.file.path}-%d{yyyy-MM-dd}.log.zip
```

#### application-dev.yml

```yaml
log:
  file:
    path: ${LOG_FILE_PATH:./english-output-trainer-dev.log}
    pattern: ${log.file.path}-%d{yyyy-MM-dd}.log.zip
```

という、本来意図した設定へ戻した。

---

## 実行

Spring Bootを起動し、

実行構成から

```
SPRING_PROFILES_ACTIVE
```

を

- `local`
- `dev`

へ変更して再起動する。

指定したプロファイルに応じて、

- 読み込まれるYAML
- 出力されるログファイル名

が切り替わることを確認した。

065-2.png

# ログレベルを環境ごとに切り替える

## 概要

ログレベルを環境ごとに変更することで、開発環境では詳細なログを、本番環境では必要最低限のログのみを出力できる。

例えば、

| 環境 | ログレベル |
|------|-----------|
| local | DEBUG |
| dev | DEBUG |
| prod | INFO または WARN |

と設定するのが一般的である。

---

## なぜ行うのか

開発環境では、

- Controller・Serviceの動作
- SQL
- パラメータ

などを確認するため、`DEBUG`ログが必要となる。

一方、本番環境では、`DEBUG`ログを大量に出力するとログ量が増えすぎるため、通常は`INFO`以上で運用する。

---

## このアプリケーションでは不要な理由

現在のこのアプリケーションでは、

- `application-local.yml`
- `application-dev.yml`

のどちらも開発環境として使用している。

そのため、

```
local : DEBUG
dev   : DEBUG
```

で十分であり、ログレベルを環境ごとに切り替える必要性は低い。

本番環境（`application-prod.yml`）を追加した際に導入すれば十分である。

---

# 所感

このアプリケーションは現在のところローカル環境での利用を前提としており、デプロイする予定もないため、設定の外部化によって変更する内容はそれほど多くなく、現時点で得られる恩恵も限定的である。

一方で、将来的に同様のアプリケーションをデプロイすることを見据え、その際のリハーサルも兼ねて今回実装を行った。

また、`logback-spring.xml`や`application.yml`などの設定ファイルの書き方にはまだ慣れていない部分もあったが、実際に実装してみると、仕組み自体は比較的シンプルで理解しやすいと感じた。

今回学んだ内容は、今後デプロイを前提としたアプリケーションを開発する際にも、そのまま活用できる知識になると感じた。

---

# 次にやること

- READMEの作成
- プロジェクト全体の振り返りログの作成

