package com.xiaodou.userCenter.response;

import com.xiaodou.summer.vo.out.ResultType;
import com.xiaodou.userCenter.response.resultype.UcenterResType;

public class UpTokenResponse extends BaseResultInfo {

	public UpTokenResponse(ResultType type){
		super(type);
	}
	
	private String upToken;

	public String getUpToken() {
		return upToken;
	}

	public void setUpToken(String upToken) {
		this.upToken = upToken;
	}
	
	public UpTokenResponse(UcenterResType type){
	    super(type);
	}
}
