package com.hyphen.service;

import java.util.List;

import com.hyphen.exception.ProductException;
import com.hyphen.model.Review;
import com.hyphen.model.User;
import com.hyphen.request.ReviewRequest;

public interface ReviewService {

	public Review createReview(ReviewRequest req, User user) throws ProductException;
	
	public List<Review> getAllReviews(Long productId);
}
