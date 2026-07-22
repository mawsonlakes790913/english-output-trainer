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

import com.example.demo.dto.PaginationDto;
import com.example.demo.dto.QuestionDto;
import com.example.demo.entity.Difficulty;
import com.example.demo.entity.Question;
import com.example.demo.entity.Users;
import com.example.demo.form.QuestionForm;
import com.example.demo.service.AdminService;
import com.example.demo.service.UserServiceImpl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@Slf4j
public class AdminController {
	
	private final UserServiceImpl userServiceImpl;
	private final AdminService adminService;
	
	@GetMapping("/admin")
	public String getAdmin() {
		return "admin/admin";
	}
	
	@GetMapping("/admin/menu")
	public String getAdminMenu() {
		return "admin/menu";
	}
	
	@GetMapping("/admin/list")
	public String getUserList(Model model) {
		List<Users> userList = userServiceImpl.getUsers();
		
		model.addAttribute("userList", userList);
		return "userList";
		
	}
	
	@PostMapping("/admin/delete")
	public String deleteUser(@RequestParam String userId,
							 Model model){
		userServiceImpl.deleteUserOne(userId);
		return "redirect:/admin/list";
	}
	
//	@GetMapping("/admin/question/list")
//	public String getQuestionList() {
//		return "admin/question/list";
//	}
	
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
		
		return "redirect:/admin/question/list";

	}
	
	@GetMapping("/admin/question/list")
	public String getAdminQuestionList(@PageableDefault(page = 0, size = 50) Pageable pageable,
								       Model model) {

		Page<Question> allQuestionList = adminService.getAllQuestions(pageable);
		PaginationDto pagination = adminService.createPagination(allQuestionList);
		
		model.addAttribute("questionList", allQuestionList.getContent());
		model.addAttribute("page", allQuestionList);
		model.addAttribute("pagination", pagination);
		model.addAttribute("conditions",
		        adminService.getAllConditions());
		
		return "/admin/question/list";
	}
	
	@GetMapping("/admin/question/search")
	public String getAdminQuestionSearch(@PageableDefault(page = 0, size = 50) Pageable pageable,
										 @RequestParam(required = false) List<Difficulty> difficulties,
										 @RequestParam(required = false) List<String> conditions,
										 @RequestParam(required = false) String keyword,
		       							 Model model) {

		Page<Question> allFilteredQuestionList = adminService.getFilteredQuestions(difficulties,
																				   conditions,
																				   keyword,
																				   pageable);
		PaginationDto pagination = adminService.createPagination(allFilteredQuestionList);
		
		model.addAttribute("questionList", allFilteredQuestionList.getContent());
		model.addAttribute("page", allFilteredQuestionList);
		model.addAttribute("pagination", pagination);
		model.addAttribute("conditions",
		        adminService.getAllConditions());
		model.addAttribute("selectedDifficulties", difficulties);
		model.addAttribute("selectedConditions", conditions);
		model.addAttribute("keyword", keyword);
		
		return "/admin/question/list";
		
	}
	
	@GetMapping("/admin/question/edit")
	public String getAdminQuestionEdit(@RequestParam long questionId,
									    Model model) {
		
		QuestionDto question = adminService.getOneQuestion(questionId);

		model.addAttribute("question", question);
		
		return "/admin/question/edit";
		
	}
	
	@PostMapping("/admin/question/edit")
	public String postAdminQuestionEdit(		
			@RequestParam long questionId,
			@ModelAttribute @Validated QuestionForm form,
			BindingResult bindingResult,
			Model model) {
		
		// 通常のバリデーションエラー確認
	    if (bindingResult.hasErrors()) {
	    	model.addAttribute("question", form);
	        return "admin/question/edit";
	    }

		adminService.updateOneQuestion(questionId, form);
		
		return "redirect:/admin/question/list";
		
	}
	
	
}