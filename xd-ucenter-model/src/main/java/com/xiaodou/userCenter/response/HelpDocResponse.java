package com.xiaodou.userCenter.response;

import java.util.List;

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

	private List<HelpDocModel> helpList;

	public List<HelpDocModel> getHelpList() {
		return helpList;
	}

	public void setHelpList(List<HelpDocModel> helpList) {
		this.helpList = helpList;
	}

}
