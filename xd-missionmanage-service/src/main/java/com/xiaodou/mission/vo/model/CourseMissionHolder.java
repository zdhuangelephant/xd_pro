package com.xiaodou.mission.vo.model;

import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.mission.constants.MissionConstant;
import com.xiaodou.mission.domain.CascadeMissionRecordModel;
import com.xiaodou.mission.engine.MissionEnums;
import com.xiaodou.mission.engine.MissionEnums.TaskType;

/**
 * @name @see com.xiaodou.mission.vo.model.CourseMissionHolder.java
 * @CopyRright (c) 2017 by Corp.XiaodouTech
 *
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年12月26日
 * @description 课程-任务包装器
 * @version 1.0
 */
public class CourseMissionHolder {

  private Set<String> courseIdSet = Sets.newLinkedHashSet();

  private Map<String, MissionHolder> courseMissionMap = Maps.newConcurrentMap();

  public Set<String> getCourseIdSet() {
    return courseIdSet;
  }

  public Map<String, MissionHolder> getCourseMissionMap() {
    return courseMissionMap;
  }

  public MissionHolder getCourseMission(String courseId) {
    if (!courseIdSet.contains(courseId)) {
      courseIdSet.add(courseId);
    }
    MissionHolder holder = courseMissionMap.get(courseId);
    if (null == holder) {
      holder = new MissionHolder();
      holder.courseId = courseId;
      courseMissionMap.put(courseId, holder);
    }
    return holder;
  }

  public void pushMission(CascadeMissionRecordModel mission) {
    if (null == mission || StringUtils.isBlank(mission.getCourseId())) {
      return;
    }
    MissionHolder holder = getCourseMission(mission.getCourseId());
    switch (MissionEnums.getEnumValue(TaskType.class, mission.getTaskType())) {
      case standard:
        if (mission.currUnValid()) {
          return;
        }
        holder.addStandardMission(mission);
        break;
      case dynamic:
        holder.addDynamicMission(mission);
        break;
      case statics:
        if (mission.currUnValid()) {
          return;
        }
        holder = getCourseMission(MissionConstant.COMMON_COURSE_ID);
        holder.addStandardMission(mission);
        break;
      default:
        break;
    }
  }

  public class MissionHolder {
    private String courseId;
    private LinkedList<CascadeMissionRecordModel> standardMission = Lists.newLinkedList();
    private LinkedList<CascadeMissionRecordModel> dynamicMission = Lists.newLinkedList();

    public String getCourseId() {
      return courseId;
    }

    public LinkedList<CascadeMissionRecordModel> getStandardMission() {
      return standardMission;
    }

    public void addStandardMission(CascadeMissionRecordModel mission) {
      int index = 0;
      for (int i = 0; i < standardMission.size(); i++) {
        if (standardMission.get(i).getMissionOrder() > mission.getMissionOrder()) {
          index = i;
        }
      }
      standardMission.add(index, mission);
    }

    public LinkedList<CascadeMissionRecordModel> getDynamicMission() {
      return dynamicMission;
    }

    public void addDynamicMission(CascadeMissionRecordModel mission) {
      int index = 0;
      for (int i = 0; i < dynamicMission.size(); i++) {
        if (dynamicMission.get(i).getMissionOrder() > mission.getMissionOrder()) {
          index = i;
        }
      }
      dynamicMission.add(index, mission);
    }

  }

}
