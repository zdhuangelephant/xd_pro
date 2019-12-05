package com.xiaodou.server.mapi.request;

import com.xiaodou.summer.validator.annotion.LegalValue.LegalValueList;
import com.xiaodou.summer.validator.annotion.NotEmpty;

public class LearnCostRequest extends MapiBaseRequest {
  @NotEmpty
  private String courseId; // 课程ID
  @NotEmpty
  private String chapterId;//章ID
  @NotEmpty
  private String itemId;// 节ID
  @NotEmpty
  @LegalValueList({"1", "2"})
  private String timing;// 1計時开始 2计时结束
  @NotEmpty
  @LegalValueList({"1","2","3"})
  private String learnType;// 1修炼2闯关3废弃状态(闯关中途退出)
  @NotEmpty(field = "timing", value = "2")
  private String learnRecordId;//学习记录id
  
  public String getCourseId() {
    return courseId;
  }

  public void setCourseId(String courseId) {
    this.courseId = courseId;
  }

  public String getItemId() {
    return itemId;
  }

  public void setItemId(String itemId) {
    this.itemId = itemId;
  }

  public String getTiming() {
    return timing;
  }

  public void setTiming(String timing) {
    this.timing = timing;
  }

  public String getLearnType() {
    return learnType;
  }

  public void setLearnType(String learnType) {
    this.learnType = learnType;
  }

  public String getLearnRecordId() {
    return learnRecordId;
  }

  public void setLearnRecordId(String learnRecordId) {
    this.learnRecordId = learnRecordId;
  }

  public String getChapterId() {
    return chapterId;
  }

  public void setChapterId(String chapterId) {
    this.chapterId = chapterId;
  }
}
