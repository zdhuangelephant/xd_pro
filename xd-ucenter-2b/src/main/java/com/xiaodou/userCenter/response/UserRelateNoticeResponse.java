package com.xiaodou.userCenter.response;

import java.util.List;

import com.google.common.collect.Lists;
import com.xiaodou.summer.vo.out.ResultType;
import com.xiaodou.userCenter.model.UserRelateNoticeModel;
import com.xiaodou.userCenter.response.resultype.UcenterResType;

public class UserRelateNoticeResponse extends BaseResultInfo {
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
