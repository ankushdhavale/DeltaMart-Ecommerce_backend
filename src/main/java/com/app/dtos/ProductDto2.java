package com.app.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto2 {
	
	@JsonProperty(access = Access.READ_ONLY)
	private Long productId;
	
	private String productName;
	
	private String description;
	private String imgUrl;
	
	private Integer quantity;
	
	private double price;
}
