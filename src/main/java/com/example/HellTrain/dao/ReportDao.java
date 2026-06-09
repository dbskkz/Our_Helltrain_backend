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
	
	//取的所有檢舉資料，選擇欄位對應前端表格
	@Query(value="SELECT r.report_id,  r.type, r.product_id, a.user_name, c.user_name, "
			+ "r.report_date, r.status"
			+ "FROM report "
			+ "JOIN user a ON r.accused_id = a.user_id "
			+ "JOIN user c ON r.complainant_id = c.user_id ",nativeQuery = true)
	public List<Object[]> getAllReport();
	
	//檢舉需要JOIN檢舉人、被檢舉人或商品
	@Query(value="SELECT r.report_id, r.product_id, r.description, r.file_path, r.report_date, "
			+ " r.status, r.type, r.violation_type, r.note, p.product_name, "
			+ " a.user_name, c.usessr_name"
			+ "FROM report "
			+ "JOIN product p ON r.product_id = p.product_id "
			+ "JOIN user a ON r.accused_id = a.user_id "
			+ "JOIN user c ON r.complainant_id = c.user_id "
			+ "WHERE report_id = ?",nativeQuery = true)
	public Object[] getReportById(int reportId);
	
	@Query(value="SELECT * FROM report WHERE report_id = ?",nativeQuery = true)
	public Report getReportId(int reportId);
	
	@Modifying
	@Transactional
	@Query(value="update report set status = ?2 where report_id = ?1 ",nativeQuery = true)
	public void check(int reportId, String status);
	
	@Modifying
	@Transactional
	@Query(value = "UPDATE report SET note = ?2 WHERE report_id = ?1", nativeQuery = true)
	void updateNote(int reportId, String note);
}
