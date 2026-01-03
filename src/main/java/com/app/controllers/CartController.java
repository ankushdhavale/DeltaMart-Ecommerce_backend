package com.app.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.dtos.CartDto;
import com.app.dtos.CartItemDto;
import com.app.response.ApiResponse;
import com.app.service.CartService;

@RestController
@RequestMapping("/carts/public")
public class CartController {
	
	@Autowired
	private CartService cartService;

	@PostMapping("/carts/{cartId}/products/{productId}/quantity/{quantity}")
	public ResponseEntity<CartDto> addProductToCart(@PathVariable Long cartId,@PathVariable Long productId,@PathVariable Integer quantity){
		CartDto cartDto = cartService.addProductToCart(cartId, productId, quantity);
		return new ResponseEntity<CartDto>(cartDto,HttpStatus.CREATED);
	}
	
	@GetMapping("/carts")
	public ResponseEntity<List<CartDto>> getCarts(){
		List<CartDto> cartDtos = cartService.getAllCarts();
		return new ResponseEntity<List<CartDto>>(cartDtos,HttpStatus.FOUND);
	}
	
	@GetMapping("/{emailId}")
	public ResponseEntity<CartDto> getCartByUserId(@PathVariable String emailId){
		CartDto cartDto = cartService.getCart(emailId);
		return new ResponseEntity<CartDto>(cartDto,HttpStatus.ACCEPTED);
	}
	
	@GetMapping("/cartId")
	public ResponseEntity<CartDto> getCartById(@PathVariable Long cartId){
		CartDto cartDto = cartService.getCartById(cartId);
		return new ResponseEntity<CartDto>(cartDto,HttpStatus.ACCEPTED);
	}
	
	@GetMapping("/cartitems/{cartId}")
	public ResponseEntity<List<CartItemDto>> getCartItems(@PathVariable Long cartId){
		List<CartItemDto> cartItemDto = cartService.getCartItemsById(cartId);
		return new ResponseEntity<List<CartItemDto>>(cartItemDto,HttpStatus.ACCEPTED);
	}
	
	@PutMapping("/updateitems/cartitemid/{cartItemId}/productId/{productId}/quantity/{quantity}")
	public ResponseEntity<ApiResponse> updateCartItems(@PathVariable Long cartItemId,@PathVariable Long productId,@PathVariable Integer quantity){
		ApiResponse apiResponse = cartService.updateItems(cartItemId, productId, quantity);
	
		return ResponseEntity.ok(apiResponse);
	}
	
	@DeleteMapping("/cartid/{cartId}/productid/{productId}")
	public ResponseEntity<String> deleteProductFromCart(@PathVariable Long cartId,@PathVariable Long productId){
		String status = cartService.deleteProductFromCart(cartId, productId);
		return new ResponseEntity<String>(status,HttpStatus.OK);
	}
}
