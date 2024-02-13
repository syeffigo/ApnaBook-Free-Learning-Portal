package com.apnabook.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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
	public ResponseEntity<?> makeFavourite(@Valid @RequestBody FavouriteDto favouriteDto){
		return favouriteService.makeFavourite(favouriteDto);
	}
	
	@PostMapping("/remove")
	public ResponseEntity<?> removeFavourite(@Valid @RequestBody FavouriteDto favouriteDto){
		return favouriteService.removeFavourite(favouriteDto);
	}
	
}
