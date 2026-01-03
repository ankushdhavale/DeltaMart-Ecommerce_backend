package com.app.service;

import com.app.dtos.OrderDto;

public interface OrderService {
	
	OrderDto placeOrder(String emailId,Long cartId,String paymentMethod);
}
