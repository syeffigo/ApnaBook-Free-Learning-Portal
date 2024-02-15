package com.apnabook.service;

import java.util.HashSet;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apnabook.dto.LoginUserDto;
import com.apnabook.dto.RegisterUserDto;
import com.apnabook.mapper.UserMapper;
import com.apnabook.model.Role;
import com.apnabook.model.User;
import com.apnabook.repository.RoleRepository;
import com.apnabook.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserService {

	@Autowired
	UserRepository userRepository;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	UserMapper userMapper;

	public boolean registerUser(RegisterUserDto registerUser) {
		Optional<Role> defaultRole = roleRepository.findByRoleType("LEARNER");

		HashSet<Role> roles = new HashSet<>();
		roles.add(defaultRole.get());
		registerUser.setRoles(roles);

		User newUser = userMapper.registerUserDtoToUser(registerUser);

		try {
			userRepository.save(newUser);
			log.info("User registered successfully: {}", newUser.getEmail());
			return true;
		} catch (Exception e) {
			log.error("Failed to register user.", e);
			return false;
		}
	}

	public boolean loginUser(LoginUserDto loginUser) {

		try {
			User user = userRepository.findByEmail(loginUser.getEmail());
			RegisterUserDto userDetails = userMapper.userToRegisterUserDto(user);

			if (userDetails.getPassword().equals(loginUser.getPassword())) {
				log.info("User logged in successfully: {}", loginUser.getEmail());
				return true;
			} else {
				log.warn("User authentication failed for email: {}", loginUser.getEmail());
				return false;
			}
		} catch (Exception e) {
			log.error("Error occurred during user authentication.", e);
			return false;
		}
	}
}
