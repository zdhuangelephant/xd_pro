package com.xiaodou.summer.dao.param;

import java.util.Map;

/**
 * @name @see com.xiaodou.summer.dao.param.IUpdateParam.java
 * @CopyRright (c) 2016 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2016年5月20日
 * @description SQL更新参数类
 * @version 1.0
 */
@SuppressWarnings("rawtypes")
public interface IUpdateParam extends IDeleteParam {
  public void addValue(String key, Object value);

  public void addValues(Map<String, Object> inputs);

  public void addValues(Object queryObject);

  public Map getValue();
}
