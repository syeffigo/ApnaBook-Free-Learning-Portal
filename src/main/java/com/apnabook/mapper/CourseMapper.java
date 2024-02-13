package com.apnabook.mapper;

import org.mapstruct.Mapper;

import com.apnabook.dto.CategoryDto;
import com.apnabook.dto.CourseDto;
import com.apnabook.model.Category;
import com.apnabook.model.Courses;

@Mapper(componentModel = "spring")
public interface CourseMapper {
   
	Category categoryDtoToCategory(CategoryDto categoryDto);
	
	Courses courseDtoToCourses(CourseDto courseDto);
	
	Category typeToCategory(String type);
	
	CategoryDto categoryToCategoryDto(Category category);
}
