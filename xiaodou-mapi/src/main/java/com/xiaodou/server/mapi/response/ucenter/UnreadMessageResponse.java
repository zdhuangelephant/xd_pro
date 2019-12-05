package com.xiaodou.server.mapi.response.ucenter;

import com.xiaodou.server.mapi.response.selftaught.BaseResponse;
import com.xiaodou.server.mapi.response.ucenter.resultype.UcenterResType;
import com.xiaodou.summer.vo.out.ResultType;

public class UnreadMessageResponse extends BaseResponse {

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
