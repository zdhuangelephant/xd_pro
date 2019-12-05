package com.xiaodou.userCenter.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.xiaodou.async.constant.AsyncMessageConst;
import com.xiaodou.async.domain.AsyncMessage;
import com.xiaodou.async.model.AbstractAsyncMessage;
import com.xiaodou.async.request.MessageResRequest;
import com.xiaodou.async.request.QueryListRequest;
import com.xiaodou.async.response.QueryListResponse;
import com.xiaodou.common.util.CommUtil;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.jmsg.client.RabbitMQSender;
import com.xiaodou.jmsg.entity.AbstractMessagePojo;
import com.xiaodou.push.agent.client.PushClient;
import com.xiaodou.push.agent.enums.MessageType;
import com.xiaodou.push.agent.enums.PushMessageProLine;
import com.xiaodou.push.agent.enums.SpreadRange;
import com.xiaodou.push.agent.enums.TargetType;
import com.xiaodou.push.agent.model.Message;
import com.xiaodou.push.agent.util.PushUtil;
import com.xiaodou.summer.vo.out.ResultType;
import com.xiaodou.summer.web.BaseController;
import com.xiaodou.userCenter.dao.AsyncMessageMapper;
import com.xiaodou.userCenter.model.UserModel;
import com.xiaodou.userCenter.module.selfTaught.model.StUserInfo;
import com.xiaodou.userCenter.module.selfTaught.model.StUserInfo.StMedal;
import com.xiaodou.userCenter.response.BaseResultInfo;
import com.xiaodou.userCenter.response.BaseUserModel;
import com.xiaodou.userCenter.response.resultype.UcenterResType;
import com.xiaodou.userCenter.service.facade.IUcenterServiceFacade;

@Service("asyncMessageService")
public class AsyncMessageService extends BaseDaoService {

  @Resource
  AsyncMessageMapper asyncMessageMapper;

  @Resource
  IUcenterServiceFacade ucenterServiceFacade;

  private static String MESSAGEID = "messageId";
  private static String TARGET_USER_ID = "userId";
  private static String TARGET_USER_NAME = "userName";
  private static String TARGET_USER_NICKNAME = "nickName";
  private static String TARGET_USER_GENDER = "gender";
  private static String TARGET_USER_ACHIEVEMENT_URL = "achievementImageUrl";
  private static String TARGET_USER_ACHIEVEMENT_NAME = "achievementName";
  private static String TARGET_USER_PORTRAIT = "portraitUrl";

  public <T extends AbstractAsyncMessage> void sendMessage(T message) {
    Errors errors = message.validate();
    if (errors.hasErrors()) {
      LoggerUtil.error("消息体不完整." + BaseController.getErrMsg(errors), new RuntimeException());
      return;
    }
    Map<String, Object> cond = Maps.newHashMap();
    Map<String, Object> input = Maps.newHashMap();
    input.put("srcUid", message.getSrcUid());
    input.put("toUid", message.getToUid());
    input.put("type", message.getType());
    input.put("classify", message.getClassify());
    input.put("dealResult", AsyncMessageConst.DOMAIN_DEALRESULT_INIT);
    cond.put("input", input);
    List<AsyncMessage> messageList = asyncMessageMapper.queryList(cond);
    if (null == messageList || messageList.size() == 0) {
      AsyncMessage domain = message.getDomain();
      asyncMessageMapper.addEntity(domain);

      Map<String, Object> userQuery = Maps.newHashMap();
      List<String> idList = Lists.newArrayList();
      idList.add(message.getSrcUid());
      idList.add(message.getToUid());
      userQuery.put("idList", idList);
      userQuery.put("module", message.getModule());
      List<UserModel> userList1 =
          ucenterServiceFacade.queryUserList(userQuery, CommUtil.getAllField(UserModel.class));
      if (checkMessageUserUnValid(message, userList1)) {
        LoggerUtil.error("目标用户未找到.", new RuntimeException());
        return;
      }
      Map<String, UserModel> userMap = Maps.newHashMap();
      for (UserModel model : userList1)
        userMap.put(model.getId().toString(), model);

      Message pushMessage = new Message();
      pushMessage.setAppMessageId(UUID.randomUUID().toString());
      if (message.getType().equals(AsyncMessageConst.POJO_TYPE_SYSTEMMES)) {
        pushMessage.setMessageContent(message.getMessageBody());
        pushMessage.setNoticeContent(message.getMessageBody());
        pushMessage.setProductLine(PushMessageProLine.SYS_MESSAGE.name());
      } else {
        pushMessage.setMessageContent(String.format(message.getRetDesc(),
            userMap.get(message.getSrcUid()).getNickName()));
        pushMessage.setNoticeContent(String.format(message.getRetDesc(),
            userMap.get(message.getSrcUid()).getNickName()));
        pushMessage.setProductLine(PushMessageProLine.OTHER_MESSAGE.name());
      }
      pushMessage.setMessageType(MessageType.ALL);
      // pushMessage.setScope(SpreadRange.ALIAS);
      pushMessage.setScope(SpreadRange.ALIAS_TAG);
      pushMessage.setTarget(TargetType.ALL);
      Map<String, String> messageextras = new HashMap<String, String>();
      Map<String, String> noticeextras = new HashMap<String, String>();
      pushMessage.addReciever(new String[] {userMap.get(message.getToUid()).getXdUniqueId()});
      pushMessage.addGroup(userMap.get(message.getToUid()).getPackageTag());
      PushUtil.setRetCode(noticeextras, message.getRetCode());
      if (message.getType().equals(AsyncMessageConst.POJO_TYPE_SYSTEMMES)) {
        PushUtil.setRetDesc(noticeextras, message.getMessageBody());
        noticeextras.put(TARGET_USER_ID, message.getSrcUid());
      } else {
        PushUtil.setRetDesc(noticeextras,
            String.format(message.getRetDesc(), userMap.get(message.getSrcUid()).getNickName()));
        noticeextras.put(TARGET_USER_NAME, userMap.get(message.getSrcUid()).getXdUniqueId());
        noticeextras.put(TARGET_USER_NICKNAME, userMap.get(message.getSrcUid()).getNickName());
        noticeextras.put(TARGET_USER_GENDER, userMap.get(message.getSrcUid()).getGender()
            .toString());
        noticeextras.put(TARGET_USER_PORTRAIT, userMap.get(message.getSrcUid()).getPortrait());
      }
      noticeextras.put(MESSAGEID, domain.getId());
      noticeextras.putAll(message.getCallBackContent());
      PushUtil.setRetCode(messageextras, message.getRetCode());
      if (message.getType().equals(AsyncMessageConst.POJO_TYPE_SYSTEMMES)) {
        PushUtil.setRetDesc(messageextras, message.getMessageBody());
        messageextras.put(TARGET_USER_ID, message.getSrcUid());
      } else {
        PushUtil.setRetDesc(messageextras,
            String.format(message.getRetDesc(), userMap.get(message.getSrcUid()).getNickName()));
        messageextras.put(TARGET_USER_ID, userMap.get(message.getSrcUid()).getId().toString());
        messageextras.put(TARGET_USER_NAME, userMap.get(message.getSrcUid()).getXdUniqueId());
        messageextras.put(TARGET_USER_NICKNAME, userMap.get(message.getSrcUid()).getNickName());
        messageextras.put(TARGET_USER_GENDER, userMap.get(message.getSrcUid()).getGender()
            .toString());
        messageextras.put(TARGET_USER_PORTRAIT, userMap.get(message.getSrcUid()).getPortrait());
      }
      messageextras.put(MESSAGEID, domain.getId());

      if (message.getType().equals(AsyncMessageConst.POJO_TYPE_OTHERMES)) {
        String moduleInfo = userMap.get(message.getSrcUid()).getModuleInfo();
        // if (StringUtils.isJsonNotBlank(moduleInfo) && "2".equals(message.getModule())) {
        StUserInfo userInfo = FastJsonUtil.fromJson(moduleInfo, StUserInfo.class);
        if (null != userInfo && StringUtils.isJsonNotBlank(userInfo.getMedal())) {
          StMedal medal = FastJsonUtil.fromJson(userInfo.getMedal(), StMedal.class);
          noticeextras.put(TARGET_USER_ACHIEVEMENT_URL, medal.getMedalImg());
          noticeextras.put(TARGET_USER_ACHIEVEMENT_NAME, medal.getMedalName());
          messageextras.put(TARGET_USER_ACHIEVEMENT_URL, medal.getMedalImg());
          messageextras.put(TARGET_USER_ACHIEVEMENT_NAME, medal.getMedalName());
        }
        // }
      }
      messageextras.putAll(message.getCallBackContent());
      pushMessage.setMessageextras(messageextras);// 消息参数
      pushMessage.setNoticeextras(noticeextras);// 通知参数
      PushClient.push(pushMessage);
    }
  }

  private <T extends AbstractAsyncMessage> boolean checkMessageUserUnValid(T message,
      List<UserModel> userList) {
    if (null == message || null == userList) return true;
    if (AsyncMessageConst.POJO_TYPE_SYSTEMMES == message.getType() && userList.size() >= 1)
      return false;
    if (AsyncMessageConst.POJO_TYPE_OTHERMES == message.getType() && userList.size() >= 2)
      return false;
    return true;
  }

  public BaseResultInfo dealMessage(MessageResRequest message) throws InstantiationException,
      IllegalAccessException {
    UserModel user = queryUser(message);
    if (null == user) {
      return new BaseResultInfo(UcenterResType.NoFoundUser);
    }
    Map<String, Object> cond = Maps.newHashMap();
    Map<String, Object> input = Maps.newHashMap();
    input.put("id", message.getMessageId());
    input.put("toUid", user.getId());
    cond.put("input", input);
    List<AsyncMessage> messageList = asyncMessageMapper.queryList(cond);
    if (null == messageList || messageList.size() == 0) {
      return new BaseResultInfo(UcenterResType.TargetMessageMissed);
    }
    AsyncMessage entity = messageList.get(0);
    if (!entity.getDealResult().equals(AsyncMessageConst.DOMAIN_DEALRESULT_INIT)) {
      return new BaseResultInfo(UcenterResType.TargetMessageNotInit);
    }
    entity.setStatus(AsyncMessageConst.POJO_STATUS_READED);
    entity.setDealResult(message.getResult());
    asyncMessageMapper.updateEntityById(entity);
    if (StringUtils.isNotBlank(entity.getCallBackMessage())) {
      AbstractMessagePojo pojo = new AbstractMessagePojo(entity.getCallBackMessage());
      pojo.setTransferObject(entity);
      RabbitMQSender.getInstance().send(pojo);
    }
    return new BaseResultInfo(ResultType.SUCCESS);
  }

  /**
   * 根据条件查询消息列表
   * 
   * @param pojo
   * @return
   */
  private List<AsyncMessage> queryList0(String idUpper, String uid, String module, String size,
      Short type) {
    Map<String, Object> cond = Maps.newHashMap();
    Map<String, Object> input = Maps.newHashMap();
    AsyncMessage upper = null;
    if (StringUtils.isNotBlank(idUpper)) {
      upper = asyncMessageMapper.queryOneById(idUpper);
    }
    input.put("toUid", uid);
    /// 与地域无关的消息 module = -1
    // input.put("module", module);
    if (null != upper) input.put("createTimeUpperNq", upper.getCreateTime());
    input.put("type", type);
    cond.put("input", input);
    cond.put("limitCount", size);
    return asyncMessageMapper.queryList(cond);
  }

  /**
   * 根据条件查询消息列表
   * 
   * @param pojo
   * @return
   * @throws IllegalAccessException
   * @throws InstantiationException
   */
  public List<AsyncMessage> queryUserList(QueryListRequest pojo, BaseUserModel user)
      throws InstantiationException, IllegalAccessException {
    List<AsyncMessage> queryList0 =
        queryList0(pojo.getUserIdUpper(), user.getId().toString(), pojo.getRegion(),
            pojo.getSize(), AsyncMessageConst.POJO_TYPE_OTHERMES);
    if (null != queryList0 && queryList0.size() > 0) {
      markMessageReaded(queryList0.get(0));
    }
    return queryList0;
  }

  /**
   * 根据条件查询消息列表
   * 
   * @param pojo
   * @return
   * @throws IllegalAccessException
   * @throws InstantiationException
   */
  public List<AsyncMessage> querySysList(QueryListRequest pojo, BaseUserModel user)
      throws InstantiationException, IllegalAccessException {
    List<AsyncMessage> queryList0 =
        queryList0(pojo.getSysIdUpper(), user.getId().toString(), pojo.getRegion(), pojo.getSize(),
            AsyncMessageConst.POJO_TYPE_SYSTEMMES);
    if (null != queryList0 && queryList0.size() > 0) {
      markMessageReaded(queryList0.get(0));
    }
    return queryList0;
  }

  private void markMessageReaded(AsyncMessage model) {
    Map<String, Object> value = Maps.newHashMap();
    value.put("status", AsyncMessageConst.POJO_STATUS_READED);
    Map<String, Object> input = Maps.newHashMap();
    input.put("toUid", model.getToUid());
//    input.put("module", model.getModule());
    input.put("type", model.getType());
    List<String> dealResultLit =
        Lists
            .newArrayList(AsyncMessageConst.DOMAIN_DEALRESULT_AGREE,
                AsyncMessageConst.DOMAIN_DEALRESULT_UNAGREE,
                AsyncMessageConst.DOMAIN_DEALRESULT_STATIC);
    input.put("dealResultList", dealResultLit);
    input.put("createTimeUpperEq", model.getCreateTime());
    asyncMessageMapper.updateList(input, value);
  }

  /**
   * 查询消息列表
   * 
   * @param pojo
   * @return
   * @throws IllegalAccessException
   * @throws InstantiationException
   */
  public QueryListResponse queryList(QueryListRequest pojo) throws InstantiationException,
      IllegalAccessException {
    BaseUserModel user = decideRedisExsited(pojo);
    if (null == user) {
      return new QueryListResponse(ResultType.NOTLOGIN);
    }
    QueryListResponse result = new QueryListResponse(ResultType.SUCCESS);
    if (AsyncMessageConst.POJO_STYPE_SYSTEMMES.equals(pojo.getType())) {
      result.addSysList(querySysList(pojo, user));
    } else if (AsyncMessageConst.POJO_STYPE_OTHERMES.equals(pojo.getType())) {
      result.addUserList(queryUserList(pojo, user));
    }
    return result;
  }

  public Integer count(String uid, Short type, Short status) {
    Map<String, Object> input = Maps.newHashMap();
    input.put("toUid", uid);
    input.put("type", type);
    input.put("status", status);
    return asyncMessageMapper.count(input);
  }

  public Integer countUSysmes(BaseUserModel user) {
    return count(user.getId(), AsyncMessageConst.POJO_TYPE_SYSTEMMES,
        AsyncMessageConst.POJO_STATUS_UNREAD);
  }
}
