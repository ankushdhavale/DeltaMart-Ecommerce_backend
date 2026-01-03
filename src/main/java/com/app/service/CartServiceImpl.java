package com.app.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.dtos.CartDto;
import com.app.dtos.CartItemDto;
import com.app.dtos.ProductDto2;
import com.app.entities.Cart;
import com.app.entities.CartItem;
import com.app.entities.Products;
import com.app.entities.User;
import com.app.exceptions.APIException;
import com.app.exceptions.ResourceNotFoundException;
import com.app.repository.CartItemRepo;
import com.app.repository.CartRepo;
import com.app.repository.ProductRepo;
import com.app.repository.UserRepo;
import com.app.response.ApiResponse;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class CartServiceImpl implements CartService {

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private ProductRepo productRepo;
	@Autowired
	private CartRepo cartRepo;

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private CartItemRepo cartItemRepo;

	@Override
	public void addCart(Cart cart) {
		cartRepo.save(cart);
	}

	@Override
	public CartDto addProductToCart(Long cartId, Long productId, Integer quantity) {
		Cart cart = cartRepo.findById(cartId)
				.orElseThrow(() -> new ResourceNotFoundException("Cart", "CartId", cartId));

		Products product = productRepo.findById(productId)
				.orElseThrow(() -> new ResourceNotFoundException("Product", "productId", productId));
		System.out.println("============Product Found==================");

		CartItem cartItem = cartItemRepo.findCartItemByCartAndProduct(cart, product);
		if (cartItem != null) {
			cartItem.setQuantity(cartItem.getQuantity() + quantity);
			cartItemRepo.save(cartItem);

			product.setQuantity(product.getQuantity() - quantity);

			cart.setTotalPrice((cart.getTotalPrice() + cartItem.getProductPrice()) * quantity);
			cartRepo.save(cart);

			CartDto cartDto = modelMapper.map(cart, CartDto.class);

			List<ProductDto2> productDto = cart.getCartItem().stream()
					.map(p -> modelMapper.map(p.getProduct(), ProductDto2.class)).collect(Collectors.toList());

			cartDto.setProducts(productDto);

			return cartDto;
		} else {

			CartItem newCartItem = new CartItem();
			newCartItem.setCart(cart);

			if (product.getQuantity() == 0) {
				throw new APIException(product.getProductName() + " is Not Available.");
			}

			if (product.getQuantity() < quantity) {
				throw new APIException("Please, make an order of the " + product.getProductName()
						+ " less than or equal to the quantity " + product.getQuantity() + ".");
			}

			newCartItem.setProduct(product);
			newCartItem.setCart(cart);
			newCartItem.setQuantity(quantity);
			newCartItem.setProductPrice(product.getPrice());

			cartItemRepo.save(newCartItem);

			product.setQuantity(product.getQuantity() - quantity);

			cart.setTotalPrice((cart.getTotalPrice() + newCartItem.getProductPrice()) * quantity);
			cartRepo.save(cart);

			CartDto cartDto = modelMapper.map(cart, CartDto.class);

			List<ProductDto2> productDtos = cart.getCartItem().stream()
					.map(p -> modelMapper.map(p.getProduct(), ProductDto2.class)).collect(Collectors.toList());

			cartDto.setProducts(productDtos);

			return cartDto;
		}
	}

	@Override
	public List<CartDto> getAllCarts() {
		List<Cart> carts = cartRepo.findAll();
		if (carts.size() == 0) {
			throw new APIException("No cart exist.");
		}

		List<CartDto> cartDtos = carts.stream().map(cart -> {
			CartDto cartDto = modelMapper.map(cart, CartDto.class);

			List<ProductDto2> products = cart.getCartItem().stream()
					.map(p -> modelMapper.map(p.getProduct(), ProductDto2.class)).collect(Collectors.toList());

			cartDto.setProducts(products);
			return cartDto;
		}).collect(Collectors.toList());

		return cartDtos;
	}

	@Override
	public String deleteProductFromCart(Long cartId, Long productId) {
		Cart cart = cartRepo.findById(cartId)
				.orElseThrow(() -> new ResourceNotFoundException("Cart", "cartId", cartId));

		Products product1 = productRepo.findById(productId)
				.orElseThrow(() -> new ResourceNotFoundException("products", "ProductId", productId));

		CartItem cartItem = cartItemRepo.findByProductAndCart(cart, product1);
		if (cartItem == null) {
			throw new ResourceNotFoundException("Products", "productId", productId);
		}

		cart.setTotalPrice(cart.getTotalPrice() - (cartItem.getProductPrice() * cartItem.getQuantity()));

		cartRepo.save(cart);

		Products product = cartItem.getProduct();
		product.setQuantity(product.getQuantity() + cartItem.getQuantity());
		productRepo.save(product);
		cartItemRepo.deleteCartItemByProductAndCart(cart, product);

		return "products " + cartItem.getProduct().getProductName() + " remove from the cart..";
	}

	@Override
	public CartDto getCart(String emailId) {
		User user = userRepo.findByEmail(emailId).orElseThrow(() -> new ResourceNotFoundException("Email Not Found."));
		Cart cart = cartRepo.findByUser(user);

		if (cart == null) {
			throw new ResourceNotFoundException("User", "EmailId", emailId);
		}

		CartDto cartDto = modelMapper.map(cart, CartDto.class);

		List<ProductDto2> products = cart.getCartItem().stream()
				.map(p -> modelMapper.map(p.getProduct(), ProductDto2.class)).collect(Collectors.toList());

		cartDto.setProducts(products);
		return cartDto;
	}

	@Override
	public CartDto getCartById(Long cartId) {
		Cart cart = cartRepo.findById(cartId).get();

		CartDto cartDto = modelMapper.map(cart, CartDto.class);

		List<ProductDto2> products = cart.getCartItem().stream()
				.map(p -> modelMapper.map(p.getProduct(), ProductDto2.class)).collect(Collectors.toList());

		cartDto.setProducts(products);
		return cartDto;
	}

	@Override
	public List<CartItemDto> getCartItemsById(Long cartId) {
		Cart cart = cartRepo.findById(cartId).orElseThrow(()-> new ResourceNotFoundException("Cart Not Found."));
		List<CartItem> cartList = cart.getCartItem();

		List<CartItemDto> cartItems = cartList.stream().map(p -> {
			CartItemDto cdtos = modelMapper.map(p, CartItemDto.class);
			cdtos.setProduct(modelMapper.map(p.getProduct(), ProductDto2.class));
			return cdtos;
		}).collect(Collectors.toList());
		
		return cartItems;
	}

	@Override
	public ApiResponse updateItems(Long cartItemId, Long productId, Integer quantity) {
		Products product = productRepo.findById(productId).get();

		CartItem cartItem = cartItemRepo.findByCartItemIdAndProduct(cartItemId, product);
		cartItem.setQuantity(cartItem.getQuantity() + quantity);
		product.setQuantity(product.getQuantity() + quantity);
		productRepo.save(product);

		Cart cart = cartItem.getCart();

		cart.setTotalPrice(cart.getTotalPrice() + (product.getPrice() * quantity));
		cartRepo.save(cart);
		cartItemRepo.save(cartItem);
		return new ApiResponse("Quantity +/- Updated.");
	}

}
