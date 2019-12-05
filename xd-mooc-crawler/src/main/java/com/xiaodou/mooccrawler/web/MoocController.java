package com.xiaodou.mooccrawler.web;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xiaodou.common.util.StringUtils;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.mooccrawler.domain.ResourceInfo;
import com.xiaodou.mooccrawler.holder.TaskHolder;
import com.xiaodou.mooccrawler.holder.TaskHolder.Task;
import com.xiaodou.mooccrawler.holder.TaskHolder.TaskWrapper;
import com.xiaodou.mooccrawler.request.CourseInfoPojo;
import com.xiaodou.mooccrawler.request.CourseListPojo;
import com.xiaodou.mooccrawler.request.FinishResourceInfoPojo;
import com.xiaodou.mooccrawler.request.ResourceInfoPojo;
import com.xiaodou.mooccrawler.service.MoocService;

@Controller("moocController")
public class MoocController {

  @Resource
  MoocService moocService;

  @RequestMapping(value = "/start_crawler", method = RequestMethod.POST)
  @ResponseBody
  public String startCrawler() {
    Integer count = TaskHolder.taskCount();
    if (count < 200) {
      return "courseList||" + TaskHolder.getPageIndex();
    } else {
      return "fetchTask";
    }
  }

  @RequestMapping(value = "/get_crawler_task/{deviceId}", method = RequestMethod.POST)
  @ResponseBody
  public String newCrawlerTask(@PathVariable String deviceId) {
    TaskWrapper task = TaskHolder.getTask(deviceId);
    if (null == task) {
      return "OVER";
    }
    return FastJsonUtil.toJson(task);
  }

  @RequestMapping(value = "/course_list", method = RequestMethod.POST)
  @ResponseBody
  public String courseList(CourseListPojo pojo) {
    String[] coursePaths = pojo.getCourseList().split(";");
    for (String coursePath : coursePaths) {
      if (StringUtils.isBlank(coursePath)) {
        continue;
      }
      TaskHolder.pushTask(new Task(coursePath));
    }
    TaskHolder.setPageIndex(pojo.getPage());
    return "SUCCESS";
  }

  @RequestMapping(value = "/course_info", method = RequestMethod.POST)
  @ResponseBody
  public String courseInfo(CourseInfoPojo pojo) {
    moocService.addChapterInfo(pojo);
    return "SUCCESS";
  }

  @RequestMapping(value = "/resource_info", method = RequestMethod.POST)
  @ResponseBody
  public String resourceInfo(ResourceInfoPojo pojo) {
    ResourceInfo info = moocService.findEmptyResource(pojo);
    if (null != info) return FastJsonUtil.toJson(info);
    return "SUCCESS";
  }


  @RequestMapping(value = "/finish_resource_info", method = RequestMethod.POST)
  @ResponseBody
  public String finishResourceInfo(FinishResourceInfoPojo pojo) {
    moocService.finishResource(pojo);
    return "SUCCESS";
  }

  @RequestMapping(value = "/show", method = RequestMethod.GET)
  @ResponseBody
  public String show() {
    return FastJsonUtil.toJson(TaskHolder.getMajor());
  }

}
