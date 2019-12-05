package com.xiaodou.st.dataclean.model.transport;

import java.math.BigDecimal;
import java.sql.Timestamp;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.xiaodou.summer.validator.annotion.NotEmpty;

/**
 * @name ProductModel CopyRright (c) 2016 by zhaodan
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2016年11月11日
 * @description 产品数据
 * @version 1.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class TransferProductData extends BaseTransferModel {

  // 主键Id
  @NotEmpty
  private Long id;

  // 产品名
  private String name;

  // 简介
  private String briefInfo;

  // 详情
  private String detail;

  // 图片地址
  private String imageUrl;

  // 当前报名人数
  private Integer currApplyCount;

  // 报名人数上限
  private Integer totalApplyCount;

  // 开始申请时间
  private Timestamp beginApplyTime;

  // 申请结束时间
  private Timestamp endApplyTime;

  // 原价
  private BigDecimal originalAmount;

  // 优惠价
  private BigDecimal payAmount;

  // 创建时间
  private Timestamp createTime;

  // 更新时间
  private Timestamp updateTime;

  // misc 杂项
  private String misc;

  // 是否显示
  private Integer showStatus;

  // 点赞数
  private Integer praiseCount;

  // 题库设置
  private String questionBankSetting;

  // 资源产品ID
  private Integer resourceSubject;

  /** shareUrl 分享url */
  private String shareUrl;

  /** courseCode 课程码 */
  private String courseCode;
  /** examDate 考期 */
  private String examDate;

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

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
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

  public String getShareUrl() {
    return shareUrl;
  }

  public void setShareUrl(String shareUrl) {
    this.shareUrl = shareUrl;
  }

  public String getCourseCode() {
    return courseCode;
  }

  public void setCourseCode(String courseCode) {
    this.courseCode = courseCode;
  }

  public String getExamDate() {
    return examDate;
  }

  public void setExamDate(String examDate) {
    this.examDate = examDate;
  }

}
