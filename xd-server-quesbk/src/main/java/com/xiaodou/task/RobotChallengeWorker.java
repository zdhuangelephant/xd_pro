package com.xiaodou.task;

import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import com.google.common.collect.Lists;
import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.domain.product.ChallengeRobot;
import com.xiaodou.enums.ExamSubmitStatus;
import com.xiaodou.queue.annotation.HandlerMessage;
import com.xiaodou.queue.callback.IMQCallBacker;
import com.xiaodou.queue.message.DefaultMessage;
import com.xiaodou.queue.worker.AbstractDefaultWorker;
import com.xiaodou.service.QuesBaseServices;
import com.xiaodou.summer.util.SpringWebContextHolder;
import com.xiaodou.vo.request.ExamResultPojo;
import com.xiaodou.vo.request.ExamResultPojo.AnswerItem;
import com.xiaodou.vo.response.ExamDetailResponse;
import com.xiaodou.vo.response.ExamDetailResponse.Question;
import com.xiaodou.vo.response.QuesDetailResponse.Answer;
import com.xiaodou.vo.task.RobotChallengeVo;

@HandlerMessage("RobotChallenge")
public class RobotChallengeWorker extends AbstractDefaultWorker {

  /** serialVersionUID */
  private static final long serialVersionUID = 5060785357838467742L;

  private Random rand = new Random();

  @Override
  public void domain(DefaultMessage message, IMQCallBacker<DefaultMessage> callback) {
    RobotChallengeVo vo =
        FastJsonUtil.fromJson(message.getMessageBodyJson(), RobotChallengeVo.class);
    QuesBaseServices quesBaseServices = SpringWebContextHolder.getBean("quesBaseServices");
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

  private ExamResultPojo processExam(ExamDetailResponse examDetail, RobotChallengeVo vo) {
    ChallengeRobot robot = vo.getRobot();
    Integer targetAbility = robot.getTargetAbility();
    List<AnswerItem> answerDetail = Lists.newArrayList();
    Integer baseRealAbility = targetAbility - rand.nextInt(5) + rand.nextInt(5);
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
        Integer realAbility = 0;
        Integer groupNum =
            100 / (question.getAnswerList().size() - question.getRightAnswerIds().size());
        List<String> answer =
            chooseAnswer(UUID.randomUUID().toString(), question.getRightAnswerIds(),
                question.getAnswerList(), realAbility, groupNum);
        answerItem.setAnswerIdList(answer);
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

  public List<String> chooseAnswer(String uuid, List<String> rightAnswerList,
      List<Answer> answerList, Integer ability, Integer groupNum) {
    List<String> chooseAnswer = Lists.newArrayList();
    for (int i = 0; i < rightAnswerList.size(); i++) {
      Answer chooseAnswer0 = chooseAnswer0(uuid, rightAnswerList, answerList, ability, groupNum);
      rightAnswerList.remove(chooseAnswer0.getAnswerId());
      answerList.remove(chooseAnswer0);
      chooseAnswer.add(chooseAnswer0.getAnswerId());
    }
    return chooseAnswer;
  }

  public Answer chooseAnswer0(String uuid, List<String> rightAnswerList, List<Answer> answerList,
      Integer ability, Integer groupNum) {
    Long total = 0L;
    for (Answer answer : answerList) {
      if (rightAnswerList.contains(answer.getAnswerId()))
        total += ability;
      else
        total += groupNum;
    }
    Long code = uuid.hashCode() & total;
    Collections.shuffle(answerList);
    for (Answer answer : answerList) {
      if (rightAnswerList.contains(answer.getAnswerId()))
        code -= ability;
      else
        code -= groupNum;
      if (code <= 0) return answer;
    }
    return answerList.get(answerList.size() - 1);
  }

  @Override
  public void domain(List<DefaultMessage> message, IMQCallBacker<List<DefaultMessage>> callback) {
    // TODO Auto-generated method stub
  }

  @Override
  public void onException(Throwable t, List<DefaultMessage> message,
      IMQCallBacker<List<DefaultMessage>> callback) {}

  @Override
  public void onException(Throwable t, DefaultMessage message,
      IMQCallBacker<DefaultMessage> callback) {}

}
