package com.app.service;

import java.util.List;

import com.app.entities.Products;
import com.app.response.ApiResponse;

public interface ProductService {
	
	ApiResponse addProduct(Products product);

	List<Products> getProducts();
	
	ApiResponse delProduct(Long pid);
	
	Products getProduct(Long pid);
	
	ApiResponse addToFav(Long pid,Long uid);
	
	Products getProductName(String name);
}
