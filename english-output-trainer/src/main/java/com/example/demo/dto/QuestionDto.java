package com.example.demo.dto;

import lombok.Data;

@Data
public class QuestionDto {

    private Long id;
    private String japaneseText;
    private String englishText;
    private String alternativeAnswer;
    private String condition;
    private String difficulty;
}