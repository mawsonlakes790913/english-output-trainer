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
	
	@GetMapping("/study")
	public String getStudy(Model model,
						   HttpSession session,
						   @RequestParam(defaultValue = "0") int page){
		// ① Sessionになければ取得して保存
		//if (session.getAttribute("questions") == null) {
		//	List<Question> questions = studyService.getRandomQuestion();
		//	session.setAttribute("questions", questions);
		//}
		
		// ② Sessionからquestions取得
		List<Question> questions = (List<Question>) session.getAttribute("questions");
		
		// ③ page番目の問題を取り出す
		//Question question = questions.get(page);
		
		// /studyへの直接アクセスを禁ずる
	    if (questions == null) {
	        return "redirect:/";
	    }
		
		// ④ HTMLが必要な情報をModelへ格納
		setStudyModel(model, questions, page);

		// ⑤ study.htmlを返す
		return "study";
	}
	
	@GetMapping("/study/start")
	public String startStudy(Model model,
							 HttpSession session,
							 @RequestParam String mode) {
	    // 既存の学習状態を破棄
	    session.removeAttribute("questions");
	    session.removeAttribute("currentPage");
	    
	    //先に宣言
	    List<Question> questions;
	    
	    // 新しい問題セットを作成
	    if ("sequential".equals(mode)) {
	    	questions = studyService.getQuestion();
	    } else {
	    	questions = studyService.getRandomQuestion();
	    }

		session.setAttribute("questions", questions);
	    session.setAttribute("currentPage", 0);
	    
	    return "redirect:/study";
	}
	
	@GetMapping("/study/resume")
	public String resumeStudy(Model model,
							  HttpSession session
							  ) {
		// 中断していないならホームに戻す
		if (session.getAttribute("questions") == null) {
		    return "redirect:/";
		}
		// 中断時のページ情報を取得
		Integer page =
		        (Integer) session.getAttribute("currentPage");
		
		return "redirect:/study?page=" + page;
		
		// セッションに保存してあった問題リストを取得
		//List<Question> questions = (List<Question>) session.getAttribute("questions");
		
		// 出題再開点を取得
		//Question question = questions.get(page);
		
		// HTMLが必要な情報をModelへ格納
		//setStudyModel(model, questions, page);
		
		//for (Question q : questions) {
		//    System.out.print(q.getQuestionId() + " ");
		//}
		//System.out.println();
		//System.out.print("resume");
		
		//return "study";
	}
	
	@GetMapping("/study/complete")
	public String completeStudy(HttpSession session) {
		session.removeAttribute("questions");
		session.removeAttribute("currentPage");
		//System.out.print("complete");
		return "redirect:/complete";
	}
	
	@GetMapping("/study/suspend")
	public String suspendStudy(@RequestParam int page,
							    HttpSession session) {
		session.setAttribute("currentPage", page);
		//System.out.print("suspend");
		return "redirect:/";
	}	
	
	@GetMapping("/study/quit")
	public String quitStudy(HttpSession session) {
		session.removeAttribute("questions");
		session.removeAttribute("currentPage");
		//System.out.print("quit");
		return "redirect:/";
	}	
	
	@PostMapping("/study/evaluation")
	public String postEvaluation(@AuthenticationPrincipal UserDetails loginUser,
	        				   @RequestParam Long questionId,
	        				   @RequestParam Evaluation evaluation,
	        				   @RequestParam Integer page) {
		evaluationService.updateEvaluation(
		        loginUser.getUsername(),
		        questionId,
		        evaluation);
		return "redirect:/study?page=" + (page + 1);
	}
	
	private void setStudyModel(
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
	

}