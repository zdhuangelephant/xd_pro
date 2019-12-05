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
 * @description 资源信息参数类
 * @version 1.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class MoocResource extends MongoBaseModel {
  @Pk
  private String resourceId;
  private String courseId;
  private String courseName;
  private String chapterId;
  private String chapterName;
  private String itemId;
  private String itemName;
  private String name;
  private String url;

  public void setItem(MoocItem item) {
    this.courseId = item.getCourseId();
    this.courseName = item.getCourseName();
    this.chapterId = item.getChapterId();
    this.chapterName = item.getName();
    this.itemId = item.getItemId();
    this.itemName = item.getIndex() + item.getName();
  }
}
