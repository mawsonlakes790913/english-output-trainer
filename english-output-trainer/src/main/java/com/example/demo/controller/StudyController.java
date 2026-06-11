package com.example.demo.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.entity.Question;
import com.example.demo.service.StudyServiceImpl;

import lombok.RequiredArgsConstructor;




@Controller
@RequiredArgsConstructor
public class StudyController {
	
	private final StudyServiceImpl studyService;
	
	@GetMapping("/study")
	public String getStudy(Model model,
	        @PageableDefault(page = 0, size = 1) Pageable pageable) {

	    Page<Question> questionPage = studyService.getQuestion(pageable);

	    
	    Question question = questionPage.getContent().get(0);

	    model.addAttribute("question", question);
	    model.addAttribute("page", questionPage);

	    return "study";
	}
	
}