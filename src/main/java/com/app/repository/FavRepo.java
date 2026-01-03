package com.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.entities.FavouriteProducts;

@Repository
public interface FavRepo extends JpaRepository<FavouriteProducts, Long>{

}
