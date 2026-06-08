package com.example.HellTrain.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.HellTrain.entity.Collect;
import com.example.HellTrain.entity.CollectId;

import jakarta.transaction.Transactional;

@Repository
public interface CollectDao extends JpaRepository<Collect, CollectId> {

	@Query(value = "select count(*) from collect where user_id = ?1 and product_id = ?2", nativeQuery = true)
	public int checkProduct(int uesrId, int productId);

	/*
	 * coalesce => 取出參數中第一個不為null的值 而在使用者沒有收藏的商品時max(collect_id)為null，因此設定最小值0
	 */
	@Query(value = "select COALESCE(MAX(collect_id), 0) from collect where user_id = ?1", nativeQuery = true)
	public int catchCollectId(int uesrId);

	@Modifying
	@Transactional
	@Query(value = "insert into collect (user_id, collect_id, product_id) " //
	+ " value (?1,?2,?3)", nativeQuery = true)
	public void addCollect(int userId, int collectId, int productId);

	@Modifying
	@Transactional
	@Query(value = "delete from collect where collect_id = ?1", nativeQuery = true)
	public void clearCollect(int collectId);
	
	@Query(value = "select * from collect where collect_id = ?1", nativeQuery = true)
	public Collect checkcollect(int collectId);

	@Query(value = "SELECT p.product_name, p.location, p.img_path, p.price, seller.user_name"//
			+ " FROM collect c "//
			+ " JOIN product p ON c.product_id = p.product_id "//
			+ " JOIN user seller ON p.user_id = seller.user_id"//
			+ " WHERE c.user_id = ?1", nativeQuery = true)
	public List<Object[]> catchcollect(int userId);
}
