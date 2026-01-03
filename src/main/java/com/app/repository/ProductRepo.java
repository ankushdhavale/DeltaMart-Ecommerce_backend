package com.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.entities.Products;

@Repository
public interface ProductRepo extends JpaRepository<Products, Long> {
	Products findProductsByProductName(String name);
}
