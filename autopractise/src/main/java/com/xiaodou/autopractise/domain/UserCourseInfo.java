package com.xiaodou.autopractise.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.xiaodou.autopractise.enums.UserCourseStatus;
import com.xiaodou.summer.dao.mongo.annotion.Pk;
import com.xiaodou.summer.dao.mongo.model.MongoBaseModel;

/**
 * @name @see com.xiaodou.autopractise.domain.UserCourseInfo.java
 * @CopyRright (c) 2018 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2018年5月4日
 * @description 用户课程绑定实体
 * @version 1.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class UserCourseInfo extends MongoBaseModel {
  /** recordId 记录ID */
  @Pk
  private String recordId;
  /** userId 用户ID */
  private String userId;
  /** courseId 课程ID */
  private String courseId;
  /** courseName 课程名称 */
  private String courseName;
  /** courseType 课程类型 */
  private String courseType;
  /** status 课程状态 */
  private Integer status = UserCourseStatus.EXAMING.getCode();
  /** index 当前阶段 */
  private Integer index;
}
