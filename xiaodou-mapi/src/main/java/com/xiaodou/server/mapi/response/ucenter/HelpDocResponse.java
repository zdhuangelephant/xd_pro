package com.xiaodou.server.mapi.response.ucenter;

import java.util.List;

import com.google.common.collect.Lists;
import com.xiaodou.server.mapi.response.selftaught.BaseResponse;
import com.xiaodou.server.mapi.response.ucenter.resultype.UcenterResType;
import com.xiaodou.server.mapi.vo.ucenter.model.HelpDocModel;
import com.xiaodou.summer.vo.out.ResultType;

public class HelpDocResponse extends BaseResponse {

  public HelpDocResponse() {}

  public HelpDocResponse(ResultType type) {
    super(type);
  }

  public HelpDocResponse(UcenterResType type) {
    super(type);
  }

  private List<HelpDocModel> helpList = Lists.newArrayList();

  public List<HelpDocModel> getHelpList() {
    return helpList;
  }

  public void setHelpList(List<HelpDocModel> helpList) {
    this.helpList = helpList;
  }

}
