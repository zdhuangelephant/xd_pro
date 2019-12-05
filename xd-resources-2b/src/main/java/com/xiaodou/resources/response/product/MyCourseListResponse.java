package com.xiaodou.resources.response.product;

import java.util.List;

import com.google.common.collect.Lists;
import com.xiaodou.resources.response.BaseResponse;
import com.xiaodou.resources.vo.product.CourseInfo;
import com.xiaodou.summer.vo.out.ResultType;

/**
 * @name MyCourseListResponse CopyRright (c) 2016 by zhaodan
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2016年10月24日
 * @description 我的课程列表响应
 * @version 1.0
 */
public class MyCourseListResponse extends BaseResponse {
  public MyCourseListResponse() {}

  public MyCourseListResponse(ResultType resultType) {
    super(resultType);
  }

  /** cominglist 未开课 */
  private List<CourseInfo> cominglist = Lists.newArrayList();
  /** progresslist 进行中 */
  private List<CourseInfo> progresslist = Lists.newArrayList();
  /** endlist 已结束 */
  private List<CourseInfo> endlist = Lists.newArrayList();

  public List<CourseInfo> getCominglist() {
    return cominglist;
  }

  public void setCominglist(List<CourseInfo> cominglist) {
    this.cominglist = cominglist;
  }

  public List<CourseInfo> getProgresslist() {
    return progresslist;
  }

  public void setProgresslist(List<CourseInfo> progresslist) {
    this.progresslist = progresslist;
  }

  public List<CourseInfo> getEndlist() {
    return endlist;
  }

  public void setEndlist(List<CourseInfo> endlist) {
    this.endlist = endlist;
  }

}
