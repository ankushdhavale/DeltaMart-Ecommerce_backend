package com.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.entities.FavouriteProducts;
import com.app.entities.Products;
import com.app.entities.User;
import com.app.repository.FavRepo;
import com.app.repository.ProductRepo;
import com.app.repository.UserRepo;
import com.app.response.ApiResponse;

@Service
public class ProductServiceImp implements ProductService{

	@Autowired
	private ProductRepo productRepo;
	@Autowired
	private UserRepo userRepo;
	@Autowired
	private FavRepo favRepo;
	
	@Override
	public ApiResponse addProduct(Products product) {
		productRepo.save(product);
		return new ApiResponse("Product Inserted");
	}

	@Override
	public List<Products> getProducts() {
		List<Products> list = productRepo.findAll();
		return list;
	}

	@Override
	public ApiResponse delProduct(Long pid) {
		productRepo.deleteById(pid);
		return new ApiResponse("Deleted SucssesFully.");
	}

	@Override
	public Products getProduct(Long pid) {
		Products products = productRepo.findById(pid).orElseThrow();
		return products;
	}

	@Override
	public ApiResponse addToFav(Long pid, Long uid) {
		User user = userRepo.findById(uid).orElseThrow();
		Products products = productRepo.findById(pid).orElseThrow(()->new RuntimeException("Product Not Found."));
		
		FavouriteProducts favouriteProducts= new FavouriteProducts(); 
		favouriteProducts.setUser(user);
		favouriteProducts.setProduct(products);
		favRepo.save(favouriteProducts);
		return new ApiResponse("Added to fav.");
	}

	@Override
	public Products getProductName(String name) {
		Products products = productRepo.findProductsByProductName(name);
		return products;
	}
	
	
}
