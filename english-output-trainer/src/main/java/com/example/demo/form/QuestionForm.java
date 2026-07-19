package com.example.demo.form;

import com.example.demo.entity.Difficulty;

import lombok.Data;

@Data
public class QuestionForm {

    private String japaneseText;

    private String englishText;

    private String alternativeAnswer;

    private String condition;

    private Difficulty difficulty;
}