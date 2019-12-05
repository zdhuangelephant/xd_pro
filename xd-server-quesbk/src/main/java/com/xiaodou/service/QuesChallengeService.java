package com.xiaodou.service;

import java.sql.Timestamp;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.xiaodou.async.model.PkMessage;
import com.xiaodou.common.util.CommUtil;
import com.xiaodou.common.util.DateUtil;
import com.xiaodou.common.util.MathUtil;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.constant.QuesBaseConstant;
import com.xiaodou.constant.ResultType;
import com.xiaodou.domain.PkExam;
import com.xiaodou.domain.behavior.UserExamRecord;
import com.xiaodou.domain.behavior.UserExamRecordDetail;
import com.xiaodou.domain.behavior.UserExamTotal;
import com.xiaodou.domain.product.ChallengeRecord;
import com.xiaodou.domain.product.ChallengeRobot;
import com.xiaodou.domain.product.CourseProduct;
import com.xiaodou.domain.product.QuesbkExamPaper;
import com.xiaodou.domain.product.QuesbkExamRule;
import com.xiaodou.enums.ExamSubmitStatus;
import com.xiaodou.enums.ExamType;
import com.xiaodou.summer.dao.pagination.Page;
import com.xiaodou.vo.alarm.ChallengeAlarm;
import com.xiaodou.vo.alarm.ExamDetailAlarm;
import com.xiaodou.vo.request.ChallengePojo;
import com.xiaodou.vo.request.ExamDetailPojo;
import com.xiaodou.vo.request.ExamResultPojo;
import com.xiaodou.vo.request.ExamResultPojo.AnswerItem;
import com.xiaodou.vo.request.PkResultPojo;
import com.xiaodou.vo.request.QuesBasePojo;
import com.xiaodou.vo.response.ChallengeResponse;
import com.xiaodou.vo.response.ExamDetailResponse;
import com.xiaodou.vo.response.ExamDetailResponse.Question;
import com.xiaodou.vo.response.PkListResponse;
import com.xiaodou.vo.response.PkListResponse.PkAbstractResult;
import com.xiaodou.vo.response.PkResultResponse;
import com.xiaodou.vo.response.QuesDetailResponse.Answer;
import com.xiaodou.vo.task.RobotChallengeVo;


/**
 * @name @see com.xiaodou.service.QuesChallengeService.java
 * @CopyRright (c) 2016 by XiaoDou NetWork Technology
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2016年2月25日
 * @description 题库挑战模块service
 * @version 1.0
 */
@Service("quesChallengeService")
public class QuesChallengeService extends AbstractQuesService {

  @Resource
  QuesBaseServices quesBaseServices;

  /**
   * 发起挑战
   * 
   * @param pojo 发起挑战参数pojo
   * @return
   * @throws Exception
   */
  public ChallengeResponse challenge(ChallengePojo pojo) throws Exception {
    if (!checkCourseId(pojo, pojo.getCourseId()))
      return new ChallengeResponse(ResultType.UNVALIDCOURSEID);
    CourseProduct product = quesOperationFacade.queryProduct(pojo.getCourseId());
    if (pojo.getUid().equals(pojo.getTargetUserId()))
      return new ChallengeResponse(ResultType.CANTCHALLENGESELF);
    if (null != product && !checkCourseId(pojo, pojo.getCourseId()))
      return new ChallengeResponse(ResultType.UNVALIDCOURSEID);
    QuesBasePojo basePojo = new QuesBasePojo();
    basePojo.setUid(pojo.getTargetUserId());
    basePojo.setModule(pojo.getModule());
    basePojo.setTypeCode(pojo.getTypeCode());
    if (!checkCourseId(basePojo, pojo.getCourseId()))
      return new ChallengeResponse(ResultType.UNVALIDCOURSEID4TARGETUSER);

    ChallengeResponse challenge = new ChallengeResponse(ResultType.SUCCESS);
    if (QuesBaseConstant.QUES_CHALLENGE_FRIEND == pojo.getType()) {
      challenge = friendChallenge(pojo, product);
    } else if (QuesBaseConstant.QUES_CHALLENGE_RANDOM == pojo.getType()) {
      challenge = randomChallenge(pojo, product);
    }
    if (challenge.isRetOk() && pojo.getType().equals(QuesBaseConstant.QUES_CHALLENGE_FRIEND)) {
      PkMessage message = new PkMessage();
      message.setModule(pojo.getModule());
      message.setSrcUid(pojo.getUid());
      message.setToUid(pojo.getTargetUserId());
      message.addCallBackContent("recordId", challenge.getRecordId());
      message.addCallBackContent("courseId", pojo.getCourseId());
      message.setMessageName("2_pk");
      message.send();
    }
    return challenge;
  }

  /**
   * 发起好友挑战接口
   * 
   * @param pojo 发起挑战参数pojo
   * @return
   * @throws Exception
   */
  private ChallengeResponse friendChallenge(ChallengePojo pojo, CourseProduct product)
      throws Exception {
    {
      Map<String, Object> input = Maps.newHashMap();
      input.put("courseId", pojo.getCourseId());
      input.put("challengerUid", pojo.getUid());
      input.put("beChallengerUid", pojo.getTargetUserId());
      input.put("status", QuesBaseConstant.QUES_CHALLENGE_STATUS_DAIYINGZHAN);
      List<ChallengeRecord> cList = quesOperationFacade.queryChallengeRecord(input);
      if (null != cList && cList.size() > 0)
        return new ChallengeResponse(ResultType.HASUNACCEPTCHALLENGE);
    }
    {
      Map<String, Object> input = Maps.newHashMap();
      input.put("courseId", pojo.getCourseId());
      input.put("beChallengerUid", pojo.getUid());
      input.put("challengerUid", pojo.getTargetUserId());
      input.put("status", QuesBaseConstant.QUES_CHALLENGE_STATUS_DAIYINGZHAN);
      List<ChallengeRecord> cList = quesOperationFacade.queryChallengeRecord(input);
      if (null != cList && !cList.isEmpty()) {
        ChallengeResponse challengeResponse = new ChallengeResponse(ResultType.SUCCESS);
        challengeResponse.setRecordId(cList.get(0).getId());
        challengeResponse.setTargetUserId(pojo.getTargetUserId());
        return challengeResponse;
      }
    }
    return friendChallenge0(pojo, product);
  }

  private ChallengeResponse friendChallenge0(ChallengePojo pojo, CourseProduct product)
      throws Exception {
    ChallengeRecord record = new ChallengeRecord();
    record.setCourseId(pojo.getCourseId());
    record.setCourseName(product.getName());
    record.setChallengerUid(pojo.getUid());
    record.setBeChallengerUid(pojo.getTargetUserId());
    record.setType(pojo.getType());
    QuesbkExamPaper paper =
        new QuesbkExamPaper(ExamType.FRIEND_CHALLENGE_PAPER, pojo.getCourseId());
    List<Object> quesList = Lists.newArrayList();
    ExamDetailPojo examPojo = new ExamDetailPojo();
    examPojo.setModule(pojo.getModule());
    examPojo.setCourseId(pojo.getCourseId());
    examPojo.setExamType(ExamType.FRIEND_CHALLENGE_PAPER.getCode());
    examPojo.setTypeCode(pojo.getTypeCode());
    examPojo.setUid(pojo.getUid());
    List<QuesbkExamRule> ruleList =
        quesOperationFacade.queryExamRuleList(examPojo.getCourseId(), examPojo.getExamType());
    if (ruleList == null || ruleList.size() == 0) {
      // 未找到命题蓝图,报警
      LoggerUtil.alarmInfo(new ExamDetailAlarm(ResultType.MISSINGEXAMRULE.getMsg()));
      return new ChallengeResponse(ResultType.MISSINGEXAMRULE);
    }
    quesList = quesOperationFacade.queryExamQuestionIdList(examPojo, ruleList, paper);
    if (quesList == null || quesList.size() == 0) {
      // 无法出题,报警
      LoggerUtil.alarmInfo(new ExamDetailAlarm(ResultType.UNVALIDQUESINFO4EXAMTYPE.getMsg()));
      return new ChallengeResponse(ResultType.UNVALIDQUESINFO4EXAMTYPE);
    }
    if (StringUtils.isBlank(paper.getQuesIds())) {
      setQuesIdLst(paper, quesList);
      quesOperationFacade.insertExamPaper(paper);
    }
    record.setPaperId(paper.getId());
    record.setCreateTime(new Timestamp(System.currentTimeMillis())); // 设置记录生成时间
    if (quesOperationFacade.insertChallengeRecord(record)) {
      ChallengeResponse challengeResponse = new ChallengeResponse(ResultType.SUCCESS);
      challengeResponse.setRecordId(record.getId());
      challengeResponse.setTargetUserId(pojo.getTargetUserId());
      return challengeResponse;
    } else
      return new ChallengeResponse(ResultType.SYSFAIL);
  }

  /**
   * 发起随机挑战接口
   * 
   * @param pojo 发起挑战参数pojo
   * @return
   * @throws Exception
   */
  private ChallengeResponse randomChallenge(ChallengePojo pojo, CourseProduct product)
      throws Exception {
    Map<String, Object> cond = Maps.newHashMap();
    cond.put("challengerUid", pojo.getUid());
    // 获取个人挑战记录
    List<ChallengeRecord> challengeList = quesOperationFacade.queryChallengeRecord(cond);
    cond = Maps.newHashMap();
    cond.put("beChallengerUid", pojo.getUid());
    // 获取个人被挑战记录
    List<ChallengeRecord> beChallengeList = quesOperationFacade.queryChallengeRecord(cond);
    // 将我已经做过的试卷排除
    List<String> paperIdList = null;
    if (null != challengeList && challengeList.size() > 0) {
      if (null == paperIdList) paperIdList = Lists.newArrayList();
      for (ChallengeRecord challenge : challengeList) {
        paperIdList.add(challenge.getPaperId());
      }
    }
    if (null != beChallengeList && beChallengeList.size() > 0) {
      if (null == paperIdList) paperIdList = Lists.newArrayList();
      for (ChallengeRecord beChallenge : beChallengeList) {
        paperIdList.add(beChallenge.getPaperId());
      }
    }
    List<UserExamRecord> challengeExamRecordList =
        quesOperationFacade.queryNotInExamRecordList(pojo.getUid(), pojo.getCourseId(), Lists
            .newArrayList(ExamType.RANDOM_CHALLENGE_PAPER.getCode(),
                ExamType.FRIEND_CHALLENGE_PAPER.getCode()), paperIdList);
    if (null == challengeExamRecordList || challengeExamRecordList.size() == 0)
      return robotChallenge(pojo, product);
    Collections.shuffle(challengeExamRecordList);
    UserExamRecord choosedRecord = challengeExamRecordList.get(0);
    ChallengeRecord record = new ChallengeRecord();
    record.setChallengerUid(pojo.getUid());
    record.setCourseId(pojo.getCourseId());
    record.setCourseName(product.getName());
    record.setType(pojo.getType());
    record.setBeChallengerUid(choosedRecord.getUserId());
    record.setPaperId(choosedRecord.getPaperNo());
    record.setBeChallengerScore(choosedRecord.getMyScore()); // 设置被挑战者得分
    record.setBeChallengerDetail(choosedRecord.getExamDetail()); // 设置挑战明细
    record.setStatus(QuesBaseConstant.QUES_CHALLENGE_STATUS_YIKAISHI); // 设置状态为已开始
    record.setCreateTime(new Timestamp(System.currentTimeMillis())); // 设置记录生成时间
    if (quesOperationFacade.insertChallengeRecord(record)) {
      ChallengeResponse challengeResponse = new ChallengeResponse(ResultType.SUCCESS);
      challengeResponse.setRecordId(record.getId());
      challengeResponse.setTargetUserId(choosedRecord.getUserId());
      return challengeResponse;
    } else {
      return new ChallengeResponse(ResultType.SYSFAIL);
    }
  }

  /**
   * 机器人模拟随机挑战
   * 
   * @param pojo
   * @param product
   * @throws Exception
   */
  private ChallengeResponse robotChallenge(ChallengePojo pojo, CourseProduct product)
      throws Exception {
    Map<String, Object> cond = Maps.newHashMap();
    Map<String, Object> input = Maps.newHashMap();
    input.put("majorId", pojo.getTypeCode());
    input.put("courseId", pojo.getCourseId());
    cond.put("input", input);
    cond.put("output", CommUtil.getAllField(ChallengeRobot.class));
    Page<ChallengeRobot> robotPage = quesOperationFacade.queryChallengeRobot(cond, null);
    if (null == robotPage || null == robotPage.getResult() || robotPage.getResult().size() == 0) {
      // 没有机器人信息,发起报警
      LoggerUtil.alarmInfo(new ChallengeAlarm(ResultType.WAIT4SOMEPEOPLE.getMsg()));
      return new ChallengeResponse(ResultType.WAIT4SOMEPEOPLE);
    }
    List<ChallengeRobot> robotList = robotPage.getResult();
    Collections.shuffle(robotList);
    ChallengeRobot robot = null;
    for (ChallengeRobot _robot : robotList) {
      if (null != _robot && null != _robot.getUserId()
          && !pojo.getUid().equals(_robot.getUserId().toString())) {
        robot = _robot;
        break;
      }
    }
    if (null == robot) return new ChallengeResponse(ResultType.WAIT4SOMEPEOPLE);
    pojo.setType(QuesBaseConstant.QUES_CHALLENGE_RANDOM);
    pojo.setTargetUserId(robot.getUserId().toString());
    ChallengeResponse response = friendChallenge0(pojo, product);
    if (response.isRetOk()) {
      ExamDetailPojo challengePojo = new ExamDetailPojo();
      challengePojo.setUid(robot.getUserId().toString());
      challengePojo.setExamType(ExamType.FRIEND_CHALLENGE_PAPER.getCode());
      challengePojo.setCourseId(pojo.getCourseId());
      challengePojo.setModule(pojo.getModule());
      challengePojo.setMajorId(pojo.getMajorId());
      challengePojo.setRecordId(response.getRecordId());
      challengePojo.setTypeCode(pojo.getTypeCode());
      RobotChallengeVo vo = new RobotChallengeVo();
      vo.setExamDetailPojo(challengePojo);
      vo.setRobot(robot);
      // queueService.addRobotChallenge(vo);
      // 20180124_改为同步模式生成机器人条件信息
      addRobotChallenge(vo);
    }
    return response;
  }

  /**
   * 同步添加机器人挑战记录
   * 
   * @param vo 机器人挑战参数类
   */
  private void addRobotChallenge(RobotChallengeVo vo) {
    try {
      ExamDetailResponse examDetail = quesBaseServices.autoExamDetail(vo.getExamDetailPojo());
      if (!examDetail.isRetOk()) {
        LoggerUtil.error(
            String.format("机器人答题异常[%s][%s]", examDetail, FastJsonUtil.toJson(vo.getRobot())),
            new RuntimeException(examDetail.getRetdesc()));
        return;
      }
      quesBaseServices.autoSubmitExamResult(processExam(examDetail, vo));
    } catch (Exception e) {
      LoggerUtil.error(
          String.format("机器人答题异常[%s][%s]", vo.getExamDetailPojo().getRecordId(),
              FastJsonUtil.toJson(vo.getRobot())), e);
    }
  }

  /**
   * 处理测验流程
   * 
   * @param examDetail 出题接口响应类
   * @param vo 机器人挑战参数类
   * @return 提交答案接口参数类
   */
  private ExamResultPojo processExam(ExamDetailResponse examDetail, RobotChallengeVo vo) {
    Random rand = new Random();
    ChallengeRobot robot = vo.getRobot();
    Integer targetAbility = robot.getTargetAbility();
    List<AnswerItem> answerDetail = Lists.newArrayList();
    Integer baseRealAbility = targetAbility - rand.nextInt(10) + rand.nextInt(10);
    Integer targetRightCount =
        Integer.parseInt(examDetail.getExamDetail().getQuesCount()) * baseRealAbility / 100;
    List<Question> questionList = examDetail.getExamDetail().getQuestionList();
    Collections.shuffle(questionList);
    for (int i = 0; i < questionList.size(); i++) {
      Question question = questionList.get(i);
      AnswerItem answerItem = new AnswerItem();
      answerItem.setQuesId(Long.parseLong(question.getQuesId()));
      if (i <= targetRightCount) {
        answerItem.setAnswerIdList(question.getRightAnswerIds());
      } else {
        Set<String> answer =
            chooseWrongAnswer(question.getRightAnswerIds(), question.getAnswerList());
        answerItem.setAnswerIdList(Lists.newArrayList(answer));
      }
      answerDetail.add(answerItem);
    }
    ExamResultPojo pojo = new ExamResultPojo();
    pojo.setCourseId(vo.getExamDetailPojo().getCourseId());
    pojo.setExamDetail(FastJsonUtil.toJson(answerDetail));
    pojo.setExamTime(Integer.toString(rand.nextInt(300)));
    pojo.setModule(vo.getExamDetailPojo().getModule());
    pojo.setMajorId(vo.getExamDetailPojo().getMajorId());
    pojo.setRecordId(vo.getExamDetailPojo().getRecordId());
    pojo.setTypeCode(vo.getExamDetailPojo().getTypeCode());
    pojo.setUid(vo.getExamDetailPojo().getUid());
    pojo.setPaperId(examDetail.getExamDetail().getPaperId());
    pojo.setExamStatus(ExamSubmitStatus.SUBMIT.getCode());
    return pojo;
  }

  /**
   * 错误答案选择器
   * 
   * @param rightAnswerList 正确答案列表
   * @param answerList 答案列表
   * @return 选择答案列表
   */
  private Set<String> chooseWrongAnswer(List<String> rightAnswerList, List<Answer> answerList) {
    Set<String> chooseAnswer = Sets.newHashSet();
    for (int i = 0; i < rightAnswerList.size(); i++) {
      Answer chooseAnswer0 = chooseWrongAnswer0(rightAnswerList, answerList);
      rightAnswerList.remove(chooseAnswer0.getAnswerId());
      answerList.remove(chooseAnswer0);
      chooseAnswer.add(chooseAnswer0.getAnswerId());
    }
    return chooseAnswer;
  }

  /**
   * 错误答案选择器
   * 
   * @param rightAnswerList 正确答案列表
   * @param answerList 答案列表
   * @return 选择答案实体
   */
  private Answer chooseWrongAnswer0(List<String> rightAnswerList, List<Answer> answerList) {
    Collections.shuffle(answerList);
    for (Answer answer : answerList) {
      if (!rightAnswerList.contains(answer.getAnswerId())) {
        return answer;
      }
    }
    return answerList.get(answerList.size() - 1);
  }

  /**
   * 挑战记录列表
   * 
   * @param pojo
   * @return
   */
  public PkListResponse pkList(QuesBasePojo pojo) {
    if (!checkOrderCourseId(pojo, pojo.getCourseId()))
      return new PkListResponse(ResultType.UNVALIDCOURSEID4TARGETUSER);
    PkListResponse response = new PkListResponse(ResultType.SUCCESS);
    Map<String, Object> cond = Maps.newHashMap();
    cond.put("challengerUid", pojo.getUid());
    cond.put("status", QuesBaseConstant.QUES_CHALLENGE_STATUS_YIJIESHU);
    cond.put("createTimeLower", new Timestamp(DateUtil.getTimesmorning(-30)));
    cond.put("createTimeUpper", new Timestamp(DateUtil.getTimesnight(0)));
    cond.put("courseId", pojo.getCourseId());
    List<ChallengeRecord> challengeList = quesOperationFacade.queryChallengeRecord(cond);
    if (null != challengeList && challengeList.size() > 0) {
      for (ChallengeRecord challengeRecord : challengeList) {
        PkAbstractResult result = new PkAbstractResult();

        result.setRecordId(challengeRecord.getId());
        result.setCourseId(challengeRecord.getCourseId());
        result.setCourseName(challengeRecord.getCourseName());
        result.setUserId(challengeRecord.getBeChallengerUid());
        if (null != challengeRecord.getResult())
          result.setResult(challengeRecord.getResult().toString());
        else
          result.setResult(QuesBaseConstant.QUES_CHALLENGE_RESULT_UNKNOWN.toString());
        if (StringUtils.isJsonNotBlank(challengeRecord.getChallengerDetail())) {
          UserExamRecordDetail detail =
              FastJsonUtil.fromJson(challengeRecord.getChallengerDetail(),
                  UserExamRecordDetail.class);
          result.setRightCount(detail.getRightCount());
          result.setWrongCount(detail.getQuesCount() - detail.getRightCount());
        }
        result.setDate(DateUtil.relativeDateFormat(challengeRecord.getUpdateTime()));
        result.setCreateDate(DateUtil.relativeDateFormat(challengeRecord.getCreateTime()));
        result.set_timeStamp(challengeRecord.getCreateTime());
        response.getList().add(result);
      }
    }

    cond = Maps.newHashMap();
    cond.put("beChallengerUid", pojo.getUid());
    cond.put("type", QuesBaseConstant.QUES_CHALLENGE_FRIEND);
    cond.put("status", QuesBaseConstant.QUES_CHALLENGE_STATUS_YIJIESHU);
    cond.put("createTimeLower", new Timestamp(DateUtil.getTimesmorning(-30)));
    cond.put("createTimeUpper", new Timestamp(DateUtil.getTimesnight(0)));
    cond.put("courseId", pojo.getCourseId());
    List<ChallengeRecord> beChallengeList = quesOperationFacade.queryChallengeRecord(cond);
    if (null != beChallengeList && beChallengeList.size() > 0) {
      for (ChallengeRecord challengeRecord : beChallengeList) {
        PkAbstractResult result = new PkAbstractResult();
        result.setRecordId(challengeRecord.getId());
        result.setCourseId(challengeRecord.getCourseId());
        result.setCourseName(challengeRecord.getCourseName());
        result.setUserId(challengeRecord.getChallengerUid());
        if (null != challengeRecord.getResult())
          result.setResult(switchResult(challengeRecord.getResult()).toString());
        else
          result.setResult(QuesBaseConstant.QUES_CHALLENGE_RESULT_UNKNOWN.toString());
        if (StringUtils.isJsonNotBlank(challengeRecord.getChallengerDetail())) {
          UserExamRecordDetail detail =
              FastJsonUtil.fromJson(challengeRecord.getChallengerDetail(),
                  UserExamRecordDetail.class);
          result.setRightCount(detail.getRightCount());
          result.setWrongCount(detail.getQuesCount() - detail.getRightCount());
        }
        result.setDate(DateUtil.relativeDateFormat(challengeRecord.getUpdateTime()));
        result.setCreateDate(DateUtil.relativeDateFormat(challengeRecord.getCreateTime()));
        result.set_timeStamp(challengeRecord.getCreateTime());
        response.getList().add(result);
      }
    }
    Collections.sort(response.getList(), new Comparator<PkAbstractResult>() {
      @Override
      public int compare(PkAbstractResult o1, PkAbstractResult o2) {
        return o1.get_timeStamp().after(o2.get_timeStamp()) ? -1 : 1;
      }
    });
    return response;
  }

  private Short switchResult(Short challengeResult) {
    if (challengeResult == QuesBaseConstant.QUES_CHALLENGE_RESULT_SUCCESS)
      return QuesBaseConstant.QUES_CHALLENGE_RESULT_FAIL;
    if (challengeResult == QuesBaseConstant.QUES_CHALLENGE_RESULT_FAIL)
      return QuesBaseConstant.QUES_CHALLENGE_RESULT_SUCCESS;
    return challengeResult;
  }

  /**
   * 获取挑战结果
   * 
   * @param pojo
   * @return
   */
  public PkResultResponse pkResult(PkResultPojo pojo) {
    // if (!checkCourseId(pojo, pojo.getCourseId())) return new
    // PkResultResponse(ResultType.UNVALIDEDUCATIONID);
    PkResultResponse response = new PkResultResponse(ResultType.SUCCESS);
    ChallengeRecord record = quesOperationFacade.queryChallengeRecord(pojo.getRecordId());
    if (null == record) return new PkResultResponse(ResultType.MISSINGRECORD);
    if (!record.getChallengerUid().equals(pojo.getUid())
        && !record.getBeChallengerUid().equals(pojo.getUid()))
      return new PkResultResponse(ResultType.CANTQUERYRECORD);
    if (record.getChallengerUid().equals(pojo.getUid())) {
      fillAsChallenger(record, response);
    }
    if (record.getBeChallengerUid().equals(pojo.getUid())) {
      fillAsBeChallenger(record, response);
    }
    List<String> examList = getPkRank(pojo.getTypeCode(), record.getCourseId());
    if (null != examList && examList.size() > 0) response.setUserIdList(examList);
    response.setPkExam(this.getPkExam(pojo.getTypeCode(), record.getCourseId(), pojo.getUid()));
    return response;
  }

  /**
   * 获取PK排名列表
   * 
   * @param typeCode 专业码
   * @param courseId 课程ID
   * @return 排名列表
   */
  public List<String> getPkRank(String typeCode, String courseId) {
    List<String> userIdList = Lists.newArrayList();
    Map<String, Object> cond = Maps.newHashMap();
    cond.put("limitStart", 0);
    cond.put("limitCount", 20);
    Map<String, Object> sort = Maps.newHashMap();
    sort.put("score", "DESC");
    cond.put("sort", sort);
    Map<String, Object> input = Maps.newHashMap();
    input.put("courseId", courseId);
    cond.put("input", input);
    List<UserExamTotal> examTotalList = quesOperationFacade.queryExamTotal(cond);
    if (null != examTotalList && examTotalList.size() > 0)
      for (UserExamTotal examTotal : examTotalList)
        userIdList.add(examTotal.getUserId());
    if (userIdList.size() > 20) return userIdList;
    List<String> robotList = getRobotIdList(typeCode, courseId);
    if (null == robotList || robotList.size() == 0) return userIdList;
    userIdList.addAll(robotList);
    return userIdList;
  }

  /**
   * 获取机器人ID列表
   * 
   * @param typeCode 专业码
   * @param courseId 课程ID
   * @return 机器人ID列表
   */
  public List<String> getRobotIdList(String typeCode, String courseId) {
    List<String> robotIdList = Lists.newArrayList();
    Map<String, Object> cond = Maps.newHashMap();
    Map<String, Object> input = Maps.newHashMap();
    input.put("majorId", typeCode);
    input.put("courseId", courseId);
    cond.put("limitStart", 0);
    cond.put("limitCount", 20);
    cond.put("input", input);
    cond.put("output", CommUtil.getAllField(ChallengeRobot.class));
    Page<ChallengeRobot> robotPage = quesOperationFacade.queryChallengeRobot(cond, null);
    if (null == robotPage || null == robotPage.getResult() || robotPage.getResult().size() == 0)
      return null;
    for (ChallengeRobot robot : robotPage.getResult())
      robotIdList.add(robot.getUserId().toString());
    return robotIdList;
  }

  /**
   * 作为挑战者填充PK结果
   */
  private void fillAsChallenger(ChallengeRecord record, PkResultResponse response) {
    if (null != record.getChallengerScore())
      response.setScore(MathUtil.getIntStringValue(record.getChallengerScore()));
    if (null != record.getBeChallengerScore())
      response.setOpponentsScore(MathUtil.getIntStringValue(record.getBeChallengerScore()));
    if (null != record.getChallengerDetail()) {
      UserExamRecordDetail recordDetail =
          FastJsonUtil.fromJson(record.getChallengerDetail(), UserExamRecordDetail.class);
      response.setDetail(recordDetail);
    }
    response.setWinner(record.getWinner());
  }

  /**
   * 作为被挑战者填充PK结果
   */
  private void fillAsBeChallenger(ChallengeRecord record, PkResultResponse response) {
    if (null != record.getBeChallengerScore())
      response.setScore(MathUtil.getIntStringValue(record.getBeChallengerScore()));
    if (null != record.getChallengerScore())
      response.setOpponentsScore(MathUtil.getIntStringValue(record.getChallengerScore()));
    if (null != record.getBeChallengerDetail()) {
      UserExamRecordDetail recordDetail =
          FastJsonUtil.fromJson(record.getBeChallengerDetail(), UserExamRecordDetail.class);
      response.setDetail(recordDetail);
    }
    response.setWinner(record.getWinner());
  }

  public ChallengeRecord updateChallengeRecord(String module, String recordId, String uid,
      UserExamRecordDetail detail) {
    ChallengeRecord record = quesOperationFacade.queryChallengeRecord(recordId);
    if (null == record) return null;
    record.setModule(module);
    if (record.getChallengerUid().equals(uid)) {
      if (null != detail) {
        record.setChallengerDetail(FastJsonUtil.toJson(detail));
        if (null != detail.getScore()) {
          record.setChallengerScore(detail.getScore());
        }
      }
    } else if (record.getBeChallengerUid().equals(uid)) {
      if (null != detail) {
        record.setBeChallengerDetail(FastJsonUtil.toJson(detail));
        if (null != detail.getScore()) {
          record.setBeChallengerScore(detail.getScore());
        }
      }
    }
    if (null != record.getChallengerScore() && null != record.getBeChallengerScore()) {
      if (record.getChallengerScore() > record.getBeChallengerScore()) {
        record.setWinner(record.getChallengerUid());
        record.setResult(QuesBaseConstant.QUES_CHALLENGE_RESULT_SUCCESS);
      } else if (record.getBeChallengerScore() > record.getChallengerScore()) {
        record.setWinner(record.getBeChallengerUid());
        record.setResult(QuesBaseConstant.QUES_CHALLENGE_RESULT_FAIL);
      } else {
        record.setWinner(QuesBaseConstant.QUES_CHALLENGE_RESULT_DRAW_WINNER);
        record.setResult(QuesBaseConstant.QUES_CHALLENGE_RESULT_DRAW);
      }
      record.setStatus(QuesBaseConstant.QUES_CHALLENGE_STATUS_YIJIESHU);
    }
    queueService.updateChallengeRecord(record);
    return record;
  }

  /**
   * 获取PK成績
   * 
   * @param typeCode 专业码
   * @param courseId 课程ID
   * @param userId 用戶ID
   * @return 排名列表
   */
  public PkExam getPkExam(String typeCode, String courseId, String userId) {
    PkExam pk = new PkExam();
    Integer pkTotalCount = 0;
    Integer pkSuccessCount = 0;
    Integer pkFailCount = 0;
    Integer equalCount = 0;
    Map<String, Object> cond = Maps.newHashMap();
    cond.put("challengerUid", userId);
    cond.put("status", QuesBaseConstant.QUES_CHALLENGE_STATUS_YIJIESHU);
    cond.put("createTimeLower", new Timestamp(DateUtil.getTimesmorning(-30)));
    cond.put("createTimeUpper", new Timestamp(DateUtil.getTimesnight(0)));
    cond.put("courseId", courseId);
    List<ChallengeRecord> challengeList = quesOperationFacade.queryChallengeRecord(cond);
    if (null != challengeList && challengeList.size() > 0) {
      for (ChallengeRecord challengeRecord : challengeList) {
        if (null != challengeRecord.getResult()) {
          pkTotalCount++;
          if (challengeRecord.getResult() == 1) {
            pkSuccessCount++;
          } else if (challengeRecord.getResult() == 2) {
            pkFailCount++;
          } else if (challengeRecord.getResult() == 3) {
            equalCount++;
          }
        }
      }
    }

    cond = Maps.newHashMap();
    cond.put("beChallengerUid", userId);
    cond.put("type", QuesBaseConstant.QUES_CHALLENGE_FRIEND);
    cond.put("status", QuesBaseConstant.QUES_CHALLENGE_STATUS_YIJIESHU);
    cond.put("createTimeLower", new Timestamp(DateUtil.getTimesmorning(-30)));
    cond.put("createTimeUpper", new Timestamp(DateUtil.getTimesnight(0)));
    cond.put("courseId", courseId);
    List<ChallengeRecord> beChallengeList = quesOperationFacade.queryChallengeRecord(cond);
    if (null != beChallengeList && beChallengeList.size() > 0) {
      for (ChallengeRecord challengeRecord : beChallengeList) {
        if (null != challengeRecord.getResult()) {
          if (null != challengeRecord.getResult()) {
            short result = switchResult(challengeRecord.getResult());
            pkTotalCount++;
            if (result == 1) {
              pkSuccessCount++;
            } else if (result == 2) {
              pkFailCount++;
            } else if (result == 3) {
              equalCount++;
            }
          }
        }
      }
    }
    pk.setEqualCount(equalCount);
    pk.setPkFailCount(pkFailCount);
    pk.setPkSuccessCount(pkSuccessCount);
    pk.setPkTotalCount(pkTotalCount);
    return pk;
  }


}
