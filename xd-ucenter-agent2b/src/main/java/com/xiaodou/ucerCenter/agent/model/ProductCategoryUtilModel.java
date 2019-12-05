package com.xiaodou.ucerCenter.agent.model;

import java.sql.Timestamp;

import com.xiaodou.common.annotation.BaseField;
import com.xiaodou.common.annotation.GeneralField;

public class ProductCategoryUtilModel {

  // 主键
  @BaseField
  @GeneralField
  private Integer id;

  // 父
  @GeneralField
  private Integer parentId;

  // 子
  @GeneralField
  private String childId;

  // 展示状态
  @GeneralField
  private Integer showStatus;

  // 所有父id
  @GeneralField
  private String allParentId;

  // 所有子id
  @GeneralField
  private String allChildId;

  // 所在层级(从1级开始，最好不要超过3级)
  @GeneralField
  private Integer level;

  // 名称
  @GeneralField
  private String name;

  /** pictureUrl 专业封面 */
  @GeneralField
  private String pictureUrl;

  // 分类类型
  @GeneralField
  private Integer courseCategoryType;

  // 描述
  @GeneralField
  private String detail;
  @GeneralField
  private String misc;

  // 创建时间
  @GeneralField
  private Timestamp createTime;

  // 更新时间
  @GeneralField
  private Timestamp updateTime;

  // 是否为叶节点
  @GeneralField
  private Integer isLeaf;

  // app模块
  @GeneralField
  private String module;

  // 分类（专业代码）
  @GeneralField
  private String typeCode;

  // 模块名
  @GeneralField
  private String moduleName;

  /* 专业层次(1：本科，2：专科) */
  @GeneralField
  private String majorLevel;
  /* 主考院校 */
  @GeneralField
  private String chiefAcademy;
  /* 学位（eg:文学学士） */
  @GeneralField
  private String degree;

  /* 专业下课程数目 */
  @GeneralField
  private String courseCount;
  
  @GeneralField
  private String majorInfo;
  public String getCourseCount() {
    return courseCount;
  }

  public void setCourseCount(String courseCount) {
    this.courseCount = courseCount;
  }

  public String getMajorLevel() {
    return majorLevel;
  }

  public void setMajorLevel(String majorLevel) {
    this.majorLevel = majorLevel;
  }

  public String getChiefAcademy() {
    return chiefAcademy;
  }

  public void setChiefAcademy(String chiefAcademy) {
    this.chiefAcademy = chiefAcademy;
  }

  public String getDegree() {
    return degree;
  }

  public void setDegree(String degree) {
    this.degree = degree;
  }

  public String getModule() {
    return module;
  }

  public void setModule(String module) {
    this.module = module;
  }

  public String getTypeCode() {
    return typeCode;
  }

  public void setTypeCode(String typeCode) {
    this.typeCode = typeCode;
  }

  public Integer getIsLeaf() {
    return isLeaf;
  }

  public void setIsLeaf(Integer isLeaf) {
    this.isLeaf = isLeaf;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Integer getMajorId() {
    return id;
  }

  public Integer getParentId() {
    return parentId;
  }

  public void setParentId(Integer parentId) {
    this.parentId = parentId;
  }

  public String getChildId() {
    return childId;
  }

  public void setChildId(String childId) {
    this.childId = childId;
  }

  public Integer getShowStatus() {
    return showStatus;
  }

  public void setShowStatus(Integer showStatus) {
    this.showStatus = showStatus;
  }

  public String getAllParentId() {
    return allParentId;
  }

  public void setAllParentId(String allParentId) {
    this.allParentId = allParentId;
  }

  public String getAllChildId() {
    return allChildId;
  }

  public void setAllChildId(String allChildId) {
    this.allChildId = allChildId;
  }

  public Integer getLevel() {
    return level;
  }

  public void setLevel(Integer level) {
    this.level = level;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getPictureUrl() {
    return pictureUrl;
  }

  public void setPictureUrl(String pictureUrl) {
    this.pictureUrl = pictureUrl;
  }

  public Integer getCourseCategoryType() {
    return courseCategoryType;
  }

  public void setCourseCategoryType(Integer courseCategoryType) {
    this.courseCategoryType = courseCategoryType;
  }

  public String getDetail() {
    return detail;
  }

  public void setDetail(String detail) {
    this.detail = detail;
  }

  public String getMisc() {
    return misc;
  }

  public void setMisc(String misc) {
    this.misc = misc;
  }

  public Timestamp getCreateTime() {
    return createTime;
  }

  public void setCreateTime(Timestamp createTime) {
    this.createTime = createTime;
  }

  public Timestamp getUpdateTime() {
    return updateTime;
  }

  public void setUpdateTime(Timestamp updateTime) {
    this.updateTime = updateTime;
  }

  public String getModuleName() {
    return moduleName;
  }

  public void setModuleName(String moduleName) {
    this.moduleName = moduleName;
  }

  public String getMajorInfo() {
    return majorInfo;
  }

  public void setMajorInfo(String majorInfo) {
    this.majorInfo = majorInfo;
  }

  public static class MajorInfo {
    // private String typeCode;
    // // 模块名
    // private String moduleName;
    // /* 专业层次(eg:专科) */
    // private String majorLevel;
    // /* 主考院校 */
    // private String chiefAcademy;

    private String createTime;// 创建记录时间
    private String degree;// 学位（eg:文学学士）
    private String detail;
    private String examSchool;// majorLevel;主考院校
    private String majorLevel;// 专业层次(eg:专科)

    public String getCreateTime() {
      return createTime;
    }

    public void setCreateTime(String createTime) {
      this.createTime = createTime;
    }

    public String getDegree() {
      return degree;
    }

    public void setDegree(String degree) {
      this.degree = degree;
    }

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

    public String getMajorLevel() {
      return majorLevel;
    }

    public void setMajorLevel(String majorLevel) {
      this.majorLevel = majorLevel;
    }
  }
}
