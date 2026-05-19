package com.example.HellTrain.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "user")
public class Score {
	
	@Id
	@Column(name="product_id")
	private int productId;

	@Column(name="base_score")
	private String baseScore;
	
	@Column(name="cilck")
	private String cilck;

	@Column(name="score")
	private int score;

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public String getBaseScore() {
		return baseScore;
	}

	public void setBaseScore(String baseScore) {
		this.baseScore = baseScore;
	}

	public String getCilck() {
		return cilck;
	}

	public void setCilck(String cilck) {
		this.cilck = cilck;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

}
