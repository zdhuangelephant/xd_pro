package com.xiaodou.live.domain;

import java.sql.Timestamp;

import com.xiaodou.autobuild.annotations.Column;

/**
 * @name LiveSerie CopyRright (c) 2016 by zhaodan
 * 
 * @author zhaodan
 * @date 2016年8月29日
 * @description 系列直播信息
 * @version 1.0
 */
public class LiveSerie {

  /** id 主键ID */
  @Column(isMajor = true, canUpdate = false)
  private String id;
  /** userId 用户ID */
  @Column(canUpdate = false)
  private String userId;
  /** title 标题 */
  private String title;
  /** cover 封面 */
  private String cover;
  /** timeBegin 直播开始时间 */
  @Column(betweenScope = true)
  private Timestamp timeBegin;
  /** timeOver 直播结束时间 */
  @Column(betweenScope = true)
  private Timestamp timeOver;
  /** timeQuantum 直播时间段 */
  private String timeQuantum;
  /** duration 直播时长 */
  private String duration;
  /** totalQuotas 直播总名额 */
  private Integer totalQuotas;
  /** enrollments 报名人数 */
  private Integer enrollments;
  /** introduction 内容介绍 */
  private String introduction;
  /** chargeType 收费类型 0 免费 1 收费 */
  private Short chargeType;
  /** charge 报名费用 */
  private Integer charge;
  /** tags 标签组 */
  private String tags;
  /** ownerInfo 主讲人信息 */
  private String ownerInfo;
  /** applyTime 申请时间 */
  @Column(betweenScope = true)
  private Timestamp applyTime;
  /** reviewDeadline 审核截止时间 */
  @Column(betweenScope = true)
  private Timestamp reviewDeadline;
  /** reviewTime 审核时间 */
  @Column(betweenScope = true)
  private Timestamp reviewTime;
  /** reviewResult 审核结果 99 草稿 0 待审核 -1 未通过 1 通过 */
  private Short reviewResult;
  /** buyStatus 用户购买状态 */
  private Short buyStatus;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getCover() {
    return cover;
  }

  public void setCover(String cover) {
    this.cover = cover;
  }

  public Timestamp getTimeBegin() {
    return timeBegin;
  }

  public void setTimeBegin(Timestamp timeBegin) {
    this.timeBegin = timeBegin;
  }

  public Timestamp getTimeOver() {
    return timeOver;
  }

  public void setTimeOver(Timestamp timeOver) {
    this.timeOver = timeOver;
  }

  public String getTimeQuantum() {
    return timeQuantum;
  }

  public void setTimeQuantum(String timeQuantum) {
    this.timeQuantum = timeQuantum;
  }

  public String getDuration() {
    return duration;
  }

  public void setDuration(String duration) {
    this.duration = duration;
  }

  public Integer getTotalQuotas() {
    return totalQuotas;
  }

  public void setTotalQuotas(Integer totalQuotas) {
    this.totalQuotas = totalQuotas;
  }

  public Integer getEnrollments() {
    return enrollments;
  }

  public void setEnrollments(Integer enrollments) {
    this.enrollments = enrollments;
  }

  public String getIntroduction() {
    return introduction;
  }

  public void setIntroduction(String introduction) {
    this.introduction = introduction;
  }

  public Short getChargeType() {
    return chargeType;
  }

  public void setChargeType(Short chargeType) {
    this.chargeType = chargeType;
  }

  public Integer getCharge() {
    return charge;
  }

  public void setCharge(Integer charge) {
    this.charge = charge;
  }

  public String getTags() {
    return tags;
  }

  public void setTags(String tags) {
    this.tags = tags;
  }

  public String getOwnerInfo() {
    return ownerInfo;
  }

  public void setOwnerInfo(String ownerInfo) {
    this.ownerInfo = ownerInfo;
  }

  public Timestamp getApplyTime() {
    return applyTime;
  }

  public void setApplyTime(Timestamp applyTime) {
    this.applyTime = applyTime;
  }

  public Timestamp getReviewDeadline() {
    return reviewDeadline;
  }

  public void setReviewDeadline(Timestamp reviewDeadline) {
    this.reviewDeadline = reviewDeadline;
  }

  public Timestamp getReviewTime() {
    return reviewTime;
  }

  public void setReviewTime(Timestamp reviewTime) {
    this.reviewTime = reviewTime;
  }

  public Short getReviewResult() {
    return reviewResult;
  }

  public void setReviewResult(Short reviewResult) {
    this.reviewResult = reviewResult;
  }

  public Short getBuyStatus() {
    return buyStatus;
  }

  public void setBuyStatus(Short buyStatus) {
    this.buyStatus = buyStatus;
  }

}
