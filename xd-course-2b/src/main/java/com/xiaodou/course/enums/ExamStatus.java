package com.xiaodou.course.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * @name @see com.xiaodou.course.enums.ExamStatus.java
 * @CopyRright (c) 2015 by XiaoDou NetWork Technology
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2015年9月25日
 * @description 考试状态
 * @version 1.0
 */
public enum ExamStatus {

  /** PASS 合格 */
  PASS(1, "合格"),
  /** NOPASS 不合格 */
  NOPASS(2, "不合格"),
  /** UNEXAM 缺考 */
  UNEXAM(3, "缺考"),
  /** UNKNOWN 未知状态 */
  UNKNOWN(-1, "未知");

  ExamStatus(Integer code, String state) {
    this.code = code;
    this.state = state;
  }

  private Integer code;
  private String state;

  public Integer getCode() {
    return code;
  }

  public String getState() {
    return state;
  }

  private static final Map<String, ExamStatus> keyMap = new HashMap<String, ExamStatus>() {
    /** serialVersionUID */
    private static final long serialVersionUID = -7686147777368509116L;

    public HashMap<String, ExamStatus> initThis() {
      for (ExamStatus status : ExamStatus.values()) {
        this.put(status.state, status);
      }
      return this;
    }
  }.initThis();

  public static ExamStatus getByState(String state) {
    ExamStatus examStatus = keyMap.get(state.trim());
    if (null == examStatus) return UNKNOWN;
    return examStatus;
  }
}
