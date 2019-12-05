package com.xiaodou.ucerCenter.agent.response;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.google.common.collect.Lists;
import com.xiaodou.summer.vo.out.ResultType;
import com.xiaodou.ucerCenter.agent.model.vo.StCourseInfo;
import com.xiaodou.ucerCenter.agent.response.UserInfoResponse.UserInfo;
import com.xiaodou.ucerCenter.agent.response.resultype.UcenterResType;

public class HomeResponse extends BaseResultInfo {

  public HomeResponse() {}

  public HomeResponse(ResultType type) {
    super(type);
  }

  public HomeResponse(UcenterResType resType) {
    super(resType);
  }

  private UserInfo userInfo = new UserInfo();

  private List<StCourseInfo> recentExamCourseList = Lists.newArrayList();// 最近考期课程列表

  private List<StCourseInfo> otherExamCourseList = Lists.newArrayList();// 其它考期课程列表

  private String recentExamDate = StringUtils.EMPTY;// 最近考期

  public UserInfo getUserInfo() {
    return userInfo;
  }

  public void setUserInfo(BaseUserModel userInfo) {
    this.userInfo = (null != userInfo) ? (UserInfo) userInfo : new UserInfo();
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
