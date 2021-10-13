package com.fount.david.runner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.fount.david.constants.UserRoles;
import com.fount.david.model.User;
import com.fount.david.service.IUserService;
import com.fount.david.util.UserUtil;

@Component
public class MasterAccountSetupRunner implements CommandLineRunner {

	@Value("${master.user.name}")
	private String displayName;
	
	@Value("${master.user.email}")
	private String username;
	
	@Autowired
	private IUserService userService;
	
	@Autowired
	private UserUtil userUtil;
	
	@Override
	public void run(String... args) throws Exception {
		
		if(!userService.findByUsername(username).isPresent()) {
			
			User user = new User();
			user.setDisplayName(displayName);
			user.setUsername(username);
			user.setPassword(userUtil.generatePassword());
			user.setRole(UserRoles.ADMIN.name());
			userService.saveUser(user);

			// TODO : Email part pending
		}

	}

}
