package com.app.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.entities.FavouriteProducts;
import com.app.entities.Products;
import com.app.exceptions.ResourceNotFoundException;
import com.app.repository.FavRepo;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class FavServiceImpl implements FavService{

	@Autowired
	private FavRepo favRepo;
	
	@Override
	public List<Products> getFavoriteProductsByUser(Long userId) {
		
		List<FavouriteProducts> favorites = favRepo.findAll();
		 if (favorites.isEmpty()) {
		        throw new ResourceNotFoundException(
		            "Favourite products", "userId", userId
		        );
		    }
		 return favorites.stream()
//				.filter(fb->fb.getUser().getUserId().equals(userId))
				.map(FavouriteProducts::getProduct)
				.collect(Collectors.toList());
	}

}
