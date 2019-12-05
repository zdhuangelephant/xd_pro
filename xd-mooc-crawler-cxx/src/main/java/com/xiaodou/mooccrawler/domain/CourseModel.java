package com.xiaodou.mooccrawler.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.xiaodou.common.util.StringUtils;
import com.xiaodou.mooccrawler.vo.CourseList.Course;
import com.xiaodou.mooccrawler.vo.CourseList.Teacher;
import com.xiaodou.summer.dao.mongo.annotion.Pk;
import com.xiaodou.summer.dao.mongo.model.MongoBaseModel;

@Data
@EqualsAndHashCode(callSuper = true)
public class CourseModel extends MongoBaseModel {
  @Pk
  private String cid;
  private Integer banxingType;
  private String banxingTypeName;
  private String bigPicUrl;
  private String config;
  private String subCourseId;
  private Integer courseId;
  private String description;
  private String itemContent;
  private String kcname;
  private Integer keshi;
  private String picUrl;
  private String teacher;

  public void setCourse(Course course) {
    this.cid = course.getCid().toString();
    this.banxingType = course.getBanxingType();
    this.banxingTypeName = course.getBanxingTypeName();
    this.bigPicUrl = course.getBigPicUrl();
    this.config = course.getConfig();
    this.subCourseId = this.config.split(",")[0];
    this.courseId = course.getCourseId();
    this.description = course.getDescription();
    this.itemContent = course.getItemContent();
    this.kcname = course.getKcname();
    this.keshi = course.getKeshi();
    this.picUrl = course.getPicUrl();
    if (null != course.getTeachers() && !course.getTeachers().isEmpty()) {
      Teacher teachers = course.getTeachers().get(0);
      if (null != teachers && StringUtils.isNotBlank(teachers.getNickName())) {
        this.teacher = teachers.getNickName();
      }
    }
  }

  public CourseModel() {}

  public CourseModel(Course c) {
    this();
    setCourse(c);
  }
}
