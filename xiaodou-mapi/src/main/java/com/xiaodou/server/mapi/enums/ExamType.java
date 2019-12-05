package com.xiaodou.server.mapi.enums;

import java.util.Map;

import com.google.common.collect.Maps;

public enum ExamType {

  REAL_PAPER("1", "真题练习", true), CHAPTER_PAPER("4", "章练习答题", false), CHAPTER_BREAKTHROUGH_PAPER(
      "5", "章总结答题", false), ITEM_BREAKTHROUGH_PAPER("6", "节闯关答题", false), RANDOM_CHALLENGE_PAPER(
      "7", "随机PK答题", true), FRIEND_CHALLENGE_PAPER("8", "好友挑战答题", true), DAILY_PRACTICE("9",
      "每日一练答题", true), FINAL_EXAM_PAPER("10", "期末测试答题", true), LEAK_FILLING("11", "查漏补缺答题", true);
  private ExamType(String code, String name, boolean allCourse) {
    this._code = code;
    this._name = name;
    this._allCourse = allCourse;
  }

  private String _code;
  private String _name;
  private boolean _allCourse;

  public boolean isAllCourse() {
    return _allCourse;
  }

  public void setAllCourse(boolean _allCourse) {
    this._allCourse = _allCourse;
  }

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
