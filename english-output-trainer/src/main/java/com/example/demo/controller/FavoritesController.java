package com.example.demo.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.entity.Users;
import com.example.demo.service.FavoritesService;
import com.example.demo.service.UserAccountService;
import com.example.demo.service.UserService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class FavoritesController {
	
	private final FavoritesService favoritesService;
	private final UserService userService;
	private final UserAccountService userAccountService;
	
	@PostMapping("/favorite/toggle")
	@ResponseBody
	public boolean toggleFavorite(
	        @RequestParam Long questionId,
	        @AuthenticationPrincipal UserDetails loginUser) {
		
		// ユーザー情報を取得
		Users user = userAccountService.getUserOne(loginUser.getUsername());

	    return favoritesService.toggleFavorite(
	            user,
	            questionId);
	}
	

}