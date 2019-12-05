package com.xiaodou.dashboard.vo.alarm.response;

import com.xiaodou.dashboard.model.alarm.domain.AlarmPolicyDo;
import com.xiaodou.summer.vo.out.ResultInfo;
import com.xiaodou.summer.vo.out.ResultType;

/**
 * @name @see com.xiaodou.dashboard.vo.alarm.response.PolicyResponse.java
 * @CopyRright (c) 2017 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年6月9日
 * @description 报警规则响应类
 * @version 1.0
 */
public class PolicyResponse extends ResultInfo {

  public PolicyResponse() {}

  public PolicyResponse(ResultType type) {
    super(type);
  }

  private AlarmPolicyDo policy;

  public AlarmPolicyDo getPolicy() {
    return policy;
  }

  public void setPolicy(AlarmPolicyDo policy) {
    this.policy = policy;
  }

}
