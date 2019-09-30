package com.instra.superrduperr.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.instra.superrduperr.dao.bean.Carts;

public interface CartRepository extends JpaRepository<Carts, Integer>{

	@Query("SELECT c from Carts c where c.userId = :userId")
	List<Carts> findByUserId(@Param("userId")int userId);

}
