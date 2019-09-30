package com.instra.superrduperr.resource;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.instra.superrduperr.exception.SuperrDuperrException;
import com.instra.superrduperr.models.Product;
import com.instra.superrduperr.service.ProductService;

@RestController
@RequestMapping("/product")
public class ProductController {
	
	@Autowired
	private ProductService productService;

	@GetMapping(value = "/list",
			produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Product> getProducts() throws SuperrDuperrException {
		return productService.getAllProducts();
	}
	
	@PutMapping(value = "/markcomplete",
			consumes = MediaType.APPLICATION_JSON_VALUE)
	public void markProductAsCompleted(@RequestBody List<Integer> productIds) throws SuperrDuperrException {
		productService.markProductAsComplete(productIds);
	}
	
}
