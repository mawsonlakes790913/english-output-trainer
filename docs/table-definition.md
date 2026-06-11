# テーブル定義書

## USER

### 概要

ユーザー情報を管理するテーブル。

| カラム名 | 意味 | PK | FK | データ型 | NOT NULL | UNIQUE | 備考 |
|----------|------|----|----|----------|-----------|---------|------|
| user_id | ユーザーID | ○ | - | VARCHAR(20) | ○ | ○ | ログインID |
| password | パスワード | - | - | VARCHAR(255) | ○ | - | ハッシュ化して保存 |

---

## PROBLEM

### 概要

英作文問題を管理するテーブル。

| カラム名 | 意味 | PK | FK | データ型 | NOT NULL | UNIQUE | 備考 |
|----------|------|----|----|----------|-----------|---------|------|
| problem_id | 問題ID | ○ | - | BIGINT | ○ | ○ | 自動採番 |
| japanese_text | 日本語文 | - | - | TEXT | ○ | - | 問題文 |
| english_text | 模範解答 | - | - | TEXT | ○ | - | 英文 |
| alternative_answer | 別解 | - | - | TEXT | - | - | 任意入力(NULL可) |
| condition | 文法条件 | - | - | VARCHAR(100) | - | - | to不定詞、動名詞など |
| difficulty | 難易度 | - | - | VARCHAR(20) | ○ | - | 初級・中級・上級 |

---

## FAVORITE

### 概要

ユーザーのお気に入り登録情報を管理するテーブル。

| カラム名 | 意味 | PK | FK | データ型 | NOT NULL | UNIQUE | 備考 |
|----------|------|----|----|----------|-----------|---------|------|
| user_id | ユーザーID | ○ | ○ | VARCHAR(20) | ○ | - | USER.user_id参照 |
| problem_id | 問題ID | ○ | ○ | BIGINT | ○ | - | PROBLEM.problem_id参照 |

### 主キー

```text
(user_id, problem_id)
```

---

## STUDY_HISTORY

### 概要

ユーザーの学習履歴および自己評価を管理するテーブル。

| カラム名 | 意味 | PK | FK | データ型 | NOT NULL | UNIQUE | 備考 |
|----------|------|----|----|----------|-----------|---------|------|
| user_id | ユーザーID | ○ | ○ | VARCHAR(20) | ○ | - | USER.user_id参照 |
| problem_id | 問題ID | ○ | ○ | BIGINT | ○ | - | PROBLEM.problem_id参照 |
| evaluation | 学習結果 | - | - | VARCHAR(10) | ○ | - | HARD / GOOD / EASY |
| last_studied_at | 最終学習日時 | - | - | TIMESTAMP | ○ | - | 最後に学習評価を登録した日時 |

### 主キー

```text
(user_id, problem_id)
```

---

# 外部キー一覧

| テーブル | カラム | 参照先 |
|----------|----------|----------|
| FAVORITE | user_id | USER.user_id |
| FAVORITE | problem_id | PROBLEM.problem_id |
| STUDY_HISTORY | user_id | USER.user_id |
| STUDY_HISTORY | problem_id | PROBLEM.problem_id |

---

# 補足

- USER と PROBLEM はマスタテーブルとして扱う。
- FAVORITE はユーザーと問題の多対多関係を管理する中間テーブルである。
- STUDY_HISTORY はユーザーごとの学習結果および復習判定情報を保持する。
- STUDY_HISTORY は問題ごとの最新の評価結果のみを保持する。
- evaluation が HARD または GOOD の問題は復習機能の出題対象となる。
- evaluation が EASY の問題は履歴として保存するが復習機能の出題対象には含めない。
- last_studied_at を利用し、一定期間経過した問題のみを復習対象として出題する。
- User と Problem の多対多関係は FAVORITE および STUDY_HISTORY によって管理する。
- パスワードは平文では保存せず、BCrypt 等によるハッシュ化を行う。