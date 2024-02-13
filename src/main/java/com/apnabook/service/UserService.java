package com.apnabook.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.apnabook.dto.LoginUserDto;
import com.apnabook.dto.RegisterUserDto;
import com.apnabook.mapper.UserMapper;
import com.apnabook.model.Role;
import com.apnabook.model.User;
import com.apnabook.repository.RoleRepository;
import com.apnabook.repository.UserRepository;

@Service
public class UserService {
	private static final Logger logger = LoggerFactory.getLogger(UserService.class);
	
   @Autowired
   UserRepository userRepository;
   
   @Autowired
   RoleRepository roleRepository;
   
   @Autowired
   UserMapper userMapper;
   
   public ResponseEntity<?> registerUser(RegisterUserDto registerUser){
	   Map<String, String> response = new HashMap<>();
	   Optional<Role> defaultRole = roleRepository.findByRoleType("LEARNER");
	   
	   HashSet<Role> roles = new HashSet<>();
	   roles.add(defaultRole.get());
	   registerUser.setRoles(roles);
	   
	   User newUser = userMapper.registerUserDtoToUser(registerUser);
	   
	   try {
		    userRepository.save(newUser);
		    response.put("Message", "User registered successfully.");
		    logger.info("User registered successfully: {}", newUser.getEmail());
		    return new ResponseEntity<>(response, HttpStatus.CREATED);
	   }catch(Exception e) {
		   logger.error("Failed to register user.", e);
		   response.put("Error", "User already exists");
		   return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	   }
   }
   
   public ResponseEntity<?> loginUser(LoginUserDto loginUser){
	   HashMap<String, String> response = new HashMap<>();
	   
	   try {
		   User user = userRepository.findByEmail(loginUser.getEmail());
		   RegisterUserDto userDetails = userMapper.userToRegisterUserDto(user);
		   
		   if(userDetails.getPassword().equals(loginUser.getPassword())) {
			   response.put("Message", "User logged in Successfully");
			   logger.info("User logged in successfully: {}", loginUser.getEmail());
			   return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
		   }
		   else {
			   response.put("Message", "User authentication failed");
			   logger.warn("User authentication failed for email: {}", loginUser.getEmail());
			   return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		   }
	   } catch(Exception e) {
		   response.put("Message", "Authentication Error");
		   logger.error("Error occurred during user authentication.", e);
		   return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	   }
   }
}

