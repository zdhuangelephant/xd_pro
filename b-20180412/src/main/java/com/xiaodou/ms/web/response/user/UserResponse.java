package com.xiaodou.ms.web.response.user;

import com.xiaodou.ms.model.user.UserModel;
import com.xiaodou.ms.web.response.BaseResponse;
import com.xiaodou.ms.web.response.ResultType;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class UserResponse extends BaseResponse {
	public UserResponse(ResultType resultType) {
		super(resultType);
	}

	public UserResponse() {
	}
	private UserModel userModel;
}
