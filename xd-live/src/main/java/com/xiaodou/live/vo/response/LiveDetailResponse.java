package com.xiaodou.live.vo.response;

import java.util.List;

import com.xiaodou.live.vo.model.LiveComment;
import com.xiaodou.live.vo.model.LiveOwner;
import com.xiaodou.live.vo.model.Tag;
import com.xiaodou.summer.vo.out.ResultInfo;
import com.xiaodou.summer.vo.out.ResultType;

/**
 * @name LiveDetailResponse 
 * CopyRright (c) 2016 by zhaodan
 *
 * @author zhaodan
 * @date 2016年8月26日
 * @description 直播详情响应
 * @version 1.0
 */
public class LiveDetailResponse extends ResultInfo {
  public LiveDetailResponse(ResultType type){
    super(type);
  }
  /** liveId 直播ID */
  private String liveId;
  /** liveUrl 直播地址 */
  private String liveUrl;
  /** liveTitle 直播标题 */
  private String liveTitle;
  /** liveCover 直播封面 */
  private String liveCover;
  /** liveTime 直播时间 */
  private String liveTime;
  /** liveDuration 直播时长 */
  private String liveDuration;
  /** liveTotalQuotas 直播总名额 */
  private String liveTotalQuotas;
  /** liveEnrollments 直播报名人数 */
  private String liveEnrollments;
  /** liveIntroduction 直播内容介绍 */
  private String liveIntroduction;
  /** liveSurplus 直播剩余名额 */
  private String liveSurplus;
  /** liveCharge 报名费用 */
  private String liveCharge;
  /** liveBuyState 直播购买状态 0 未购买 1 已购买 */
  private String liveBuyState;
  /** liveComments 直播评论列表 */
  private List<LiveComment> liveComments;
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
  public String getLiveUrl() {
    return liveUrl;
  }
  public void setLiveUrl(String liveUrl) {
    this.liveUrl = liveUrl;
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
  public String getLiveTotalQuotas() {
    return liveTotalQuotas;
  }
  public void setLiveTotalQuotas(String liveTotalQuotas) {
    this.liveTotalQuotas = liveTotalQuotas;
  }
  public String getLiveEnrollments() {
    return liveEnrollments;
  }
  public void setLiveEnrollments(String liveEnrollments) {
    this.liveEnrollments = liveEnrollments;
  }
  public String getLiveIntroduction() {
    return liveIntroduction;
  }
  public void setLiveIntroduction(String liveIntroduction) {
    this.liveIntroduction = liveIntroduction;
  }
  public String getLiveSurplus() {
    return liveSurplus;
  }
  public void setLiveSurplus(String liveSurplus) {
    this.liveSurplus = liveSurplus;
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
  public List<LiveComment> getLiveComments() {
    return liveComments;
  }
  public void setLiveComments(List<LiveComment> liveComments) {
    this.liveComments = liveComments;
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
