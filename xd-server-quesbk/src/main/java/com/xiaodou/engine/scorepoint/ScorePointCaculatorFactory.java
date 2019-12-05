package com.xiaodou.engine.scorepoint;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import com.google.common.collect.Maps;
import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.constant.ProductConstants;
import com.xiaodou.domain.behavior.UserScorePointRecord;

/**
 * @name @see com.xiaodou.engine.scorepoint.ScorePointCaculatorFactory.java
 * @CopyRright (c) 2018 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2018年4月16日
 * @description 积分点计算器工厂
 * @version 1.0
 */
public class ScorePointCaculatorFactory {

  /** caculatorMap 计算器类型集合 */
  private static Map<Short, Class<? extends BaseScorePointCaculator>> caculatorMap = Maps
      .newHashMap();

  static {
    // 节练习
    caculatorMap.put(ProductConstants.RULE_TYPE_ITEM_PRACTICE, ItemScorePointCaculator.class);
    // 章总结
    caculatorMap.put(ProductConstants.RULE_TYPE_CHAPTER_PRACTICE, ChapterScorePointCaculator.class);
    // 期末测试
    caculatorMap.put(ProductConstants.RULE_TYPE_FINAL_EXAM, FinalExamScorePointCaculator.class);
  }

  /**
   * 构建计算器
   * 
   * @param type 计分节点类型
   * @param record 计分点明细
   * @return 计分点计算器
   */
  public static BaseScorePointCaculator buildCaculator(Short type, UserScorePointRecord record) {
    Class<? extends BaseScorePointCaculator> caculatorType = caculatorMap.get(type);
    if (null == caculatorType) {
      return null;
    }
    try {
      Constructor<? extends BaseScorePointCaculator> constructor =
          caculatorType.getConstructor(UserScorePointRecord.class);
      return constructor.newInstance(record);
    } catch (NoSuchMethodException | SecurityException | InstantiationException
        | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
      LoggerUtil.error("构造计分点计算器失败.", e);
      return null;
    }
  }

}
