package com.app.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.dtos.OrderDto;
import com.app.service.OrderService;

@RestController
@RequestMapping("/orders")
public class OrderControllers {
	
	@Autowired
	private OrderService orderService;
	
	@PostMapping("/users/{emailId}/carts/{cartId}/{paymentMethod}")
	public ResponseEntity<OrderDto> orderProducts(@PathVariable String emailId,@PathVariable Long cartId,@PathVariable String paymentMethod){
		OrderDto placeOrder = orderService.placeOrder(emailId, cartId, paymentMethod);
		return ResponseEntity.ok(placeOrder);
	}
}
