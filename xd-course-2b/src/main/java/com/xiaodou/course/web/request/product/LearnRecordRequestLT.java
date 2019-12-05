package com.xiaodou.course.web.request.product;

import com.xiaodou.course.constant.CourseConstant;
import com.xiaodou.summer.validator.annotion.LegalValue.LegalValueList;
import com.xiaodou.summer.validator.annotion.NotEmpty;
import com.xiaodou.summer.validator.annotion.NotEmpty.OrNotEmptyList;
import com.xiaodou.summer.validator.annotion.OverComeField;
import com.xiaodou.summer.validator.enums.AnnotationType;

/**
 * 
 * 51xiaodou.com (c) 2016 by 李德洪
 * 
 * @name LearnRecordRequest
 * 
 * @author 李德洪
 * @date 2016年5月31日
 * @description TODO
 * @version 1.0
 */

public class LearnRecordRequestLT extends BaseLearnRecordRequest {

  @NotEmpty
  private Integer learnTime;// 学习时长(秒为单位)

  @NotEmpty
  private String courseId; // 课程ID
  @OrNotEmptyList({@NotEmpty(field = "learnType", value = "21"),
      @NotEmpty(field = "learnType", value = "22")})
  private String chapterId;// 章ID
  @NotEmpty(field = "learnType", value = "61")
  private String itemId;// 节ID
  @NotEmpty
  @LegalValueList({CourseConstant.LEARN_RECORD_TYPE_ITEM_BT_ANALYZE,
      CourseConstant.LEARN_RECORD_TYPE_ITEM_BT_EXAM, CourseConstant.LEARN_RECORD_TYPE_DAILY,
      CourseConstant.LEARN_RECORD_TYPE_FINAL, CourseConstant.LEARN_RECORD_TYPE_PK_ANALYZE,
      CourseConstant.LEARN_RECORD_TYPE_PK_EXAM, CourseConstant.LEARN_RECORD_TYPE_TRAINING,
      CourseConstant.LEARN_RECORD_TYPE_WRONGQUES, CourseConstant.LEARN_RECORD_TYPE_DAILY_ANALYZE,
      CourseConstant.LEARN_RECORD_TYPE_FINAL_ANALYZE, CourseConstant.LEARN_RECORD_TYPE_SUPPLEMENT,
      CourseConstant.LEARN_RECORD_TYPE_SUPPLEMENT_ANALYZE,
      CourseConstant.LEARN_RECORD_TYPE_CHAPTER_EX_EXAM,
      CourseConstant.LEARN_RECORD_TYPE_CHAPTER_BT_EXAM,
      CourseConstant.LEARN_RECORD_TYPE_CHAPTER_BT_ANALYZE,
      CourseConstant.LEARN_RECORD_TYPE_TASK_TYPE_MICRO_VIDEO})
  private String learnType;// 类型（pk(11做题，12解析),闯关（21做题，22解析），31修炼，41错题，51每日一练，52每日一练解析，
                           // 61期末考试，62期末考试解析，71查漏补缺，72查漏补缺解析）

  private String learnScope;// 范围（courseId,chapterId,itemId的非空组合范围）


  public Integer getLearnTime() {
    return learnTime;
  }

  public void setLearnTime(Integer learnTime) {
    this.learnTime = learnTime;
  }

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

  public String getChapterId() {
    return chapterId;
  }

  public void setChapterId(String chapterId) {
    this.chapterId = chapterId;
  }

  public String getLearnType() {
    return learnType;
  }

  public void setLearnType(String learnType) {
    this.learnType = learnType;
  }

  public String getLearnScope() {
    return learnScope;
  }

  public void setLearnScope(String learnScope) {
    this.learnScope = learnScope;
  }

  @OverComeField(annotiation = AnnotationType.NotEmpty, field = {"learnTime", "courseId", "itemId",
      "learnType"})
  public static class LearnRecordRequestDTO extends LearnRecordRequestLT {

  }
}
