package com.example.demo.advice;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
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
    
    @ExceptionHandler(IllegalArgumentException.class)
    public String handleIllegalArgumentException(
            IllegalArgumentException e,
            Model model) {

        model.addAttribute("errorMessage", e.getMessage());

        return "error/error";
    }
}