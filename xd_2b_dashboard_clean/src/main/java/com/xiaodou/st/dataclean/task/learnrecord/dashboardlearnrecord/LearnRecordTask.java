package com.xiaodou.st.dataclean.task.learnrecord.dashboardlearnrecord;

import java.util.List;

import com.xiaodou.common.util.StringUtils;
import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.queue.annotation.HandlerMessage;
import com.xiaodou.queue.callback.IMQCallBacker;
import com.xiaodou.queue.enums.MessageType;
import com.xiaodou.queue.message.DefaultMessage;
import com.xiaodou.queue.worker.AbstractDefaultWorker;
import com.xiaodou.st.dataclean.model.domain.dashboard.score.LearnRecordDO;
import com.xiaodou.st.dataclean.model.domain.raw.RawDataLearnRecordModel;
import com.xiaodou.st.dataclean.service.facade.DashBoardServiceFacade;
import com.xiaodou.st.dataclean.util.LockFactory;
import com.xiaodou.st.dataclean.util.RealSqlSession;
import com.xiaodou.summer.util.SpringWebContextHolder;

@HandlerMessage(value = "UserLearnRecord", type = MessageType.Multiple)
public class LearnRecordTask extends AbstractDefaultWorker {
  /**
   * serialVersionUID
   */
  private static final long serialVersionUID = -4334009237028174065L;

  @Override
  public void domain(DefaultMessage message, IMQCallBacker<DefaultMessage> callback) {
    try {
      LockFactory.getCategoryUnitSessionLearnLock();
      if (StringUtils.isBlank(message.getMessageBodyJson())) return;
      DashBoardServiceFacade dashBoardServiceFacade =
          SpringWebContextHolder.getBean("dashBoardServiceFacade");
      RawDataLearnRecordModel rdl =
          FastJsonUtil.fromJson(message.getMessageBodyJson(), RawDataLearnRecordModel.class);
      RealSqlSession.setRealSqlSession(rdl.getModuleId());
      LearnRecordDO lrdo = LearnRecordDO.getInstance(rdl);
      if (null != lrdo) dashBoardServiceFacade.savelearnRecord(lrdo);
    }catch(Exception e){
      e.printStackTrace();
    } finally {
      LockFactory.returnCategoryUnitSessionLearnLock();
    }
  }

  @Override
  public void domain(List<DefaultMessage> message, IMQCallBacker<List<DefaultMessage>> callback) {}

  @Override
  public void onException(Throwable t, List<DefaultMessage> message,
      IMQCallBacker<List<DefaultMessage>> callback) {}

  @Override
  public void onException(Throwable t, DefaultMessage message,
      IMQCallBacker<DefaultMessage> callback) {
    LoggerUtil.error("记录 学习记录 失败", t);
  }
}
