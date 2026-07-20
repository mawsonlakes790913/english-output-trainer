package com.example.demo.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
	
	@GetMapping("/admin/question/list")
	public String getQuestionList() {
		return "admin/question/list";
	}
	
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
}