package com.example.demo.controller;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.entity.Users;
import com.example.demo.form.SignupForm;
import com.example.demo.service.UserServiceImpl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Controller
@Slf4j
@RequiredArgsConstructor
public class SignupController {
	
	private final UserServiceImpl userServiceImpl;
	private final ModelMapper modelMapper;
	
	@GetMapping("/signup")
	public String getSignup(Model model, @ModelAttribute SignupForm form) {
		return "signup";
	}
	
	@PostMapping("/signup")
	public String postSignup(Model model,
							 @ModelAttribute @Validated SignupForm form,
							 BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
            // NG：ユーザー登録画面に戻ります
            return getSignup(model, form); 
        }
		
		log.info(form.toString());
		
		// formをUsersクラスに変換
        Users users = modelMapper.map(form, Users.class);
        // ユーザー登録
        userServiceImpl.signup(users);
		return "redirect:/login";
	}
}