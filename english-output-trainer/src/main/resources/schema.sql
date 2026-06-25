DROP TABLE IF EXISTS study_history;
DROP TABLE IF EXISTS favorites;
DROP TABLE IF EXISTS question;
DROP TABLE IF EXISTS users;

CREATE TABLE users (
    user_id VARCHAR(20) PRIMARY KEY,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(20) NOT NULL
);

CREATE TABLE question (
    question_id BIGSERIAL PRIMARY KEY,
    japanese_text TEXT NOT NULL,
    english_text TEXT NOT NULL,
    alternative_answer TEXT,
    condition VARCHAR(100),
    difficulty VARCHAR(20) NOT NULL
);

CREATE TABLE favorites (
    user_id VARCHAR(20) NOT NULL,
    question_id BIGINT NOT NULL,

    PRIMARY KEY (user_id, question_id),

    CONSTRAINT fk_favorites_user
        FOREIGN KEY (user_id)
        REFERENCES users(user_id),

    CONSTRAINT fk_favorites_question
        FOREIGN KEY (question_id)
        REFERENCES question(question_id)
);

CREATE TABLE study_history (
    user_id VARCHAR(20) NOT NULL,
    question_id BIGINT NOT NULL,
    evaluation VARCHAR(10) NOT NULL,
    last_studied_at TIMESTAMP NOT NULL,

    PRIMARY KEY (user_id, question_id),

    CONSTRAINT fk_study_history_user
        FOREIGN KEY (user_id)
        REFERENCES users(user_id),

    CONSTRAINT fk_study_history_question
        FOREIGN KEY (question_id)
        REFERENCES question(question_id)
);