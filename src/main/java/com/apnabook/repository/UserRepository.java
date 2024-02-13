package com.apnabook.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.apnabook.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    
	User findByEmail(String email);
}
