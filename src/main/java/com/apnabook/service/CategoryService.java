package com.apnabook.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.apnabook.dto.CategoryDto;
import com.apnabook.mapper.CourseMapper;
import com.apnabook.model.Category;
import com.apnabook.repository.CategoryRepository;

@Service
public class CategoryService {
   @Autowired
   private CategoryRepository categoryRepository;
   
   private static final Logger logger = LoggerFactory.getLogger(CategoryService.class);
   
   @Autowired
   private CourseMapper courseMapper;
   
   public ResponseEntity<?> addCategory(CategoryDto categoryDto){
	   Map<String, String> response = new HashMap<>();
	   
	   try {
		   Category toAdd = courseMapper.categoryDtoToCategory(categoryDto);
		   
		   if(toAdd == null) {
			   response.put("Message", "Something went wrong");
			   logger.error("Failed to add category. Received null Category object from mapper.");
			   return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		   }
		   categoryRepository.save(toAdd);
		   response.put("Message", "Category Added.");
		   logger.info("Category added successfully: {}", toAdd);
		   return new ResponseEntity<>(response, HttpStatus.CREATED);
		   
	   } catch(Exception e) {
		   response.put("Message", "Category already exists.");
		   logger.error("Failed to add category. Category already exists.", e);
		   return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	   }
   }
   
   public ResponseEntity<?> getCategory(){
	   Map<String, List<Category>> response = new HashMap<>();
	   response.put("Message", categoryRepository.findAll());
	   return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
   }
}
