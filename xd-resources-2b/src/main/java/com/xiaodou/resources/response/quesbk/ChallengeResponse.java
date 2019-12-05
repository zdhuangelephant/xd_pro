package com.xiaodou.resources.response.quesbk;

import com.xiaodou.resources.constant.quesbk.ResultType;
import com.xiaodou.resources.response.BaseResponse;

/**
 * @name @see com.xiaodou.vo.response.ChallengeResponse.java
 * @CopyRright (c) 2016 by XiaoDou NetWork Technology
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2016年2月26日
 * @description 挑战接口响应
 * @version 1.0
 */
public class ChallengeResponse extends BaseResponse {

  public ChallengeResponse(ResultType type) {
    super(type);
  }

  /** recordId 记录ID */
  private String recordId;
  /** targetUserId 挑战目标用户ID */
  private String targetUserId;

  public String getRecordId() {
    return recordId;
  }

  public void setRecordId(String recordId) {
    this.recordId = recordId;
  }

  public String getTargetUserId() {
    return targetUserId;
  }

  public void setTargetUserId(String targetUserId) {
    this.targetUserId = targetUserId;
  }

}
