package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.form.SignupForm;

import lombok.extern.slf4j.Slf4j;


@Controller
@Slf4j
public class SignupController {
	
	@GetMapping("/signup")
	public String getSignup(Model model, @ModelAttribute SignupForm form) {
		return "signup";
	}
	
	@PostMapping("/signup")
	public String postSignup(@ModelAttribute SignupForm form) {
		log.info(form.toString());
		return "redirect:/login";
	}
}