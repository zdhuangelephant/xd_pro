package com.xiaodou.summer.dao.param;

import java.util.Map;

import com.xiaodou.summer.dao.param.QueryEnums.Sort;

/**
 * @name @see com.xiaodou.summer.dao.param.IQueryParam.java
 * @CopyRright (c) 2016 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2016年5月20日
 * @description SQL查询参数类
 * @version 1.0
 */
@SuppressWarnings("rawtypes")
public interface IQueryParam extends IDeleteParam {

  public void addOutput(String key, Object value);

  public void addOutputs(Map<String, Object> outputs);

  public void addOutputs(Class<?> outputType);

  public Map getOutput();

  public void addSort(String key, Sort sort);

  public void addSorts(Map<String, Sort> sorts);

  public Map getSort();

  public void addLimitStart(Object value);

  public void addLimitCount(Object value);

  public Map getLimit();

}
