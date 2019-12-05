package com.xiaodou.message.vo;

import java.util.List;
import java.util.Map;
import com.xiaodou.common.util.StringUtils;

public class MessagePushInfo {
  /** module **Notice : 为了兼容教师资格考试,这里设置默认值为1 */
//  private String module;// = "1";
  // 消息类型 2:pushMessageNotice 3:shortMessageNotice
  private String messageType;
  // 目标设备类型
  private String targetType;
  // 消息内容
  private String messageContent;
  // 通知内容
  private String noticeContent;
  // 传播范围，确定以下三种组合的类型
  private String spreadRange;
  // 别名指定的手机号
  private List<String> alias;
  // 分类标签（现有三类）
  private List<String> tags;
  // 设备id
  private List<String> registrationIds;
  // 这条消息被存入数据库之后的id
  private Long messagedbid;
  /** messageextras 消息参数 */
  private Map<String, String> messageextras;
  /** noticeextras 通知参数 */
  private Map<String, String> noticeextras;

  @Override
  public String toString() {
    String str =
        "messageType:" + this.messageType + ",targetType:" + this.targetType + ",messageContent:"
            + this.messageContent + ",noticeContent:" + this.noticeContent + ",spreadRange:"
            + this.spreadRange + ",alias:" + this.alias + ",tags:" + this.tags
            + ",registrationIds:" + this.registrationIds + ",messagedbid:" + this.messagedbid;
    return str;
  }

//  public String getModule() {
//    return module;
//  }
//
//  public void setModule(String module) {
//    this.module = StringUtils.isBlank(module) ? "1" : module;
//  }

  public String getNoticeContent() {
    return noticeContent;
  }

  public void setNoticeContent(String noticeContent) {
    this.noticeContent = noticeContent;
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

  public String getSpreadRange() {
    return spreadRange;
  }

  public void setSpreadRange(String spreadRange) {
    this.spreadRange = spreadRange;
  }

  public List<String> getAlias() {
    return alias;
  }

  public void setAlias(List<String> alias) {
    this.alias = alias;
  }

  public List<String> getTags() {
    return tags;
  }

  public void setTags(List<String> tags) {
    this.tags = tags;
  }

  public List<String> getRegistrationIds() {
    return registrationIds;
  }

  public void setRegistrationIds(List<String> registrationIds) {
    this.registrationIds = registrationIds;
  }

  public Long getMessagedbid() {
    return messagedbid;
  }

  public void setMessagedbid(Long messagedbid) {
    this.messagedbid = messagedbid;
  }

  public Map<String, String> getMessageextras() {
    return messageextras;
  }

  public void setMessageextras(Map<String, String> messageextras) {
    this.messageextras = messageextras;
  }

  public Map<String, String> getNoticeextras() {
    return noticeextras;
  }

  public void setNoticeextras(Map<String, String> noticeextras) {
    this.noticeextras = noticeextras;
  }
}
