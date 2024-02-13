package com.apnabook.mapper;

import org.mapstruct.Mapper;

import com.apnabook.dto.RegisterUserDto;
import com.apnabook.model.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
    
	User registerUserDtoToUser(RegisterUserDto registerUser);
	
	RegisterUserDto userToRegisterUserDto(User user);
}
