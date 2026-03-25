package com.myapp.authsystem.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.myapp.authsystem.dto.RegisterRequest;
import com.myapp.authsystem.model.entity.User;
import com.myapp.authsystem.model.enums.Role;
import com.myapp.authsystem.repository.UserRepository;

@Service
public class AuthService {
 
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	
	public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}
	
	public User register(RegisterRequest request) {
		if(userRepository.findByEmail(request.email()).isPresent()){
			throw new IllegalArgumentException("Email já cadastrado no sistema");
		}
		
		User user = User.builder()
				.name(request.name())
				.email(request.email())
				.password(passwordEncoder.encode(request.password()))
				.role(Role.USER)
				.build();
				
		return userRepository.save(user);
	}
}