package com.xiaodou.server.mapi.response.ucenter;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.google.common.collect.Lists;
import com.xiaodou.server.mapi.response.selftaught.BaseResponse;
import com.xiaodou.server.mapi.response.selftaught.HomeResponse.StCourseInfo;
import com.xiaodou.server.mapi.response.ucenter.resultype.UcenterResType;
import com.xiaodou.summer.vo.out.ResultType;

public class PersonInfoResponse extends BaseResponse {

  public PersonInfoResponse() {}

  public PersonInfoResponse(ResultType type) {
    super(type);
  }

  public PersonInfoResponse(UcenterResType type) {
    super(type);
  }

  private String portrait = StringUtils.EMPTY;
  private String gender = StringUtils.EMPTY;
  private String nickName = StringUtils.EMPTY;
  private String sign = StringUtils.EMPTY;
  private String coin = String.valueOf(0);
  /* credit 积分 */
  private String credit = String.valueOf(0);
  /* learnTime 学习时长 */
  private String learnTime = String.valueOf(0);
  /* learnDays 连续学习天数 */
  private String learnDays = String.valueOf(0);
  /* learnDays 连续登錄天数 */
  private String loginDays = String.valueOf(0);
  /* userType 用户类型 */
  private String userType = StringUtils.EMPTY;
  /** noticeCount 未读通知数量 */
  private String noticeCount = Integer.toString(0);
  /** sysMesCount 未读系统消息数量 */
  private String sysMesCount = Integer.toString(0);
  /* 无效课程数目 */
  private String otherExamCourseCount = Integer.toString(0);

  private String audioCount = Integer.toString(0);

  public String getOtherExamCourseCount() {
    return otherExamCourseCount;
  }

  public void setOtherExamCourseCount(String otherExamCourseCount) {
    this.otherExamCourseCount = otherExamCourseCount;
  }

  public String getUserType() {
    return userType;
  }

  public void setUserType(String userType) {
    this.userType = userType;
  }

  private List<StCourseInfo> courseList = Lists.newArrayList();// 用户课程列表

  public String getLoginDays() {
    return loginDays;
  }

  public void setLoginDays(String loginDays) {
    this.loginDays = loginDays;
  }

  public String getLearnDays() {
    return learnDays;
  }

  public void setLearnDays(String learnDays) {
    this.learnDays = learnDays;
  }

  public String getPortrait() {
    return portrait;
  }

  public void setPortrait(String portrait) {
    this.portrait = portrait;
  }

  public String getGender() {
    return gender;
  }

  public void setGender(String gender) {
    this.gender = gender;
  }

  public String getNickName() {
    return nickName;
  }

  public void setNickName(String nickName) {
    this.nickName = nickName;
  }

  public String getSign() {
    return sign;
  }

  public void setSign(String sign) {
    this.sign = sign;
  }

  public String getLearnTime() {
    return learnTime;
  }

  public void setLearnTime(String learnTime) {
    this.learnTime = learnTime;
  }

  public String getCredit() {
    return credit;
  }

  public void setCredit(String credit) {
    this.credit = credit;
  }

  public String getCoin() {
    return coin;
  }

  public void setCoin(String coin) {
    this.coin = coin;
  }

  public List<StCourseInfo> getCourseList() {
    return courseList;
  }

  public void setCourseList(List<StCourseInfo> courseList) {
    this.courseList = courseList;
  }

  public String getNoticeCount() {
    return noticeCount;
  }


  public void setNoticeCount(String noticeCount) {
    this.noticeCount = noticeCount;
  }

  public void setPersonInfo(UserModelResponse baseUserModel) {
    this.credit = baseUserModel.getCredit();
    this.gender = baseUserModel.getGender();
    this.nickName = baseUserModel.getNickName();
    this.portrait = baseUserModel.getPortrait();
    this.sign = baseUserModel.getSign();
    if (null != baseUserModel.getLoginDays())
      this.loginDays = baseUserModel.getLoginDays().toString();
    this.userType = baseUserModel.getUserType();
  }

  public void setNoticeCount(Integer noticeCount) {
    if (null != noticeCount) this.noticeCount = noticeCount.toString();
  }

  public String getSysMesCount() {
    return sysMesCount;
  }

  public void setSysMesCount(String sysMesCount) {
    this.sysMesCount = sysMesCount;
  }

  public void setSysMesCount(Integer sysMesCount) {
    if (null != sysMesCount) this.sysMesCount = sysMesCount.toString();
  }

  public String getAudioCount() {
    return audioCount;
  }

  public void setAudioCount(String audioCount) {
    this.audioCount = audioCount;
  }

}
