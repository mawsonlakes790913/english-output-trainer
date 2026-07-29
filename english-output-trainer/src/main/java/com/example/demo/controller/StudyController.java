package com.example.demo.controller;

import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.dto.NewStudyCountDto;
import com.example.demo.dto.StudyMenuDto;
import com.example.demo.entity.Difficulty;
import com.example.demo.entity.Evaluation;
import com.example.demo.entity.Question;
import com.example.demo.entity.Users;
import com.example.demo.service.EvaluationService;
import com.example.demo.service.FavoritesService;
import com.example.demo.service.StudyService;
import com.example.demo.service.UserAccountService;
import com.example.demo.util.QuestionModelUtil;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class StudyController {
	
	private final StudyService studyService;
	private final EvaluationService evaluationService;
	private final QuestionModelUtil questionModelUtil;
	private final FavoritesService favoritesService;	
	private final UserAccountService userAccountService;

	
	@GetMapping("/study/menu")
	public String getStudyMenu(@AuthenticationPrincipal UserDetails loginUser,
							   HttpSession session,
							   Model model) {
		
		// 通常問題数を取得
		StudyMenuDto menu = studyService.countStudyQuestions();
		model.addAttribute("studyMenu", menu);
		
	    // 未学習問題数を取得
	    if (loginUser != null) {
	    Users user = getLoginUser(loginUser);
		NewStudyCountDto count = studyService.countNewStudyQuestions(user.getId());
		model.addAttribute("newQuestioncount", count);
	    }
	    
	    // セッションから情報を取得
	    List<Question> questions =
	            (List<Question>) session.getAttribute("studyQuestions");

	    Integer currentPage =
	            (Integer) session.getAttribute("studyCurrentPage");
	    
	    // 中断したデータがあるか判定
	    boolean canResume = questions != null && currentPage != null;
	    
	    // 中断したデータ情報を返す
	    model.addAttribute("canResume", canResume);

	    if (canResume) {
		    model.addAttribute("currentPage", currentPage);
		    model.addAttribute("totalCount", questions.size());
	    } 

	    return "study/menu";
	}
	
	@GetMapping("/study/start")
	public String getStudyStart(
	        HttpSession session,
	        @RequestParam(required = false) Integer beginnerRange,
	        @RequestParam(required = false) Integer intermediateRange,
	        @RequestParam(required = false) Integer advancedRange,
	        @RequestParam(name = "random") boolean random,
	        RedirectAttributes redirectAttributes
	        ) {
		
	    int selectedCount = 0;

	    if (beginnerRange != null) selectedCount++;
	    if (intermediateRange != null) selectedCount++;
	    if (advancedRange != null) selectedCount++;
	    
	    if (selectedCount != 1) {
	        redirectAttributes.addFlashAttribute(
	                "errorMessage",
	                "出題範囲を1つ選択してください。");
	        return "redirect:/study/menu";
	    }
		
	    Difficulty difficulty;
	    int start;
		
	    if (beginnerRange != null) {
	        difficulty = Difficulty.BEGINNER;
	        start = beginnerRange;
	    } else if (intermediateRange != null) {
	        difficulty = Difficulty.INTERMEDIATE;
	        start = intermediateRange;
	    } else if (advancedRange != null) {
	        difficulty = Difficulty.ADVANCED;
	        start = advancedRange;
	    } else {
	        return "redirect:/study/menu";
	    }
		
	    // 既存の学習状態を破棄
	    clearStudySession(session);
	    
	    //問題セットを取得
	    List<Question> questions = studyService.getQuestions(difficulty, start, random);

		session.setAttribute("studyQuestions", questions);
	    session.setAttribute("studyCurrentPage", 0);
	    
	    return "redirect:/study/question?page=0";	    
	}
	
	@GetMapping("/study/new/start")
	public String getStudyNewStart(
	        HttpSession session,
	        @AuthenticationPrincipal UserDetails loginUser,
	        @RequestParam(name = "difficulties", required = false) 
			List<Difficulty> difficulty
	        ) {
		
	    // 既存の学習状態を破棄
		clearStudySession(session);
	    
	    //先に宣言
	    List<Question> questions;
	    
	    // user_id(文字列)からUsersを取得
	    Users user = getLoginUser(loginUser);
	    Long userId = user.getId();
	    
	    //問題セットを取得
	    questions = studyService.getNewQuestions(userId, difficulty);

		session.setAttribute("studyQuestions", questions);
	    session.setAttribute("studyCurrentPage", 0);
	    
	    return "redirect:/study/question?page=0";	    
	}
	
	@GetMapping("/study/question")
	public String getStudyQuestion(Model model,
								   HttpSession session,
								   @RequestParam(defaultValue = "0") int page,
								   @AuthenticationPrincipal UserDetails loginUser) {
		// Sessionからquestions取得
		List<Question> questions = (List<Question>) session.getAttribute("studyQuestions");
		
		// /questionへの直接アクセスを禁ずる
	    if (questions == null) {
	        return "redirect:study/menu";
	    }
	    
	    // 現在表示する問題を取得
	    Question question = questions.get(page);
	    
		// HTMLが必要な情報をModelへ格納
	    questionModelUtil.setQuestionModel(model, questions, page);
	    
	    // ログインしている場合だけお気に入り判定
	    if (loginUser != null) {
	        boolean isFavorite = favoritesService.isFavorite(
	        		getLoginUser(loginUser),
	                question.getQuestionId());

	        model.addAttribute("isFavorite", isFavorite);
	    }
		
		return "study/question";
	}
	
	
	@GetMapping("/study/resume")
	public String getStudyResume(Model model,
							  HttpSession session
							  ) {
		// 中断していないならmenuに戻す
		if (session.getAttribute("studyQuestions") == null) {
		    return "redirect:/study/menu";
		}
		// 中断時のページ情報を取得
		Integer page =
		        (Integer) session.getAttribute("studyCurrentPage");
		
		return "redirect:/study/question?page=" + page;
		
	}
	
	@GetMapping("/study/complete")
	public String getStudyComplete(HttpSession session) {
		clearStudySession(session);
		//System.out.print("complete");
		return "redirect:/complete";
	}
	
	@GetMapping("/study/suspend")
	public String getStudySuspend(@RequestParam int page,
							    HttpSession session) {
		session.setAttribute("studyCurrentPage", page);
		return "redirect:/";
	}	
	
	@GetMapping("/study/quit")
	public String getStudyQuit(HttpSession session) {
		clearStudySession(session);
		return "redirect:/";
	}	
	
	@PostMapping("/study/evaluation")
	public String postEvaluation(@AuthenticationPrincipal UserDetails loginUser,
	        				   @RequestParam Long questionId,
	        				   @RequestParam Evaluation evaluation,
	        				   @RequestParam Integer page,
	        				   HttpSession session) {
		
		// ユーザー情報を取得
		Users user = getLoginUser(loginUser);
	
		evaluationService.updateEvaluation(
		        user,
		        questionId,
		        evaluation);
		
		List<Question> questions =
			    (List<Question>) session.getAttribute("studyQuestions");
		
		if (page + 1 >= questions.size()) {
		    return "redirect:/study/complete";
		}
		
		return "redirect:/study/question?page=" + (page + 1);
	}
	
//	@PostMapping("/evaluation/toggle")
//	@ResponseBody
//	public void toggleEvaluation(@AuthenticationPrincipal UserDetails loginUser,
//	        				   @RequestParam Long questionId,
//	        				   @RequestParam Evaluation evaluation) {
//		
//		// ユーザー情報を取得
//		Users user = getLoginUser(loginUser);
//		
//		evaluationService.updateEvaluation(
//		        user,
//		        questionId,
//		        evaluation);
//		
//	}
	
	private Users getLoginUser(UserDetails loginUser) {
		return userAccountService.getUserOne(loginUser.getUsername());
	}

	private void clearStudySession(HttpSession session) {
	    session.removeAttribute("studyQuestions");
	    session.removeAttribute("studyCurrentPage");
	}
	
}
	
