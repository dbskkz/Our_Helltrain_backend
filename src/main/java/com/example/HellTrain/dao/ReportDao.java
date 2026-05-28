package com.example.HellTrain.dao;

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
	@Query(value="insert ignore into report () "
			+ " value ()",nativeQuery = true)
	public void addReport();
}
