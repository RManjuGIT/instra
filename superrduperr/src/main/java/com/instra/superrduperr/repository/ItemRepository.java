package com.instra.superrduperr.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.instra.superrduperr.dao.bean.Items;

@Repository
public interface ItemRepository extends JpaRepository<Items, Integer> {

	@Query("select max(i.version) from Items i where i.carts.id = :cartId")
	int findLatestVersionForCart(@Param("cartId") int cartId);

	@Query("select i from Items i where i.carts.id = :cartId and i.productId in :productIds")
	List<Items> findByProductIds(@Param("productIds") List<Integer> productIds, @Param("cartId") int cartId);

	@Modifying(clearAutomatically = true)
	@Query("delete from Items i where i.version = (select max(ii.version) from Items ii where ii.carts.id = :cartId) and i.version>1"
			+ " and i.carts.id = :cartId")
	void deleteLastVersionForCartId(@Param("cartId") int cartId);
	
	@Query("select i from Items i where i.carts.id = :cartId")
	List<Items> findByCartId(@Param("cartId") int cartId);

	@Modifying(clearAutomatically = true)
	@Query("update Items i set i.tag = :tag where i.productId in :productIds and i.carts.id = :cartId")
	void updateTagForProductsByCartId(@Param("cartId") Integer cartId, @Param("productIds") List<Integer> productIds, String tag);

}
