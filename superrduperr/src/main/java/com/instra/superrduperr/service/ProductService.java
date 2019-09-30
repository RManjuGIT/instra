package com.instra.superrduperr.service;

import java.util.List;

import com.instra.superrduperr.exception.SuperrDuperrException;
import com.instra.superrduperr.models.Product;

public interface ProductService {

	List<Product> getAllProducts() throws SuperrDuperrException;
	void markProductAsComplete(List<Integer> productIds) throws SuperrDuperrException;
}
