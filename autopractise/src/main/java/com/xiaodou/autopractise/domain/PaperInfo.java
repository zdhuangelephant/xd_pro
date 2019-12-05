package com.xiaodou.autopractise.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.xiaodou.summer.dao.mongo.annotion.Pk;
import com.xiaodou.summer.dao.mongo.model.MongoBaseModel;

@Data
@EqualsAndHashCode(callSuper = true)
public class PaperInfo extends MongoBaseModel {
  /** paperId 试卷ID */
  @Pk
  private String paperId;
  /** courseId 课程ID */
  private String courseId;
  /** courseName 课程名称 */
  private String courseName;
  /** quesInfoArray 题目详情列表 */
  private String quesInfoArray;
  /** paperDetail 试卷详情 */
  private String paperDetail;
  /** hasAnswer 是否完成 */
  private Boolean hasAnswer = Boolean.FALSE;
}
