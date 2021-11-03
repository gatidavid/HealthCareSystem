package com.fount.david.runner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.fount.david.constants.UserRoles;
import com.fount.david.model.User;
import com.fount.david.service.IUserService;
import com.fount.david.util.MyMailUtil;
import com.fount.david.util.UserUtil;

@Component
public class MasterAccountSetupRunner implements CommandLineRunner {

	@Value("${master.user.name}")
	private String displayName;
	
	@Value("${master.user.email}")
	private String username;
	
	@Autowired
	private MyMailUtil mailUtil;
	
	
	@Autowired
	private IUserService userService;
	
	@Autowired
	private UserUtil userUtil;
	
	@Override
	public void run(String... args) throws Exception {
		
		if(!userService.findByUsername(username).isPresent()) {
			
			String pwd = userUtil.generatePassword();
	
			User user = new User();
			user.setDisplayName(displayName);
			user.setUsername(username);
			user.setPassword(pwd);
			user.setRole(UserRoles.ADMIN.name());

			Long userId =userService.saveUser(user);
			
			if(userId !=null) {
				
				new Thread(
						new Runnable() {
							
							@Override
							public void run() {
								String text = "Your name is "+username+
										", password is "+pwd;
								mailUtil.send(username, 
											  "Admin Account Created ",
											   text);
							}
						}
						).start();
			}
		}

	}

}
