package com.xiaodou.resources.vo.product;

import java.util.List;

import com.google.common.collect.Lists;

/**
 * @name ClassifyInfo 
 * CopyRright (c) 2016 by zhaodan
 *
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2016年10月24日
 * @description 分类信息
 * @version 1.0
 */
public class ClassifyInfo {

  /** classifyId 分类ID */
  private String classifyId = "1";
  /** classifyName 分类名称 */
  private String classifyName = "能源管理必修课";
  /** courseList 课程列表 */
  private List<CourseInfo> courseList = Lists.newArrayList();
  public String getClassifyId() {
    return classifyId;
  }
  public void setClassifyId(String classifyId) {
    this.classifyId = classifyId;
  }
  public String getClassifyName() {
    return classifyName;
  }
  public void setClassifyName(String classifyName) {
    this.classifyName = classifyName;
  }
  public List<CourseInfo> getCourseList() {
    return courseList;
  }
  public void setCourseList(List<CourseInfo> courseList) {
    this.courseList = courseList;
  }

}
