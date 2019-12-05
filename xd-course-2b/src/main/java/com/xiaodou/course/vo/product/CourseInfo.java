package com.xiaodou.course.vo.product;

import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

/**
 * Created by zyp on 15/8/20.
 */
public class CourseInfo {

  private static SimpleDateFormat _FORMAT = new SimpleDateFormat("yy-MM-dd");

  private static DecimalFormat _DFORMAT = new DecimalFormat("#0.00");

  // 最后学习日期
  private Timestamp lastStudyDate;

  // 章节名称
  private String chapterName;

  // 章节Id
  private String chapterId = "-1";

  // 条目名称
  private String itemName;

  // 条目Id
  private String itemId = "-1";

  // 学习进度
  private Integer studyProgress;

  public String getLastStudyDate() {
    return _FORMAT.format(lastStudyDate);
  }

  public void setLastStudyDate(Timestamp lastStudyDate) {
    this.lastStudyDate = lastStudyDate;
  }

  public String getChapterName() {
    return chapterName;
  }

  public void setChapterName(String chapterName) {
    this.chapterName = chapterName;
  }

  public String getChapterId() {
    return chapterId;
  }

  public void setChapterId(String chapterId) {
    this.chapterId = chapterId;
  }

  public String getItemId() {
    return itemId;
  }

  public void setItemId(String itemId) {
    this.itemId = itemId;
  }

  public String getItemName() {
    return itemName;
  }

  public void setItemName(String itemName) {
    this.itemName = itemName;
  }

  public String getStudyProgress() {
    return _DFORMAT.format(new Double(studyProgress) / 100d);
  }

  public void setStudyProgress(Integer studyProgress) {
    this.studyProgress = studyProgress;
  }
}
