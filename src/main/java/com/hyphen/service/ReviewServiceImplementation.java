package com.hyphen.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hyphen.exception.ProductException;
import com.hyphen.model.Product;
import com.hyphen.model.Review;
import com.hyphen.model.User;
import com.hyphen.repository.ProductRepository;
import com.hyphen.repository.ReviewRepository;
import com.hyphen.request.ReviewRequest;

@Service
public class ReviewServiceImplementation implements ReviewService {
	
	@Autowired
	private ReviewRepository reviewRepository;
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private ProductRepository productRepository;
	
	public ReviewServiceImplementation(
			ReviewRepository reviewRepository,
			ProductService productService,
			ProductRepository productRepository
	) {
		this.reviewRepository = reviewRepository;
		this.productService = productService;
		this.productRepository = productRepository;
	}

	@Override
	public Review createReview(ReviewRequest req, User user) throws ProductException {
		Product product = productService.findProductById(req.getProductId());
		
		Review review = new Review();
		review.setUser(user);
		review.setProduct(product);
		review.setReview(req.getReview());
		review.setCreatedAt(LocalDateTime.now());
		
		return reviewRepository.save(review);
	}

	@Override
	public List<Review> getAllReviews(Long productId) {
		return reviewRepository.getAllProductsReview(productId);
	}
	
}
