package com.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.entities.Cart;
import com.app.entities.CartItem;
import com.app.entities.Products;

@Repository
public interface CartItemRepo extends JpaRepository<CartItem, Long>{

	CartItem findCartItemBycartItemId(Long cartItemId);
	
	CartItem findByProductAndCart(Cart cart,Products product);
	
	CartItem deleteCartItemByProductAndCart(Cart cart,Products product);
	
	CartItem findCartItemByCartAndProduct(Cart cart,Products product);
	
	CartItem findByCartItemIdAndProduct(Long cartItemId,Products product);
	
	CartItem findByProduct(Products product);
}
