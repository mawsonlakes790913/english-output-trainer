package com.example.demo.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.service.FavoritesService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class FavoritesController {
	
	private final FavoritesService favoritesService;
	
	@PostMapping("/favorite/toggle")
	@ResponseBody
	public boolean toggleFavorite(
	        @RequestParam Long questionId,
	        @AuthenticationPrincipal UserDetails loginUser) {

	    return favoritesService.toggleFavorite(
	            loginUser.getUsername(),
	            questionId);
	}
}