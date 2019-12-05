package com.xiaodou.server.mapi.response.ucenter;

import com.xiaodou.common.util.StringUtils;
import com.xiaodou.server.mapi.response.selftaught.BaseResponse;
import com.xiaodou.server.mapi.response.ucenter.resultype.UcenterResType;
import com.xiaodou.summer.vo.out.ResultType;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class LoginResponse extends BaseResponse {

	public LoginResponse() {
	}

	public LoginResponse(UcenterResType type) {
		super(type);
	}

	public LoginResponse(ResultType type) {
		super(type);
	}

	/** user 用户模型 */
	private UserModelResponse user;
	/**
	 * 用户token值
	 */
	private String token = StringUtils.EMPTY;

	public UserModelResponse getUser() {
		return user;
	}

	public void setUser(UserModelResponse user) {
		this.user = user;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

}
