package com.app.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.dtos.OrderDto;
import com.app.dtos.OrderItemDto;
import com.app.entities.Cart;
import com.app.entities.CartItem;
import com.app.entities.Order;
import com.app.entities.OrderItem;
import com.app.entities.Payment;
import com.app.entities.Products;
import com.app.exceptions.APIException;
import com.app.exceptions.ResourceNotFoundException;
import com.app.repository.CartItemRepo;
import com.app.repository.CartRepo;
import com.app.repository.OrderItemRepo;
import com.app.repository.OrderRepo;
import com.app.repository.PaymentRepo;
import com.app.repository.UserRepo;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class OrderServiceImpl implements OrderService{
	
	@Autowired
	public UserRepo userRepo;
	
	@Autowired
	public CartRepo cartRepo;
	
	@Autowired
	public OrderRepo orderRepo;
	
	@Autowired
	public PaymentRepo paymentRepo;
	
	@Autowired
	public OrderItemRepo orderItemRepo;
	
	@Autowired
	public CartItemRepo cartItemRepo;
	
	@Autowired
	public UserService userService;
	
	@Autowired
	public CartService cartService;
	
	@Autowired
	public ModelMapper modelMapper;
	
	@Override
	public OrderDto placeOrder(String emailId, Long cartId, String paymentMethod) {
		
		Cart cart = cartRepo.findById(cartId).get();
		
		if(cart==null) {
			throw new ResourceNotFoundException("Cart","cartId",cartId);
		}
		
		Order order = new Order();
		
		order.setEmail(emailId);
		order.setOrderDate(LocalDate.now());
		order.setTotalAmount(cart.getTotalPrice());
		
		Payment payment = new Payment();
		
		payment.setOrder(order);
		payment.setPaymentMethod(paymentMethod);
		
		order.setPayment(payment);
		
		Order saveOrder = orderRepo.save(order);
		
		List<CartItem> cartItems = cart.getCartItem();
		
		if(cartItems.size()==0) {
			throw new APIException("Cart is empty.");
		}
		
		List<OrderItem> orderItems = new ArrayList<>();
		
		for(CartItem cartItem :cartItems) {
			OrderItem orderItem = new OrderItem();
			
			orderItem.setProduct(cartItem.getProduct());
			orderItem.setQuantity(cartItem.getQuantity());
			
			orderItem.setProductPrice(cartItem.getProductPrice());
			orderItem.setOrder(saveOrder);
			
			orderItems.add(orderItem);
		}
		
		orderItems = orderItemRepo.saveAllAndFlush(orderItems);
		cart.getCartItem().forEach(item->{
			int quantity = item.getQuantity();
			Products product = item.getProduct();
			cartService.deleteProductFromCart(cartId, item.getProduct().getProductId());
			product.setQuantity(product.getQuantity()- quantity);
		});
		
		OrderDto orderDto = modelMapper.map(saveOrder, OrderDto.class);
		orderItems.forEach(item->orderDto.getOrderItems().add(modelMapper.map(item,OrderItemDto.class)));
		return orderDto;
	}

}
