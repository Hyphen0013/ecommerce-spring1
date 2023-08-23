package com.hyphen.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hyphen.exception.OrderException;
import com.hyphen.model.Orders;
import com.hyphen.response.ApiResponse;
import com.hyphen.service.OrderService;

@RestController
@RequestMapping("/api/admin/orders")
public class AdminOrderController {

	@Autowired
	private OrderService orderService;
	
	@GetMapping("/")
	public ResponseEntity<List<Orders>> getAllOrdersHandler() {
		List<Orders> orders = orderService.getAllOrders();
		
		return new ResponseEntity<List<Orders>>(orders, HttpStatus.ACCEPTED);
	}
	
	@PutMapping("/{orderId}/confirmed")
	public ResponseEntity<Orders> ConfirmedOrderHandler(
			@PathVariable Long orderId,
			@RequestHeader("Authorization") String jwt
			
	) throws OrderException {
		Orders orders = orderService.confirmOrder(orderId);
		return new ResponseEntity<>(orders, HttpStatus.OK);
	}
	
	@PutMapping("/{orderId}/ship")
	public ResponseEntity<Orders> ShippedOrderHander(
			@PathVariable Long orderId,
			@RequestHeader("Authorization") String jwt
	) throws OrderException {
		Orders orders = orderService.shippedOrder(orderId);
		return new ResponseEntity<>(orders, HttpStatus.OK);
	}
	
	
	@PutMapping("/{orderId}/deliver")
	public ResponseEntity<Orders> DeliverOrderHandler(
			@PathVariable Long orderId,
			@RequestHeader("Authorization") String jwt
	) throws OrderException {
		Orders orders = orderService.deliveryOrder(orderId);
		return new ResponseEntity<>(orders, HttpStatus.OK);	
	}
	
	@PutMapping("/{orderId}/cancel")
	public ResponseEntity<Orders> CancelOrderHandler(
			@PathVariable Long orderId,
			@RequestHeader("Authorization") String jwt
	) throws OrderException {
		Orders orders = orderService.cancelOrder(orderId);
		return new ResponseEntity<>(orders, HttpStatus.OK);	
	}
	
	
	@DeleteMapping("/{orderId}/delete")
	public ResponseEntity<ApiResponse> DeleteOrderHandler(
			@PathVariable Long orderId,
			@RequestHeader("Authorization") String jwt
	) throws OrderException {
		orderService.deleteOrder(orderId);
		
		ApiResponse res = new ApiResponse();
		res.setMessage("Order deleted successfully");
		res.setStatus(true);
		
		return new ResponseEntity<>(res, HttpStatus.OK);
	}
}



