package com.apnabook.service;

import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apnabook.dto.FavouriteDto;
import com.apnabook.model.Courses;
import com.apnabook.model.User;
import com.apnabook.repository.CourseRepository;
import com.apnabook.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class FavouriteService {

	@Autowired
	private CourseRepository courseRepository;

	@Autowired
	private UserRepository userRepository;

	public boolean makeFavourite(FavouriteDto favouriteDto) {

		Optional<Courses> course = courseRepository.findById(favouriteDto.getCourseId());
		String email = favouriteDto.getEmail();
		User user = userRepository.findByEmail(email);

		if (user == null || course.isEmpty()) {
			log.error("Failed to add course to favourites. Validation error.");
			return false;
		}

		Set<Courses> favourite = user.getFavourites();
		favourite.add(course.get());
		user.setFavourites(favourite);
		userRepository.save(user);

		log.info("Course added to favourites. CourseId: {}, User: {}", favouriteDto.getCourseId(), email);
		return true;
	}

	public boolean removeFavourite(FavouriteDto favouriteDto) {

		Optional<Courses> course = courseRepository.findById(favouriteDto.getCourseId());
		String email = favouriteDto.getEmail();
		User user = userRepository.findByEmail(email);

		if (user == null || course.isEmpty()) {
			log.info("Course added to favourites. CourseId: {}, User: {}", favouriteDto.getCourseId(), email);
			return false;
		}

		Set<Courses> favourite = user.getFavourites();

		try {
			favourite.remove(course.get());
			log.info("Course removed from favourites. CourseId: {}, User: {}", favouriteDto.getCourseId(), email);
		} catch (Exception e) {
			log.error("Failed to remove course from favourites.", e);
			return false;
		}

		user.setFavourites(favourite);
		userRepository.save(user);
		return true;
	}

}
