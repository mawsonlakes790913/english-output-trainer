package com.example.demo.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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
import com.example.demo.service.UserService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class FavoritesController {
	
	private final FavoritesService favoritesService;
	private final UserService userService;
	
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
								   @PageableDefault(page = 0, size = 50) Pageable pageable,
								   Model model) {
		Users user =
		        userService.getUserOne(loginUser.getUsername());

		Page<Question> favoritesList = favoritesService.getFavoritesList(user.getId(), pageable);
		
		//model.addAttribute("favoritesList", favoritesList);
		model.addAttribute("favoritesList", favoritesList.getContent());
		model.addAttribute("page", favoritesList);
		return "favorites/list";
	}

}