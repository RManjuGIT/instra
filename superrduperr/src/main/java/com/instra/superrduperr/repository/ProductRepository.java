package com.instra.superrduperr.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.instra.superrduperr.dao.bean.Products;

public interface ProductRepository extends JpaRepository<Products, Integer> {

	@Modifying(clearAutomatically = true)
	@Query("update Products p set p.quantity = 0 where p.id in :productIds")
	int updateQuantityToZeroById(@Param("productIds") List<Integer> productIds);
}
