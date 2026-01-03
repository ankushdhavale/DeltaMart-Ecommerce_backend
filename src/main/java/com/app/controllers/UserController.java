package com.app.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.dtos.UserDto;
import com.app.dtos.UserLoginDto;
import com.app.entities.Cart;
import com.app.entities.RoleEnum;
import com.app.entities.User;
import com.app.response.ApiResponse;
import com.app.service.AddressService;
import com.app.service.CartService;
import com.app.service.UserService;


@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private AddressService addressService; 
	
	@Autowired
	private CartService cartService;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@PostMapping
	public ResponseEntity<ApiResponse> createUser(@RequestBody UserDto userDto){
		User user = convertToEntity(userDto);
		Cart cart = new Cart();
		user.setCart(cart);
		ApiResponse userResponse = userService.addUser(user);
		cart.setUser(user);
		cartService.addCart(cart);
		return ResponseEntity.ok(userResponse); 
	}
	
	@PostMapping("/login")
	public ResponseEntity<UserDto> getUserById(@RequestBody UserLoginDto userLogin){
		System.out.println(userLogin.getUsername());
		System.out.println(userLogin.getPassword());
		UserDto dto = userService.getUser(userLogin.getUsername(), userLogin.getPassword());
		return ResponseEntity.ok(dto);
	}
	
	@GetMapping
	public ResponseEntity<List<UserDto>> getUsers(){
		List<User> users = userService.getUsers();
		List<UserDto> userDtoList = users.stream().map(user->modelMapper.map(user,UserDto.class)).collect(Collectors.toList());
		return ResponseEntity.ok(userDtoList);
	}
	
	@DeleteMapping("/{uid}")
	public ResponseEntity<ApiResponse> delUser(@PathVariable Long uid){
		ApiResponse delUser = userService.delUser(uid);
		return ResponseEntity.ok(delUser);
	}
	
	public User convertToEntity(UserDto userDto) {
		User user = new User();
		user.setFirstname(userDto.getFirstName());
		user.setLastname(userDto.getLastName());
		user.setMobileNumber(userDto.getMobileNumber());
		user.setEmail(userDto.getEmail());
		user.setPassword(userDto.getPassword());
		user.setRole(RoleEnum.valueOf(userDto.getRole().toUpperCase()));
		user.setCity(userDto.getCity());
		user.setState(userDto.getState());
		user.setCountry(userDto.getCountry());
		return user;
	}
}
