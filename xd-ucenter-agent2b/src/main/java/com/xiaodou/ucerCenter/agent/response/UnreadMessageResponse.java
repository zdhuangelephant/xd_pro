package com.xiaodou.ucerCenter.agent.response;

import com.xiaodou.summer.vo.out.ResultType;
import com.xiaodou.ucerCenter.agent.response.resultype.UcenterResType;

public class UnreadMessageResponse extends BaseResultInfo {

  public UnreadMessageResponse() {}

  public UnreadMessageResponse(UcenterResType type) {
    super(type);
  }

  public UnreadMessageResponse(ResultType type) {
    super(type);
  }

  private Integer noticeCount = 0;
  private Integer sysMesCount = 0;
  private Integer userMesCount = 0;

  public Integer getNoticeCount() {
    return noticeCount;
  }

  public void setNoticeCount(Integer noticeCount) {
    this.noticeCount = noticeCount;
  }

  public Integer getSysMesCount() {
    return sysMesCount;
  }

  public void setSysMesCount(Integer sysMesCount) {
    this.sysMesCount = sysMesCount;
  }

  public Integer getUserMesCount() {
    return userMesCount;
  }

  public void setUserMesCount(Integer userMesCount) {
    this.userMesCount = userMesCount;
  }


}
