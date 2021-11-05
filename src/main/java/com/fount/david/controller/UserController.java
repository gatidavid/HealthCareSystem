package com.fount.david.controller;

import java.security.Principal;
import java.util.Optional;

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
import com.fount.david.util.MyMailUtil;
import com.fount.david.util.UserUtil;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private IUserService service;
	
	@Autowired
	private UserUtil userUtil;
	
	@Autowired
	private MyMailUtil mailUtil; 

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
	@GetMapping("/showForgot")
	public String showForgetPassword() {
		
		return "user-new-pwd-gen";
	}
	
	
	@PostMapping("/genNewPwd")
	public String genNewPwd(
			@RequestParam String email,
			Model model) 
	{
		Optional<User> opt =  service.findByUsername(email);
		if(opt.isPresent()) {
			//read user object
			User user = opt.get();
			
			//Generate new Password
			String pwd = userUtil.generatePassword();
			
			//encode and update in DB
			service.updateUserPwd(pwd, user.getId());
			
			//send message to UI
			model.addAttribute("message", "Password Updated! Check your Inbox!!");
			
			//send email to user
			if(user.getId()!=null)
				new Thread(new Runnable() {
					public void run() {
						String text = "YOUR USERNAME IS: " + user.getUsername() +", AND NEW PASSWORD IS "+ pwd;
						mailUtil.send(user.getUsername(), "PASWORD UPDATED!", text);
					}
				}).start();
			
		} else { // if user not present
			model.addAttribute("message", "User Not Found!");
		}
		return "user-new-pwd-gen";
	}
	
}
