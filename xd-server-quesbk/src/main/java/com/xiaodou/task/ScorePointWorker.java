package com.xiaodou.task;

import java.util.List;

import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.domain.behavior.UserScorePointRecord;
import com.xiaodou.engine.scorepoint.BaseScorePointCaculator;
import com.xiaodou.engine.scorepoint.ScorePointCaculatorFactory;
import com.xiaodou.manager.facade.QuesOperationFacade;
import com.xiaodou.queue.annotation.HandlerMessage;
import com.xiaodou.queue.callback.IMQCallBacker;
import com.xiaodou.queue.message.DefaultMessage;
import com.xiaodou.queue.worker.AbstractDefaultWorker;
import com.xiaodou.summer.util.SpringWebContextHolder;

/**
 * @name @see com.xiaodou.task.ScorePointWorker.java
 * @CopyRright (c) 2018 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2018年4月13日
 * @description 计分点异步计算逻辑
 * @version 1.0
 */
@HandlerMessage("ScorePoint")
public class ScorePointWorker extends AbstractDefaultWorker {

  /** serialVersionUID */
  private static final long serialVersionUID = 1744525511433456484L;

  @Override
  public void domain(DefaultMessage message, IMQCallBacker<DefaultMessage> callback) {
    UserScorePointRecord record =
        FastJsonUtil.fromJson(message.getMessageBodyJson(), UserScorePointRecord.class);
    QuesOperationFacade quesOperationFacade = SpringWebContextHolder.getBean("quesOperationFacade");
    record.markCreate();
    record =
        quesOperationFacade.queryScorePointRecord(record.getModule(), record.getTypeCode(),
            record.getProductId(), record.getUserId(), record.getRuleType());
    // 计算成绩
    if (caculateScore(record, quesOperationFacade)) {
      quesOperationFacade.insertOrUpdateUserScorePointRecord(record);
      // 更新成绩
      quesOperationFacade.updateUserScoreWithEvent(record.getModule(), record.getProductId(),
          record.getUserId().toString(), record.getTypeCode());
    }

  }

  /**
   * 计算计分点成绩
   * 
   * @param userScorePointRecord 计分点明细记录
   * @param quesOperationFacade 逻辑门面层
   */
  private boolean caculateScore(UserScorePointRecord userScorePointRecord,
      QuesOperationFacade quesOperationFacade) {
    BaseScorePointCaculator cacultor =
        ScorePointCaculatorFactory.buildCaculator(userScorePointRecord.getRuleType(),
            userScorePointRecord);
    if (null == cacultor) {
      return false;
    }
    cacultor.caculateScore(quesOperationFacade);
    userScorePointRecord.setScore(cacultor.getScore());
    userScorePointRecord.setCompletePercent(cacultor.getCompletePercent());
    return true;
  }

  @Override
  public void domain(List<DefaultMessage> message, IMQCallBacker<List<DefaultMessage>> callback) {}

  @Override
  public void onException(Throwable t, List<DefaultMessage> message,
      IMQCallBacker<List<DefaultMessage>> callback) {
    LoggerUtil.error("得分点计算异常.", t);
  }

  @Override
  public void onException(Throwable t, DefaultMessage message,
      IMQCallBacker<DefaultMessage> callback) {
    LoggerUtil.error("得分点计算异常.", t);
  }

}
