package com.example.demo.dto;

import com.example.demo.entity.Difficulty;
import com.example.demo.entity.Evaluation;

public interface UserQuestionListDto {

    Long getQuestionId();

    String getJapaneseText();

    String getEnglishText();

    String getAlternativeAnswer();

    String getCondition();

    Difficulty getDifficulty();

    Evaluation getEvaluation();

    boolean isFavorite();
}