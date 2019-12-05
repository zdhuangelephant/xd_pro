package com.xiaodou.st.dashboard.domain.session;

import java.sql.Timestamp;

import com.xiaodou.autobuild.annotations.Column;
import com.xiaodou.autobuild.tool.MybatisXmlTool;

/**
 * 
 * @name CategoryUnitSessionProductAvgScoreDO CopyRright (c) 2017 by 李德洪
 * 
 * @author 李德洪
 * @date 2017年3月31日
 * @description 专业下的，第三级单位下的，课程平均成绩
 * @version 1.0
 */
public class CategoryUnitSessionProductAvgScoreDO {
  @Column(isMajor = true)
  private Long id;
  /* 专业id */
  private Long catId;
  /* 第三级单位id */
  private Long pilotUnitId;
  /* 课程id */
  private Long productId;
  /* 课程平均成绩 */
  private double avgScore;
  /* 角色类型 */
  private short roleType;
  /* 单位id */
  private Long unitId;
  private Timestamp createTime;

  /* 课程名称 */
  private String productName;

  public String getProductName() {
    return productName;
  }

  public void setProductName(String productName) {
    this.productName = productName;
  }
  public Timestamp getCreateTime() {
    return createTime;
  }

  public void setCreateTime(Timestamp createTime) {
    this.createTime = createTime;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getCatId() {
    return catId;
  }

  public void setCatId(Long catId) {
    this.catId = catId;
  }

  public Long getPilotUnitId() {
    return pilotUnitId;
  }

  public void setPilotUnitId(Long pilotUnitId) {
    this.pilotUnitId = pilotUnitId;
  }

  public Long getProductId() {
    return productId;
  }

  public void setProductId(Long productId) {
    this.productId = productId;
  }

  public double getAvgScore() {
    return avgScore;
  }

  public void setAvgScore(double avgScore) {
    this.avgScore = avgScore;
  }

  public short getRoleType() {
    return roleType;
  }

  public void setRoleType(short roleType) {
    this.roleType = roleType;
  }

  public Long getUnitId() {
    return unitId;
  }

  public void setUnitId(Long unitId) {
    this.unitId = unitId;
  }

  public static void main(String[] args) {
    MybatisXmlTool.getInstance(CategoryUnitSessionProductAvgScoreDO.class,
        "xd_dashboard_category_unit_session_product_avg_score",
        "D:/work/workspace_xd/xd_2b_dashboard_report/src/main/resources/conf/mybatis/session/")
        .buildXml();
  }
}
