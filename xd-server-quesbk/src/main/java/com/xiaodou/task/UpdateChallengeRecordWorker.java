package com.xiaodou.task;

import java.util.List;

import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.constant.QuesBaseConstant;
import com.xiaodou.domain.product.ChallengeRecord;
import com.xiaodou.manager.facade.QuesOperationFacade;
import com.xiaodou.mission.engine.event.EventBuilder;
import com.xiaodou.mission.engine.event.FriendPkEvent;
import com.xiaodou.mission.engine.event.RandomPkEvent;
import com.xiaodou.queue.annotation.HandlerMessage;
import com.xiaodou.queue.callback.IMQCallBacker;
import com.xiaodou.queue.message.DefaultMessage;
import com.xiaodou.queue.worker.AbstractDefaultWorker;
import com.xiaodou.summer.util.SpringWebContextHolder;

@HandlerMessage("UpdateChallengeRecord")
public class UpdateChallengeRecordWorker extends AbstractDefaultWorker {

  /** serialVersionUID */
  private static final long serialVersionUID = -2466786283295002712L;

  @Override
  public void domain(DefaultMessage message, IMQCallBacker<DefaultMessage> callback) {
    QuesOperationFacade quesOperationFacade = SpringWebContextHolder.getBean("quesOperationFacade");
    ChallengeRecord record =
        FastJsonUtil.fromJson(message.getMessageBodyJson(), ChallengeRecord.class);
    quesOperationFacade.updateChallengeRecord(record);
    if (QuesBaseConstant.QUES_CHALLENGE_STATUS_YIJIESHU == record.getStatus()) {
      if (record.getType() == QuesBaseConstant.QUES_CHALLENGE_FRIEND) {
        buildFriendPkEvent(record.getModule(), record.getCourseId(), record.getChallengerUid(),
            record.getWinner());
        buildFriendPkEvent(record.getModule(), record.getCourseId(), record.getBeChallengerUid(),
            record.getWinner());
      }
      if (record.getType() == QuesBaseConstant.QUES_CHALLENGE_RANDOM) {
        buildRandomPkEvent(record.getModule(), record.getCourseId(), record.getChallengerUid(),
            record.getWinner());
        buildRandomPkEvent(record.getModule(), record.getCourseId(), record.getBeChallengerUid(),
            record.getWinner());
      }
    }
  }

  private void buildRandomPkEvent(String module, String courseId, String userId, String winnerId) {
    RandomPkEvent event = EventBuilder.buildRandomPkEvent();
    event.setModule(module);
    event.setUserId(userId);
    event.setCourseId(courseId);
    if (userId.equals(winnerId)) {
      event.setIsWinner(true);
    } else {
      event.setIsFailer(true);
    }
    event.send();
  }

  private void buildFriendPkEvent(String module, String courseId, String userId, String winnerId) {
    FriendPkEvent event = EventBuilder.buildFriendPkEvent();
    event.setModule(module);
    event.setUserId(userId);
    event.setCourseId(courseId);
    if (userId.equals(winnerId)) {
      event.setIsWinner(true);
    } else {
      event.setIsFailer(true);
    }
    event.send();
  }

  @Override
  public void domain(List<DefaultMessage> message, IMQCallBacker<List<DefaultMessage>> callback) {}

  @Override
  public void onException(Throwable t, List<DefaultMessage> message,
      IMQCallBacker<List<DefaultMessage>> callback) {}

  @Override
  public void onException(Throwable t, DefaultMessage message,
      IMQCallBacker<DefaultMessage> callback) {
    LoggerUtil.error("更新挑战记录异常." + message.getMessageBodyJson(), t);
  }

}
