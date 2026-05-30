package com.example.HellTrain.dao;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.HellTrain.entity.Announcement;

import jakarta.transaction.Transactional;

@Repository
public interface AnnounceDao extends JpaRepository<Announcement, Integer> {

	// 提取個別公告資料
	@Query(value = "select * from Announcement where id = ?", nativeQuery = true)
	public Announcement getById(int id);

	// 提取所有公告資料
	@Query(value = "select * from Announcement", nativeQuery = true)
	public List<Announcement> getAllAnnouncement();

	@Modifying
	@Transactional
	@Query(value = "insert into Announcement (title ,img_path ,shelf_date ,remove_date, "//
			+ " publish ,content) value (?1?2?3?4?5?6)", nativeQuery = true)
	public void addAnnounce(String title, String img, LocalDate shelfDate, //
			LocalDate removeDate, boolean publish, String content);
	
	//Active:因為date的資料庫型態是字串，所以用
	@Query(value = "select * from Announcement where shelf_date > CURDATE()"
			+ "and publish = 1", nativeQuery = true)
	public List<Announcement> getOnActive();
	
	@Modifying
	@Transactional
	@Query(value = "update Announcement set title = ?2 ,img_path = ?3 ,shelf_date = ?4 ,"//
			+ "remove_date = ?5, publish = ?6 ,content = ?7 whrer id = ?1", nativeQuery = true)
	public void upddateAnnounce(int id,String title, String img, LocalDate shelfDate, //
			LocalDate removeDate, boolean publish, String content);
}
