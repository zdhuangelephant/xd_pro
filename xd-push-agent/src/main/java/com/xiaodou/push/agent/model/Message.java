package com.xiaodou.push.agent.model;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.push.agent.annotation.NamePair;
import com.xiaodou.push.agent.enums.MessageType;
import com.xiaodou.push.agent.enums.SpreadRange;
import com.xiaodou.push.agent.enums.TargetType;
import com.xiaodou.summer.validator.annotion.NotEmpty;
import com.xiaodou.summer.validator.annotion.NotEmpty.OrNotEmptyList;

/**
 * @name @see cpm.xiaodou.push.agent.model.PushMessage.java
 * @CopyRright (c) 2015 by XiaoDou NetWork Technology
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2015年8月14日
 * @description 推送系统接收推送消息模型
 * @version 1.0
 */
public class Message extends AbstractMessage {

  /** id 唯一标识 */
  @NamePair
  private UUID id = UUID.randomUUID();

  /** recieverList 接收者列表 */
  @OrNotEmptyList({@NotEmpty(field = "scope", value = "ALIAS"),
      @NotEmpty(field = "scope", value = "ALIAS_TAG"),
      @NotEmpty(field = "scope", value = "ALIAS_REGISTRATION_ID"),
      @NotEmpty(field = "scope", value = "ALIAS_TAG_REGISTRATION_ID")})
  @NamePair
  private List<String> recieverList = Lists.newArrayList();

  /** groupList 分组列表 */
  @OrNotEmptyList({@NotEmpty(field = "scope", value = "TAG"),
      @NotEmpty(field = "scope", value = "ALIAS_TAG"),
      @NotEmpty(field = "scope", value = "TAG_REGISTRATION_ID"),
      @NotEmpty(field = "scope", value = "ALIAS_TAG_REGISTRATION_ID")})
  @NamePair
  private List<String> groupList = Lists.newArrayList();

  /** registerList 注册列表 */
  @OrNotEmptyList({@NotEmpty(field = "scope", value = "REGISTRATION_ID"),
      @NotEmpty(field = "scope", value = "ALIAS_REGISTRATION_ID"),
      @NotEmpty(field = "scope", value = "TAG_REGISTRATION_ID"),
      @NotEmpty(field = "scope", value = "ALIAS_TAG_REGISTRATION_ID")
  /*
   * @NotEmpty(field = "recieverList", value = "null"),
   * 
   * @NotEmpty(field = "groupList", value = "null")
   */})
  @NamePair
  private List<String> registerList = Lists.newArrayList();

  /** target 目标设备类型 */
  @NotEmpty
  @NamePair
  private TargetType target;

  /** messageType 消息类型 */
  @NotEmpty
  @NamePair
  private MessageType messageType;

  /** scope 传播范围 */
  @NotEmpty
  @NamePair
  private SpreadRange scope;
  /** noticeContent 通知内容 */
  @OrNotEmptyList({@NotEmpty(field = "messageType", value = "ALL"),
      @NotEmpty(field = "messageType", value = "NOTICE")})
  @NamePair
  private String noticeContent;

  /** messageContent 消息内容 */
  @OrNotEmptyList({@NotEmpty(field = "messageType", value = "ALL"),
      @NotEmpty(field = "messageType", value = "MESSAGE")})
  @NamePair
  private String messageContent;

  /** messageextras 消息参数 */
  @NotEmpty
  @NamePair
  private Map<String, String> messageextras;

  /** noticeextras 通知参数 */
  @NotEmpty
  @NamePair
  private Map<String, String> noticeextras;

  /** otherTags 其他标记列表 */
  @OrNotEmptyList({@NotEmpty(field = "scope", value = "INTENDED"),
      @NotEmpty(field = "messageType", value = "ALL"),
      @NotEmpty(field = "messageType", value = "NOTICE")})
  @NamePair
  private Map<String, Object> otherTags = Maps.newHashMap();
  
  /** appMessageId 应用消息ID */
  @NotEmpty
  @NamePair
  private String appMessageId;
  
  /** productLine 产品线 */
  @NotEmpty
  @NamePair
  private String productLine;

  public String getAppMessageId() {
    return appMessageId;
  }

  public void setAppMessageId(String appMessageId) {
    this.appMessageId = appMessageId;
  }

  public String getProductLine() {
    return productLine;
  }

  public void setProductLine(String productLine) {
    this.productLine = productLine;
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

  public String getNoticeContent() {
    return noticeContent;
  }

  public UUID getId() {
    return id;
  }

  public List<String> getRecieverList() {
    return recieverList;
  }

  public void addReciever(String reciever) {
    this.recieverList.add(reciever);
  }
  
  public void addReciever(String... recieverList) {
    this.recieverList.addAll(Lists.newArrayList(recieverList));
  }
  
  public void addReciever(List<String> recieverList) {
    if (null == recieverList || recieverList.size() == 0) return;
    this.recieverList.addAll(recieverList);
  }

  public List<String> getGroupList() {
    return groupList;
  }

  public void addGroup(String group) {
    this.groupList.add(group);
  }
  
  public void addGroup(String... groupList) {
    this.groupList.addAll(Lists.newArrayList(groupList));
  }

  public void addGroup(List<String> groupList) {
    if (null == groupList || groupList.size() == 0) return;
    this.groupList.addAll(groupList);
  }

  public List<String> getRegisterList() {
    return registerList;
  }

  public void addRegister(String register) {
    this.registerList.add(register);
  }

  public void addRegister(String... registerList) {
    this.registerList.addAll(Lists.newArrayList(registerList));
  }

  public void addRegister(List<String> registerList) {
    if (null == registerList || registerList.size() == 0) return;
    this.registerList.addAll(registerList);
  }

  public Map<String, Object> getOtherTags() {
    return otherTags;
  }

  public void addOtherTags(String key, Object value) {
    this.otherTags.put(key, value);
  }

  public void setNoticeContent(String noticeContent) {
    this.noticeContent = noticeContent;
  }

  public String getMessageContent() {
    return messageContent;
  }

  public void setMessageContent(String messageContent) {
    this.messageContent = messageContent;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public TargetType getTarget() {
    return target;
  }

  public void setTarget(TargetType target) {
    this.target = target;
  }

  public MessageType getMessageType() {
    return messageType;
  }

  public void setMessageType(MessageType messageType) {
    this.messageType = messageType;
  }

  public SpreadRange getScope() {
    return scope;
  }

  public void setScope(SpreadRange scope) {
    this.scope = scope;
  }

  @Override
  public String toString() {
    return FastJsonUtil.toJson(this);
  }

}
