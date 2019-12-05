package com.xiaodou.st.dashboard.service.task.staticinfo;

import java.util.List;

import com.xiaodou.common.util.StringUtils;
import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.goeasy.GoEasy;
import com.xiaodou.queue.annotation.HandlerMessage;
import com.xiaodou.queue.callback.IMQCallBacker;
import com.xiaodou.queue.enums.MessageType;
import com.xiaodou.queue.message.DefaultMessage;
import com.xiaodou.queue.worker.AbstractDefaultWorker;
import com.xiaodou.st.dashboard.constants.enums.SyncResult;
import com.xiaodou.st.dashboard.domain.staticinfo.SyncLogDO;
import com.xiaodou.st.dashboard.service.facade.IStServiceFacade;
import com.xiaodou.st.dashboard.util.StaticInfoProp;
import com.xiaodou.st.dashboard.util.SyncLogUtil;
import com.xiaodou.summer.util.SpringWebContextHolder;

@HandlerMessage(value = "SyncFinish", type = MessageType.Single)
public class SyncFinish extends AbstractDefaultWorker {

  /** serialVersionUID */
  private static final long serialVersionUID = 1L;
  public static GoEasy goEasy;
  static {
    if (null == goEasy) goEasy = new GoEasy(StaticInfoProp.goEasyCommonkey());
  }
  @Override
  public void domain(DefaultMessage message, IMQCallBacker<DefaultMessage> callback)
      throws Exception {
    if (StringUtils.isBlank(message.getMessageBodyJson())) return;
    IStServiceFacade stServiceFacade = SpringWebContextHolder.getBean("stServiceFacade");
    SyncLogDO syncLogDO = FastJsonUtil.fromJson(message.getMessageBodyJson(), SyncLogDO.class);
    syncLogDO.setSyncResult(SyncResult.SyncFinish.getCode());
    syncLogDO.setSyncResultMsg(SyncResult.SyncFinish.getDesc());
    if(stServiceFacade.updateSyncLog(syncLogDO)){
      SyncLogUtil.clear(syncLogDO.getId().toString());
    }
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
    LoggerUtil.error("更新同步日志失败", t);
  }

}
