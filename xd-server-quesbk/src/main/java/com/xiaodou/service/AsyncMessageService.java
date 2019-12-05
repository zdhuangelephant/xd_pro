package com.xiaodou.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.xiaodou.async.constant.AsyncMessageConst;
import com.xiaodou.async.domain.AsyncMessage;
import com.xiaodou.async.enums.AsyncMessageEnums.AsyncResInfo;
import com.xiaodou.async.model.PkCallbackMessage;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.constant.ProductConstants;
import com.xiaodou.constant.QuesBaseConstant;
import com.xiaodou.domain.behavior.UserScorePointRecord;
import com.xiaodou.domain.product.ChallengeRecord;
import com.xiaodou.domain.product.RedBonus;
import com.xiaodou.frameworkcache.page.PageManager;
import com.xiaodou.jmsg.entity.JMSGPojo;
import com.xiaodou.jmsg.entity.MessageRemoteResult;
import com.xiaodou.jmsg.entity.MessageRemoteResult.MessageRemoteResultType;
import com.xiaodou.mission.engine.enums.RedBonusType;
import com.xiaodou.mission.model.UserMissionRecordModel;
import com.xiaodou.util.BonusUtil;
import com.xiaodou.vo.mq.FollowBonus;
import com.xiaodou.vo.mq.MissionScore;
import com.xiaodou.vo.mq.StudyCourseWareScore;

@Service("asyncMessageService")
public class AsyncMessageService extends AbstractQuesService {

  @Resource(name = "quesRecordService")
  QuesRecordService quesRecordService;
  @Resource(name = "quesBaseServices")
  QuesBaseServices quesBaseServices;
  @Resource
  PageManager pageManager;

  /**
   * @param pojo
   * @return
   */
  @SuppressWarnings("unchecked")
  public MessageRemoteResult pkCallback(JMSGPojo pojo) {
    MessageRemoteResult messageRemoteResult =
        new MessageRemoteResult(MessageRemoteResultType.SUCCESS);
    messageRemoteResult.setTag(pojo.getTag());
    if (StringUtils.isJsonBlank(pojo.getMessage())) {
      LoggerUtil.error("消息体为空。Tag:" + pojo.getTag(), new RuntimeException());
      messageRemoteResult.setExceptionMessgage("消息体为空。");
      return messageRemoteResult;
    }
    AsyncMessage message = FastJsonUtil.fromJson(pojo.getMessage(), AsyncMessage.class);
    String callBackContent = message.getCallBackContent();
    if (null != callBackContent) {
      Map<String, String> callbackConentMap = FastJsonUtil.fromJson(callBackContent, HashMap.class);
      String recordId = callbackConentMap.get("recordId");
      if (StringUtils.isBlank(recordId)) {
        LoggerUtil.error("missing recordId", new RuntimeException());
        messageRemoteResult.appendRetdesc("missing recordId");
        return messageRemoteResult;
      }
      ChallengeRecord record = quesOperationFacade.queryChallengeRecord(recordId);
      if (record.getStatus() == QuesBaseConstant.QUES_CHALLENGE_STATUS_DAIYINGZHAN) {
        if (AsyncMessageConst.DOMAIN_DEALRESULT_AGREE.equals(message.getDealResult())) {
          record.setStatus(QuesBaseConstant.QUES_CHALLENGE_STATUS_YIKAISHI);
        } else {
          record.setStatus(QuesBaseConstant.QUES_CHALLENGE_STATUS_WEIWANCHENG);
        }
        quesOperationFacade.updateChallengeRecord(record);
      }

      // 回调通知发起pk方结果
      PkCallbackMessage messageCallback = null;
      if (AsyncMessageConst.DOMAIN_DEALRESULT_UNAGREE.equals(message.getDealResult())) {
        messageCallback = new PkCallbackMessage(AsyncResInfo.PKFailCallBack);
      } else {
        messageCallback = new PkCallbackMessage(AsyncResInfo.PKSuccessCallBack);
      }
      messageCallback.setModule(message.getModule());
      messageCallback.setSrcUid(record.getBeChallengerUid());
      messageCallback.setToUid(record.getChallengerUid());
      messageCallback.setMessageName("2_pk_result");
      messageCallback.send();
    }

    return messageRemoteResult;
  }

  public MessageRemoteResult followBonus(JMSGPojo pojo) {
    MessageRemoteResult messageRemoteResult =
        new MessageRemoteResult(MessageRemoteResultType.SUCCESS);
    if (StringUtils.isJsonBlank(pojo.getMessage())) {
      LoggerUtil.error("消息体为空。Tag:" + pojo.getTag(), new RuntimeException());
      messageRemoteResult.setExceptionMessgage("消息体为空。");
      return messageRemoteResult;
    }
    FollowBonus followBonus = FastJsonUtil.fromJson(pojo.getMessage(), FollowBonus.class);
    if (null == followBonus || StringUtils.isBlank(followBonus.getBonusId())) {
      throw new RuntimeException("[followBonus][非法参数][" + FastJsonUtil.toJson(pojo) + "]");
    }
    RedBonus redBonus = quesOperationFacade.queryRedBonusById(followBonus.getBonusId());
    if (null == redBonus) {
      return messageRemoteResult;
    }
    BonusUtil.followUp(redBonus);
    return messageRemoteResult;
  }

  public MessageRemoteResult createRedBonus(JMSGPojo pojo) {
    MessageRemoteResult messageRemoteResult =
        new MessageRemoteResult(MessageRemoteResultType.SUCCESS);
    if (StringUtils.isJsonBlank(pojo.getMessage())) {
      LoggerUtil.error("消息体为空。Tag:" + pojo.getTag(), new RuntimeException());
      messageRemoteResult.setExceptionMessgage("消息体为空。");
      return messageRemoteResult;
    }
    UserMissionRecordModel missionRecord =
        FastJsonUtil.fromJson(pojo.getMessage(), UserMissionRecordModel.class);
    if (null == missionRecord || null == missionRecord.getMission()) {
      throw new RuntimeException("[createRedBonus][非法参数][" + FastJsonUtil.toJson(pojo) + "]");
    }
    if (RedBonusType.Random_pk_win.name().equals(missionRecord.getMission().getRedBonusType())
        || RedBonusType.Tollgate_complete_item.name().equals(
            missionRecord.getMission().getRedBonusType())
        || RedBonusType.Leak_filling_complete.name().equals(
            missionRecord.getMission().getRedBonusType())) {
      RedBonus redBonus = new RedBonus();
      redBonus.setRedBonusType(missionRecord.getMission().getRedBonusType());
      redBonus.setMissionId(missionRecord.getMissionId());
      redBonus.setModule(missionRecord.getModule());
      redBonus.setUserId(missionRecord.getUserId());
      redBonus.setCourseId(missionRecord.getCourseId());
      redBonus.setChapterId(missionRecord.getChapterId());
      redBonus.setItemId(missionRecord.getThresholdNum());
      redBonus.setStatue(QuesBaseConstant.RED_BONUS_STATUS_INIT);
      redBonus.markCreate();
      quesOperationFacade.insertRedBonus(redBonus);
    }
    return messageRemoteResult;
  }

  public MessageRemoteResult updateScore(JMSGPojo pojo) throws Exception {
    MessageRemoteResult messageRemoteResult =
        new MessageRemoteResult(MessageRemoteResultType.SUCCESS);
    if (StringUtils.isJsonBlank(pojo.getMessage())) {
      LoggerUtil.error("消息体为空。Tag:" + pojo.getTag(), new RuntimeException());
      messageRemoteResult.setExceptionMessgage("消息体为空。");
      return messageRemoteResult;
    }
    MissionScore missionScore = FastJsonUtil.fromJson(pojo.getMessage(), MissionScore.class);
    if (null == missionScore) {
      throw new RuntimeException("[updateScore][非法参数][" + FastJsonUtil.toJson(pojo) + "]");
    }
    if (missionScore.getMissionCount() != null
        && (Double.valueOf(missionScore.getMissionCount()) > 0)) {
      UserScorePointRecord record =
          quesOperationFacade.queryScorePointRecord(missionScore.getModule(), null,
              Long.parseLong(missionScore.getCourseId()), Long.parseLong(missionScore.getUserId()),
              ProductConstants.RULE_TYPE_STANDARD_MISSION);
      Double _missionScore =
          Double.valueOf(missionScore.getFinishMissionCount())
              / Double.valueOf(missionScore.getMissionCount()) * 100;
      record.setScore(_missionScore);
      record.setCompletePercent(1d);
      quesOperationFacade.insertOrUpdateUserScorePointRecord(record);
      List<String> typeCodeList =
          quesOperationFacade.queryTypeCodeList(missionScore.getModule(), missionScore
              .getCourseId().toString());
      String[] typeCodes = null;
      if (null != typeCodeList && typeCodeList.size() > 0) {
        typeCodes = new String[typeCodeList.size()];
        typeCodes = typeCodeList.toArray(typeCodes);
      }
      quesOperationFacade.updateUserScoreWithEvent(missionScore.getModule(),
          Long.parseLong(missionScore.getCourseId()), missionScore.getUserId(), typeCodes);
    }
    return messageRemoteResult;
  }

  public MessageRemoteResult studyCourseWare(JMSGPojo pojo) {
    MessageRemoteResult messageRemoteResult =
        new MessageRemoteResult(MessageRemoteResultType.SUCCESS);
    if (StringUtils.isJsonBlank(pojo.getMessage())) {
      LoggerUtil.error("消息体为空。Tag:" + pojo.getTag(), new RuntimeException());
      messageRemoteResult.setExceptionMessgage("消息体为空。");
      return messageRemoteResult;
    }
    StudyCourseWareScore studyCourseWareScore =
        FastJsonUtil.fromJson(pojo.getMessage(), StudyCourseWareScore.class);
    if (null == studyCourseWareScore) {
      throw new RuntimeException("[studyCourseWare][非法参数][" + FastJsonUtil.toJson(pojo) + "]");
    }
    UserScorePointRecord record =
        quesOperationFacade.queryScorePointRecord(studyCourseWareScore.getModule(), null,
            Long.parseLong(studyCourseWareScore.getCourseId()),
            Long.parseLong(studyCourseWareScore.getUserId()),
            ProductConstants.RULE_TYPE_STUDY_COURSE_WARE);
    Double _studyCourseWareScore = studyCourseWareScore.getCourseWareScore() * 100;
    if (_studyCourseWareScore > 100d) {
      _studyCourseWareScore = 100d;
    }
    record.setScore(_studyCourseWareScore);
    record.setCompletePercent(1d);
    quesOperationFacade.insertOrUpdateUserScorePointRecord(record);
    List<String> typeCodeList =
        quesOperationFacade.queryTypeCodeList(studyCourseWareScore.getModule(),
            studyCourseWareScore.getCourseId().toString());
    String[] typeCodes = null;
    if (null != typeCodeList && typeCodeList.size() > 0) {
      typeCodes = new String[typeCodeList.size()];
      typeCodes = typeCodeList.toArray(typeCodes);
    }
    quesOperationFacade.updateUserScoreWithEvent(studyCourseWareScore.getModule(),
        Long.parseLong(studyCourseWareScore.getCourseId()), studyCourseWareScore.getUserId(),
        typeCodes);
    return messageRemoteResult;
  }

}
