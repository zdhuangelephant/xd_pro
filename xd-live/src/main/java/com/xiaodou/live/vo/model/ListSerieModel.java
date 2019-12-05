package com.xiaodou.live.vo.model;

import java.util.List;

import com.xiaodou.live.domain.LiveSerie;

/**
 * @name ListSerieModel 
 * CopyRright (c) 2016 by zhaodan
 *
 * @author zhaodan
 * @date 2016年8月24日
 * @description 直播系列列表模型
 * @version 1.0
 */
public class ListSerieModel {
  public ListSerieModel(LiveSerie serie) {
    // TODO Auto-generated constructor stub
  }
  /** liveSerieId 直播系列ID */
  private String liveSerieId;
  /** liveSerieTitle 直播系列标题 */
  private String liveSerieTitle;
  /** liveSerieCover 直播系列封面 */
  private String liveSerieCover;
  /** liveSerieTimeQuantum 直播系列时间段 */
  private String liveSerieTimeQuantum;
  /** liveSerieDuration 直播系列时长 */
  private String liveSerieDuration;
  /** liveSerieEnrollments 直播系列报名人数 */
  private String liveSerieEnrollments;
  /** liveSerieSurplus 直播系列剩余名额 */
  private String liveSerieSurplus;
  /** liveSerieChargeType 直播系列收费类型 1 收费 0 免费 */
  private String liveSerieChargeType;
  /** liveSerieCharge 系列报名费用 */
  private String liveSerieCharge;
  /** liveSerieBuyState 直播系列购买状态 0 未购买 1 已购买 */
  private String liveSerieBuyState;
  /** tags 直播系列标签组 */
  private List<Tag> tags;
  /** liveOwnerInfo 主讲人信息 */
  private LiveOwner liveOwnerInfo;
  public String getLiveSerieId() {
    return liveSerieId;
  }
  public void setLiveSerieId(String liveSerieId) {
    this.liveSerieId = liveSerieId;
  }
  public String getLiveSerieTitle() {
    return liveSerieTitle;
  }
  public void setLiveSerieTitle(String liveSerieTitle) {
    this.liveSerieTitle = liveSerieTitle;
  }
  public String getLiveSerieCover() {
    return liveSerieCover;
  }
  public void setLiveSerieCover(String liveSerieCover) {
    this.liveSerieCover = liveSerieCover;
  }
  public String getLiveSerieTimeQuantum() {
    return liveSerieTimeQuantum;
  }
  public void setLiveSerieTimeQuantum(String liveSerieTimeQuantum) {
    this.liveSerieTimeQuantum = liveSerieTimeQuantum;
  }
  public String getLiveSerieDuration() {
    return liveSerieDuration;
  }
  public void setLiveSerieDuration(String liveSerieDuration) {
    this.liveSerieDuration = liveSerieDuration;
  }
  public String getLiveSerieEnrollments() {
    return liveSerieEnrollments;
  }
  public void setLiveSerieEnrollments(String liveSerieEnrollments) {
    this.liveSerieEnrollments = liveSerieEnrollments;
  }
  public String getLiveSerieSurplus() {
    return liveSerieSurplus;
  }
  public void setLiveSerieSurplus(String liveSerieSurplus) {
    this.liveSerieSurplus = liveSerieSurplus;
  }
  public String getLiveSerieChargeType() {
    return liveSerieChargeType;
  }
  public void setLiveSerieChargeType(String liveSerieChargeType) {
    this.liveSerieChargeType = liveSerieChargeType;
  }
  public String getLiveSerieCharge() {
    return liveSerieCharge;
  }
  public void setLiveSerieCharge(String liveSerieCharge) {
    this.liveSerieCharge = liveSerieCharge;
  }
  public String getLiveSerieBuyState() {
    return liveSerieBuyState;
  }
  public void setLiveSerieBuyState(String liveSerieBuyState) {
    this.liveSerieBuyState = liveSerieBuyState;
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
