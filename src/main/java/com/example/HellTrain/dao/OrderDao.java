package com.example.HellTrain.dao;

import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.HellTrain.entity.Order;
import jakarta.transaction.Transactional;

@Repository
public interface OrderDao extends JpaRepository<Order, Integer> {
	
	@Modifying
	@Transactional
	@Query(value="insert order (buyer_id, product_id, create_date, status,"
			+ " buyer_check = 0, seller_check = 0 )"
			+ " value (?1,?2,?3,?4) ",nativeQuery = true)
	public void addOrder(int buyerId,int productId,LocalDate createDate,String status);
	
	@Query(value="select * from order where order_id = ?1",nativeQuery = true)
	public Order getOrderById(int orderId);
	
	@Modifying
	@Transactional
	@Query(value="update order set status = ?2 where order_id = ?1",nativeQuery = true)
	public void updateStatus(int orderId,String status);
	
	@Modifying
	@Transactional
	@Query(value="update order set status = ?3 where product_id = ?1 and order_id != ?2 "
			+ " and status = ?4",nativeQuery = true)
	public void cancelOtherOrders(int productId, int orderId, String newstatus, String status);

	@Modifying
	@Transactional
	@Query(value="update order set buyer_check = 1 where order_id = ?1",nativeQuery = true)
	public void buyerCheack(int orderId);

	@Modifying
	@Transactional
	@Query(value="update order set seller_check = 1 where order_id = ?1",nativeQuery = true)
	public void salesCheack(int orderId);
}
