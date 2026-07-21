package com.example.demo.dto;

import lombok.Data;

@Data
public class QuestionDto {
	
	private String questionId;
	
	private String japaneseText;
	
	private String englishText;
	
	private String alternativeAnswer;
	
	private String difficulty;
	
	private String condition;
}