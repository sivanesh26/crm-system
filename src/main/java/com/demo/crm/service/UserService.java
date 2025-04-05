package com.demo.crm.service;

import com.demo.crm.model.User;
import com.demo.crm.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	public Optional<User> findUser(String email) {
		return userRepository.findByEmail(email);
	}
}
