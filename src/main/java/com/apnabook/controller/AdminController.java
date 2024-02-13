package com.apnabook.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.apnabook.dto.LoginUserDto;
import com.apnabook.dto.RegisterUserDto;
import com.apnabook.service.UserService;

@RestController
@RequestMapping("/admin")
public class AdminController {
     @Autowired
     private UserService userService;
     
     @PostMapping("/register")
     public ResponseEntity<?> register(@Valid @RequestBody RegisterUserDto  registerUserDto){
    	 return userService.registerUser(registerUserDto);
     }
     
     @PostMapping("/login")
     public ResponseEntity<?> login(@Valid @RequestBody LoginUserDto loginUserDto){
    	 return userService.loginUser(loginUserDto);
     }
}
