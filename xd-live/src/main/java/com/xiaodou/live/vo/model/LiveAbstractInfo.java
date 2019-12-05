package com.xiaodou.live.vo.model;

/**
 * @name LiveAbstractInfo 
 * CopyRright (c) 2016 by zhaodan
 *
 * @author zhaodan
 * @date 2016年8月26日
 * @description 直播概要信息
 * @version 1.0
 */
public class LiveAbstractInfo {
  /** liveId 直播ID */
  private String liveId;
  /** liveTitle 直播标题 */
  private String liveTitle;
  /** liveTime 直播时间 */
  private String liveTime;
  /** liveState 直播状态 0 未开播 1 直播中 2 已结束*/
  private String liveState;
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
  public String getLiveTime() {
    return liveTime;
  }
  public void setLiveTime(String liveTime) {
    this.liveTime = liveTime;
  }
  public String getLiveState() {
    return liveState;
  }
  public void setLiveState(String liveState) {
    this.liveState = liveState;
  }

}
