package com.example.demo.controller;

import java.util.List;

//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.Question;
import com.example.demo.service.StudyServiceImpl;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;



@Controller
@RequiredArgsConstructor
public class StudyController {
	
	private final StudyServiceImpl studyService;
	//private List<Question> questions;
	
	@GetMapping("/study")
	public String getStudy(Model model,
						   HttpSession session,
						   @RequestParam(defaultValue = "0") int page){
		// ① Sessionになければ取得して保存
		if (session.getAttribute("questions") == null) {
			List<Question> questions = studyService.getRandomQuestion();
			session.setAttribute("questions", questions);
		}
		
		// ② Sessionからquestions取得
		List<Question> questions = (List<Question>) session.getAttribute("questions");
		
		// ③ page番目の問題を取り出す
		Question question = questions.get(page);
				
		// ④ HTMLが必要な情報をModelへ格納
		model.addAttribute("question", question);
		model.addAttribute("currentPage", page + 1);
		model.addAttribute("totalPages", questions.size());
		model.addAttribute("hasPrevious", page > 0);
		model.addAttribute(
		        "hasNext",
		        page < questions.size() - 1);
		

		// ⑤ study.htmlを返す
		return "study";
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
		
		// セッションに保存してあった問題リストを取得
		List<Question> questions = (List<Question>) session.getAttribute("questions");
		
		// 出題再開点を取得
		Question question = questions.get(page);
				
		model.addAttribute("question", question);
		model.addAttribute("currentPage", page + 1);
		model.addAttribute("totalPages", questions.size());
		model.addAttribute("hasPrevious", page > 0);
		model.addAttribute(
		        "hasNext",
		        page < questions.size() - 1);
		
		//for (Question q : questions) {
		//    System.out.print(q.getQuestionId() + " ");
		//}
		//System.out.println();
		//System.out.print("resume");
		
		return "study";
	}
	
	@GetMapping("/study/complete")
	public String completeStudy(Model model,
							    HttpSession session) {
		session.removeAttribute("questions");
		//System.out.print("complete");
		return "redirect:/complete";
	}
	
	@GetMapping("/study/suspend")
	public String suspendStudy(Model model,
								@RequestParam int page,
							    HttpSession session) {
		session.setAttribute("currentPage", page);
		//System.out.print("suspend");
		return "redirect:/";
	}	
	
	@GetMapping("/study/quit")
	public String quitStudy(Model model,
							    HttpSession session) {
		session.removeAttribute("questions");
		//System.out.print("quit");
		return "redirect:/";
	}	
}