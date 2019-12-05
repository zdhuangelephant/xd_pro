package com.xiaodou.resources.response.product;

import java.util.List;

import com.google.common.collect.Lists;
import com.xiaodou.resources.response.BaseResponse;
import com.xiaodou.resources.response.resultype.ProductResType;
import com.xiaodou.resources.vo.product.CourseInfo;
import com.xiaodou.summer.vo.out.ResultType;

public class RecommendCourseResponse extends BaseResponse {
  public RecommendCourseResponse() {}

  public RecommendCourseResponse(ResultType resultType) {
    super(resultType);
  }

  public RecommendCourseResponse(ProductResType resultType) {
    super(resultType);
  }

  /** recommendCourseList 推荐课程列表 */
  private List<CourseInfo> recommendCourseList = Lists.newArrayList();

  public List<CourseInfo> getRecommendCourseList() {
    return recommendCourseList;
  }

  public void setRecommendCourseList(List<CourseInfo> recommendCourseList) {
    this.recommendCourseList = recommendCourseList;
  }
}
