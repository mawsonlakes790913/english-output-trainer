package com.example.demo.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller

public class HomeController {
	
	@GetMapping("/")
	public String getHome() {
	    // hello.htmlを表示
		return "home";
	}
	
}