package com.xiaodou.st.dataclean.model.domain.dashboard;

import java.sql.Timestamp;

import com.xiaodou.autobuild.annotations.Column;
import com.xiaodou.autobuild.tool.MybatisXmlTool;
import com.xiaodou.common.annotation.GeneralField;

/**
 * @name @see com.xiaodou.st.dataclean.model.domain.dashboard.StudentModel.java
 * @CopyRright (c) 2017 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhouhuan@corp.51xiaodou.com">zhouhuan</a>
 * @date 2017年3月31日
 * @description 学生信息表
 * @version 1.0
 */
public class StudentModel {
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
  private String chiefUnits;
  @GeneralField
  @Column(canUpdate = true, sortBy = false)
  private String cats;
  @GeneralField
  @Column(canUpdate = true, sortBy = false)
  private Long classId;
  @GeneralField
  @Column(canUpdate = true, sortBy = false)
  private String className;
  @GeneralField
  @Column(canUpdate = true, sortBy = false)
  private Integer adminId;
  @GeneralField
  @Column(canUpdate = true, sortBy = false)
  private String adminName;
  @GeneralField
  @Column(canUpdate = true, sortBy = false)
  private Integer gender;
  @GeneralField
  @Column(canUpdate = true, sortBy = false)
  private String realName;
  @GeneralField
  @Column(canUpdate = true, sortBy = false)
  private String portrait;
  @GeneralField
  @Column(canUpdate = true, sortBy = false)
  private Long telephone;
  @GeneralField
  @Column(canUpdate = true, sortBy = false)
  private Integer userId;
  @GeneralField
  @Column(canUpdate = true, sortBy = false)
  private String identificationCardCode;
  @GeneralField
  @Column(canUpdate = true, sortBy = false)
  private String admissionCardCode;
  @GeneralField
  @Column(canUpdate = true, sortBy = false)
  private String studentStatus;
  @GeneralField
  @Column(canUpdate = true, sortBy = false)
  private String sourcePortrait;
  @GeneralField
  @Column(canUpdate = true, sortBy = false)
  private String collectWay;
  @GeneralField
  @Column(canUpdate = true, sortBy = false, betweenScope = true)
  private Timestamp uploadTime;
  @GeneralField
  @Column(canUpdate = true, sortBy = false)
  private String uploadDevice;
  @GeneralField
  @Column(canUpdate = true, sortBy = true)
  private Timestamp createTime;

  public String getSourcePortrait() {
    return sourcePortrait;
  }

  public void setSourcePortrait(String sourcePortrait) {
    this.sourcePortrait = sourcePortrait;
  }

  public String getCollectWay() {
    return collectWay;
  }

  public void setCollectWay(String collectWay) {
    this.collectWay = collectWay;
  }

  public Timestamp getUploadTime() {
    return uploadTime;
  }

  public void setUploadTime(Timestamp uploadTime) {
    this.uploadTime = uploadTime;
  }

  public String getUploadDevice() {
    return uploadDevice;
  }

  public void setUploadDevice(String uploadDevice) {
    this.uploadDevice = uploadDevice;
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

  public String getChiefUnits() {
    return chiefUnits;
  }

  public void setChiefUnits(String chiefUnits) {
    this.chiefUnits = chiefUnits;
  }

  public String getCats() {
    return cats;
  }

  public void setCats(String cats) {
    this.cats = cats;
  }

  public Long getClassId() {
    return classId;
  }

  public void setClassId(Long classId) {
    this.classId = classId;
  }

  public String getClassName() {
    return className;
  }

  public void setClassName(String className) {
    this.className = className;
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

  public Integer getGender() {
    return gender;
  }

  public void setGender(Integer gender) {
    this.gender = gender;
  }

  public String getRealName() {
    return realName;
  }

  public void setRealName(String realName) {
    this.realName = realName;
  }

  public String getPortrait() {
    return portrait;
  }

  public void setPortrait(String portrait) {
    this.portrait = portrait;
  }

  public Long getTelephone() {
    return telephone;
  }

  public void setTelephone(Long telephone) {
    this.telephone = telephone;
  }

  public Integer getUserId() {
    return userId;
  }

  public void setUserId(Integer userId) {
    this.userId = userId;
  }

  public String getIdentificationCardCode() {
    return identificationCardCode;
  }

  public void setIdentificationCardCode(String identificationCardCode) {
    this.identificationCardCode = identificationCardCode;
  }

  public String getAdmissionCardCode() {
    return admissionCardCode;
  }

  public void setAdmissionCardCode(String admissionCardCode) {
    this.admissionCardCode = admissionCardCode;
  }

  public String getStudentStatus() {
    return studentStatus;
  }

  public void setStudentStatus(String studentStatus) {
    this.studentStatus = studentStatus;
  }

  public Timestamp getCreateTime() {
    return createTime;
  }

  public void setCreateTime(Timestamp createTime) {
    this.createTime = createTime;
  }

  public static void main(String[] args) {
    MybatisXmlTool.getInstance(StudentModel.class, "xd_dashboard_student",
        "src/main/resources/conf/mybatis/dashboard/").buildXml();
  }
}
