package com.app.service;

import java.util.List;

import com.app.entities.Products;

public interface FavService {
	
	List<Products> getFavoriteProductsByUser(Long userId);
}
