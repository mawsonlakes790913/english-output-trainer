package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class UserMenuController {
	
	@GetMapping("/menu")
	public String getUserMenu() {
		return "userMenu";
	}
	
}