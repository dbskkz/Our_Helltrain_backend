package com.example.HellTrain.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.HellTrain.entity.User;

@Repository
public interface UserDao extends JpaRepository<User, Integer> {
	
	@Query(value="select count(email) from user where email=?",nativeQuery = true)
	public int selectCount(String email);

	@Query(value="insert ignore into user () "
			+ " value ()",nativeQuery = true)
	public int addUser(String email);

}
