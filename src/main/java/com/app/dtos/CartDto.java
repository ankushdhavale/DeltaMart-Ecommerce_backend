package com.app.dtos;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CartDto {
	
	private Long cartId;
	
	private Double totalPrice = 0.0;
	
	private List<ProductDto2> products = new ArrayList<>();
}
