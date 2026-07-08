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

import com.example.demo.entity.Difficulty;
import com.example.demo.entity.Evaluation;
import com.example.demo.entity.Question;
import com.example.demo.entity.Users;
import com.example.demo.service.EvaluationService;
import com.example.demo.service.ReviewService;
import com.example.demo.service.UserServiceImpl;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ReviewController {
	
	private final UserServiceImpl userServiceImpl;
	private final ReviewService reviewService;
	private final EvaluationService evaluationService;
	
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
	
	@GetMapping("/review/question")
	public String getReviewQuestion(Model model,
			   					    HttpSession session,
			   					    @RequestParam(defaultValue = "0") int page) {
		
		// Sessionからquestions取得
		List<Question> questions = (List<Question>) session.getAttribute("reviewQuestions");

		// /questionへの直接アクセスを禁ずる
	    if (questions == null) {
	        return "redirect:review/menu";
	    }
		// HTMLが必要な情報をModelへ格納
		setReviewQuestionModel(model, questions, page);

		// study.htmlを返す
		return "review/question";	    
	}
	
	private void setReviewQuestionModel(
	        Model model,
	        List<Question> questions,
	        int page) {

	    Question question = questions.get(page);

	    model.addAttribute("question", question);
	    model.addAttribute("currentPage", page + 1);
	    model.addAttribute("totalPages", questions.size());
	    model.addAttribute("hasPrevious", page > 0);
	    model.addAttribute(
	            "hasNext",
	            page < questions.size() - 1);
	}

	
	@GetMapping("/review/start")
	public String getReviewStart(Model model,
							 HttpSession session,
							 @AuthenticationPrincipal UserDetails loginUser,
							 @RequestParam(name = "evaluations", required = false) 
									List<Evaluation> evaluations,
							 @RequestParam(name = "difficulties", required = false) 
									List<Difficulty> difficulties,
							 @RequestParam(name = "random", required = false)
									boolean random
							 ) {
	    // 既存の学習状態を破棄
	    session.removeAttribute("reviewQuestions");
	    session.removeAttribute("reviewCurrentPage");
	    
	    //先に宣言
	    List<Question> questions;
	    
	    // user_id(文字列)からUsersを取得
	    Users user = userServiceImpl.getUserOne(loginUser.getUsername());
	    Long userId = user.getId();
	    
	    // 新しい問題セットを作成
	    questions = reviewService.getQuestion(userId, evaluations, difficulties, random);

		session.setAttribute("reviewQuestions", questions);
	    session.setAttribute("reviewCurrentPage", 0);
	    
	    return "redirect:/review/question";
	}

	
	@GetMapping("/review/resume")
	public String getReviewResume(Model model,
							  HttpSession session
							  ) {
		// 中断していないならmenuに戻す
		if (session.getAttribute("reviewQuestions") == null) {
		    return "redirect:review/menu";
		}
		// 中断時のページ情報を取得
		Integer page =
		        (Integer) session.getAttribute("reviewCurrentPage");
		
		return "redirect:/review/question?page=" + page;
		
	}
	
	@GetMapping("/review/complete")
	public String completeReview(HttpSession session) {
		session.removeAttribute("reviewQuestions");
		session.removeAttribute("reviewCurrentPage");
		//System.out.print("complete");
		return "redirect:/complete";
	}
	
	@GetMapping("/review/suspend")
	public String suspendReview(@RequestParam int page,
							    HttpSession session) {
		session.setAttribute("reviewCurrentPage", page);
		//System.out.print("suspend");
		return "redirect:/";
	}	
	
	@GetMapping("/review/quit")
	public String quitReview(HttpSession session) {
		session.removeAttribute("reviewQuestions");
		session.removeAttribute("reviewCurrentPage");
		//System.out.print("quit");
		return "redirect:/";
	}	
	
	@PostMapping("/review/evaluation")
	public String postEvaluation(@AuthenticationPrincipal UserDetails loginUser,
	        				   @RequestParam Long questionId,
	        				   @RequestParam Evaluation evaluation,
	        				   @RequestParam Integer page,
	        				   HttpSession session) {
		evaluationService.updateEvaluation(
		        loginUser.getUsername(),
		        questionId,
		        evaluation);
		
		List<Question> questions =
			    (List<Question>) session.getAttribute("reviewQuestions");
		
		if (page + 1 >= questions.size()) {
		    return "redirect:/review/complete";
		}
		
		return "redirect:/review/question?page=" + (page + 1);
	}
}