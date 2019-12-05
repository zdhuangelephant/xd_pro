package com.xiaodou.userCenter.request.selftaught;

import com.xiaodou.summer.validator.annotion.NotEmpty;
import com.xiaodou.userCenter.request.UserBaseInfo;

public abstract class ModifyStMyInfoRequest extends
		com.xiaodou.userCenter.request.ModifyMyInfoRequest {
	public ModifyStMyInfoRequest(UserBaseInfo info) {
		super(info);
	}

	@NotEmpty
	private String type;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}