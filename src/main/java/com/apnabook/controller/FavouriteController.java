package com.apnabook.controller;

import java.util.HashMap;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.apnabook.dto.FavouriteDto;
import com.apnabook.service.FavouriteService;

@RestController
@RequestMapping("/favourite")
public class FavouriteController {

	@Autowired
	private FavouriteService favouriteService;

	@PostMapping("/make")
	public ResponseEntity<?> makeFavourite(@Valid @RequestBody FavouriteDto favouriteDto) {
		boolean favourite = favouriteService.makeFavourite(favouriteDto);
		HashMap<String, String> response = new HashMap<>();

		if (favourite) {
			response.put("Message", "Course added to favourites.");
			return new ResponseEntity<>(response, HttpStatus.OK);
		} else {
			response.put("Message", "Validation error");
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping("/remove")
	public ResponseEntity<?> removeFavourite(@Valid @RequestBody FavouriteDto favouriteDto) {
		boolean deletefavourite = favouriteService.removeFavourite(favouriteDto);
		HashMap<String, String> response = new HashMap<>();
		if (deletefavourite) {
			response.put("Message", "Course removed from favourites");
			return new ResponseEntity<>(response, HttpStatus.OK);
		} else {
			response.put("Message", "Something went wrong");
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}
	}

}
