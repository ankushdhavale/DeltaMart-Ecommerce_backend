package com.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.entities.OrderItem;

@Repository
public interface OrderItemRepo extends JpaRepository<OrderItem, Long>{

}
