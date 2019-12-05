package com.xiaodou.ms.web.request.product;

import lombok.Data;

@Data
public class ProductCategoryQueryConditionReq {
  private String productLine;
  private String majorName;
  private String examSchool;
  private String typeCode;
  private String module;//所属地域
  private Integer regionId;//所属地域Id
public String getProductLine() {
	return productLine;
}
public void setProductLine(String productLine) {
	this.productLine = productLine;
}
public String getMajorName() {
	return majorName;
}
public void setMajorName(String majorName) {
	this.majorName = majorName;
}
public String getExamSchool() {
	return examSchool;
}
public void setExamSchool(String examSchool) {
	this.examSchool = examSchool;
}
public String getTypeCode() {
	return typeCode;
}
public void setTypeCode(String typeCode) {
	this.typeCode = typeCode;
}
public String getModule() {
	return module;
}
public void setModule(String module) {
	this.module = module;
}
public Integer getRegionId() {
	return regionId;
}
public void setRegionId(Integer regionId) {
	this.regionId = regionId;
}
  
}
