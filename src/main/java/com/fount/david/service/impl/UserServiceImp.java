package com.fount.david.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fount.david.model.User;
import com.fount.david.repo.UserRepository;
import com.fount.david.service.IUserService;

@Service
public class UserServiceImp implements IUserService {

	
	@Autowired
	private UserRepository repo;
	
	@Override
	public Long saveUser(User user) {
		
		return repo.save(user).getId();
	}

	@Override
	public Optional<User> findByUsername(String username) {
		
		
		return repo.findByUsername(username);
	}

}
