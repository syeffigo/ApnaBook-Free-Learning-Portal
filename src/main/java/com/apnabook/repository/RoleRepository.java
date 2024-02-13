package com.apnabook.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.apnabook.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long>{
       Optional<Role> findByRoleType(String roletype);
}
