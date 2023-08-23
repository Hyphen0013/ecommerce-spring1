package com.hyphen.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hyphen.exception.ProductException;
import com.hyphen.model.Product;
import com.hyphen.service.ProductService;

@RestController
@RequestMapping("/api")
public class ProductController {

	@Autowired
	private ProductService productService;
	
	@GetMapping("/products")
	public ResponseEntity<Page<Product>> findProductByCategoryHandler(
		@RequestParam String category,
		@RequestParam List<String> color,
		@RequestParam List<String> size,
		@RequestParam Integer minPrice,
		@RequestParam Integer maxPrice,
		@RequestParam Integer minDiscount,
		@RequestParam String sort,
		@RequestParam String stock,
		@RequestParam Integer pageNumber,
		@RequestParam Integer pageSize
	) {
		Page<Product> res = productService
				.getAllProduct(
					category, 
					color, 
					size, 
					minPrice, 
					maxPrice, 
					minDiscount, 
					sort, 
					stock, 
					pageNumber, 
					pageSize
		);	
		System.out.println(res);
		return new ResponseEntity<>(res, HttpStatus.ACCEPTED);
	}
	
	@GetMapping("/products/id/{productId}")
	public ResponseEntity<Product> findProductByIdHandler(
			@PathVariable Long productId
	) throws ProductException {
		
		Product product = productService.findProductById(productId);
		
		return new ResponseEntity<Product>(product, HttpStatus.ACCEPTED);
	}
	
//	@GetMapping("/products/search")
//	public ResponseEntity<List<Product>> searchProductHandler(
//			@RequestParam String q
//	) throws ProductException {
//		
//		List<Product> product = productService.searchProduct(q);
//		
//		return new ResponseEntity<List<Product>>(product, HttpStatus.OK);
//	}
}