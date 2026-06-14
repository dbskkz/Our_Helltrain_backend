package com.example.HellTrain.dao;

import java.time.LocalDateTime;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.HellTrain.entity.User;
import com.example.HellTrain.entity.Wish;

import jakarta.transaction.Transactional;

@Repository
public interface WishDao extends JpaRepository<Wish, Integer>{

	//偷抄user的
//	@Modifying
//	@Transactional
//	@Query(value = "insert ignore into user (user_email, user_name, password, phone,"//
//			+ " location, school, status,create_date) "//
//			+ " value (?1,?2,?3,?4,?5,?6,?7,?8)", nativeQuery = true)
//	public void addUser(String email, String name, String password, String phone, String location, String school,
//			String status, LocalDateTime creatDate);
}
