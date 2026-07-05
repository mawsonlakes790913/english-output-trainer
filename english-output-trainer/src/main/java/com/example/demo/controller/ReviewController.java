package com.example.demo.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.entity.Users;
import com.example.demo.service.ReviewService;
import com.example.demo.service.UserServiceImpl;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ReviewController {
	
	private final UserServiceImpl userServiceImpl;
	private final ReviewService reviewService;
	
	@GetMapping("/review/menu")
	public String getReviewMenu(@AuthenticationPrincipal UserDetails loginUser, Model model) {
		
	    // user_id(文字列)からUsersを取得
	    Users user = userServiceImpl.getUserOne(loginUser.getUsername());

	    Long userId = user.getId();
	    
//	    long totalAdvanced = reviewService.countTotalAdvanced();
//	    long totalIntermediate = reviewService.countTotalIntermediate();
//	    long totalBeginner = reviewService.countTotalBeginner();

	    long evaluatedHard = reviewService.countEvaluatedHard(userId);
	    long evaluatedGood = reviewService.countEvaluatedGood(userId);
	    long evaluatedEasy = reviewService.countEvaluatedEasy(userId);
	    
//	    model.addAttribute("totalAdvanced", totalAdvanced);
//	    model.addAttribute("totalIntermediate", totalIntermediate);
//	    model.addAttribute("totalBeginner", totalBeginner);

	    model.addAttribute("evaluatedHard", evaluatedHard);
	    model.addAttribute("evaluatedGood", evaluatedGood);
	    model.addAttribute("evaluatedEasy", evaluatedEasy);
		
		return "/review/menu";
	}
}