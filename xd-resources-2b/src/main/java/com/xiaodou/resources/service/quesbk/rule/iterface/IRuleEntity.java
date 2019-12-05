package com.xiaodou.resources.service.quesbk.rule.iterface;

import java.util.List;


/**
 * @name IRuleEntity 
 * CopyRright (c) 2015 by <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 *
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2015年7月6日
 * @description 出题规则的实体 
 * @version 1.0
 */
public interface IRuleEntity<T extends IRuleEntityParam> {

  /**
   * 获取试卷问题列表
   * @throws InterruptedException 
   */
  public List<Object> getQuestions(T param) throws InterruptedException;
 
  /**
   * 获取规则所占权重
   */
  public Integer getWeight();
  
}
