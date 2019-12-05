package com.xiaodou.userCenter.response;

import com.xiaodou.common.util.StringUtils;
import com.xiaodou.summer.vo.out.ResultType;
import com.xiaodou.userCenter.model.UserRelateNoticeModel;
import com.xiaodou.userCenter.response.resultype.UcenterResType;

public class NoticeResponse extends BaseResultInfo {

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
