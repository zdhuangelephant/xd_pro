package com.xiaodou.ms.web.response.robot;

import com.xiaodou.ms.model.robot.ChallengeRobotModel;
import com.xiaodou.ms.web.response.BaseResponse;
import com.xiaodou.ms.web.response.ResultType;

import lombok.Data;
import lombok.EqualsAndHashCode;
@Data
@EqualsAndHashCode(callSuper = true)
public class ChallengeRobotResponse extends BaseResponse {
	public ChallengeRobotResponse(ResultType resultType) {
		super(resultType);
	}

	public ChallengeRobotResponse() {
	}
	private ChallengeRobotModel challengeRobotModel;
}
