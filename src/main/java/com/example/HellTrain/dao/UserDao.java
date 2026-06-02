package com.example.HellTrain.dao;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.HellTrain.entity.User;

import jakarta.transaction.Transactional;

@Repository
public interface UserDao extends JpaRepository<User, Integer> {

	@Query(value = "select count(user_email) from user where user_email=?", nativeQuery = true)
	public int selectCount(String email);

	@Modifying
	@Transactional
	@Query(value = "insert ignore into user (user_email, user_name, password, phone,"
			+ " location, school, status,create_date) " + " value (?1,?2,?3,?4,?5,?6,?7,?8)", nativeQuery = true)
	public void addUser(String email, String name, String password, String phone, String location, String school,
			String status, LocalDateTime creatDate);

	@Query(value = "select * from user where user_email = ?1", nativeQuery = true)
	public User getAccount(String email);

	@Modifying
	@Transactional
	@Query(value = "update user set user_name = :name, location = :location,"
			+ " school = :school, department = :department, " + " phone = :phone, msg = :msg, img_path = :imgPath"
			+ " where user_email = :email", nativeQuery = true)
	public void setInfo(@Param("email") String email,//
			@Param("name") String name, //
			@Param("imgPath") String imgPath,//
			@Param("location") String location, //
			@Param("school") String school, //
			@Param("department") String department,//
			@Param("phone") String phone, //
			@Param("msg") String msg);

	// 設定驗證日期
	@Modifying
	@Transactional
	@Query(value = "update user set verified = ?2, status = ?3 where user_email = ?1", nativeQuery = true)
	public void updateverified(String email, LocalDate verified, String status);

	// 更新密碼
	@Modifying
	@Transactional
	@Query(value = "update user set password = ?2 where user_email = ?1", nativeQuery = true)
	public void updatePad(String email, String password);

	// 設定評價
	@Modifying
	@Transactional
	@Query(value = "update user set good_level = ?2 where user_email = ?1", nativeQuery = true)
	public void goodLevel(String email, float level);

	// 提取使用者信譽
	@Query(value = "select user_name,good_level from user where user_id=?", nativeQuery = true)
	public User getLevel(int id);

	// 提取個別使用者資料
	@Query(value = "select * from user where user_id=?", nativeQuery = true)
	public User getById(int id);

	// 改變使用者狀態(檢舉應對方法)
	@Modifying
	@Transactional
	@Query(value = "update user set status = ?2 where user_id = ?1", nativeQuery = true)
	public void changeStatus(int id, String status);

	// 取得所有使用者資料
	@Query(value = "select * from user", nativeQuery = true)
	public List<User> getAllUser();

	// 找出在某日期之前註冊但未驗證的帳號
	@Query(value = "select *  from user where verified is null and create_date <= ?", nativeQuery = true)
	List<User> findUnverifiedBefore(LocalDateTime deadline);
	
	//超過驗證時間仍未驗證則刪除資料
	@Modifying
	@Transactional
	@Query(value ="DELETE FROM user WHERE verified IS NULL AND create_date <= ?1", nativeQuery = true)
	void deleteUnverified(LocalDateTime deadline);
	
	//用email抓user資料，ㄍㄋㄋ我居然沒有寫也太雷了八
	@Query(value ="select * from user where user_email=?1", nativeQuery = true)
	User getEmail(String email);
}