package com.xiaodou.mission.vo.model;

import java.sql.Timestamp;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.xiaodou.common.util.DateUtil;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.mission.constants.MissionConstant;
import com.xiaodou.mission.engine.MissionEnums.TaskType;
import com.xiaodou.mission.vo.response.TaskListResponse.CourseTask;
import com.xiaodou.mission.vo.response.TaskListResponse.DateTask;
import com.xiaodou.mission.vo.response.TaskListResponse.Task;

/**
 * @name @see com.xiaodou.mission.vo.model.DateTaskHolder.java
 * @CopyRright (c) 2017 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年12月26日
 * @description 日期-任务包装器
 * @version 1.0
 */
public class DateTaskHolder {

  private Map<String, CourseTaskHolder> courseTaskMap = Maps.newConcurrentMap();

  public CourseTaskHolder getCourseTask(Timestamp date) {
    String key = DateUtil.SDF_YMD.format(date);
    CourseTaskHolder holder = courseTaskMap.get(key);
    if (null == holder) {
      holder = new CourseTaskHolder();
      holder.date = new Timestamp(DateUtil.getStartTimeOfDay(date));
      courseTaskMap.put(key, holder);
    }
    return holder;
  }

  public void pushTask(Timestamp date, Task task) {
    if (StringUtils.isBlank(task.getCourseId())) {
      return;
    }
    getCourseTask(date).pushTask(task);
  }

  public void initTodayTask() {
    getCourseTask(new Timestamp(System.currentTimeMillis())).pushTask(null);
  }

  public List<DateTask> getDateTaskList() {
    List<DateTask> dateTaskList = Lists.newArrayList();
    for (Entry<String, CourseTaskHolder> entry : courseTaskMap.entrySet()) {
      DateTask dateTask = new DateTask();
      dateTask.setDate(entry.getValue().getDate());
      dateTask.setShowDate(entry.getKey());
      dateTask.setCourseList(entry.getValue().getCourseTask());
      dateTaskList.add(dateTask);
    }
    Collections.sort(dateTaskList, new Comparator<DateTask>() {
      @Override
      public int compare(DateTask o1, DateTask o2) {
        if (null == o1.getDate()) {
          return 1;
        }
        if (null == o2.getDate()) {
          return -1;
        }
        return o2.getDate().compareTo(o1.getDate());
      }
    });
    return dateTaskList;
  }

  public static class CourseTaskHolder {

    private Timestamp date;
    private LinkedHashMap<String, TaskHolder> taskMap = Maps.newLinkedHashMap();

    public Timestamp getDate() {
      return date;
    }

    public Map<String, TaskHolder> getTaskMap() {
      return taskMap;
    }

    public TaskHolder getTaskHolder(String courseId, String courseName) {
      TaskHolder holder = taskMap.get(courseId);
      if (null == holder) {
        holder = new TaskHolder();
        holder.courseName = courseName;
        taskMap.put(courseId, holder);
      }
      return holder;
    }

    public void pushTask(Task task) {
      if (null == task || StringUtils.isBlank(task.getCourseId())) {
        return;
      }
      if (TaskType.statics.equals(task.getTaskType())) {
        getTaskHolder(MissionConstant.COMMON_COURSE_ID, MissionConstant.COMMON_COURSE_NAME)
            .pushTask(task);
      } else {
        getTaskHolder(task.getCourseId(), task.getCourseName()).pushTask(task);
      }
    }

    private List<CourseTask> getCourseTask() {
      List<CourseTask> courseTaskList = Lists.newArrayList();
      for (Entry<String, TaskHolder> entry : taskMap.entrySet()) {
        CourseTask courseTask = new CourseTask();
        courseTask.setCourseId(entry.getKey());
        courseTask.setCourseName(entry.getValue().getCourseName());
        courseTask.setTotalTaskCount(entry.getValue().getTotalCount().toString());
        courseTask.setCompleteTaskCount(entry.getValue().getCompleteCount().toString());
        courseTask.setTaskList(entry.getValue().taskList);
        courseTaskList.add(courseTask);
      }
      Collections.sort(courseTaskList, new Comparator<CourseTask>() {
        @Override
        public int compare(CourseTask o1, CourseTask o2) {
          return o2.getCourseId().compareTo(o1.getCourseId());
        }
      });
      return courseTaskList;
    }

  }

  public static class TaskHolder {
    private String courseName;
    private Integer totalCount = 0;
    private Integer completeCount = 0;
    private Set<String> taskKeySet = Sets.newHashSet();
    private List<Task> taskList = Lists.newArrayList();

    public String getCourseName() {
      return courseName;
    }

    public Integer getTotalCount() {
      return totalCount;
    }

    public Integer getCompleteCount() {
      return completeCount;
    }

    public void pushTask(Task task) {
      if (null == task) {
        return;
      }
      String key =
          TaskType.dynamic.equals(task.getTaskType()) ? task.getRecordId() : task.getTaskId();
      if (taskKeySet.contains(key)) {
        return;
      }
      taskKeySet.add(key);
      totalCount++;
      if (task.isComplete()) {
        completeCount++;
      }
      taskList.add(task);
    }

  }
}
