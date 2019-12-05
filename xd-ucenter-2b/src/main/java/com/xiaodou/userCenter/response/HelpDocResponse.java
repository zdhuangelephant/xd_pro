package com.xiaodou.userCenter.response;

import java.util.List;

import com.google.common.collect.Lists;
import com.xiaodou.summer.vo.out.ResultType;
import com.xiaodou.userCenter.model.HelpDocModel;
import com.xiaodou.userCenter.response.resultype.UcenterResType;

public class HelpDocResponse extends BaseResultInfo {
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
