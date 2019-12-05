package com.xiaodou.resources.dao.quesbk;

import java.util.List;

import com.xiaodou.resources.model.quesbk.QuesbkExamRule;

/**
 * @name @see com.xiaodou.dao.QuesbkExamRuleMapper.java
 * @CopyRright (c) 2015 by XiaoDou NetWork Technology
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2015年8月8日
 * @description 练习规则数据层Mapper
 * @version 1.0
 */
public interface QuesbkExamRuleMapper {

  /**
   * 根据产品ID和练习类型获取练习规则列表
   * 
   * @param productId 产品ID
   * @param examType 练习类型
   * @return 练习规则列表
   */
  List<QuesbkExamRule> selectByProductIdAndExamType(String productId, String examType);
}
