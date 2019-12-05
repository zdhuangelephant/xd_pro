package com.xiaodou.mqcr.model;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Maps;
import com.xiaodou.common.util.log.LoggerUtil;


public enum MessageTrans {

  SUCC("成功", MessageStatus.INIT, MessageStatus.SUCCESS), FAIL("失败", MessageStatus.INIT,
      MessageStatus.FAIL), TIMEOUT("超时", MessageStatus.INIT, MessageStatus.TIMEOUT), OTHER("其它",
      MessageStatus.INIT, MessageStatus.OTHER), FAIL2SUCC("失败2成功", MessageStatus.FAIL,
      MessageStatus.SUCCESS), FAIL2INIT("失败2初始化", MessageStatus.FAIL,
        MessageStatus.INIT),TIMEOUT2SUCC("超时2成功", MessageStatus.TIMEOUT, MessageStatus.SUCCESS), OTHER2SUCC(
      "其它2成功", MessageStatus.OTHER, MessageStatus.SUCCESS);

  private String transName;
  private String tag;
  private List<String> tagLst;
  private Integer fromStatus;
  private Integer toStatus;


  public String getTag() {
    return tag;
  }

  public void setTag(String tag) {
    this.tag = tag;
  }

  public List<String> getTagLst() {
    return tagLst;
  }

  public void setTagLst(List<String> tagLst) {
    this.tagLst = tagLst;
  }

  public String getTransName() {
    return transName;
  }

  public void setTransName(String transName) {
    this.transName = transName;
  }

  public Integer getFromStatus() {
    return fromStatus;
  }

  public void setFromStatus(Integer fromStatus) {
    this.fromStatus = fromStatus;
  }

  public Integer getToStatus() {
    return toStatus;
  }

  public void setToStatus(Integer toStatus) {
    this.toStatus = toStatus;
  }

  MessageTrans(String name, MessageStatus from, MessageStatus to) {
    this.fromStatus = from.getCode();
    this.toStatus = to.getCode();
    this.transName = name;
  }

  public Map<String, Object> getSqlParam() {
    Map<String, Object> param = Maps.newHashMap();
    for (Field argum : this.getClass().getDeclaredFields()) {
      try {
        Object value = argum.get(this);
        if (null != value) {
          param.put(argum.getName(), value);
        }
      } catch (IllegalArgumentException e) {
        LoggerUtil.error("Err:参数不合法:" + argum.getName(), e);
        continue;
      } catch (IllegalAccessException e) {
        LoggerUtil.error("Err:参数无法访问:" + argum.getName(), e);
        continue;
      }
    }
    return param;
  }

}
