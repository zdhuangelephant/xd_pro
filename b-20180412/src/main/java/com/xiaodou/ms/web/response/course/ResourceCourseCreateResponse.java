package com.xiaodou.ms.web.response.course;

import com.xiaodou.ms.model.course.CourseSubjectModel;
import com.xiaodou.ms.web.response.BaseResponse;
import com.xiaodou.ms.web.response.ResultType;

/**
 * Created by zyp on 15/4/19.
 */
public class ResourceCourseCreateResponse extends BaseResponse {

  public ResourceCourseCreateResponse(ResultType resultType) {
    super(resultType);
  }

  /** subjectModel 课程模型 */
  private CourseSubjectModel subjectModel;

  public CourseSubjectModel getSubjectModel() {
    return subjectModel;
  }

  public void setSubjectModel(CourseSubjectModel subjectModel) {
    this.subjectModel = subjectModel;
  }

}
