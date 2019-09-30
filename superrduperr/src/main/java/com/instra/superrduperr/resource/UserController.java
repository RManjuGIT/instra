package com.instra.superrduperr.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.instra.superrduperr.exception.SuperrDuperrException;
import com.instra.superrduperr.models.ProductReminder;
import com.instra.superrduperr.service.ProductReminderService;

@RestController
@RequestMapping(value = "/user")
public class UserController {

	@Autowired
	private ProductReminderService productReminderService;
	@PutMapping(value = "/addproductreminder/{userId}",
			consumes = MediaType.APPLICATION_JSON_VALUE)
	public void addRemindersToProductsForUser(@PathVariable("userId") Integer userId,
			@RequestBody ProductReminder productReminder) throws SuperrDuperrException {
		productReminderService.addReminderToProduct(userId, productReminder);
	}
	
	@GetMapping(value = "/productreminder/{userId}",
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ProductReminder getRemiderForProducts(@PathVariable("userId") Integer userId) throws SuperrDuperrException {
		return productReminderService.getProductReminderForUser(userId);
	}
}
