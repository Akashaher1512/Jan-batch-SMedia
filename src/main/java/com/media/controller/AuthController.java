package com.media.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.media.dto.JWTAuthResponse;
import com.media.dto.LoginDto;
import com.media.dto.RegisterDto;
import com.media.service.AuthService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

	@Autowired
	private AuthService authService;
	
	// login rest api
	
	@PostMapping(value = {"/login", "/signin" })
	public ResponseEntity<JWTAuthResponse> login(@RequestBody LoginDto loginDto){
		String token = authService.login(loginDto);
		
		JWTAuthResponse jwtAuthResponse = new JWTAuthResponse();
		
		jwtAuthResponse.setAccessToken(token);
		
		return new ResponseEntity<JWTAuthResponse>( jwtAuthResponse , HttpStatus.OK );
	}
	
	// register api
	
	@PostMapping(value = {"/register","/signup"})
	public ResponseEntity<String> register(@RequestBody RegisterDto registerDto){
		String msg = authService.register(registerDto);
		
		return new ResponseEntity<String>( msg , HttpStatus.CREATED );
	}
	
	
}
