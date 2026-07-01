package com.example.demo.controller;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.entity.Users;
import com.example.demo.form.EditForm;
import com.example.demo.service.UserServiceImpl;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class UserMenuController {
	
	private final UserServiceImpl userServiceImpl;
	
	@GetMapping("/menu")
	public String getUserMenu() {
		return "userMenu";
	}
	
	@GetMapping("/user/profile")
	public String getUserProfile(
	        @AuthenticationPrincipal UserDetails loginUser,
	        Model model) {

	    Users user = userServiceImpl.getUserOne(loginUser.getUsername());
	    
	    System.out.println(loginUser.getUsername());
	    System.out.println(user);

	    model.addAttribute("user", user);

	    return "user/profile";
	}
	
	@GetMapping("/user/edit")
	public String getUserEdit(
	        @AuthenticationPrincipal UserDetails loginUser,
	        Model model,
	        @ModelAttribute EditForm form) {

	    if (form.getUserId() == null) {
	        Users user = userServiceImpl.getUserOne(loginUser.getUsername());
	        form.setUserId(user.getUserId());
	    }

	    return "user/edit";
	}
	
	@PostMapping("/user/edit")
	public String postUserEdit(
	        @AuthenticationPrincipal UserDetails loginUser,
	        HttpSession session,
	        Model model,
	        @Validated @ModelAttribute EditForm form,
	        BindingResult bindingResult) {

	    if (bindingResult.hasErrors()) {
	        return getUserEdit(loginUser, model, form);
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

	        return getUserEdit(loginUser, model, form);
	    }

	    // ★ここでログアウト状態にする
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
}