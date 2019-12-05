package com.xiaodou.mission.task;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.xiaodou.common.util.CommUtil;
import com.xiaodou.common.util.DateUtil;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.mission.domain.UserTaskHitCompleteModel;
import com.xiaodou.mission.service.facade.MissionOperationFacade;
import com.xiaodou.queue.annotation.HandlerMessage;
import com.xiaodou.queue.callback.IMQCallBacker;
import com.xiaodou.queue.message.DefaultMessage;
import com.xiaodou.queue.worker.AbstractDefaultWorker;
import com.xiaodou.summer.dao.pagination.Page;
import com.xiaodou.summer.dao.param.IQueryParam;
import com.xiaodou.summer.dao.param.QueryParam;
import com.xiaodou.summer.util.SpringWebContextHolder;

/**
 * @name @see com.xiaodou.mission.task.DelayTaskWorker.java
 * @CopyRright (c) 2017 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年12月26日
 * @description 延迟任务执行Worker
 * @version 1.0
 */
@HandlerMessage("UserTaskHitComplete")
public class UserTaskHitCompleteWorker extends AbstractDefaultWorker {

  /** serialVersionUID */
  private static final long serialVersionUID = 5497429856772578203L;

  @Override
  public void domain(DefaultMessage message, IMQCallBacker<DefaultMessage> callback)
      throws Exception {
    if (StringUtils.isJsonBlank(message.getMessageBodyJson())) {
      return;
    }
    UserTaskHitCompleteModel record =
        FastJsonUtil.fromJson(message.getMessageBodyJson(), UserTaskHitCompleteModel.class);
    if (null == record) {
      return;
    }
    MissionOperationFacade facade = SpringWebContextHolder.getBean("missionOperationFacade");
    IQueryParam param = new QueryParam();
    param.addInput("module", record.getModule());
    param.addInput("courseId", record.getCourseId());
    param.addInput("uid", record.getUid());
    param.addInput("createTimeUpper", new Date(DateUtil.getStartTimeOfDay(new Timestamp(System.currentTimeMillis()))));
    param.addInput("createTimeLower", new Date(DateUtil.getTimesnight(0)));
    param.addOutputs(CommUtil.getAllField(UserTaskHitCompleteModel.class));
    Page<UserTaskHitCompleteModel> page = facade.listUserTaskHitComplete(param, null);
    if (null == page || page.getResult() == null || page.getResult().isEmpty()) {
      facade.insertUserTaskHitComplete(record);
    }
  }

  @Override
  public void domain(List<DefaultMessage> message, IMQCallBacker<List<DefaultMessage>> callback) {}

  @Override
  public void onException(Throwable t, List<DefaultMessage> message,
      IMQCallBacker<List<DefaultMessage>> callback) {
    LoggerUtil.error("处理事件异常.", t);
  }

  @Override
  public void onException(Throwable t, DefaultMessage message,
      IMQCallBacker<DefaultMessage> callback) {
    LoggerUtil.error("处理事件异常.", t);
  }

}
