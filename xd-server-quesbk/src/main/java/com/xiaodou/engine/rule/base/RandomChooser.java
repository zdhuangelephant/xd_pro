package com.xiaodou.engine.rule.base;

import java.util.Collections;
import java.util.List;

import com.google.common.collect.Lists;
import com.xiaodou.engine.rule.iterface.IRuleEntity;

/**
 * @name RandomChooser 
 * CopyRright (c) 2015 by <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 *
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2015年7月7日
 * @description 随机选择器
 * @version 1.0
 */
@SuppressWarnings("rawtypes")
public class RandomChooser<T extends IRuleEntity> {

  protected T choose(List<T> ruleList) {
    List<T> _ruleList = Lists.newArrayList(ruleList);
    Collections.shuffle(_ruleList);
    return _ruleList.get(0);
  }

}
