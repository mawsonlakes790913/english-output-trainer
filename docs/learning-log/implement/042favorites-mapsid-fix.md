# お気に入り登録ボタンが動作しない

## 現象

お気に入り登録ボタンが正常に動作しない。

正確には、

- 色付き（登録済み）の状態では解除できる
- 色なし（未登録）の状態では登録できない

という状態になっていた。

---

## 原因調査

コンソールログを確認すると、以下の例外が発生していた。

```text
attempted to assign id from null one-to-one property
[com.example.demo.entity.Favorites.question]
```

また、スタックトレースを見ると

```text
FavoritesService.toggleFavorite(FavoritesService.java:41)
```

で `favoritesRepository.save()` を実行した際に例外が発生していることが分かった。

---

## 原因

Hibernate が

> Favoritesエンティティの `question` が `null` のまま保存されようとしている

と判断していた。

つまり問題はJavaScriptではなく、**EntityクラスとServiceクラスの整合性**にあった。

---

## なぜエラーになったのか

041でFavoritesクラスを次のように修正した。

```java
@ManyToOne
@MapsId("userId")
@JoinColumn(name = "user_id")
private Users user;

@ManyToOne
@MapsId("questionId")
@JoinColumn(name = "question_id")
private Question question;
```

この実装により、Hibernateは

```java
favorite.getUser();
favorite.getQuestion();
```

から複合キーを取得するようになった。

しかし、お気に入り登録機能を実装した当時の保存処理は次のままだった。

```java
Favorites favorite = new Favorites();
favorite.setFavoritesKey(key);
favorite.setCreatedAt(LocalDateTime.now());

favoritesRepository.save(favorite);
```

このコードは、Favoritesクラスに `user` と `question` が存在しなかった頃の実装である。

そのため、`@MapsId` を導入した現在では

- user
- question

がどちらも `null` の状態となり、

```text
attempted to assign id from null one-to-one property
```

という例外が発生してしまう。

なお、DELETE処理は

```java
favoritesRepository.deleteById(key);
```

のみで完結しており、今回のEntity構成変更の影響を受けないため問題なく動作していた。

---

# FavoritesServiceクラスのtoggleFavorite修正

**commit**

```bash
git commit -m "fix: set User and Question when saving Favorites"
```

## 修正前

```java
public boolean toggleFavorite(String loginUser, long questionId) {

    FavoritesKey key = createFavoritesKey(loginUser, questionId);

    Optional<Favorites> optionalFavorites =
            favoritesRepository.findByFavoritesKey(key);

    if (optionalFavorites.isEmpty()) {

        Favorites favorite = new Favorites();
        favorite.setFavoritesKey(key);
        favorite.setCreatedAt(LocalDateTime.now());

        favoritesRepository.save(favorite);
        return true;

    } else {

        favoritesRepository.deleteById(key);
        return false;
    }
}
```

---

## 修正後

```java
public boolean toggleFavorite(String loginUser, long questionId) {

    Users user = userServiceImpl.getUserOne(loginUser);

    FavoritesKey key = createFavoritesKey(loginUser, questionId);

    Optional<Favorites> optionalFavorites =
            favoritesRepository.findByFavoritesKey(key);

    if (optionalFavorites.isEmpty()) {

        Question question = new Question();
        question.setQuestionId(questionId);

        Favorites favorite = new Favorites();
        favorite.setFavoritesKey(key);
        favorite.setUser(user);
        favorite.setQuestion(question);
        favorite.setCreatedAt(LocalDateTime.now());

        favoritesRepository.save(favorite);
        return true;

    } else {

        favoritesRepository.deleteById(key);
        return false;
    }
}
```

---

## 修正内容

従来は `FavoritesKey` のみを設定して保存していた。

修正後は、

- Usersを取得
- Questionを生成（questionIdのみ保持）
- Favoritesに関連エンティティを設定

してから保存するように変更した。

```java
favorite.setUser(user);
favorite.setQuestion(question);
```

これにより、Hibernateは `user` と `question` から複合キーを正しく取得できるようになった。

---

## なぜQuestionも設定する必要があるのか

`Favorites` エンティティでは `@MapsId` を使用している。

そのため、Hibernateは

```java
favorite.getUser();
favorite.getQuestion();
```

から複合キーを生成する。

つまり、

```java
favorite.setFavoritesKey(key);
```

だけでは不十分であり、

```java
favorite.setUser(user);
favorite.setQuestion(question);
```

も必ず設定しなければならない。

---

## Questionオブジェクトについて

```java
Question question = new Question();
question.setQuestionId(questionId);
```

この `Question` オブジェクトは

- questionIdのみ保持
- その他のフィールドはすべてnull

という状態である。

しかし、Favoritesテーブルへ保存する際に必要なのは `question_id` のみなので、この実装で問題なく動作する。

---

# 実行結果

以下すべての画面で正常動作を確認した。

- study/question.html
- review/question.html
- favorites/list.html

ハートボタンで

- お気に入り登録
- お気に入り解除

の双方が正常に行えることを確認した。

---

# 所感

今回の不具合は、Entityクラスの構成を途中で変更したことにより発生した。

Favoritesエンティティに `@MapsId` を導入したことで、保存処理側も新しいEntity構成に合わせて修正する必要があった。

今回は影響範囲がFavoritesServiceだけだったため修正は比較的容易だったが、実際の開発ではEntityの変更がRepository・Service・Controller・画面表示など多方面へ波及することも少なくない。

仕様やEntity設計を途中で変更すること自体は避けられない場面もあるが、そのたびに既存コードへの影響調査や修正コストが発生することを改めて実感した。

---

# 次にやること

study/menuで

- 中級
- 上級

を選択した際に正しく問題が取得できない不具合を修正する。