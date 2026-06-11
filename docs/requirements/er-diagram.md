```mermaid
erDiagram

    USER ||--o{ FAVORITE
    PROBLEM ||--o{ FAVORITE

    USER ||--o{ STUDY_HISTORY
    PROBLEM ||--o{ STUDY_HISTORY

    USER {
        varchar user_id PK
        varchar password
    }

    PROBLEM {
        bigint problem_id PK
        text japanese_text
        text english_text
        text alternative_answer
        varchar condition
        varchar difficulty
    }

    FAVORITE {
        varchar user_id FK
        bigint problem_id FK
    }

    STUDY_HISTORY {
        varchar user_id FK
        bigint problem_id FK
        varchar evaluation
        timestamp last_studied_at
    }
```