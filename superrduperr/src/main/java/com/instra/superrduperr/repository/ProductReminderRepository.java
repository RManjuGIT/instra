package com.instra.superrduperr.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.instra.superrduperr.dao.bean.UserProductReminder;

@Repository
public interface ProductReminderRepository extends JpaRepository<UserProductReminder, Integer> {

	@Query("select upr from UserProductReminder upr where upr.userId = :userId")
	List<UserProductReminder> findUserProductReminderForUserId(@Param("userId") Integer userId);
}
