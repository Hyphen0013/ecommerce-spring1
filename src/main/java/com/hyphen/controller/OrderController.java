package com.hyphen.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hyphen.exception.OrderException;
import com.hyphen.exception.UserException;
import com.hyphen.model.Address;
import com.hyphen.model.OrderItem;
import com.hyphen.model.Orders;
import com.hyphen.model.User;
import com.hyphen.service.OrderService;
import com.hyphen.service.UserService;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

	@Autowired
	private OrderService orderService;
	
	@Autowired
	private UserService userService;
	
	@PostMapping("/")
	public ResponseEntity<Orders> createOrder(
			@RequestBody Address shippingAddress,
			@RequestHeader("Authorization") String jwt
	) throws UserException {
		
		User user = userService.findUserProfileByJwt(jwt);
		Orders order = orderService.createOrder(user, shippingAddress);
		
		return new ResponseEntity<Orders>(order, HttpStatus.CREATED);
	}
	
	@GetMapping("/user")
	public ResponseEntity<List<Orders>> userOrderHistory(
			@RequestHeader("Authorization") String jwt
	) throws UserException {
		
		User user = userService.findUserProfileByJwt(jwt);
		List<Orders> order = orderService.usersOrderHistory(user.getId());
		
		return new ResponseEntity<>(order, HttpStatus.CREATED);
	}	
	
	@GetMapping("/{id}")
	public ResponseEntity<List<OrderItem>> findOrderById(
			@PathVariable("id") Long orderId,
			@RequestHeader("Authorization") String jwt
	) throws UserException, OrderException {
		
		User user = userService.findUserProfileByJwt(jwt);
//		Orders order = orderService.findOrderById(orderId);
		
		List<OrderItem> order = orderService.findOrderItemsById(orderId);
		
		return new ResponseEntity<>(order, HttpStatus.ACCEPTED);
	}	
}
