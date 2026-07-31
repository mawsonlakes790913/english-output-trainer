package com.example.demo.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.dto.PaginationDto;
import com.example.demo.dto.QuestionDto;
import com.example.demo.entity.Difficulty;
import com.example.demo.entity.Question;
import com.example.demo.form.QuestionForm;
import com.example.demo.service.AdminService;
import com.example.demo.service.PaginationService;
import com.example.demo.service.QuestionService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@Slf4j
public class AdminQuestionController {
	
	private final AdminService adminService;
	private final PaginationService paginationService;
	private final QuestionService questionService;
	
	@GetMapping("/admin/question/add")
	public String getQuestionAdd(
	        Model model) {

	    model.addAttribute(
	            "questionForm",
	            new QuestionForm());

	    return "admin/question/add";
	}
	
	@PostMapping("/admin/question/add")
	public String postQuestionAdd(
			@ModelAttribute @Validated QuestionForm form,
			BindingResult bindingResult,
			Model model) {
		
		//① 通常のバリデーションエラー確認
	    if (bindingResult.hasErrors()) {
	        return "admin/question/add";
	    }

		
		log.info("問題登録 {}", form);  
		adminService.addQuestion(form);
		
		return "redirect:/admin/question/search";

	}
	
	
	@GetMapping("/admin/question/search")
	public String getAdminQuestionSearch(@PageableDefault(page = 0, size = 50) Pageable pageable,
										 @RequestParam(required = false) List<Difficulty> difficulties,
										 @RequestParam(required = false) String condition,
										 @RequestParam(required = false) String keyword,
		       							 Model model) {
		
		Page<Question> allFilteredQuestionList = adminService.getFilteredQuestions(difficulties,
																				   condition,
																				   keyword,
																				   pageable);
		PaginationDto pagination = paginationService.createPagination(allFilteredQuestionList);
		
		long start = allFilteredQuestionList.getNumber() * allFilteredQuestionList.getSize() + 1;
		long end = start + allFilteredQuestionList.getNumberOfElements() - 1;

		model.addAttribute("start", start);
		model.addAttribute("end", end);
		model.addAttribute("total", allFilteredQuestionList.getTotalElements());
		
		model.addAttribute("questionList", allFilteredQuestionList.getContent());
		model.addAttribute("page", allFilteredQuestionList);
		model.addAttribute("pagination", pagination);
		model.addAttribute("conditions",
				questionService.getAllConditions());
		model.addAttribute("selectedDifficulties", difficulties);
		model.addAttribute("selectedConditions", condition);
		model.addAttribute("keyword", keyword);
		
		return "/admin/question/list";
		
	}
	
	@GetMapping("/admin/question/edit")
	public String getAdminQuestionEdit(@RequestParam long questionId,
									    Model model) {
		
		QuestionDto question = adminService.getOneQuestion(questionId);

		model.addAttribute("questionForm", question);
		
		return "/admin/question/edit";
		
	}
	
	@PostMapping("/admin/question/edit")
	public String postAdminQuestionEdit(		
			@RequestParam long questionId,
			@ModelAttribute("questionForm") @Validated QuestionForm form,
			BindingResult bindingResult,
			Model model) {
		
		// 通常のバリデーションエラー確認
	    if (bindingResult.hasErrors()) {
	    	model.addAttribute("questionForm", form);
	        return "admin/question/edit";
	    }

		adminService.updateOneQuestion(questionId, form);
		
		return "redirect:/admin/question/search";
		
	}
	
	@PostMapping("/admin/question/delete")
	public String postAdminQuestionDelete(
			@RequestParam long questionId,
			RedirectAttributes redirectAttributes) {
		
		adminService.deleteOneQuestion(questionId);
		
	    redirectAttributes.addFlashAttribute(
	            "successMessage",
	            "問題を削除しました。");
	    
		return "redirect:/admin/question/search";
	}
	
}