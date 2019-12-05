package com.xiaodou.mooccrawler.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.xiaodou.summer.dao.mongo.annotion.Pk;
import com.xiaodou.summer.dao.mongo.model.MongoBaseModel;

/**
 * @name @see com.xiaodou.mooccrawler.domain.MoocCourse.java
 * @CopyRright (c) 2018 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2018年4月25日
 * @description 课程信息参数类
 * @version 1.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class MoocCourse extends MongoBaseModel {
  @Pk
  private String courseId;
  private String cxId;
  private String name;
}
