package com.example.HellTrain.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "score")
public class Score {
	
	@Id
	@Column(name="user_id")
	private int userId;

	@Column(name="base_score")
	private String baseScore;
	
	@Column(name="click")
	private String click;

	@Column(name="score")
	private int score;


	public String getBaseScore() {
		return baseScore;
	}

	public void setBaseScore(String baseScore) {
		this.baseScore = baseScore;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getClick() {
		return click;
	}

	public void setClick(String click) {
		this.click = click;
	}

}
