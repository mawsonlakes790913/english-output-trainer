package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpSession;

@Controller

public class HomeController {
	
	@GetMapping("/")
	public String getHome(Model model, HttpSession session) {
	    boolean resumable =
	            session.getAttribute("questions") != null;
	    model.addAttribute("resumable", resumable);
	    
	    // hello.htmlを表示
		return "home";
	}
	
	// 通常学習をクリック
	@GetMapping("/study/menu")
	public String getStudyMenu() {
		
		return "study/menu";
	}
	
//	// 復習をクリック
//	@GetMapping("/review/menu")
//	public String getReviewMenu(
//	        @AuthenticationPrincipal UserDetails loginUser,
//	        Model model) {
//
//	    model.addAttribute(
//	            "hardCount",
//	            studyService.getHardCount(loginUser.getUsername()));
//
//	    model.addAttribute(
//	            "goodCount",
//	            studyService.getGoodCount(loginUser.getUsername()));
//
//	    model.addAttribute(
//	            "favoriteCount",
//	            favoriteService.getFavoriteCount(loginUser.getUsername()));
//
//	    model.addAttribute(
//	            "hardGoodCount",
//	            studyService.getHardGoodCount(loginUser.getUsername()));
//
//	    return "review/menu";
//	}
	
	
}