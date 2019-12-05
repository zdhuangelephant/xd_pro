package com.xiaodou.ms.model.major;

import java.sql.Timestamp;

/**
 * 
 * @ClassName: MajorInfo
 * @Description: 专业详细信息
 * @author zhouhuan
 * @date 2016年7月1日 
 */
public class MajorInfo  {
	 // 专业介绍
    private String detail;
	 // 主考院校
    private String examSchool;
     //专业层次
    private String majorLevel;
    //学位
    private String degree;
     // 创建时间
 	private Timestamp createTime;
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}
	public String getExamSchool() {
		return examSchool;
	}
	public void setExamSchool(String examSchool) {
		this.examSchool = examSchool;
	}
	public String getDegree() {
		return degree;
	}
	public void setDegree(String degree) {
		this.degree = degree;
	}
	public String getMajorLevel() {
		return majorLevel;
	}
	public void setMajorLevel(String majorLevel) {
		this.majorLevel = majorLevel;
	}
	public Timestamp getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}


  

}
