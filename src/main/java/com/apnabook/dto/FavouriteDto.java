package com.apnabook.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class FavouriteDto {
   @JsonProperty("email")
   private String email;
   
   @JsonProperty("course_id")
   private Long courseId;
}
