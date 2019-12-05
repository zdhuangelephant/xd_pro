package com.xiaodou.st.dashboard.domain.alarm;

import java.sql.Timestamp;

import com.xiaodou.autobuild.annotations.Column;
import com.xiaodou.autobuild.tool.MybatisXmlTool;

/**
 * @name PortraitRecordDO 
 * CopyRright (c) 2017 by 李德洪
 *
 * @author 李德洪
 * @date 2017年3月30日
 * @description 头像识别记录表
 * @version 1.0
 */
public class RawDataFaceRecognitionDO {
  @Column(isMajor=true)
  private Long id;
  /*测验id*/
  private String testId;
  /*学生id*/
  private Long studentId;
  /*专业名称*/
  private String catName;
  /*产品名称*/
  private String productName;
  /*测试点*/
  private String testPoint;
  /*采集时间*/
  private Timestamp collectTime;
  /*采集图片地址*/
  private String collectPortrait;
  /*相似度*/
  private double similarity;
  /*系统预判结果 1:是本人0:不是本人*/
  private short result;
  private Timestamp createTime;
  public Long getId() {
    return id;
  }
  public void setId(Long id) {
    this.id = id;
  }
  public String getTestId() {
    return testId;
  }
  public void setTestId(String testId) {
    this.testId = testId;
  }
  public Long getStudentId() {
    return studentId;
  }
  public void setStudentId(Long studentId) {
    this.studentId = studentId;
  }
  public String getCatName() {
    return catName;
  }
  public void setCatName(String catName) {
    this.catName = catName;
  }
  public String getProductName() {
    return productName;
  }
  public void setProductName(String productName) {
    this.productName = productName;
  }
  public String getTestPoint() {
    return testPoint;
  }
  public void setTestPoint(String testPoint) {
    this.testPoint = testPoint;
  }
  public Timestamp getCollectTime() {
    return collectTime;
  }
  public void setCollectTime(Timestamp collectTime) {
    this.collectTime = collectTime;
  }
  public String getCollectPortrait() {
    return collectPortrait;
  }
  public void setCollectPortrait(String collectPortrait) {
    this.collectPortrait = collectPortrait;
  }
  public double getSimilarity() {
    return similarity;
  }
  public void setSimilarity(double similarity) {
    this.similarity = similarity;
  }
  public short getResult() {
    return result;
  }
  public void setResult(short result) {
    this.result = result;
  }
  public Timestamp getCreateTime() {
    return createTime;
  }
  public void setCreateTime(Timestamp createTime) {
    this.createTime = createTime;
  }
  
  public static void main(String[] args) {
    MybatisXmlTool.getInstance(RawDataFaceRecognitionDO.class, "xd_raw_data_face_recognition",
        "D:/work/workspace_xd/xd_2b_dashboard_report/src/main/resources/conf/mybatis/alarm/").buildXml();
  }
}
