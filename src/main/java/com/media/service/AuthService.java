package com.media.service;

import com.media.dto.LoginDto;
import com.media.dto.RegisterDto;

public interface AuthService {

	String login(LoginDto loginDto);
	
	String register(RegisterDto registerDto);
}
