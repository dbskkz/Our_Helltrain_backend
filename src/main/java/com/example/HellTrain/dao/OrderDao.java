package com.example.HellTrain.dao;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.HellTrain.entity.Order;
import jakarta.transaction.Transactional;

@Repository
public interface OrderDao extends JpaRepository<Order, Integer> {
	
	//新增
	@Modifying
	@Transactional
	@Query(value="insert into `order` (buyer_id, product_id, create_date, status,"//
			+ " buyer_check , seller_check )"
			+ " value (?1,?2,?3,?4,0,0) ",nativeQuery = true)
	public void addOrder(int buyerId,int productId,LocalDate createDate,String status);
	
	//查詢商品的所有預定(賣家查詢)
	@Query(value="select * from `order` where product_id = ?1",nativeQuery = true)
	public List<Order> getByProductId(int productId);
	
	//查詢預定編號(service使用)
	@Query(value="select * from `order` where order_id = ?1",nativeQuery = true)
	public Order getOrderById(int orderId);

	//買家的所有預定
	@Query(value="select * from `order` where buter_id = ?1",nativeQuery = true)
	public List<Order> getByBuyId(int BuyerId);
	
	@Modifying
	@Transactional
	@Query(value="update `order` set status = ?2 where order_id = ?1",nativeQuery = true)
	public void updateStatus(int orderId,String status);
	
	//賣家決定瑪家後改變其他申請人的訂單狀況，(商業邏輯:如取消則不需要改變)
	@Modifying
	@Transactional
	@Query(value="update `order` set status = ?3 where product_id = ?1 and order_id != ?2 "
			+ " and status = ?4",nativeQuery = true)
	public void cancelOtherOrders(int productId, int orderId, String newstatus, String status);

	@Modifying
	@Transactional
	@Query(value="delete from `order` where order_id = ?",nativeQuery = true)
	public void cancelOrder(int orderId);

	@Modifying
	@Transactional
	@Query(value="update `order` set buyer_check = 1 where order_id = ?1",nativeQuery = true)
	public void buyerCheack(int orderId);
	
	@Modifying
	@Transactional
	@Query(value="update `order` set seller_check = 1 where order_id = ?1",nativeQuery = true)
	public void salesCheack(int orderId);
}
