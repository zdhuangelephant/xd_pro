package com.xiaodou.userCenter.response;

import com.xiaodou.summer.vo.out.ResultType;
import com.xiaodou.userCenter.model.BlankNoticeModel;
import com.xiaodou.userCenter.response.resultype.UcenterResType;

public class BlankNoticeResponse extends BaseResultInfo {
	public BlankNoticeResponse(UcenterResType type) {
		super(type);
	}

	public BlankNoticeResponse(ResultType type) {
		super(type);
	}

	// 展示方式：0 每次都展示，1 只展示一次，2 不展示
	private Short type;
	// 展示地址 http://
	private String showUrl;
	// 跳转地址 http:// app://
	private String jumpUrl;

	public BlankNoticeResponse setBlankNoticeResponse(BlankNoticeModel model){
		BlankNoticeResponse response = new BlankNoticeResponse(ResultType.SUCCESS);
		response.setJumpUrl(model.getJumpUrl());
		response.setType(model.getType());
		response.setShowUrl(model.getShowUrl());
		return response;
	}
	
	public Short getType() {
		return type;
	}

	public void setType(Short type) {
		this.type = type;
	}

	public String getShowUrl() {
		return showUrl;
	}

	public void setShowUrl(String showUrl) {
		this.showUrl = showUrl;
	}

	public String getJumpUrl() {
		return jumpUrl;
	}

	public void setJumpUrl(String jumpUrl) {
		this.jumpUrl = jumpUrl;
	}
}
