package com.example.HellTrain.entity;

import java.time.LocalDate;
import jakarta.persistence.*;

@Entity
@Table(name = "user")
public class Order {

	@Id
	@Column(name = "order_id")
	private int orderId;

	@Column(name = "buyer_id")
	private int buyerId;

	@Column(name = "product_id")
	private int productId;

	@Column(name = "create_date")
	private LocalDate createDate;

	@Column(name = "status")
	private String status;

	@Column(name = "buyer_check")
	private boolean buyerCheck;

	@Column(name = "seller_check")
	private boolean sellerCheck;

	@Column(name = "buyer_rank")
	private int buyerRank;

	@Column(name = "salesman_rank")
	private int salesmanRank;

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public int getBuyerId() {
		return buyerId;
	}

	public void setBuyerId(int buyerId) {
		this.buyerId = buyerId;
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public LocalDate getCreateDate() {
		return createDate;
	}

	public void setCreateDate(LocalDate createDate) {
		this.createDate = createDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public boolean isBuyerCheck() {
		return buyerCheck;
	}

	public void setBuyerCheck(boolean buyerCheck) {
		this.buyerCheck = buyerCheck;
	}

	public boolean isSellerCheck() {
		return sellerCheck;
	}

	public void setSellerCheck(boolean sellerCheck) {
		this.sellerCheck = sellerCheck;
	}

	public int getBuyerRank() {
		return buyerRank;
	}

	public void setBuyerRank(int buyerRank) {
		this.buyerRank = buyerRank;
	}

	public int getSalesmanRank() {
		return salesmanRank;
	}

	public void setSalesmanRank(int salesmanRank) {
		this.salesmanRank = salesmanRank;
	}

}
