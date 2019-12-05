package com.xiaodou.userCenter.module.tk.request;

import java.util.Map;

import com.xiaodou.userCenter.module.jz.request.JzUserInfoModify;
import com.xiaodou.userCenter.request.ModifyMyInfoRequest;
import com.xiaodou.userCenter.request.UserBaseInfo;

public class TkUserInfoModify extends ModifyMyInfoRequest{
	
	public TkUserInfoModify(){
		super(null);
	}

	public TkUserInfoModify(UserBaseInfo info) {
		super(info);
	}


	@Override
	public String getMoudelInfo() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String setMoudelInfo(Map<String, Object> moudelInfoMap) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public ModifyMyInfoRequest getModifyAccountInfo() {
		// 通过构造方法设置基础属性
	    JzUserInfoModify info = new JzUserInfoModify(this);
	    // 模块独有属性需要单独设置
	    return info;
	}
}
