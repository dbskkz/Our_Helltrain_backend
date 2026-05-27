package com.example.HellTrain.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.HellTrain.entity.Product;

@Repository
public interface ProductDao extends JpaRepository<Product, Integer> {

    @Query(value = "SELECT * FROM product", nativeQuery = true)
    List<Product> getAllData();

    @Query(value = "SELECT * FROM product WHERE price >= ?1 AND price <= ?2", nativeQuery = true)
    List<Product> findByPriceBetween(int minPrice, int maxPrice);

    @Query(value = "SELECT * FROM product WHERE user_id = ?1", nativeQuery = true)
    List<Product> findByUserId(int userId);

    // 修正：原本 %?% 語法錯誤，改用 CONCAT
    @Query(value = "SELECT * FROM product WHERE type LIKE CONCAT('%', ?1, '%')", nativeQuery = true)
    List<Product> findByType(String type);

    // 新增：年級搜尋
    @Query(value = "SELECT * FROM product WHERE grade = ?1", nativeQuery = true)
    List<Product> findByGrade(String grade);

    // 新增：商品名稱模糊搜尋
    @Query(value = "SELECT * FROM product WHERE product_name LIKE CONCAT('%', ?1, '%')", nativeQuery = true)
    List<Product> findByProductName(String productName);

    // 新增：複合搜尋（productName + price，其餘在 Java 層過濾）
    @Query(value = """
        SELECT * FROM product
        WHERE (:keyword IS NULL OR product_name LIKE CONCAT('%', :keyword, '%'))
          AND (:minPrice IS NULL OR price >= :minPrice)
          AND (:maxPrice IS NULL OR price <= :maxPrice)
        """, nativeQuery = true)
    List<Product> findByMultiConditions(
        @Param("keyword") String productName,
        @Param("minPrice") Integer minPrice,
        @Param("maxPrice") Integer maxPrice
    );
}