package com.example.HellTrain.entity;

import java.io.Serializable;
import java.util.Objects;

@SuppressWarnings("serial")
public class CollectId implements Serializable {
	private int userId;
	
	private int collectId;

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

	public CollectId() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CollectId(int userId, int collectId) {
		super();
		this.userId = userId;
		this.collectId = collectId;
	}
	
	@Override
	public boolean equals(Object o) {
       /* 檢查是否為同一個記憶體位址 */
       if (this == o) return true;       
       /* 檢查物件是否為 null 或類別不一致 */
       if (o == null || getClass() != o.getClass()) return false;       
       /* 轉型後比較欄位內容 */
       CollectId that = (CollectId) o;
       return userId == that.userId &&
               collectId == that.collectId;
   }
   @Override
   public int hashCode() {
       // 根據欄位內容產生 Hash 值
       return Objects.hash(userId, collectId);
   }
}
