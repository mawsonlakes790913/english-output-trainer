package com.example.demo.controller;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.entity.Users;
import com.example.demo.form.EditPasswordForm;
import com.example.demo.form.EditUserIdForm;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserServiceImpl;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@Slf4j
public class UserMenuController {
	
	private final UserServiceImpl userServiceImpl;
	private final UserRepository repository;
	private final PasswordEncoder encoder;
	
	@GetMapping("/menu")
	public String getUserMenu() {
		return "userMenu";
	}
	
	@GetMapping("/user/profile")
	public String getUserProfile(
	        @AuthenticationPrincipal UserDetails loginUser,
	        Model model) {

	    Users user = userServiceImpl.getUserOne(loginUser.getUsername());
	    
//	    System.out.println(loginUser.getUsername());
//	    System.out.println(user);

	    model.addAttribute("user", user);

	    return "user/profile";
	}
	
	@GetMapping("/user/edit/userId")
	public String getEditUserId(
	        @AuthenticationPrincipal UserDetails loginUser,
	        Model model,
	        @ModelAttribute EditUserIdForm form) {

	    if (form.getUserId() == null) {
	        Users user = userServiceImpl.getUserOne(loginUser.getUsername());
	        form.setUserId(user.getUserId());
	    }

	    return "user/edit/userId";
	}
	
	@PostMapping("/user/edit/userId")
	public String postEditUserId(
	        @AuthenticationPrincipal UserDetails loginUser,
	        HttpSession session,
	        Model model,
	        @Validated @ModelAttribute EditUserIdForm form,
	        BindingResult bindingResult) {

	    if (bindingResult.hasErrors()) {
	        return getEditUserId(loginUser, model, form);
	    }

	    try {
	        userServiceImpl.updateUserId(
	                loginUser.getUsername(),
	                form.getUserId());

	    } catch (DuplicateKeyException e) {

	        bindingResult.rejectValue(
	                "userId",
	                "duplicate",
	                e.getMessage());

	        return getEditUserId(loginUser, model, form);
	    }

	    // ログアウト状態にする
	    SecurityContextHolder.clearContext();
	    session.invalidate();

	    return "redirect:/login";
	}

	
	@PostMapping("/user/delete")
	public String cancelMembership(
	        @AuthenticationPrincipal UserDetails loginUser,
	        HttpServletRequest request)
	        throws ServletException {

	    userServiceImpl.cancelMembership(loginUser.getUsername());

	    request.logout();

	    return "redirect:/user/canceled";
	    //return "redirect:/login";
	}

	@GetMapping("/user/canceled")
	public String getCanceled() {
		return "user/canceled";
	}
	
	@GetMapping("/user/edit/password")
	public String getEditPassword(
	        Model model,
	        @ModelAttribute EditPasswordForm form) {
		return "user/edit/password";
	}
	
	@PostMapping("/user/edit/password")
	public String postEditPassword(@AuthenticationPrincipal UserDetails loginUser,
	        				 HttpSession session,
	        				 Model model,
							 @ModelAttribute @Validated EditPasswordForm form,
							 BindingResult bindingResult) {
		
		
		// ① 通常のバリデーションエラー確認
	    if (bindingResult.hasErrors()) {
	        return getEditPassword(model, form);
	    }


	    try {
	    	log.info(form.toString());
	    	
	        // ② Serviceの業務処理
	        userServiceImpl.updateUserPassword(
	                loginUser.getUsername(),
	                form.getCurrentPassword(),
	                form.getNewPassword());

	    } catch (IllegalArgumentException e) {

	        // ③ Serviceで発生した重複エラーをBindingResultへ追加
	    	bindingResult.rejectValue(
	    	        "currentPassword",
	    	        "invalid",
	    	        e.getMessage());

	        return getEditPassword(model, form);
	    }

	    // ログアウト状態にする
	    SecurityContextHolder.clearContext();
	    session.invalidate();

	    return "redirect:/login";
	}
}