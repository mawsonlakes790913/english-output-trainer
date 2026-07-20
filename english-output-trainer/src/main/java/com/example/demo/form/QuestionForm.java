package com.example.demo.form;

import org.hibernate.validator.constraints.Length;

import com.example.demo.entity.Difficulty;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class QuestionForm {
	
	@NotBlank
	@Length(max = 100)
    private String japaneseText;

	@NotBlank
	@Length(max = 200)
    @Pattern(
    		regexp = "^[a-zA-Z0-9 .,!?:;'\"()/%$&+-]+$"
        )
    private String englishText;
	
	@Length(max = 200)
    @Pattern(
    		regexp = "^[a-zA-Z0-9 .,!?:;'\"()/%$&+-]*$"
        )
    private String alternativeAnswer;

	@Length(max = 20)
    private String condition;

	@NotNull
    private Difficulty difficulty;
}