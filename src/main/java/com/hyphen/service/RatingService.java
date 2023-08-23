package com.hyphen.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.hyphen.exception.ProductException;
import com.hyphen.model.Rating;
import com.hyphen.model.User;
import com.hyphen.request.RatingRequest;

@Service
public interface RatingService {

	public Rating createRating(RatingRequest req, User user) throws ProductException;
	
	public List<Rating> getProductsRating(Long productId);
}
