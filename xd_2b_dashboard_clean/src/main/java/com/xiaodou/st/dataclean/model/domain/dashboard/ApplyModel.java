package com.xiaodou.st.dataclean.model.domain.dashboard;

import java.sql.Timestamp;

import lombok.Data;

import com.xiaodou.autobuild.annotations.Column;
import com.xiaodou.autobuild.tool.MybatisXmlTool;
import com.xiaodou.common.annotation.GeneralField;
/**
 * @name @see com.xiaodou.st.dataclean.model.domain.dashboard.ApplyModel.java
 * @CopyRright (c) 2017 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhouhuan@corp.51xiaodou.com">zhouhuan</a>
 * @date 2017年3月31日
 * @description 报名信息
 * @version 1.0
 */
@Data
public class ApplyModel {
	// id
	@Column(isMajor = true)
	@GeneralField
	private Integer id;
	@GeneralField
	@Column(canUpdate = true, sortBy = false)
	private Integer pilotUnitId;
	@GeneralField
	@Column(canUpdate = true, sortBy = false)
	private String pilotUnitName;
	@GeneralField
	@Column(canUpdate = true, sortBy = false)
	private Integer adminId;
	@GeneralField
	@Column(canUpdate = true, sortBy = false)
	private String adminName;
	@GeneralField
	@Column(canUpdate = true, sortBy = true)
	private Integer classId;
	@GeneralField
	@Column(canUpdate = true, sortBy = false)
	private String className;
	@GeneralField
	@Column(canUpdate = true, sortBy = false)
	private Integer studentId;
	@GeneralField
	@Column(canUpdate = true, sortBy = false)
	private String studentName;
	@GeneralField
	@Column(canUpdate = true, sortBy = false)
	private Long telephone;
	@GeneralField
	@Column(canUpdate = true, sortBy = false)
	private String admissionCardCode;
	@GeneralField
	@Column(canUpdate = true, sortBy = false)
	private String examDate;
	@GeneralField
	@Column(canUpdate = true, sortBy = false)
	private Integer catId;
	@GeneralField
	@Column(canUpdate = true, sortBy = false)
	private String catName;
	@GeneralField
	@Column(canUpdate = true, sortBy = false)
	private Integer productId;
	@GeneralField
	@Column(canUpdate = true, sortBy = false)
	private String productName;
	/* 订单状态0：待缴费，1：已缴费，2未缴费 */
	@GeneralField
	@Column(canUpdate = true, sortBy = false)
	private Short orderStatus;
    /* 报名状态（0：后台报名1：业务系统报名成功） */
	@GeneralField
    @Column(canUpdate = true, sortBy = false)
    private Short applyStatus;  
	@GeneralField
	@Column(canUpdate = true, sortBy = false)
	private String orderNumber;
	@GeneralField
	@Column(canUpdate = true, sortBy = false)
	private Timestamp createTime;

	public static void main(String[] args) {
		MybatisXmlTool.getInstance(ApplyModel.class, "xd_dashboard_apply",
				"E:/work3/xd-dashboard-2b/src/main/resources/conf/mybatis/")
				.buildXml();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getPilotUnitId() {
		return pilotUnitId;
	}

	public void setPilotUnitId(Integer pilotUnitId) {
		this.pilotUnitId = pilotUnitId;
	}

	public String getPilotUnitName() {
		return pilotUnitName;
	}

	public void setPilotUnitName(String pilotUnitName) {
		this.pilotUnitName = pilotUnitName;
	}

	public Integer getAdminId() {
		return adminId;
	}

	public void setAdminId(Integer adminId) {
		this.adminId = adminId;
	}

	public String getAdminName() {
		return adminName;
	}

	public void setAdminName(String adminName) {
		this.adminName = adminName;
	}

	public Integer getClassId() {
		return classId;
	}

	public void setClassId(Integer classId) {
		this.classId = classId;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public Integer getStudentId() {
		return studentId;
	}

	public void setStudentId(Integer studentId) {
		this.studentId = studentId;
	}

	public String getStudentName() {
		return studentName;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

	public Long getTelephone() {
		return telephone;
	}

	public void setTelephone(Long telephone) {
		this.telephone = telephone;
	}

	public String getAdmissionCardCode() {
		return admissionCardCode;
	}

	public void setAdmissionCardCode(String admissionCardCode) {
		this.admissionCardCode = admissionCardCode;
	}

	public String getExamDate() {
		return examDate;
	}

	public void setExamDate(String examDate) {
		this.examDate = examDate;
	}

	public Integer getCatId() {
		return catId;
	}

	public void setCatId(Integer catId) {
		this.catId = catId;
	}

	public String getCatName() {
		return catName;
	}

	public void setCatName(String catName) {
		this.catName = catName;
	}

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public Short getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(Short orderStatus) {
		this.orderStatus = orderStatus;
	}

	public Short getApplyStatus() {
		return applyStatus;
	}

	public void setApplyStatus(Short applyStatus) {
		this.applyStatus = applyStatus;
	}

	public String getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}
	
}
