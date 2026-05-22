package com.example.HellTrain.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.HellTrain.entity.Product;

@Repository
public interface ProductDao extends JpaRepository<Product, Integer>{
	@Query(value = "SELECT * FROM product",nativeQuery = true)
	public List<Product> getAllData();
	
	@Query(value = "SELECT * FROM product WHERE price >= ?1 AND price <= ?2",nativeQuery = true)
	public List<Product> findByPriceBetween(int minPrice, int maxPrice);
	
	@Query(value = "SELECT * FROM product WHERE user_id = ?1",nativeQuery = true)
	public List<Product> findByUserId(int userId);
	
	@Query(value = "SELECT * FROM product WHERE type Like %?%",nativeQuery = true)
	public List<Product> findByType(String type);
	

	
}
