package com.csk.ppmtool.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.csk.ppmtool.domain.User;
import com.csk.ppmtool.repositories.UserRepository;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	public User saveUser(User newUser) {
		newUser.setPassword(bCryptPasswordEncoder.encode(newUser.getPassword()));
		//Username has to be unique(Exception)
		//Make sure that password and confirmPassword match
		//We don't persist or show the confirmPassord
		newUser.setConfirmPassword("");
		userRepository.save(newUser);
		return newUser;
	} 
	
	
	
}
