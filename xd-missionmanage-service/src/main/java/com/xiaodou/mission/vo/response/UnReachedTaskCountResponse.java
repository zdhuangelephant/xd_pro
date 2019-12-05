package com.xiaodou.mission.vo.response;

import com.xiaodou.mission.constants.MissionResultType;
import com.xiaodou.summer.vo.out.ResultInfo;
import com.xiaodou.summer.vo.out.ResultType;

/**
 * @name @see com.xiaodou.mission.vo.response.UnReachedTaskCountResponse.java
 * @CopyRright (c) 2016 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2016年7月13日
 * @description 获取未完成任务数量
 * @version 1.0
 */
public class UnReachedTaskCountResponse extends ResultInfo {

  public UnReachedTaskCountResponse() {}

  public UnReachedTaskCountResponse(ResultType type) {
    super(type);
  }

  public UnReachedTaskCountResponse(MissionResultType type) {
    setRetcode(type.getCode());
    setRetdesc(type.getMsg());
    setServerIp(type.getServerIp());
  }

  /** unReachedMissionCount 未完成任务数量 */
  private String unReachedMissionCount;

  public String getUnReachedMissionCount() {
    return unReachedMissionCount;
  }

  public void setUnReachedMissionCount(String unReachedMissionCount) {
    this.unReachedMissionCount = unReachedMissionCount;
  }

}
