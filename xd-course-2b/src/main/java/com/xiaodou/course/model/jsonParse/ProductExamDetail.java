package com.xiaodou.course.model.jsonParse;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ProductExamDetail {
  /* 近期考试安排 */

  /* 考试期 */
  private String examTime;// eg:1610
  /* 考试日期和时间 */
  private String examDate;// eg:2016年10月22日星期六上午09:00-11:30
  /** tExamDate 考试日期时间 */
  private String examDateStr;

  public String getExamTime() {
    return examTime;
  }

  public void setExamTime(String examTime) {
    this.examTime = examTime;
  }

  public String getExamDate() {
    return examDate;
  }

  public void setExamDate(String examDate) {
    this.examDate = examDate;
  }

  public Date getdExamDate() {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    try {
      return null != examDateStr?sdf.parse(examDateStr):null;
    } catch (ParseException e) {
      e.printStackTrace();
    }
    return null;
  }

  public String getExamDateStr() {
    return examDateStr;
  }

  public void setExamDateStr(String examDateStr) {
    this.examDateStr = examDateStr;
  }

}
