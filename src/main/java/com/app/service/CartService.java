package com.app.service;

import java.util.List;

import com.app.dtos.CartDto;
import com.app.dtos.CartItemDto;
import com.app.entities.Cart;
import com.app.response.ApiResponse;

public interface CartService {
	
	void addCart(Cart cart);
	
	CartDto addProductToCart(Long cartId,Long productId,Integer quantity);
	
	List<CartDto> getAllCarts();
	
	String deleteProductFromCart(Long cartId,Long productId);
	
	CartDto getCart(String emailId);
	
	CartDto getCartById(Long cartId);
	
	List<CartItemDto> getCartItemsById(Long cartId);
	
	ApiResponse updateItems(Long cartItemId,Long productId,Integer quant);
}
