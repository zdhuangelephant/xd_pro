package com.xiaodou.mission.task;

import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

import org.springframework.util.Assert;

import com.xiaodou.common.util.StringUtils;
import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.jmsg.client.RabbitMQSender;
import com.xiaodou.mission.domain.CascadeMissionRecordModel;
import com.xiaodou.mission.domain.UserMissionRecordModel;
import com.xiaodou.mission.service.facade.MissionOperationFacade;
import com.xiaodou.mission.vo.message.CreateRedBonusMessage;
import com.xiaodou.mission.vo.model.UserMissionList;
import com.xiaodou.queue.annotation.HandlerMessage;
import com.xiaodou.queue.callback.IMQCallBacker;
import com.xiaodou.queue.message.DefaultMessage;
import com.xiaodou.queue.worker.AbstractDefaultWorker;
import com.xiaodou.summer.util.SpringWebContextHolder;

/**
 * @name @see com.xiaodou.mission.task.AddMissionRecordWorker.java
 * @CopyRright (c) 2017 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年12月25日
 * @description 异步添加任务记录Worker
 * @version 1.0
 */
@HandlerMessage("AddMissionRecord")
public class AddMissionRecordWorker extends AbstractDefaultWorker {

  private static ReentrantLock tableLock = new ReentrantLock();

  /** serialVersionUID */
  private static final long serialVersionUID = -8734050298859220948L;

  @Override
  public void domain(DefaultMessage message, IMQCallBacker<DefaultMessage> callback)
      throws Exception {
    try {
      tableLock.lock();
      String messageBody = message.getMessageBodyJson();
      if (StringUtils.isJsonBlank(messageBody)) {
        return;
      }
      UserMissionList userMissionList = FastJsonUtil.fromJson(messageBody, UserMissionList.class);
      MissionOperationFacade missionOperationFacade =
          SpringWebContextHolder.getBean("missionOperationFacade");
      for (CascadeMissionRecordModel missionModel : userMissionList.getTaskList()) {
        if (!missionModel.getNeedBuildRecord()) {
          continue;
        }
        UserMissionRecordModel record = new UserMissionRecordModel();
        record.setUserId(missionModel.getUserId());
        record.setMissionId(missionModel.getId());
        List<UserMissionRecordModel> missionRecordList =
            missionOperationFacade.queryUserMissionRecordList(record);
        if (null != missionRecordList && !missionRecordList.isEmpty()) {
          continue;
        }
        record = new UserMissionRecordModel(missionModel);
        missionOperationFacade.insertUserMissionRecord(record);
        Assert.isTrue(StringUtils.isNotBlank(record.getModule()));
        RabbitMQSender.getInstance().send(new CreateRedBonusMessage(record));
      }
    } finally {
      tableLock.unlock();
    }
  }

  @Override
  public void domain(List<DefaultMessage> message, IMQCallBacker<List<DefaultMessage>> callback)
      throws Exception {}

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
