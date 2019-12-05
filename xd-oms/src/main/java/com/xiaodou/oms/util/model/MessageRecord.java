package com.xiaodou.oms.util.model;

import java.lang.reflect.Field;
import java.sql.Timestamp;
import java.util.Map;

import com.google.common.collect.Maps;
import com.xiaodou.common.util.log.LoggerUtil;

/**
 * <p>
 * 异步消息入库实体类
 * </p>
 * 
 * @author <a href="mailto:dan.zhao@elong.corp.com">zhaodan</a>
 * @version 1.0
 * @date 2014年8月20日
 */
public class MessageRecord {

  public MessageRecord() {}

  public MessageRecord(String tag) {
    this(tag, null);
  }

  public MessageRecord(String tag, String productLine) {
    this.tag = tag;
    this.productLine = productLine;
  }

  public MessageRecord(String tag, String productLine, Timestamp insertTime) {
    this.tag = tag;
    this.productLine = productLine;
    this.insertTime = insertTime;
  }

  public MessageRecord(String tag, String productLine, String gorderId, String orderId,
      Timestamp insertTime, String content, String messageName) {
    this.tag = tag;
    this.productLine = productLine;
    this.gorderId = gorderId;
    this.orderId = orderId;
    this.insertTime = insertTime;
    this.content = content;
    this.messageName = messageName;
  }

  /**
   * 消息唯一UUID标识
   */
  private String tag;
  /**
   * 消息所属产品线
   */
  private String productLine;
  /** gorderId 支付订单ID */
  private String gorderId;
  /** orderId 订单ID */
  private String orderId;
  /**
   * 消息插入时间
   */
  private Timestamp insertTime;

  /**
   * 消息内容
   */
  private String content;

  /**
   * 消息名
   */
  private String messageName;

  public String getTag() {
    return tag;
  }

  public void setTag(String tag) {
    this.tag = tag;
  }

  public String getProductLine() {
    return productLine;
  }

  public void setProductLine(String productLine) {
    this.productLine = productLine;
  }

  public String getGorderId() {
    return gorderId;
  }

  public void setGorderId(String gorderId) {
    this.gorderId = gorderId;
  }

  public String getOrderId() {
    return orderId;
  }

  public void setOrderId(String orderId) {
    this.orderId = orderId;
  }

  public Timestamp getInsertTime() {
    return insertTime;
  }

  public void setInsertTime(Timestamp insertTime) {
    this.insertTime = insertTime;
  }

  public static Map<String, Object> getOutputMap(String... fieldNames) {
    Map<String, Object> params = Maps.newHashMap();
    if (fieldNames.length == 0 || null == fieldNames) {
      for (Field argument : MessageRecord.class.getDeclaredFields()) {
        try {
          params.put(argument.getName(), 1);
        } catch (IllegalArgumentException e) {
          LoggerUtil.error("Err:参数不合法:" + argument.getName(), e);
          continue;
        }
      }
    }
    for (String name : fieldNames) {
      params.put(name, 1);
    }
    return params;
  }

  public Map<String, Object> getInputMap(String... fieldNames) {
    Map<String, Object> params = Maps.newHashMap();
    if (fieldNames.length == 0 || null == fieldNames) {
      for (Field argument : this.getClass().getDeclaredFields()) {
        try {
          params.put(argument.getName(), argument.get(this));
        } catch (IllegalArgumentException | IllegalAccessException e) {
          LoggerUtil.error("Err:参数不合法:" + argument.getName(), e);
          continue;
        }
      }
    }
    for (String name : fieldNames)
      try {
        params.put(name, this.getClass().getField(name).get(this));
      } catch (IllegalArgumentException e) {
        LoggerUtil.error("Err:参数不合法:" + name, e);
      } catch (IllegalAccessException e) {
        LoggerUtil.error("Err:参数无法访问:" + name, e);
      } catch (NoSuchFieldException e) {
        LoggerUtil.error("Err:不存在属性:" + name, e);
      }
    return params;
  }

  public Map<String, Object> initInOut(String... fieldNames) {
    Map<String, Object> inout = Maps.newHashMap();
    inout.put("input", getInputMap(fieldNames));
    inout.put("output", getOutputMap(fieldNames));
    return inout;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public String getMessageName() {
    return messageName;
  }

  public void setMessageName(String messageName) {
    this.messageName = messageName;
  }
}
