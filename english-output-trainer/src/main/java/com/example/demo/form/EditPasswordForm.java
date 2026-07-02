package com.example.demo.form;

import org.hibernate.validator.constraints.Length;

import com.example.demo.validator.PasswordMatch;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@PasswordMatch(
	    passwordFieldName = "newPassword",
	    passwordConfirmFieldName = "newPasswordConfirm"
	)

@Data
public class EditPasswordForm {
    // パスワード変更用
	@NotBlank
    private String currentPassword;

	@NotBlank
	@Length(min = 12, max = 30)
	@Pattern( regexp = "^[a-zA-Z0-9]+$")
	private String newPassword;
	
	//@NotBlank
	//@Length(min = 12, max = 30)
	//@Pattern( regexp = "^[a-zA-Z0-9]+$")	
	private String newPasswordConfirm;
}