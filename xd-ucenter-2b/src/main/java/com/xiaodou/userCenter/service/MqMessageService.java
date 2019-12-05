package com.xiaodou.userCenter.service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.xiaodou.async.constant.AsyncMessageConst;
import com.xiaodou.async.domain.AsyncMessage;
import com.xiaodou.async.enums.AsyncMessageEnums.AsyncResInfo;
import com.xiaodou.async.model.AddFriendCallbackMessage;
import com.xiaodou.common.util.CommUtil;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.jmsg.client.RabbitMQSender;
import com.xiaodou.jmsg.entity.JMSGPojo;
import com.xiaodou.jmsg.entity.MessageRemoteResult;
import com.xiaodou.jmsg.entity.MessageRemoteResult.MessageRemoteResultType;
import com.xiaodou.mission.engine.event.CreditTotalEvent;
import com.xiaodou.mission.engine.event.EventBuilder;
import com.xiaodou.userCenter.cache.UserInfoByTokenCache;
import com.xiaodou.userCenter.constant.UcenterModelConstant;
import com.xiaodou.userCenter.model.CreditChangeLog;
import com.xiaodou.userCenter.model.UserModel;
import com.xiaodou.userCenter.model.UserPraiseModel;
import com.xiaodou.userCenter.model.message.StudentCallBackMessage;
import com.xiaodou.userCenter.model.message.StudentCallBackMessage.StudentCallBackMessageBody;
import com.xiaodou.userCenter.model.message.StudentCallBackMessage.StudentCallBackMessageBodyDTO;
import com.xiaodou.userCenter.model.message.StudentMessageBody;
import com.xiaodou.userCenter.model.message.StudentMessageBody.StudentMessageBodyDTO;
import com.xiaodou.userCenter.module.selfTaught.request.StOfficialInfoRegist;
import com.xiaodou.userCenter.module.selfTaught.response.StUserInfoResponse;
import com.xiaodou.userCenter.request.AddCreditRequest;
import com.xiaodou.userCenter.response.BaseUserModel;
import com.xiaodou.userCenter.response.ImportMemberResponse;
import com.xiaodou.userCenter.service.facade.IUcenterServiceFacade;
import com.xiaodou.userCenter.util.CreditLogIDGenerator;

/**
 * @name @see com.xiaodou.userCenter.service.MqMessageService.java
 * @CopyRright (c) 2018 by XiaoDou NetWork Technology
 *
 * @author <a href="mailto:lidehong@corp.51xiaodou.com">lidehong</a>
 * @date 2018年4月18日
 * @description 发送消息
 * @version 2.0
 */
@Service("mqMessageService")
public class MqMessageService {

  @Resource
  FriendService friendService;

  @Resource
  IUcenterServiceFacade ucenterServiceFacade;

  @Resource
  UcenterService ucenterService;

  /**
   * @param pojo
   * @return
   */
  public MessageRemoteResult addfriendCallback(JMSGPojo pojo) {
    MessageRemoteResult messageRemoteResult =
        new MessageRemoteResult(MessageRemoteResultType.SUCCESS);
    if (StringUtils.isJsonBlank(pojo.getMessage())) {
      LoggerUtil.error("消息体为空。Tag:" + pojo.getTag(), new RuntimeException());
      messageRemoteResult.setExceptionMessgage("消息体为空。");
      return messageRemoteResult;
    }
    AsyncMessage message = FastJsonUtil.fromJson(pojo.getMessage(), AsyncMessage.class);

    AddFriendCallbackMessage messageCallback = null;
    if (AsyncMessageConst.DOMAIN_DEALRESULT_AGREE.equals(message.getDealResult())) {
      friendService.doAddFriend(message.getSrcUid(), message.getToUid());
      friendService.doAddFriend(message.getToUid(), message.getSrcUid());
      messageCallback = new AddFriendCallbackMessage(AsyncResInfo.AddFriendSuccessCallBack);
    } else {
      messageCallback = new AddFriendCallbackMessage(AsyncResInfo.AddFriendFailCallBack);
    }
    messageCallback.setSrcUid(message.getToUid());
    messageCallback.setToUid(message.getSrcUid());
    messageCallback.setMessageName("addfriend_result");
    messageCallback.send();

    return messageRemoteResult;
  }

  public MessageRemoteResult addCredit(JMSGPojo pojo)
      throws InstantiationException, IllegalAccessException {
    MessageRemoteResult messageRemoteResult =
        new MessageRemoteResult(MessageRemoteResultType.SUCCESS);
    if (StringUtils.isJsonBlank(pojo.getMessage())) {
      LoggerUtil.error("消息体为空。Tag:" + pojo.getTag(), new RuntimeException());
      messageRemoteResult.setExceptionMessgage("消息体为空。");
      return messageRemoteResult;
    }
    AddCreditRequest request = FastJsonUtil.fromJson(pojo.getMessage(), AddCreditRequest.class);

    Map<String, Object> input = Maps.newHashMap();
    input.put("id", request.getUid());
    List<UserModel> userList =
        ucenterServiceFacade.queryUserList(input, CommUtil.getAllField(UserModel.class));
    if (null == userList || userList.size() == 0) {
      LoggerUtil.error("目标用户不存在。Tag:" + pojo.getTag(), new RuntimeException());
      messageRemoteResult.setExceptionMessgage("目标用户不存在。");
      return messageRemoteResult;
    }
    CreditChangeLog log = new CreditChangeLog();
    log.setId(CreditLogIDGenerator.getSeqID());
    log.setCustomTag(pojo.getTag());
    log.setUserId(request.getUid());
    log.setOperateDesc(request.getType());
    if (null == request.getCreditUpper() || request.getCreditUpper() == 0) {
      return messageRemoteResult;
    } else if (request.getCreditUpper() < 0) {
      log.setCount(request.getCreditUpper().toString());
    } else {
      log.setCount("+" + request.getCreditUpper().toString());
    }
    log.setCreateTime(new Timestamp(System.currentTimeMillis()));
    try {
      ucenterServiceFacade.addCreditChangeLog(log);
      UserModel targetUser = userList.get(0);
      UserModel user = new UserModel();
      user.setCredit(targetUser.getCredit() + request.getCreditUpper());
      targetUser.setCredit(user.getCredit());
      ucenterServiceFacade.updateUser(input, user);
      UserInfoByTokenCache.deleteUserInfoFromCache(targetUser.getToken());
      StUserInfoResponse userInfo =
          (StUserInfoResponse) new StUserInfoResponse().initFromUserModel(targetUser);
      UserInfoByTokenCache.addUserInfoToCache(userInfo, targetUser.getToken());
      user.setId(targetUser.getId());
      sendCreditTotalEvent(user,request.getModule());
    } catch (Exception e) {
      LoggerUtil.error("更新用户积分失败.log:" + FastJsonUtil.toJson(log), e);
    }
    return messageRemoteResult;
  }

  private void sendCreditTotalEvent(UserModel user,String module) {
    CreditTotalEvent event = EventBuilder.buildCreditTotalEvent();
    event.setUserId(user.getId().toString());
    event.setModule(module);
    event.setTotalCredit(user.getCredit());
    event.send();
  }

  public MessageRemoteResult updatePraiseNum(JMSGPojo pojo) {
    MessageRemoteResult messageRemoteResult =
        new MessageRemoteResult(MessageRemoteResultType.SUCCESS);
    if (StringUtils.isJsonBlank(pojo.getMessage())) {
      LoggerUtil.error("消息体为空。Tag:" + pojo.getTag(), new RuntimeException());
      messageRemoteResult.setExceptionMessgage("消息体为空。");
      return messageRemoteResult;
    }
    UserPraiseModel request = FastJsonUtil.fromJson(pojo.getMessage(), UserPraiseModel.class);

    Map<String, Object> input = Maps.newHashMap();
    input.put("id", request.getTargetUserId());
    List<UserModel> userList =
        ucenterServiceFacade.queryUserList(input, CommUtil.getAllField(BaseUserModel.class));
    if (null == userList || userList.size() == 0) {
      LoggerUtil.error("目标用户不存在。Tag:" + pojo.getTag(), new RuntimeException());
      messageRemoteResult.setExceptionMessgage("目标用户不存在。");
      return messageRemoteResult;
    }
    List<UserPraiseModel> praiseL =
        ucenterServiceFacade.queryUserPraiseList(request.getTargetUserId());

    UserModel user = new UserModel();
    user.setBePraiseNum(praiseL.size());
    ucenterServiceFacade.updateUser(input, user);
    return messageRemoteResult;
  }

  /**
   * @deprecated
   * @description 教师端后台，定时任务
   * @author 李德洪
   * @Date 2017年4月19日
   * @param pojo
   * @return
   */
  public MessageRemoteResult studentScheduler(JMSGPojo pojo) {
    MessageRemoteResult messageRemoteResult =
        new MessageRemoteResult(MessageRemoteResultType.SUCCESS);
    if (StringUtils.isJsonBlank(pojo.getMessage())) {
      LoggerUtil.error("消息体为空。Tag:" + pojo.getTag(), new RuntimeException());
      messageRemoteResult.setExceptionMessgage("消息体为空。");
      return messageRemoteResult;
    }
    StudentMessageBody messageBody =
        FastJsonUtil.fromJson(pojo.getMessage(), StudentMessageBody.class);
    try {
      List<StudentCallBackMessageBodyDTO> scmbList = Lists.newArrayList();
      for (StudentMessageBodyDTO stm : messageBody.getMessageBody()) {
        StOfficialInfoRegist request = new StOfficialInfoRegist();
        request.setAdmissionCardCode(stm.getAdmissionCardCode());
        request.setRealName(stm.getRealName());
        request.setOfficialGender(stm.getGender());
        request.setIdentificationCardCode(stm.getIdentificationCardCode());
        request.setMerchant(stm.getPilotUnitName());
        // request.setPhoneNum(stm.getTelephone());
        ImportMemberResponse response = ucenterService.importMember(request);
        StudentCallBackMessageBodyDTO scmb = new StudentCallBackMessageBodyDTO();
        scmb.setStudentId(stm.getStudentId());
        if (!response.getRetcode().equals("0")) {
          // 记录失败日志
          scmb.setStudentStatus(UcenterModelConstant.FAIL_REGISTER);
          break;
        }
        // 发消息
        scmb.setUserId(response.getUserId());
        scmb.setStudentStatus(UcenterModelConstant.SUCCESS_REGISTER);
        scmbList.add(scmb);
        // 发消息
        this.sendStudentCallBackMessage(scmbList);
      }
    } catch (Exception e) {
      LoggerUtil.error("导入用户异常", e);
      messageRemoteResult.setExceptionMessgage("导入用户异常。");
      return messageRemoteResult;
    }
    return messageRemoteResult;
  }

  /**
   * @deprecated
   */
  public void sendStudentCallBackMessage(List<StudentCallBackMessageBodyDTO> scmbList) {
    StudentCallBackMessageBody messageBody = new StudentCallBackMessageBody();
    messageBody.setMessageBody(scmbList);
    RabbitMQSender.getInstance().send(new StudentCallBackMessage(messageBody));
  }

  /**
   * @deprecated
   */
  public void sendStudent(List<StudentCallBackMessageBodyDTO> scmbList) {
    StudentCallBackMessageBody messageBody = new StudentCallBackMessageBody();
    messageBody.setMessageBody(scmbList);
    RabbitMQSender.getInstance().send(new StudentCallBackMessage(messageBody));
  }


}
