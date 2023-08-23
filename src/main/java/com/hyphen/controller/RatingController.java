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

import com.hyphen.exception.ProductException;
import com.hyphen.exception.UserException;
import com.hyphen.model.Rating;
import com.hyphen.model.User;
import com.hyphen.request.RatingRequest;
import com.hyphen.service.RatingService;
import com.hyphen.service.UserService;

@RestController
@RequestMapping("/api/ratings")
public class RatingController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private RatingService ratingService;
	
	@PostMapping("/create")
	public ResponseEntity<Rating> finUserCart(
			@RequestBody RatingRequest req,
			@RequestHeader("Authorization") String jwt
	) throws UserException, ProductException {
		
		User user = userService.findUserProfileByJwt(jwt);
		Rating rating = ratingService.createRating(req, user);
		return new ResponseEntity<Rating>(rating, HttpStatus.CREATED);
	}
	
	@GetMapping("/product/{productId}")
	public ResponseEntity<List<Rating>> getProductRating(
			@PathVariable Long productId,
			@RequestHeader("Authorization") String jwt
	) throws UserException, ProductException {
		
		User user = userService.findUserProfileByJwt(jwt);
		List<Rating> rating = ratingService.getProductsRating(productId);
		return new ResponseEntity<>(rating, HttpStatus.CREATED);
	}
}
