package com.apnabook.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.apnabook.model.Courses;

public interface CourseRepository extends JpaRepository<Courses, Long> {

}
