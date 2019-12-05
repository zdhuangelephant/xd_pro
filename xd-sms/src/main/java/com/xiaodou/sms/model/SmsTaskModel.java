package com.xiaodou.sms.model;

import java.sql.Timestamp;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.common.util.warp.FastJsonUtil;

public class SmsTaskModel {
  // 主键id
  private Long id;
  // 短信内容
  private String message;
  // 短信渠道id列表
  private String channelId;
  // 短信模板id
  private Integer templateId;
  // 手机号
  private String mobile;
  // 创建时间
  private Timestamp createTime;
  // 可重试次数
  private Integer canRetryTime;
  // 短信模板类型id
  private Integer templateTypeId;
  // 状态（0待发送 1 正在发送 2发送完毕）
  private Integer status;
  // 短信发送返回说明
  private String msg;
  
  /** appMessageId 应用消息ID */
  private String appMessageId; 
  /** productLine 产品线 */
  private String productLine;
  
  private SmsChannelModel smsChannelModel = new SmsChannelModel();

  public String getMsg() {
    return msg;
  }

  public void setMsg(String msg) {
    this.msg = msg;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public String getChannelId() {
    return channelId;
  }

  public List<Integer> getChannelIdList() {
    if (StringUtils.isJsonBlank(channelId)) return null;
    return JSON.parseArray(channelId, Integer.class);
  }

  public void setChannelIdList(List<Integer> channelId) {
    this.channelId = FastJsonUtil.toJson(channelId);
  }

  public void setChannelId(String channelId) {
    this.channelId = channelId;
  }

  public Integer getTemplateId() {
    return templateId;
  }

  public void setTemplateId(Integer templateId) {
    this.templateId = templateId;
  }

  public String getMobile() {
    return mobile;
  }

  public void setMobile(String mobile) {
    this.mobile = mobile;
  }

  public Timestamp getCreateTime() {
    return createTime;
  }

  public void setCreateTime(Timestamp createTime) {
    this.createTime = createTime;
  }

  public Integer getCanRetryTime() {
    return canRetryTime;
  }

  public void setCanRetryTime(Integer canRetryTime) {
    this.canRetryTime = canRetryTime;
  }

  public Integer getTemplateTypeId() {
    return templateTypeId;
  }

  public void setTemplateTypeId(Integer templateTypeId) {
    this.templateTypeId = templateTypeId;
  }

  public Integer getStatus() {
    return status;
  }

  public void setStatus(Integer status) {
    this.status = status;
  }

  public SmsChannelModel getSmsChannelModel() {
    return smsChannelModel;
  }

  public void setSmsChannelModel(SmsChannelModel smsChannelModel) {
    this.smsChannelModel = smsChannelModel;
  }

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

  @Override
  public String toString() {
    return "SmsTaskModel [id=" + id + ", message=" + message + ", channelId=" + channelId
        + ", templateId=" + templateId + ", mobile=" + mobile + ", createTime=" + createTime
        + ", canRetryTime=" + canRetryTime + ", templateTypeId=" + templateTypeId + ", status="
        + status + ", msg=" + msg + ", appMessageId=" + appMessageId + ", productLine="
        + productLine + ", smsChannelModel=" + smsChannelModel + "]";
  }
 
}
