package com.example.HellTrain.dao;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.HellTrain.entity.Report;

import jakarta.transaction.Transactional;

@Repository
public interface ReportDao extends JpaRepository<Report, Integer> {

	@Modifying
	@Transactional
	@Query(value = "INSERT IGNORE INTO report (product_id, accused_id, complainant_id, description, file_path, report_date, status, type, violation_type) "
	            + "VALUES (:productId, :accusedId, :complainantId, :description, :filePath, :reportDate, :status, :type, :violationType)", 
	       nativeQuery = true)
	public void addReport(
	    @Param("productId") int productId, //
	    @Param("accusedId") int accusedId, //
	    @Param("complainantId") int complainantId, //
	    @Param("description") String description, //
	    @Param("filePath") String filePath, //
	    @Param("reportDate") LocalDate reportDate, //
	    @Param("status") String status, //
	    @Param("type") String type, //
	    @Param("violationType") String violationType//
	);
	
	@Query(value="select * from report ",nativeQuery = true)
	public List<Report> getAllReport();
	
	@Query(value="select * from report where report_id = ? ",nativeQuery = true)
	public Report getReportById(int reportId);
	
	@Modifying
	@Transactional
	@Query(value="update report set status = ?2 where report_id = ?1 ",nativeQuery = true)
	public void check(int reportId, String status);
}
