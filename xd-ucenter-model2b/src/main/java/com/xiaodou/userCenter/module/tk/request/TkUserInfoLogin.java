package com.xiaodou.userCenter.module.tk.request;

import com.xiaodou.userCenter.request.NewLoginRequest;
import com.xiaodou.userCenter.request.UserBaseInfo;

public class TkUserInfoLogin extends NewLoginRequest{
	
	public TkUserInfoLogin(){
		super(null);
	}
	
	public TkUserInfoLogin(UserBaseInfo info) {
		super(info);
	}
 
	
	@Override
	public String getMoudelInfo() {
		return null;
	}

}
