package com.app.service;

import java.util.List;

import com.app.dtos.CatDto;
import com.app.entities.Category;
import com.app.response.ApiResponse;

public interface CatService {
	ApiResponse addCat(CatDto catDto);
	List<Category> getCat();
	ApiResponse delCat(Long cid);
	Category getById(Long cateId);
}
