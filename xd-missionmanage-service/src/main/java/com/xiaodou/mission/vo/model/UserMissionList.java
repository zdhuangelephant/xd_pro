package com.xiaodou.mission.vo.model;

import java.util.LinkedList;
import java.util.List;

import com.google.common.collect.Lists;
import com.xiaodou.mission.domain.CascadeMissionRecordModel;


/**
 * @name @see com.xiaodou.mission.vo.model.UserMissionList.java
 * @CopyRright (c) 2016 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2016年5月16日
 * @description 用户任务列表
 * @version 1.0
 */
public class UserMissionList {

  /** courseMissionList 课程任务列表 */
  private LinkedList<UserCourseMission> courseMissionList = Lists.newLinkedList();

  public LinkedList<UserCourseMission> getCourseMissionList() {
    return courseMissionList;
  }

  public void setCourseMissionList(LinkedList<UserCourseMission> courseMissionList) {
    this.courseMissionList = courseMissionList;
  }

  /**
   * @name @see com.xiaodou.mission.vo.model.UserMissionList.java
   * @CopyRright (c) 2016 by Corp.XiaodouTech
   * 
   * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
   * @date 2016年5月16日
   * @description 用户课程任务模型
   * @version 1.0
   */
  public static class UserCourseMission {
    /** courseId 课程ID */
    private String courseId;
    /** missionList 任务列表 */
    private List<CascadeMissionRecordModel> missionList = Lists.newArrayList();

    public String getCourseId() {
      return courseId;
    }

    public void setCourseId(String courseId) {
      this.courseId = courseId;
    }

    public List<CascadeMissionRecordModel> getMissionList() {
      return missionList;
    }

    public void setMissionList(List<CascadeMissionRecordModel> missionList) {
      this.missionList = missionList;
    }

  }

  public List<CascadeMissionRecordModel> getTaskList() {
    List<CascadeMissionRecordModel> taskList = Lists.newArrayList();
    for (UserCourseMission userCourseMission : courseMissionList) {
      taskList.addAll(userCourseMission.getMissionList());
    }
    return taskList;
  }
}
