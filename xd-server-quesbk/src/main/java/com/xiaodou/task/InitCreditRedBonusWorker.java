package com.xiaodou.task;

import java.util.List;

import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.constant.QuesBaseConstant;
import com.xiaodou.domain.product.RedBonus;
import com.xiaodou.manager.facade.QuesOperationFacade;
import com.xiaodou.queue.annotation.HandlerMessage;
import com.xiaodou.queue.callback.IMQCallBacker;
import com.xiaodou.queue.message.DefaultMessage;
import com.xiaodou.queue.worker.AbstractDefaultWorker;
import com.xiaodou.summer.util.SpringWebContextHolder;
import com.xiaodou.vo.task.InitCreditRedBonus;

@HandlerMessage("InitCreditRedBonus")
public class InitCreditRedBonusWorker extends AbstractDefaultWorker {

  /** serialVersionUID */
  private static final long serialVersionUID = 1L;

  @Override
  public void domain(DefaultMessage message, IMQCallBacker<DefaultMessage> callback) {
    QuesOperationFacade quesOperationFacade = SpringWebContextHolder.getBean("quesOperationFacade");
    InitCreditRedBonus record =
        FastJsonUtil.fromJson(message.getMessageBodyJson(), InitCreditRedBonus.class);
    RedBonus redBonus = new RedBonus();
    redBonus.setRedBonusType(record.getRedBonusType());
    redBonus.setMissionId(record.getUniqueId());
    redBonus.setModule(record.getModule());
    redBonus.setUserId(record.getUserId());
    redBonus.setCourseId(record.getCourseId().toString());
    redBonus.setChapterId(record.getChapterId().toString());
    redBonus.setItemId(record.getItemId().toString());
    redBonus.setStatue(QuesBaseConstant.RED_BONUS_STATUS_INIT);
    redBonus.markCreate();
    quesOperationFacade.insertRedBonus(redBonus);
  }

  @Override
  public void domain(List<DefaultMessage> message, IMQCallBacker<List<DefaultMessage>> callback) {}

  @Override
  public void onException(Throwable t, List<DefaultMessage> message,
      IMQCallBacker<List<DefaultMessage>> callback) {
    LoggerUtil.error("初始化积分红包异常.", t);
  }

  @Override
  public void onException(Throwable t, DefaultMessage message,
      IMQCallBacker<DefaultMessage> callback) {
    LoggerUtil.error("初始化积分红包异常.", t);
  }

}
