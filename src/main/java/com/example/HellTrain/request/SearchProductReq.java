package com.example.HellTrain.requeest;

import java.util.List;

public class SearchProductReq {

    private String keyword;           // 商品名稱關鍵字
    private Integer minPrice;         // 最低價（用 Integer 可以傳 null）
    private Integer maxPrice;         // 最高價
    private List<String> types;       // 商品類型（多選）
    private List<String> conditions;  // 新舊程度（多選）
    private List<String> grades;      // 使用年級（多選）
    private List<String> deptGroups;  // 適用學群

    public String getKeyword() { return keyword; }
    public void setKeyword(String keyword) { this.keyword = keyword; }

    public Integer getMinPrice() { return minPrice; }
    public void setMinPrice(Integer minPrice) { this.minPrice = minPrice; }

    public Integer getMaxPrice() { return maxPrice; }
    public void setMaxPrice(Integer maxPrice) { this.maxPrice = maxPrice; }

    public List<String> getTypes() { return types; }
    public void setTypes(List<String> types) { this.types = types; }

    public List<String> getConditions() { return conditions; }
    public void setConditions(List<String> conditions) { this.conditions = conditions; }

    public List<String> getGrades() { return grades; }
    public void setGrades(List<String> grades) { this.grades = grades; }
    
    public List<String> getDeptGroups() {
        return deptGroups;
    }

    public void setDeptGroups(List<String> deptGroups) {
        this.deptGroups = deptGroups;
    }
}