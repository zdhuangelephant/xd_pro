package com.xiaodou.summer.dao.param;

import java.util.Map;

import com.google.common.collect.Maps;
import com.xiaodou.common.util.CommUtil;

/**
 * @name @see com.xiaodou.summer.dao.param.UpdateParam.java
 * @CopyRright (c) 2018 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2018年1月5日
 * @description 更新参数基础实现
 * @version 1.0
 */
public class UpdateParam extends DeleteParam implements IUpdateParam {

  /** values 赋值域 */
  private Map<String, Object> values = Maps.newHashMap();

  @Override
  public void addValue(String key, Object value) {
    this.values.put(key, value);
  }

  @Override
  public void addValues(Map<String, Object> inputs) {
    if (null == values || values.size() == 0) return;
    this.values.putAll(inputs);
  }

  @Override
  public void addValues(Object entity) {
    Map<String, Object> value = Maps.newHashMap();
    CommUtil.transferFromVO2Map(value, entity);
    addInputs(value);
  }

  @Override
  public Map<String, Object> getValue() {
    return values;
  }

}
