package com.xiaodou.resources.enums.quesbk;

import java.util.Map;

import com.google.common.collect.Maps;

public enum ExamType {

  REAL_PAPER("1", "真题练习"), APTITUDE_PAPER("2", "智能练习"), SIMULATE_PAPER("3", "模拟练习"), CHAPTER_PAPER(
      "4", "章节练习"), HOMEWORK_PAPER("5", "课后练习"), BREAKTHROUGH_PAPER("6", "闯关练习"), RANDOM_CHALLENGE_PAPER(
      "7", "随机挑战练习"), FRIEND_CHALLENGE_PAPER("8", "好友挑战练习"), INCLASS_PAPER("9", "随堂练习"), UNIT_PAPER(
      "10", "单元测验"), FINAL_PAPER("11", "期末考试");

  private ExamType(String code, String name) {
    this._code = code;
    this._name = name;
  }

  private String _code;
  private String _name;

  public String getCode() {
    return _code;
  }

  public void setCode(String code) {
    this._code = code;
  }

  public String getName() {
    return _name;
  }

  public void setName(String name) {
    this._name = name;
  }

  private final static Map<String, ExamType> _allExamType = Maps.newHashMap();

  private static void init() {
    for (ExamType exam : ExamType.values()) {
      if (null == exam) continue;
      _allExamType.put(exam.getCode(), exam);
    }
  }

  static {
    init();
  }

  public static ExamType getByCode(String code) {
    return _allExamType.get(code);
  }

}
