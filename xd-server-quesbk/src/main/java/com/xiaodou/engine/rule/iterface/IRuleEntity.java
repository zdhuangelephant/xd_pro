package com.xiaodou.engine.rule.iterface;

import java.util.List;

import com.xiaodou.engine.rule.model.RuleEntityParam;


/**
 * @name IRuleEntity CopyRright (c) 2015 by <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2015年7月6日
 * @description 出题规则的实体
 * @version 1.0
 */
public interface IRuleEntity<T extends IRuleEntityParam> {

  /**
   * 获取试卷问题列表
   * 
   * @throws InterruptedException
   */
  public List<Object> getQuestions(T param) throws InterruptedException;

  /**
   * 获取规则所占权重
   */
  public Integer getWeight();

  /**
   * 2017/11/13_ADD_BY zhaodan 将初始化规则列表从组卷逻辑中抽离出来,方便1.3.8版本以后出题逻辑整体变更为命题蓝图出圈后对老版本的兼容
   * 初始化规则列表(兼容1.3.8以前版本)
   * 
   * @param param
   */
  void initRule_v1_3_8(RuleEntityParam param);

  /**
   * 初始化规则列表
   * 
   * @param param
   */
  void initRule(RuleEntityParam param);

}
