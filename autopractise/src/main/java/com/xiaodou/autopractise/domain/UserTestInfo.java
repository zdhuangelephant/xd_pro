package com.xiaodou.autopractise.domain;

import com.xiaodou.autopractise.enums.UserTestStatus;
import com.xiaodou.summer.dao.mongo.annotion.Pk;
import com.xiaodou.summer.dao.mongo.model.MongoBaseModel;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @name @see com.xiaodou.autopractise.domain.UserTestInfo.java
 * @CopyRright (c) 2018 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2018年5月7日
 * @description 用户阶段测验信息
 * @version 1.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class UserTestInfo extends MongoBaseModel {
  /** testId 测试ID */
  @Pk
  private String testId;
  /** userId 用户ID */
  private String userId;
  /** courseId 课程ID */
  private String courseId;
  /** index 阶段 */
  private Integer index;
  /** time 练习次数 */
  private Integer time;
  /** paperId 试卷ID */
  private String paperId;
  /** status 测试状态 */
  private Integer status = UserTestStatus.EXAMING.getCode();
  /** score 成绩 */
  private Double score;
  /** date 日期 */
  private String date;
}
