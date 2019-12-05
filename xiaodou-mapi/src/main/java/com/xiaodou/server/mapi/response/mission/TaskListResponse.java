package com.xiaodou.server.mapi.response.mission;

import java.util.List;

import com.google.common.collect.Lists;
import com.xiaodou.server.mapi.response.selftaught.BaseResponse;
import com.xiaodou.summer.vo.out.ResultType;

public class TaskListResponse extends BaseResponse {
  public TaskListResponse() {}

  public TaskListResponse(ResultType type) {
    super(type);
  }

  /** todayFinishMissionStatus 今日完成任务状态 0:未完成 1:已完成 */
  private String todayFinishMissionStatus;
  /** todayReachedMissionCount 今日完成必做任务数量 */
  private String todayReachedMissionCount;
  /** todayTotalMissionCount 今日必做任务总数量 */
  private String todayTotalMissionCount;
  /** reachedMissionCount 完成任务数量 */
  private String reachedMissionCount;
  /** totalMissionCount 课程任务总数 */
  private String totalMissionCount;
  /** learnTotalDays 本次考期已过天数 */
  private String examPassDays;
  /** examIntervalsDays 考试间隔天数 */
  private String examIntervalsDays;
  /** dateList 任务日期列表 */
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

  public String getExamPassDays() {
    return examPassDays;
  }

  public void setExamPassDays(String examPassDays) {
    this.examPassDays = examPassDays;
  }

  public String getExamIntervalsDays() {
    return examIntervalsDays;
  }

  public void setExamIntervalsDays(String examIntervalsDays) {
    this.examIntervalsDays = examIntervalsDays;
  }

  public List<DateTask> getDateList() {
    return dateList;
  }

  public void setDateList(List<DateTask> dateList) {
    this.dateList = dateList;
  }

  public static class DateTask {
    private String date;
    private String showDate;
    private List<CourseTask> courseList = Lists.newArrayList();

    public String getDate() {
      return date;
    }

    public void setDate(String date) {
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
    /** taskDesc 任务描述 */
    private String taskDesc;
    /** status 任务状态 */
    private String status;// -1 进行中 0 待领取奖励 1 已完成
    /** creditUpper 积分涨幅 */
    private String creditUpper;
    /** taskType 任务类型 */
    private String taskType;
    /** jumpType 跳转类型 */
    private String jumpType;
    /** courseId 课程ID */
    private String courseId;
    /** chapterId 章ID */
    private String chapterId;
    /** itemId 节ID */
    private String itemId;

    public String getTaskId() {
      return taskId;
    }

    public void setTaskId(String taskId) {
      this.taskId = taskId;
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

    public String getTaskType() {
      return taskType;
    }

    public void setTaskType(String taskType) {
      this.taskType = taskType;
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

    public String getCourseId() {
      return courseId;
    }

    public void setCourseId(String courseId) {
      this.courseId = courseId;
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

}
