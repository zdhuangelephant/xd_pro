package com.xiaodou.summer.dao.param;

import java.util.Map;
import java.util.Map.Entry;

import org.springframework.util.Assert;

import com.google.common.collect.Maps;
import com.xiaodou.common.util.CommUtil;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.summer.dao.param.QueryEnums.Limit;
import com.xiaodou.summer.dao.param.QueryEnums.Sort;

/**
 * @name @see com.xiaodou.summer.dao.param.QueryParam.java
 * @CopyRright (c) 2018 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2018年1月5日
 * @description 查询参数基础实现
 * @version 1.0
 */
public class QueryParam extends DeleteParam implements IQueryParam {

  /** output 查询输出 */
  private Map<String, Object> output = Maps.newHashMap();
  /** sort 查询排序 */
  private Map<String, Object> sort = Maps.newHashMap();
  /** limit 查询限制 */
  private Map<String, Object> limit = Maps.newHashMap();

  public Map<String, Object> getOutput() {
    return output;
  }

  public Map<String, Object> getSort() {
    return sort;
  }

  public Map<String, Object> getLimit() {
    return limit;
  }

  @Override
  public void addSort(String key, Sort sort) {
    Assert.isTrue(!StringUtils.isBlank(key), "key can't be null");
    Assert.notNull(sort, "sort can't be null");
    this.sort.put(key, sort.toString());
  }

  @Override
  public void addLimitStart(Object value) {
    limit.put(Limit.limitStart.toString(), value);
  }

  @Override
  public void addLimitCount(Object value) {
    limit.put(Limit.limitCount.toString(), value);
  }

  @Override
  public void addOutput(String key, Object value) {
    output.put(key, value);
  }

  @Override
  public void addOutputs(Map<String, Object> outputs) {
    if (null == outputs || outputs.size() == 0) return;
    this.output.putAll(outputs);
  }

  @Override
  public void addSorts(Map<String, Sort> sorts) {
    if (null == sorts || sorts.size() == 0) return;
    for (Entry<String, Sort> entry : sorts.entrySet())
      this.addSort(entry.getKey(), entry.getValue());
  }

  @Override
  public void addOutputs(Class<?> outputType) {
    addOutputs(CommUtil.getAllField(outputType));
  }

}
