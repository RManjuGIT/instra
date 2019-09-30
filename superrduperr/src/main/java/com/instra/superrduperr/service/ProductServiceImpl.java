package com.instra.superrduperr.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.instra.superrduperr.dao.bean.Products;
import com.instra.superrduperr.exception.SuperrDuperrException;
import com.instra.superrduperr.models.Product;
import com.instra.superrduperr.repository.ProductRepository;

@Service
public class ProductServiceImpl implements ProductService {
	
	@Autowired
	private ProductRepository productRepo;
	
	@Override
	public List<Product> getAllProducts() throws SuperrDuperrException {
		List<Products> products = productRepo.findAll();
		List<Product> productList = new ArrayList<>();
		products.forEach(prod -> {
			productList.add(new Product(prod.getId(),prod.getName(), (prod.getQuantity()>0), prod.getPrice()));
		});
		if(productList.isEmpty()) {
			throw new SuperrDuperrException("404 Not Found", "Products are unavailable");
		}
		return productList;
	}

	@Transactional
	@Override
	public void markProductAsComplete(List<Integer> productIds) throws SuperrDuperrException {
		if(Objects.isNull(productIds) || productIds.isEmpty()) {
			throw new SuperrDuperrException("400 Bad Request", "No products to mark as complete");
		}
		int rowsUpdated = productRepo.updateQuantityToZeroById(productIds);
		if(rowsUpdated<=0) {
			throw new SuperrDuperrException("417 Expectation Failed", "Could not update product please check the product id");
		}
	}

}
