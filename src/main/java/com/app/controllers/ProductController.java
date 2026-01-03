package com.app.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.dtos.ProductDto;
import com.app.dtos.ProductDto2;
import com.app.entities.Category;
import com.app.entities.Products;
import com.app.entities.User;
import com.app.response.ApiResponse;
import com.app.service.CatService;
import com.app.service.FavService;
import com.app.service.ProductService;
import com.app.service.UserService;

@RestController
@RequestMapping("/products")
public class ProductController {

	@Autowired
	private ProductService productService;
	@Autowired
	private FavService favService;
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private CatService catService;

	@PostMapping("/add")
	public ResponseEntity<ApiResponse> addProduct(@RequestBody ProductDto productDto) {
		Products product = mapToEntity(productDto);
		ApiResponse apiResponse = productService.addProduct(product);
		return ResponseEntity.ok(apiResponse);
	}

	@GetMapping
	public ResponseEntity<List<ProductDto>> getProducts() {
		List<Products> products = productService.getProducts();
//		System.out.println(products+"hello buddy.");
		List<ProductDto> productDto = products.stream().map(p -> modelMapper.map(p, ProductDto.class))
				.collect(Collectors.toList());
		return ResponseEntity.ok(productDto);
	}
	
	@GetMapping("/getfevs/{userId}")
	public ResponseEntity<List<ProductDto>> getFavoriteProductsByUser(@PathVariable Long userId){
		List<Products> list = favService.getFavoriteProductsByUser(userId);
		List<ProductDto> listDto = list.stream().map(p->modelMapper.map(p, ProductDto.class)).collect(Collectors.toList());
		return ResponseEntity.ok(listDto);
	}
	
	@GetMapping("/{bid}")
	public ResponseEntity<ProductDto2> getProduct(@PathVariable("bid") Long bid){
		Products product = productService.getProduct(bid);
		ProductDto2 productDto = modelMapper.map(product, ProductDto2.class);
		return ResponseEntity.ok(productDto);
	}
	
	
	@GetMapping("/productname/{bname}")
	public ResponseEntity<ProductDto2> getProduct(@PathVariable String bname){
		Products product = productService.getProductName(bname);
		ProductDto2 productDto2 = modelMapper.map(product, ProductDto2.class);
		return ResponseEntity.ok(productDto2);
	}
	
	@PostMapping("/addtofav/{bid}/{uid}")
	public ResponseEntity<ApiResponse> addProduct(@PathVariable Long bid,@PathVariable Long uid){
		ApiResponse api = productService.addToFav(bid, uid);
		return ResponseEntity.ok(api);
	}
	
	public Products mapToEntity(ProductDto productDto) {
		Products product = new Products();
		
		product.setProductName(productDto.getProductName());
		product.setDescription(productDto.getDescription());
		product.setQuantity(productDto.getQuantity());
		product.setPrice(productDto.getPrice());
		product.setImgUrl(productDto.getImgUrl());
		
		User user = userService.getById(productDto.getSellerId().getUserId());
		Category category = catService.getById(productDto.getCategoryId().getCategoryId());
		product.setSeller(user);
		product.setCategory(category);
		return product;
	}
}
