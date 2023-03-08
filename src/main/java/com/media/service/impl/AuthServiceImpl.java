package com.media.service.impl;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.media.dto.LoginDto;
import com.media.dto.RegisterDto;
import com.media.entity.Role;
import com.media.entity.User;
import com.media.exception.SMediaException;
import com.media.repository.RoleRepository;
import com.media.repository.UserRepository;
import com.media.security.JwtTokenProvider;
import com.media.service.AuthService;

@Service
public class AuthServiceImpl implements AuthService {

	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private JwtTokenProvider jwtTokenProvider;
	
	// login 
	@Override
	public String login(LoginDto loginDto) {
		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getUserNameOrEmail(), loginDto.getPassword()));
		
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		String token = jwtTokenProvider.generateToken(authentication);
		
		
		return token;
	}

	// register
	
	@Override
	public String register(RegisterDto registerDto) {
		
		// 1. check for username exists in db
		if(userRepository.existsByUserName(registerDto.getUserName())) {
			throw new SMediaException(HttpStatus.BAD_REQUEST , "UserName Already exists in database");
		}
		
		// 2. check for email exists in db
		if(userRepository.existsByEmail(registerDto.getEmail())) {
			throw new SMediaException(HttpStatus.BAD_REQUEST , "Email already exists in database");
		}
		
		// 3. create new user object
		User user = new User();
		
		// 4. transfer values from registerDto to user
		user.setName(registerDto.getName());
		user.setEmail(registerDto.getEmail());
		user.setUserName(registerDto.getUserName());
		user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
		
		// 5. set roles for user
		Set<Role> roles =new HashSet<>();
		Role userRole = roleRepository.findByName("ROLE_USER").get();
		
		roles.add(userRole);
		
		// 6. set roles to user
		user.setRoles(roles);
		
		
		// 7. save user in db
		userRepository.save(user);
		
		return "user Registered successfully..!!";
	}

}
