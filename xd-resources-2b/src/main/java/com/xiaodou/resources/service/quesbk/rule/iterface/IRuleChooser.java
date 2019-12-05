package com.xiaodou.resources.service.quesbk.rule.iterface;

import java.util.List;

/**
 * @name IRuleChooser 
 * CopyRright (c) 2015 by <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 *
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2015年7月6日
 * @description 规则选择器
 * @version 1.0
 */
public interface IRuleChooser<T> {

  /**
   * 从规则列表中选出使用规则 
   */
  public T chooseRule(List<T> ruleList);

}
