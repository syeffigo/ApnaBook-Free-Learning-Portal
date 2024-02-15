package com.apnabook.controller;

import java.util.HashMap;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.apnabook.dto.CategoryDto;
import com.apnabook.model.Category;
import com.apnabook.service.CategoryService;

@RestController
@RequestMapping("/category")
public class CategoryController {
	@Autowired
	private CategoryService categoryService;

	@PostMapping("/create")
	public ResponseEntity<?> createCategory(@Valid @RequestBody CategoryDto categoryDto) {
		boolean add = categoryService.addCategory(categoryDto);
		HashMap<String, String> response = new HashMap<>();

		if (add) {
			response.put("Message", "Category Added.");
			return new ResponseEntity<>(response, HttpStatus.CREATED);
		} else {
			response.put("Message", "Something went wrong");
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/all")
	public ResponseEntity<?> getAllCategory() {
		HashMap<String, List<Category>> response = new HashMap<>();
		return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
	}
}
