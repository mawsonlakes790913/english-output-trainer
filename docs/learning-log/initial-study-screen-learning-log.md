# English Output Trainer 学習ログ

## 実装方針

今回はまず最低限動作するものを作ることを優先し、機能を極力絞った構成とした。

実装した画面は以下の2画面のみである。

- ホーム画面（home.html）
![ホーム画面1](../docs/images/initial-study-screen1.png)
- 学習画面（study.html）
![学習画面1](../docs/images/initial-study-screen2.png)
![学習画面2](../docs/images/initial-study-screen3.png)

学習機能も非常にシンプルな構成とし、あらかじめ登録した5問の問題を順番に表示するだけとした。

また、データアクセス方式については MyBatis と JPA のどちらを採用するか検討したが、今回は実装の容易さを優先して JPA を採用した。

---

## HomeController

HomeController はホーム画面を表示するだけの単純な役割であり、これまで学習した内容を基に特に迷うことなく実装できた。

現時点では Service や Repository を呼び出す処理もなく、

```java
@GetMapping("/")
public String getHome() {
    return "home";
}
```

程度のシンプルな構成である。

---

## Question.java

Question エンティティについても大きな問題はなかった。

テーブル名やカラム名とJava側の命名を対応させるため、

```java
@Column(name = "japanese_text")
```

などのアノテーションを付与している。

Spring Boot の設定次第では @Column を省略しても動作する可能性はあるが、可読性を考慮して明示的に残すことにした。

また、実装時に一度

```java
import org.springframework.data.annotation.Id;
```

を import してしまったためエラーとなった。

JPA の @Id は

```java
import jakarta.persistence.Id;
```

を使用する必要があることを再確認した。

---

## QuestionRepository.java

QuestionRepository も特に難しい点はなかった。

```java
public interface QuestionRepository
        extends JpaRepository<Question, Long> {
}
```

というシンプルな構成である。

一点だけ注意したのは主キー型である。

question_id は BIGSERIAL として定義しているため、

```java
JpaRepository<Question, Long>
```

としなければならない。

当初は Integer も考えたが、DB定義と合わせて Long を採用した。

---

## StudyServiceImpl

今回実装していて最も興味深かった点の一つがページネーション処理である。

同一画面内で問題を1問ずつ表示するため、Page・Pageable を利用して実装した。

当初は MyBatis の感覚で実装しようとしたため少し混乱した。

MyBatis の場合は、

```java
// ① 問題一覧取得
List<Question> questionList = repository.findAll(pageable);

// ② 総件数取得
long count = repository.count();

// ③ Page生成
return new PageImpl<Question>(questionList, pageable, count);
```

という流れで初めてページ情報付きのオブジェクトを生成できる。

しかし JPA の場合は、

```java
return repository.findAll(pageable);
```

だけで Page<Question> を返すことができる。

この違いは非常に印象的だった。

JPA は学習コストだけでなく実装量もかなり少なく済むことを実感した。

---

## StudyController

さすがに Controller 全体を何も見ずに実装することはできなかったが、これまで学習してきた内容を参考にしながら組み立てることができた。

今回は画面から入力値を受け取り Service や DB に渡す処理は存在しないため DTO は使用していない。

処理の流れは以下の通りである。

1. Serviceから問題一覧を取得
2. 1件分の問題を取得
3. Modelに格納
4. study.htmlへ遷移

というシンプルな構成で実装した。

---

## home.html

ホーム画面についても特に難しい点はなかった。

学習開始ボタンを設置し、

```html
<form th:action="@{/study}" method="get">
    <button type="submit">学習開始</button>
</form>
```

によって StudyController の GET 処理へ遷移させている。

---

## study.html

今回最も試行錯誤したのが study.html である。

### 問題表示

同一画面で問題を順番に表示するため、

```html
th:each="question : ${question}"
```

を利用した。

これが最適解かどうかは分からないが、現時点で学習済みの内容の中では最も実装しやすい方法だった。

---

### 条件・別解の表示制御

条件や別解はデータが存在する場合のみ表示するようにした。

例えば条件については、

```html
th:if="${question.condition != null}"
```

を利用した。

Thymeleaf の条件分岐を初めて実践的に利用できた。

---

### 解答表示ボタン

解答表示ボタンについては初見の仕様だった。

ボタンを押した際に画面遷移することなく、その場に解答を表示したかったため JavaScript を利用した。

```html
<button type="button" onclick="showAnswer()">
    解答を見る
</button>

<script>
function showAnswer() {
    document.getElementById("answerArea").style.display = "block";
}
</script>
```

JavaScript を使わずに実現する方法もあるかもしれないが、現時点ではこの方法が最も理解しやすかった。

---

### 次の問題ボタン

次の問題ボタンは問題が存在する場合のみ表示するようにした。

```html
<a th:if="${page.hasNext()}"
   th:href="@{/study(page=${page.number + 1},size=1)}">
```

ここでは Controller に対して再度 GET リクエストを送り、

- page
- size

の値を渡している。

これにより次の問題を取得できる。

今回初めて Page オブジェクトの

```java
page.hasNext()
page.number
```

などの機能を実践的に利用した。

---

## DB周りで苦戦した点

今回最も時間を使ったのは DB 初期化周りだった。

原因としては、

- question と questions のテーブル名不一致
- JPA の Entity 名との不一致
- schema.sql と data.sql の内容不一致
- spring.sql.init.mode=always の挙動理解不足

などが重なったためである。

特に、

```text
Index 0 out of bounds for length 0
```

というエラーは Controller の問題だと思っていたが、

実際には

```text
DBにデータが1件も存在しない
↓
Pageが空になる
↓
get(0)で例外発生
```

という流れだった。

今回の経験で、エラーメッセージだけを見るのではなく、

- 実際のSQL
- テーブル定義
- データ件数

を確認する重要性を学んだ。

---

## 所感

必要なフォルダ構成、パッケージ構成、ファイル構成については、これまでのインプットのおかげでかなり自力で組み立てることができた。

また、

- Controller
- Service
- Repository
- Entity
- HTML

がどのような順番で呼び出されるかについても以前より理解できていると感じた。

一方でコーディング、とくに HTML や Thymeleaf についてはまだ教科書を参考にしながら実装している状態であり、発展途上であると感じた。

しかしコードを見れば、

「そうそう、こうやるんだった」

と思い出せるようになっており、

「コードを読んでも何をしているのかわからない」

という状態は完全になくなった。

その点は大きな成長だと感じている。

---

## 次に実装すること

- 戻るボタンの追加
- やめるボタンの追加
- 学習終了画面の作成