# リファクタリング・改善メモ

## 初回表示時のURLを統一

### 問題点

`study/question`および`review/question`の初回表示だけ

```
/study/question
/review/question
```

となり、2問目以降は

```
/study/question?page=1
/review/question?page=1
```

となっていた。

### 修正

開始画面からのリダイレクト先を`page=0`付きに変更した。

#### StudyController

```java
// return "redirect:/study/question";
return "redirect:/study/question?page=0";
```

#### ReviewController

```java
// return "redirect:/review/question";
return "redirect:/review/question?page=0";
```

### 修正後

初回からURLが

```
/study/question?page=0
/review/question?page=0
```

となり、ページ番号の表現を統一できた。