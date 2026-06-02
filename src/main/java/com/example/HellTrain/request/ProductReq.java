package com.example.HellTrain.request;

import java.util.List;

public class ProductReq {
    private int productId;
    private String productName;
    private String description;		//商品描述(外觀或是解釋狀況的描述)
    private int price;
    private List<String> imgList;  // 前端傳來的base64，最多7張
    private List<String> type;		//類別
    private String productCondition;	//商品狀況(全新、九成新...之類的)
    private List<String> grade;		//建議購買年級
    private List<String> location;	//商品可面交地點
    private List<String> deptGroup;	//適用學群
    private String status;			//狀態，一般新建時由後端給予對應資料(enum)
//    private LocalDate shelfDate;  //開始販售日期需要再打開
    
	public int getProductId() {
		return productId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public void setProductId(int productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}

	public List<String> getType() {
		return type;
	}
	public void setType(List<String> type) {
		this.type = type;
	}
//	public LocalDate getShelfDate() {
//		return shelfDate;
//	}
//	public void setShelfDate(LocalDate shelfDate) {
//		this.shelfDate = shelfDate;
//	}
	public String getProductCondition() {
		return productCondition;
	}
	public void setProductCondition(String productCondition) {
		this.productCondition = productCondition;
	}
	public List<String> getGrade() {
		return grade;
	}
	public void setGrade(List<String> grade) {
		this.grade = grade;
	}
	public List<String> getLocation() {
		return location;
	}
	public void setLocation(List<String> location) {
		this.location = location;
	}
	public List<String> getDeptGroup() {
		return deptGroup;
	}
	public void setDeptGroup(List<String> deptGroup) {
		this.deptGroup = deptGroup;
	}
	public List<String> getImgList() {
		return imgList;
	}
	public void setImgList(List<String> imgList) {
		this.imgList = imgList;
	}

}
