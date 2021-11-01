package com.fount.david.service;

import java.util.Optional;

import com.fount.david.model.User;

public interface IUserService {

	public Long saveUser(User user);
	
	Optional<User> findByUsername(String username);
	
	void updateUserPwd(String encPwd, Long userId); 
}
