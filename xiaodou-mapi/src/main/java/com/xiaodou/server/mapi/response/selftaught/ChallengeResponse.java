package com.xiaodou.server.mapi.response.selftaught;

import java.util.Map;

import com.xiaodou.server.mapi.response.quesbk.ChallengeInfo;
import com.xiaodou.summer.vo.out.ResultType;

public class ChallengeResponse extends BaseResponse {

  public ChallengeResponse(ResultType type) {
    super(type);
  }

  public void setChallengeInfo(ChallengeInfo challengeInfo) {
    this.recordId = challengeInfo.getRecordId();
  }

  public void setTargetUserInfo(Map<String, Object> targetUserInfo) {
    this.targetUserInfo = targetUserInfo;
  }

  /** recordId 记录ID */
  private String recordId;
  private Map<String, Object> targetUserInfo;

  public String getRecordId() {
    return recordId;
  }

  public void setRecordId(String recordId) {
    this.recordId = recordId;
  }

  public Map<String, Object> getTargetUserInfo() {
    return targetUserInfo;
  }

}
