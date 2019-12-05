package com.xiaodou.common.worker;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.xiaodou.common.util.StringUtils;
import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.queue.annotation.HandlerMessage;
import com.xiaodou.queue.callback.IMQCallBacker;
import com.xiaodou.queue.enums.CallBackStatus;
import com.xiaodou.queue.message.DefaultMessage;
import com.xiaodou.queue.worker.AbstractDefaultWorker;
import com.xiaodou.sms.model.SmsLogModel;
import com.xiaodou.sms.model.SmsTaskModel;
import com.xiaodou.sms.service.facade.ISmsServiceFacade;
import com.xiaodou.sms.service.platform.PlatformServiceFactory;
import com.xiaodou.sms.vo.MessageInfo;
import com.xiaodou.sms.vo.MessageResult;
import com.xiaodou.summer.util.SpringWebContextHolder;

@HandlerMessage("sms_checkcode")
public class SmsCheckCodeHandler extends AbstractDefaultWorker {

  /** serialVersionUID */
  private static final long serialVersionUID = -8839558984440250964L;

  @Override
  public void domain(DefaultMessage message, IMQCallBacker<DefaultMessage> callback) {
    ISmsServiceFacade smsServiceFacade = SpringWebContextHolder.getBean("smsServiceFacade");
    if (null == smsServiceFacade || StringUtils.isBlank(message.getMessageBodyJson())) return;
    SmsTaskModel model = FastJsonUtil.fromJson(message.getMessageBodyJson(), SmsTaskModel.class);
    if (StringUtils.isBlank(model.getMobile()) || StringUtils.isBlank(model.getMessage())) return;
    List<Integer> channelIdList = model.getChannelIdList();
    if (channelIdList.size() <= 0) {
      LoggerUtil.error("没有可用的短信通道.", new RuntimeException());
      return;
    }
    try {
      MessageInfo messageInfo = new MessageInfo();
      messageInfo.setTelephone(model.getMobile());
      messageInfo.setContent(model.getMessage());
      
      model.getProductLine();
      MessageResult mr = PlatformServiceFactory
          .getSmsPlatformService(channelIdList.get(0).toString()).sendSms(messageInfo);
      System.out.println(mr.getFailReason());
      int sendStatus = 0;
      String channelSendResult = "无返回数据";
      if (null != mr) {
        sendStatus = ("0").equals(mr.isStatus()) ? 2 : 0;
        channelSendResult = (null == mr.getFailReason()) ? "发送成功" : mr.getFailReason();
      }
      // 生成短信成功之后，将task从表中逻辑删除
      Map<String, Object> condition = new HashMap<String, Object>();
      condition.put("id", model.getId());
      SmsTaskModel task1 = new SmsTaskModel();
      task1.setStatus(sendStatus);
      task1.setMsg(channelSendResult);
      smsServiceFacade.updateSmsTaskModelEntity(condition, task1);
      addLogModel(smsServiceFacade, model, sendStatus, channelSendResult);
    } catch (Exception e) {
      LoggerUtil.error("发送失败", e);
      addLogModel(smsServiceFacade, model, -1, "发送失败." + e.getMessage());
      channelIdList.remove(0);
      model.setChannelIdList(channelIdList);
      if (channelIdList.size() > 0) {
        message.setMessageBodyJson(FastJsonUtil.toJson(model));
        if (message.getFailedCount() < 3) {
          message.addFailedCount();
          callback.onFail(CallBackStatus.FAIL, message);
        }
      }
    }
    callback.onSuccess(message);
  }

  private void addLogModel(ISmsServiceFacade smsServiceFacade, SmsTaskModel model, int sendStatus,
      String channelSendResult) {
    try {
      // 添加log表数据
      SmsLogModel smsLogModel = new SmsLogModel();
      smsLogModel.setChannelId(model.getChannelIdList().get(0));
      smsLogModel.setChannelSendResult(channelSendResult);
      smsLogModel.setCreateTime(new Timestamp(System.currentTimeMillis()));
      smsLogModel.setMessage(model.getMessage());
      smsLogModel.setMobile(model.getMobile());
      smsLogModel.setOperateTime(new Timestamp(System.currentTimeMillis()));
      smsLogModel.setSendStatus(sendStatus);
      smsLogModel.setTemplateId(model.getTemplateId());
      smsLogModel.setTypeId(model.getTemplateTypeId());

      // TODO 1、设置应用端类型；2、设置消息业务ID(UUID)
      smsLogModel.setAppMessageId(model.getAppMessageId());
      smsLogModel.setProductLine(model.getProductLine());
      smsServiceFacade.addSmsLogModelEntity(smsLogModel);// 添加一条短信日志
    } catch (Exception e) {
      LoggerUtil.error("记录短信日志失败", e);
    }
  }

  @Override
  public void domain(List<DefaultMessage> message, IMQCallBacker<List<DefaultMessage>> callback) {


  }

  @Override
  public void onException(Throwable t, List<DefaultMessage> message,
      IMQCallBacker<List<DefaultMessage>> callback) {
    // TODO Auto-generated method stub

  }

  @Override
  public void onException(Throwable t, DefaultMessage message,
      IMQCallBacker<DefaultMessage> callback) {
    LoggerUtil.error("发送短信异常", t);
    // callback.onFail(CallBackStatus.EXCEPTION, message);
  }

}
