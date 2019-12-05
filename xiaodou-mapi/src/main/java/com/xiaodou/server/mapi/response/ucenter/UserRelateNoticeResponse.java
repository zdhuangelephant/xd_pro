package com.xiaodou.server.mapi.response.ucenter;

import java.util.List;

import com.google.common.collect.Lists;
import com.xiaodou.server.mapi.response.selftaught.BaseResponse;
import com.xiaodou.server.mapi.response.ucenter.resultype.UcenterResType;
import com.xiaodou.server.mapi.vo.ucenter.model.UserRelateNoticeModel;
import com.xiaodou.summer.vo.out.ResultType;

public class UserRelateNoticeResponse extends BaseResponse {

  public UserRelateNoticeResponse() {}

  public UserRelateNoticeResponse(UcenterResType type) {
    super(type);
  }

  public UserRelateNoticeResponse(ResultType type) {
    super(type);
  }

  private List<UserRelateNoticeModel> noticeList = Lists.newArrayList();

  public List<UserRelateNoticeModel> getNoticeList() {
    return noticeList;
  }

  public void setNoticeList(List<UserRelateNoticeModel> noticeList) {
    this.noticeList = noticeList;
  }

}
