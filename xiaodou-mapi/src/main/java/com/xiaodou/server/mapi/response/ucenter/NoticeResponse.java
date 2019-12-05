package com.xiaodou.server.mapi.response.ucenter;

import com.xiaodou.common.util.StringUtils;
import com.xiaodou.server.mapi.response.selftaught.BaseResponse;
import com.xiaodou.server.mapi.response.ucenter.resultype.UcenterResType;
import com.xiaodou.server.mapi.vo.ucenter.model.UserRelateNoticeModel;
import com.xiaodou.summer.vo.out.ResultType;

public class NoticeResponse extends BaseResponse {

  public NoticeResponse() {
    super();
  }

  public NoticeResponse(UcenterResType type) {
    super(type);
  }

  public NoticeResponse(ResultType type) {
    super(type);
  }

  private UserRelateNoticeModel userRelateNoticeModel;

  private String userId = StringUtils.EMPTY;

  private String noticeId = StringUtils.EMPTY;

  public UserRelateNoticeModel getUserRelateNoticeModel() {
    return userRelateNoticeModel;
  }

  public void setUserRelateNoticeModel(UserRelateNoticeModel userRelateNoticeModel) {
    this.userRelateNoticeModel = userRelateNoticeModel;
  }

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public String getNoticeId() {
    return noticeId;
  }

  public void setNoticeId(String noticeId) {
    this.noticeId = noticeId;
  }

}
