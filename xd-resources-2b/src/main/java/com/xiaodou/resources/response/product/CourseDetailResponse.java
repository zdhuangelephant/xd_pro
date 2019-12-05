package com.xiaodou.resources.response.product;

import java.util.List;

import com.google.common.collect.Lists;
import com.xiaodou.resources.response.BaseResponse;
import com.xiaodou.resources.vo.product.ChapterInfo;
import com.xiaodou.resources.vo.product.CourseInfo;
import com.xiaodou.summer.vo.out.ResultType;

/**
 * @name CourseDetailResponse 
 * CopyRright (c) 2016 by zhaodan
 *
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2016年10月24日
 * @description 课程详情响应类
 * @version 1.0
 */
public class CourseDetailResponse extends BaseResponse {
  public CourseDetailResponse(){}
  public CourseDetailResponse(ResultType resultType) {
    super(resultType);
  }
  /** courseInfo 课程信息 */
  private CourseInfo courseInfo;
  /** coursePlan 教学计划 */
  private List<ChapterInfo> coursePlan = Lists.newArrayList();
  public CourseInfo getCourseInfo() {
    return courseInfo;
  }
  public void setCourseInfo(CourseInfo courseInfo) {
    this.courseInfo = courseInfo;
  }
  public List<ChapterInfo> getCoursePlan() {
    return coursePlan;
  }
  public void setCoursePlan(List<ChapterInfo> coursePlan) {
    this.coursePlan = coursePlan;
  }
  public static CourseDetailResponse init() {
    CourseDetailResponse res = new CourseDetailResponse(ResultType.SUCCESS);
    res.setCourseInfo(new CourseInfo());
    res.getCoursePlan().add(new ChapterInfo());
    return res;
  }
}