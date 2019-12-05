package com.xiaodou.util;

import java.text.DecimalFormat;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.xiaodou.common.util.StringUtils;
import com.xiaodou.constant.ProductConstants;
import com.xiaodou.constant.QuesBaseConstant;
import com.xiaodou.enums.ExamType;

public class QuesbkUtil {

  private static final DecimalFormat _FORMAT = new DecimalFormat("#0.00");

  public static DecimalFormat getFormat() {
    return _FORMAT;
  }

  public static <T> List<T> random(List<T> srcList, Integer count) {
    Collections.shuffle(srcList);
    return srcList.subList(0, count);
  }

  public static String trim(String property) {
    return StringUtils.isNotBlank(property) ? property.trim() : property;
  }

  /**
   * 匹配目标串
   */
  public static boolean match(String srcInfo, String matchInfo, String seperator) {
    String _matchInfo = matchInfo.intern();
    if (StringUtils.isNotBlank(seperator)) {
      if (srcInfo.indexOf(seperator) > 0) {
        String forumTem =
            "^:PARAM:" + seperator + "+|" + seperator + "+:PARAM:" + seperator + "+|" + seperator
                + "+:PARAM:$";
        _matchInfo = forumTem.replaceAll(":PARAM:", matchInfo);
      }
    }
    Pattern pattern = Pattern.compile(_matchInfo);
    Matcher matcher = pattern.matcher(srcInfo);
    return matcher.find();
  }


  public static QuesNum getQuesNum(String examType) {
    return new QuesNum(StaticInfoProp.getInt(String
        .format(QuesBaseConstant.QUESNUM_LOWER, examType)), StaticInfoProp.getInt(String.format(
        QuesBaseConstant.QUESNUM_UPPER, examType)));
  }

  public static class QuesNum {
    private static final Random _random = new Random();

    public QuesNum(int lower, int upper) {
      if (lower < 0 || upper < 0 || lower > upper)
        throw new IllegalArgumentException(String.format(
            "lower or upper is wrong. lower is %d, upper is %d", lower, upper));
      this.lower = lower;
      this.upper = upper;
    }

    private Integer lower;
    private Integer upper;

    public Integer getQuesNum() {
      if (lower == upper) return lower;
      return _random.nextInt(upper - lower) + lower;
    }
  }

  public static Short transferExamType(String examTypeId, Long itemId) {
    if (StringUtils.isBlank(examTypeId)) return null;
    ExamType type = ExamType.getByCode(examTypeId);
    if (ExamType.ITEM_BREAKTHROUGH_PAPER.equals(type)) {
      if (null == itemId) {
        return ProductConstants.RULE_TYPE_CHAPTER_PRACTICE;
      }
      return ProductConstants.RULE_TYPE_ITEM_PRACTICE;
    }
    if (ExamType.CHAPTER_BREAKTHROUGH_PAPER.equals(type)) {
      return ProductConstants.RULE_TYPE_CHAPTER_PRACTICE;
    }
    if (ExamType.FINAL_EXAM_PAPER.equals(type)) {
      return ProductConstants.RULE_TYPE_FINAL_EXAM;
    }
    if (ExamType.LEAK_FILLING.equals(type)) {
      return ProductConstants.RULE_TYPE_LEAK_FILLING;
    }
    return null;
  }
}
