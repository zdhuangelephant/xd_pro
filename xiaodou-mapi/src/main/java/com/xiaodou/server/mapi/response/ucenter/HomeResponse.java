package com.xiaodou.server.mapi.response.ucenter;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.google.common.collect.Lists;
import com.xiaodou.server.mapi.response.selftaught.BaseResponse;
import com.xiaodou.server.mapi.response.selftaught.HomeResponse.StCourseInfo;
import com.xiaodou.server.mapi.response.ucenter.resultype.UcenterResType;
import com.xiaodou.summer.vo.out.ResultType;

public class HomeResponse extends BaseResponse {
  
  public HomeResponse(){}

  public HomeResponse(ResultType type) {
    super(type);
  }

  public HomeResponse(UcenterResType resType) {
    super(resType);
  }

  private List<StCourseInfo> recentExamCourseList = Lists.newArrayList();// 最近考期课程列表

  private List<StCourseInfo> otherExamCourseList = Lists.newArrayList();// 其它考期课程列表

  private String recentExamDate = StringUtils.EMPTY;// 最近考期

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
