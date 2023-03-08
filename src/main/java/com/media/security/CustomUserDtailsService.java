package com.media.security;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.media.entity.User;
import com.media.repository.UserRepository;

@Service
public class CustomUserDtailsService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String userNameOrEmail) throws UsernameNotFoundException {
		
		// fetch user from db
		User user = userRepository.findByUserNameOrEmail(userNameOrEmail, userNameOrEmail)
		.orElseThrow(()-> new UsernameNotFoundException("User not found with username or email : " +userNameOrEmail));
		
		Set<GrantedAuthority> authority = user.getRoles().stream().map((role) -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toSet());
		
		return new org.springframework.security.core.userdetails.User(user.getEmail() , user.getPassword() , authority );
	}

}
