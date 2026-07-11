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

import com.example.demo.dto.StudyMenuDto;
import com.example.demo.entity.Difficulty;
import com.example.demo.entity.Evaluation;
import com.example.demo.entity.Question;
import com.example.demo.service.EvaluationService;
import com.example.demo.service.StudyServiceImpl;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class StudyController {
	
	private final StudyServiceImpl studyService;
	//private List<Question> questions;
	private final EvaluationService evaluationService;
	private final QuestionModelUtil questionModelUtil;

	
	@GetMapping("/study/menu")
	public String getStudyMenu(Model model) {

		StudyMenuDto menu = studyService.countStudyQuestions();
	    model.addAttribute("studyMenu", menu);

	    return "study/menu";
	}
	
	@GetMapping("/study/start")
	public String getStudyStart(
	        HttpSession session,
	        @RequestParam(name = "difficulty") Difficulty difficulty,
	        @RequestParam(name = "start") int start,
	        @RequestParam(name = "random") boolean random) {
		
	    // 既存の学習状態を破棄
	    session.removeAttribute("questions");
	    session.removeAttribute("currentPage");
	    
	    //問題セットを取得
	    List<Question> questions = studyService.getQuestions(difficulty, start, random);

		session.setAttribute("questions", questions);
	    session.setAttribute("currentPage", 0);
	    
	    return "redirect:/study/question";
	    
	}
	
	@GetMapping("/study/question")
	public String getStudyQuestion(Model model,
								   HttpSession session,
								   @RequestParam(defaultValue = "0") int page) {
		// Sessionからquestions取得
		List<Question> questions = (List<Question>) session.getAttribute("studyQuestions");
		
		// /questionへの直接アクセスを禁ずる
	    if (questions == null) {
	        return "redirect:study/menu";
	    }
	    
		// HTMLが必要な情報をModelへ格納
	    questionModelUtil.setQuestionModel(model, questions, page);
		
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
		session.removeAttribute("studyQuestions");
		session.removeAttribute("studyCurrentPage");
		//System.out.print("complete");
		return "redirect:/complete";
	}
	
	@GetMapping("/study/suspend")
	public String getStudySuspend(@RequestParam int page,
							    HttpSession session) {
		session.setAttribute("studyCurrentPage", page);
		//System.out.print("suspend");
		return "redirect:/";
	}	
	
	@GetMapping("/study/quit")
	public String getStudyQuit(HttpSession session) {
		session.removeAttribute("studyQuestions");
		session.removeAttribute("studyCurrentPage");
		//System.out.print("quit");
		return "redirect:/";
	}	
	
	@PostMapping("/study/evaluation")
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
			    (List<Question>) session.getAttribute("studyQuestions");
		
		if (page + 1 >= questions.size()) {
		    return "redirect:/study/complete";
		}
		
		return "redirect:/study?page=" + (page + 1);
	}
	
}
	
