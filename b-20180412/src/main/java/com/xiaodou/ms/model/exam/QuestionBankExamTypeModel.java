package com.xiaodou.ms.model.exam;

import java.util.Map;

import com.google.common.collect.Maps;

/**
 * Created by zyp on 15/6/26.
 */
public class QuestionBankExamTypeModel {

  public enum ExamType {

    REAL_PAPER("1", "真题练习"), CHAPTER_PAPER("4", "章练习答题"), CHAPTER_BREAKTHROUGH_PAPER("5", "章总结答题"), ITEM_BREAKTHROUGH_PAPER(
        "6", "节闯关答题"), RANDOM_CHALLENGE_PAPER("7", "随机PK答题"), FRIEND_CHALLENGE_PAPER("8", "好友挑战答题"), DAILY_PRACTICE(
        "9", "每日一练答题"), FINAL_EXAM_PAPER("10", "期末测试答题"), LEAK_FILLING("11", "查漏补缺答题"), UNKNOWN(
        "-1", "未知类型");

    private ExamType(String code, String showName) {
      this.code = code;
      this.showName = showName;
    }

    private String code;
    private String showName;

    public String getCode() {
      return code;
    }

    public void setCode(String code) {
      this.code = code;
    }

    public String getShowName() {
      return showName;
    }

    public void setShowName(String showName) {
      this.showName = showName;
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

  /**
   * 主键ID
   */
  private Integer id;

  /**
   * 名称
   */
  private String examTypeName;

  /**
   * 状态
   */
  private String status;

  /**
   * 描述
   */
  private String mdesc;

  /**
   * 描述
   */
  private String misc;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getExamTypeName() {
    return examTypeName;
  }

  public void setExamTypeName(String examTypeName) {
    this.examTypeName = examTypeName;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getMdesc() {
    return mdesc;
  }

  public void setMdesc(String mdesc) {
    this.mdesc = mdesc;
  }

  public String getMisc() {
    return misc;
  }

  public void setMisc(String misc) {
    this.misc = misc;
  }
}
