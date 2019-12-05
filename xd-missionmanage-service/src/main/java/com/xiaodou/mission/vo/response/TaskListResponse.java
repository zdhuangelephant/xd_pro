package com.xiaodou.mission.vo.response;

import java.sql.Timestamp;
import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;
import com.google.common.collect.Lists;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.mission.constants.MissionConstant;
import com.xiaodou.mission.constants.MissionResultType;
import com.xiaodou.mission.engine.MissionEnums.TaskType;
import com.xiaodou.mission.engine.model.mission.AbstractMissionRecord;
import com.xiaodou.mission.vo.model.DateTaskHolder;
import com.xiaodou.summer.vo.out.ResultInfo;
import com.xiaodou.summer.vo.out.ResultType;

/**
 * @name @see com.xiaodou.mission.vo.response.MedalListResponse.java
 * @CopyRright (c) 2016 by <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2016年4月4日
 * @description 任务列表响应类
 * @version 1.0
 */
public class TaskListResponse extends ResultInfo {

  public TaskListResponse() {}

  public TaskListResponse(ResultType type) {
    super(type);
  }

  public TaskListResponse(MissionResultType type) {
    setRetcode(type.getCode());
    setRetdesc(type.getMsg());
    setServerIp(type.getServerIp());
  }

  /** todayFinishMissionStatus 今日完成任务状态 0:未完成 1:已完成 */
  private String todayFinishMissionStatus = MissionConstant.FALSE.toString();
  /** todayReachedMissionCount 今日完成必做任务数量 */
  private String todayReachedMissionCount;
  /** todayTotalMissionCount 今日必做任务总数量 */
  private String todayTotalMissionCount;
  /** reachedMissionCount 完成任务数量 */
  private String reachedMissionCount;
  /** totalMissionCount 课程任务总数 */
  private String totalMissionCount;

  private List<DateTask> dateList = Lists.newArrayList();

  public String getTodayFinishMissionStatus() {
    return todayFinishMissionStatus;
  }

  public void setTodayFinishMissionStatus(String todayFinishMissionStatus) {
    this.todayFinishMissionStatus = todayFinishMissionStatus;
  }

  public String getTodayReachedMissionCount() {
    return todayReachedMissionCount;
  }

  public void setTodayReachedMissionCount(String todayReachedMissionCount) {
    this.todayReachedMissionCount = todayReachedMissionCount;
  }

  public String getTodayTotalMissionCount() {
    return todayTotalMissionCount;
  }

  public void setTodayTotalMissionCount(String todayTotalMissionCount) {
    this.todayTotalMissionCount = todayTotalMissionCount;
  }

  public String getReachedMissionCount() {
    return reachedMissionCount;
  }

  public void setReachedMissionCount(String reachedMissionCount) {
    this.reachedMissionCount = reachedMissionCount;
  }

  public String getTotalMissionCount() {
    return totalMissionCount;
  }

  public void setTotalMissionCount(String totalMissionCount) {
    this.totalMissionCount = totalMissionCount;
  }

  public List<DateTask> getDateList() {
    return dateList;
  }

  public void setDateList(List<DateTask> dateList) {
    this.dateList = dateList;
  }

  public static class DateTask {
    private Timestamp date;
    private String showDate;
    private List<CourseTask> courseList = Lists.newArrayList();

    public Timestamp getDate() {
      return date;
    }

    public void setDate(Timestamp date) {
      this.date = date;
    }

    public String getShowDate() {
      return showDate;
    }

    public void setShowDate(String showDate) {
      this.showDate = showDate;
    }

    public List<CourseTask> getCourseList() {
      return courseList;
    }

    public void setCourseList(List<CourseTask> courseList) {
      this.courseList = courseList;
    }

  }

  public static class CourseTask {
    /** courseId 课程ID */
    private String courseId;
    /** courseName 课程名 */
    private String courseName;
    /** completeTaskCount 完成任务数量 */
    private String completeTaskCount;
    /** totalTaskCount 任务总数量 */
    private String totalTaskCount;
    /** taskList 任务列表 */
    private List<Task> taskList = Lists.newArrayList();

    public String getCourseId() {
      return courseId;
    }

    public void setCourseId(String courseId) {
      this.courseId = courseId;
    }

    public String getCourseName() {
      return courseName;
    }

    public void setCourseName(String courseName) {
      this.courseName = courseName;
    }

    public String getCompleteTaskCount() {
      return completeTaskCount;
    }

    public void setCompleteTaskCount(String completeTaskCount) {
      this.completeTaskCount = completeTaskCount;
    }

    public String getTotalTaskCount() {
      return totalTaskCount;
    }

    public void setTotalTaskCount(String totalTaskCount) {
      this.totalTaskCount = totalTaskCount;
    }

    public List<Task> getTaskList() {
      return taskList;
    }

    public void setTaskList(List<Task> taskList) {
      this.taskList = taskList;
    }

  }

  public static class Task {
    /** taskId 任务ID */
    private String taskId;
    /** recordId 记录ID */
    private String recordId;
    /** taskDesc 任务描述 */
    private String taskDesc;
    /** status 任务状态 : -1 进行中 0 待领取奖励 1 已完成 */
    private String status;
    /** creditUpper 积分涨幅 */
    private String creditUpper;
    /** jumpType 跳转类型 */
    private String jumpType;
    /** taskType 任务类型明细 */
    private TaskType taskType;
    /** courseId 课程ID */
    private String courseId;
    /** courseName 课程名 */
    @JSONField(serialize = false)
    private String courseName;
    /** chapterId 章ID */
    private String chapterId;
    /** itemId 节ID */
    private String itemId;

    public Task(String courseName, AbstractMissionRecord record) {
      if (StringUtils.isNotBlank(courseName)) {
        this.courseName = courseName;
      }
      if (null == record) {
        return;
      }
      this.taskId = record.getMissionId();
      if (null != record.getId()) {
        this.recordId = record.getId().toString();
      }
      this.taskDesc = record.getMissionDesc();
      if (null != record.getMission() && null != record.getMission().getCreditUpper()) {
        this.creditUpper = record.getMission().getCreditUpper().toString();
      }
      if (null != record.getIsReached()) {
        this.status = record.getIsReached().toString();
      }
      if (null != record.getMission() && null != record.getMission().getJumpType()) {
        this.jumpType = record.getMission().getJumpType().toString();
      }
      if (null != record.getCourseId()) {
        this.courseId = record.getCourseId();
      }
      if (null != record.getChapterId()) {
        this.chapterId = record.getChapterId();
      }
      if (null != record.getThresholdNum()) {
        this.itemId = record.getThresholdNum();
      }
      if (null != record.getMission() && null != record.getMission().getTaskType()) {
        this.taskType = record.getMission().getTaskType();
        if (TaskType.statics.equals(record.getMission().getTaskType())
            && StringUtils.isBlank(this.courseId)) {
          this.courseId = MissionConstant.COMMON_COURSE_ID;
        }
      }
    }

    public boolean isComplete() {
      return StringUtils.isNotBlank(this.status)
          && (MissionConstant.TASK_STATUS_REACHED.toString().equals(this.status) || MissionConstant.TASK_STATUS_RECEIVED
              .toString().equals(this.status));
    }

    public String getTaskId() {
      return taskId;
    }

    public void setTaskId(String taskId) {
      this.taskId = taskId;
    }

    public String getRecordId() {
      return recordId;
    }

    public void setRecordId(String recordId) {
      this.recordId = recordId;
    }

    public String getStatus() {
      return status;
    }

    public void setStatus(String status) {
      this.status = status;
    }

    public String getCreditUpper() {
      return creditUpper;
    }

    public void setCreditUpper(String creditUpper) {
      this.creditUpper = creditUpper;
    }

    public String getTaskDesc() {
      return taskDesc;
    }

    public void setTaskDesc(String taskDesc) {
      this.taskDesc = taskDesc;
    }

    public String getJumpType() {
      return jumpType;
    }

    public void setJumpType(String jumpType) {
      this.jumpType = jumpType;
    }

    public TaskType getTaskType() {
      return taskType;
    }

    public void setTaskType(TaskType taskType) {
      this.taskType = taskType;
    }

    public String getCourseId() {
      return courseId;
    }

    public void setCourseId(String courseId) {
      this.courseId = courseId;
    }

    public String getCourseName() {
      return courseName;
    }

    public void setCourseName(String courseName) {
      this.courseName = courseName;
    }

    public String getChapterId() {
      return chapterId;
    }

    public void setChapterId(String chapterId) {
      this.chapterId = chapterId;
    }

    public String getItemId() {
      return itemId;
    }

    public void setItemId(String itemId) {
      this.itemId = itemId;
    }

  }

  public void addAllTask(DateTaskHolder taskHolder) {
    if (null == taskHolder) {
      return;
    }
    this.dateList = taskHolder.getDateTaskList();
  }

}
