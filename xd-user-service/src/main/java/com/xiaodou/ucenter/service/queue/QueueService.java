package com.xiaodou.ucenter.service.queue;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.xiaodou.common.util.DateUtil;
import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.push.agent.client.PushClient;
import com.xiaodou.push.agent.enums.MessageType;
import com.xiaodou.push.agent.enums.SpreadRange;
import com.xiaodou.push.agent.enums.TargetType;
import com.xiaodou.queue.client.AbstractMQClient;
import com.xiaodou.queue.client.IMQClient;
import com.xiaodou.queue.manager.DefaultMessageQueueManager;
import com.xiaodou.queue.worker.AliyunWorker;
import com.xiaodou.ucenter.enums.PushPar;
import com.xiaodou.ucenter.model.UserModel;
import com.xiaodou.ucenter.request.BaseRequest;
import com.xiaodou.ucenter.service.UcenterService;

@Service("QueueService")
public class QueueService {
  @Resource
  UcenterService ucenterService;

  public enum Message {
    LogoutMessage("LogoutMessage"), RegistIMAccount("RegistIMAccount");
    Message(String message) {
      this.message = message;
    }

    private String message;

    public String getMessage() {
      return message;
    }

    public void setMessage(String message) {
      this.message = message;
    }

  }

  private IMQClient m = new AbstractMQClient(AliyunWorker.class, DefaultMessageQueueManager.class);

  public void pushLogoutMessage(String registerId) {
    m.sendMessage(Message.LogoutMessage.getMessage(), registerId);
  }

  public void registIMAccount(UserModel model) {
    m.sendMessage(Message.RegistIMAccount.getMessage(), model);
  }

  public void addAliyunMessage(String messageName, Object message) {
    m.sendMessage(messageName, message);
  }


  public void pushLoginOut(UserModel entity, String registrationId, String systemType) {
    try {
      // 当同一用户在不同的设备登录，并且在之前设备上此用户没有退出登录时
      PushPar untokencode = PushPar.UNTOKENCODE;
      PushPar untokenmsg = PushPar.UNTOKENMSG;
      com.xiaodou.push.agent.model.Message ms = new com.xiaodou.push.agent.model.Message();
      String loginDate = DateUtil.formatDate(new Timestamp(System.currentTimeMillis()), "HH:mm");
      ms.setModule(entity.getModule());
      ms.setMessageContent("您的账号于" + loginDate + "在另一台手机登录。如非本人操作，则密码可能已泄露，建议前往“我的->设置”修改密码");
      new Date(System.currentTimeMillis());
      ms.setNoticeContent("您的账号于" + loginDate + "在另一台手机登录。如非本人操作，则密码可能已泄露，建议前往“我的->设置”修改密码");
      ms.setMessageType(MessageType.ALL);
      ms.setScope(SpreadRange.REGISTRATION_ID);
      if ("android".equals(systemType)) ms.setTarget(TargetType.ANDROID);
      if ("ios".equals(systemType)) ms.setTarget(TargetType.IOS);
      Map<String, String> messageextras = new HashMap<String, String>();
      Map<String, String> noticeextras = new HashMap<String, String>();
      ms.addRegister(new String[] {registrationId});
      messageextras.put(untokencode.getCode(), untokencode.getMsg());
      messageextras.put(untokenmsg.getCode(), untokenmsg.getMsg());
      messageextras.put("sessionToken", entity.getToken());// 防止自己顶自己
      ms.setMessageextras(messageextras);// 消息参数
      ms.setNoticeextras(noticeextras);
      PushClient.push(ms);
      // 调取退出登录接口
      BaseRequest baseRequest = new BaseRequest();
      baseRequest.setModule(entity.getModule());
      baseRequest.setSessionToken(entity.getToken());
      baseRequest.setDeviceId(entity.getDeviceId());
      ucenterService.loginOut(baseRequest);
    } catch (Exception e) {
      LoggerUtil.error("推送顶人消息失败", e);
    }
  }
}
