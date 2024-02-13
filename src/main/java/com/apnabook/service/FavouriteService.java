package com.apnabook.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.apnabook.dto.FavouriteDto;
import com.apnabook.model.Courses;
import com.apnabook.model.User;
import com.apnabook.repository.CourseRepository;
import com.apnabook.repository.UserRepository;

@Service
public class FavouriteService {
	
	private static final Logger logger = LoggerFactory.getLogger(FavouriteService.class);
  
   @Autowired
   private CourseRepository courseRepository;
   
   @Autowired
   private UserRepository userRepository;
   
   public ResponseEntity<Map<String,String>> makeFavourite(FavouriteDto favouriteDto){
	   Map<String, String> response = new HashMap<>();
	   
	   Optional<Courses> course = courseRepository.findById(favouriteDto.getCourseId());
	   String email = favouriteDto.getEmail();
	   User user = userRepository.findByEmail(email);
	   
	   if(user == null || course.isEmpty()) {
		   response.put("Message", "Validation error");
		   logger.error("Failed to add course to favourites. Validation error.");
		   return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	   }
	   
	   Set<Courses> favourite = user.getFavourites();
	   favourite.add(course.get());
	   user.setFavourites(favourite);
	   userRepository.save(user);
	   
	   response.put("Message", "Course added to favourites.");
	   logger.info("Course added to favourites. CourseId: {}, User: {}", favouriteDto.getCourseId(), email);
	   return new ResponseEntity<>(response, HttpStatus.OK);
   }
   
   public ResponseEntity<Map<String, String>> removeFavourite(FavouriteDto favouriteDto){
	   Map<String, String> response = new HashMap<>();
	   
	   Optional<Courses> course = courseRepository.findById(favouriteDto.getCourseId());
	   String email = favouriteDto.getEmail();
	   User user = userRepository.findByEmail(email);
	   
	   if(user == null || course.isEmpty()) {
		   response.put("Message", "Something went wrong");
		   logger.info("Course added to favourites. CourseId: {}, User: {}", favouriteDto.getCourseId(), email);
		   return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	   }
	   
	   Set<Courses> favourite = user.getFavourites();
	   
	   try {
		   favourite.remove(course.get());
		   logger.info("Course removed from favourites. CourseId: {}, User: {}", favouriteDto.getCourseId(), email);
	   }
	   catch(Exception e) {
		   logger.error("Failed to remove course from favourites.", e);
		   response.put("Error", e.toString());
		   return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	   }
	   
	   user.setFavourites(favourite);
	   userRepository.save(user);
	   
	   response.put("Message", "Course removed from favourites");
	   return new ResponseEntity<>(response, HttpStatus.OK);
   }
   
}
