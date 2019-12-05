package com.xiaodou.st.dataclean.model.domain.raw;

import java.math.BigDecimal;
import java.sql.Timestamp;

import com.xiaodou.autobuild.annotations.Column;
import com.xiaodou.autobuild.tool.MybatisXmlTool;
import com.xiaodou.common.annotation.BaseField;
import com.xiaodou.common.annotation.GeneralField;

/**
 * 
 * @name RawDataProductDO CopyRright (c) 2017 by 李德洪
 * 
 * @author 李德洪
 * @date 2017年4月4日
 * @description TODO
 * @version 1.0
 */
public class RawDataProductModel {

  // 主键Id
  @Column(isMajor = true, autoIncrement = true, listValue = true)
  @BaseField
  @GeneralField
  private Long id;

  /** module 所属地域 */
  @BaseField
  @GeneralField
  private String module;

  // 产品名
  @BaseField
  @GeneralField
  private String name;

  // 简介
  @BaseField
  @GeneralField
  private String briefInfo;

  // 详情
  @BaseField
  @GeneralField
  private String detail;

  // 图片地址
  @BaseField
  @GeneralField
  private String imageUrl;

  // 当前报名人数
  @BaseField
  @GeneralField
  private Integer currApplyCount;

  // 报名人数上线
  @BaseField
  @GeneralField
  private Integer totalApplyCount;

  // 开始申请时间
  @BaseField
  @GeneralField
  private Timestamp beginApplyTime;

  // 申请结束时间
  @BaseField
  @GeneralField
  private Timestamp endApplyTime;

  // 原价
  @BaseField
  @GeneralField
  private BigDecimal originalAmount;

  // 优惠价
  @BaseField
  @GeneralField
  private BigDecimal payAmount;

  // 创建时间
  @BaseField
  @GeneralField
  private Timestamp createTime;

  // 更新时间
  @BaseField
  private Timestamp updateTime;

  // misc 杂项
  @BaseField
  @GeneralField
  private String misc;

  // 是否显示
  @BaseField
  @GeneralField
  private Integer showStatus;

  // 点赞数
  @BaseField
  @GeneralField
  private Integer praiseCount;

  // 产品类型
  @BaseField
  @GeneralField
  private Integer type;

  // 题库设置
  @BaseField
  @GeneralField
  private String questionBankSetting;

  // 资源产品ID
  @BaseField
  @GeneralField
  private Integer resourceSubject;

  // 分享url
  @BaseField
  @GeneralField
  private String shareUrl;
  @GeneralField
  private String courseCode;// 课程代码

  private String courseQuality;// 课程性质
  private String courseCredit;// 课程学分
  private String courseCheckType;// 课程考核方式

  @GeneralField
  private String moduleCourse;
  // 考期
  @GeneralField
  private String examDate;
  /** 计分规则Id */
  @GeneralField
  private String ruleId;


  /* 展示属性 */
  private Short applyStatus;

  public Short getApplyStatus() {
    return applyStatus;
  }

  public void setApplyStatus(Short applyStatus) {
    this.applyStatus = applyStatus;
  }

  public String getExamDate() {
    return examDate;
  }

  public void setExamDate(String examDate) {
    this.examDate = examDate;
  }

  public String getShareUrl() {
    return shareUrl;
  }

  public void setShareUrl(String shareUrl) {
    this.shareUrl = shareUrl;
  }

  public Integer getResourceSubject() {
    return resourceSubject;
  }

  public void setResourceSubject(Integer resourceSubject) {
    this.resourceSubject = resourceSubject;
  }

  public String getQuestionBankSetting() {
    return questionBankSetting;
  }

  public void setQuestionBankSetting(String questionBankSetting) {
    this.questionBankSetting = questionBankSetting;
  }

  public Integer getType() {
    return type;
  }

  public void setType(Integer type) {
    this.type = type;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getModule() {
    return module;
  }

  public void setModule(String module) {
    this.module = module;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getBriefInfo() {
    return briefInfo;
  }

  public void setBriefInfo(String briefInfo) {
    this.briefInfo = briefInfo;
  }

  public String getDetail() {
    return detail;
  }

  public void setDetail(String detail) {
    this.detail = detail;
  }

  public String getImageUrl() {
    return imageUrl;
  }

  public void setImageUrl(String imageUrl) {
    this.imageUrl = imageUrl;
  }

  public Integer getCurrApplyCount() {
    return currApplyCount;
  }

  public void setCurrApplyCount(Integer currApplyCount) {
    this.currApplyCount = currApplyCount;
  }

  public Integer getTotalApplyCount() {
    return totalApplyCount;
  }

  public void setTotalApplyCount(Integer totalApplyCount) {
    this.totalApplyCount = totalApplyCount;
  }

  public Timestamp getBeginApplyTime() {
    return beginApplyTime;
  }

  public void setBeginApplyTime(Timestamp beginApplyTime) {
    this.beginApplyTime = beginApplyTime;
  }

  public Timestamp getEndApplyTime() {
    return endApplyTime;
  }

  public void setEndApplyTime(Timestamp endApplyTime) {
    this.endApplyTime = endApplyTime;
  }

  public BigDecimal getOriginalAmount() {
    return originalAmount;
  }

  public void setOriginalAmount(BigDecimal originalAmount) {
    this.originalAmount = originalAmount;
  }

  public BigDecimal getPayAmount() {
    return payAmount;
  }

  public void setPayAmount(BigDecimal payAmount) {
    this.payAmount = payAmount;
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

  public String getMisc() {
    return misc;
  }

  public void setMisc(String misc) {
    this.misc = misc;
  }

  public Integer getShowStatus() {
    return showStatus;
  }

  public void setShowStatus(Integer showStatus) {
    this.showStatus = showStatus;
  }

  public Integer getPraiseCount() {
    return praiseCount;
  }

  public void setPraiseCount(Integer praiseCount) {
    this.praiseCount = praiseCount;
  }

  public String getCourseCode() {
    return courseCode;
  }

  public void setCourseCode(String courseCode) {
    this.courseCode = courseCode;
  }

  public String getCourseQuality() {
    return courseQuality;
  }

  public void setCourseQuality(String courseQuality) {
    this.courseQuality = courseQuality;
  }

  public String getCourseCredit() {
    return courseCredit;
  }

  public void setCourseCredit(String courseCredit) {
    this.courseCredit = courseCredit;
  }

  public String getCourseCheckType() {
    return courseCheckType;
  }

  public void setCourseCheckType(String courseCheckType) {
    this.courseCheckType = courseCheckType;
  }

  public String getModuleCourse() {
    return moduleCourse;
  }

  public void setModuleCourse(String moduleCourse) {
    this.moduleCourse = moduleCourse;
  }

  public String getRuleId() {
    return ruleId;
  }

  public void setRuleId(String ruleId) {
    this.ruleId = ruleId;
  }

  public static void main(String[] args) {
    MybatisXmlTool.getInstance(RawDataProductModel.class, "xd_raw_data_product",
        "D:/work/workspace_xd/xd_2b_dashboard_clean/src/main/resources/conf/mybatis/raw/")
        .buildXml();
  }

}
