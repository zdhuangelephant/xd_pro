package com.xiaodou.mooccrawler.vo;

import java.util.List;

import lombok.Data;

import com.alibaba.fastjson.annotation.JSONField;

@Data
public class CourseList {

  private List<Course> V;

  @Data
  public static class Course {
    private Integer banxingType;
    private String banxingTypeName;
    private String bigPicUrl;
    private String config;
    private String subCourseId;
    private Integer courseId;
    private String description;
    private String disInfo;
    private Integer disPrice;
    @JSONField(name = "id")
    private Integer cid;
    private String itemContent;
    private String kcname;
    private Integer keshi;
    private String picUrl;
    private Integer price;
    private List<Teacher> teachers;
  }

  @Data
  public static class Teacher {
    private String nickName;
  }
}
