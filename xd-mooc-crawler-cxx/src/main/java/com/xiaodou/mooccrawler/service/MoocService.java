package com.xiaodou.mooccrawler.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.xiaodou.common.util.CommUtil;
import com.xiaodou.common.util.RandomUtil;
import com.xiaodou.mooccrawler.dao.MoocCourseDao;
import com.xiaodou.mooccrawler.dao.MoocItemDao;
import com.xiaodou.mooccrawler.dao.MoocResourceDao;
import com.xiaodou.mooccrawler.domain.MoocChapter;
import com.xiaodou.mooccrawler.domain.MoocCourse;
import com.xiaodou.mooccrawler.domain.MoocItem;
import com.xiaodou.mooccrawler.domain.MoocResource;
import com.xiaodou.mooccrawler.holder.MoocCourseHolder;
import com.xiaodou.mooccrawler.request.CourseInfoPojo;
import com.xiaodou.mooccrawler.request.CourseInfoPojo.ChapterInfo;
import com.xiaodou.mooccrawler.request.CourseInfoPojo.ItemInfo;
import com.xiaodou.mooccrawler.request.ItemInfoPojo;
import com.xiaodou.mooccrawler.request.ResourceInfoPojo;
import com.xiaodou.mooccrawler.request.ResourceInfoPojo.ResourceInfo;
import com.xiaodou.mooccrawler.service.QueueService.Message;
import com.xiaodou.queue.client.AbstractMQClient.MessageBox;
import com.xiaodou.summer.dao.pagination.Page;
import com.xiaodou.summer.dao.param.IQueryParam;
import com.xiaodou.summer.dao.param.QueryParam;

@Service
public class MoocService {

  @Resource
  QueueService queueService;
  @Resource
  MoocCourseDao moocCourseDao;
  @Resource
  MoocItemDao moocItemDao;
  @Resource
  MoocResourceDao moocResourceDao;

  public void createCourseInfo(CourseInfoPojo pojo) {
    if (null == pojo) return;
    MessageBox box = new MessageBox();
    IQueryParam param = new QueryParam();
    param.addInput("cxId", pojo.getId());
    param.addOutput("courseId", "1");
    Page<MoocCourse> coursePage = moocCourseDao.findEntityListByCond(param, null);
    if (null != coursePage && null != coursePage.getResult() && !coursePage.getResult().isEmpty()) {
      return;
    }
    MoocCourse course = new MoocCourse();
    course.setCourseId(RandomUtil.randomString(10));
    course.setCxId(pojo.getId());
    course.setName(pojo.getName());
    box.addCurrentLevelMessage(Message.CreateCourse.toString(), course);
    if (null == pojo.getChapterArray() || pojo.getChapterArray().isEmpty()) return;
    for (ChapterInfo chapterInfo : pojo.getChapterArray()) {
      MoocChapter chapter = new MoocChapter();
      chapter.setChapterId(RandomUtil.randomString(10));
      chapter.setIndex(chapterInfo.getIndex());
      chapter.setName(chapterInfo.getText());
      chapter.setCourse(course);
      box.addCurrentLevelMessage(Message.CreateChapter.toString(), chapter);
      if (null != chapterInfo.getItemArray() && !chapterInfo.getItemArray().isEmpty()) {
        for (ItemInfo itemInfo : chapterInfo.getItemArray()) {
          MoocItem item = new MoocItem();
          item.setItemId(RandomUtil.randomString(10));
          item.setIndex(itemInfo.getIndex());
          item.setName(itemInfo.getText());
          if (itemInfo.getHref().startsWith("javascript")) {
            item.setDownload(true);
          } else {
            if (itemInfo.getHref().startsWith("http")) {
              item.setHref(itemInfo.getHref());
            } else if (itemInfo.getHref().startsWith("/")) {
              item.setHref("http://mooc1.chaoxing.com" + itemInfo.getHref());
            } else if (itemInfo.getHref().startsWith("?")) {
              item.setHref("http://mooc1.chaoxing.com/nodedetailcontroller/visitnodedetail"
                  + itemInfo.getHref());
            }
          }
          item.setChapter(chapter);
          box.addCurrentLevelMessage(Message.CreateItem.toString(), item);
        }
      }
    }
    queueService.sendMessageBox(box);
  }

  public String uploadResourceInfo(ResourceInfoPojo pojo) {
    MoocItem moocItem = MoocCourseHolder.check(pojo.getUrl());
    if (null != moocItem) {
      if (null != pojo.getResourceInfoList() && !pojo.getResourceInfoList().isEmpty()) {
        MessageBox box = new MessageBox();
        for (ResourceInfo resourceInfo : pojo.getResourceInfoList()) {
          MoocResource resource = new MoocResource();
          resource.setName(resourceInfo.getName());
          resource.setUrl(resourceInfo.getUrl());
          resource.setItem(moocItem);
          box.addCurrentLevelMessage(Message.CreateResource.toString(), resource);
        }
        queueService.sendMessageBox(box);
      }
      moocItem.setDownload(true);
      MoocItem entity = new MoocItem();
      entity.setItemId(moocItem.getItemId());
      entity.setDownload(true);
      moocItemDao.updateEntityById(entity);
      MoocCourseHolder.finish(pojo.getUrl());
    }
    MoocItem item = MoocCourseHolder.renew();
    if (null == item) {
      return "Finish!";
    } else {
      return item.getHref();
    }
  }

  public String getScript(String courseId) {
    StringBuilder sb = new StringBuilder(1000);
    sb.append("#!/bin/bash");
    sb.append(System.getProperty("line.separator"));
    IQueryParam param = new QueryParam();
    param.addInput("courseId", courseId);
    param.addOutputs(CommUtil.getAllField(MoocResource.class));
    Page<MoocResource> resourcePage = moocResourceDao.findEntityListByCond(param, null);
    if (null != resourcePage && null != resourcePage.getResult()
        && !resourcePage.getResult().isEmpty()) {
      sb.append("#Init Base Path And Move");
      sb.append(System.getProperty("line.separator"));
      sb.append("cd && mkdir -p ").append(courseId).append(" && cd ").append(courseId);
      sb.append(System.getProperty("line.separator"));
      for (MoocResource resource : resourcePage.getResult()) {
        sb.append("mkdir -p `pwd`/").append(resource.getCourseName().replaceAll("\\s*", ""))
            .append("/").append(resource.getChapterName().replaceAll("\\s*", "")).append("/")
            .append(resource.getItemName().replaceAll("\\s*", "")).append(" && wget ")
            .append(resource.getUrl()).append(" -O `pwd`/")
            .append(resource.getCourseName().replaceAll("\\s*", "")).append("/")
            .append(resource.getChapterName().replaceAll("\\s*", "")).append("/")
            .append(resource.getItemName().replaceAll("\\s*", "")).append("/")
            .append(resource.getName().replaceAll("\\s*", ""));
        sb.append(System.getProperty("line.separator"));
      }
      sb.append("#Zip All.");
      sb.append(System.getProperty("line.separator"));
      sb.append("cd && zip ").append(courseId).append(".zip -r ./").append(courseId).append("/*");
      sb.append(System.getProperty("line.separator"));
    }
    sb.append("#Finish");
    sb.append(System.getProperty("line.separator"));
    return sb.toString();
  }

  public String finishItemInfo(ItemInfoPojo pojo) {
    MoocItem moocItem = MoocCourseHolder.check(pojo.getHref());
    if (moocItem != null) {
      if (null != pojo.getItemArray() && !pojo.getItemArray().isEmpty()) {
        MessageBox box = new MessageBox();
        for (ItemInfo itemInfo : pojo.getItemArray()) {
          MoocItem item = new MoocItem();
          item.setItemId(RandomUtil.randomString(10));
          item.setIndex(itemInfo.getIndex());
          item.setName(itemInfo.getText());
          if (itemInfo.getHref().startsWith("javascript")) {
            item.setDownload(true);
          } else {
            if (itemInfo.getHref().startsWith("http")) {
              item.setHref(itemInfo.getHref());
            } else if (itemInfo.getHref().startsWith("/")) {
              item.setHref("http://mooc1.chaoxing.com" + itemInfo.getHref());
            } else if (itemInfo.getHref().startsWith("?")) {
              item.setHref("http://mooc1.chaoxing.com/nodedetailcontroller/visitnodedetail"
                  + itemInfo.getHref());
            }
          }
          item.setCourseId(moocItem.getCourseId());
          item.setCourseName(moocItem.getCourseName());
          item.setChapterId(moocItem.getChapterId());
          item.setChapterName(moocItem.getChapterName());
          box.addCurrentLevelMessage(Message.CreateItem.toString(), item);
        }
        queueService.sendMessageBox(box);
      }
      moocItem.setDownload(true);
      MoocItem entity = new MoocItem();
      entity.setItemId(moocItem.getItemId());
      entity.setDownload(true);
      moocItemDao.updateEntityById(entity);
      MoocCourseHolder.finish(pojo.getHref());
    }
    MoocItem item = MoocCourseHolder.renew();
    if (null == item) {
      return "Finish!";
    } else {
      return item.getHref();
    }
  }
}
