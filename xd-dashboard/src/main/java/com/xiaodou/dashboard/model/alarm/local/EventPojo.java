package com.xiaodou.dashboard.model.alarm.local;

import com.xiaodou.common.util.StringUtils;
import com.xiaodou.dashboard.model.alarm.IPojo;
import com.xiaodou.dashboard.prop.ExceptionMessageProp;
import com.xiaodou.dashboard.request.AlarmRequestPojo;

/**
 * @name @see com.xiaodou.dashboard.engine.EventPojo.java
 * @CopyRright (c) 2015 by XiaoDou NetWork Technology
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2015年11月25日
 * @description 报警事件实体类
 * @version 1.0
 */
public class EventPojo implements IPojo {
  /** module 所属模块 */
  private String module;
  /** name 事件名称 */
  private String name;
  /** rate 频率 */
  private Integer rate;
  /** threshold 阈值 */
  private Integer threshold;
  /** starttime 开始时间 */
  private Integer starttime;
  /** endtime 结束时间 */
  private Integer endtime;
  /** message 短信联系人 */
  private String message;
  /** mail 邮件联系人 */
  private String mail;
  /** dingURL 钉钉机器人 */
  private String dingURL;

  public EventPojo() {}

  public EventPojo(AlarmRequestPojo pojo) {
    this.module = pojo.getEventModule();
    this.name = pojo.getEventName();
  }

  public String getModule() {
    return module;
  }

  public void setModule(String module) {
    this.module = module;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Integer getRate() {
    return rate;
  }

  public void setRate(Integer rate) {
    this.rate = rate;
  }

  public Integer getThreshold() {
    return threshold;
  }

  public void setThreshold(Integer threshold) {
    this.threshold = threshold;
  }

  public Integer getStarttime() {
    return starttime;
  }

  public void setStarttime(Integer starttime) {
    this.starttime = starttime;
  }

  public Integer getEndtime() {
    return endtime;
  }

  public void setEndtime(Integer endtime) {
    this.endtime = endtime;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public String getMail() {
    return mail;
  }

  public void setMail(String mail) {
    this.mail = mail;
  }

  public String getDingURL() {
    return dingURL;
  }

  public void setDingURL(String dingURL) {
    this.dingURL = dingURL;
  }

  @Override
  public String uniqueId() {
    if (StringUtils.isBlank(module))
      throw new RuntimeException(ExceptionMessageProp.getErrMessage(
          "error.doc.loaddoc.event.misattr", "module"));
    if (StringUtils.isBlank(name))
      throw new RuntimeException(ExceptionMessageProp.getErrMessage(
          "error.doc.loaddoc.event.misattr", "name"));
    return String.format("[EventPojo:{Module:%s;Name:%s}]", module, name);
  }

}
