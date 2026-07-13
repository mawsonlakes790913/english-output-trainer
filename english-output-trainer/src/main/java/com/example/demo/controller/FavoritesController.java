package com.example.demo.controller;

import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.entity.Question;
import com.example.demo.entity.Users;
import com.example.demo.service.FavoritesService;
import com.example.demo.service.UserServiceImpl;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class FavoritesController {
	
	private final FavoritesService favoritesService;
	private final UserServiceImpl userServiceImpl;
	
	@PostMapping("/favorite/toggle")
	@ResponseBody
	public boolean toggleFavorite(
	        @RequestParam Long questionId,
	        @AuthenticationPrincipal UserDetails loginUser) {

	    return favoritesService.toggleFavorite(
	            loginUser.getUsername(),
	            questionId);
	}
	
	@GetMapping("/favorites/list")
	public String getFavoritesList(@AuthenticationPrincipal UserDetails loginUser,
								   Model model) {
		Users user =
		        userServiceImpl.getUserOne(loginUser.getUsername());

		List<Question> favoritesList = favoritesService.getFavoritesList(user.getId());
		
		model.addAttribute("favoritesList", favoritesList);
		return "favorites/list";
	}
}