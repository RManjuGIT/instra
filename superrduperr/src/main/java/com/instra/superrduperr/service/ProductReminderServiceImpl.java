package com.instra.superrduperr.service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.instra.superrduperr.dao.bean.UserProductReminder;
import com.instra.superrduperr.exception.SuperrDuperrException;
import com.instra.superrduperr.models.ProductReminder;
import com.instra.superrduperr.repository.ProductReminderRepository;
import com.instra.superrduperr.repository.UserRepository;

@Service
public class ProductReminderServiceImpl implements ProductReminderService {

	private static Logger logger = LoggerFactory.getLogger(ProductReminderServiceImpl.class);
	
	@Autowired
	private ProductReminderRepository productReminderRepo;
	
	@Autowired
	private UserRepository userRepo;
	
	@Override
	public void addReminderToProduct(int userId, ProductReminder productReminder) throws SuperrDuperrException {
		if(Objects.isNull(userRepo.getOne(userId))) {
			throw new SuperrDuperrException("400 Bad Request", String.format("%d is invalid", userId));
		}
		if(Objects.isNull(productReminder.getProductIds()) || productReminder.getProductIds().isEmpty()){
			throw new SuperrDuperrException("400 Bad Request", "There are no productIds");
		}
		List<UserProductReminder> userReminders = productReminder.getProductIds().stream().map(productId ->{
			UserProductReminder reminder = new UserProductReminder();
			reminder.setUserId(userId);
			reminder.setProductId(productId);
			reminder.setName(productReminder.getName());
			return reminder;
		}).collect(Collectors.toList());
		productReminderRepo.saveAll(userReminders);
		logger.debug("Saved the reminder to all products");
	}

	@Override
	public ProductReminder getProductReminderForUser(int userId) throws SuperrDuperrException {
		List<UserProductReminder> productReminders = productReminderRepo.findUserProductReminderForUserId(userId);
		if(Objects.isNull(productReminders) || productReminders.isEmpty()) {
			throw new SuperrDuperrException("400 Bad Request", "There are no reminders for user");
		}
		ProductReminder productReminder = new ProductReminder();
		productReminder.setName(productReminders.get(0).getName());
		List<Integer> productIds = productReminders.stream().map(UserProductReminder::getProductId).collect(Collectors.toList());
		logger.debug(productIds.size()+" products got the reminder");
		productReminder.setProductIds(productIds);
		return productReminder;
	}

}
