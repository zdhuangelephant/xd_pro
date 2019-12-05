package com.xiaodou.async.model;

import java.util.Map;
import java.util.UUID;

import org.springframework.validation.Errors;

import com.google.common.collect.Maps;
import com.xiaodou.async.constant.AsyncMessageConst;
import com.xiaodou.async.domain.AsyncMessage;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.jmsg.client.RabbitMQSender;
import com.xiaodou.jmsg.entity.AbstractMessagePojo;
import com.xiaodou.summer.validator.annotion.NotEmpty;
import com.xiaodou.summer.vo.in.BaseValidatorPojo;
import com.xiaodou.summer.web.BaseController;

public class AbstractAsyncMessage extends BaseValidatorPojo {

  /** id tag */
  @NotEmpty
  private String id = UUID.randomUUID().toString();
  /** messageName 消息名 */
  @NotEmpty
  private String messageName;
  @NotEmpty
  private String retCode;
  @NotEmpty
  private String retDesc;
  /** module 所属模块 */
  @NotEmpty
  private String module;
  /** type 消息类型 1 系统消息 2 其它消息 */
  @NotEmpty
  private Short type;
  /** status 消息状态 1 未读 2 已读 */
  @NotEmpty
  private Short status;
  /** classify 消息分类 1 加好友 2 发pk 3 点赞 4 系统通知 5 系统消息 */
  @NotEmpty
  private Short classify;
  /** srcUid 发起人 */
  @NotEmpty
  private String srcUid;
  /** toUid 接收人 */
  @NotEmpty
  private String toUid;
  /** messageBody 消息内容 */
  @NotEmpty
  private String messageBody;
  /** callBackContent 回调内容 */
  @NotEmpty(field = "needCallBack", value = "true")
  private Map<String, String> callBackContent = Maps.newHashMap();
  @NotEmpty
  private Boolean needCallBack = Boolean.FALSE;
  /** dealResult 初始记录结果 */
  @NotEmpty
  private String dealResult = AsyncMessageConst.DOMAIN_DEALRESULT_INIT;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getMessageName() {
    return messageName;
  }

  public void setMessageName(String messageName) {
    this.messageName = messageName;
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

  public Map<String, String> getCallBackContent() {
    return callBackContent;
  }

  public void setCallBackContent(Map<String, String> callBackContent) {
    this.callBackContent = callBackContent;
  }

  public void addCallBackContent(String key, String value) {
    callBackContent.put(key, value);
  }

  public Boolean getNeedCallBack() {
    return needCallBack;
  }

  public void setNeedCallBack(Boolean needCallBack) {
    this.needCallBack = needCallBack;
  }

  public String getDealResult() {
    return dealResult;
  }

  public void setDealResult(String dealResult) {
    this.dealResult = dealResult;
  }

  public AsyncMessage getDomain() {
    AsyncMessage domain = new AsyncMessage();
    if (null != type) domain.setType(type);
    if (StringUtils.isNotBlank(toUid)) domain.setToUid(toUid);
    if (null != status) domain.setStatus(status);
    if (StringUtils.isNotBlank(srcUid)) domain.setSrcUid(srcUid);
    if (null != classify) domain.setClassify(classify);
    if (StringUtils.isNotBlank(dealResult)) domain.setDealResult(dealResult);
    if (StringUtils.isNotBlank(module)) domain.setModule(module);
    if (StringUtils.isJsonNotBlank(messageBody)) domain.setMessageBody(messageBody);
    if (needCallBack) {
      domain.setCallBackMessage(messageName + "Callback");
      domain.setCallBackContent(FastJsonUtil.toJson(callBackContent));
    }
    return domain;
  }

  public String getRetCode() {
    return retCode;
  }

  public void setRetCode(String retCode) {
    this.retCode = retCode;
  }

  public String getRetDesc() {
    return retDesc;
  }

  public void setRetDesc(String retDesc) {
    this.retDesc = retDesc;
  }

  public void send() {
    Errors errors = this.validate();
    if (errors.hasErrors()) {
      RuntimeException e = new RuntimeException("发送消息失败");
      LoggerUtil.error(BaseController.getErrMsg(errors), e);
      throw e;
    }
    AbstractMessagePojo pojo = new AbstractMessagePojo(messageName);
    pojo.setCustomTag(id);
    pojo.setTransferObject(this);
    RabbitMQSender.getInstance().send(pojo);
  }

}
