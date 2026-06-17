package com.example.HellTrain.dao;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.HellTrain.entity.User;
import com.example.HellTrain.entity.Wish;

import org.springframework.transaction.annotation.Transactional;

@Repository
public interface WishDao extends JpaRepository<Wish, Integer>{

	// INSERT 新許願
    @Modifying
    @Transactional
    @Query(value = "INSERT INTO wish (user_id, title, description, type, location, "
                 + "budget_min, budget_max, status, created_at, expired_at) "
                 + "VALUES (?1, ?2, ?3, ?4, ?5, ?6, ?7, ?8, ?9, ?10)", nativeQuery = true)
    void insert(int userId, String title, String description, String category, String location,
                int budgetMin, int budgetMax, String status,
                LocalDateTime createdAt, LocalDateTime expiredAt);

    // 查詢全部 active 的許願（最新的排前面）
    @Query(value = "SELECT * FROM wish WHERE status = 'active' ORDER BY created_at DESC", nativeQuery = true)
    List<Wish> findAllActive();

    // 依學校篩選（JOIN user table）
    @Query(value = "SELECT w.* FROM wish w "
                 + "JOIN user u ON w.user_id = u.user_id "
                 + "WHERE w.status = 'active' AND u.school = ?1 "
                 + "ORDER BY w.created_at DESC", nativeQuery = true)
    List<Wish> findActiveBySchool(String school);

//    // 依 wish id 查單筆
//    @Query(value = "SELECT * FROM wish WHERE id = ?1", nativeQuery = true)
//    Wish findById(int id);

    // 依 user_id 查該使用者的所有許願
    @Query(value = "SELECT * FROM wish WHERE user_id = ?1 ORDER BY created_at DESC", nativeQuery = true)
    List<Wish> findByUserId(int userId);
    
    // 查詢使用者一天內許了幾筆願望
    @Query(value = "SELECT COUNT(*) FROM wish WHERE user_id = ?1 AND DATE(created_at) = ?2", nativeQuery = true)
    int countWishesByIdAndDate(int userId, LocalDate today);

    // 更新狀態（fulfilled / expired）
    @Modifying
    @Transactional
    @Query(value = "UPDATE wish SET status = ?2 WHERE id = ?1", nativeQuery = true)
    void updateStatus(int id, String status);
    
    @Modifying
    @Transactional
    @Query(value = "DELETE FROM wish WHERE expired_at < NOW()", nativeQuery = true)
    void deleteExpired();
    
}
