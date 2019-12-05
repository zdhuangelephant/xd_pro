package com.xiaodou.course.web.response.product;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.google.common.collect.Lists;
import com.xiaodou.course.vo.product.ForumCard;
import com.xiaodou.course.vo.product.StCourseInfo;
import com.xiaodou.course.web.response.BaseResponse;
import com.xiaodou.course.web.response.resultType.ProductResType;
import com.xiaodou.summer.vo.out.ResultType;
import com.xiaodou.ucerCenter.agent.response.resultype.UcenterResType;

public class HomeResponse extends BaseResponse {

  public HomeResponse() {}

  public HomeResponse(ResultType type) {
    super(type);
  }

  public HomeResponse(ProductResType resType) {
    super(resType);
  }

  public HomeResponse(UcenterResType resType) {
    setRetcode(resType.getCode());
    setRetdesc(resType.getMsg());
  }

  private ForumCard forumCard = new ForumCard();

  private List<StCourseInfo> recentExamCourseList = Lists.newArrayList();// 最近考期课程列表

  private List<StCourseInfo> otherExamCourseList = Lists.newArrayList();// 其它考期课程列表

  private String recentExamDate = StringUtils.EMPTY;// 最近考期

  private String countDownTime = Integer.toString(0);
  
  private String majorName = StringUtils.EMPTY;

  public String getMajorName() {
    return majorName;
  }

  public void setMajorName(String majorName) {
    this.majorName = majorName;
  }

  public String getCountDownTime() {
    return countDownTime;
  }

  public void setCountDownTime(String countDownTime) {
    this.countDownTime = countDownTime;
  }

  public ForumCard getForumCard() {
    return forumCard;
  }

  public void setForumCard(ForumCard forumCard) {
    this.forumCard = forumCard;
  }

  public List<StCourseInfo> getRecentExamCourseList() {
    return recentExamCourseList;
  }

  public void setRecentExamCourseList(List<StCourseInfo> recentExamCourseList) {
    this.recentExamCourseList = recentExamCourseList;
  }

  public String getRecentExamCourseCount() {
    return (null != recentExamCourseList) ? String.valueOf(recentExamCourseList.size()) : Integer
        .toString(0);
  }

  public List<StCourseInfo> getOtherExamCourseList() {
    return otherExamCourseList;
  }

  public void setOtherExamCourseList(List<StCourseInfo> otherExamCourseList) {
    this.otherExamCourseList = otherExamCourseList;
  }

  public String getOtherExamCourseCount() {
    return (null != otherExamCourseList) ? String.valueOf(otherExamCourseList.size()) : Integer
        .toString(0);
  }

  public String getRecentExamDate() {
    return recentExamDate;
  }

  public void setRecentExamDate(String recentExamDate) {
    this.recentExamDate = recentExamDate;
  }
}
