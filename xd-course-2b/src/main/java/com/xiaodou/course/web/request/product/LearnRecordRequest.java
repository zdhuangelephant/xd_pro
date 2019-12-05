package com.xiaodou.course.web.request.product;

import lombok.Data;

import com.xiaodou.course.constant.CourseConstant;
import com.xiaodou.summer.validator.annotion.LegalValue.LegalValueList;
import com.xiaodou.summer.validator.annotion.NotEmpty;
import com.xiaodou.summer.validator.annotion.NotEmpty.OrNotEmptyList;

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
public class LearnRecordRequest extends BaseLearnRecordRequest {

  /** learnTime 学习时长(秒为单位) */
  @NotEmpty
  private Integer learnTime;
  /** courseId 课程ID */
  @NotEmpty
  private String courseId;
  /** chapterId 章ID */
  @OrNotEmptyList({
      @NotEmpty(field = "learnType", value = CourseConstant.LEARN_RECORD_TYPE_CHAPTER_EX_EXAM),
      @NotEmpty(field = "learnType", value = CourseConstant.LEARN_RECORD_TYPE_CHAPTER_BT_EXAM),
      @NotEmpty(field = "learnType", value = CourseConstant.LEARN_RECORD_TYPE_ITEM_BT_EXAM),
      @NotEmpty(field = "learnType", value = CourseConstant.LEARN_RECORD_TYPE_ITEM_BT_ANALYZE),
      @NotEmpty(field = "learnType", value = CourseConstant.LEARN_RECORD_TYPE_TRAINING)})
  private String chapterId;
  /** itemId 节ID */
  @OrNotEmptyList({
      @NotEmpty(field = "learnType", value = CourseConstant.LEARN_RECORD_TYPE_ITEM_BT_EXAM),
      @NotEmpty(field = "learnType", value = CourseConstant.LEARN_RECORD_TYPE_ITEM_BT_ANALYZE),
      @NotEmpty(field = "learnType", value = CourseConstant.LEARN_RECORD_TYPE_TRAINING),
      @NotEmpty(field = "learnType", value = CourseConstant.LEARN_RECORD_TYPE_FINAL)})
  private String itemId;
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
      CourseConstant.LEARN_RECORD_TYPE_TASK_TYPE_VIDEO,
      CourseConstant.LEARN_RECORD_TYPE_TASK_TYPE_AUDIO,
      CourseConstant.LEARN_RECORD_TYPE_TASK_TYPE_MICRO_VIDEO})
  private String learnType;
  private String learnScope;// 范围（courseId,chapterId,itemId的非空组合范围）

  /** lastLearnTime Modify by zdhuang at 2018-7-10 12:54:09 */
  @OrNotEmptyList({
      @NotEmpty(field = "learnType", value = CourseConstant.LEARN_RECORD_TYPE_TASK_TYPE_MICRO_VIDEO),
      @NotEmpty(field = "learnType", value = CourseConstant.LEARN_RECORD_TYPE_TASK_TYPE_VIDEO),
      @NotEmpty(field = "learnType", value = CourseConstant.LEARN_RECORD_TYPE_TASK_TYPE_AUDIO)})
  // private List<String> points;
  private String points;

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

  public String getPoints() {
    return points;
  }

  public void setPoints(String points) {
    this.points = points;
  }



  @Data
  public static class PointVo {
    private String pointId;
    private String lastLearnTime;
  }

}
