package com.xiaodou.ms.model.message;

import java.sql.Timestamp;

public class AdminPushMessage {
  // 标识id
  private Long id;
  // 后台消息操作状态（0.未发送1.已经发送）
  private Integer messageStatus;
  // 消息类型 2:pushMessageNotice 3:shortMessageNotice
  /*
   * 0. ALL 通知+消息 2. NOTICE 通知 3. MESSAGE 消息
   */
  private String messageType;
  // 目标设备类型
  /*
   * ALL 全部設備 ALL("0"), ANDROID 安卓設備 ANDROID("1"), IOS IOS設備 IOS("2");
   */
  private String targetType;
  // 消息内容
  private String messageContent;
  // 通知内容
  private String noticeContent;
  // 传播范围，确定以下三种组合的类型
  /**
   * ALL 全部 ALL("0"), INTENDED 指定目标 通过ALIAS别名指定目标 ALIAS("1"), 通过TAG标签指定目标 TAG("2"),
   * 通过registration_id注册ID来指定目标 REGISTRATION_ID("3"), 通过别名ALIAS和标签TAG来指定目标 ALIAS_TAG("4"),
   * 通过ALIAS和REGISTRATION_ID来指定目标 ALIAS_REGISTRATION_ID("5"), 通过TAG和REGISTRATION_ID来指定目标
   * TAG_REGISTRATION_ID("6"), 通过ALIAS,TAG和REGISTRATION_ID来指定目标 ALIAS_TAG_REGISTRATION_ID("7");
   */
  private String spreadRange;
  // 别名指定的手机号
  private String alias;
  // 分类标签（现有三类）
  private String tags;
  // jpush定义的推送id
  private String registrationIds;
  /** messageextras 消息参数 */
  private String messageExtras;
  /** noticeextras 通知参数 */
  private String noticeExtras;
  // 创建时间
  private Timestamp createTime;
  // 创建人
  private String createUser;
  /**
   * 最后操作时间
   */
  private Timestamp updateTime;
  // 操作人
  private String updateUser;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Integer getMessageStatus() {
    return messageStatus;
  }

  public void setMessageStatus(Integer messageStatus) {
    this.messageStatus = messageStatus;
  }

  public String getMessageType() {
    return messageType;
  }

  public void setMessageType(String messageType) {
    this.messageType = messageType;
  }

  public String getTargetType() {
    return targetType;
  }

  public void setTargetType(String targetType) {
    this.targetType = targetType;
  }

  public String getMessageContent() {
    return messageContent;
  }

  public void setMessageContent(String messageContent) {
    this.messageContent = messageContent;
  }

  public String getNoticeContent() {
    return noticeContent;
  }

  public void setNoticeContent(String noticeContent) {
    this.noticeContent = noticeContent;
  }

  public String getSpreadRange() {
    return spreadRange;
  }

  public void setSpreadRange(String spreadRange) {
    this.spreadRange = spreadRange;
  }

  public String getAlias() {
    return alias;
  }

  public void setAlias(String alias) {
    this.alias = alias;
  }

  public String getTags() {
    return tags;
  }

  public void setTags(String tags) {
    this.tags = tags;
  }

  public String getRegistrationIds() {
    return registrationIds;
  }

  public void setRegistrationIds(String registrationIds) {
    this.registrationIds = registrationIds;
  }

  public String getMessageExtras() {
    return messageExtras;
  }

  public void setMessageExtras(String messageExtras) {
    this.messageExtras = messageExtras;
  }

  public String getNoticeExtras() {
    return noticeExtras;
  }

  public void setNoticeExtras(String noticeExtras) {
    this.noticeExtras = noticeExtras;
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

  public String getCreateUser() {
    return createUser;
  }

  public void setCreateUser(String createUser) {
    this.createUser = createUser;
  }

  public String getUpdateUser() {
    return updateUser;
  }

  public void setUpdateUser(String updateUser) {
    this.updateUser = updateUser;
  }
}
