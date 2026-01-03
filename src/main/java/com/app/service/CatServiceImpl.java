package com.app.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.dtos.CatDto;
import com.app.dtos.CategoryIdDto;
import com.app.entities.Category;
import com.app.repository.CategoryRepo;
import com.app.response.ApiResponse;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class CatServiceImpl implements CatService{
	
	@Autowired
	private CategoryRepo catRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	public ApiResponse addCat(CatDto catDto) {
		Category category = modelMapper.map(catDto, Category.class);
		catRepo.save(category);
		return new ApiResponse("Category Inserted.");
	}

	@Override
	public List<Category> getCat() {
		return catRepo.findAll();
	}

	@Override
	public ApiResponse delCat(Long cid) {
		catRepo.deleteById(cid);
		return new ApiResponse("Category Deleted.");
	}

	@Override
	public Category getById(Long cateId) {
		return catRepo.findById(cateId).get();
	}
}
