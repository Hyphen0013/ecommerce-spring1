package com.hyphen.service;


import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.hyphen.exception.ProductException;
import com.hyphen.model.Category;
import com.hyphen.model.Product;
import com.hyphen.repository.CategoryRepository;
import com.hyphen.repository.ProductRepository;
import com.hyphen.request.CreateProductRequest;

@Service
public class ProductServiceImplementation implements ProductService {
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	public ProductServiceImplementation(
			ProductRepository productRepository,
			UserService userService,
			CategoryRepository categoryRepository
	) {
		this.productRepository = productRepository;
		this.userService = userService;
		this.categoryRepository = categoryRepository;
	}

	@Override
	public Product createProduct(CreateProductRequest req) {
		Category topLevel = categoryRepository.findByName(req.getTopLevelCategory());
		
		if(topLevel == null) {
			Category topLevelCategory = new Category();
			topLevelCategory.setName(req.getTopLevelCategory());
			topLevelCategory.setLevel(1);
			
			topLevel = categoryRepository.save(topLevelCategory);
		}
		
		Category secondLevel = categoryRepository
				.findByNameAndParent(req.getSecondLevelCategory(), topLevel.getName());
		
		if(secondLevel == null) {
			Category secondLevelCategory = new Category();
			secondLevelCategory.setName(req.getSecondLevelCategory());
			secondLevelCategory.setParentCategory(topLevel);
			secondLevelCategory.setLevel(2);
			
			secondLevel = categoryRepository.save(secondLevelCategory);			
		}
		
		Category thirdLevel = categoryRepository
				.findByNameAndParent(req.getThirdLevelCategory(), secondLevel.getName());
		
		if(thirdLevel == null) {
			Category thridLevelCategory = new Category();
			thridLevelCategory.setName(req.getThirdLevelCategory());
			thridLevelCategory.setParentCategory(topLevel);
			thridLevelCategory.setLevel(3);
			
			thirdLevel = categoryRepository.save(thridLevelCategory);				
		}
		
		Product product = new Product();
		
		product.setTitle(req.getTitle());
		product.setDescription(req.getDescription());
		product.setPrice(req.getPrice());
		product.setDiscountedPrice(req.getDiscountedPrice());
		product.setDiscountedPercent(req.getDiscountedPercent());
		product.setQuantity(req.getQuantity());
		product.setBrand(req.getBrand());
		product.setColor(req.getColor());
		product.setSizes(req.getSizes());
		product.setImageUrl(req.getImageUrl());
		product.setCategory(thirdLevel);
		product.setCreatedAt(LocalDateTime.now());
		
		Product savedProduct = productRepository.save(product);
		return savedProduct;
	}

	@Override
	public String deleteProduct(Long productId) throws ProductException {
		Product product = findProductById(productId);
		product.getSizes().clear();
		productRepository.delete(product);
		return "Product deleted successfully";
	}

	@Override
	public Product updateProduct(Long productId, Product reqProduct) throws ProductException {
		Product product = findProductById(productId);
		if(reqProduct.getQuantity() != 0) {
			product.setQuantity(reqProduct.getQuantity());
		}
		return productRepository.save(product);
	}

	@Override
	public Product findProductById(Long productId) throws ProductException {
		Optional<Product> optional = productRepository.findById(productId);
		if(optional.isPresent()) {
			return optional.get();
		}
		throw new ProductException("Product not found with id: " + productId);
	}

	@Override
	public List<Product> findProductByCategory(String category) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
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
	) {
		Pageable pageable = PageRequest.of(pageNumber, pageSize);
		
		List<Product> products = productRepository.filterProducts(category, minPrice, maxPrice, minDiscount, sort);
		
		if(!colors.isEmpty()) {
			products = products
					.stream()
					.filter(p->colors.stream()
							.anyMatch(c->c.equalsIgnoreCase(p.getColor()))
					)
					.collect(Collectors.toList());
		}
		
		if(stock != null) {
			if(stock.equals("in_stock")) {
				products = products
						.stream()
						.filter(p->p.getQuantity()>0)
						.collect(Collectors.toList());
			} else if(stock.equals("out_of_stock")) {
				products = products
						.stream()
						.filter(p->p.getQuantity()<1)
						.collect(Collectors.toList());
			}
		}
		
		int startIndex = (int) pageable.getOffset();
		int endIndex = Math.min(startIndex + pageable.getPageSize(), products.size());
		
		List<Product> pageContent = products.subList(startIndex, endIndex);
		
		Page<Product> filteredProducts = new PageImpl<>(pageContent, pageable, products.size());
		
		return filteredProducts;
	}

	@Override
	public List<Product> findAllProducts() {
		// TODO Auto-generated method stub
		return null;
	}

}
