package com.xiaodou.mooccrawler.holder;

import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.xiaodou.common.cache.redis.JedisUtil;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.mooccrawler.domain.TaskModel;
import com.xiaodou.mooccrawler.enums.CrawlerEnum.TaskType;
import com.xiaodou.mooccrawler.service.QueueService;
import com.xiaodou.summer.util.SpringWebContextHolder;

public class TaskHolder {

  private static Integer pageIndex = 0;
  private static Map<String, Task> taskHolder = Maps.newHashMap();
  private static List<String> courseInfoUnfinishHolder = Lists.newArrayList();
  private static List<String> resourceInfoUnfinishHolder = Lists.newArrayList();
  private static List<String> finishHolder = Lists.newArrayList();
  private static Map<String, TaskWrapper> taskWrapperMap = Maps.newHashMap();

  public synchronized static void pushTask(Task task) {
    pushTask(task, true);
  }

  public synchronized static void pushTask(Task task, boolean store) {
    if (taskHolder.containsKey(task.getCourseId())) {
      return;
    }
    taskHolder.put(task.getCourseId(), task);
    if (task.courseInfoFinish && task.resourceInfoFinish) {
      finishHolder.add(task.getCourseId());
    } else if (task.courseInfoFinish) {
      resourceInfoUnfinishHolder.add(task.getCourseId());
    } else {
      courseInfoUnfinishHolder.add(task.getCourseId());
    }
    if (!store) {
      return;
    }
    QueueService queueService = SpringWebContextHolder.getBean("queueService");
    if (null != queueService) {
      queueService.addTask(task);
    }
  }

  public synchronized static TaskWrapper getTask(String deviceId) {
    if (courseInfoUnfinishHolder.size() != 0) {
      String courseId = courseInfoUnfinishHolder.get(0);
      courseInfoUnfinishHolder.remove(courseId);
      TaskWrapper taskWrapper = new TaskWrapper(TaskType.CourseInfo.name(), courseId);
      taskWrapperMap.put(deviceId, taskWrapper);
      return taskWrapper;
    }
    if (resourceInfoUnfinishHolder.size() != 0) {
      String courseId = resourceInfoUnfinishHolder.get(0);
      resourceInfoUnfinishHolder.remove(courseId);
      TaskWrapper taskWrapper = new TaskWrapper(TaskType.CourseWareInfo.name(), courseId);
      taskWrapperMap.put(deviceId, taskWrapper);
      return taskWrapper;
    }
    return null;
  }

  public synchronized static void finishCourseInfo(String courseId) {
    courseInfoUnfinishHolder.remove(courseId);
    resourceInfoUnfinishHolder.add(courseId);
  }

  public synchronized static void finishResourceInfo(String courseId) {
    resourceInfoUnfinishHolder.remove(courseId);
    finishHolder.add(courseId);
  }

  public synchronized static void setPageIndex(Integer pageIndex) {
    if (pageIndex <= TaskHolder.pageIndex) {
      return;
    }
    TaskHolder.pageIndex = pageIndex;
    JedisUtil.addStringToJedis("xd:mooc:crawler:pageindex", pageIndex.toString(), 0);
  }

  public static Integer getPageIndex() {
    if (pageIndex == 0) {
      String sPageIndex = JedisUtil.getStringFromJedis("xd:mooc:crawler:pageindex");
      if (StringUtils.isNotBlank(sPageIndex)) {
        pageIndex = Integer.parseInt(sPageIndex);
      }
    }
    return pageIndex;
  }

  public static Integer taskCount() {
    return taskHolder.size();
  }

  public static class TaskWrapper {
    public TaskWrapper() {}

    public TaskWrapper(String type, String courseId) {
      this.type = type;
      this.courseId = courseId;
    }

    private String type;
    private String courseId;

    public String getType() {
      return type;
    }

    public void setType(String type) {
      this.type = type;
    }

    public String getCourseId() {
      return courseId;
    }

    public void setCourseId(String courseId) {
      this.courseId = courseId;
    }
  }

  public static class Task {
    public Task() {}

    public Task(String baseUrl) {
      String courseIdAndTid = baseUrl.replaceAll("/course/", "");
      if (courseIdAndTid.indexOf("?tid=") > 0) {
        String[] courseT = courseIdAndTid.split("\\?tid=");
        this.courseId = courseT[0];
        if (courseT.length > 1) {
          this.tid = courseT[1];
        }
      } else {
        this.courseId = courseIdAndTid;
      }
      this.baseUrl = baseUrl.split("\\?tid=")[0];
    }

    public Task(TaskModel model) {
      this.courseId = model.getCourseId();
      this.courseName = model.getCourseName();
      this.tid = model.getTid();
      this.baseUrl = model.getBaseUrl();
      this.courseInfoFinish = model.getCourseInfoFinish();
      this.resourceInfoFinish = model.getResourceInfoFinish();
    }

    private String courseId;
    private String courseName;
    private String tid;
    private String baseUrl;
    private Boolean courseInfoFinish = false;
    private Boolean resourceInfoFinish = false;

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

    public String getTid() {
      return tid;
    }

    public void setTid(String tid) {
      this.tid = tid;
    }

    public String getBaseUrl() {
      return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
      this.baseUrl = baseUrl;
    }

    public Boolean getCourseInfoFinish() {
      return courseInfoFinish;
    }

    public void setCourseInfoFinish(Boolean courseInfoFinish) {
      this.courseInfoFinish = courseInfoFinish;
    }

    public Boolean getResourceInfoFinish() {
      return resourceInfoFinish;
    }

    public void setResourceInfoFinish(Boolean resourceInfoFinish) {
      this.resourceInfoFinish = resourceInfoFinish;
    }

  }

  public static Major getMajor() {
    Major major = new Major();
    major.pageIndex = getPageIndex();
    major.allTaskCount = taskCount();
    major.courseInfoUnfinishCount = courseInfoUnfinishHolder.size();
    major.resourceInfoUnfinishCount = resourceInfoUnfinishHolder.size();
    major.finishCount = finishHolder.size();
    major.courseInfoUnfinishHolder.addAll(courseInfoUnfinishHolder);
    major.resourceInfoUnfinishHolder.addAll(resourceInfoUnfinishHolder);
    major.finishHolder.addAll(finishHolder);
    major.taskWrapperMap.putAll(taskWrapperMap);
    return major;
  }

  public static class Major {
    private Integer pageIndex;
    private Integer allTaskCount;
    private Integer courseInfoUnfinishCount;
    private Integer resourceInfoUnfinishCount;
    private Integer finishCount;
    private Integer taskCount;
    private List<String> courseInfoUnfinishHolder = Lists.newArrayList();
    private List<String> resourceInfoUnfinishHolder = Lists.newArrayList();
    private List<String> finishHolder = Lists.newArrayList();
    private Map<String, TaskWrapper> taskWrapperMap = Maps.newHashMap();

    public Integer getPageIndex() {
      return pageIndex;
    }

    public void setPageIndex(Integer pageIndex) {
      this.pageIndex = pageIndex;
    }

    public Integer getAllTaskCount() {
      return allTaskCount;
    }

    public void setAllTaskCount(Integer allTaskCount) {
      this.allTaskCount = allTaskCount;
    }

    public Integer getCourseInfoUnfinishCount() {
      return courseInfoUnfinishCount;
    }

    public void setCourseInfoUnfinishCount(Integer courseInfoUnfinishCount) {
      this.courseInfoUnfinishCount = courseInfoUnfinishCount;
    }

    public Integer getResourceInfoUnfinishCount() {
      return resourceInfoUnfinishCount;
    }

    public void setResourceInfoUnfinishCount(Integer resourceInfoUnfinishCount) {
      this.resourceInfoUnfinishCount = resourceInfoUnfinishCount;
    }

    public Integer getFinishCount() {
      return finishCount;
    }

    public void setFinishCount(Integer finishCount) {
      this.finishCount = finishCount;
    }

    public Integer getTaskCount() {
      return taskCount;
    }

    public void setTaskCount(Integer taskCount) {
      this.taskCount = taskCount;
    }

    public List<String> getCourseInfoUnfinishHolder() {
      return courseInfoUnfinishHolder;
    }

    public void setCourseInfoUnfinishHolder(List<String> courseInfoUnfinishHolder) {
      this.courseInfoUnfinishHolder = courseInfoUnfinishHolder;
    }

    public List<String> getResourceInfoUnfinishHolder() {
      return resourceInfoUnfinishHolder;
    }

    public void setResourceInfoUnfinishHolder(List<String> resourceInfoUnfinishHolder) {
      this.resourceInfoUnfinishHolder = resourceInfoUnfinishHolder;
    }

    public List<String> getFinishHolder() {
      return finishHolder;
    }

    public void setFinishHolder(List<String> finishHolder) {
      this.finishHolder = finishHolder;
    }

    public Map<String, TaskWrapper> getTaskWrapperMap() {
      return taskWrapperMap;
    }

    public void setTaskWrapperMap(Map<String, TaskWrapper> taskWrapperMap) {
      this.taskWrapperMap = taskWrapperMap;
    }

  }

}
