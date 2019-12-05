package com.xiaodou.st.dataclean.task.alarm;

import java.util.List;

import com.xiaodou.common.util.StringUtils;
import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.queue.annotation.HandlerMessage;
import com.xiaodou.queue.callback.IMQCallBacker;
import com.xiaodou.queue.enums.MessageType;
import com.xiaodou.queue.message.DefaultMessage;
import com.xiaodou.queue.worker.AbstractDefaultWorker;
import com.xiaodou.st.dataclean.model.domain.dashboard.alarm.AlarmRecordModel;
import com.xiaodou.st.dataclean.service.facade.DashBoardServiceFacade;
import com.xiaodou.summer.util.SpringWebContextHolder;

/**
 * @name NoneLearnRecordTask CopyRright (c) 2018 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:huangzedong@corp.51xiaodou.com">zdhuang</a>
 * @date 2018年3月5日
 * @description TODO
 * @version 1.0
 */
@HandlerMessage(value = "UserLearnTracker", type = MessageType.Multiple)
public class NoneLearnRecordTask extends AbstractDefaultWorker {

  /** serialVersionUID */
  private static final long serialVersionUID = -4065586141524892930L;

  @Override
  public void domain(DefaultMessage message, IMQCallBacker<DefaultMessage> callback)
      throws Exception {
    /**
     * 考虑到是异步调用、防止数据库的IO堵塞、所有的条件都独立开
     */
    if (StringUtils.isBlank(message.getMessageBodyJson())) return;
    DashBoardServiceFacade facade = SpringWebContextHolder.getBean("dashBoardServiceFacade");
    AlarmRecordModel alarmRecord =
        FastJsonUtil.fromJson(message.getMessageBodyJson(), AlarmRecordModel.class);
    if (null == alarmRecord) return;
    alarmRecord.setModule(alarmRecord.getModule());
    facade.insertAlarmRecord(alarmRecord);
  }

  @Override
  public void domain(List<DefaultMessage> message, IMQCallBacker<List<DefaultMessage>> callback)
      throws Exception {

  }

  @Override
  public void onException(Throwable t, List<DefaultMessage> message,
      IMQCallBacker<List<DefaultMessage>> callback) {

  }

  @Override
  public void onException(Throwable t, DefaultMessage message,
      IMQCallBacker<DefaultMessage> callback) {
    LoggerUtil.error("无学习记录的报警发生异常", t);
  }

}
