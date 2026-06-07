package com.example.HellTrain.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "collect")
public class Collect {
	@Id
	@Column(name = "user_id")
	private int userId;
	
	@Id
	@Column(name = "collect_id")
	private int collectId;
	
	@Column(name = "product_id")
	private int productId;

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getCollectId() {
		return collectId;
	}

	public void setCollectId(int collectId) {
		this.collectId = collectId;
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

}
