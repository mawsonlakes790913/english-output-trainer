package com.example.demo.advice;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class GlobalControllerAdvice {

    @ModelAttribute
    public void addLoginUser(
            Model model,
            @AuthenticationPrincipal UserDetails loginUser) {
    	model.addAttribute("loginUser",
    	            loginUser != null ? loginUser.getUsername() : "ゲスト");
    }
}