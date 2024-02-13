package com.apnabook.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.apnabook.dto.CategoryDto;
import com.apnabook.service.CategoryService;

@RestController
@RequestMapping("/category")
public class CategoryController {
   @Autowired
   private CategoryService categoryService;
   
   @PostMapping("/create")
   public ResponseEntity<?> createCategory(@Valid @RequestBody CategoryDto categoryDto){
	   return categoryService.addCategory(categoryDto);
   }
   
   @GetMapping("/all")
   public ResponseEntity<?> getAllCategory(){
	   return categoryService.getCategory();
   }
}
