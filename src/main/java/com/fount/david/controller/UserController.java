package com.fount.david.controller;

import java.security.Principal;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fount.david.model.User;
import com.fount.david.service.IUserService;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private IUserService service;

	@GetMapping("/login")
	public String showLogin() {
		return "user-login";
	}
	
	@GetMapping("/setup")
	public String setup(HttpSession session, Principal p) {

		// read current username
		String username = p.getName();

		// load user object
		User user = service.findByUsername(username).get();

		// store in HttpSession
		session.setAttribute("userOb", user);

		return "user-home";
	}
	
	
	@GetMapping("/profile")
	public String showProfile() {
		
		return "user-profile";
	}
	
	
	@GetMapping("/showPwdUpdate")
	public String showPwdUpdate() {
		
		return "user-password-update";
	}
	
	@PostMapping("/pwdUpdate")
	public String passwordUpdate(@RequestParam String password,
						HttpSession session, Model model) {
		//read current user from session
		User user = (User)session.getAttribute("userOb");
		
		//read userId
		Long userId = user.getId();
		
		//make service call
		service.updateUserPwd(password, userId);
		
		//TODO: Email Sending 
		
		model.addAttribute("message", "Password Updated!");
		
		return "user-password-update";
	}
}
