package com.hyphen.service;

import java.util.List;

import com.hyphen.exception.OrderException;
import com.hyphen.model.Address;
import com.hyphen.model.OrderItem;
import com.hyphen.model.Orders;
import com.hyphen.model.User;

public interface OrderService {
	
	public Orders createOrder(User user, Address shippingAddress);
	
	public Orders findOrderById(Long orderId) throws OrderException;
	
	public List<OrderItem> findOrderItemsById(Long orderId) throws OrderException;
	
	public List<Orders> usersOrderHistory(Long userId);
	
	public Orders placedOrder(Long orderId) throws OrderException;
	
	public Orders confirmOrder(Long orderId) throws OrderException;
	
	public Orders shippedOrder(Long orderId) throws OrderException;
	
	public Orders deliveryOrder(Long orderId) throws OrderException;
	
	public Orders cancelOrder(Long orderId) throws OrderException;
	
	public List<Orders> getAllOrders();
	
	public void deleteOrder(Long orderId) throws OrderException;
}
