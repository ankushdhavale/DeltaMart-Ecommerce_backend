package com.app.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.dtos.UserDto;
import com.app.entities.User;
import com.app.exceptions.ResourceNotFoundException;
import com.app.repository.FavRepo;
import com.app.repository.UserRepo;
import com.app.response.ApiResponse;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private FavRepo favRepo;
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private ModelMapper modelMapper;

    @Override
   	public ApiResponse addUser(User user) {
   		userRepo.save(user);
   		return new ApiResponse("User Inserted");
   	}

    @Override
	public List<User> getUsers() {
		List<User> userList = userRepo.findAll();
		return userList;
	}

    @Override
	public ApiResponse delUser(Long uid) {
		userRepo.deleteById(uid);
		return new ApiResponse("User Deleted.");
	}

    @Override
	public User getById(Long userId) {
		User user = userRepo.findById(userId).orElseThrow();
		return user;
	}

	@Override
	public UserDto getUser(String email, String password) {
		User user = userRepo.findUserByEmailAndPassword(email, password).orElseThrow(() -> new RuntimeException("Invalid email or password"));
		if(user!=null) {
			System.out.println("Success User");
		}
		return modelMapper.map(user, UserDto.class);
	}
}
