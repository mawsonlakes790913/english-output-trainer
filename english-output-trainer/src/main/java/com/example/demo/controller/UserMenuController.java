package com.example.demo.controller;

import java.util.List;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.dto.PaginationDto;
import com.example.demo.dto.UserQuestionListDto;
import com.example.demo.entity.Difficulty;
import com.example.demo.entity.Evaluation;
import com.example.demo.entity.FavoriteCondition;
import com.example.demo.entity.StudyCondition;
import com.example.demo.entity.Users;
import com.example.demo.form.EditPasswordForm;
import com.example.demo.form.EditUserIdForm;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.AdminService;
import com.example.demo.service.PaginationService;
import com.example.demo.service.QuestionService;
import com.example.demo.service.UserAccountService;
import com.example.demo.service.UserQuestionService;
import com.example.demo.service.UserService;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@Slf4j
public class UserMenuController {
	
	private final UserService userService;
	private final UserRepository repository;
	private final PasswordEncoder encoder;
	private final AdminService adminService;
	private final PaginationService paginationService;
	private final QuestionService questionService;
	private final UserAccountService userAccountService;
	private final UserQuestionService userQuestionService;
	
	@GetMapping("/menu")
	public String getUserMenu() {
		return "userMenu";
	}
	
	@GetMapping("/user/profile")
	public String getUserProfile(
	        @AuthenticationPrincipal UserDetails loginUser,
	        Model model) {

	    Users user = userAccountService.getUserOne(loginUser.getUsername());
	    
//	    System.out.println(loginUser.getUsername());
//	    System.out.println(user);

	    model.addAttribute("user", user);

	    return "user/profile";
	}
	
	@GetMapping("/user/edit/userId")
	public String getEditUserId(
	        @AuthenticationPrincipal UserDetails loginUser,
	        Model model,
	        @ModelAttribute EditUserIdForm form) {

	    if (form.getUserId() == null) {
	        Users user = userAccountService.getUserOne(loginUser.getUsername());
	        form.setUserId(user.getUserId());
	    }

	    return "user/edit/userId";
	}
	
	@PostMapping("/user/edit/userId")
	public String postEditUserId(
	        @AuthenticationPrincipal UserDetails loginUser,
	        HttpSession session,
	        Model model,
	        @Validated @ModelAttribute EditUserIdForm form,
	        BindingResult bindingResult) {

	    if (bindingResult.hasErrors()) {
	        return getEditUserId(loginUser, model, form);
	    }

	    try {
	        userService.updateUserId(
	                loginUser.getUsername(),
	                form.getUserId());

	    } catch (DuplicateKeyException e) {

	        bindingResult.rejectValue(
	                "userId",
	                "duplicate",
	                e.getMessage());

	        return getEditUserId(loginUser, model, form);
	    }

	    // ログアウト状態にする
	    SecurityContextHolder.clearContext();
	    session.invalidate();

	    return "redirect:/login";
	}

	
	@PostMapping("/user/delete")
	public String cancelMembership(
	        @AuthenticationPrincipal UserDetails loginUser,
	        HttpServletRequest request)
	        throws ServletException {

	    userService.cancelMembership(loginUser.getUsername());

	    request.logout();

	    return "redirect:/user/canceled";
	    //return "redirect:/login";
	}

	@GetMapping("/user/canceled")
	public String getCanceled() {
		return "user/canceled";
	}
	
	@GetMapping("/user/edit/password")
	public String getEditPassword(
	        Model model,
	        @ModelAttribute EditPasswordForm form) {
		return "user/edit/password";
	}
	
	@PostMapping("/user/edit/password")
	public String postEditPassword(@AuthenticationPrincipal UserDetails loginUser,
	        				 HttpSession session,
	        				 Model model,
							 @ModelAttribute @Validated EditPasswordForm form,
							 BindingResult bindingResult) {
		
		
		// ① 通常のバリデーションエラー確認
	    if (bindingResult.hasErrors()) {
	        return getEditPassword(model, form);
	    }


	    try {
	    	log.info(form.toString());
	    	
	        // ② Serviceの業務処理
	        userService.updateUserPassword(
	                loginUser.getUsername(),
	                form.getCurrentPassword(),
	                form.getNewPassword());

	    } catch (IllegalArgumentException e) {

	        // ③ Serviceで発生した重複エラーをBindingResultへ追加
	    	bindingResult.rejectValue(
	    	        "currentPassword",
	    	        "invalid",
	    	        e.getMessage());

	        return getEditPassword(model, form);
	    }

	    // ログアウト状態にする
	    SecurityContextHolder.clearContext();
	    session.invalidate();

	    return "redirect:/login";
	}
	
//	@GetMapping("/user/question/list")
//	public String getUserQuestionList(@AuthenticationPrincipal UserDetails loginUser,
//									  @PageableDefault(page = 0, size = 50) Pageable pageable,
//									  Model model) {
//		
//		Users user = userServiceImpl.getUserOne(loginUser.getUsername());
//		Long userId = user.getId();
//		
//		Page<UserQuestionListDto> userQuestionList = userServiceImpl.getUserQuestionList(userId, pageable);
//		PaginationDto pagination = adminService.createPagination(userQuestionList);
//
//		
//		model.addAttribute("questionList", userQuestionList.getContent());
//		model.addAttribute("page", userQuestionList);
//		model.addAttribute("pagination", pagination);
//		
//		return "/user/question/list";
//		
//	}
	
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