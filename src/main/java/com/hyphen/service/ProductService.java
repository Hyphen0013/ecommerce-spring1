package com.hyphen.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.hyphen.exception.ProductException;
import com.hyphen.model.Product;
import com.hyphen.request.CreateProductRequest;

public interface ProductService {
	public Product createProduct(CreateProductRequest req);

	public String deleteProduct(Long productId) throws ProductException;

	public Product updateProduct(Long productId, Product reqProduct) throws ProductException;

	public Product findProductById(Long productId) throws ProductException;

	public List<Product> findProductByCategory(String category);

	public Page<Product> getAllProduct(
		String category, 
		List<String> colors, 
		List<String> sizes, 
		Integer minPrice, 
		Integer maxPrice,
		Integer minDiscount,
		String sort,
		String stock,
		Integer pageNumber,
		Integer pageSize
	);

	public List<Product> findAllProducts();
}
