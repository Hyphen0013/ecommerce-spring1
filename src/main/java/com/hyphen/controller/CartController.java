package com.hyphen.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hyphen.annotation.CustomTag;
import com.hyphen.exception.ProductException;
import com.hyphen.exception.UserException;
import com.hyphen.model.Cart;
import com.hyphen.model.User;
import com.hyphen.request.AddItemRequest;
import com.hyphen.response.ApiResponse;
import com.hyphen.service.CartService;
import com.hyphen.service.UserService;

@RestController
@RequestMapping("/api/cart")
@CustomTag(name="Cart Management", description="find user cart, add item to cart")
//@Tag(name="Cart Management", description="find user cart, add item to cart")
public class CartController {

	@Autowired
	private CartService cartService;
	
	@Autowired
	private UserService userService;
	
	@GetMapping("/")
//	@Operation(description = "find cart by user id")
	public ResponseEntity<Cart> finUserCart(
			@RequestHeader("Authorization") String jwt
	) throws UserException {
		
		User user = userService.findUserProfileByJwt(jwt);
		Cart cart = cartService.findUserCart(user.getId());
		return new ResponseEntity<Cart>(cart, HttpStatus.OK);
	}
	
	@PutMapping("/add")
//	@Operation(description = "Add item to cart")
	public ResponseEntity<ApiResponse> addItemToCart(
		@RequestBody AddItemRequest req,
		@RequestHeader("Authorization") String jwt
	) throws UserException, ProductException {
		User user = userService.findUserProfileByJwt(jwt);
		
		cartService.addCartItem(user.getId(), req);
		
		ApiResponse res = new ApiResponse();
		res.setMessage("Item added to cart successfully");
		res.setStatus(true);
		
		return new ResponseEntity<>(res, HttpStatus.OK);
	}
	
	
	
	
	
	
}
