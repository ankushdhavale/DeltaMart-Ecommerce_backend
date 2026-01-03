package com.app.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.dtos.CatDto;
import com.app.entities.Category;
import com.app.response.ApiResponse;
import com.app.service.CatService;

@RestController
@RequestMapping("/category")
public class CategoryController {
	
	@Autowired
	private CatService catService;
	@Autowired
	private ModelMapper modelMapper;

	@PostMapping
	public ResponseEntity<ApiResponse> addCat(@RequestBody CatDto catDto){
			ApiResponse response = catService.addCat(catDto);
			return new ResponseEntity<ApiResponse>(response,HttpStatus.OK);
	}
	
	@GetMapping
	public ResponseEntity<List<CatDto>> getCat(){
		List<Category> cat = catService.getCat();
		List<CatDto> catDto = cat.stream().map(p->modelMapper.map(p,CatDto.class)).collect(Collectors.toList());
		
		return new ResponseEntity<List<CatDto>>(catDto,HttpStatus.ACCEPTED);
	}
	
	@GetMapping("/{catId}")
	public ResponseEntity<CatDto> getCatById(@PathVariable Long catId){
		Category category = catService.getById(catId);
		CatDto catDto = modelMapper.map(category, CatDto.class);
		return new ResponseEntity<CatDto>(catDto,HttpStatus.ACCEPTED);
	}
	
	@DeleteMapping("/{catId}")
	public ResponseEntity<ApiResponse> delCat(@PathVariable Long catId){
		return new ResponseEntity<ApiResponse>(catService.delCat(catId),HttpStatus.ACCEPTED);
	}
}
