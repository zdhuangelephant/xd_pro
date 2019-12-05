package com.xiaodou.live.domain;

import java.sql.Timestamp;

import com.xiaodou.autobuild.annotations.Column;

/**
 * @name @see com.xiaodou.live.domain.LiveBuyRecord.java
 * @CopyRright (c) 2016 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2016年8月30日
 * @description 直播购买记录
 * @version 1.0
 */
public class LiveBuyRecord {

  /** id 主键ID */
  @Column(isMajor = true, canUpdate = false)
  private String id;
  /** orderId 订单ID */
  @Column(canUpdate = false)
  private String orderId;
  /** userId 用户ID */
  @Column(canUpdate = false)
  private String userId;
  /** liveId 直播ID */
  @Column(canUpdate = false)
  private String liveId;
  /** recordStatus 记录状态 0 待支付 1 购买成功 -1 待退款 -2 退款成功 */
  private Short recordStatus;
  /** recordTime 记录时间 */
  @Column(betweenScope = true)
  private Timestamp recordTime;
  /** finishTime 完成时间 */
  @Column(betweenScope = true)
  private Timestamp finishTime;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getOrderId() {
    return orderId;
  }

  public void setOrderId(String orderId) {
    this.orderId = orderId;
  }

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public String getLiveId() {
    return liveId;
  }

  public void setLiveId(String liveId) {
    this.liveId = liveId;
  }

  public Short getRecordStatus() {
    return recordStatus;
  }

  public void setRecordStatus(Short recordStatus) {
    this.recordStatus = recordStatus;
  }

  public Timestamp getRecordTime() {
    return recordTime;
  }

  public void setRecordTime(Timestamp recordTime) {
    this.recordTime = recordTime;
  }

  public Timestamp getFinishTime() {
    return finishTime;
  }

  public void setFinishTime(Timestamp finishTime) {
    this.finishTime = finishTime;
  }

}
