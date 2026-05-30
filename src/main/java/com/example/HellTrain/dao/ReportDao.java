package com.example.HellTrain.dao;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.HellTrain.entity.Report;

import jakarta.transaction.Transactional;

@Repository
public interface ReportDao extends JpaRepository<Report, Integer> {

	@Modifying
	@Transactional
	@Query(value="insert ignore into report (complainant_id,accsed_id,description,"
			+ " file_path,report_date,status,type,violation_type) "
			+ " value (?1,?2,?3,?4,?5,?6,?7,?8)",nativeQuery = true)
	public void addReport(int complainantId, int accusedId, String description,
			String filePath, LocalDate reportDate, String status, String type, String violationType);
	
	@Query(value="select * form report ",nativeQuery = true)
	public List<Report> getAllReport();
	
	@Query(value="select * form report where report_id = ? ",nativeQuery = true)
	public Report getReportById(int reportId);
}
