package com.hyphen.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hyphen.exception.OrderException;
import com.hyphen.model.Address;
import com.hyphen.model.Cart;
import com.hyphen.model.CartItem;
import com.hyphen.model.OrderItem;
import com.hyphen.model.Orders;
import com.hyphen.model.User;
import com.hyphen.repository.AddressRepository;
import com.hyphen.repository.CartRepository;
import com.hyphen.repository.OrderItemRepository;
import com.hyphen.repository.OrderRepository;
import com.hyphen.repository.UserRepository;

@Service
public class OrderServiceImplementation implements OrderService {
	
	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private CartService cartService;
	
	@Autowired
	private AddressRepository addressRepository;
	
	@Autowired
	private UserRepository userRepository;	
	
	@Autowired
	private OrderItemService orderItemService;	
	
	@Autowired
	private OrderItemRepository orderItemRepository;
	
	public OrderServiceImplementation(
		OrderRepository orderRepository,
		CartRepository cartRepository,
		CartService cartService,
		AddressRepository addressRepository,
		UserRepository userRepository,
		OrderItemRepository orderItemRepository
	) {
		this.orderRepository = orderRepository;
		this.addressRepository = addressRepository;
		this.userRepository = userRepository;
		this.cartService = cartService;
		this.orderItemRepository = orderItemRepository;
	}

	@Override
	public Orders createOrder(User user, Address shippingAddress) {
		shippingAddress.setUser(user);
		
		Address address = addressRepository.save(shippingAddress);
		
		user.getAddress().add(address);
		userRepository.save(user);
		
		Cart cart = cartService.findUserCart(user.getId());
		List<OrderItem> orderItems = new ArrayList<>();
		
		for(CartItem item : cart.getCartItems()) {
			OrderItem orderItem = new OrderItem();
			
			orderItem.setPrice(item.getPrice());
			orderItem.setProduct(item.getProduct());
			orderItem.setQuantity(item.getQuantity());
			orderItem.setSize(item.getSize());
			orderItem.setUserId(item.getUserId());
			orderItem.setDiscountedPrice(item.getDiscountedPrice());
			
			OrderItem createdOrderItem = orderItemRepository.save(orderItem);
			
			orderItems.add(createdOrderItem);
			
		}
		
		Orders createdOrder = new Orders();
		createdOrder.setUser(user);
		createdOrder.setOrderItems(orderItems);
		createdOrder.setTotalPrice(cart.getTotalPrice());
		createdOrder.setTotalDiscountedPrice(cart.getTotalDiscountedPrice());
		createdOrder.setDiscount(cart.getDiscount());
		createdOrder.setTotalItem(cart.getTotalItem());
		
		createdOrder.setShippingAddress(address);
		createdOrder.setOrderDate(LocalDateTime.now());
		createdOrder.setOrderStatus("PENDING");
		createdOrder.getPaymentDetails().setStatus("PENDING");
		createdOrder.setCreatedAt(LocalDateTime.now());
		
		Orders savedOrder = orderRepository.save(createdOrder);
		
		for(OrderItem item : orderItems) {
			item.setOrder(savedOrder);
			orderItemRepository.save(item);
		}
		
		return savedOrder;
	}

	@Override
	public Orders findOrderById(Long orderId) throws OrderException {
		Optional<Orders> order = orderRepository.findById(orderId);
		
		if(order.isPresent()) {
			return order.get();
		}
		
		throw new OrderException("Order not exists with id: " + orderId);
	}
	
	@Override
	public List<OrderItem> findOrderItemsById(Long orderId) throws OrderException {
		Optional<Orders> order = orderRepository.findById(orderId);
		
		if(order.isPresent()) {
			return order.get().getOrderItems();
		}
		
		throw new OrderException("Order not exists with id: " + orderId);
	}	

	@Override
	public List<Orders> usersOrderHistory(Long userId) {
		List<Orders> orders = orderRepository.getUsersOrders(userId);
		
		return orders;
	}

	@Override
	public Orders placedOrder(Long orderId) throws OrderException {
		Orders order = findOrderById(orderId);
		order.setOrderStatus("PLACED");
		order.getPaymentDetails().setStatus("COMPLETED");
		return order;
	}

	@Override
	public Orders confirmOrder(Long orderId) throws OrderException {
		Orders order = findOrderById(orderId);
		order.setOrderStatus("CONFIRMED");
		return orderRepository.save(order);
	}

	@Override
	public Orders shippedOrder(Long orderId) throws OrderException {
		Orders order = findOrderById(orderId);
		order.setOrderStatus("SHIPPED");
		return orderRepository.save(order);
	}

	@Override
	public Orders deliveryOrder(Long orderId) throws OrderException {
		Orders order = findOrderById(orderId);
		order.setOrderStatus("DELIVERED");
		return orderRepository.save(order);
	}

	@Override
	public Orders cancelOrder(Long orderId) throws OrderException {
		Orders order = findOrderById(orderId);
		order.setOrderStatus("CANCELLED");
		return orderRepository.save(order);
	}

	@Override
	public List<Orders> getAllOrders() {
		return orderRepository.findAll();
	}

	@Override
	public void deleteOrder(Long orderId) throws OrderException {
		Orders order = findOrderById(orderId);
		orderRepository.deleteById(orderId);
	}

}
