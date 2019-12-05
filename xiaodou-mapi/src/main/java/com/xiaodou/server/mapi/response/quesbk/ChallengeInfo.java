package com.xiaodou.server.mapi.response.quesbk;

import com.xiaodou.server.mapi.response.selftaught.BaseResponse;
import com.xiaodou.summer.vo.out.ResultType;

/**
 * @name @see com.xiaodou.server.mapi.response.ChallengeResponse.java
 * @CopyRright (c) 2016 by <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2016年3月28日
 * @description 挑战信息
 * @version 1.0
 */
public class ChallengeInfo extends BaseResponse {
  public ChallengeInfo() {}

  public ChallengeInfo(ResultType type) {
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
