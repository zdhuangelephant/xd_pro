package com.xiaodou.resources.enums.quesbk;

import java.util.Map;

import com.google.common.collect.Maps;

/**
 * @name ExamTimeType CopyRright (c) 2016 by zhaodan
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2016年10月27日
 * @description 考试时间类型
 * @version 1.0
 */
public enum ExamTimeType {
  NOTIME("不限时", 0), THIRTY("30分钟", 1), SIXTY("60分钟", 2), NINTH("90分钟", 3), ONETWENTY("120分钟", 4);

  /**
   * 类型名称
   */
  private String typeName;

  /**
   * 类型Id
   */
  private Integer typeId;

  ExamTimeType(String typeName, Integer typeId) {
    this.typeName = typeName;
    this.typeId = typeId;
  }

  public String getTypeName() {
    return typeName;
  }

  public Integer getTypeId() {
    return typeId;
  }

  private final static Map<Integer, ExamTimeType> _allExamTimeType = Maps.newHashMap();

  private static void init() {
    for (ExamTimeType type : ExamTimeType.values()) {
      if (null == type) continue;
      _allExamTimeType.put(type.getTypeId(), type);
    }
  }

  static {
    init();
  }

  public static ExamTimeType getByTypeId(Integer code) {
    ExamTimeType examTimeType = _allExamTimeType.get(code);
    if (null != examTimeType) return examTimeType;
    return NOTIME;
  }
}
