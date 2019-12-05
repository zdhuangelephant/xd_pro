package com.xiaodou.push.agent.model;

import java.lang.reflect.Field;
import java.util.Map;

import com.google.common.collect.Maps;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.push.agent.annotation.NamePair;
import com.xiaodou.summer.vo.in.BaseValidatorPojo;

/**
 * @name @see cpm.xiaodou.push.agent.model.AbstractMessage.java
 * @CopyRright (c) 2015 by XiaoDou NetWork Technology
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2015年8月16日
 * @description 消息体主干方法实现
 * @version 1.0
 */
public class AbstractMessage extends BaseValidatorPojo {

  /**
   * 获取参数content
   * 
   * @return
   */
  public String getParams() {
    StringBuilder paramBuilder = new StringBuilder(200);
    Map<String, Object> paramMap = Maps.newHashMap();
    for (Field _field : this.getClass().getDeclaredFields()) {
      try {
        if (null != _field.getAnnotation(NamePair.class)) {
          NamePair namePair = _field.getAnnotation(NamePair.class);
          _field.setAccessible(Boolean.TRUE);
          if (StringUtils.isNotBlank(namePair.alias())) {
            paramMap.put(namePair.alias(), _field.get(this));
          } else {
            paramMap.put(_field.getName(), _field.get(this));
          }
          _field.setAccessible(Boolean.FALSE);
        }
      } catch (IllegalArgumentException | IllegalAccessException e) {
        e.printStackTrace();
      }
    }
    paramBuilder.append(FastJsonUtil.toJson(paramMap));
    return paramBuilder.toString();
  }
}
