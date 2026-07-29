package com.example.demo.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.Users;
import com.example.demo.service.AdminService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class AdminUserController {
	
	private final AdminService adminService;
	
	@GetMapping("/admin/list")
	public String getUserList(Model model) {
		List<Users> userList = adminService.getUsers();
		
		model.addAttribute("userList", userList);
		return "userList";
		
	}
	
	@PostMapping("/admin/delete")
	public String deleteUser(@RequestParam String userId,
							 Model model){
		adminService.deleteOneUser(userId);
		return "redirect:/admin/list";
	}
}