package com.app.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemDto {
	
	private Long orderItemId;
	private ProductDto2 product;
	private Integer quantity;
	
	private double orderdProductPrice;
}		
