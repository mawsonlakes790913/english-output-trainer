```mermaid
erDiagram

    USER ||--o{ FAVORITE
    Question ||--o{ FAVORITE

    USER ||--o{ STUDY_HISTORY
    Question ||--o{ STUDY_HISTORY

    USER {
        varchar user_id PK
        varchar password
    }

    Question {
        bigint question_id PK
        text japanese_text
        text english_text
        text alternative_answer
        varchar condition
        varchar difficulty
    }

    FAVORITE {
        varchar user_id FK
        bigint question_id FK
    }

    STUDY_HISTORY {
        varchar user_id FK
        bigint question_id FK
        varchar evaluation
        timestamp last_studied_at
    }
```