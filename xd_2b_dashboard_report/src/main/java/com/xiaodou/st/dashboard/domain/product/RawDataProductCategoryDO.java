package com.xiaodou.st.dashboard.domain.product;

import java.sql.Timestamp;

import com.xiaodou.autobuild.annotations.Column;
import com.xiaodou.autobuild.tool.MybatisXmlTool;

/**
 * 
 * @name RawDataProductCategoryDO CopyRright (c) 2017 by 李德洪
 * 
 * @author 李德洪
 * @date 2017年4月4日
 * @description TODO
 * @version 1.0
 */
public class RawDataProductCategoryDO {

  // 主键
  @Column(isMajor = true, autoIncrement = true, listValue = true)
  private Integer id;

  // 父
  private Integer parentId;

  // 子
  private String childId;

  // 展示状态
  private Integer showStatus;

  // 所有父id
  private String allParentId;

  // 所有子id
  private String allChildId;

  // 所在层级(从1级开始，最好不要超过3级)
  private Integer level;

  // 名称
  private String name;

  // 分类类型
  private Integer courseCategoryType;

  // 描述
  private String detail;

  private String misc;

  // 创建时间
  private Timestamp createTime;

  // 更新时间
  private Timestamp updateTime;

  // 是否为叶节点
  private Integer isLeaf;

  // app模块
  private String module;

  // 分类（专业代码）
  private String typeCode;

  /* 专业层次(eg:专科) */
  private String majorLevel;
  /* 第二级单位 */
  private String chiefAcademy;
  /* 学位（eg:文学学士） */
  private String degree;

  private short classify;

  public short getClassify() {
    return classify;
  }

  public void setClassify(short classify) {
    this.classify = classify;
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

  public static void main(String[] args) {
    MybatisXmlTool.getInstance(RawDataProductCategoryDO.class, "xd_raw_data_product_category",
        "D:/work/workspace_xd/xd_2b_dashboard_report/src/main/resources/conf/mybatis/product/")
        .buildXml();
  }
}
