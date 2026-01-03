package com.app.dtos;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OrderDto {
	
	private Long orderItemId;
	private String email;
	private List<OrderItemDto> orderItems = new ArrayList<>();
	private LocalDate orderDate;
	private PaymentDto payment;
	private Double totalAmount;
}

