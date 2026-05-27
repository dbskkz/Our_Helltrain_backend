package com.example.HellTrain.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.HellTrain.entity.Manager;

import jakarta.transaction.Transactional;

@Repository
public interface ManagerDao extends JpaRepository<Manager, String> {
	
	@Query(value="select * from manager where email = ?1",nativeQuery = true)
	public Manager getByEmail(String email) ;


	@Modifying
	@Transactional
	@Query(value="insert ignore into manager (email, name, password) "
			+ " value (?1,?2,?3)",nativeQuery = true)
	public void addManager(String email,String name,String password);
}
