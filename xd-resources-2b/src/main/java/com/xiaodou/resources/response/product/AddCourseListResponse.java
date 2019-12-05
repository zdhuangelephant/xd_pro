package com.xiaodou.resources.response.product;

import java.util.List;

import com.google.common.collect.Lists;
import com.xiaodou.resources.response.BaseResponse;
import com.xiaodou.resources.response.resultype.ProductResType;
import com.xiaodou.resources.vo.product.ClassifyInfo;
import com.xiaodou.resources.vo.product.CourseInfo;
import com.xiaodou.resources.vo.product.Topic;
import com.xiaodou.summer.vo.out.ResultType;

/**
 * @name AddCourseListResponse CopyRright (c) 2016 by zhaodan
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2016年10月24日
 * @description 添加课程列表响应类
 * @version 1.0
 */
public class AddCourseListResponse extends BaseResponse {
  public AddCourseListResponse() {}

  public AddCourseListResponse(ResultType resultType) {
    super(resultType);
  }

  public AddCourseListResponse(ProductResType resultType) {
    super(resultType);
  }

  /** topicList 轮播图列表 */
  private List<Topic> topicList = Lists.newArrayList();
  /** recommendCourseList 推荐课程列表 */
  private List<CourseInfo> recommendCourseList = Lists.newArrayList();
  /** commonClassifyList 分类列表 */
  private List<ClassifyInfo> commonClassifyList = Lists.newArrayList();

  public List<Topic> getTopicList() {
    return topicList;
  }

  public void setTopicList(List<Topic> topicList) {
    this.topicList = topicList;
  }

  public List<CourseInfo> getRecommendCourseList() {
    return recommendCourseList;
  }

  public void setRecommendCourseList(List<CourseInfo> recommendCourseList) {
    this.recommendCourseList = recommendCourseList;
  }

  public List<ClassifyInfo> getCommonClassifyList() {
    return commonClassifyList;
  }

  public void setCommonClassifyList(List<ClassifyInfo> commonClassifyList) {
    this.commonClassifyList = commonClassifyList;
  }

  public static AddCourseListResponse init() {
    AddCourseListResponse res = new AddCourseListResponse(ResultType.SUCCESS);
    res.getTopicList().add(new Topic());
    res.getRecommendCourseList().add(new CourseInfo());
    ClassifyInfo classify = new ClassifyInfo();
    classify.getCourseList().add(new CourseInfo());
    res.getCommonClassifyList().add(classify);
    return res;
  }
}
