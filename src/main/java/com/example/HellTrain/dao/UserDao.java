package com.example.HellTrain.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.HellTrain.entity.User;

import jakarta.transaction.Transactional;

@Repository
public interface UserDao extends JpaRepository<User, Integer> {
	
	@Query(value="select count(user_email) from user where user_email=?",nativeQuery = true)
	public int selectCount(String email);

	@Modifying
	@Transactional
	@Query(value="insert ignore into user (user_email,user_name,password,phone,"
			+ " location,school_id,status) "
			+ " value (?1,?2,?3,?4,?5,?6,?7)",nativeQuery = true)
	public void addUser(String email,String name,String password,String phone,
			String location,String school,String status);

	@Query(value="select * from user where user_email = ?1",nativeQuery = true)
	public User getAccount(String email);
}
