package com.xiaodou.st.dashboard.service.task.student;

import java.util.List;
import java.util.Map;

import com.google.common.collect.Maps;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.queue.annotation.HandlerMessage;
import com.xiaodou.queue.callback.IMQCallBacker;
import com.xiaodou.queue.enums.MessageType;
import com.xiaodou.queue.message.DefaultMessage;
import com.xiaodou.queue.worker.AbstractDefaultWorker;
import com.xiaodou.st.dashboard.domain.apply.ApplyDO;
import com.xiaodou.st.dashboard.domain.student.StudentDO;
import com.xiaodou.st.dashboard.service.facade.IStServiceFacade;
import com.xiaodou.st.dashboard.util.LockFactory;
import com.xiaodou.summer.util.SpringWebContextHolder;

@HandlerMessage(value = "UpdateTelephone", type = MessageType.Multiple)
public class UpdateTelephoneToStudent extends AbstractDefaultWorker {
  /**
   * serialVersionUID
   */
  private static final long serialVersionUID = -4334009237028174065L;

  @Override
  public void domain(DefaultMessage message, IMQCallBacker<DefaultMessage> callback) {
    try {
      LockFactory.getCategoryUnitSessionLearnLock();
      if (StringUtils.isBlank(message.getMessageBodyJson())) return;
      IStServiceFacade stServiceFacade = SpringWebContextHolder.getBean("stServiceFacade");
      String telephone = FastJsonUtil.fromJson(message.getMessageBodyJson(), String.class);
    } catch (Exception e) {
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
    LoggerUtil.error("学生信息 异步更新 失败", t);
  }
}
