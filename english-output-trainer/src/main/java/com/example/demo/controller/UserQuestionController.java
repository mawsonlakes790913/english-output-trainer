package com.example.demo.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.dto.PaginationDto;
import com.example.demo.dto.UserQuestionListDto;
import com.example.demo.entity.Difficulty;
import com.example.demo.entity.Evaluation;
import com.example.demo.entity.FavoriteCondition;
import com.example.demo.entity.StudyCondition;
import com.example.demo.entity.Users;
import com.example.demo.service.PaginationService;
import com.example.demo.service.QuestionService;
import com.example.demo.service.UserAccountService;
import com.example.demo.service.UserQuestionService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class UserQuestionController {
	
	private final PaginationService paginationService;
	private final QuestionService questionService;
	private final UserAccountService userAccountService;
	private final UserQuestionService userQuestionService;
	
	@GetMapping("/user/question/search")
	public String getUserQuestionSearch(
	        @AuthenticationPrincipal UserDetails loginUser,
	        @PageableDefault(page = 0, size = 50) Pageable pageable,
	        @RequestParam(required = false) List<Difficulty> difficulties,
	        @RequestParam(required = false) List<Evaluation> evaluations,
	        @RequestParam(required = false) StudyCondition studyCondition,
	        @RequestParam(required = false) FavoriteCondition favoriteCondition,
	        @RequestParam(required = false) List<String> conditions,
	        @RequestParam(required = false, defaultValue = "") String keyword,
	        Model model) {

	    Users user = userAccountService.getUserOne(loginUser.getUsername());
	    Long userId = user.getId();

	    // 検索（パラメータが未指定ならService側で全件扱い）
	    Page<UserQuestionListDto> questionList =
	    		userQuestionService.getFilteredUserQuestionList(
	                    userId,
	                    difficulties,
	                    evaluations,
	                    studyCondition,
	                    favoriteCondition,
	                    conditions,
	                    keyword,
	                    pageable);

	    PaginationDto pagination =
	    		paginationService.createPagination(questionList);

	    // 一覧
	    model.addAttribute("questionList", questionList.getContent());
	    model.addAttribute("page", questionList);
	    model.addAttribute("pagination", pagination);

	    // 条件一覧
	    model.addAttribute("conditions", questionService.getAllConditions());

	    // 検索条件を画面へ戻す
	    model.addAttribute("selectedDifficulties", difficulties);
	    model.addAttribute("selectedEvaluations", evaluations);
	    model.addAttribute("selectedStudyCondition", studyCondition);
	    model.addAttribute("selectedFavoriteCondition", favoriteCondition);
	    model.addAttribute("selectedConditions", conditions);
	    model.addAttribute("keyword", keyword);

	    return "user/question/list";
	}
	
}