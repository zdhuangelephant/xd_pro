package com.xiaodou.mooccrawler.task;

import java.util.List;

import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.mooccrawler.dao.MoocCourseDao;
import com.xiaodou.mooccrawler.domain.MoocCourse;
import com.xiaodou.queue.annotation.HandlerMessage;
import com.xiaodou.queue.callback.IMQCallBacker;
import com.xiaodou.queue.message.DefaultMessage;
import com.xiaodou.queue.worker.AbstractDefaultWorker;
import com.xiaodou.summer.util.SpringWebContextHolder;

@HandlerMessage("CreateCourse")
public class CreateCourseWork extends AbstractDefaultWorker {

  /** serialVersionUID */
  private static final long serialVersionUID = 1L;

  @Override
  public void domain(DefaultMessage message, IMQCallBacker<DefaultMessage> callback)
      throws Exception {
    String mes = message.getMessageBodyJson();
    MoocCourse course = FastJsonUtil.fromJson(mes, MoocCourse.class);
    MoocCourseDao courseDao = SpringWebContextHolder.getBean(MoocCourseDao.class);
    courseDao.addEntity(course);
  }

  @Override
  public void domain(List<DefaultMessage> message, IMQCallBacker<List<DefaultMessage>> callback)
      throws Exception {

  }

  @Override
  public void onException(Throwable t, List<DefaultMessage> message,
      IMQCallBacker<List<DefaultMessage>> callback) {}

  @Override
  public void onException(Throwable t, DefaultMessage message,
      IMQCallBacker<DefaultMessage> callback) {
    LoggerUtil.error(message.getMessageBodyJson(), t);
  }

}
