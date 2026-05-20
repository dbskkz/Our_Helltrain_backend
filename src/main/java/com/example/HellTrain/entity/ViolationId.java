package com.example.HellTrain.entity;

import java.io.Serializable;
import java.util.Objects;

@SuppressWarnings("serial")
public class ViolationId implements Serializable {

	private String userEmail;
	
	private int violationId;

	public ViolationId() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ViolationId(String userEmail, int violationId) {
		super();
		this.userEmail = userEmail;
		this.violationId = violationId;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public int getViolationId() {
		return violationId;
	}

	public void setViolationId(int violationId) {
		this.violationId = violationId;
	}
	/*必須實作 equals 和 hashCode : JPA 
	 * 靠這兩個方法來比對兩個 ID 是否代表同一個實體；
	 *如果不寫，會導致快取失效或找不到資料
	 */
	@Override
	public boolean equals(Object o) {
		/* 檢查是否為同一個記憶體位址 */
		if (this == o)
			return true;
		/* 檢查物件是否為 null 或類別不一致 */
		if (o == null || getClass() != o.getClass())
			return false;
		/* 轉型後比較欄位內容 */
		ViolationId that = (ViolationId) o;
		return 	violationId == that.violationId && //
				Objects.equals(userEmail, that.userEmail);
	}

	@Override
	public int hashCode() {
		// 根據欄位內容產生 Hash 值
		return Objects.hash(violationId, userEmail);
	}
}
