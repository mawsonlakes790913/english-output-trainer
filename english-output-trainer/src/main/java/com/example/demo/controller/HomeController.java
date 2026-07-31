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

	    // ログアウトメッセージ
	    String logoutMessage = (String) session.getAttribute("logoutMessage");
	    if (logoutMessage != null) {
	        model.addAttribute("successMessage", logoutMessage);
	        session.removeAttribute("logoutMessage");
	    }

	    return "home";
	}
	
}