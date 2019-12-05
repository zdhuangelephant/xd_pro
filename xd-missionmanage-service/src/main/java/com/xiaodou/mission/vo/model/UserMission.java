package com.xiaodou.mission.vo.model;

import com.xiaodou.mission.constants.MissionConstant;
import com.xiaodou.mission.domain.MissionModel;

/**
 * @name @see com.xiaodou.mission.vo.model.UserMission.java
 * @CopyRright (c) 2017 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年12月26日
 * @description 用户任务请求类
 * @version 1.0
 */
public class UserMission {

  private MissionModel mission;
  private Integer status = MissionConstant.TASK_STATUS_UNREACH;

  public Integer getStatus() {
    return status;
  }

  public void setStatus(Integer status) {
    this.status = status;
  }

  public UserMission() {}

  public UserMission(MissionModel mission) {
    this.mission = mission;
  }

  public MissionModel getMission() {
    return mission;
  }

  public void setMission(MissionModel mission) {
    this.mission = mission;
  }

}
