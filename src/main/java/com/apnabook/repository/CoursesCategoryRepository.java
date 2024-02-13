package com.apnabook.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.apnabook.model.Category;
import com.apnabook.model.CoursesCategory;

public interface CoursesCategoryRepository extends JpaRepository<CoursesCategory, Long> {
     
	List<CoursesCategory> findByCategory(Category category);
} 
