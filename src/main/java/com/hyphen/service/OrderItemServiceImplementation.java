package com.hyphen.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hyphen.model.OrderItem;
import com.hyphen.repository.OrderItemRepository;

@Service
public class OrderItemServiceImplementation implements OrderItemService {
	
	@Autowired
	private OrderItemRepository orderItemRepository;
	
	public OrderItemServiceImplementation(
		OrderItemRepository orderItemRepository
	) {
		this.orderItemRepository = orderItemRepository;
	}
	@Override
	public OrderItem createOrderItem(OrderItem orderItem) {
		return orderItemRepository.save(orderItem);
	}

}
