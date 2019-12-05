package com.xiaodou.mooccrawler.init;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.xiaodou.common.util.CommUtil;
import com.xiaodou.mooccrawler.dao.ChapterInfoDao;
import com.xiaodou.mooccrawler.dao.TaskModelDao;
import com.xiaodou.mooccrawler.domain.TaskModel;
import com.xiaodou.mooccrawler.holder.TaskHolder;
import com.xiaodou.mooccrawler.holder.TaskHolder.Task;
import com.xiaodou.summer.dao.pagination.Page;
import com.xiaodou.summer.dao.param.IQueryParam;
import com.xiaodou.summer.dao.param.QueryParam;

@Service("taskInit")
public class TaskInit {

  @Resource
  TaskModelDao taskModelDao;

  @Resource
  ChapterInfoDao chapterInfoDao;

  @PostConstruct
  public void init() {
    Page<TaskModel> taskModelPage = new Page<>(500);
    IQueryParam param = new QueryParam();
    param.addOutputs(CommUtil.getAllField(TaskModel.class));
    taskModelPage = taskModelDao.findEntityListByCond(param, taskModelPage);
    if (null != taskModelPage && null != taskModelPage.getResult()
        && taskModelPage.getResult().size() > 0) {
      for (TaskModel model : taskModelPage.getResult()) {
        TaskHolder.pushTask(new Task(model), false);
      }
    }
  }

  // @PostConstruct
  // public void processChapter() {
  // Page<ChapterInfo> chapterInfoPage = new Page<>(20000);
  // IQueryParam param = new QueryParam();
  // // param.addInput("type", "chapter");
  // param.addOutputs(CommUtil.getAllField(ChapterInfo.class));
  // chapterInfoPage = chapterInfoDao.findEntityListByCond(param, chapterInfoPage);
  // if (null != chapterInfoPage && null != chapterInfoPage.getResult()
  // && chapterInfoPage.getResult().size() > 0) {
  // for (ChapterInfo model : chapterInfoPage.getResult()) {
  // String chapterName = model.getChapterName();
  // String[] name = chapterName.split(" ");
  // if (name.length > 1) {
  // ChapterInfo info = new ChapterInfo();
  // info.setCid(model.getCid());
  // info.setChapterName(name[name.length - 1]);
  // System.out.println(info.getChapterName());
  // chapterInfoDao.updateEntityById(info);
  // }
  // }
  // }
  // }

}
