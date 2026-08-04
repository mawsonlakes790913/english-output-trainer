package com.example.demo.controller;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.entity.Users;
import com.example.demo.form.SignupForm;
import com.example.demo.service.SignupService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Controller
@Slf4j
@RequiredArgsConstructor
public class SignupController {
	
	private final SignupService signupService;
	
	@GetMapping("/signup/signup")
	public String getSignup(Model model, @ModelAttribute SignupForm form) {
		return "signup/signup";
	}
	
	@GetMapping("/signup/complete")
	public String getSignupComplete(){
		return "signup/complete";
	}
	
	@PostMapping("/signup")
	public String postSignup(Model model,
							 @ModelAttribute @Validated SignupForm form,
							 BindingResult bindingResult) {
		// ① 通常のバリデーションエラー確認
	    if (bindingResult.hasErrors()) {
	        return getSignup(model, form);
	    }

	    try {
	    	log.debug("ユーザー登録開始 userId={}", form.getUserId());
	    	
	    	Users users = new Users();

	    	users.setUserId(form.getUserId());
	    	users.setPassword(form.getPassword());
	    	
	        // ② Serviceの業務処理
	    	signupService.signup(users);

	    } catch (DuplicateKeyException e) {

	        // ③ Serviceで発生した重複エラーをBindingResultへ追加
	        bindingResult.rejectValue(
	                "userId",
	                "duplicate",
	                e.getMessage());

	        return getSignup(model, form);
	    }

	    return "redirect:/signup/complete";
	}
	// AOP動作確認用の例外
	//@GetMapping("/test-error")
	//public String testError() {

	    //userServiceImpl.testException();

	    //return "home";
	//}

	

}