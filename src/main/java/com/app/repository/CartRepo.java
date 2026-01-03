package com.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.entities.Cart;
import com.app.entities.User;

@Repository
public interface CartRepo extends JpaRepository<Cart, Long>{
	
	Cart findByUser(User user);
}
