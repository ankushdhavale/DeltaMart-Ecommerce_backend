package com.app.dtos;

import java.util.Set;

import com.app.entities.Products;
import com.app.entities.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class FavDto {
	
	private Long favId;
	
	private User user;
	
	private Set<Products> products;
}
