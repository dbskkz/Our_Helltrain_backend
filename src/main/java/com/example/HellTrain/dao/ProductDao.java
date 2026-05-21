package com.example.HellTrain.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.HellTrain.entity.Product;

@Repository
public interface ProductDao extends JpaRepository<Product, Integer>{
	@Query(value = "SELECT COUNT(product_id) FROM product",nativeQuery = true)
	public int findData();
}
