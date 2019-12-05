package com.xiaodou.enums;

import java.util.Map;

import com.google.common.collect.Maps;

public enum ExamType {

  REAL_PAPER("1", "真题练习"), CHAPTER_PAPER("4", "章练习答题"), CHAPTER_BREAKTHROUGH_PAPER("5", "章总结答题"), ITEM_BREAKTHROUGH_PAPER(
      "6", "节闯关答题"), RANDOM_CHALLENGE_PAPER("7", "随机PK答题"), FRIEND_CHALLENGE_PAPER("8", "好友挑战答题"), DAILY_PRACTICE(
      "9", "每日一练答题"), FINAL_EXAM_PAPER("10", "期末测试答题"), LEAK_FILLING("11", "查漏补缺答题");

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
