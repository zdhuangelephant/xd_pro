package com.xiaodou.resources.service.quesbk;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.TypeReference;
import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.xiaodou.common.util.CommUtil;
import com.xiaodou.common.util.DateUtil;
import com.xiaodou.common.util.MathUtil;
import com.xiaodou.common.util.RandomUtil;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.mission.engine.event.CollectionEvent;
import com.xiaodou.mission.engine.event.EventBuilder;
import com.xiaodou.resources.cache.quesbk.QuesExamCache;
import com.xiaodou.resources.constant.quesbk.QuesBaseConstant;
import com.xiaodou.resources.constant.quesbk.ResultType;
import com.xiaodou.resources.enums.product.ResourceType;
import com.xiaodou.resources.enums.quesbk.ExamSubmitStatus;
import com.xiaodou.resources.enums.quesbk.ExamType;
import com.xiaodou.resources.model.product.ProductItemModel;
import com.xiaodou.resources.model.quesbk.QuesbkExamPaper;
import com.xiaodou.resources.model.quesbk.QuesbkQues;
import com.xiaodou.resources.model.quesbk.QuesbkQuesType;
import com.xiaodou.resources.model.quesbk.RaiseWrongQues;
import com.xiaodou.resources.model.quesbk.TaskScoreModel;
import com.xiaodou.resources.model.quesbk.UserChapterRecord;
import com.xiaodou.resources.model.quesbk.UserExamRecord;
import com.xiaodou.resources.model.quesbk.UserExamRecordDetail;
import com.xiaodou.resources.model.quesbk.UserExamTotal;
import com.xiaodou.resources.model.quesbk.UserStoreRecord;
import com.xiaodou.resources.model.quesbk.UserStoreRecordCollect;
import com.xiaodou.resources.model.quesbk.UserWrongRecord;
import com.xiaodou.resources.model.quesbk.UserWrongRecordCollect;
import com.xiaodou.resources.request.quesbk.CourseStatisticsPojo;
import com.xiaodou.resources.request.quesbk.ExamDetailPojo;
import com.xiaodou.resources.request.quesbk.ExamHisList;
import com.xiaodou.resources.request.quesbk.ExamHistoryPojo;
import com.xiaodou.resources.request.quesbk.ExamResultPojo;
import com.xiaodou.resources.request.quesbk.ExamResultPojo.AnswerItem;
import com.xiaodou.resources.request.quesbk.PerformanceDetailPojo;
import com.xiaodou.resources.request.quesbk.RaiseWrongQuesRequest;
import com.xiaodou.resources.request.quesbk.StoreQuesDetailPojo;
import com.xiaodou.resources.request.quesbk.StoreQuesListPojo;
import com.xiaodou.resources.request.quesbk.StoreQuesPojo;
import com.xiaodou.resources.request.quesbk.StoreQuesRequest;
import com.xiaodou.resources.request.quesbk.UnWrongQuesRequest;
import com.xiaodou.resources.request.quesbk.WrongQuesDetailPojo;
import com.xiaodou.resources.request.quesbk.WrongQuesListPojo;
import com.xiaodou.resources.response.BaseResponse;
import com.xiaodou.resources.response.quesbk.CourseScore;
import com.xiaodou.resources.response.quesbk.CourseScore.ItemScore;
import com.xiaodou.resources.response.quesbk.CourseStatisticsResponse;
import com.xiaodou.resources.response.quesbk.ExamDetailResponse;
import com.xiaodou.resources.response.quesbk.ExamDetailResponse.Question;
import com.xiaodou.resources.response.quesbk.ExamHisListResponse;
import com.xiaodou.resources.response.quesbk.ExamHistoryResponse;
import com.xiaodou.resources.response.quesbk.ExamHistoryResponse.ExamInfo;
import com.xiaodou.resources.response.quesbk.ExamHistoryResponse.ExamRecordInfo;
import com.xiaodou.resources.response.quesbk.PerformanceDetailResponse;
import com.xiaodou.resources.response.quesbk.StoreQuesDetailResponse;
import com.xiaodou.resources.response.quesbk.StoreQuesListResponse;
import com.xiaodou.resources.response.quesbk.SubmitResultResponse;
import com.xiaodou.resources.response.quesbk.WrongQuesDetailResponse;
import com.xiaodou.resources.response.quesbk.WrongQuesListResponse;
import com.xiaodou.resources.util.QuesCaculateUtil;
import com.xiaodou.resources.util.QuesCaculateUtil.IQuesCaculateTask;
import com.xiaodou.resources.vo.quesbk.ExamDetail;
import com.xiaodou.resources.vo.task.WrongQuesCountVo;
import com.xiaodou.summer.dao.pagination.Page;
import com.xiaodou.summer.dao.param.IQueryParam;
import com.xiaodou.summer.dao.param.QueryParam;

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

  public ExamDetailResponse examDetail0(ExamDetailPojo pojo) throws Exception {
    ExamDetailResponse response = new ExamDetailResponse(ResultType.SUCCESS);
    // 查询ProductItem
    ProductItemModel item = quesOperationFacade.queryItem(pojo.getProductItemId());
    if (null == item) return new ExamDetailResponse(ResultType.UNVALIDPROITEMID);
    if (null == item.getProductId() || !pojo.getProductId().equals(item.getProductId().toString())) {
      return new ExamDetailResponse(ResultType.EXAMMATCHEDCOURSEIDFAILED);
    }
    Map<Long, AnswerItem> opponentsAnswerMap = null;
    QuesbkExamPaper paper = null;
    List<Object> quesList = Lists.newArrayList();
    ResourceType resourceType = ResourceType.getByTypeId(item.getResourceType().intValue());
    ExamDetail examDetailVo = new ExamDetail(pojo.getUid());
    switch (resourceType) {
      case QUIZ: // 随堂练习
        examDetailVo.setCourseId(item.getProductId().toString());
        examDetailVo.setItemId(item.getParentId().toString());
        examDetailVo.setExamType(ExamType.HOMEWORK_PAPER.getCode());
        examDetailVo.setLastQuesId(pojo.getLastQuesId());
        paper = new QuesbkExamPaper(ExamType.HOMEWORK_PAPER, pojo.getProductId());
        break;
      case EXAM: // 单元测验
        examDetailVo.setCourseId(item.getProductId().toString());
        examDetailVo.setChapterId(item.getParentId().toString());
        examDetailVo.setExamType(ExamType.CHAPTER_PAPER.getCode());
        paper = new QuesbkExamPaper(ExamType.CHAPTER_PAPER, pojo.getProductId());
        break;
      case FINAL: // 期末考试
        examDetailVo.setCourseId(item.getProductId().toString());
        examDetailVo.setExamType(ExamType.APTITUDE_PAPER.getCode());
        paper = new QuesbkExamPaper(ExamType.APTITUDE_PAPER, pojo.getProductId());
        break;
      default: // 其它练习
        return new ExamDetailResponse(ResultType.UNVALIDEXAMTYPE);
    }
    quesList = quesOperationFacade.queryExamQuestionIdList(examDetailVo, paper);
    if (quesList == null || quesList.size() == 0)
      return new ExamDetailResponse(ResultType.UNVALIDQUESINFO4EXAMTYPE);
    if (StringUtils.isBlank(paper.getQuesIds())) {
      setQuesIdLst(paper, quesList);
      quesOperationFacade.insertExamPaper(paper);
    }
    ExamDetailResponse.ExamDetail examDetail = new ExamDetailResponse.ExamDetail(paper);
    List<Question> questionList =
        packageQuestionList(quesList, null, opponentsAnswerMap, new CheckStoreRule(pojo.getUid(),
            item.getProductId().toString()));
    // if (resourceType != null && resourceType.equals(ResourceType.QUIZ)) {
    // nextItem(examDetail, item.getProductId().toString(), item.getParentId().toString(), item
    // .getId().toString());
    // }
    examDetail.setQuestionList(questionList);
    response.setExamDetail(examDetail);
    return response;
  }

  // private void nextItem(ExamDetailResponse.ExamDetail examDetail, String courseId,
  // String chapterId, String itemId) {
  // List<CourseProductItem> itemList = quesOperationFacade.queryChapterItemList(courseId);
  // if (null == itemList || itemList.size() == 0) return;
  // for (int i = 0; i < itemList.size(); i++) {
  // CourseProductItem item = itemList.get(i);
  // if (null == item || item.getResourceType() != 1) continue;
  // if (null != itemId && item.getId() != Integer.parseInt(itemId)) continue;
  // if (null != itemId && item.getId() == Integer.parseInt(itemId)) examDetail.setItemInfo(item);
  // if (null == itemId && null != chapterId && item.getParentId() == Integer.parseInt(chapterId))
  // examDetail.setNextItemInfo(item);
  // loopNextItem(examDetail, itemList, i);
  // return;
  // }
  // }

  // private void loopNextItem(ExamDetailResponse.ExamDetail examDetail,
  // List<CourseProductItem> itemList, int point) {
  // if (itemList.size() <= point + 1) return;
  // if (null != itemList.get(point + 1) && itemList.get(point + 1).getResourceType() == 1
  // && itemList.get(point + 1).getParentId() != 0) {
  // examDetail.setNextItemInfo(itemList.get(point + 1));
  // return;
  // }
  // loopNextItem(examDetail, itemList, point + 1);
  // }

  /**
   * 5 出题接口 新练习（包括智能练习 组卷模考 章节练习） · 说明 智能练习、组卷模式、章节练习根据所选模式，触发相关组卷规则进行出题； 历年真题返回真题卷。 · url
   * /quesbk/exam_detail · 参数 uid subjectId chapterId examType paperId
   * 
   * @param pojo
   */
  public ExamDetailResponse examDetail(ExamDetailPojo pojo) throws Exception {
    if (!checkCourseId(pojo, pojo.getProductId()))
      return new ExamDetailResponse(ResultType.UNVALIDCOURSEID);
    if (StringUtils.isNotBlank(pojo.getRecordId())) {
      return viewExamDetail(pojo);
    }
    return examDetail0(pojo);
  }

  /**
   * 获取练习历史接口
   */
  public ExamHistoryResponse examHistory(ExamHistoryPojo pojo) throws Exception {
    if (!checkCourseId(pojo, pojo.getProductId()))
      return new ExamHistoryResponse(ResultType.UNVALIDCOURSEID);
    return examHistory0(pojo);
  }

  public ExamHistoryResponse examHistory0(ExamHistoryPojo pojo) throws Exception {
    ExamHistoryResponse response = new ExamHistoryResponse(ResultType.SUCCESS);
    // 查询ProductItem
    ProductItemModel item = quesOperationFacade.queryItem(pojo.getProductItemId());
    if (null == item) return new ExamHistoryResponse(ResultType.UNVALIDPROITEMID);
    if (null == item.getProductId() || !pojo.getProductId().equals(item.getProductId().toString())) {
      return new ExamHistoryResponse(ResultType.EXAMMATCHEDCOURSEIDFAILED);
    }
    ExamInfo info = new ExamInfo(item);
    response.setExamInfo(info);
    List<UserExamRecord> recordList =
        quesOperationFacade.queryExamRecordListByItemId(pojo.getUid(), pojo.getProductId(), item
            .getId().toString());
    if (null == recordList || recordList.size() == 0) return response;
    info.setSubmitTimes(recordList.size());
    if (info.getSubmitTimes() >= info.getExamFrequency())
      info.setExamStatus(QuesBaseConstant.EXAM_STATUS_FROZEN);
    for (UserExamRecord record : recordList) {
      if (record.getMyScore() > info.getCurrentScore()) info.setCurrentScore(record.getMyScore());
      response.getExamRecordInfoList().add(new ExamRecordInfo(record));
    }
    if (info.getExamStatus().equals(QuesBaseConstant.EXAM_STATUS_FROZEN))
      info.setFinalScore(info.getCurrentScore());
    return response;
  }

  /**
   * 6 提交答案接口 · 说明 上传用户做题详情返回给后台处理接口。 · url /quesbk/exam_result
   * 
   * @param pojo
   */
  public SubmitResultResponse submitExamResult(ExamResultPojo pojo) {
    if (!checkCourseId(pojo, pojo.getProductId()))
      return new SubmitResultResponse(ResultType.UNVALIDCOURSEID);
    return autoSubmitExamResult(pojo);
  }

  private boolean canSubmit(ExamResultPojo pojo, ProductItemModel item) {
    List<UserExamRecord> recordList =
        quesOperationFacade.queryExamRecordListByItemId(pojo.getUid(), pojo.getProductId(), item
            .getId().toString());
    if (new Timestamp(System.currentTimeMillis()).after(item.getDeadline())) return false;
    Integer frequency = null == item.getFrequency() ? 1 : item.getFrequency();
    if (frequency > 0 && null != recordList && recordList.size() >= frequency) return false;
    return true;
  }

  /**
   * (机器人)提交答案接口 · 说明 上传用户做题详情返回给后台处理接口。 · url /quesbk/exam_result
   * 
   * @param pojo
   */
  public SubmitResultResponse autoSubmitExamResult(ExamResultPojo pojo) {
    SubmitResultResponse response = new SubmitResultResponse(ResultType.SUCCESS);
    UserExamRecord record = quesOperationFacade.queryExamRecord(pojo.getPaperId(), pojo.getUid());
    if (record != null) return new SubmitResultResponse(ResultType.CANTREPEATEXAM);
    ProductItemModel item = quesOperationFacade.queryItem(pojo.getProductItemId());
    if (null == item) return new SubmitResultResponse(ResultType.UNVALIDPROITEMID);
    if (null == item.getProductId() || !pojo.getProductId().equals(item.getProductId().toString())) {
      return new SubmitResultResponse(ResultType.EXAMMATCHEDCOURSEIDFAILED);
    }
    if (!canSubmit(pojo, item)) return new SubmitResultResponse(ResultType.UNVALIDEXAM);
    ResourceType resourceType = ResourceType.getByTypeId(item.getResourceType().intValue());
    switch (resourceType) {
      case QUIZ: // 随堂练习
        pojo.setExamType(ExamType.HOMEWORK_PAPER.getCode());
        break;
      case EXAM: // 单元测验
        pojo.setExamType(ExamType.CHAPTER_PAPER.getCode());
        break;
      case FINAL: // 期末考试
        pojo.setExamType(ExamType.APTITUDE_PAPER.getCode());
        break;
      default: // 其它练习
        return new SubmitResultResponse(ResultType.SUCCESS);
    }
    IQuesCaculateTask caculateTask = QuesCaculateUtil.getTask(pojo.getProductId());
    QuesbkExamPaper paper = quesOperationFacade.queryExamPaper(pojo.getPaperId());
    // 获取保存结果操作类型
    UserExamRecordDetail detail = new UserExamRecordDetail();
    detail.setCaculateTask(caculateTask);
    List<String> quesIds =
        FastJsonUtil.fromJsons(paper.getQuesIds(), new TypeReference<List<String>>() {});
    detail.setQuesCount(quesIds.size()); // 设置总题数
    detail.setStatus(ExamSubmitStatus.SUBMIT.getCode()); // 设置提交类型
    Map<Long, QuesbkQues> quesbkQuesMap = new HashMap<>();
    // 根据问题ID列表查询问题详情
    List<QuesbkQues> quesList =
        quesOperationFacade.queryAbstractQuesList(quesIds, pojo.getProductId());
    for (QuesbkQues ques : quesList) {
      quesbkQuesMap.put(ques.getId(), ques);
    }
    Integer finishCount = 0;
    List<AnswerItem> exanDetail =
        FastJsonUtil.fromJsons(pojo.getExamDetail(), new TypeReference<List<AnswerItem>>() {});
    String tag = UUID.randomUUID().toString();
    QuesExamCache.initTotalExamRecord(tag);
    for (AnswerItem answerItem : exanDetail) {
      QuesbkQues quesbkQues = quesbkQuesMap.get(answerItem.getQuesId());
      if (null == quesbkQues) {
        LoggerUtil.error("submitExamResult : 题目不存在." + answerItem.getQuesId(),
            new RuntimeException());
        continue;
      }
      // 只有提交状态为交卷时记录错题,并记录正确数和正确率
      // if (examStatus.equals(ExamSubmitStatus.SUBMIT)
      // || ExamSubmitStatus.SUBMIT.getCode().equals(answerItem.getExamStatus())) {
      Boolean isWrong = false;
      if (isWrong(quesbkQues, answerItem.getAnswerIdList())) {
        isWrong = true; // 记录状态为错误
      } else {
        detail.addRightCount(); // 记录正确数和正确率
      }
      recordWrong(pojo, paper, quesbkQuesMap.get(answerItem.getQuesId()), answerItem, isWrong, tag); // 记录错题集
      // } else {
      // if (null == answerItem.getAnswerIdList() || answerItem.getAnswerIdList().size() == 0)
      // continue;
      // }
      finishCount++;
    }
    QuesExamCache.setTotalExamRecord(tag, new Long(finishCount));
    detail.setFinishCount(finishCount); // 设置完成题数
    // if (ExamType.BREAKTHROUGH_PAPER.getCode().equals(pojo.getExamType())) {
    // 记录学习进度
    // recordLearnProcess(pojo, paper);
    // 发送闯关事件
    // sendTollgateEvent(pojo, paper, detail);
    // CourseProductItem item =
    // quesOperationFacade.queryItem(paper.getCourseId().toString(), paper.getChapterId()
    // .toString(), paper.getItemId().toString());
    // List<String> userList =
    // FastJsonUtil.fromJsons(item.getTopUserList(), new TypeReference<List<String>>() {});
    // if (null != userList && userList.size() > 0) response.setUserIdList(userList);
    // response.setCompleteCount(item.getCompleteCount());
    // }
    // response.setSelf(pojo.getUid());
    // 统计练习历史并记录章节闯关记录
    if (resourceType.equals(ResourceType.EXAM) || resourceType.equals(ResourceType.FINAL))
      recordItemScore(pojo, detail);
    recordExamHis(pojo, paper, detail, tag);
    // addUserCredit(pojo, detail);
    response.setDetail(detail); // 设置结果细节
    return response;
  }

  /**
   * 记录学习进度
   * 
   * @param pojo
   * @param paper
   */
  // private void recordLearnProcess(ExamResultPojo pojo, QuesbkExamPaper paper) {
  // UserLearnAchieveVo vo = new UserLearnAchieveVo();
  // vo.setUserId(pojo.getUid());
  // vo.setModuleId(pojo.getModule());
  // vo.setCourseId(pojo.getProductId());
  // vo.setChapterId(paper.getChapterId().toString());
  // vo.setItemId(paper.getItemId().toString());
  // RabbitMQSender.getInstance().send(vo, UUID.randomUUID().toString(), "2_saveLearnAchieve");
  // }

  // private void addUserCredit(ExamResultPojo pojo, UserExamRecordDetail detail) {
  // AddCreditRequest request = new AddCreditRequest();
  // request.setUid(pojo.getUid());
  // request.setModule(pojo.getModule());
  // request.setCreditUpper(detail.getCreditUpper());
  // RabbitMQSender.getInstance().send(request, UUID.randomUUID().toString(),
  // String.format(QuesBaseConstant.ASYNC_MESSAGE_ADDCREDIT, pojo.getModule()));
  // }

  // private void sendTollgateEvent(ExamResultPojo pojo, QuesbkExamPaper paper,
  // UserExamRecordDetail detail) {
  // TollgateEvent event = EventBuilder.buildTollgateEvent();
  // event.setUserId(pojo.getUid());
  // event.setModule(pojo.getModule());
  // event.setCourseId(paper.getCourseId().toString());
  // event.setChapterId(paper.getChapterId().toString());
  // event.setTollgateId(paper.getItemId().toString());
  // event.setCount(detail.getFinishCount());
  // event.setCredit(detail.getCreditUpper());
  // event.setScore(detail.getScore());
  // event.setStarLevel(detail.getStarLevel());
  // event.send();
  // }

  /**
   * 记录采分点成绩
   * 
   * @param pojo
   * @param detail
   */
  private void recordItemScore(ExamResultPojo pojo, UserExamRecordDetail detail) {
    IQueryParam param = new QueryParam();
    param.addInput("userId", pojo.getUid());
    param.addInput("productId", pojo.getProductId());
    param.addInput("taskId", pojo.getProductItemId());
    param.addOutputs(CommUtil.getAllField(TaskScoreModel.class));
    Page<TaskScoreModel> scoreModelPage = quesOperationFacade.queryTaskScoreModel(param);
    TaskScoreModel model = new TaskScoreModel();
    if (null == scoreModelPage || scoreModelPage.getResult() == null
        || scoreModelPage.getResult().size() == 0) {
      model.setId(Long.valueOf(RandomUtil.randomNumber(9)));
      model.setProductId(Long.valueOf(pojo.getProductId()));
      model.setTaskId(Long.valueOf(pojo.getProductItemId()));
      model.setUserId(Long.valueOf(pojo.getUid()));
      model.setScore(detail.getScore());
      quesOperationFacade.insertTaskScoreModel(model);
    } else {
      TaskScoreModel oldScoreModel = scoreModelPage.getResult().get(0);
      if (detail.getScore() <= oldScoreModel.getScore()) return;
      model.setId(oldScoreModel.getId());
      model.setScore(detail.getScore());
      quesOperationFacade.updateTaskScoreModel(model);
    }
  }

  /**
   * 5 出题接口 继续练习（包括智能练习 组卷模考 章节练习） · 继续练习 · url /quesbk/exam_detail · 参数 uid subjectId chapterId
   * examType paperId
   * 
   * @param pojo
   * @param examRecord
   * @throws Exception
   */
  public ExamDetailResponse viewExamDetail(ExamDetailPojo pojo) throws Exception {
    UserExamRecord examRecord = quesOperationFacade.queryExamRecord(pojo.getRecordId());
    if (null == examRecord) return new ExamDetailResponse(ResultType.UNVALIDEXAMRECORDID);
    QuesbkExamPaper paper = quesOperationFacade.queryExamPaper(examRecord.getPaperNo());
    Map<Long, AnswerItem> myAnswerMap = Maps.newHashMap();
    if (StringUtils.isNotBlank(examRecord.getQuestions())) {
      for (AnswerItem myAnswer : FastJsonUtil.fromJsons(examRecord.getQuestions(),
          new TypeReference<List<AnswerItem>>() {})) {
        myAnswerMap.put(myAnswer.getQuesId(), myAnswer);
      }
    }
    ExamDetail detail = new ExamDetail(pojo.getUid());
    detail.setCourseId(pojo.getProductId());
    List<Object> quesList = quesOperationFacade.queryExamQuestionIdList(detail, paper);
    if (quesList == null || quesList.size() == 0)
      return new ExamDetailResponse(ResultType.UNVALIDQUESINFO4EXAMTYPE);
    List<Question> questionList =
        packageQuestionList(quesList, myAnswerMap, null,
            new CheckStoreRule(pojo.getUid(), pojo.getProductId()));
    ExamDetailResponse response = new ExamDetailResponse(ResultType.SUCCESS);
    ExamDetailResponse.ExamDetail examDetail = new ExamDetailResponse.ExamDetail(paper);
    if (null != examRecord.getExamTime())
      examDetail.setExamTime(DateUtil.SDF_FULL.format(examRecord.getExamTime()));
    if (null != examRecord.getMyScore())
      examDetail.setScore(QuesBaseConstant.D_FORMAT.format(examRecord.getMyScore()));
    if (null != examRecord.getExamCost())
      examDetail.setExamCost(examRecord.getExamCost().toString()); // 设置上次练习耗时
    examDetail.setQuestionList(questionList);
    if (StringUtils.isJsonNotBlank(examRecord.getExamDetail())) {
      UserExamRecordDetail recordDetail =
          FastJsonUtil.fromJson(examRecord.getExamDetail(), UserExamRecordDetail.class);
      if (null != recordDetail.getRightCount())
        examDetail.setRightCount(recordDetail.getRightCount().toString());
    }
    response.setExamDetail(examDetail);
    return response;
  }

  /**
   * 记录练习历史
   * 
   * @param pojo 结果参数
   * @param paper 考卷
   * @param detail
   */
  private UserExamRecord recordExamHis(ExamResultPojo pojo, QuesbkExamPaper paper,
      UserExamRecordDetail detail, String tag) {
    UserExamRecord userExamRecord = new UserExamRecord();
    userExamRecord.setUserId(pojo.getUid());
    userExamRecord.setExamTypeId(paper.getExamTypeId());
    userExamRecord.setCourseId(pojo.getProductId());
    userExamRecord.setItemId(pojo.getProductItemId());
    userExamRecord.setPaperNo(pojo.getPaperId());
    userExamRecord.setExamName(paper.getExamName());
    userExamRecord.setQuestions(pojo.getExamDetail());
    userExamRecord.setExamTime(new Timestamp(System.currentTimeMillis()));
    userExamRecord.setExamCost(Long.valueOf(pojo.getExamTime()));
    userExamRecord.setExamDetail(FastJsonUtil.toJson(detail));
    // if (ExamSubmitStatus.SUBMIT.getCode().equals(pojo.getExamStatus())
    // && StringUtils.isNotBlank(pojo.getPaperId()) && null != detail.getScore()) {
    userExamRecord.setMyScore(detail.getScore());
    // }
    queueService.addExamRecord(userExamRecord);
    /** 记录个人练习历史 */
    processExamTotal(pojo.getUid(), pojo.getModule(), pojo.getProductId(), detail, tag);
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
  private void processExamTotal(String uid, String module, String courseId,
      UserExamRecordDetail detail, String tag) {
    UserExamTotal myExamTotal = new UserExamTotal();
    myExamTotal.setUserId(uid);
    myExamTotal.setCourseId(courseId);
    myExamTotal.setTotalQues(detail.getFinishCount());
    myExamTotal.setRightQues(detail.getRightCount());
    myExamTotal.setTag(tag);
    queueService.addExamTotal(myExamTotal);
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
      AnswerItem answerItem, Boolean isWrong, String tag) {
    UserWrongRecord userWrongRecord = new UserWrongRecord();
    userWrongRecord.setUserId(pojo.getUid());
    userWrongRecord.setModule(pojo.getModule());
    userWrongRecord.setCourseId(pojo.getProductId());
    userWrongRecord.setChapterId(quesbkQues.getChapterId());
    userWrongRecord.setQuestionId(Long.parseLong(quesbkQues.getId().toString()));
    userWrongRecord.setWrongAnswer(FastJsonUtil.toJson(answerItem.getAnswerIdList()));
    userWrongRecord.setExamTime(new Date());
    userWrongRecord.setWrong(isWrong);
    userWrongRecord.setTag(tag);
    if (isWrong) {
      userWrongRecord.addWrongTimes();
    } else {
      userWrongRecord.addRightTimes();
    }
    // 记录错题队列中
    queueService.addWrongQues(userWrongRecord);
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
        if (wrongRecord.getQuestionNumber() == 0) continue;
        WrongQuesListResponse.ChapterInfo parentChapter = null;
        WrongQuesListResponse.ChapterInfo chapter = new WrongQuesListResponse.ChapterInfo();
        chapter.setChapterName(wrongRecord.getChapterName());
        chapter.setChapterId(wrongRecord.getChapterId().toString());
        // 更新子章节的问题数量
        chapter.setQuesCount(wrongRecord.getQuestionNumber());
        if (wrongRecord.getParentChapter() > 0) {
          parentChapter = _parentMap.get(wrongRecord.getParentChapter());
          if (parentChapter == null) {
            parentChapter = new WrongQuesListResponse.ChapterInfo();
            parentChapter.setChapterId(wrongRecord.getParentChapter().toString());
            parentChapter.setChapterName(wrongRecord.getParentChapterName());
            // 保存父章节至父章节Map中
            _parentMap.put(wrongRecord.getParentChapter(), parentChapter);
            // 这里章列表只保存父章节即可
            chapterList.add(parentChapter);
          }
          // 更新父章节的问题数量
          parentChapter
              .setQuesCount(parentChapter.getQuesCount() + wrongRecord.getQuestionNumber());
          // 未掌握问题数量
          parentChapter.setUncatchQuesCount(parentChapter.getUncatchQuesCount()
              + wrongRecord.getUncatchQuesCount());
          // 待强化问题数量
          parentChapter.setCatchingQuesCount(parentChapter.getCatchingQuesCount()
              + wrongRecord.getCatchingQuesCount());
          // 已消灭问题数量
          parentChapter.setCatchedQuesCount(parentChapter.getCatchedQuesCount()
              + wrongRecord.getCatchedQuesCount());
          // 转化为节信息保存至父章节的子章节列表中
          // parentChapter.getChildList().add(chapter.transfer2Item());
        } else {
          // 该章节本身就是父章节
          _parentMap.put(wrongRecord.getId(), chapter);
          chapterList.add(chapter);
        }
      }
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
   * 9 收藏题目 ·说明 收藏题目到个人收藏列表。 ·url /quesbk/store_ques
   * 
   * @param pojo
   */
  public BaseResponse storeQues(StoreQuesPojo pojo) {
    // 验证课程id
    if (!checkCourseId(pojo, pojo.getCourseId()))
      return new BaseResponse(ResultType.UNVALIDCOURSEID);
    // 验证章id
    // List<String> chapterIdList = queryChapterIdByCourseId(pojo.getCourseId());
    // if (!chapterIdList.contains(pojo.getChapterId()))
    // return new BaseResponse(ResultType.NotFindChapter);
    // 验证题目id
    QuesbkQues ques = quesOperationFacade.queryQues(pojo.getQuesId(), pojo.getCourseId());
    if (ques == null) return new BaseResponse(ResultType.UNVALIDQUESINFO);
    BaseResponse response = new BaseResponse(ResultType.SUCCESS);
    // if ("4".equals(pojo.getType())) return new BaseResponse(ResultType.StatusFail);
    UserStoreRecord record =
        quesOperationFacade.queryStoreRecord(pojo.getUid(), ques.getCourseId().toString(), ques
            .getChapterId().toString(), pojo.getQuesId());
    if (null != record && !("4").equals(record.getStoreStatus()))
      return new BaseResponse(ResultType.HasStore);
    record = new UserStoreRecord();
    record.setUserId(pojo.getUid());
    record.setCourseId(ques.getCourseId());
    record.setChapterId(ques.getChapterId());
    record.setQuestionId(Long.valueOf(pojo.getQuesId()));
    record.setStoreStatus(pojo.getType());
    try {
      // 添加收藏/
      if (null != record && ("4").equals(record.getStoreStatus())) {
        Boolean flag = quesOperationFacade.changeStoreQues(record);
        if (!flag) return new BaseResponse(ResultType.StoreFail);
      } else {
        Boolean flag = quesOperationFacade.insertStoreRecord(record);
        if (!flag) return new BaseResponse(ResultType.StoreFail);
      }
      response = changeStoreRecordCollect(pojo.getUid(), ques);
      sendCollectEvent(pojo, record);
    } catch (Exception e) {
      LoggerUtil.error("插入收藏记录失败 : " + FastJsonUtil.toJson(pojo), e);
    }
    queueService.addStoreCollect(new UserStoreRecordCollect(record));
    return response;
  }

  /**
   * 更改收藏集合记录表
   * 
   */
  private void sendCollectEvent(StoreQuesPojo pojo, UserStoreRecord record) {
    CollectionEvent event = EventBuilder.buildCollectionEvent();
    event.setModule(pojo.getModule());
    event.setUserId(pojo.getUid());
    event.setCourseId(pojo.getCourseId());
    event.send();
  }

  private BaseResponse changeStoreRecordCollect(String userId, QuesbkQues ques) {
    // 更改收藏集合表
    BaseResponse response = new BaseResponse(ResultType.SUCCESS);

    // 查询所属章节与子章节问题
    List<String> chapterIdList = Lists.newArrayList();
    chapterIdList.add(ques.getChapterId().toString());
    Map<String, Object> cond = Maps.newHashMap();
    Map<String, Object> input = Maps.newHashMap();
    cond.put("userId", userId);
    cond.put("courseId", ques.getCourseId());
    cond.put("chapterIdList", chapterIdList);
    input.put("input", cond);

    List<UserStoreRecord> selectByUSCList = quesOperationFacade.queryStoreRecordList(input);
    int questionNumber = 0;// 问题数
    int goodQuesCount = 0;// 好题数量
    int unknownQuesCount = 0;// 我不会的问题数量
    int needMemeryQuesCount = 0; // 需要记忆问题数量
    // 1 好题(默认)    2 我不会的    3 需要记忆   4取消收藏
    if (null != selectByUSCList && selectByUSCList.size() > 0) {
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
    }
    // 2、修改
    UserStoreRecordCollect userStoreRecordCollect =
        quesOperationFacade.queryStoreRecordCollect(userId, ques.getCourseId().toString(), ques
            .getChapterId().toString());
    if (null == userStoreRecordCollect) {
      UserStoreRecordCollect recordCollect = new UserStoreRecordCollect();
      recordCollect.setUserId(userId);
      recordCollect.setCourseId(ques.getCourseId());
      recordCollect.setChapterId(ques.getChapterId());
      recordCollect.setQuestionNumber(questionNumber);
      recordCollect.setGoodQuesCount(goodQuesCount);
      recordCollect.setUnknownQuesCount(unknownQuesCount);
      recordCollect.setNeedMemeryQuesCount(needMemeryQuesCount);
      Boolean _flag = quesOperationFacade.insertStoreRecordCollect(recordCollect);
      if (!_flag) return new BaseResponse(ResultType.StoreFail);
    }
    userStoreRecordCollect.setUserId(userId);
    userStoreRecordCollect.setCourseId(ques.getCourseId());
    userStoreRecordCollect.setChapterId(ques.getChapterId());
    userStoreRecordCollect.setQuestionNumber(questionNumber);
    userStoreRecordCollect.setGoodQuesCount(goodQuesCount);
    userStoreRecordCollect.setUnknownQuesCount(unknownQuesCount);
    userStoreRecordCollect.setNeedMemeryQuesCount(needMemeryQuesCount);
    int updateCount = quesOperationFacade.updateStoreRecordCollet(userStoreRecordCollect);
    if (updateCount != 1) return new BaseResponse(ResultType.StoreFail);
    return response;
  }

  /**
   * 10 获取收藏列表 ·说明 根据用户ID和课程ID获取个人收藏列表。 ·url /quesbk/store_ques_list
   * 
   * @param pojo
   */
  public StoreQuesListResponse storeQuesList(StoreQuesListPojo pojo) {
    if (!checkCourseId(pojo, pojo.getCourseId()))
      return new StoreQuesListResponse(ResultType.UNVALIDCOURSEID);
    StoreQuesListResponse response = new StoreQuesListResponse(ResultType.SUCCESS);
    List<UserStoreRecordCollect> recordList =
        quesOperationFacade.queryStoreRecordCollectList(pojo.getUid(), pojo.getCourseId());
    List<WrongQuesListResponse.ChapterInfo> chapterList = Lists.newArrayList();
    // 此Map用于保存父章节信息，用于后续组织列表时查询使用
    Map<Long, WrongQuesListResponse.ChapterInfo> _parentMap = Maps.newHashMap();
    for (UserStoreRecordCollect storeRecord : recordList) {
      WrongQuesListResponse.ChapterInfo parentChapter = null;
      WrongQuesListResponse.ChapterInfo chapter = new WrongQuesListResponse.ChapterInfo();
      chapter.setChapterName(storeRecord.getChapterName());
      chapter.setChapterId(storeRecord.getChapterId().toString());
      // 更新子章节的问题数量
      chapter.setQuesCount(storeRecord.getQuestionNumber());
      if (storeRecord.getParentChapter() > 0) {
        parentChapter = _parentMap.get(storeRecord.getParentChapter());
        if (parentChapter == null) {
          parentChapter = new WrongQuesListResponse.ChapterInfo();
          parentChapter.setChapterId(storeRecord.getParentChapter().toString());
          parentChapter.setChapterName(storeRecord.getParentChapterName());
          // 保存父章节信息至父章节Map中
          _parentMap.put(storeRecord.getParentChapter(), parentChapter);
          // 这里章列表只保存父章节即可
          chapterList.add(parentChapter);
        }
        // 更新父章节的问题数量
        parentChapter.setQuesCount(parentChapter.getQuesCount() + storeRecord.getQuestionNumber());
        // 好题数量
        parentChapter.setGoodQuesCount(parentChapter.getGoodQuesCount()
            + storeRecord.getGoodQuesCount());
        // 我不会的问题数量
        parentChapter.setUnknownQuesCount(parentChapter.getUnknownQuesCount()
            + storeRecord.getUnknownQuesCount());
        // 需要记忆问题数量
        parentChapter.setNeedMemeryQuesCount(parentChapter.getNeedMemeryQuesCount()
            + storeRecord.getNeedMemeryQuesCount());
        // 转化为节信息保存至父章节的子章节列表中
        // parentChapter.getChildList().add(chapter.transfer2Item());
      } else {
        // 该章节本身就是父章节
        _parentMap.put(storeRecord.getId(), chapter);
        chapterList.add(chapter);
      }
    }
    response.setCourseItemList(chapterList);
    return response;
  }

  /**
   * 12 收藏详情展示 ·url /quesbk/store_ques_detail
   * 
   * @param pojo
   */
  public StoreQuesDetailResponse storeQuesDetail(StoreQuesDetailPojo pojo) {
    if (!checkCourseId(pojo, pojo.getCourseId()))
      return new StoreQuesDetailResponse(ResultType.UNVALIDCOURSEID);
    StoreQuesDetailResponse response = new StoreQuesDetailResponse(ResultType.SUCCESS);
    // 查询子章节列表
    List<String> chapterIdList = Lists.newArrayList();
    if (StringUtils.isNotBlank(pojo.getItemId())) {
      chapterIdList.add(pojo.getItemId());
    } else {
      List<ProductItemModel> chapterList =
          quesOperationFacade.queryItemList(pojo.getCourseId(), pojo.getChapterId());
      chapterIdList.add(pojo.getChapterId());
      if (null != chapterList && chapterList.size() > 0) {
        for (ProductItemModel chapter : chapterList) {
          chapterIdList.add(chapter.getId().toString());
        }
      }
    }
    // 查询所属章节与子章节问题
    Map<String, Object> cond = Maps.newHashMap();
    Map<String, Object> input = Maps.newHashMap();
    cond.put("userId", pojo.getUid());
    cond.put("courseId", pojo.getCourseId());
    cond.put("chapterIdList", chapterIdList);
    cond.put("storeStatus", pojo.getType());
    input.put("input", cond);
    List<UserStoreRecord> recordList = quesOperationFacade.queryStoreRecordList(input);
    ArrayList<String> quesIdList = Lists.newArrayList();
    for (UserStoreRecord record : recordList) {
      if (null != record.getQuestionId()) {
        quesIdList.add(record.getQuestionId().toString());
      }
    }
    if (quesIdList != null && quesIdList.size() > 0) {
      List<QuesbkQues> quesList =
          quesOperationFacade.queryQuesList(quesIdList, pojo.getUid(), pojo.getCourseId());
      if (null != quesList && quesList.size() > 0) {
        List<StoreQuesDetailResponse.StoreQuestion> storeQuesList = packageQuestionList(quesList);
        response.setQuestionList(storeQuesList);
      }
    }
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
      List<ProductItemModel> chapterList =
          quesOperationFacade.queryItemList(pojo.getCourseId(), pojo.getChapterId());
      chapterIdList.add(pojo.getChapterId());
      if (null != chapterList && chapterList.size() > 0) {
        for (ProductItemModel chapter : chapterList) {
          chapterIdList.add(chapter.getId().toString());
        }
      }
    }
    // 查询所属章节与子章节问题
    Map<String, Object> cond = Maps.newHashMap();
    cond.put("userId", pojo.getUid());
    cond.put("courseId", pojo.getCourseId());
    // cond.put("rightTimes", StaticInfoProp.getWrongLimit());//1在错题集里面改状态的操作由前段来做2根据状态就可以区分错题类型了
    cond.put("wrongTimesLower", 0);// 错题数至少要大于0才属于错题
    cond.put("chapterIdList", chapterIdList);
    cond.put("wrongStatus", pojo.getType());

    Page<UserWrongRecord> userWrongRecordList =
        quesOperationFacade.queryWrongRecordList(cond, null);
    if (userWrongRecordList.getResult() != null && userWrongRecordList.getResult().size() > 0) {
      List<WrongQuesDetailResponse.WrongQuestion> quesList =
          packageQuestionList(userWrongRecordList.getResult(),
              new WrongCheckStoreRule(pojo.getUid(), pojo.getCourseId()));
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
      final Map<Long, AnswerItem> myAnswerList, final Map<Long, AnswerItem> opponentsAnswerList,
      final CheckStoreRule checkRule) {
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
            } else
              question.setMyAnswerIds(null);
            if (null != opponentsAnswerList && null != opponentsAnswerList.get(ques.getId()))
              question.setOpponentsIds(opponentsAnswerList.get(ques.getId()).getAnswerIdList());
            else
              question.setOpponentsIds(null);
            // 如果收藏列表不为空,判断收藏状态
            if (checkRule.needCheck()) question.setStoreStatus(checkRule.check(ques));
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
  private List<StoreQuesDetailResponse.StoreQuestion> packageQuestionList(List<QuesbkQues> quesList) {
    final Map<Long, String> typeMap = getTypeMap();
    return packageQuestionList(quesList,
        new Function<QuesbkQues, StoreQuesDetailResponse.StoreQuestion>() {
          @Override
          public StoreQuesDetailResponse.StoreQuestion apply(QuesbkQues quesbkQues) {
            try {
              quesbkQues.setQuestionTypeName(typeMap.get(quesbkQues.getQuestionType()));
              return new StoreQuesDetailResponse.StoreQuestion(quesbkQues);
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
  private List<WrongQuesDetailResponse.WrongQuestion> packageQuestionList(
      List<UserWrongRecord> quesList, final WrongCheckStoreRule checkRule) {
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
  private interface ICheckStoreRule {
    public Boolean needCheck();

    public String check(QuesbkQues quesbkQues);
  }

  /**
   * @name @see com.xiaodou.service.CheckStoreRule.java
   * @CopyRright (c) 2015 by XiaoDou NetWork Technology
   * 
   * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
   * @date 2015年8月7日
   * @description 普通问题收藏规则
   * @version 1.0
   */
  private class CheckStoreRule implements ICheckStoreRule {
    String uid;
    String courseId;
    private Set<Long> storeQues = Sets.newHashSet();
    private HashMap<Long, UserStoreRecord> storeQuesMap = Maps.newHashMap();
    private Boolean isInited = false;

    public CheckStoreRule(String uid, String courseId) {
      this.uid = uid;
      this.courseId = courseId;
    }

    private void init() {
      // 获取用户收藏列表
      if (StringUtils.isNotBlank(uid) && StringUtils.isNotBlank(courseId)) {
        List<UserStoreRecord> userStoreRecords =
            quesOperationFacade.queryStoreRecordList(uid, courseId);
        if (null != userStoreRecords && userStoreRecords.size() > 0) {
          for (UserStoreRecord storeRecord : userStoreRecords) {
            if (null == storeRecord || null == storeRecord.getQuestionId()) continue;
            if (!storeQues.contains(storeRecord.getQuestionId())) {
              storeQues.add(storeRecord.getQuestionId());
              storeQuesMap.put(storeRecord.getQuestionId(), storeRecord);
            }
          }
        }
      }
      isInited = true;
    }

    @Override
    public Boolean needCheck() {
      if (!isInited) init();
      return null != storeQues && storeQues.size() > 0;
    }

    @Override
    public String check(QuesbkQues quesbkQues) {
      return storeQues.contains(quesbkQues.getId()) ? storeQuesMap.get(quesbkQues.getId())
          .getStoreStatus() : "0";
    }

  }

  /**
   * @name @see com.xiaodou.service.WrongCheckStoreRule.java
   * @CopyRright (c) 2015 by XiaoDou NetWork Technology
   * 
   * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
   * @date 2015年8月7日
   * @description 错题判断收藏规则
   * @version 1.0
   */
  private class WrongCheckStoreRule extends CheckStoreRule {
    public WrongCheckStoreRule(String uid, String courseId) {
      super(uid, courseId);
    }
  }


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
    // List<String> chapterIdList = queryChapterIdByCourseId(pojo.getCourseId());
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
    record.setCourseId(ques.getCourseId());
    record.setChapterId(ques.getChapterId());
    record.setQuestionId(Long.valueOf(pojo.getQuesId()));
    record.setWrongStatus(pojo.getType());
    if (quesOperationFacade.changeWrongQues(record)) {
      return changeWrongRecordCollect(pojo.getUid(), pojo.getModule(), ques);
    } else
      return new BaseResponse(ResultType.SUCCESS);
  }

  private BaseResponse changeWrongRecordCollect(String userId, String module, QuesbkQues ques) {
    // 更改错题集合表
    BaseResponse response = new BaseResponse(ResultType.SUCCESS);
    // 1、查询
    Map<String, Object> _cond = new HashMap<String, Object>();
    _cond.put("userId", userId);
    _cond.put("courseId", ques.getCourseId());
    _cond.put("chapterId", ques.getChapterId());
    _cond.put("wrongTimesLower", 0);// 错题数至少要大于0才属于错题
    Page<UserWrongRecord> pageList = quesOperationFacade.queryWrongRecordList(_cond, null);
    if (null == pageList) return new BaseResponse(ResultType.UNVALIDQUESINFO);
    List<UserWrongRecord> userWrongRecordList = pageList.getResult();
    if (null == userWrongRecordList || userWrongRecordList.size() < 1)
      return new BaseResponse(ResultType.UNVALIDQUESINFO);
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
    // 发送课程错题数量事件
    sendWrongQuesCountEvent(userId, module, ques.getCourseId());
    // 2、修改
    UserWrongRecordCollect userWrongRecordCollect =
        quesOperationFacade.queryWrongRecordCollect(userId, ques.getCourseId().toString(), ques
            .getChapterId().toString());
    if (null == userWrongRecordCollect) {
      UserWrongRecordCollect recordCollect = new UserWrongRecordCollect();
      recordCollect.setUserId(userId);
      recordCollect.setCourseId(ques.getCourseId());
      recordCollect.setChapterId(ques.getChapterId());
      recordCollect.setQuestionNumber(questionNumber);
      recordCollect.setUncatchQuesCount(uncatchQuesCount);
      recordCollect.setCatchingQuesCount(catchingQuesCount);
      recordCollect.setCatchedQuesCount(catchedQuesCount);
      quesOperationFacade.insertWrongRecordCollect(recordCollect);
    }
    userWrongRecordCollect.setChapterId(ques.getChapterId());
    userWrongRecordCollect.setCourseId(ques.getCourseId());
    userWrongRecordCollect.setUserId(userId);
    userWrongRecordCollect.setQuestionNumber(questionNumber);
    userWrongRecordCollect.setUncatchQuesCount(uncatchQuesCount);
    userWrongRecordCollect.setCatchingQuesCount(catchingQuesCount);
    userWrongRecordCollect.setCatchedQuesCount(catchedQuesCount);
    int updateCount = quesOperationFacade.updateWrongRecordCollect(userWrongRecordCollect);
    if (updateCount != 1) return new BaseResponse(ResultType.WrongFail);
    return response;
  }

  private void sendWrongQuesCountEvent(String userId, String module, Long courseId) {
    WrongQuesCountVo vo = new WrongQuesCountVo();
    vo.setUserId(userId);
    vo.setModule(module);
    vo.setCourseId(courseId.toString());
    queueService.addWrongQuesCount(vo);
  }

  public BaseResponse changeStoreQues(StoreQuesRequest pojo) {
    // 验证课程id
    if (!checkCourseId(pojo, pojo.getCourseId()))
      return new BaseResponse(ResultType.UNVALIDCOURSEID);
    // 验证章id
    // List<String> chapterIdList = queryChapterIdByCourseId(pojo.getCourseId());
    // if (!chapterIdList.contains(pojo.getChapterId()))
    // return new BaseResponse(ResultType.NotFindChapter);
    // 验证题目id
    QuesbkQues ques = quesOperationFacade.queryQues(pojo.getQuesId(), pojo.getCourseId());
    if (ques == null) return new BaseResponse(ResultType.UNVALIDQUESINFO);
    UserStoreRecord record =
        quesOperationFacade.queryStoreRecord(pojo.getUid(), ques.getCourseId().toString(), ques
            .getChapterId().toString(), pojo.getQuesId());
    if (null == record) return new BaseResponse(ResultType.NotFindStore);
    record.setUserId(pojo.getUid());
    record.setCourseId(ques.getCourseId());
    record.setChapterId(ques.getChapterId());
    record.setQuestionId(Long.valueOf(pojo.getQuesId()));
    record.setStoreStatus(pojo.getType());
    if (quesOperationFacade.changeStoreQues(record))
      return changeStoreRecordCollect(pojo.getUid(), ques);
    else
      return new BaseResponse(ResultType.StoreFail);
  }

  public CourseStatisticsResponse courseStatistics(CourseStatisticsPojo pojo) {
    if (!checkCourseId(pojo, pojo.getCourseId()))
      return new CourseStatisticsResponse(ResultType.UNVALIDCOURSEID);
    CourseStatisticsResponse response = new CourseStatisticsResponse(ResultType.SUCCESS);
    UserExamTotal myExamTotal =
        quesOperationFacade.queryExamTotal(pojo.getUid(), pojo.getCourseId());
    // Integer baseJoinNum = StaticInfoProp.getInt("xiaodou.quesbase.baseJoinNum");
    // Integer baseRank = StaticInfoProp.getInt("xiaodou.quesbase.baseRank");
    // Integer baseRightRank = StaticInfoProp.getInt("xiaodou.quesbase.baseRightRank");
    // Integer allExamCount = quesOperationFacade.countExamTotal(pojo.getCourseId()) + baseJoinNum;
    if (null != myExamTotal) {
      // 我的答题数
      response.setMyQues(myExamTotal.getTotalQues().toString());
      if (StringUtils.isBlank(myExamTotal.getRightPercent()))
        response.setMyRightPercent("0.00");
      else {
        // Double rightPercent =
        // (new Double(myExamTotal.getRightQues()) / new Double(myExamTotal.getTotalQues())) * 100;
        // response.setMyRightPercent(rightPercent.intValue() + "%");
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
    Double bonusScore = 0d;
    UserExamTotal examTotal = quesOperationFacade.queryExamTotal(pojo.getUid(), pojo.getCourseId());
    CourseScore courseScore = new CourseScore();
    courseScore.init(pojo.getCourseId());
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
        ItemScore itemScore = courseScore.getItem(chapterRecord.getItemId().toString());
        itemScore.setOriginalScore(chapterRecord.getScore());
        if (bonusScore > 0d)
          itemScore.setBonusScore(bonusScore * (100d - chapterRecord.getScore()) / itemSurp);
      }
    }
    response.setAward(courseScore);
    return response;
  }
}
