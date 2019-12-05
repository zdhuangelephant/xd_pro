package com.xiaodou.server.mapi.response.quesbk;

import java.util.List;

import com.google.common.collect.Lists;
import com.xiaodou.server.mapi.response.quesbk.WrongQuesListResponse.ChapterInfo;
import com.xiaodou.server.mapi.response.selftaught.BaseResponse;
import com.xiaodou.summer.vo.out.ResultType;

public class StoreQuesListResponse extends BaseResponse {

  public StoreQuesListResponse() {}

  public StoreQuesListResponse(ResultType type) {
    super(type);
  }

  /**
   * 章列表
   */
  private List<ChapterInfo> courseItemList = Lists.newArrayList();

  public List<ChapterInfo> getCourseItemList() {
    return courseItemList;
  }

  public void setCourseItemList(List<ChapterInfo> courseItemList) {
    this.courseItemList = courseItemList;
  }
}
