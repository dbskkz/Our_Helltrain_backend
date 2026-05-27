package com.example.HellTrain.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.HellTrain.entity.Manager;

@Repository
public interface ManagerDao extends JpaRepository<Manager, String> {
	
	@Query(value="select * from manager where email = ?1",nativeQuery = true)
	public Manager getByEmail(String email) ;

}
