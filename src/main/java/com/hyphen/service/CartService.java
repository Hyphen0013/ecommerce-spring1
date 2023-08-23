package com.hyphen.service;

import com.hyphen.exception.ProductException;
import com.hyphen.model.Cart;
import com.hyphen.model.User;
import com.hyphen.request.AddItemRequest;

public interface CartService {
	
	public Cart createCart(User user);
	
	public String addCartItem(Long userId, AddItemRequest req) throws ProductException;
	
	public Cart findUserCart(Long userId);
}
