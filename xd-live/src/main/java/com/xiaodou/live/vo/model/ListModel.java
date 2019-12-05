package com.xiaodou.live.vo.model;

import java.util.List;

import com.xiaodou.live.domain.LiveInfo;

/**
 * @name ListModel 
 * CopyRright (c) 2016 by zhaodan
 *
 * @author zhaodan
 * @date 2016年8月24日
 * @description 直播列表模型
 * @version 1.0
 */
public class ListModel {
  public ListModel(LiveInfo live) {
    // TODO Auto-generated constructor stub
  }
  /** liveId 直播ID */
  private String liveId;
  /** liveTitle 直播标题 */
  private String liveTitle;
  /** liveCover 直播封面 */
  private String liveCover;
  /** liveTime 直播时间 */
  private String liveTime;
  /** liveDuration 直播时长 */
  private String liveDuration;
  /** liveEnrollments 直播报名人数 */
  private String liveEnrollments;
  /** liveSurplus 直播剩余名额 */
  private String liveSurplus;
  /** liveChargeType 直播收费类型 0 免费 1 收费 */
  private String liveChargeType;
  /** liveCharge 报名费用 */
  private String liveCharge;
  /** liveBuyState 直播购买状态 0 未购买 1 已购买 */
  private String liveBuyState;
  /** tags 直播标签组 */
  private List<Tag> tags;
  /** liveOwnerInfo 主讲人信息 */
  private LiveOwner liveOwnerInfo;
  public String getLiveId() {
    return liveId;
  }
  public void setLiveId(String liveId) {
    this.liveId = liveId;
  }
  public String getLiveTitle() {
    return liveTitle;
  }
  public void setLiveTitle(String liveTitle) {
    this.liveTitle = liveTitle;
  }
  public String getLiveCover() {
    return liveCover;
  }
  public void setLiveCover(String liveCover) {
    this.liveCover = liveCover;
  }
  public String getLiveTime() {
    return liveTime;
  }
  public void setLiveTime(String liveTime) {
    this.liveTime = liveTime;
  }
  public String getLiveDuration() {
    return liveDuration;
  }
  public void setLiveDuration(String liveDuration) {
    this.liveDuration = liveDuration;
  }
  public String getLiveEnrollments() {
    return liveEnrollments;
  }
  public void setLiveEnrollments(String liveEnrollments) {
    this.liveEnrollments = liveEnrollments;
  }
  public String getLiveSurplus() {
    return liveSurplus;
  }
  public void setLiveSurplus(String liveSurplus) {
    this.liveSurplus = liveSurplus;
  }
  public String getLiveChargeType() {
    return liveChargeType;
  }
  public void setLiveChargeType(String liveChargeType) {
    this.liveChargeType = liveChargeType;
  }
  public String getLiveCharge() {
    return liveCharge;
  }
  public void setLiveCharge(String liveCharge) {
    this.liveCharge = liveCharge;
  }
  public String getLiveBuyState() {
    return liveBuyState;
  }
  public void setLiveBuyState(String liveBuyState) {
    this.liveBuyState = liveBuyState;
  }
  public List<Tag> getTags() {
    return tags;
  }
  public void setTags(List<Tag> tags) {
    this.tags = tags;
  }
  public LiveOwner getLiveOwnerInfo() {
    return liveOwnerInfo;
  }
  public void setLiveOwnerInfo(LiveOwner liveOwnerInfo) {
    this.liveOwnerInfo = liveOwnerInfo;
  }

}
