package com.example.HellTrain.dao;

import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.HellTrain.entity.User;

import jakarta.transaction.Transactional;

@Repository
public interface UserDao extends JpaRepository<User, Integer> {
	
	@Query(value="select count(user_email) from user where user_email=?",nativeQuery = true)
	public int selectCount(String email);

	@Modifying
	@Transactional
	@Query(value="insert ignore into user (user_email, user_name, password, phone,"
			+ " location, school, status) "
			+ " value (?1,?2,?3,?4,?5,?6,?7)",nativeQuery = true)
	public void addUser(String email,String name,String password,String phone,
			String location,String school,String status);

	@Query(value="select * from user where user_email = ?1",nativeQuery = true)
	public User getAccount(String email);
	
	@Modifying
	@Transactional
	@Query(value="update user set user_name = :name, location = :location,"
			+ " school = :school, department = :department, "
			+ " phone = :phone, msg = :msg, img_path = :imgPath "
			+ " where user_email = :email",nativeQuery = true)
	public void  setInfo(@Param("email") String email,
            @Param("name") String name,
            @Param("imgPath") String imgPath,
            @Param("location") String location,
            @Param("school") String school,
            @Param("department") String department,
            @Param("phone") String phone,
            @Param("msg") String msg);

	@Modifying
	@Transactional
	@Query(value="update user set verified = ?2 where user_email = ?1",nativeQuery = true)
	public void updateverified(String email,LocalDate verified);

	@Modifying
	@Transactional
	@Query(value="update user set password = ?2 where user_email = ?1",nativeQuery = true)
	public void updatePad(String email,String password);

	@Modifying
	@Transactional
	@Query(value="update user set good_level = ?2 where user_email = ?1",nativeQuery = true)
	public void goodLevel(String email,float level);
	
	//提取使用者信譽
	@Query(value="select user_name,good_level from user where user_id=?",nativeQuery = true)
	public User getById(int id);
}
