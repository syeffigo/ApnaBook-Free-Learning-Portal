package com.apnabook.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.apnabook.dto.CourseDto;
import com.apnabook.dto.GetCourseDto;
import com.apnabook.mapper.CourseMapper;
import com.apnabook.model.Category;
import com.apnabook.model.Courses;
import com.apnabook.model.CoursesCategory;
import com.apnabook.repository.CategoryRepository;
import com.apnabook.repository.CourseRepository;
import com.apnabook.repository.CoursesCategoryRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CourseService {

	@Autowired
	private CourseRepository courseRepository;

	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private CoursesCategoryRepository coursesCategoryRepository;

	@Autowired
	private CourseMapper courseMapper;

	public boolean addCourse(CourseDto courseDto) {

		Optional<Category> categoryObj = categoryRepository.findByCategoryType(courseDto.getCategory());

		if (categoryObj.isEmpty()) {
			return false;
		}

		Category category = categoryObj.get();
		Courses course = courseMapper.courseDtoToCourses(courseDto);

		CoursesCategory obj = new CoursesCategory();
		obj.setCategory(category);
		obj.setCourse(courseRepository.save(course));
		coursesCategoryRepository.save(obj);

		log.info("Course added successfully: {}", course);
		return false;
	}

	public Optional<List<CoursesCategory>> getCourse(GetCourseDto getCourseDto) {
		Map<String, String> response = new HashMap<>();

		Optional<Category> categoryObject = categoryRepository.findByCategoryType(getCourseDto.getCategory());

		if (categoryObject.isEmpty()) {
			return Optional.of(null);
		}

		Category category = categoryObject.get();

		List<CoursesCategory> courses = coursesCategoryRepository.findByCategory(category);
		Optional<List<CoursesCategory>> optionalList = Optional.of(courses);
		log.info("Retrieved {} courses for category: {}", courses.size(), category.getCategoryType());
		return optionalList;
	}

	public ResponseEntity<?> getCourseId(Long id, GetCourseDto getCourseDto) {
		List<CoursesCategory> courses = getCourse(getCourseDto).get();

		for (CoursesCategory course : courses) {
			if (course.getId().equals(id)) {
				return new ResponseEntity<>(course, HttpStatus.OK);
			}
		}
		return new ResponseEntity<>(new HashMap<>().put("Message", "Course id doesn't exists"), HttpStatus.BAD_REQUEST);
	}
}
