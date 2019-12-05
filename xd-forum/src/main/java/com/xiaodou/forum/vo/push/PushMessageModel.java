package com.xiaodou.forum.vo.push;

public class PushMessageModel {
  /**
   * 话题model
   */
  private PushForumModel forumModel;
  /**
   * 设备号
   */
  private String deviceId;
  /**
   * 个推号
   */
  private String pushId;
  /**
   * 用户id
   */
  private Long userId;

  public PushForumModel getForumModel() {
    return forumModel;
  }

  public void setForumModel(PushForumModel forumModel) {
    this.forumModel = forumModel;
  }

  public String getDeviceId() {
    return deviceId;
  }

  public void setDeviceId(String deviceId) {
    this.deviceId = deviceId;
  }

  public String getPushId() {
    return pushId;
  }

  public void setPushId(String pushId) {
    this.pushId = pushId;
  }

  public Long getUserId() {
    return userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }
}
