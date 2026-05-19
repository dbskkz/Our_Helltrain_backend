package com.example.HellTrain.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "department")
public class Department {

	@Id
	@Column(name = "department_id")
	private int departmentId;
	
	@Column(name="school")
	private String school;
	
	@Column(name="department")
	private String department;

	public int getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(int departmentId) {
		this.departmentId = departmentId;
	}

	public String getSchool() {
		return school;
	}

	public void setSchool(String school) {
		this.school = school;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

}
