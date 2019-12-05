package com.xiaodou.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.aop.framework.AopContext;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.TypeReference;
import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.xiaodou.common.util.ConfigProp;
import com.xiaodou.common.util.MathUtil;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.common.util.log.model.AlarmEntityImpl;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.constant.QuesBaseConstant;
import com.xiaodou.constant.ResultType;
import com.xiaodou.constant.ScoreConstant;
import com.xiaodou.domain.CourseKeyword;
import com.xiaodou.domain.QuesbkAlarmEntity;
import com.xiaodou.domain.behavior.UserChapterRecord;
import com.xiaodou.domain.behavior.UserExamRecord;
import com.xiaodou.domain.behavior.UserExamRecordDetail;
import com.xiaodou.domain.behavior.UserExamTotal;
import com.xiaodou.domain.behavior.UserFinalExamRecord;
import com.xiaodou.domain.behavior.UserScorePointRecord;
import com.xiaodou.domain.behavior.UserWrongRecord;
import com.xiaodou.domain.behavior.UserWrongRecordCollect;
import com.xiaodou.domain.product.ChallengeRecord;
import com.xiaodou.domain.product.CourseProduct;
import com.xiaodou.domain.product.CourseProductItem;
import com.xiaodou.domain.product.FinalExamModel;
import com.xiaodou.domain.product.ProductScorePointRule;
import com.xiaodou.domain.product.QuesbkAudioLog;
import com.xiaodou.domain.product.QuesbkExamPaper;
import com.xiaodou.domain.product.QuesbkExamRule;
import com.xiaodou.domain.product.QuesbkQues;
import com.xiaodou.domain.product.QuesbkQues.KeyPoint;
import com.xiaodou.domain.product.QuesbkQuesType;
import com.xiaodou.domain.product.RaiseWrongQues;
import com.xiaodou.domain.product.RedBonus;
import com.xiaodou.domain.product.RedBonus.CourseBonus;
import com.xiaodou.enums.ExamSubmitStatus;
import com.xiaodou.enums.ExamType;
import com.xiaodou.jmsg.client.RabbitMQSender;
import com.xiaodou.mission.engine.event.DailyPracticeEvent;
import com.xiaodou.mission.engine.event.EventBuilder;
import com.xiaodou.mission.engine.event.LeakFillingEvent;
import com.xiaodou.mission.engine.event.RandomPkEvent;
import com.xiaodou.mission.engine.event.TollgateEvent;
import com.xiaodou.queue.client.AbstractMQClient.MessageBox;
import com.xiaodou.service.QueueService.Message;
import com.xiaodou.summer.dao.pagination.Page;
import com.xiaodou.userCenter.request.AddCreditRequest;
import com.xiaodou.util.QuesCaculateUtil;
import com.xiaodou.util.QuesCaculateUtil.IQuesCaculateTask;
import com.xiaodou.util.QuesbkUtil;
import com.xiaodou.util.StaticInfoProp;
import com.xiaodou.vo.alarm.ExamDetailAlarm;
import com.xiaodou.vo.alarm.SubmitExamAlarm;
import com.xiaodou.vo.mq.AddCreditMessage;
import com.xiaodou.vo.mq.UserLearnAchieveMessage;
import com.xiaodou.vo.mq.UserLearnAchieveMessage.UserLearnAchieveVo;
import com.xiaodou.vo.request.BaseDetailPojo;
import com.xiaodou.vo.request.BaseListPojo;
import com.xiaodou.vo.request.CourseStatisticsPojo;
import com.xiaodou.vo.request.ExamDetailPojo;
import com.xiaodou.vo.request.ExamDetailPojo_v1_3_8;
import com.xiaodou.vo.request.ExamHisList;
import com.xiaodou.vo.request.ExamResultPojo;
import com.xiaodou.vo.request.ExamResultPojo.AnswerItem;
import com.xiaodou.vo.request.PerformanceDetailPojo;
import com.xiaodou.vo.request.QuesAudioLogDetailPojo;
import com.xiaodou.vo.request.QuesAudioLogListPojo;
import com.xiaodou.vo.request.QuesAudioLogPojo;
import com.xiaodou.vo.request.QuesBasePojo;
import com.xiaodou.vo.request.RaiseWrongQuesRequest;
import com.xiaodou.vo.request.RealExamListPojo;
import com.xiaodou.vo.request.ScorePointRulePojo;
import com.xiaodou.vo.request.UnWrongQuesRequest;
import com.xiaodou.vo.request.WrongQuesDetailPojo;
import com.xiaodou.vo.request.WrongQuesListPojo;
import com.xiaodou.vo.response.BaseDetailResponse;
import com.xiaodou.vo.response.BaseListResponse;
import com.xiaodou.vo.response.BaseResponse;
import com.xiaodou.vo.response.CourseScore;
import com.xiaodou.vo.response.CourseScore.ItemScore;
import com.xiaodou.vo.response.CourseStatisticsResponse;
import com.xiaodou.vo.response.ExamDetailResponse;
import com.xiaodou.vo.response.ExamDetailResponse.Question;
import com.xiaodou.vo.response.ExamHisListResponse;
import com.xiaodou.vo.response.PerformanceDetailResponse;
import com.xiaodou.vo.response.ProductScorePointRuleResponse;
import com.xiaodou.vo.response.QuesAudioLogCountResponse;
import com.xiaodou.vo.response.QuesAudioLogListResponse;
import com.xiaodou.vo.response.QuesAudioLogResponse;
import com.xiaodou.vo.response.QuesbkAudioLogVo;
import com.xiaodou.vo.response.RealExamListResponse;
import com.xiaodou.vo.response.SubmitResultResponse;
import com.xiaodou.vo.response.WrongQuesDetailResponse;
import com.xiaodou.vo.response.WrongQuesListResponse;
import com.xiaodou.vo.response.WrongQuesListResponse.ChapterInfo;
import com.xiaodou.vo.task.InitCreditRedBonus;

/**
 * @name @see com.xiaodou.service.QuesBaseServices.java
 * @CopyRright (c) 2015 by XiaoDou NetWork Technology
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2015年8月7日
 * @description 题库模块Service
 * @version 1.0
 */
@Service("quesBaseServices")
public class QuesBaseServices extends AbstractQuesService {

  @Resource
  QuesChallengeService quesChallengeService;

  /**
   * 1 题库列表 · 说明 获取题库列表用于展示。 · url /quesbk/base_list
   * 
   * @param pojo
   */
  public BaseListResponse baseList(BaseListPojo pojo) {
    BaseListResponse response = new BaseListResponse(ResultType.SUCCESS);
    List<CourseProduct> productList =
        quesOperationFacade.queryProductList(pojo.getModule(), pojo.getTypeCode());
    for (CourseProduct product : productList) {
      BaseListResponse.CourseInfo courseInfo = new BaseListResponse.CourseInfo(product);
      response.getCourseInfo().add(courseInfo);
    }
    return response;
  }

  /**
   * 2 题库详情 · 说明 点击题库进入首页，展示题库详情。 · url /quesbk/base_detail
   * 
   * @param pojo
   */
  public BaseDetailResponse baseDetail(BaseDetailPojo pojo) {
    if (!checkCourseId(pojo, pojo.getCourseId()))
      return new BaseDetailResponse(ResultType.UNVALIDCOURSEID);
    BaseDetailResponse response = new BaseDetailResponse(ResultType.SUCCESS);
    UserExamTotal myExamTotal =
        quesOperationFacade.queryExamTotal(pojo.getUid(), pojo.getCourseId());
    Integer baseJoinNum = StaticInfoProp.getInt("xiaodou.quesbase.baseJoinNum");
    Integer baseRank = StaticInfoProp.getInt("xiaodou.quesbase.baseRank");
    Integer baseRightRank = StaticInfoProp.getInt("xiaodou.quesbase.baseRightRank");
    Integer allExamCount = quesOperationFacade.countExamTotal(pojo.getCourseId()) + baseJoinNum;
    BaseDetailResponse.ExamDetail examDetail = new BaseDetailResponse.ExamDetail();
    response.setExamTotal(examDetail);
    if (null != myExamTotal) {
      // 设置总参与人数#总记录中该字段存储的为总参与人数
      examDetail.setTotalMembers(allExamCount.toString());
      // 我的答题数
      examDetail.setMyQues(myExamTotal.getTotalQues().toString());
      // 我的答题排名
      examDetail.setMyQuesRank(myExamTotal.getTotalRank() <= 0
          ? "0"
          : (myExamTotal.getTotalRank() + baseRank) + "");
      if (null == myExamTotal.getTotalQues() || 0 == myExamTotal.getTotalQues())
        examDetail.setMyRightPercent("0.00");
      else
        examDetail.setMyRightPercent(myExamTotal.getRightPercent());
      // 我的正确答题数
      examDetail.setMyRightQues(myExamTotal.getRightQues().toString());
      // 我的正确答题排名
      examDetail.setMyRightRank(myExamTotal.getRightRank() <= 0
          ? "0"
          : (myExamTotal.getRightRank() + baseRightRank) + "");
    }
    return response;
  }

  public ExamDetailResponse examDetail0_v1_3_8(ExamDetailPojo_v1_3_8 pojo) throws Exception {
    ExamDetailResponse response = new ExamDetailResponse(ResultType.SUCCESS);
    Map<Long, AnswerItem> opponentsAnswerMap = null;
    QuesbkExamPaper paper = null;
    List<Object> quesList = Lists.newArrayList();
    ExamType examType = ExamType.getByCode(pojo.getExamType());
    Integer credit = 0, deductCredit = 0;
    switch (examType) {
      case REAL_PAPER: // 真题练习
        paper = quesOperationFacade.queryExamPaper(pojo.getPaperId());
        break;
      case FRIEND_CHALLENGE_PAPER: // 好友挑战练习
        ChallengeRecord friendChallengeRecord =
            quesOperationFacade.queryChallengeRecord(pojo.getRecordId());
        if (null == friendChallengeRecord) return new ExamDetailResponse(ResultType.MISSINGRECORD);
        paper = quesOperationFacade.queryExamPaper(friendChallengeRecord.getPaperId());
        break;
      case RANDOM_CHALLENGE_PAPER: // 随机挑战练习
        ChallengeRecord randomChallengeRecord =
            quesOperationFacade.queryChallengeRecord(pojo.getRecordId());
        if (null == randomChallengeRecord) return new ExamDetailResponse(ResultType.MISSINGRECORD);
        paper = quesOperationFacade.queryExamPaper(randomChallengeRecord.getPaperId());
        String opponentsId =
            randomChallengeRecord.getChallengerUid().equals(pojo.getUid()) ? randomChallengeRecord
                .getBeChallengerUid() : randomChallengeRecord.getChallengerUid();
        UserExamRecord opponentsRecord =
            quesOperationFacade.queryExamRecord(randomChallengeRecord.getPaperId(), opponentsId);
        opponentsAnswerMap = Maps.newHashMap();
        if (null != opponentsRecord && StringUtils.isNotBlank(opponentsRecord.getQuestions())) {
          for (AnswerItem myAnswer : FastJsonUtil.fromJsons(opponentsRecord.getQuestions(),
              new TypeReference<List<AnswerItem>>() {})) {
            opponentsAnswerMap.put(myAnswer.getQuesId(), myAnswer);
          }
        }
        break;
      case FINAL_EXAM_PAPER: // 期末测试
        if (finalExamIsLock(pojo.getUid(), pojo.getCourseId())) {
          return new ExamDetailResponse(ResultType.ChapterFail);
        }
        // 扣分
        if (ExamType.FINAL_EXAM_PAPER.getCode().equals(pojo.getExamType())
            && (StringUtils.isBlank(pojo.getChapterId()) || pojo.getChapterId().equals("-1"))
            && pojo.getItemId() != null) {
          Map<String, Object> input = Maps.newHashMap();
          input.put("userId", pojo.getUid());
          input.put("module", pojo.getModule());
          input.put("typeCode", pojo.getTypeCode());
          input.put("courseId", pojo.getCourseId());
          input.put("itemId", pojo.getItemId());
          List<UserChapterRecord> chapterRecordList =
              quesOperationFacade.queryUserChapterRecord(input);
          if (null != chapterRecordList && chapterRecordList.size() > 0) {
            if (pojo.getUserCredit() < ScoreConstant.FINAL_SCORE) {
              return new ExamDetailResponse(ResultType.CREDITNOTENOUGH);
            }
            deductCredit = ScoreConstant.FINAL_SCORE * -1;
            credit = pojo.getUserCredit() + deductCredit;
          }
        }
        UserFinalExamRecord finalExamRecord =
            quesOperationFacade.selectByUidAndExamId(pojo.getItemId(), pojo.getUid());
        boolean hasPaper = false;
        if (null != finalExamRecord) {
          paper = quesOperationFacade.queryExamPaper(finalExamRecord.getPaperNo());
          if (paper != null) {
            hasPaper = true;
          } else {
            quesOperationFacade.deleteUserFinalExam(finalExamRecord.getId().toString());
          }
        }
        if (!hasPaper) {
          paper =
              new QuesbkExamPaper(ExamType.FINAL_EXAM_PAPER, pojo.getCourseId(), pojo.getItemId());
          UserFinalExamRecord record = new UserFinalExamRecord();
          record.setId(UUID.randomUUID().toString());
          record.setPaperNo(paper.getId());
          record.setFinalExamId(pojo.getItemId());
          record.setUserId(pojo.getUid());
          quesOperationFacade.insertUserFinalExam(record);
        }
        break;
      default: // 其它练习
        if (pojo.getExamType().equals(ExamType.ITEM_BREAKTHROUGH_PAPER.getCode())
            && (StringUtils.isBlank(pojo.getItemId()) || pojo.getItemId().equals("-1") || pojo
                .getItemId().equals(pojo.getChapterId()))) {
          if (chapterIsLock(pojo.getUid(), pojo.getCourseId(), pojo.getChapterId(), null)) {
            return new ExamDetailResponse(ResultType.ChapterFail);
          } else {
            // 扣分
            if (ExamType.ITEM_BREAKTHROUGH_PAPER.getCode().equals(pojo.getExamType())) {
              Map<String, Object> input = Maps.newHashMap();
              input.put("userId", pojo.getUid());
              input.put("module", pojo.getModule());
              input.put("typeCode", pojo.getTypeCode());
              input.put("courseId", pojo.getCourseId());
              input.put("itemId", pojo.getChapterId());
              input.put("chapterId", pojo.getChapterId());
              List<UserChapterRecord> chapterRecordList =
                  quesOperationFacade.queryUserChapterRecord(input);
              if (null != chapterRecordList && chapterRecordList.size() > 0) {
                if (pojo.getUserCredit() < ScoreConstant.CHAPTER_SCORE) {
                  return new ExamDetailResponse(ResultType.CREDITNOTENOUGH);
                }
                deductCredit = ScoreConstant.CHAPTER_SCORE * -1;
                credit = pojo.getUserCredit() + deductCredit;
              }
            }
          }
        }
        paper =
            new QuesbkExamPaper(examType, pojo.getCourseId(), pojo.getChapterId(), pojo.getItemId());
        break;
    }
    quesList = quesOperationFacade.queryExamQuestionIdList_v1_3_8(pojo, paper);
    if (quesList == null || quesList.size() == 0) {
      // 无法出题,报警
      LoggerUtil.alarmInfo(new ExamDetailAlarm(ResultType.UNVALIDQUESINFO4EXAMTYPE.getMsg()));
      return new ExamDetailResponse(ResultType.UNVALIDQUESINFO4EXAMTYPE);
    }
    if (pojo.getExamType().equals(ExamType.FINAL_EXAM_PAPER.getCode())) {
      Collections.shuffle(quesList);
    }
    if (StringUtils.isBlank(paper.getQuesIds())) {
      setQuesIdLst(paper, quesList);
      quesOperationFacade.insertExamPaper(paper);
    }
    ExamDetailResponse.ExamDetail examDetail = new ExamDetailResponse.ExamDetail(paper);
    examDetail.setCredit(credit);
    List<Question> questionList = packageQuestionList(quesList, null, opponentsAnswerMap);
    // packageQuestionList(quesList, null, opponentsAnswerMap, new CheckStoreRule(pojo.getUid(),
    // pojo.getCourseId()));
    if (StringUtils.isAllNotBlank(pojo.getCourseId(), pojo.getChapterId(), pojo.getItemId())
        && !pojo.getChapterId().equals("-1")) {
      nextItem(examDetail, pojo.getCourseId(), pojo.getChapterId(), pojo.getItemId(), pojo.getUid());
    } else if (StringUtils.isAllNotBlank(pojo.getCourseId(), pojo.getChapterId())
        && !pojo.getChapterId().equals("-1")) {
      nextChapter(examDetail, pojo.getCourseId(), pojo.getChapterId(), pojo.getUid());
    } else if (ExamType.FINAL_EXAM_PAPER.getCode().equals(pojo.getExamType())
        && (StringUtils.isBlank(pojo.getChapterId()) || pojo.getChapterId().equals("-1"))
        && pojo.getItemId() != null) {
      FinalExamModel f = quesOperationFacade.selectFinalExamById(Long.valueOf(pojo.getItemId()));
      if (f != null) {
        examDetail.setItemIndex("");
        examDetail.setItemName(f.getExamName());
      };
    }
    examDetail.setQuestionList(questionList);
    response.setExamDetail(examDetail);
    addUserCredit(pojo, deductCredit, ExamType.getByCode(pojo.getExamType()));
    return response;
  }

  public ExamDetailResponse examDetail0(ExamDetailPojo pojo) throws Exception {
    ExamDetailResponse response = new ExamDetailResponse(ResultType.SUCCESS);
    Map<Long, AnswerItem> opponentsAnswerMap = null;
    QuesbkExamPaper paper = null;
    List<Object> quesList = Lists.newArrayList();
    ExamType examType = ExamType.getByCode(pojo.getExamType());
    Integer credit = 0, deductCredit = 0;
    switch (examType) {
      case REAL_PAPER: // 真题练习
        paper = quesOperationFacade.queryExamPaper(pojo.getPaperId());
        break;
      case RANDOM_CHALLENGE_PAPER: // 随机挑战练习
      case FRIEND_CHALLENGE_PAPER: // 好友挑战练习 机器人出题需要使用此练习类型,勿删
        ChallengeRecord randomChallengeRecord =
            quesOperationFacade.queryChallengeRecord(pojo.getRecordId());
        if (null == randomChallengeRecord) return new ExamDetailResponse(ResultType.MISSINGRECORD);
        paper = quesOperationFacade.queryExamPaper(randomChallengeRecord.getPaperId());
        String opponentsId =
            randomChallengeRecord.getChallengerUid().equals(pojo.getUid()) ? randomChallengeRecord
                .getBeChallengerUid() : randomChallengeRecord.getChallengerUid();
        UserExamRecord opponentsRecord =
            quesOperationFacade.queryExamRecord(randomChallengeRecord.getPaperId(), opponentsId);
        opponentsAnswerMap = Maps.newHashMap();
        if (null != opponentsRecord && StringUtils.isNotBlank(opponentsRecord.getQuestions())) {
          for (AnswerItem myAnswer : FastJsonUtil.fromJsons(opponentsRecord.getQuestions(),
              new TypeReference<List<AnswerItem>>() {})) {
            opponentsAnswerMap.put(myAnswer.getQuesId(), myAnswer);
          }
        }
        break;
      case FINAL_EXAM_PAPER: // 期末测试
        if (finalExamIsLock(pojo.getUid(), pojo.getCourseId())) {
          return new ExamDetailResponse(ResultType.ChapterFail);
        }
        // 扣分
        if (ExamType.FINAL_EXAM_PAPER.getCode().equals(pojo.getExamType())
            && (StringUtils.isBlank(pojo.getChapterId()) || pojo.getChapterId().equals("-1"))
            && pojo.getItemId() != null) {
          Map<String, Object> input = Maps.newHashMap();
          input.put("userId", pojo.getUid());
          input.put("module", pojo.getModule());
          input.put("typeCode", pojo.getTypeCode());
          input.put("courseId", pojo.getCourseId());
          input.put("itemId", pojo.getItemId());
          List<UserChapterRecord> chapterRecordList =
              quesOperationFacade.queryUserChapterRecord(input);
          if (null != chapterRecordList && chapterRecordList.size() > 0) {
            if (pojo.getUserCredit() < ScoreConstant.FINAL_SCORE) {
              return new ExamDetailResponse(ResultType.CREDITNOTENOUGH);
            }
            deductCredit = ScoreConstant.FINAL_SCORE * -1;
            credit = pojo.getUserCredit() + deductCredit;
          }
        }
        UserFinalExamRecord finalExamRecord =
            quesOperationFacade.selectByUidAndExamId(pojo.getItemId(), pojo.getUid());
        boolean hasPaper = false;
        if (null != finalExamRecord) {
          paper = quesOperationFacade.queryExamPaper(finalExamRecord.getPaperNo());
          if (paper != null) {
            hasPaper = true;
          } else {
            quesOperationFacade.deleteUserFinalExam(finalExamRecord.getId().toString());
          }
        }
        if (!hasPaper) {
          paper =
              new QuesbkExamPaper(ExamType.FINAL_EXAM_PAPER, pojo.getCourseId(), pojo.getItemId());
          UserFinalExamRecord record = new UserFinalExamRecord();
          record.setId(UUID.randomUUID().toString());
          record.setPaperNo(paper.getId());
          record.setFinalExamId(pojo.getItemId());
          record.setUserId(pojo.getUid());
          quesOperationFacade.insertUserFinalExam(record);
        }
        break;
      default: // 其它练习
        if (pojo.getExamType().equals(ExamType.CHAPTER_BREAKTHROUGH_PAPER.getCode())
            || (pojo.getExamType().equals(ExamType.ITEM_BREAKTHROUGH_PAPER.getCode()) && (StringUtils
                .isBlank(pojo.getItemId()) || pojo.getItemId().equals("-1") || pojo.getItemId()
                .equals(pojo.getChapterId())))) {
          if (chapterIsLock(pojo.getUid(), pojo.getCourseId(), pojo.getChapterId(), null)) {
            return new ExamDetailResponse(ResultType.ChapterFail);
          } else {
            // 扣分
            if (ExamType.CHAPTER_BREAKTHROUGH_PAPER.getCode().equals(pojo.getExamType())
                || ExamType.ITEM_BREAKTHROUGH_PAPER.getCode().equals(pojo.getExamType())) {
              Map<String, Object> input = Maps.newHashMap();
              input.put("userId", pojo.getUid());
              input.put("module", pojo.getModule());
              input.put("typeCode", pojo.getTypeCode());
              input.put("courseId", pojo.getCourseId());
              input.put("itemId", pojo.getChapterId());
              input.put("chapterId", pojo.getChapterId());
              List<UserChapterRecord> chapterRecordList =
                  quesOperationFacade.queryUserChapterRecord(input);
              if (null != chapterRecordList && chapterRecordList.size() > 0) {
                if (pojo.getUserCredit() < ScoreConstant.CHAPTER_SCORE) {
                  return new ExamDetailResponse(ResultType.CREDITNOTENOUGH);
                }
                deductCredit = ScoreConstant.CHAPTER_SCORE * -1;
                credit = pojo.getUserCredit() + deductCredit;
              }
            }
          }
        }
        paper =
            new QuesbkExamPaper(examType, pojo.getCourseId(), pojo.getChapterId(), pojo.getItemId());
        break;
    }
    List<QuesbkExamRule> ruleList =
        quesOperationFacade.queryExamRuleList(pojo.getCourseId(), pojo.getExamType());
    if (ruleList == null || ruleList.size() == 0) {
      // 未找到命题蓝图,报警
      LoggerUtil.alarmInfo(new ExamDetailAlarm(ResultType.MISSINGEXAMRULE.getMsg()));
      return new ExamDetailResponse(ResultType.MISSINGEXAMRULE);
    }
    quesList = quesOperationFacade.queryExamQuestionIdList(pojo, ruleList, paper);
    if (quesList == null || quesList.size() == 0) {
      // 无法出题,报警
      LoggerUtil.alarmInfo(new ExamDetailAlarm(ResultType.UNVALIDQUESINFO4EXAMTYPE.getMsg()));
      return new ExamDetailResponse(ResultType.UNVALIDQUESINFO4EXAMTYPE);
    }
    if (pojo.getExamType().equals(ExamType.FINAL_EXAM_PAPER.getCode())) {
      Collections.shuffle(quesList);
    }
    if (StringUtils.isBlank(paper.getQuesIds())) {
      setQuesIdLst(paper, quesList);
      quesOperationFacade.insertExamPaper(paper);
    }
    ExamDetailResponse.ExamDetail examDetail = new ExamDetailResponse.ExamDetail(paper);
    examDetail.setCredit(credit);
    List<Question> questionList = packageQuestionList(quesList, null, opponentsAnswerMap);
    // packageQuestionList(quesList, null, opponentsAnswerMap, new CheckStoreRule(pojo.getUid(),
    // pojo.getCourseId()));
    if (StringUtils.isAllNotBlank(pojo.getCourseId(), pojo.getChapterId(), pojo.getItemId())
        && !pojo.getChapterId().equals("-1")) {
      nextItem(examDetail, pojo.getCourseId(), pojo.getChapterId(), pojo.getItemId(), pojo.getUid());
    } else if (StringUtils.isAllNotBlank(pojo.getCourseId(), pojo.getChapterId())
        && !pojo.getChapterId().equals("-1")) {
      nextChapter(examDetail, pojo.getCourseId(), pojo.getChapterId(), pojo.getUid());
    } else if (ExamType.FINAL_EXAM_PAPER.getCode().equals(pojo.getExamType())
        && (StringUtils.isBlank(pojo.getChapterId()) || pojo.getChapterId().equals("-1"))
        && pojo.getItemId() != null) {
      FinalExamModel f = quesOperationFacade.selectFinalExamById(Long.valueOf(pojo.getItemId()));
      if (f != null) {
        examDetail.setItemIndex("");
        examDetail.setItemName(f.getExamName());
      };
    }
    examDetail.setQuestionList(questionList);
    response.setExamDetail(examDetail);
    addUserCredit(pojo, deductCredit, ExamType.getByCode(pojo.getExamType()));
    return response;
  }


  private void nextItem(ExamDetailResponse.ExamDetail examDetail, String courseId,
      String chapterId, String itemId, String userId) {
    List<CourseProductItem> itemList = quesOperationFacade.queryChapterItemList(courseId);
    if (null == itemList || itemList.size() == 0) return;
    for (int i = 0; i < itemList.size(); i++) {
      CourseProductItem item = itemList.get(i);
      if (null == item || item.getResourceType() != 1) continue;
      if (null != itemId && item.getId() != Integer.parseInt(itemId)) continue;
      if (null != itemId && item.getId() == Integer.parseInt(itemId)) examDetail.setItemInfo(item);
      if (null == itemId && null != chapterId && item.getParentId() == Integer.parseInt(chapterId))
        examDetail.setNextItemInfo(item);
      loopNextItem(examDetail, itemList, i, userId, chapterId);
      return;
    }
  }

  private Long maxChapterId(List<CourseProductItem> itemList) {
    Long maxChapterId = 0L;
    Iterator<CourseProductItem> iterator = itemList.iterator();
    while (iterator.hasNext()) {
      CourseProductItem item = iterator.next();
      if (item.getResourceType() == 1 && item.getParentId() == 0) {
        maxChapterId = item.getId();
      }
    }
    return maxChapterId;
  }

  /**
   * @description 包装 每一章的第一节 数据
   * @author 李德洪
   * @Date 2018年2月9日
   * @param itemList
   * @return
   */
  @SuppressWarnings("unused")
  private Map<Long, CourseProductItem> warpPerChapterOfFirstItem(List<CourseProductItem> itemList) {
    Map<Long, CourseProductItem> map = Maps.newHashMap();
    for (CourseProductItem item : itemList) {
      if (item.getResourceType() == 1 && item.getParentId() != 0
          && !map.containsKey(item.getParentId().longValue())) {
        map.put(item.getParentId().longValue(), item);
      }
    }
    return map;
  }

  /**
   * @param examDetail
   * @param courseId
   * @param chapterId
   */
  private void nextChapter(ExamDetailResponse.ExamDetail examDetail, String courseId,
      String chapterId, String userId) {
    examDetail.setItemIndex("章总结");
    examDetail.setItemName("习题解析");
    List<CourseProductItem> itemList = quesOperationFacade.queryChapterItemList(courseId);
    if (null == itemList || itemList.size() == 0) return;
    // 此门课的最后一章 章总结
    if (Long.valueOf(chapterId).equals(maxChapterId(itemList))) {
      examDetail.setNextChapterId("-1");
      examDetail.setNextItemId("-1");
      return;
    }
    for (int i = 0; i < itemList.size(); i++) {
      CourseProductItem item = itemList.get(i);
      if (null == item || item.getResourceType() != 1) continue;
      if (null != chapterId && item.getId() != Integer.parseInt(chapterId)) continue;
      loopNextItem(examDetail, itemList, i, userId, chapterId);
      return;
    }
  }

  private void loopNextItem(ExamDetailResponse.ExamDetail examDetail,
      List<CourseProductItem> itemList, int point, String userId, String chapterId) {
    // 此门课的最后一节 节id,返回章总结
    if (itemList.size() == point + 1) {
      examDetail.setNextChapterId(chapterId);
      examDetail.setNextItemId("");
    }
    if (itemList.size() <= point + 1) return;
    if (null != itemList.get(point + 1) && itemList.get(point + 1).getResourceType() == 1) {
      if (itemList.get(point + 1).getParentId() == 0) {
        examDetail.setNextChapterId(itemList.get(point + 1).getId().toString());
        // examDetail.setNextItemId(warpPerChapterOfFirstItem(itemList)
        // .get(itemList.get(point + 1).getId()).getId().toString());
        examDetail.setNextItemId("");
        Map<String, Object> cond = Maps.newHashMap();
        cond.put("userId", userId);
        cond.put("courseId", examDetail.getCourseId());
        cond.put("chapterId", itemList.get(point + 1).getId().toString());
        examDetail.setNextDeductCredit(ScoreConstant.CHAPTER_SCORE.toString());
        cond.put("itemId", itemList.get(point + 1).getId().toString());
        List<UserChapterRecord> chapter = quesOperationFacade.queryUserChapterRecord(cond);
        if (chapter != null && chapter.size() > 0) {
          examDetail.setStatus(chapter.get(0).getStatus().toString());
        }
      } else {
        examDetail.setNextItemInfo(itemList.get(point + 1));
      }
    }
    return;
  }

  // 章总结是否加锁
  public boolean chapterIsLock(String userId, String courseId, String chapterId, Long itemId) {
    List<CourseProductItem> itemList = quesOperationFacade.queryChapterItemList(courseId);
    int itemCount = 0;
    for (CourseProductItem c : itemList) {
      if (c.getParentId() != null && c.getParentId().toString().equals(chapterId)
          && c.getResourceType() != null && c.getResourceType().toString().equals("1")
          && !c.getId().equals(itemId)) {
        itemCount++;
      }
    }
    int finishCount = 0;
    Map<String, Object> cond = Maps.newHashMap();
    cond.put("userId", userId);
    cond.put("courseId", courseId);
    cond.put("chapterId", chapterId);
    List<UserChapterRecord> itemRecordList = quesOperationFacade.queryUserChapterRecord(cond);
    if (null != itemRecordList && itemRecordList.size() > 0) {
      for (UserChapterRecord itemRecord : itemRecordList) {
        if (itemRecord.getChapterId() != null
            && !itemRecord.getChapterId().toString().equals(itemRecord.getItemId().toString())
            && !itemRecord.getItemId().equals(itemId)) {
          if (itemRecord.getScore() != null && itemRecord.getScore() >= 60) {
            finishCount++;
          }
        }
      }
      if (ConfigProp.getParams("lock") != null && ConfigProp.getParams("lock").equals("1")) {
        if (finishCount < itemCount) {
          return true;
        } else {
          return false;
        }
      } else {
        return false;
      }
    }
    if (ConfigProp.getParams("lock") != null && ConfigProp.getParams("lock").equals("1")
        && finishCount < itemCount) {
      return true;
    } else {
      return false;
    }
  }

  // 期末测试是否加锁
  public boolean finalExamIsLock(String userId, String courseId) {
    List<CourseProductItem> itemList = quesOperationFacade.queryChapterItemList(courseId);
    int chapterCount = 0;
    for (CourseProductItem c : itemList) {
      if (c.getParentId() != null && c.getParentId().toString().equals("0")
          && c.getResourceType() != null && c.getResourceType().toString().equals("1")) {
        chapterCount++;
      }
    }
    Map<String, Object> cond = Maps.newHashMap();
    cond.put("userId", userId);
    cond.put("courseId", courseId);
    List<UserChapterRecord> itemRecordList = quesOperationFacade.queryUserChapterRecord(cond);
    if (null != itemRecordList && itemRecordList.size() > 0) {
      int finishCount = 0;
      for (UserChapterRecord itemRecord : itemRecordList) {
        if ((itemRecord.getChapterId() != null && itemRecord.getItemId() != null
            && itemRecord.getChapterId().toString().equals(itemRecord.getItemId().toString())
            && itemRecord.getScore() != null && itemRecord.getScore() >= 60)) {
          finishCount++;
        }
      }
      if (ConfigProp.getParams("lock") != null && ConfigProp.getParams("lock").equals("1")) {
        if (finishCount < chapterCount) {
          return true;
        } else {
          return false;
        }
      } else {
        return false;
      }
    }
    return false;
  }

  /**
   * 4 真题模考列表接口 · 说明 点击历年真题模拟接口进入真题模考列表。 · url /quesbk/real_exam_list · 参数 uid courseId
   * 
   * @param pojo
   */
  public RealExamListResponse realExamList(RealExamListPojo pojo) {
    if (!checkCourseId(pojo, pojo.getCourseId()))
      return new RealExamListResponse(ResultType.UNVALIDCOURSEID);
    List<QuesbkExamPaper> quesbkExamPapers =
        quesOperationFacade.queryExamPaperList(pojo.getCourseId(), ExamType.REAL_PAPER.getCode());
    RealExamListResponse response = new RealExamListResponse(ResultType.SUCCESS);
    List<RealExamListResponse.RealExam> realExams = new ArrayList<>();
    for (QuesbkExamPaper quesbkExamPaper : quesbkExamPapers) {
      RealExamListResponse.RealExam realExam = new RealExamListResponse.RealExam();
      realExam.setDiffculty(quesbkExamPaper.getDiffculty());
      realExam.setPaperId(quesbkExamPaper.getId().toString());
      realExam.setPaperName(quesbkExamPaper.getExamName());
      realExam.setQuesCount(quesbkExamPaper.getQuesNum().toString());
      realExams.add(realExam);
    }
    response.setRealExamList(realExams);
    return response;
  }

  /**
   * 5 出题接口 新练习（包括智能练习 组卷模考 章节练习） · 说明 智能练习、组卷模式、章节练习根据所选模式，触发相关组卷规则进行出题； 历年真题返回真题卷。 · url
   * /quesbk/exam_detail · 参数 uid subjectId chapterId examType paperId
   * 
   * @param pojo
   */
  public ExamDetailResponse examDetail_v1_3_8(ExamDetailPojo_v1_3_8 pojo) throws Exception {
    if (!checkCourseId(pojo, pojo.getCourseId()))
      return new ExamDetailResponse(ResultType.UNVALIDCOURSEID);
    return autoExamDetail_v1_3_8(pojo);
  }

  /**
   * 5 出题接口 新练习（包括智能练习 组卷模考 章节练习） · 说明 智能练习、组卷模式、章节练习根据所选模式，触发相关组卷规则进行出题； 历年真题返回真题卷。 · url
   * /quesbk/exam_detail · 参数 uid subjectId chapterId examType paperId
   * 
   * @param pojo
   */
  public ExamDetailResponse examDetail(ExamDetailPojo pojo) throws Exception {
    if (!checkCourseId(pojo, pojo.getCourseId()))
      return new ExamDetailResponse(ResultType.UNVALIDCOURSEID);
    return autoExamDetail(pojo);
  }

  /**
   * 2017/11/13 兼容1.3.8以前版本逻辑 (机器人)出题接口 新练习（包括智能练习 组卷模考 章节练习） · 说明
   * 智能练习、组卷模式、章节练习根据所选模式，触发相关组卷规则进行出题； 历年真题返回真题卷。 · url
   * 
   * @param pojo
   */
  public ExamDetailResponse autoExamDetail_v1_3_8(ExamDetailPojo_v1_3_8 pojo) {
    try {
      if (QuesBaseConstant.IMPORT_USER_TYPE.equals(pojo.getUserType())
          && (ExamType.ITEM_BREAKTHROUGH_PAPER.getCode().equals(pojo.getExamType()) || ExamType.CHAPTER_BREAKTHROUGH_PAPER
              .getCode().equals(pojo.getExamType())) && StringUtils.isNotBlank(pojo.getChapterId())
          && StringUtils.isAllBlank(pojo.getItemId(), pojo.getSrcFaceId()))
        return new ExamDetailResponse(ResultType.MissSrcFace);
      if (StringUtils.isNotBlank(pojo.getPaperId())) {
        REAL_PAPER: {
          UserExamRecord examRecord =
              quesOperationFacade.queryExamRecord(pojo.getPaperId(), pojo.getUid());
          if (null == examRecord) break REAL_PAPER;
          return continueExam_v1_3_8(pojo, examRecord);
        }
      }
      if (StringUtils.isNotBlank(pojo.getRecordId())) {
        CHALLENGE: {
          ChallengeRecord challengeRecord =
              quesOperationFacade.queryChallengeRecord(pojo.getRecordId());
          if (null == challengeRecord || StringUtils.isBlank(challengeRecord.getPaperId()))
            break CHALLENGE;
          pojo.setPaperId(challengeRecord.getPaperId());
          UserExamRecord examRecord =
              quesOperationFacade.queryExamRecord(challengeRecord.getPaperId(), pojo.getUid());
          if (null == examRecord) break CHALLENGE;
          return continueExam_v1_3_8(pojo, examRecord);
        }
      }
      return examDetail0_v1_3_8(pojo);
    } catch (Exception e) {
      AlarmEntityImpl alarm =
          new QuesbkAlarmEntity(QuesBaseConstant.MODELE_STR, "exam-detail-fail", "出题失败报警",
              "出题失败报警", null);
      LoggerUtil.alarmInfo(alarm);
      ExamDetailResponse response = new ExamDetailResponse(ResultType.SYSFAIL);
      response.setRetdesc(e.getMessage());
      LoggerUtil.error("autoExamDetail_v1_3_8", e);
      return response;
    }
  }

  /**
   * (机器人)出题接口 新练习（包括智能练习 组卷模考 章节练习） · 说明 智能练习、组卷模式、章节练习根据所选模式，触发相关组卷规则进行出题； 历年真题返回真题卷。 · url
   * 
   * @param pojo
   * @throws Exception
   */
  public ExamDetailResponse autoExamDetail(ExamDetailPojo pojo) throws Exception {
    try {
      if (QuesBaseConstant.IMPORT_USER_TYPE.equals(pojo.getUserType())
          && (ExamType.ITEM_BREAKTHROUGH_PAPER.getCode().equals(pojo.getExamType()) || ExamType.CHAPTER_BREAKTHROUGH_PAPER
              .getCode().equals(pojo.getExamType())) && StringUtils.isNotBlank(pojo.getChapterId())
          && StringUtils.isAllBlank(pojo.getItemId(), pojo.getSrcFaceId()))
        return new ExamDetailResponse(ResultType.MissSrcFace);
      if (StringUtils.isNotBlank(pojo.getPaperId())) {
        REAL_PAPER: {
          UserExamRecord examRecord =
              quesOperationFacade.queryExamRecord(pojo.getPaperId(), pojo.getUid());
          if (null == examRecord) break REAL_PAPER;
          return continueExam(pojo, examRecord);
        }
      }
      if (StringUtils.isNotBlank(pojo.getRecordId())) {
        CHALLENGE: {
          ChallengeRecord challengeRecord =
              quesOperationFacade.queryChallengeRecord(pojo.getRecordId());
          if (null == challengeRecord || StringUtils.isBlank(challengeRecord.getPaperId()))
            break CHALLENGE;
          pojo.setPaperId(challengeRecord.getPaperId());
          UserExamRecord examRecord =
              quesOperationFacade.queryExamRecord(challengeRecord.getPaperId(), pojo.getUid());
          if (null == examRecord) break CHALLENGE;
          return continueExam(pojo, examRecord);
        }
      }
      if (StringUtils.isAllNotBlank(pojo.getChapterId(), pojo.getItemId())
          && pojo.getChapterId().equals(pojo.getItemId())
          && ExamType.ITEM_BREAKTHROUGH_PAPER.getCode().equals(pojo.getExamType())) {
        return new ExamDetailResponse(ResultType.UNVALIDQUESINFO4EXAMTYPE);
      }
      return examDetail0(pojo);
    } catch (Exception e) {
      AlarmEntityImpl alarm = new ExamDetailAlarm(e.getMessage());
      LoggerUtil.alarmInfo(alarm);
      ExamDetailResponse response = new ExamDetailResponse(ResultType.SYSFAIL);
      response.setRetdesc(e.getMessage());
      LoggerUtil.error("autoExamDetail", e);
      return response;
    }
  }

  /**
   * 6 提交答案接口 · 说明 上传用户做题详情返回给后台处理接口。 · url /quesbk/exam_result
   * 
   * @param pojo
   */
  public SubmitResultResponse submitExamResult(ExamResultPojo pojo) {
    if (!checkCourseId(pojo, pojo.getCourseId()))
      return new SubmitResultResponse(ResultType.UNVALIDCOURSEID);
    return ((QuesBaseServices) AopContext.currentProxy()).autoSubmitExamResult(pojo);
  }


  /**
   * (机器人)提交答案接口 · 说明 上传用户做题详情返回给后台处理接口。 · url /quesbk/exam_result
   * 
   * @param pojo
   */
  public SubmitResultResponse autoSubmitExamResult(ExamResultPojo pojo) {
    SubmitResultResponse response = new SubmitResultResponse(ResultType.SUCCESS);
    if (null != pojo.getUserCredit()) response.setUserCredit(pojo.getUserCredit());
    QuesbkExamPaper paper = quesOperationFacade.queryExamPaper(pojo.getPaperId());
    if (paper == null) {
      // 找不到试卷,报警
      LoggerUtil.alarmInfo(new SubmitExamAlarm(ResultType.NOPAPER.getMsg()));
      return new SubmitResultResponse(ResultType.NOPAPER);
    }
    String examType = paper.getExamTypeId().toString();
    if (ExamType.RANDOM_CHALLENGE_PAPER.getCode().equals(examType)
        && StringUtils.isBlank(pojo.getRecordId())) {
      return new SubmitResultResponse(ResultType.MISSINGRECORD);
    }
    String examTypeId = paper.getExamTypeId().toString();
    UserExamRecord record = quesOperationFacade.queryExamRecord(pojo.getPaperId(), pojo.getUid());
    if (!ExamType.FINAL_EXAM_PAPER.getCode().equals(examTypeId) && record != null)
      return new SubmitResultResponse(ResultType.CANTREPEATEXAM);
    response.setPkExam(quesChallengeService.getPkExam(pojo.getTypeCode(), pojo.getCourseId(),
        pojo.getUid()));
    IQuesCaculateTask caculateTask = QuesCaculateUtil.getTask(pojo.getCourseId());
    // 获取保存结果操作类型
    ExamSubmitStatus examStatus = ExamSubmitStatus.getByCode(pojo.getExamStatus());
    UserExamRecordDetail detail = new UserExamRecordDetail(pojo.getUid());
    detail.setCaculateTask(caculateTask);
    List<String> quesIds =
        FastJsonUtil.fromJsons(paper.getQuesIds(), new TypeReference<List<String>>() {});
    detail.setStatus(pojo.getExamStatus()); // 设置提交类型
    Map<Long, AnswerItem> answerMap = new HashMap<>();
    // 根据问题ID列表查询问题详情
    List<QuesbkQues> quesList =
        quesOperationFacade.queryAbstractQuesList(quesIds, pojo.getCourseId());
    quesList =
        ((QuesBaseServices) AopContext.currentProxy()).duplicateRemoval(pojo, paper, quesList);
    detail.setQuesCount(quesList.size()); // 设置总题数
    Integer finishCount = 0;
    List<AnswerItem> examDetail =
        FastJsonUtil.fromJsons(pojo.getExamDetail(), new TypeReference<List<AnswerItem>>() {});
    for (AnswerItem answer : examDetail) {
      answerMap.put(answer.getQuesId(), answer);
    }
    // 2018/02/26 ADD BY zhaodan 使用组合消息替换原消息逻辑
    MessageBox messageBox = new MessageBox();
    // String tag = UUID.randomUUID().toString();
    // QuesExamCache.initTotalExamRecord(tag);
    // 2017/11/22 ADD BY zhaodan 筛选记录错题类型
    List<QuesbkQuesType> typeList = quesOperationFacade.queryQuesTypeList();
    Set<Long> quesTypeIdSet = Sets.newHashSet();
    for (QuesbkQuesType type : typeList) {
      if (QuesBaseConstant.QUES_TYPE_RECORD_WRONG == type.getAnswerType()) {
        quesTypeIdSet.add(type.getId());
      }
    }
    List<AnswerItem> answerItemList = Lists.newArrayList();
    for (QuesbkQues ques : quesList) {
      AnswerItem answer = answerMap.get(ques.getId());
      if (null == answer) {
        answer = new AnswerItem();
        answer.setQuesId(ques.getId());
      }
      // 只有提交状态为交卷时记录错题,并记录正确数和正确率
      if (examStatus.equals(ExamSubmitStatus.SUBMIT)
          || ExamSubmitStatus.SUBMIT.getCode().equals(answer.getExamStatus())) {
        Boolean isWrong = false;
        if (isWrong(ques, answer.getAnswerIdList())) {
          isWrong = true; // 记录状态为错误
        } else {
          detail.addRightCount(); // 记录正确数和正确率
        }
        // recordWrong(pojo, paper, ques, answer, isWrong, tag, idSet);
        recordWrong(pojo, paper, ques, answer, isWrong, quesTypeIdSet, messageBox); // 记录错题集
      }
      // 记录完成数
      if (answer.getAnswerIdList() != null && answer.getAnswerIdList().size() > 0) {
        finishCount++;
      }
      // 记录答题情况
      answerItemList.add(answer);
    }
    detail.setFinishCount(finishCount); // 设置完成题数
    if (ExamType.RANDOM_CHALLENGE_PAPER.getCode().equals(examTypeId)
        || ExamType.FRIEND_CHALLENGE_PAPER.getCode().equals(examTypeId)) {
      // 记录PK信息
      ChallengeRecord challengeRecord =
          quesChallengeService.updateChallengeRecord(pojo.getModule(), pojo.getRecordId(),
              pojo.getUid(), detail);
      if (pojo.getUid().equals(challengeRecord.getChallengerUid())
          && null != challengeRecord.getBeChallengerScore())
        response.setOpponentsScore(MathUtil.getIntStringValue(challengeRecord
            .getBeChallengerScore()));
      if (pojo.getUid().equals(challengeRecord.getBeChallengerUid())
          && null != challengeRecord.getChallengerScore())
        response
            .setOpponentsScore(MathUtil.getIntStringValue(challengeRecord.getChallengerScore()));
      response.setWinner(challengeRecord.getWinner());
      // 发送PK事件
      // sendPkEvent(examTypeId, pojo, detail);
      sendPkEvent(examTypeId, pojo, detail, messageBox);
      List<String> examList =
          quesChallengeService.getPkRank(pojo.getTypeCode(), pojo.getCourseId());
      if (null != examList && examList.size() > 0) response.setUserIdList(examList);
    } else if (ExamType.CHAPTER_BREAKTHROUGH_PAPER.getCode().equals(examTypeId)
        || ExamType.ITEM_BREAKTHROUGH_PAPER.getCode().equals(examTypeId)) {
      // 记录学习进度
      recordLearnProcess(pojo, paper);
      // 发送闯关事件
      // sendTollgateEvent(pojo, paper, detail);
      sendTollgateEvent(pojo, paper, detail, messageBox);
      CourseProductItem item;
      if (paper.getItemId() != null) {
        item =
            quesOperationFacade.queryItem(paper.getCourseId().toString(), paper.getChapterId()
                .toString(), paper.getItemId().toString());
      } else {
        item =
            quesOperationFacade.queryChapter(paper.getCourseId().toString(), paper.getChapterId()
                .toString());
      }
      List<String> userList = null;
      if (null != item) {
        if (StringUtils.isJsonNotBlank(item.getTopUserList()))
          userList =
              FastJsonUtil.fromJsons(item.getTopUserList(), new TypeReference<List<String>>() {});
        if (null != userList && userList.size() > 0) response.setUserIdList(userList);
        response.setCompleteCount(item.getCompleteCount());
      }
      if (detail.getScore() >= 60) {
        if (StringUtils.isNotBlank(pojo.getNextChapterSummery())) {
          if (chapterIsLock(pojo.getUid(), pojo.getCourseId(), pojo.getNextChapterSummery(),
              paper.getItemId())) {
            response.setLock("1");
          } else {
            response.setLock("0");
          }
        }
      } else {
        if (chapterIsLock(pojo.getUid(), pojo.getCourseId(), pojo.getNextChapterSummery(), null)) {
          response.setLock("1");
        } else {
          response.setLock("0");
        }
      }
    } else if (ExamType.FINAL_EXAM_PAPER.getCode().equals(examTypeId)) {
      // 发送闯关事件
      // sendTollgateEvent(pojo, paper, detail);
      sendTollgateEvent(pojo, paper, detail, messageBox);
    } else if (ExamType.DAILY_PRACTICE.getCode().equals(examTypeId)) {
      // 发送每日一练事件
      // sendDailyPracticeEvent(pojo, detail);
      sendDailyPracticeEvent(pojo, detail, messageBox);
    } else if (ExamType.LEAK_FILLING.getCode().equals(examTypeId)) {
      // 发送查漏补缺事件
      // sendLeakFillingEvent(pojo, detail);
      sendLeakFillingEvent(pojo, detail, messageBox);
    }

    // 刚才是发消息，并不确定消息是否执行成功
    ADD_SCORE_STATUS: {
      if (detail.getScore() < 60) {
        break ADD_SCORE_STATUS;
      } else if (ExamType.CHAPTER_BREAKTHROUGH_PAPER.getCode().equals(examTypeId)
          || ExamType.ITEM_BREAKTHROUGH_PAPER.getCode().equals(examTypeId)) {
        Map<String, Object> input = Maps.newHashMap();
        input.put("userId", pojo.getUid());
        input.put("module", pojo.getModule());
        input.put("typeCode", pojo.getTypeCode());
        input.put("courseId", pojo.getCourseId());
        input.put("chapterId", paper.getChapterId());
        if (null == paper.getItemId() || paper.getItemId().equals(paper.getChapterId())) {
          input.put("itemId", paper.getChapterId());
          List<UserChapterRecord> chapterRecordList =
              quesOperationFacade.queryUserChapterRecord(input);
          if (null == chapterRecordList || chapterRecordList.size() == 0) break ADD_SCORE_STATUS;
          UserChapterRecord chapterRecord = chapterRecordList.get(0);
          if (chapterRecord.getScore() >= 60) response.markCompleteUser(pojo.getUid());
          String uniqueId = UUID.randomUUID().toString();
          chapterRecord.setModule(pojo.getModule());
          InitCreditRedBonus bonus = new InitCreditRedBonus(uniqueId, chapterRecord);
          // queueService.initCreditRedBonus(bonus);
          messageBox.addTargetLevelMessage(MessageBox.THIRD_LEVEL,
              Message.InitCreditRedBonus.name(), bonus);
          response.setUniqueId(uniqueId);
        } else {
          input.put("itemId", paper.getItemId());
          List<UserChapterRecord> chapterRecordList =
              quesOperationFacade.queryUserChapterRecord(input);
          if (null == chapterRecordList || chapterRecordList.size() == 0) break ADD_SCORE_STATUS;
          UserChapterRecord chapterRecord = chapterRecordList.get(0);
          if (chapterRecord.getScore() >= 60) response.markCompleteUser(pojo.getUid());
        }
      } else if (ExamType.FINAL_EXAM_PAPER.getCode().equals(examTypeId)) {
        Map<String, Object> input = Maps.newHashMap();
        input.put("userId", pojo.getUid());
        input.put("module", pojo.getModule());
        input.put("typeCode", pojo.getTypeCode());
        input.put("courseId", pojo.getCourseId());
        input.put("itemId", paper.getItemId());
        List<UserChapterRecord> chapterRecordList =
            quesOperationFacade.queryUserChapterRecord(input);
        if (null == chapterRecordList || chapterRecordList.size() == 0) break ADD_SCORE_STATUS;
        String uniqueId = UUID.randomUUID().toString();
        UserChapterRecord chapterRecord = chapterRecordList.get(0);
        chapterRecord.setModule(pojo.getModule());
        InitCreditRedBonus bonus = new InitCreditRedBonus(uniqueId, chapterRecord);
        // queueService.initCreditRedBonus(bonus);
        messageBox.addTargetLevelMessage(MessageBox.THIRD_LEVEL, Message.InitCreditRedBonus.name(),
            bonus);
        response.setUniqueId(uniqueId);
      }
    }
    // 计算得分点成绩
    caculateScorePoint(examTypeId, pojo, paper, messageBox);
    // 统计练习历史并记录章节闯关记录
    // recordExamHis(pojo, paper, detail, tag);
    recordExamHis(pojo, paper, answerItemList, detail, messageBox);
    addUserCredit(pojo, detail.getCreditUpper(), ExamType.getByCode(examType));
    // 刷新learn_record_view
    messageBox.addTargetLevelMessage(MessageBox.THIRD_LEVEL, Message.UpdateLearnRecordView.name(),
        pojo);
    // 发消息逻辑
    queueService.sendMessageBox(messageBox);
    response.setDetail(detail); // 设置结果细节
    return response;
  }


  /**
   * 对试题列表做去重处理
   * 
   * @param pojo 入参
   * @param paper 试卷
   * @param quesList 试题列表
   * @return 去重后试题列表
   */
  public List<QuesbkQues> duplicateRemoval(ExamResultPojo pojo, QuesbkExamPaper paper,
      List<QuesbkQues> quesList) {
    if (null == quesList || quesList.isEmpty()) {
      return quesList;
    }
    Map<Long, QuesbkQues> quesMap = Maps.newHashMap();
    for (QuesbkQues ques : quesList) {
      if (!quesMap.containsKey(ques.getId())) {
        quesMap.put(ques.getId(), ques);
        continue;
      }
      if (null != paper.getItemId() && !paper.getItemId().equals(paper.getChapterId())
          && ques.getChapterId().equals(paper.getItemId())) {
        quesMap.put(ques.getId(), ques);
        continue;
      }
      if (null == paper.getItemId() || paper.getItemId().equals(paper.getChapterId())) {
        if (ques.getChapterId().equals(paper.getChapterId())) {
          quesMap.put(ques.getId(), ques);
          continue;
        }
      }
    }
    return Lists.newArrayList(quesMap.values());
  }

  private void caculateScorePoint(String examTypeId, ExamResultPojo pojo, QuesbkExamPaper paper,
      MessageBox messageBox) {
    Short ruleType = QuesbkUtil.transferExamType(examTypeId, paper.getItemId());
    if (null == ruleType) return;
    UserScorePointRecord record = new UserScorePointRecord();
    record.setModule(pojo.getModule());
    record.setTypeCode(pojo.getTypeCode());
    record.setRuleType(ruleType);
    record.setProductId(Long.parseLong(pojo.getCourseId()));
    record.setUserId(Long.parseLong(pojo.getUid()));
    messageBox
        .addTargetLevelMessage(MessageBox.SECOND_LEVEL, Message.ScorePoint.toString(), record);
  }

  /**
   * 记录学习进度
   * 
   * @param pojo
   * @param paper
   */
  private void recordLearnProcess(ExamResultPojo pojo, QuesbkExamPaper paper) {
    UserLearnAchieveVo vo = new UserLearnAchieveVo();
    vo.setUserId(pojo.getUid());
    vo.setModuleId(pojo.getModule());
    vo.setCourseId(pojo.getCourseId());
    if (paper.getItemId() == null) {
      vo.setChapterId(paper.getChapterId().toString());
      vo.setItemId(paper.getChapterId().toString());
    } else {
      vo.setChapterId(paper.getChapterId().toString());
      vo.setItemId(paper.getItemId().toString());
    }
    RabbitMQSender.getInstance().send(new UserLearnAchieveMessage(vo));

  }

  public void addUserCredit(QuesBasePojo pojo, Integer credit, ExamType examType) {
    if (credit == null || 0 == credit) return;
    AddCreditRequest request = new AddCreditRequest();
    request.setUid(pojo.getUid());
    request.setModule(pojo.getModule());
    request.setCreditUpper(credit);
    request.setType(examType.getName());
    RabbitMQSender.getInstance().send(new AddCreditMessage(request));
  }

  private void sendTollgateEvent(ExamResultPojo pojo, QuesbkExamPaper paper,
  // UserExamRecordDetail detail) {
      UserExamRecordDetail detail, MessageBox messageBox) {
    TollgateEvent event = EventBuilder.buildTollgateEvent();
    event.setUserId(pojo.getUid());
    event.setModule(pojo.getModule());
    event.setMajorId(pojo.getMajorId());
    event.setCourseId(paper.getCourseId().toString());
    if (ExamType.CHAPTER_BREAKTHROUGH_PAPER.getCode().equals(paper.getExamTypeId().toString())
        || ExamType.ITEM_BREAKTHROUGH_PAPER.getCode().equals(paper.getExamTypeId().toString())) {
      event.setChapterId(paper.getChapterId().toString());
      if (paper.getItemId() != null) {
        event.setTollgateId(paper.getItemId().toString());
      } else if (paper.getChapterId() != null) {
        event.setTollgateId(paper.getChapterId().toString());
      }
    }
    if (ExamType.FINAL_EXAM_PAPER.getCode().equals(paper.getExamTypeId().toString())) {
      event.setChapterId(paper.getItemId().toString());
      event.setTollgateId(paper.getItemId().toString());
    }
    event.setCount(detail.getFinishCount());
    event.setCredit(detail.getCreditUpper());
    event.setScore(detail.getScore());
    event.setStarLevel(detail.getStarLevel());
    // event.send();
    messageBox.addTargetLevelMessage(MessageBox.THIRD_LEVEL, Message.TollgateEvent.name(), event);
  }

  // private void sendDailyPracticeEvent(ExamResultPojo pojo, UserExamRecordDetail detail) {
  private void sendDailyPracticeEvent(ExamResultPojo pojo, UserExamRecordDetail detail,
      MessageBox messageBox) {
    DailyPracticeEvent event = EventBuilder.buildDailyPracticeEvent();
    event.setUserId(pojo.getUid());
    event.setModule(pojo.getModule());
    event.setMajorId(pojo.getMajorId());
    event.setCourseId(pojo.getCourseId());
    event.setCount(detail.getFinishCount());
    event.setScore(detail.getScore());
    // event.send();
    messageBox.addTargetLevelMessage(MessageBox.THIRD_LEVEL, Message.DailyPracticeEvent.name(),
        event);
  }

  // private void sendLeakFillingEvent(ExamResultPojo pojo, UserExamRecordDetail detail) {
  private void sendLeakFillingEvent(ExamResultPojo pojo, UserExamRecordDetail detail,
      MessageBox messageBox) {
    LeakFillingEvent event = EventBuilder.buildLeakFillingEvent();
    event.setUserId(pojo.getUid());
    event.setModule(pojo.getModule());
    event.setMajorId(pojo.getMajorId());
    event.setCourseId(pojo.getCourseId());
    event.setCount(detail.getFinishCount());
    event.setScore(detail.getScore());
    // event.send();
    messageBox
        .addTargetLevelMessage(MessageBox.THIRD_LEVEL, Message.LeakFillingEvent.name(), event);
  }

  // private void sendPkEvent(String examType, ExamResultPojo pojo, UserExamRecordDetail detail) {
  private void sendPkEvent(String examType, ExamResultPojo pojo, UserExamRecordDetail detail,
      MessageBox messageBox) {
    if (examType.equals(ExamType.RANDOM_CHALLENGE_PAPER.toString())) {
      RandomPkEvent event = EventBuilder.buildRandomPkEvent();
      event.setUserId(pojo.getUid());
      event.setModule(pojo.getModule());
      event.setMajorId(pojo.getMajorId());
      event.setCourseId(pojo.getCourseId());
      event.setCount(detail.getFinishCount());
      event.setCredit(detail.getCreditUpper());
      event.setScore(detail.getScore());
      // event.send();
      messageBox.addTargetLevelMessage(MessageBox.THIRD_LEVEL, Message.RandomPkEvent.name(), event);
    }
  }

  public ExamDetailResponse continueExam_v1_3_8(ExamDetailPojo_v1_3_8 pojo,
      UserExamRecord examRecord) throws Exception {
    QuesbkExamPaper paper = quesOperationFacade.queryExamPaper(pojo.getPaperId());
    Map<Long, AnswerItem> myAnswerMap = Maps.newHashMap();
    if (StringUtils.isNotBlank(examRecord.getQuestions())) {
      for (AnswerItem myAnswer : FastJsonUtil.fromJsons(examRecord.getQuestions(),
          new TypeReference<List<AnswerItem>>() {})) {
        myAnswerMap.put(myAnswer.getQuesId(), myAnswer);
      }
    }
    List<Object> quesList = quesOperationFacade.queryExamQuestionIdList_v1_3_8(pojo, paper);
    if (quesList == null || quesList.size() == 0) {
      // 无法出题,报警
      LoggerUtil.alarmInfo(new ExamDetailAlarm(ResultType.UNVALIDQUESINFO4EXAMTYPE.getMsg()));
      return new ExamDetailResponse(ResultType.UNVALIDQUESINFO4EXAMTYPE);
    }
    List<Question> questionList = packageQuestionList(quesList, myAnswerMap, null);
    // packageQuestionList(quesList, myAnswerMap, null,
    // new CheckStoreRule(pojo.getUid(), pojo.getCourseId()));
    ExamDetailResponse response = new ExamDetailResponse(ResultType.SUCCESS);
    ExamDetailResponse.ExamDetail examDetail = new ExamDetailResponse.ExamDetail(paper);
    examDetail.setExamCost(examRecord.getExamCost().toString()); // 设置上次练习耗时
    examDetail.setQuestionList(questionList);
    response.setExamDetail(examDetail);
    return response;
  }

  /**
   * 5 出题接口 继续练习（包括智能练习 组卷模考 章节练习） · 继续练习 · url /quesbk/exam_detail · 参数 uid subjectId chapterId
   * examType paperId
   * 
   * @param pojo
   * @param examRecord
   * @throws Exception
   */
  public ExamDetailResponse continueExam(ExamDetailPojo pojo, UserExamRecord examRecord)
      throws Exception {
    QuesbkExamPaper paper = quesOperationFacade.queryExamPaper(pojo.getPaperId());
    Map<Long, AnswerItem> myAnswerMap = Maps.newHashMap();
    if (StringUtils.isNotBlank(examRecord.getQuestions())) {
      for (AnswerItem myAnswer : FastJsonUtil.fromJsons(examRecord.getQuestions(),
          new TypeReference<List<AnswerItem>>() {})) {
        myAnswerMap.put(myAnswer.getQuesId(), myAnswer);
      }
    }
    List<QuesbkExamRule> ruleList =
        quesOperationFacade.queryExamRuleList(pojo.getCourseId(), pojo.getExamType());
    if (ruleList == null || ruleList.size() == 0) {
      // 未找到命题蓝图,报警
      LoggerUtil.alarmInfo(new ExamDetailAlarm(ResultType.MISSINGEXAMRULE.getMsg()));
      return new ExamDetailResponse(ResultType.MISSINGEXAMRULE);
    }
    List<Object> quesList = quesOperationFacade.queryExamQuestionIdList(pojo, ruleList, paper);
    if (quesList == null || quesList.size() == 0) {
      // 无法出题,报警
      LoggerUtil.alarmInfo(new ExamDetailAlarm(ResultType.UNVALIDQUESINFO4EXAMTYPE.getMsg()));
      return new ExamDetailResponse(ResultType.UNVALIDQUESINFO4EXAMTYPE);
    }
    List<Question> questionList = packageQuestionList(quesList, myAnswerMap, null);
    // packageQuestionList(quesList, myAnswerMap, null,
    // new CheckStoreRule(pojo.getUid(), pojo.getCourseId()));
    ExamDetailResponse response = new ExamDetailResponse(ResultType.SUCCESS);
    ExamDetailResponse.ExamDetail examDetail = new ExamDetailResponse.ExamDetail(paper);
    examDetail.setExamCost(examRecord.getExamCost().toString()); // 设置上次练习耗时
    examDetail.setQuestionList(questionList);
    response.setExamDetail(examDetail);
    return response;
  }

  private UserChapterRecord buildChapterScore(String uid, String module, String typeCode,
      Long courseId, Long chapterId, Long itemId, Integer starLevel, Double score,
      List<String> robotIdList) {
    if (null == chapterId || null == courseId || null == starLevel) return null;
    UserChapterRecord record = new UserChapterRecord();
    record.setUserId(uid);
    record.setModule(module);
    record.setTypeCode(typeCode);
    record.setCourseId(courseId);
    record.setChapterId(chapterId);
    record.setItemId(itemId);
    record.setStarLevel(starLevel.toString());
    record.setScore(score);
    record.setRobotIdList(robotIdList);
    record.markCreate();
    return record;
  }

  private UserChapterRecord buildFinalExamScore(String uid, String module, String typeCode,
      Long courseId, Long chapterId, Long itemId, Integer starLevel, Double score,
      List<String> robotIdList) {
    if (null == itemId || null == courseId || null == starLevel) return null;
    UserChapterRecord record = new UserChapterRecord();
    record.setUserId(uid);
    record.setModule(module);
    record.setTypeCode(typeCode);
    record.setCourseId(courseId);
    record.setChapterId(chapterId);
    record.setItemId(itemId);
    record.setStarLevel(starLevel.toString());
    record.setScore(score);
    record.setRobotIdList(robotIdList);
    record.markCreate();
    return record;
  }

  /**
   * 记录练习历史
   * 
   * @param pojo 结果参数
   * @param paper 考卷
   * @param detail
   */
  private UserExamRecord recordExamHis(ExamResultPojo pojo, QuesbkExamPaper paper,
      List<AnswerItem> answerItemList, UserExamRecordDetail detail, MessageBox messageBox) {
    UserExamRecord userExamRecord = new UserExamRecord();
    userExamRecord.setUserId(pojo.getUid());
    userExamRecord.setExamTypeId(paper.getExamTypeId());
    userExamRecord.setCourseId(pojo.getCourseId());
    userExamRecord.setPaperNo(pojo.getPaperId());
    userExamRecord.setExamName(paper.getExamName());
    userExamRecord.setQuestions(FastJsonUtil.toJson(answerItemList));
    userExamRecord.setExamTime(new Date());
    userExamRecord.setExamCost(Long.valueOf(pojo.getExamTime()));
    userExamRecord.setExamDetail(FastJsonUtil.toJson(detail));
    if (ExamSubmitStatus.SUBMIT.getCode().equals(pojo.getExamStatus())
        && StringUtils.isNotBlank(pojo.getPaperId()) && null != detail.getScore()) {
      userExamRecord.setMyScore(detail.getScore());
    }
    // queueService.addExamRecord(userExamRecord);
    messageBox.addTargetLevelMessage(MessageBox.FIRST_LEVEL, Message.ExamRecord.toString(),
        userExamRecord);
    UserChapterRecord chapterRecord = null;
    if (ExamType.CHAPTER_BREAKTHROUGH_PAPER.getCode().equals(paper.getExamTypeId().toString())
        || ExamType.ITEM_BREAKTHROUGH_PAPER.getCode().equals(paper.getExamTypeId().toString())) {
      // 记录章节闯关记录
      chapterRecord =
          buildChapterScore(pojo.getUid(), pojo.getModule(), pojo.getTypeCode(),
              paper.getCourseId(), paper.getChapterId(), paper.getItemId(), detail.getStarLevel(),
              detail.getScore(),
              quesChallengeService.getRobotIdList(pojo.getTypeCode(), pojo.getCourseId()));
    }
    if (ExamType.FINAL_EXAM_PAPER.getCode().equals(paper.getExamTypeId().toString())) {
      // 记录期末记录
      chapterRecord =
          buildFinalExamScore(pojo.getUid(), pojo.getModule(), pojo.getTypeCode(),
              paper.getCourseId(), paper.getChapterId(), paper.getItemId(), detail.getStarLevel(),
              detail.getScore(),
              quesChallengeService.getRobotIdList(pojo.getTypeCode(), pojo.getCourseId()));
    }
    /** 记录个人练习历史 */
    // processExamTotal(pojo, detail, tag, chapterRecord);
    if (chapterRecord != null) {
      if (chapterRecord.getItemId() == null) {
        chapterRecord.setItemId(chapterRecord.getChapterId());
      }
      messageBox.addTargetLevelMessage(MessageBox.FIRST_LEVEL,
          Message.UserChapterRecord.toString(), chapterRecord);
    }
    processExamTotal(pojo, detail, chapterRecord, messageBox);
    return userExamRecord;
  }

  /**
   * 处理练习历史方法
   * 
   * @param uid 用户ID
   * @param module 模块ID
   * @param courseId 课程ID
   * @param detail 练习详情
   */
  private void processExamTotal(ExamResultPojo pojo, UserExamRecordDetail detail,
  // UserChapterRecord chapterRecord) {
      UserChapterRecord chapterRecord, MessageBox messageBox) {
    UserExamTotal myExamTotal = new UserExamTotal();
    myExamTotal.setUserId(pojo.getUid());
    myExamTotal.setModule(pojo.getModule());
    myExamTotal.setCourseId(pojo.getCourseId());
    myExamTotal.setMajorId(pojo.getMajorId());
    myExamTotal.setTotalQues(detail.getFinishCount());
    myExamTotal.setRightQues(detail.getRightCount());
    messageBox.addTargetLevelMessage(MessageBox.SECOND_LEVEL, Message.ExamTotal.toString(),
        myExamTotal);
  }

  /**
   * 判断该题是否做错
   * 
   * @param quesbkQues
   * @param answerIdList
   * @return
   */
  private boolean isWrong(QuesbkQues quesbkQues, List<String> answerIdList) {
    if (StringUtils.isBlank(quesbkQues.getAnswerIds())) return false;
    if (null == answerIdList || answerIdList.size() == 0) return true;
    List<String> rightAnswerList =
        FastJsonUtil.fromJsons(quesbkQues.getAnswerIds(), new TypeReference<List<String>>() {});
    Set<Object> rightAnswerSet = Sets.newHashSet(rightAnswerList.toArray());
    for (String myAnswer : answerIdList) {
      if (!rightAnswerSet.contains(myAnswer)) return true;
    }
    return false;
  }

  /**
   * 将错题放入错题集中
   * 
   * @param pojo
   * @param paper
   * @param quesbkQues
   * @param answerItem
   */
  private void recordWrong(ExamResultPojo pojo, QuesbkExamPaper paper, QuesbkQues quesbkQues,
  // AnswerItem answerItem, Boolean isWrong, String tag, Set<Long> idSet) {
      AnswerItem answerItem, Boolean isWrong, Set<Long> idSet, MessageBox messageBox) {
    if (null == idSet || idSet.isEmpty()) return;
    if (!idSet.contains(quesbkQues.getQuestionType())) return;
    UserWrongRecord userWrongRecord = new UserWrongRecord();
    userWrongRecord.setUserId(pojo.getUid());
    userWrongRecord.setModule(pojo.getModule());
    userWrongRecord.setMajorId(pojo.getMajorId());
    userWrongRecord.setCourseId(pojo.getCourseId());
    userWrongRecord.setChapterId(quesbkQues.getChapterId());
    userWrongRecord.setQuestionId(Long.parseLong(quesbkQues.getId().toString()));
    userWrongRecord.setWrongAnswer(FastJsonUtil.toJson(answerItem.getAnswerIdList()));
    userWrongRecord.setExamTime(new Date());
    userWrongRecord.setWrong(isWrong);
    // userWrongRecord.setTag(tag);
    if (isWrong) {
      userWrongRecord.addWrongTimes();
    } else {
      userWrongRecord.addRightTimes();
    }
    // 记录错题队列中
    // queueService.addWrongQues(userWrongRecord);
    messageBox.addTargetLevelMessage(MessageBox.FIRST_LEVEL, Message.ExamWrongRecord.toString(),
        userWrongRecord);
  }

  /**
   * 7 错题集 · 说明 获取用户错题记录集。 · url /quesbk/wrong_ques_list
   * 
   * @param pojo
   */
  public WrongQuesListResponse wrongQuesList(WrongQuesListPojo pojo) {
    if (!checkCourseId(pojo, pojo.getCourseId()))
      return new WrongQuesListResponse(ResultType.UNVALIDCOURSEID);
    WrongQuesListResponse response = new WrongQuesListResponse(ResultType.SUCCESS);
    List<UserWrongRecordCollect> userWrongRecordList =
        quesOperationFacade.queryWrongRecordCollectList(pojo.getUid(), pojo.getCourseId());
    if (null != userWrongRecordList && userWrongRecordList.size() > 0) {
      List<WrongQuesListResponse.ChapterInfo> chapterList = Lists.newArrayList();
      // 此Map用于保存父章节信息，用于后续组织列表时查询使用
      Map<Long, WrongQuesListResponse.ChapterInfo> _parentMap = Maps.newHashMap();
      for (UserWrongRecordCollect wrongRecord : userWrongRecordList) {
        if (wrongRecord.getQuestionNumber() == 0 || wrongRecord.getChapterId() == null) continue;
        WrongQuesListResponse.ChapterInfo parentChapter = null;
        INIT_CHAPTER: {
          if (wrongRecord.getParentChapter() == 0) {
            parentChapter = _parentMap.get(wrongRecord.getChapterId());
            if (null != parentChapter) break INIT_CHAPTER;
            parentChapter = new WrongQuesListResponse.ChapterInfo();
            parentChapter.setChapterId(wrongRecord.getChapterId());
            parentChapter.setChapterName(wrongRecord.getChapterName());
            parentChapter.setListOrder(wrongRecord.getListOrder());
            _parentMap.put(parentChapter.getChapterId(), parentChapter);
          } else {
            parentChapter = _parentMap.get(wrongRecord.getParentChapter());
            if (null != parentChapter) break INIT_CHAPTER;
            parentChapter = new WrongQuesListResponse.ChapterInfo();
            parentChapter.setChapterId(wrongRecord.getParentChapter());
            parentChapter.setChapterName(wrongRecord.getParentChapterName());
            parentChapter.setListOrder(wrongRecord.getParentListOrder());
            _parentMap.put(parentChapter.getChapterId(), parentChapter);
          }
        }
        // 更新父章节的问题数量
        parentChapter.setQuesCount(parentChapter.getQuesCount() + wrongRecord.getQuestionNumber());
        // 未掌握问题数量
        parentChapter.setUncatchQuesCount(parentChapter.getUncatchQuesCount()
            + wrongRecord.getUncatchQuesCount());
        // 待强化问题数量
        parentChapter.setCatchingQuesCount(parentChapter.getCatchingQuesCount()
            + wrongRecord.getCatchingQuesCount());
        // 已消灭问题数量
        parentChapter.setCatchedQuesCount(parentChapter.getCatchedQuesCount()
            + wrongRecord.getCatchedQuesCount());
      }
      List<ChapterInfo> values = Lists.newArrayList(_parentMap.values());
      Collections.sort(values, new Comparator<ChapterInfo>() {
        @Override
        public int compare(ChapterInfo o1, ChapterInfo o2) {
          if (o2 == null || o2.getListOrder() == null) return 1;
          if (o1 == null || o1.getListOrder() == null) return -1;
          return (int) (o2.getListOrder() - o1.getListOrder());
        }
      });
      chapterList.addAll(values);
      response.setCourseItemList(chapterList);
    }
    return response;
  }


  /**
   * 8 练习记录 · 说明 获取用户练习列表。 · url /quesbk/exam_his_list
   * 
   * @param pojo
   */
  public ExamHisListResponse examHisList(ExamHisList pojo) {
    if (!checkCourseId(pojo, pojo.getCourseId()))
      return new ExamHisListResponse(ResultType.UNVALIDCOURSEID);
    ExamHisListResponse response = new ExamHisListResponse(ResultType.SUCCESS);
    List<ExamHisListResponse.Exam> examList = new ArrayList<>();
    List<UserExamRecord> recordList =
        quesOperationFacade.queryExamRecordList(pojo.getUid(), pojo.getCourseId());
    for (UserExamRecord record : recordList) {
      ExamHisListResponse.Exam exam = new ExamHisListResponse.Exam(record);
      examList.add(exam);
    }
    response.setExamList(examList);
    return response;
  }

  /**
   * 11 错题详情展示 ·说明 根据题目ID展示用户错题详细信息。 ·url /quesbk/wrong_ques_detail
   * 
   * @param pojo
   */
  public WrongQuesDetailResponse wrongQuesDetail(WrongQuesDetailPojo pojo) {
    if (!checkCourseId(pojo, pojo.getCourseId()))
      return new WrongQuesDetailResponse(ResultType.UNVALIDCOURSEID);
    WrongQuesDetailResponse response = new WrongQuesDetailResponse(ResultType.SUCCESS);
    List<String> chapterIdList = Lists.newArrayList();
    if (StringUtils.isNotBlank(pojo.getItemId())) {
      chapterIdList.add(pojo.getItemId());
    } else {
      // 查询子章节列表
      List<CourseProductItem> chapterList =
          quesOperationFacade.queryItemList(pojo.getCourseId(), pojo.getChapterId());
      chapterIdList.add(pojo.getChapterId());
      if (null != chapterList && chapterList.size() > 0) {
        for (CourseProductItem chapter : chapterList) {
          chapterIdList.add(chapter.getId().toString());
        }
      }
    }
    // 查询所属章节与子章节问题
    Map<String, Object> cond = Maps.newHashMap();
    cond.put("userId", pojo.getUid());
    cond.put("courseId", pojo.getCourseId());
    // cond.put("rightTimes",
    // StaticInfoProp.getWrongLimit());//1在错题集里面改状态的操作由前段来做2根据状态就可以区分错题类型了
    cond.put("wrongTimesLower", 0);// 错题数至少要大于0才属于错题
    cond.put("chapterIdList", chapterIdList);
    cond.put("wrongStatus", pojo.getType());

    Page<UserWrongRecord> userWrongRecordList =
        quesOperationFacade.queryWrongRecordList(cond, null);
    if (userWrongRecordList.getResult() != null && userWrongRecordList.getResult().size() > 0) {
      List<WrongQuesDetailResponse.WrongQuestion> quesList =
          packageQuestionList(userWrongRecordList.getResult());
      // packageQuestionList(userWrongRecordList.getResult(),
      // new WrongCheckStoreRule(pojo.getUid(), pojo.getCourseId()));
      Collections.sort(quesList, new Comparator<WrongQuesDetailResponse.WrongQuestion>() {
        @Override
        public int compare(WrongQuesDetailResponse.WrongQuestion o1,
            WrongQuesDetailResponse.WrongQuestion o2) {
          return o1.getWrongStatus().compareTo(o2.getWrongStatus());
        }
      });
      response.setQuestionList(quesList);
    }
    return response;
  }

  /**
   * 获取课程问题类型名称
   * 
   * @param courseId
   * @return
   */
  private Map<Long, String> getTypeMap() {
    Map<Long, String> typeMap = Maps.newHashMap();
    for (QuesbkQuesType type : quesOperationFacade.queryQuesTypeList()) {
      if (null == type || null == type.getId()) continue;
      typeMap.put(type.getId(), type.getTypeName());
    }
    return typeMap;
  }

  /**
   * 13 获取做题情况 · 说明 获取用户今天的做题情况统计。 · url /quesbk/daily_exam
   * 
   * @param pojo
   */
  /*
   * public DailyExamResponse dailyExam(DailyExamPojo pojo) { DailyExamResponse result = new
   * DailyExamResponse(ResultType.SUCCESS); List<String> courseIdList = Lists.newArrayList(); if
   * (StringUtils.isBlank(pojo.getCourseId())) { courseIdList = Lists.transform(
   * quesOperationFacade.queryProductList(pojo.getModule(), pojo.getTypeCode()), new
   * Function<CourseProduct, String>() {
   * 
   * @Override public String apply(CourseProduct input) { if (null != input && null !=
   * input.getId()) return input.getId().toString(); return StringUtils.EMPTY; } }); } else {
   * courseIdList.add(pojo.getCourseId()); } List<String> quesIdList =
   * quesOperationFacade.queryTodayQuesIdList(pojo.getUid(), courseIdList); if (null != quesIdList
   * && quesIdList.size() != 0) { Map<String, Object> cond = Maps.newHashMap(); cond.put("userId",
   * pojo.getUid()); cond.put("questionIdList", quesIdList); Page<UserWrongRecord> quesList =
   * quesOperationFacade.queryAbstractWrongRecordList(cond, null); if (null == quesList || null ==
   * quesList.getResult() || quesList.getResult().size() == 0) return result;
   * result.getDailyExamDetail().setTotalQues(String.valueOf (quesList.getResult().size()));
   * result.getDailyExamDetail().setRightQues(getRightCount (quesList.getResult())); }
   * 
   * Integer totalQues = 0, rightQues = 0; for (String courseId : courseIdList) { UserExamTotal
   * examTotal = quesOperationFacade.queryExamTotal(pojo.getUid(), courseId); if (null != examTotal)
   * { totalQues += examTotal.getTotalQues(); rightQues += examTotal.getRightQues(); } }
   * result.getTotalExamDetail().setTotalQues(String.valueOf(totalQues));
   * result.getTotalExamDetail().setRightQues(String.valueOf(rightQues)); return result; }
   */

  /**
   * 获取上次做对题目数量
   * 
   * @param quesList
   * @return
   */
  @SuppressWarnings("unused")
  private String getRightCount(List<UserWrongRecord> quesList) {
    Integer count = 0;
    for (UserWrongRecord ques : quesList) {
      if (null == ques || null == ques.getRightTimes()) continue;
      if (ques.getRightTimes() > 0) count++;
    }
    return count.toString();
  }

  /**
   * 出题接口包装问题方法
   */
  @SuppressWarnings({"unchecked", "rawtypes"})
  private List<Question> packageQuestionList(List quesList,
      final Map<Long, AnswerItem> myAnswerList, final Map<Long, AnswerItem> opponentsAnswerList) {
    // final Map<Long, AnswerItem> myAnswerList, final Map<Long, AnswerItem> opponentsAnswerList,
    // final CheckStoreRule checkRule) {
    return packageQuestionList(quesList, new Function<Object, ExamDetailResponse.Question>() {
      @Override
      public Question apply(Object quesbkQues) {
        try {
          if (quesbkQues instanceof String) {
            String sQuesbkQues = (String) quesbkQues;
            if (StringUtils.isNotBlank(sQuesbkQues)) {
              ExamDetailResponse.Question question = new ExamDetailResponse.Question(sQuesbkQues);
              return question;
            }
          }
          if (quesbkQues instanceof QuesbkQues) {
            QuesbkQues ques = (QuesbkQues) quesbkQues;
            ExamDetailResponse.Question question = new ExamDetailResponse.Question(ques);
            if (null != myAnswerList && null != myAnswerList.get(ques.getId())) {
              question.setMyAnswerIds(myAnswerList.get(ques.getId()).getAnswerIdList());
              if (StringUtils.isNotBlank(myAnswerList.get(ques.getId()).getExamStatus()))
                question.setExamStatus(myAnswerList.get(ques.getId()).getExamStatus());
            } else {
              question.setMyAnswerIds(null);
            }
            if (null != opponentsAnswerList && null != opponentsAnswerList.get(ques.getId())) {
              question.setOpponentsIds(opponentsAnswerList.get(ques.getId()).getAnswerIdList());
            } else {
              question.setOpponentsIds(null);
            }
            // 如果收藏列表不为空,判断收藏状态
            // if (checkRule.needCheck()) question.setStoreStatus(checkRule.check(ques));
            return question;
          }
          return null;
        } catch (Exception e) {
          LoggerUtil.error("题目解析异常." + quesbkQues.toString(), e);
          return null;
        }
      }
    });
  }

  /**
   * 收藏详情接口包装问题方法
   */
  /*
   * private List<StoreQuesDetailResponse.StoreQuestion> packageQuestionList(List<QuesbkQues>
   * quesList) { final Map<Long, String> typeMap = getTypeMap(); return
   * packageQuestionList(quesList, new Function<QuesbkQues, StoreQuesDetailResponse.StoreQuestion>()
   * {
   * 
   * @Override public StoreQuesDetailResponse.StoreQuestion apply(QuesbkQues quesbkQues) { try {
   * quesbkQues.setQuestionTypeName(typeMap.get(quesbkQues.getQuestionType())); return new
   * StoreQuesDetailResponse.StoreQuestion(quesbkQues); } catch (Exception e) {
   * LoggerUtil.error("题目解析异常." + quesbkQues.toString(), e); return null; } } }); }
   */

  /**
   * 错题接口包装问题方法
   */
  private List<WrongQuesDetailResponse.WrongQuestion> packageQuestionList(
      List<UserWrongRecord> quesList) {
    // List<UserWrongRecord> quesList, final WrongCheckStoreRule checkRule) {
    final Map<Long, String> typeMap = getTypeMap();
    return packageQuestionList(quesList,
        new Function<UserWrongRecord, WrongQuesDetailResponse.WrongQuestion>() {
          @Override
          public WrongQuesDetailResponse.WrongQuestion apply(UserWrongRecord quesbkQues) {
            try {
              quesbkQues.getQuesDetail().setQuestionTypeName(
                  typeMap.get(quesbkQues.getQuesDetail().getQuestionType()));
              WrongQuesDetailResponse.WrongQuestion question =
                  new WrongQuesDetailResponse.WrongQuestion(quesbkQues.getQuesDetail());
              // // 如果收藏列表不为空,判断收藏状态
              // if (checkRule.needCheck())
              // question.setStoreStatus(checkRule.check(quesbkQues.getQuesDetail()));
              return question;
            } catch (Exception e) {
              LoggerUtil.error("题目解析异常." + quesbkQues.toString(), e);
              return null;
            }
          }
        });
  }

  /**
   * 错题接口包装问题方法
   */
  @SuppressWarnings("unused")
  private List<WrongQuesDetailResponse.WrongQuestion> packageWrongQuestionList(
      List<QuesbkQues> quesList) {
    final Map<Long, String> typeMap = getTypeMap();
    return packageQuestionList(quesList,
        new Function<QuesbkQues, WrongQuesDetailResponse.WrongQuestion>() {
          @Override
          public WrongQuesDetailResponse.WrongQuestion apply(QuesbkQues quesbkQues) {
            try {
              quesbkQues.setQuestionTypeName(typeMap.get(quesbkQues.getQuestionType()));
              return new WrongQuesDetailResponse.WrongQuestion(quesbkQues);
            } catch (Exception e) {
              LoggerUtil.error("题目解析异常." + quesbkQues.toString(), e);
              return null;
            }
          }
        });
  }

  /**
   * 包装问题列表基础方法
   */
  private <T, F> List<T> packageQuestionList(List<F> quesList, Function<F, T> transFunc) {
    // 如果问题列表不为空,组装试卷
    if (quesList != null && quesList.size() > 0) {
      List<T> resultLst = Lists.newArrayList();
      for (F ques : quesList) {
        T toQues = transFunc.apply(ques);
        if (null != toQues) resultLst.add(toQues);
      }
      return resultLst;
    }
    return null;
  }

  /***********************************************/
  /** 这里使用内部类主要是为了直接使用QuesOperationFacade **/
  /***********************************************/

  /**
   * @name ICheckStoreRule CopyRright (c) 2015 by <a
   *       href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
   * 
   * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
   * @date 2015年7月3日
   * @description 校验收藏状态规则
   * @version 1.0
   */
  /*
   * private interface ICheckStoreRule { public Boolean needCheck();
   * 
   * public String check(QuesbkQues quesbkQues); }
   */
  /**
   * @name @see com.xiaodou.service.CheckStoreRule.java
   * @CopyRright (c) 2015 by XiaoDou NetWork Technology
   * 
   * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
   * @date 2015年8月7日
   * @description 普通问题收藏规则
   * @version 1.0
   */
  /*
   * private class CheckStoreRule implements ICheckStoreRule { String uid; String courseId; private
   * Set<Long> storeQues = Sets.newHashSet(); private HashMap<Long, UserStoreRecord> storeQuesMap =
   * Maps.newHashMap(); private Boolean isInited = false;
   * 
   * public CheckStoreRule(String uid, String courseId) { this.uid = uid; this.courseId = courseId;
   * }
   * 
   * private void init() { // 获取用户收藏列表 if (StringUtils.isNotBlank(uid) &&
   * StringUtils.isNotBlank(courseId)) { List<UserStoreRecord> userStoreRecords =
   * quesOperationFacade.queryStoreRecordList(uid, courseId); if (null != userStoreRecords &&
   * userStoreRecords.size() > 0) { for (UserStoreRecord storeRecord : userStoreRecords) { if (null
   * == storeRecord || null == storeRecord.getQuestionId()) continue; if
   * (!storeQues.contains(storeRecord.getQuestionId())) {
   * storeQues.add(storeRecord.getQuestionId()); storeQuesMap.put(storeRecord.getQuestionId(),
   * storeRecord); } } } } isInited = true; }
   * 
   * @Override public Boolean needCheck() { if (!isInited) init(); return null != storeQues &&
   * storeQues.size() > 0; }
   * 
   * @Override public String check(QuesbkQues quesbkQues) { return
   * storeQues.contains(quesbkQues.getId()) ? storeQuesMap.get(quesbkQues.getId()) .getStoreStatus()
   * : "0"; }
   * 
   * }
   */

  /**
   * @name @see com.xiaodou.service.WrongCheckStoreRule.java
   * @CopyRright (c) 2015 by XiaoDou NetWork Technology
   * 
   * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
   * @date 2015年8月7日
   * @description 错题判断收藏规则
   * @version 1.0
   */
  /*
   * private class WrongCheckStoreRule extends CheckStoreRule { public WrongCheckStoreRule(String
   * uid, String courseId) { super(uid, courseId); } }
   */

  public BaseResponse insertRaiseWrongQues(RaiseWrongQuesRequest pojo) {
    BaseResponse response = new BaseResponse(ResultType.SUCCESS);
    QuesbkQues ques = quesOperationFacade.queryQues(pojo.getQuesId(), pojo.getCourseId());
    if (ques == null) return new BaseResponse(ResultType.UNVALIDQUESINFO);
    RaiseWrongQues raise = new RaiseWrongQues();
    // 1、判断入参
    if (StringUtils.isBlank(pojo.getWrongType()))
      return new BaseResponse(ResultType.NotFindWrongType);
    List<String> wrongTypeList =
        FastJsonUtil.fromJsons(pojo.getWrongType(), new TypeReference<List<String>>() {});
    for (String wrongType : wrongTypeList) {
      switch (wrongType) {
        case QuesBaseConstant.raise_wrong_ques_type_wrongly_written:
          break;
        case QuesBaseConstant.raise_wrong_ques_type_wrongly_content:
          break;
        case QuesBaseConstant.raise_wrong_ques_type_wrongly_answer:
          break;
        case QuesBaseConstant.raise_wrong_ques_type_wrongly_orther:
          if (StringUtils.isBlank(pojo.getWrongMsg()))
            return new BaseResponse(ResultType.WrongMsgNotEmpty);
          raise.setWrongMsg(pojo.getWrongMsg());
          break;
        default:
          return new BaseResponse(ResultType.NotFindWrongType);
      }
    }
    // 2、添加记录
    raise.setQuesId(Long.valueOf(pojo.getQuesId()));
    raise.setUserId(pojo.getUid());
    raise.setChapterId(ques.getChapterId());
    raise.setCourseId(ques.getCourseId());
    raise.setWrongType(pojo.getWrongType());
    if (quesOperationFacade.insertRaiseWrongQues(raise))
      return response;
    else
      return new BaseResponse(ResultType.SYSFAIL);
  }

  public BaseResponse changeWrongQues(UnWrongQuesRequest pojo) {
    // 验证课程id
    if (!checkCourseId(pojo, pojo.getCourseId()))
      return new BaseResponse(ResultType.UNVALIDCOURSEID);
    // 验证章id
    // List<String> chapterIdList =
    // queryChapterIdByCourseId(pojo.getCourseId());
    // if (!chapterIdList.contains(pojo.getChapterId()))
    // return new BaseResponse(ResultType.NotFindChapter);
    // 验证题目id
    QuesbkQues ques = quesOperationFacade.queryQues(pojo.getQuesId(), pojo.getCourseId());
    if (ques == null) return new BaseResponse(ResultType.UNVALIDQUESINFO);
    Map<String, Object> cond = new HashMap<String, Object>();
    cond.put("userId", pojo.getUid());
    cond.put("courseId", ques.getCourseId());
    cond.put("chapterId", ques.getChapterId());
    cond.put("questionId", pojo.getQuesId());
    cond.put("wrongTimesLower", 0);// 错题数至少要大于0才属于错题
    Page<UserWrongRecord> pageList = quesOperationFacade.queryWrongRecordList(cond, null);
    if (null == pageList) return new BaseResponse(ResultType.UNVALIDQUESINFO);
    List<UserWrongRecord> userWrongRecordList = pageList.getResult();
    if (null == userWrongRecordList || userWrongRecordList.size() < 1)
      return new BaseResponse(ResultType.UNVALIDQUESINFO);
    UserWrongRecord record = userWrongRecordList.get(0);
    record.setUserId(pojo.getUid());
    record.setMajorId(pojo.getMajorId());
    record.setModule(pojo.getModule());
    record.setCourseId(ques.getCourseId());
    record.setChapterId(ques.getChapterId());
    record.setQuestionId(Long.valueOf(pojo.getQuesId()));
    record.setWrongStatus(pojo.getType());
    record.setWrong(Boolean.FALSE);
    // quesOperationFacade.changeWrongQues(record);
    // 异步更新错题状态
    MessageBox messageBox = new MessageBox();
    messageBox.addTargetLevelMessage(MessageBox.FIRST_LEVEL, Message.ChangeWrongRecord.toString(),
        record);
    // 异步更新examTotal记录
    UserExamTotal myExamTotal = new UserExamTotal();
    myExamTotal.setUserId(pojo.getUid());
    myExamTotal.setModule(pojo.getModule());
    myExamTotal.setCourseId(pojo.getCourseId());
    myExamTotal.setMajorId(pojo.getMajorId());
    messageBox.addTargetLevelMessage(MessageBox.SECOND_LEVEL, Message.ExamTotal.toString(),
        myExamTotal);
    queueService.sendMessageBox(messageBox);
    return new BaseResponse(ResultType.SUCCESS);
  }

  public CourseStatisticsResponse courseStatistics(CourseStatisticsPojo pojo) {
    if (!checkCourseId(pojo, pojo.getCourseId()))
      return new CourseStatisticsResponse(ResultType.UNVALIDCOURSEID);
    CourseStatisticsResponse response = new CourseStatisticsResponse(ResultType.SUCCESS);
    UserExamTotal myExamTotal =
        quesOperationFacade.queryExamTotal(pojo.getUid(), pojo.getCourseId());
    if (null != myExamTotal) {
      // 我的答题数
      response.setMyQues(myExamTotal.getTotalQues().toString());
      if (StringUtils.isBlank(myExamTotal.getRightPercent())) {
        response.setMyRightPercent("0.00");
      } else {
        response.setMyRightPercent(myExamTotal.getRightPercent());
      }
      // 我的错误答题数
      response.setMyWrongQues(Integer.toString(myExamTotal.getTotalQues()
          - myExamTotal.getRightQues()));
      response.setScore(MathUtil.getIntStringValue(myExamTotal.getScore()));
    }
    return response;
  }

  public PerformanceDetailResponse performanceDetail(PerformanceDetailPojo pojo) {
    PerformanceDetailResponse response = new PerformanceDetailResponse(ResultType.SUCCESS);
    if (!checkOrderCourseIdOverTypeCode(pojo, pojo.getCourseId()))
      return new PerformanceDetailResponse(ResultType.UNVALIDCOURSEID4TARGETUSER);
    Double bonusScore = 0d;
    GET_BONUSSCORE: {
      if (null == pojo || StringUtils.isBlank(pojo.getBonusId())) break GET_BONUSSCORE;
      RedBonus bonus = quesOperationFacade.queryRedBonusById(pojo.getBonusId());
      if (null == bonus || StringUtils.isJsonBlank(bonus.getBonusDetail())) break GET_BONUSSCORE;
      CourseBonus courseBonus = FastJsonUtil.fromJson(bonus.getBonusDetail(), CourseBonus.class);
      if (null == courseBonus || null == courseBonus.getCourseScore()) break GET_BONUSSCORE;
      response.setAward(courseBonus.getCourseScore());
      return response;
    }
    UserExamTotal examTotal = quesOperationFacade.queryExamTotal(pojo.getUid(), pojo.getCourseId());
    CourseScore courseScore = new CourseScore();
    courseScore.init(pojo.getModule(), pojo.getCourseId(), pojo.getUid(), examTotal, null);
    ADD_TO_ITEM: {
      int itemCount = courseScore.getItemCount();
      Double itemTotal = 100d * itemCount;
      Double examScore = 0d;
      if (null != examTotal && null != examTotal.getScore()) examTotal.getScore();
      Double itemCurr = examScore * itemCount;
      Double itemSurp = itemTotal - itemCurr;
      Map<String, Object> cond = Maps.newHashMap();
      cond.put("userId", pojo.getUid());
      cond.put("courseId", pojo.getCourseId());
      List<UserChapterRecord> chaperRecordList = quesOperationFacade.queryUserChapterRecord(cond);
      if (null == chaperRecordList || chaperRecordList.size() == 0) break ADD_TO_ITEM;
      for (UserChapterRecord chapterRecord : chaperRecordList) {
        if (null == chapterRecord) continue;
        ItemScore itemScore = new ItemScore();
        if (null != chapterRecord.getItemId())
          itemScore = courseScore.getItem(chapterRecord.getItemId().toString());
        if (null != itemScore && null != chapterRecord.getScore()) {
          itemScore.setOriginalScore(MathUtil.getIntStringValue(chapterRecord.getScore()));
          if (bonusScore > 0d)
            itemScore.setBonusScore(MathUtil.getIntStringValue(bonusScore
                * (100d - chapterRecord.getScore()) / itemSurp));
        }
      }
    }
    response.setAward(courseScore);
    return response;
  }



  public BaseResponse quesAudioLogAdd(QuesAudioLogPojo pojo) {
    if (!checkCourseId(pojo, pojo.getCourseId())) {
      return new ExamDetailResponse(ResultType.UNVALIDCOURSEID);
    }
    BaseResponse response = new BaseResponse(ResultType.SUCCESS);
    CourseProduct product = quesOperationFacade.queryProduct(pojo.getCourseId());
    if (product == null) {
      response = new BaseResponse(ResultType.NOCOURSE);
      return response;
    }
    QuesbkQues ques = quesOperationFacade.queryQues(pojo.getQuesId(), pojo.getCourseId());
    if (ques == null) {
      response = new BaseResponse(ResultType.NOQUES);
      return response;
    }
    if (ques.getQuestionType().toString().equals("8")
        || ques.getQuestionType().toString().equals("15")) {
      QuesbkAudioLog log = new QuesbkAudioLog();
      log.setProductId(pojo.getCourseId());
      log.setCategoryId(Integer.valueOf(pojo.getMajorId()));
      if (ques.getKeyPoint() != null) {
        List<QuesbkQues.KeyPoint> keyPointList = Lists.newArrayList();
        List<CourseKeyword> qkeyPointList =
            FastJsonUtil.fromJsons(ques.getKeyPoint(), new TypeReference<List<CourseKeyword>>() {});
        for (CourseKeyword keyPoint : qkeyPointList) {
          keyPointList.add(new KeyPoint(keyPoint)); // 考点列表
        }
        log.setExamPoint(FastJsonUtil.toJson(keyPointList));
      }
      log.setProductImageUrl(product.getImageUrl());
      log.setProductName(product.getName());
      log.setQuesAnswer(ques.getManalyze());
      log.setQuesAudioUrl(pojo.getQuesVideoUrl());
      log.setQuesDetail(ques.getMdesc());
      log.setQuesId(pojo.getQuesId());
      log.setQuesType(ques.getQuestionType().toString());
      log.setSubmitTime(new Timestamp(System.currentTimeMillis()));
      log.setTraceId(pojo.getTraceId());
      log.setUserId(pojo.getUid());
      quesOperationFacade.insertQuesVideoLog(log);
      return response;
    } else {
      response = new BaseResponse(ResultType.NOTYPE);
      return response;
    }
  }


  public QuesAudioLogListResponse getQuesAudioLogList(QuesAudioLogListPojo pojo) {
    Integer count = quesOperationFacade.selectCountQuesVideoLogListByUserId(pojo.getUid());
    QuesAudioLogListResponse response = new QuesAudioLogListResponse(ResultType.SUCCESS);
    response.setLogCount(count);
    if (pojo.getQuesId() != null) {
      QuesbkAudioLog ques =
          quesOperationFacade.findQuesVideoLogById(pojo.getQuesId(), pojo.getUid());
      if (ques == null) {
        response = new QuesAudioLogListResponse(ResultType.NOAUDIO);
        return response;
      } else {
        List<QuesbkAudioLog> list =
            quesOperationFacade
                .selectQuesVideoLogListByUserIdAndId(pojo.getUid(), pojo.getQuesId());
        for (QuesbkAudioLog log : list) {
          QuesbkAudioLogVo vo = new QuesbkAudioLogVo(log);
          response.getList().add(vo);
        }
        return response;
      }
    } else {
      List<QuesbkAudioLog> list = quesOperationFacade.selectQuesVideoLogListByUserId(pojo.getUid());
      if (null != list && !list.isEmpty()) {
        for (QuesbkAudioLog log : list) {
          if (null == log) {
            continue;
          }
          QuesbkAudioLogVo vo = new QuesbkAudioLogVo(log);
          response.getList().add(vo);
        }
      }
      return response;
    }

  }

  public QuesAudioLogResponse getQuesAudioLogDetail(QuesAudioLogDetailPojo pojo) {
    QuesAudioLogResponse response = new QuesAudioLogResponse(ResultType.SUCCESS);
    QuesbkAudioLog log = quesOperationFacade.findQuesVideoLogById(pojo.getQuesId(), pojo.getUid());
    if (log == null) {
      return new QuesAudioLogResponse(ResultType.NOAUDIO);
    }
    QuesbkAudioLogVo vo = new QuesbkAudioLogVo(log);
    response.setLog(vo);
    return response;
  }

  public QuesAudioLogCountResponse getAudioCount(QuesAudioLogListPojo pojo) {
    QuesAudioLogCountResponse response = new QuesAudioLogCountResponse(ResultType.SUCCESS);
    Integer count = quesOperationFacade.selectCountQuesVideoLogListByUserId(pojo.getUid());
    if (count != null) {
      response.setAudioCount(count.toString());
    }
    return response;
  }

  public ProductScorePointRuleResponse scorePointRule(ScorePointRulePojo pojo) {
    ProductScorePointRuleResponse response = new ProductScorePointRuleResponse(ResultType.SUCCESS);
    ProductScorePointRule rule =
        quesOperationFacade.selectProductScorePointRuleByModule(pojo.getModule());
    if (rule != null) {
      response.setScorePointRule(rule);
    }
    return response;
  }

}
