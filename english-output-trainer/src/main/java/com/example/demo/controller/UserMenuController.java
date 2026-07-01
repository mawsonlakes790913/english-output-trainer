package com.example.demo.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.entity.Users;
import com.example.demo.service.UserServiceImpl;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
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

	    model.addAttribute("user", user);

	    return "user/profile";
	}
	
	//@GetMapping("/user/edit")
	//public String getUserEdit(
	//		@AuthenticationPrincipal UserDetails loginUser,
	//        Model model) {
	//	
	//	Users user = userServiceImpl.getUserOne(loginUser.getUsername());

	//    model.addAttribute("user", user);
	    
	//	return "user/edit";
	//}
	
	//@PostMapping("/user/edit")
	//public String postUserEdit() {
	//}
	
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