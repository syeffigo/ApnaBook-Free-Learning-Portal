package com.apnabook.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class CategoryDto {
    @JsonProperty("category_name")
	private String categoryType;
}
