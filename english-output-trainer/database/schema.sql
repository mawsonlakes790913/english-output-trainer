-- ==========================================
-- English Output Trainer
-- Database Schema
-- PostgreSQL
-- ==========================================

CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    user_id VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(255) NOT NULL
);

CREATE TABLE question (
    question_id BIGSERIAL PRIMARY KEY,
    japanese_text VARCHAR(255) NOT NULL,
    english_text VARCHAR(255) NOT NULL,
    alternative_answer VARCHAR(255),
    condition VARCHAR(255),
    difficulty VARCHAR(255) NOT NULL
);

CREATE TABLE study_history (
    user_id BIGINT NOT NULL,
    question_id BIGINT NOT NULL,
    evaluation VARCHAR(255) NOT NULL,
    evaluation_updated_at TIMESTAMP NOT NULL,

    PRIMARY KEY (user_id, question_id),

    CONSTRAINT fk_study_history_user
        FOREIGN KEY (user_id)
        REFERENCES users(id),

    CONSTRAINT fk_study_history_question
        FOREIGN KEY (question_id)
        REFERENCES question(question_id)
);

CREATE TABLE favorites (
    user_id BIGINT NOT NULL,
    question_id BIGINT NOT NULL,
    created_at TIMESTAMP NOT NULL,

    PRIMARY KEY (user_id, question_id),

    CONSTRAINT fk_favorites_user
        FOREIGN KEY (user_id)
        REFERENCES users(id),

    CONSTRAINT fk_favorites_question
        FOREIGN KEY (question_id)
        REFERENCES question(question_id)
);