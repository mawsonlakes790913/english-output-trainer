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
	
	@GetMapping("/study/complete")
	public String completeStudy(Model model,
							    HttpSession session) {
		session.removeAttribute("questions");
		return "redirect:/complete";
	}
	
	@GetMapping("/study/quit")
	public String quitStudy(Model model,
							    HttpSession session) {
		session.removeAttribute("questions");
		return "redirect:/";
	}	
}