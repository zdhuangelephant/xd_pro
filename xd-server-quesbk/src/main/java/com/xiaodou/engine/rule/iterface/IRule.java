package com.xiaodou.engine.rule.iterface;

/**
 * @name IRule 
 * CopyRright (c) 2015 by <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 *
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2015年7月6日
 * @description 规则整体解析模型
 * @version 1.0
 */
@SuppressWarnings("rawtypes")
public interface IRule<T extends IRuleChooser,V extends IRuleEntity> {
  
  /**
   * 获取规则实体
   */
  public V getRuleEntity();

}
