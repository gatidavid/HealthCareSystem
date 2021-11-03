package com.fount.david.service.impl;

import java.util.Collections;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.fount.david.model.User;
import com.fount.david.repo.UserRepository;
import com.fount.david.service.IUserService;

@Service
public class UserServiceImp implements IUserService, UserDetailsService {

	@Autowired
	private UserRepository repo;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Override
	public Long saveUser(User user) {

		String pwd = user.getPassword();// get password from user
		String encPwd = passwordEncoder.encode(pwd);// encode password
		user.setPassword(encPwd); // set back to object

		return repo.save(user).getId();

	}

	@Override
	public Optional<User> findByUsername(String username) { 
		return repo.findByUsername(username);

	}
	@Transactional
	@Override
	public void updateUserPwd(String pwd, Long userId) {
	
		String encPwd = passwordEncoder.encode(pwd);
		repo.updateUserPwd(encPwd, userId);
		
	}

	@Override
	public UserDetails loadUserByUsername(String username) 
			throws UsernameNotFoundException {

		Optional<User> opt = findByUsername(username);

		if (!opt.isPresent()) {

			throw new UsernameNotFoundException(username);

		} else {

			User user = opt.get();

			return new org.springframework.security.core.userdetails.User(
					user.getUsername(), 
					 user.getPassword(),
					Collections.singletonList(new SimpleGrantedAuthority(user.getRole())));
		}

	}

	

}
