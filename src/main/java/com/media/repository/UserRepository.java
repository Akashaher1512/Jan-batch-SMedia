package com.media.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.media.entity.User;


public interface UserRepository extends JpaRepository<User, Long> {

	Optional<User> findByEmail(String email);
	Optional<User> findByUserName(String userName);
	
	Optional<User> findByUserNameOrEmail(String userName , String email);
	
	Boolean existsByUserName(String userName);
	Boolean existsByEmail(String Email);
	
}
