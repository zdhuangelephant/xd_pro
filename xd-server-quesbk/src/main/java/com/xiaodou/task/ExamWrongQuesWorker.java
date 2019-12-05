package com.xiaodou.task;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.dao.DuplicateKeyException;

import com.alibaba.fastjson.TypeReference;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.constant.QuesBaseConstant;
import com.xiaodou.domain.behavior.UserWrongRecord;
import com.xiaodou.domain.behavior.UserWrongRecordCollect;
import com.xiaodou.domain.product.QuesbkQuesStatistics;
import com.xiaodou.domain.product.QuesbkQuesStatistics.AnswerDetail;
import com.xiaodou.manager.facade.QuesOperationFacade;
import com.xiaodou.mission.engine.event.EliminateWrongQuesEvent;
import com.xiaodou.mission.engine.event.EventBuilder;
import com.xiaodou.queue.annotation.HandlerMessage;
import com.xiaodou.queue.callback.IMQCallBacker;
import com.xiaodou.queue.message.DefaultMessage;
import com.xiaodou.queue.worker.AbstractDefaultWorker;
import com.xiaodou.summer.dao.pagination.Page;
import com.xiaodou.summer.util.SpringWebContextHolder;

@HandlerMessage("ExamWrongRecord")
public class ExamWrongQuesWorker extends AbstractDefaultWorker {

  QuesOperationFacade quesOperationFacade;
  /** UID **/
  private static final long serialVersionUID = 5205304582372387601L;

  @Override
  public void domain(DefaultMessage message, IMQCallBacker<DefaultMessage> callback) {
    UserWrongRecord wrongRecord =
        FastJsonUtil.fromJson(message.getMessageBodyJson(), UserWrongRecord.class);
    if (null == quesOperationFacade)
      quesOperationFacade = SpringWebContextHolder.getBean("quesOperationFacade");
    try {
      // 记录或更新错题记录
      UserWrongRecord oldRecord = getWrongRecord(quesOperationFacade, wrongRecord);
      // 如果错题记录存在,更新信息,否则,新插入一条错题信息
      if (null == oldRecord) {
        insertWrongRecord(wrongRecord, oldRecord);
      } else {
        updateWrongRecord(wrongRecord, oldRecord);
      }
      UserWrongRecordCollect wrongCollect =
          quesOperationFacade.queryWrongRecordCollect(wrongRecord.getUserId(), wrongRecord
              .getCourseId().toString(), wrongRecord.getChapterId().toString());
      recordWrongCollect(quesOperationFacade, wrongRecord, wrongCollect);
      // QuesExamCache.incrExamRecord(wrongRecord.getTag());
      // 问题分析信息若存在,则更新原信息,否则,插入一条新信息
      QuesbkQuesStatistics statistic =
          quesOperationFacade.queryQuesStatistic(wrongRecord.getQuestionId().toString(),
              wrongRecord.getCourseId().toString());
      if (null == statistic) {
        insertStatistic(wrongRecord, statistic);
      } else {
        updateStatistic(wrongRecord, statistic);
      }
    } catch (Exception e1) {
      if (!(e1 instanceof DuplicateKeyException)) LoggerUtil.error("插入/更新统计信息异常", e1);
    }
  }

  private void updateStatistic(UserWrongRecord wrongRecord, QuesbkQuesStatistics statistic) {
    List<AnswerDetail> answerDetailLst = Lists.newArrayList();
    boolean finded = false;
    if (StringUtils.isNotBlank(statistic.getAnswerDetail())) {
      answerDetailLst =
          FastJsonUtil.fromJsons(statistic.getAnswerDetail(),
              new TypeReference<List<AnswerDetail>>() {});
      for (AnswerDetail ad : answerDetailLst) {
        if (ad.getId().equals(wrongRecord.getWrongAnswer())) {
          ad.addTimes();
          finded = true;
          break;
        }
      }
    }
    if (!finded) {
      AnswerDetail ad = new AnswerDetail();
      ad.setId(wrongRecord.getWrongAnswer());
      ad.setTimes(1);
      answerDetailLst.add(ad);
    }
    statistic.setAnswerDetail(FastJsonUtil.toJson(answerDetailLst));
    if (wrongRecord.getWrong()) {
      statistic.addWrongTime();
    } else {
      statistic.addTimes();
    }
    quesOperationFacade.updateQuesStatistic(statistic);
  }

  private void insertStatistic(UserWrongRecord wrongRecord, QuesbkQuesStatistics statistic) {
    try {
      statistic = new QuesbkQuesStatistics();
      statistic.setQuestionId(wrongRecord.getQuestionId());
      statistic.setCourseId(wrongRecord.getCourseId());
      List<AnswerDetail> answerDetailLst = Lists.newArrayList();
      AnswerDetail ad = new AnswerDetail();
      ad.setId(wrongRecord.getWrongAnswer());
      ad.setTimes(1);
      answerDetailLst.add(ad);
      statistic.setAnswerDetail(FastJsonUtil.toJson(answerDetailLst));
      if (wrongRecord.getWrong()) {
        statistic.addWrongTime();
      } else {
        statistic.addTimes();
      }
      quesOperationFacade.insertQuesStatistic(statistic);
    } catch (Exception e) {
      if (!(e instanceof DuplicateKeyException))
        LoggerUtil.error("插入错题分析记录异常", e);
      else {
        statistic =
            quesOperationFacade.queryQuesStatistic(wrongRecord.getQuestionId().toString(),
                wrongRecord.getCourseId().toString());
        updateStatistic(wrongRecord, statistic);
      }
    }
  }

  private void insertWrongRecord(UserWrongRecord wrongRecord, UserWrongRecord oldRecord) {
    try {
      quesOperationFacade.insertWrongRecord(wrongRecord);
    } catch (Exception e) {
      if (!(e instanceof DuplicateKeyException))
        LoggerUtil.error("插入错题记录异常", e);
      else {
        oldRecord = getWrongRecord(quesOperationFacade, wrongRecord);
        updateWrongRecord(wrongRecord, oldRecord);
      }
    }
  }

  private void updateWrongRecord(UserWrongRecord wrongRecord, UserWrongRecord oldRecord) {
    oldRecord.setModule(wrongRecord.getModule());
    oldRecord.setMajorId(wrongRecord.getMajorId());
    if (wrongRecord.getWrong()) {
      oldRecord.setWrongStatus(QuesBaseConstant.QUES_WRONG_RECORD_STATUS_UNCONTROL);
      quesOperationFacade.updateWrongTimes(oldRecord);
    } else {
      if (QuesBaseConstant.QUES_WRONG_RECORD_STATUS_UNCONTROL.equals(oldRecord.getWrongStatus())) {
        oldRecord.setWrongStatus(QuesBaseConstant.QUES_WRONG_RECORD_STATUS_NEEDEXAM);
      } else if (QuesBaseConstant.QUES_WRONG_RECORD_STATUS_NEEDEXAM.equals(oldRecord
          .getWrongStatus())) {
        oldRecord.setWrongStatus(QuesBaseConstant.QUES_WRONG_RECORD_STATUS_RESOLVED);
        sendWrongRecordEvent(oldRecord);
      }
      quesOperationFacade.updateRightTimes(oldRecord);
    }
  }

  private void sendWrongRecordEvent(UserWrongRecord oldRecord) {
    EliminateWrongQuesEvent event = EventBuilder.buildEliminateWrongQuesEvent();
    event.setUserId(oldRecord.getUserId());
    event.setModule(oldRecord.getModule());
    event.setMajorId(oldRecord.getMajorId());
    event.setCourseId(oldRecord.getCourseId().toString());
    event.setCount(1);
    event.send();
  }

  private void recordWrongCollect(QuesOperationFacade quesOperationFacade,
      UserWrongRecord wrongRecord, UserWrongRecordCollect wrongCollect) {
    try {
      if (null == wrongCollect) {
        try {
          UserWrongRecordCollect entity = new UserWrongRecordCollect();
          entity.setUserId(wrongRecord.getUserId());
          entity.setCourseId(wrongRecord.getCourseId());
          entity.setChapterId(wrongRecord.getChapterId());
          quesOperationFacade.insertWrongRecordCollect(entity);
        } catch (Exception e1) {
          if (!(e1 instanceof DuplicateKeyException)) LoggerUtil.error("插入错题记录统计信息异常", e1);
        }
      }
      changeWrongRecordCollect(quesOperationFacade, wrongRecord.getUserId(), wrongRecord
          .getCourseId().toString(), wrongRecord.getChapterId().toString());
    } catch (Exception e1) {
      LoggerUtil.error("更新错题记录统计信息异常", e1);
    }
  }

  private UserWrongRecord getWrongRecord(QuesOperationFacade quesOperationFacade,
      UserWrongRecord record) {
    Map<String, Object> cond = Maps.newHashMap();
    cond.put("userId", record.getUserId());
    cond.put("courseId", record.getCourseId());
    cond.put("chapterId", record.getChapterId());
    cond.put("questionId", record.getQuestionId());
    Page<UserWrongRecord> userWrongRecordList =
        quesOperationFacade.queryAbstractWrongRecordList(cond, null);
    if (null != userWrongRecordList.getResult() && userWrongRecordList.getResult().size() > 0) {
      return userWrongRecordList.getResult().get(0);
    }
    return null;
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

  public void changeWrongRecordCollect(QuesOperationFacade quesOperationFacade, String userId,
      String courseId, String chapterId) {
    // 更改错题集合表
    // 1、查询
    Map<String, Object> _cond = new HashMap<String, Object>();
    _cond.put("userId", userId);
    _cond.put("courseId", courseId);
    _cond.put("chapterId", chapterId);
    _cond.put("wrongTimesLower", 0);// 错题数至少要大于0才属于错题
    Page<UserWrongRecord> pageList = quesOperationFacade.queryWrongRecordList(_cond, null);
    if (null == pageList) return;
    List<UserWrongRecord> userWrongRecordList = pageList.getResult();
    if (null == userWrongRecordList || userWrongRecordList.size() < 1) return;
    int questionNumber = 0;// 问题数
    int uncatchQuesCount = 0;// 未掌握问题数量
    int catchingQuesCount = 0;// 待强化问题数量
    int catchedQuesCount = 0;// 已消灭问题数量
    // 错题状态 1 未掌握 2 待强化 3 已消灭 4 已移除(默认)
    for (UserWrongRecord userWrongRecord : userWrongRecordList) {
      if (QuesBaseConstant.QUES_WRONG_RECORD_STATUS_REMOVED
          .equals(userWrongRecord.getWrongStatus())) continue;
      ++questionNumber;
      switch (userWrongRecord.getWrongStatus()) {
        case QuesBaseConstant.QUES_WRONG_RECORD_STATUS_UNCONTROL:
          ++uncatchQuesCount;
          break;
        case QuesBaseConstant.QUES_WRONG_RECORD_STATUS_NEEDEXAM:
          ++catchingQuesCount;
          break;
        case QuesBaseConstant.QUES_WRONG_RECORD_STATUS_RESOLVED:
          ++catchedQuesCount;
          break;
        default:
          break;
      }
    }
    // 2、修改
    UserWrongRecordCollect recordCollect = new UserWrongRecordCollect();
    recordCollect.setUserId(userId);
    recordCollect.setCourseId(courseId);
    recordCollect.setChapterId(Long.valueOf(chapterId));
    recordCollect.setQuestionNumber(questionNumber);
    recordCollect.setUncatchQuesCount(uncatchQuesCount);
    recordCollect.setCatchingQuesCount(catchingQuesCount);
    recordCollect.setCatchedQuesCount(catchedQuesCount);
    int updateCount = quesOperationFacade.updateWrongRecordCollect(recordCollect);
    if (updateCount != 1) return;
  }
}
