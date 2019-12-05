package com.xiaodou.ucerCenter.agent.response;

import java.util.List;

import com.google.common.collect.Lists;
import com.xiaodou.summer.vo.out.ResultType;
import com.xiaodou.ucerCenter.agent.model.HelpDocModel;
import com.xiaodou.ucerCenter.agent.response.resultype.UcenterResType;

public class HelpDocResponse extends BaseResultInfo {

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
