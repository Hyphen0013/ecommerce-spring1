package com.hyphen.service;

import com.hyphen.exception.CartItemException;
import com.hyphen.exception.UserException;
import com.hyphen.model.Cart;
import com.hyphen.model.CartItem;
import com.hyphen.model.Product;

public interface CartItemService {

	public CartItem createCartItem(CartItem cartItem);
	
	public CartItem updateCartItem(Long userId, Long id, CartItem cartItem) throws CartItemException, UserException;
	
	public CartItem isCartItemExist(Cart cart, Product product, String size, Long userId);
	
	public void removeCartItem(Long userId, Long cartItemId) throws CartItemException, UserException;
	
	public CartItem findCartItemById(Long cartItemId) throws CartItemException;
}
