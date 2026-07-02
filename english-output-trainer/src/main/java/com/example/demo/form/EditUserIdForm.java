package com.example.demo.form;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class EditUserIdForm {
	@NotBlank
	@Length(min = 8, max = 20)
	@Pattern( regexp = "^[a-zA-Z0-9]+$")
	private String userId;
}