package com.example.demo.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.entity.Evaluation;
import com.example.demo.entity.Users;
import com.example.demo.service.EvaluationService;
import com.example.demo.service.UserAccountService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class EvaluationController {
	
	private final UserAccountService userAccountService;
	private final EvaluationService evaluationService;
	
	@PostMapping("/evaluation/toggle")
	@ResponseBody
	public void toggleEvaluation(@AuthenticationPrincipal UserDetails loginUser,
	        				   @RequestParam Long questionId,
	        				   @RequestParam Evaluation evaluation) {
		
		// ユーザー情報を取得
		Users user = userAccountService.getUserOne(loginUser.getUsername());
		
		evaluationService.updateEvaluation(
		        user,
		        questionId,
		        evaluation);
		
	}
	
}