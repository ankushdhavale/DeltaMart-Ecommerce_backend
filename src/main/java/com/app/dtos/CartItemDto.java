package com.app.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CartItemDto {

	private Long cartItemId;
	
	private ProductDto2 product;
	
	private Integer quantity;
	
	private double productPrice;
}
