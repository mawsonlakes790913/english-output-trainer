package com.example.demo.form;

import org.hibernate.validator.constraints.Length;

import com.example.demo.validator.PasswordMatch;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@PasswordMatch(passwordFieldName = "password",
			   passwordConfirmFieldName = "passwordConfirm")

@Data
public class SignupForm {
	
	
	@NotBlank
	@Length(min = 8, max = 20)
	@Pattern( regexp = "^[a-zA-Z0-9]+$")
	private String userId;
	
	@Min(0)
	@Max(120)
	private Integer age;
	
	@NotBlank
	@Length(min = 12, max = 30)
	@Pattern( regexp = "^[a-zA-Z0-9]+$")
	private String password;
	
	@NotBlank
	private String passwordConfirm;
}