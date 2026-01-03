package com.app.service;

import java.util.List;

import com.app.dtos.UserDto;
import com.app.entities.User;
import com.app.response.ApiResponse;

public interface UserService {
	
	ApiResponse addUser(User user);
	
	List<User> getUsers();
	
	ApiResponse delUser(Long uid);
	
	User getById(Long userId);
	
	UserDto getUser(String email,String password);
}
