package com.xiaodou.mooccrawler.service;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.TypeReference;
import com.google.common.collect.Maps;
import com.xiaodou.common.util.CommUtil;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.mooccrawler.dao.ChapterInfoDao;
import com.xiaodou.mooccrawler.dao.ResourceInfoDao;
import com.xiaodou.mooccrawler.dao.TaskModelDao;
import com.xiaodou.mooccrawler.domain.ChapterInfo;
import com.xiaodou.mooccrawler.domain.ResourceInfo;
import com.xiaodou.mooccrawler.domain.TaskModel;
import com.xiaodou.mooccrawler.holder.TaskHolder;
import com.xiaodou.mooccrawler.holder.TaskHolder.Task;
import com.xiaodou.mooccrawler.request.CourseInfoPojo;
import com.xiaodou.mooccrawler.request.CourseInfoPojo.ChapterDetail;
import com.xiaodou.mooccrawler.request.FinishResourceInfoPojo;
import com.xiaodou.mooccrawler.request.ResourceInfoPojo;
import com.xiaodou.summer.dao.pagination.Page;
import com.xiaodou.summer.dao.param.IQueryParam;
import com.xiaodou.summer.dao.param.QueryParam;

@Service("moocService")
public class MoocService {

  @Resource
  QueueService queueService;

  @Resource
  FileUtilService fileUtilService;

  @Resource
  TaskModelDao taskModelDao;

  @Resource
  ChapterInfoDao chapterInfoDao;

  @Resource
  ResourceInfoDao resourceInfoDao;

  public void addChapterInfo(CourseInfoPojo pojo) {
    if (StringUtils.isJsonBlank(pojo.getCourseDetail())) {
      return;
    }
    Task task = new Task(pojo.getCourseUrl());
    task.setCourseName(pojo.getCourseName());
    String courseId = task.getCourseId();
    IQueryParam param = new QueryParam();
    param.addInput("courseId", courseId);
    param.addOutputs(CommUtil.getAllField(TaskModel.class));
    Page<TaskModel> taskPage = taskModelDao.findEntityListByCond(param, null);
    if (null != taskPage && null != taskPage.getResult() && !taskPage.getResult().isEmpty()
        && StringUtils.isNotBlank(taskPage.getResult().get(0).getCourseName())) {
      System.out.println(courseId + " has crawled.");
      return;
    }
    Map<String, String> chapterMap = Maps.newHashMap();
    Map<String, String> itemMap = Maps.newHashMap();
    List<ChapterDetail> courseDetail =
        FastJsonUtil.fromJsons(pojo.getCourseDetail(), new TypeReference<List<ChapterDetail>>() {});
    for (ChapterDetail detail : courseDetail) {
      ChapterInfo chapter = new ChapterInfo();
      chapter.setCourseId(courseId);
      chapter.setParentId("0");
      chapter.setType("chapter");
      String[] chapterName = detail.getWeek().split(" ");
      chapter.setChapterName(chapterName[chapterName.length - 1]);
      if (!chapterMap.containsKey(detail.getWeek())) {
        String id = UUID.randomUUID().toString();
        chapterMap.put(detail.getWeek(), id);
        chapter.setCid(id);
        chapterInfoDao.addEntity(chapter);
      }

      ChapterInfo item = new ChapterInfo();
      item.setCourseId(courseId);
      item.setParentId(chapterMap.get(detail.getWeek()));
      item.setType("item");
      String[] itemName = detail.getChapter().split(" ");
      item.setChapterName(itemName[itemName.length - 1]);
      if (!itemMap.containsKey(detail.getChapter())) {
        String id = UUID.randomUUID().toString();
        itemMap.put(detail.getChapter(), id);
        item.setCid(id);
        chapterInfoDao.addEntity(item);
      }

      ResourceInfo resource = new ResourceInfo();
      resource.setCourseId(courseId);
      resource.setChapterId(itemMap.get(detail.getChapter()));
      resource.setIndex(detail.getIndex());
      resource.setCid(detail.getCid());
      resource.setResourceName(detail.getTitle());
      resource.setType(detail.getTitle().split(":")[0]);
      resourceInfoDao.addEntity(resource);
    }
    task.setCourseInfoFinish(true);
    queueService.addInfo(task);
    TaskHolder.finishCourseInfo(courseId);
  }

  public ResourceInfo findEmptyResource(ResourceInfoPojo pojo) {
    Task task = new Task(pojo.getCourseUrl());
    String courseId = task.getCourseId();
    IQueryParam param = new QueryParam();
    param.addInput("courseId", courseId);
    // param.addInput("type", new MongoFieldParam("文档", Scope.LIKE));
    param.addOutputs(CommUtil.getAllField(TaskModel.class));
    Page<ResourceInfo> resourceInfoPage = resourceInfoDao.findEntityListByCond(param, null);
    if (null != resourceInfoPage && null != resourceInfoPage.getResult()
        && !resourceInfoPage.getResult().isEmpty()) {
      for (ResourceInfo info : resourceInfoPage.getResult()) {
        if (StringUtils.isBlank(info.getResourceUrl())) {
          return info;
        }
      }
    }
    task.setCourseInfoFinish(true);
    task.setResourceInfoFinish(true);
    queueService.finishResource(task);
    TaskHolder.finishResourceInfo(courseId);
    return null;
  }

  public ResourceInfo finishResource(FinishResourceInfoPojo pojo) {
    if (StringUtils.isOrBlank(pojo.getCid(), pojo.getUrl())) {
      return null;
    }
    IQueryParam param = new QueryParam();
    param.addInput("cid", pojo.getCid());
    param.addOutputs(CommUtil.getAllField(TaskModel.class));
    Page<ResourceInfo> resourceInfoPage = resourceInfoDao.findEntityListByCond(param, null);
    if (null != resourceInfoPage && null != resourceInfoPage.getResult()
        && !resourceInfoPage.getResult().isEmpty()) {
      if (resourceInfoPage.getResult().size() > 1) {
        throw new RuntimeException("CID 不唯一");
      }
      if (StringUtils.isNotBlank(resourceInfoPage.getResult().get(0).getResourceUrl())) {
        throw new RuntimeException(String.format("url 已存在.[%s]",
            FastJsonUtil.toJson(resourceInfoPage.getResult().get(0))));
      }
      ResourceInfo info = new ResourceInfo();
      String url = pojo.getUrl();
      if (url.indexOf("https") == 0) {
        url = url.replaceAll("https", "http");
      } else if (url.indexOf("//") == 0) {
        url = "http:" + url;
      }
      if (resourceInfoPage.getResult().get(0).getType().indexOf("文档") > -1) {
        String baseUrl = "http://resource.51xiaodou.com/";
        String fileName = UUID.randomUUID().toString() + ".pdf";
        if (fileUtilService.downLoadByUrl(url, fileName)) {
          info.setResourceUrl(baseUrl + fileName);
          Map<String, Object> cond = Maps.newHashMap();
          cond.put("cid", pojo.getCid());
          resourceInfoDao.updateEntityByCond(cond, info);
        }
      } else {
        info.setResourceUrl(url);
        Map<String, Object> cond = Maps.newHashMap();
        cond.put("cid", pojo.getCid());
        resourceInfoDao.updateEntityByCond(cond, info);
      }
    }
    return null;
  }

}
