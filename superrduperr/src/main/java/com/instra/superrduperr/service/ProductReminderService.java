package com.instra.superrduperr.service;

import com.instra.superrduperr.exception.SuperrDuperrException;
import com.instra.superrduperr.models.ProductReminder;

public interface ProductReminderService {

	void addReminderToProduct(int userId, ProductReminder productReminder) throws SuperrDuperrException;
	ProductReminder getProductReminderForUser(int userId) throws SuperrDuperrException;
}
