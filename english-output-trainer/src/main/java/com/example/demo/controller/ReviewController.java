package com.example.demo.controller;

import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.entity.Difficulty;
import com.example.demo.entity.Evaluation;
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
	public String getReviewMenu() {

	    return "review/menu";
	}
	
	@GetMapping("/review/count")
	@ResponseBody
	public long getReviewMenu(@AuthenticationPrincipal UserDetails loginUser,
								@RequestParam(name = "evaluations", required = false) 
									List<Evaluation> evaluations,
								@RequestParam(name = "difficulties", required = false) 
									List<Difficulty> difficulties,
								Model model) {
		
	    // user_id(文字列)からUsersを取得
	    Users user = userServiceImpl.getUserOne(loginUser.getUsername());
	    Long userId = user.getId();
	    
	    // 出題数を返す
	    return reviewService.countReviewQuestions(
	            userId,
	            evaluations,
	            difficulties);
	}
}