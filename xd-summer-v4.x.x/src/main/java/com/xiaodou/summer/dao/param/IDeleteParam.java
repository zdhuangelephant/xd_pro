package com.xiaodou.summer.dao.param;

import java.util.Map;

/**
 * @name @see com.xiaodou.summer.dao.param.IDeleteParam.java
 * @CopyRright (c) 2016 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2016年5月20日
 * @description SQL删除参数类
 * @version 1.0
 */
@SuppressWarnings("rawtypes")
public interface IDeleteParam {
  public void addInput(String key, Object value);

  public void addInputs(Map<String, Object> inputs);

  public void addInputs(Object queryObject);

  public Map getInput();
}
