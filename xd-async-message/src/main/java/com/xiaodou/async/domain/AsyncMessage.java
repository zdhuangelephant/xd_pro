package com.xiaodou.async.domain;

import java.sql.Timestamp;
import java.util.UUID;

import com.xiaodou.async.constant.AsyncMessageConst;

public class AsyncMessage {

  /** id 主键ID */
  private String id = UUID.randomUUID().toString();
  /** module 所属模块 */
  private String module;
  /** type 消息类型 1 系统消息 2 其它消息 */
  private Short type;
  /** status 消息状态 1 未读 2 已读 */
  private Short status;
  /** classify 消息分类 1 加好友 2 发pk 3 点赞 4 系统通知 5 系统消息 */
  private Short classify;
  /** srcUid 发起人 */
  private String srcUid;
  /** srcNickName 发起人昵称 */
  private String srcNickName;
  /** srcPortrait 发起人头像 */
  private String srcPortrait;
  /** srcGender 发起人性别 */
  private String srcGender;
  /** srcUserName 发起人账号 */
  private String srcUserName;
  /** srcModuleInfo 发起人模块信息 */
  private String srcModuleInfo;
  /** toUid 接收人 */
  private String toUid;
  /** messageBody 消息内容 */
  private String messageBody;
  /** callBackMessage 回调消息 */
  private String callBackMessage;
  /** callBackContent 回调消息内容 */
  private String callBackContent;
  /** dealResult 处理结果 -1 初始化 0 同意 1 拒绝 2 静止状态 */
  private String dealResult = AsyncMessageConst.DOMAIN_DEALRESULT_INIT;
  /** createTime 消息产生时间 */
  private Timestamp createTime = new Timestamp(System.currentTimeMillis());

  public String getDealResult() {
    return dealResult;
  }

  public void setDealResult(String dealResult) {
    this.dealResult = dealResult;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getModule() {
    return module;
  }

  public void setModule(String module) {
    this.module = module;
  }

  public Short getType() {
    return type;
  }

  public void setType(Short type) {
    this.type = type;
  }

  public Short getStatus() {
    return status;
  }

  public void setStatus(Short status) {
    this.status = status;
  }

  public Short getClassify() {
    return classify;
  }

  public void setClassify(Short classify) {
    this.classify = classify;
  }

  public String getSrcUid() {
    return srcUid;
  }

  public void setSrcUid(String srcUid) {
    this.srcUid = srcUid;
  }

  public String getSrcNickName() {
    return srcNickName;
  }

  public void setSrcNickName(String srcNickName) {
    this.srcNickName = srcNickName;
  }

  public String getSrcPortrait() {
    return srcPortrait;
  }

  public void setSrcPortrait(String srcPortrait) {
    this.srcPortrait = srcPortrait;
  }

  public String getSrcGender() {
    return srcGender;
  }

  public void setSrcGender(String srcGender) {
    this.srcGender = srcGender;
  }

  public String getSrcUserName() {
    return srcUserName;
  }

  public void setSrcUserName(String srcUserName) {
    this.srcUserName = srcUserName;
  }

  public String getSrcModuleInfo() {
    return srcModuleInfo;
  }

  public void setSrcModuleInfo(String srcModuleInfo) {
    this.srcModuleInfo = srcModuleInfo;
  }

  public String getToUid() {
    return toUid;
  }

  public void setToUid(String toUid) {
    this.toUid = toUid;
  }

  public String getMessageBody() {
    return messageBody;
  }

  public void setMessageBody(String messageBody) {
    this.messageBody = messageBody;
  }

  public String getCallBackMessage() {
    return callBackMessage;
  }

  public void setCallBackMessage(String callBackMessage) {
    this.callBackMessage = callBackMessage;
  }

  public String getCallBackContent() {
    return callBackContent;
  }

  public void setCallBackContent(String callBackContent) {
    this.callBackContent = callBackContent;
  }

  public Timestamp getCreateTime() {
    return createTime;
  }

  public void setCreateTime(Timestamp createTime) {
    this.createTime = createTime;
  }

}
