# 0059 残りのバックエンドのリファクタリング
（Repository、Entity、DTO、Formなど）

これまで実装してきたバックエンド全体を見直し、Repository・Entity・DTO・Formなどを中心にリファクタリングを実施する。

目的は以下のとおりである。

- クエリの整理
- メソッド名の見直し
- 未使用コードの削除
- Entity設計の見直し
- DTO・Formの簡素化
- パッケージ構成の整理

---

# Repository編

## 目的

Repositoryについて、以下の観点から見直しを行う。

- クエリの整理
- メソッド名の見直し
- 重複クエリの削除
- JPQLとNative SQLの使い分け
- Repositoryごとの責務整理

---

## FavoritesRepository

リファクタリング不要。

---

## QuestionRepository

以下のメソッドについて確認した。

```java
countByDifficulty(...)
countNewQuestions(...)
findAllByOrderByQuestionIdDesc(...)
findFilteredQuestions(...)
```

これらは責務・命名ともに適切であり、修正不要と判断した。

---

### findDistinctConditions()

このメソッドのみNative SQLではなくJPQLで実装されていた。

```java
List<String> findDistinctConditions()
```

当時JPQLを採用した明確な理由は残っていなかった。

ただし、

- クエリが非常にシンプル
- Native SQLへ変更するメリットも特にない

ため、そのまま残すことにした。

（詳細は反省点に記載）

---

### getQuestion()

メソッド名が曖昧である。

実際に行っている処理は

> 指定難易度の問題を指定位置から100件取得する

ことである。

Repositoryでは取得系メソッドを`find...`で統一するため、

```text
getQuestion
↓

findQuestionsByDifficulty
```

へ変更した。

---

### getNewQuestions()

こちらも命名が曖昧である。

実際には

> 指定ユーザーの未学習問題を難易度別に取得する

処理であるため、

```text
getNewQuestions
↓

findUnlearnedQuestionsByUserIdAndDifficulty
```

へ変更した。

---

### getFilteredUserQuestionList()

Repositoryでは取得系を`find...`で統一するため、

```text
getFilteredUserQuestionList
↓

findFilteredUserQuestionList
```

へ変更した。

---

### getUserQuestionList()

調査した結果、

どのクラスからも参照されていない未使用メソッドであることが判明した。

そのため削除した。

---

## 一部メソッド名の修正

**commit**

```text
refactor: rename repository methods for consistency
```

変更内容

```text
getQuestion
→ findQuestionsByDifficulty

getNewQuestions
→ findUnlearnedQuestionsByUserIdAndDifficulty

getFilteredUserQuestionList
→ findFilteredUserQuestionList
```

これに伴い、Repositoryを呼び出しているServiceも修正した。

---

## 未使用メソッドの削除

**commit**

```text
refactor: remove unused getUserQuestionList method
```

未使用となっていた

```java
getUserQuestionList()
```

を削除した。

---

## StudyHistoryRepository

Repositoryとしては

```
get...
```

より

```
find...
```

の方が命名として自然である。

また、

通常学習との区別を明確にするため、

Reviewをメソッド名へ含めた。

---

### メソッド名の修正

**commit**

```text
refactor: rename review repository methods
```

変更内容

```text
getQuestions
↓

findReviewQuestions

countQuestions
↓

countReviewQuestions
```

これに伴い、Repositoryを利用しているServiceも修正した。

---

## UserRepository

リファクタリング不要。

---

# Entity編

## Entity同士の関係を整理

FavoritesはUsersとQuestionを結ぶ中間Entityである。

関係は次のようになる。

```text
Users
    ↓
List<Favorites>
    ↓
Favorites
    ↓
Users
```

```text
Question
    ↓
List<Favorites>
    ↓
Favorites
    ↓
Question
```

つまり、

- Users ⇔ Favorites
- Question ⇔ Favorites

は互いに参照し合う**双方向関連（Bidirectional Association）**となっている。

---

## 双方向関連とは

双方向関連とは、

**関連するEntity同士がお互いを参照できる関係**である。

例えば、

```text
Users
    ↓
List<Favorites>

Favorites
    ↓
Users
```

では

- UsersからFavoritesを取得できる
- FavoritesからUsersを取得できる

QuestionとFavoritesも同様である。

---

## 循環参照による無限再帰

問題となるのは、

Lombokの`@Data`が自動生成する`toString()`である。

例えば、

```java
System.out.println(user);
```

を実行すると、

```
Users.toString()

↓

Favorites.toString()

↓

Users.toString()

↓

・・・
```

という循環参照が発生し、

無限再帰となる可能性がある。

---

## 今回のEntity

| Entity | Lombok |
|--------|--------|
| Question | @Getter @Setter |
| Favorites | @Data |
| Users | @Data |

Questionは`@Data`ではないため対象外である。

---

## 対策

**commit**

```text
fix: prevent recursive toString in Users entity
```

Usersクラスへ

```java
@OneToMany(mappedBy = "user")
@ToString.Exclude
private List<Favorites> favorites;
```

を追加した。

これにより、

```
Users.toString()

↓

favoritesは出力しない

↓

終了
```

となり、

循環参照を防止できる。

`Favorites`側を修正する必要はなく、

片側で参照を断ち切れば十分である。

---

# その他のクラス

## GlobalControllerAdvice

リファクタリング不要。

---

# DTO編

## NewStudyCountDto

**commit**

```text
refactor: simplify DTOs using Lombok
```

手書きのGetter/Setterを削除し、

Lombok（`@Data`または`@Getter`・`@Setter`）へ置き換えた。

---

## StudyMenuDto

**commit**

```text
refactor: simplify DTOs using Lombok
```

こちらもGetter/SetterをLombokへ置き換えた。

---

## Range

**commit**

```text
refactor: make Range immutable and move it to value package
```

### Setterの削除

`Range`は

```java
new Range(start, end)
```

で生成した後、

値を書き換えることはない。

そのためSetterは不要である。

また、

`start`・`end`を`final`とし、

不変オブジェクト（Immutable Object）として扱うよう修正した。

---

### パッケージ構成の見直し

当初はDTOパッケージへ配置していた。

しかし、

`Range`は

- 範囲という概念を表現する
- `getDisplayText()`という振る舞いを持つ

クラスであり、

DTOではなく**値オブジェクト（Value Object）**である。

そのため、

新たに

```text
value
```

パッケージを作成し、

`Range`を移動した。

これに伴い、

```text
StudyMenuDto
StudyService
```

のimportも修正した。

---

## UserQuestionListDto

リファクタリング不要。

---

# Form編

## AdminUserForm

リファクタリング不要。

---

## EditUserIdForm

リファクタリング不要。

---

## QuestionForm

リファクタリング不要。

---

## EditPasswordForm

**commit**

```text
fix: require confirmation password fields
```

```java
newPasswordConfirm
```

へ

```java
@NotBlank
```

を追加した。

---

## SignupForm

### passwordConfirm

**commit**

```text
fix: require confirmation password fields
```

```java
passwordConfirm
```

へ

```java
@NotBlank
```

を追加した。

---

### ageの削除

**commit**

```text
refactor: remove unused age field from SignupForm
```

使用しなくなった

```java
private Integer age;
```

を削除した。

---

### なぜ@NotBlankが必要なのか

現在の実装でも動作はする。

しかし、

責務を明確に分けるため、

- `@PasswordMatch`
  - パスワードが一致しているか
- `@NotBlank`
  - 入力されているか

をそれぞれ担当させる設計とした。

---

# util編

リファクタリング不要。

---

# validator編

リファクタリング不要。

---

# 全体のリファクタリングを終えて

今回のリファクタリングでは、新機能の追加は行わず、既存コードの品質向上に重点を置いた。

特に、

- Repositoryの命名統一
- 未使用コードの削除
- Entity設計の見直し
- DTOの簡素化
- 値オブジェクトの導入
- Formバリデーションの整理

などを実施したことで、コード全体の可読性と保守性が向上した。

また、Entity同士の双方向関連やLombokによる循環参照など、これまで曖昧だった設計についても理解を深めることができた。

---

# 反省点

`QuestionRepository.findDistinctConditions()`では、

Native SQLではなくJPQLを採用していた。

しかし、

リファクタリング中に

「なぜここだけJPQLなのか」

という疑問が生じ、

Git履歴や過去の学習ログを調査したものの、

採用理由を確認することはできなかった。

（削除済みの`getFavoritesList()`については明確な理由が残っていた。）

今回は修正せず、そのまま残すことにした。

今回の経験から、

実装前に

- 原則としてNative SQLを使用する
- JPQLを採用する場合は理由を学習ログへ残す

など、

実装方針を明文化しておく重要性を実感した。

---

# 次にやること

実装し忘れていた細かい機能を追加し、アプリケーションを最終仕上げする。

例

- 問題削除機能
- 戻るボタンの追加
- その他細かなUI・UX改善
```