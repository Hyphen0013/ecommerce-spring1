package com.hyphen.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.hyphen.model.Orders;

@Repository
public interface OrderRepository extends JpaRepository<Orders, Long> {

	@Query("SELECT o FROM Orders o "
		       + "WHERE o.user.id = :userId "
		       + "AND (o.orderStatus = 'PLACED' "
		       + "OR o.orderStatus = 'CONFIRMED' "
		       + "OR o.orderStatus = 'SHIPPED' "
		       + "OR o.orderStatus = 'DELIVERED')")
	public List<Orders> getUsersOrders(@Param("userId") Long userId);
}
