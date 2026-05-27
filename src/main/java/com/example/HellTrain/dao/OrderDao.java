package com.example.HellTrain.dao;

import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.HellTrain.entity.Order;

@Repository
public interface OrderDao extends JpaRepository<Order, Integer> {
	
	@Query(value="insert order (buyer_id, product_id, create_date, status,"
			+ " buyer_check = 0, seller_check = 0 )"
			+ " value (?1,?2,?3,?4) ",nativeQuery = true)
	public void addOrder(int buyerId,int productId,LocalDate createDate,String status);

}
