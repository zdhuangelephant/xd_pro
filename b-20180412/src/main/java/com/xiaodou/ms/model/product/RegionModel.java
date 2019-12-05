package com.xiaodou.ms.model.product;


import java.sql.Timestamp;

import com.xiaodou.autobuild.annotations.Column;
import com.xiaodou.autobuild.tool.MybatisXmlTool;

import lombok.Data;

/**
 * @name RegionModel 
 * CopyRright (c) 2018 by <a href=mailto:zhuweijin@corp.51xiaodou.com>zhuweijin</a>
 *
 * @author <a href=mailto:zhuweijin@corp.51xiaodou.com>zhuweijin</a>
 * @date 2018年4月12日
 * @description 地域模块，之前的module指的是产品模块，已经废弃
 * @version 1.0
 */
@Data
public class RegionModel {
  
  @Column(isMajor = true)
  private Long id;
  
  private String ruleId;

  private String module;

  private String moduleName;

  private Integer listOrder;

  private String detail;
  
  private Integer firstChoice;
  
  private Integer showStatus;
  
  private Timestamp createTime;
  
  //新手课程对应的模块
  private String hasBeginnerCourse;
  //新手课程ID
  private String courseId;
  
  
  public static void main(String[] args) {
    MybatisXmlTool
        .getInstance(RegionModel.class, "xd_course_product_module_new",
          "F:\\workspace\\b-20180412\\src\\main\\resources\\conf\\mybatis")
        .buildXml();
  }


public Long getId() {
	return id;
}


public void setId(Long id) {
	this.id = id;
}


public String getRuleId() {
	return ruleId;
}


public void setRuleId(String ruleId) {
	this.ruleId = ruleId;
}


public String getModule() {
	return module;
}


public void setModule(String module) {
	this.module = module;
}


public String getModuleName() {
	return moduleName;
}


public void setModuleName(String moduleName) {
	this.moduleName = moduleName;
}


public Integer getListOrder() {
	return listOrder;
}


public void setListOrder(Integer listOrder) {
	this.listOrder = listOrder;
}


public String getDetail() {
	return detail;
}


public void setDetail(String detail) {
	this.detail = detail;
}


public Integer getFirstChoice() {
	return firstChoice;
}


public void setFirstChoice(Integer firstChoice) {
	this.firstChoice = firstChoice;
}


public Timestamp getCreateTime() {
	return createTime;
}


public void setCreateTime(Timestamp createTime) {
	this.createTime = createTime;
}


public String getHasBeginnerCourse() {
	return hasBeginnerCourse;
}


public void setHasBeginnerCourse(String hasBeginnerCourse) {
	this.hasBeginnerCourse = hasBeginnerCourse;
}


public String getCourseId() {
	return courseId;
}


public void setCourseId(String courseId) {
	this.courseId = courseId;
}

}
