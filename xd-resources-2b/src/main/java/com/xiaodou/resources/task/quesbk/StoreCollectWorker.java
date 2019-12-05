package com.xiaodou.resources.task.quesbk;

import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.queue.annotation.HandlerMessage;
import com.xiaodou.queue.callback.IMQCallBacker;
import com.xiaodou.queue.message.DefaultMessage;
import com.xiaodou.queue.worker.AbstractDefaultWorker;
import com.xiaodou.resources.constant.quesbk.QuesBaseConstant;
import com.xiaodou.resources.model.quesbk.UserStoreRecord;
import com.xiaodou.resources.model.quesbk.UserStoreRecordCollect;
import com.xiaodou.resources.service.quesbk.facade.QuesOperationFacade;
import com.xiaodou.summer.util.SpringWebContextHolder;

@HandlerMessage("StoreCollect")
public class StoreCollectWorker extends AbstractDefaultWorker {

  /** serialVersionUID */
  private static final long serialVersionUID = -773115944073968360L;

  @Override
  public void domain(DefaultMessage message, IMQCallBacker<DefaultMessage> callback) {
    UserStoreRecordCollect record =
        FastJsonUtil.fromJson(message.getMessageBodyJson(), UserStoreRecordCollect.class);
    QuesOperationFacade quesOperationFacade = SpringWebContextHolder.getBean("quesOperationFacade");
    UserStoreRecordCollect oldRecord =
        quesOperationFacade.queryStoreRecordCollect(record.getUserId(), record.getCourseId()
            .toString(), record.getChapterId().toString());
    if (null != oldRecord) {
      updateStoreRecordCollet(quesOperationFacade, record.getUserId(), record.getCourseId()
          .toString(), record.getChapterId().toString());
    } else {
      try {
        quesOperationFacade.insertStoreRecordCollect(record);
      } catch (Exception e1) {
        LoggerUtil.error("插入收藏记录统计信息异常", e1);
        updateStoreRecordCollet(quesOperationFacade, record.getUserId(), record.getCourseId()
            .toString(), record.getChapterId().toString());
      }
    }
  }

  @Override
  public void domain(List<DefaultMessage> message, IMQCallBacker<List<DefaultMessage>> callback) {}

  @Override
  public void onException(Throwable t, List<DefaultMessage> message,
      IMQCallBacker<List<DefaultMessage>> callback) {
    LoggerUtil.error("插入/更新错误记录异常.", t);
  }

  @Override
  public void onException(Throwable t, DefaultMessage message,
      IMQCallBacker<DefaultMessage> callback) {
    LoggerUtil.error("插入/更新错误记录异常.", t);
  }

  public void updateStoreRecordCollet(QuesOperationFacade quesOperationFacade, String userId,
      String courseId, String chapterId) {
    // 更改收藏集合表
    List<String> chapterIdList = Lists.newArrayList();
    chapterIdList.add(chapterId);
    Map<String, Object> cond = Maps.newHashMap();
    Map<String, Object> input = Maps.newHashMap();
    cond.put("userId", userId);
    cond.put("courseId", courseId);
    cond.put("chapterIdList", chapterIdList);
    input.put("input", cond);

    List<UserStoreRecord> selectByUSCList = quesOperationFacade.queryStoreRecordList(input);
    if (null == selectByUSCList || selectByUSCList.size() < 1) return;
    int questionNumber = 0;// 问题数
    int goodQuesCount = 0;// 好题数量
    int unknownQuesCount = 0;// 我不会的问题数量
    int needMemeryQuesCount = 0; // 需要记忆问题数量
    // 1 好题(默认)    2 我不会的    3 需要记忆   4取消收藏
    for (UserStoreRecord userStoreRecord : selectByUSCList) {
      if (QuesBaseConstant.QUES_STORE_RECORD_STATUS_CANCELQUES.equals(userStoreRecord
          .getStoreStatus())) continue;
      ++questionNumber;
      switch (userStoreRecord.getStoreStatus()) {
        case QuesBaseConstant.QUES_STORE_RECORD_STATUS_GOODQUES:
          ++goodQuesCount;
          break;
        case QuesBaseConstant.QUES_STORE_RECORD_STATUS_UNKNOWQUES:
          ++unknownQuesCount;
          break;
        case QuesBaseConstant.QUES_STORE_RECORD_STATUS_NEEDMEMERYQUES:
          ++needMemeryQuesCount;
          break;
        default:
          break;
      }
    }
    // 2、修改
    UserStoreRecordCollect userStoreRecordCollect =
        quesOperationFacade.queryStoreRecordCollect(userId, courseId, chapterId);
    if (null == userStoreRecordCollect) {
      UserStoreRecordCollect recordCollect = new UserStoreRecordCollect();
      recordCollect.setUserId(userId);
      recordCollect.setCourseId(Long.valueOf(courseId));
      recordCollect.setChapterId(Long.valueOf(chapterId));
      quesOperationFacade.insertStoreRecordCollect(recordCollect);
    }
    userStoreRecordCollect.setChapterId(Long.valueOf(chapterId));
    userStoreRecordCollect.setCourseId(Long.valueOf(courseId));
    userStoreRecordCollect.setUserId(userId);
    userStoreRecordCollect.setQuestionNumber(questionNumber);
    userStoreRecordCollect.setGoodQuesCount(goodQuesCount);
    userStoreRecordCollect.setUnknownQuesCount(unknownQuesCount);
    userStoreRecordCollect.setNeedMemeryQuesCount(needMemeryQuesCount);
    quesOperationFacade.updateStoreRecordCollet(userStoreRecordCollect);
  }
}
