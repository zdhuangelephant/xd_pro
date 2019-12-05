package com.xiaodou.mooccrawler.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.xiaodou.summer.dao.mongo.annotion.Pk;
import com.xiaodou.summer.dao.mongo.model.MongoBaseModel;

/**
 * @name @see com.xiaodou.mooccrawler.request.CourseInfoPojo.java
 * @CopyRright (c) 2018 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2018年4月25日
 * @description 节信息参数类
 * @version 1.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class MoocItem extends MongoBaseModel {
  @Pk
  private String itemId;
  private String courseId;
  private String courseName;
  private String chapterId;
  private String chapterName;
  private String index;
  private String name;
  private String href;
  private Boolean download = false;

  public void setChapter(MoocChapter chapter) {
    this.courseId = chapter.getCourseId();
    this.courseName = chapter.getCourseName();
    this.chapterId = chapter.getChapterId();
    this.chapterName = chapter.getIndex() + chapter.getName();
  }
}
