package com.xiaodou.mqcr.model;

import java.lang.reflect.Field;
import java.sql.Timestamp;
import java.util.Map;

import com.google.common.collect.Maps;
import com.xiaodou.common.util.log.LoggerUtil;

public class MessageEntity {
  
  public MessageEntity(){}
  
  public MessageEntity(String tag){
    this(tag,null);
  }
  
  public MessageEntity(String tag, Integer status){
    this.tag = tag;
    this.status = status;
  }
  
  public MessageEntity(String tag, Integer status, Timestamp insertTime){
    this.tag = tag;
    this.status = status;
    this.inserTime = insertTime;
  }
  
  private String tag;
  
  private Integer status;
  
  private Timestamp inserTime;

  public String getTag() {
    return tag;
  }

  public void setTag(String tag) {
    this.tag = tag;
  }

  public Integer getStatus() {
    return status;
  }

  public void setStatus(Integer status) {
    this.status = status;
  }

  public Timestamp getInserTime() {
    return inserTime;
  }

  public void setInserTime(Timestamp inserTime) {
    this.inserTime = inserTime;
  }
  public static Map<String, Object> getOutputMap(String... fieldNames) {
    Map<String, Object> params = Maps.newHashMap();
    if (fieldNames.length == 0 || null == fieldNames) {
      for (Field argument : MessageEntity.class.getDeclaredFields()) {
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
        } catch (IllegalArgumentException e) {
          LoggerUtil.error("Err:参数不合法:" + argument.getName(), e);
          continue;
        } catch (IllegalAccessException e) {
          LoggerUtil.error("Err:参数无法访问:" + argument.getName(), e);
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

}
