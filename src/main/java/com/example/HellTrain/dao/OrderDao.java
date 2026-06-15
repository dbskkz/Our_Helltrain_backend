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
	@Query(value="SELECT o.order_id, o.create_date, o.buyer_check, o.seller_check, o.status,"
			+ " seller.user_name, p.product_name, p.price, buyer.user_name, p.img_path"
			+ " FROM `order` o "
			+ " JOIN product p ON o.product_id = p.product_id"
			+ " JOIN `user` buyer ON buyer.user_id = o.buyer_id"
			+ " JOIN user seller ON seller.user_id = p.user_id"
			+ " WHERE p.product_id = ?1",nativeQuery = true)
	public List<Object[]> getByProductId(int productId);
	
	//查詢預定編號(service使用)
	@Query(value="select * from `order` where order_id = ?1",nativeQuery = true)
	public Order getOrderById(int orderId);

	//買家的所有預定
	@Query(value="select * from `order` where buter_id = ?1",nativeQuery = true)
	public List<Order> getByBuyId(int BuyerId);

	//買家的所有定單
	@Query(value = "SELECT " +
		    "o.order_id AS orderId, " +
		    "o.create_date AS createDate, " +
		    "o.buyer_check AS buyerCheck, " +
		    "o.seller_check AS sellerCheck, " +
		    "o.status AS status, " +
		    "seller.user_name AS sellerName, " +
		    "p.product_name AS productName, " +
		    "p.price AS price, " +
		    "buyer.user_name AS buyerName, " +
		    "p.img_path AS imgPath, " +
		    "o.buyer_id AS buyerId, " +
		    "p.user_id AS sellerId, " +
		    "o.buyer_rank AS buyerRank, " +       
		    "o.salesman_rank AS salesmanRank " +  
		    "FROM `order` o " +
		    "JOIN product p ON o.product_id = p.product_id " +
		    "JOIN user buyer ON buyer.user_id = o.buyer_id " +
		    "JOIN user seller ON seller.user_id = p.user_id " +
		    "WHERE p.user_id = ?1 OR o.buyer_id = ?1",
		    nativeQuery = true)
		public List<OrderListProjection> getUserAllOrder(int buyerId);
	
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

	//設定賣家評分(session存儲的為買家資訊時)
	@Modifying
	@Transactional
	@Query(value="update `order` set salesman_rank = ?2 where order_id = ?1",nativeQuery = true)
	public void setSalesRank(int orderId, int rank);

	//設定買家評分(session存儲的為賣家資訊時)
	@Modifying
	@Transactional
	@Query(value="update `order` set buyer_rank = ?2 where order_id = ?1",nativeQuery = true)
	public void setbuyerRank(int orderId, int rank);
	
	//抓取user作為買家時的所有評分
	@Query(value="select buyer_rank from `order` where buyer_id = ?1 and status = ?2",nativeQuery = true)
	public List<Float> getAllBuyerRank(int userId, String status);

	//抓取user作為賣家時的所有評分
	@Query(value="select salesman_rank "
			+ " from `order` o "
			+ " join product p on p.product_id = o.product_id "
			+ " where p.user_id  = ?1 and o.status = ?2",nativeQuery = true)
	public List<Float> getAllSalesmanRank(int userId, String status);
}
