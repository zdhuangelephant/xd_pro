package com.xiaodou.summer.dao.param;

import java.util.Map;

import com.google.common.collect.Maps;
import com.xiaodou.common.util.CommUtil;

/**
 * @name @see com.xiaodou.summer.dao.param.DeleteParam.java
 * @CopyRright (c) 2018 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2018年1月5日
 * @description 删除参数基础实现
 * @version 1.0
 */
public class DeleteParam implements IDeleteParam {

  /** input 查询条件 */
  private Map<String, Object> inputs = Maps.newHashMap();

  @Override
  public Map<String, Object> getInput() {
    return inputs;
  }

  @Override
  public void addInput(String key, Object value) {
    inputs.put(key, value);
  }


  @Override
  public void addInputs(Map<String, Object> inputs) {
    if (null == inputs || inputs.size() == 0) return;
    this.inputs.putAll(inputs);
  }

  @Override
  public void addInputs(Object queryObject) {
    addInputs(CommUtil.getParams(queryObject));
  }

}
