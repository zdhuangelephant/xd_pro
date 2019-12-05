package com.xiaodou.userCenter.task;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.xiaodou.common.util.DateUtil;
import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.push.agent.client.PushClient;
import com.xiaodou.push.agent.enums.MessageType;
import com.xiaodou.push.agent.enums.PushMessageProLine;
import com.xiaodou.push.agent.enums.SpreadRange;
import com.xiaodou.push.agent.enums.TargetType;
import com.xiaodou.queue.annotation.HandlerMessage;
import com.xiaodou.queue.callback.IMQCallBacker;
import com.xiaodou.queue.message.DefaultMessage;
import com.xiaodou.queue.worker.AbstractDefaultWorker;
import com.xiaodou.summer.util.SpringWebContextHolder;
import com.xiaodou.userCenter.enums.PushPar;
import com.xiaodou.userCenter.model.UserModel;
import com.xiaodou.userCenter.request.BaseRequest;
import com.xiaodou.userCenter.service.UcenterService;
import com.xiaodou.userCenter.service.queue.QueueService.PushLoginOutDTO;

@HandlerMessage("LogoutMessage")
public class LogoutMessageWorker extends AbstractDefaultWorker {

    /** serialVersionUID */
    private static final long serialVersionUID = 681700950367469783L;

    @Override
    public void domain(DefaultMessage message, IMQCallBacker<DefaultMessage> callback) {
        PushLoginOutDTO pushLoginOutDTO =
                FastJsonUtil.fromJson(message.getMessageBodyJson(), PushLoginOutDTO.class);
        pushLoginOut(pushLoginOutDTO.getEntity(), pushLoginOutDTO.getRegistrationId(),
                pushLoginOutDTO.getSystemType());
    }

    @Override
    public void domain(List<DefaultMessage> message, IMQCallBacker<List<DefaultMessage>> callback) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onException(Throwable t, List<DefaultMessage> message,
            IMQCallBacker<List<DefaultMessage>> callback) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onException(Throwable t, DefaultMessage message,
            IMQCallBacker<DefaultMessage> callback) {
        // TODO Auto-generated method stub

    }

    public static void pushLoginOut(UserModel entity, String registrationId, String systemType) {
        try {
            // 当同一用户在不同的设备登录，并且在之前设备上此用户没有退出登录时
            PushPar untokencode = PushPar.UNTOKENCODE;
            PushPar untokenmsg = PushPar.UNTOKENMSG;
            com.xiaodou.push.agent.model.Message ms = new com.xiaodou.push.agent.model.Message();
            String loginDate =
                    DateUtil.formatDate(new Timestamp(System.currentTimeMillis()), "HH:mm");
            ms.setMessageContent("您的账号于" + loginDate + "在另一台手机登录。如非本人操作，则密码可能已泄露，建议前往“我的->设置”修改密码");
            new Date(System.currentTimeMillis());
            ms.setNoticeContent("您的账号于" + loginDate + "在另一台手机登录。如非本人操作，则密码可能已泄露，建议前往“我的->设置”修改密码");
            ms.setMessageType(MessageType.ALL);
            // ms.setScope(SpreadRange.REGISTRATION_ID);
            ms.setScope(SpreadRange.TAG_REGISTRATION_ID);
            if ("android".equals(systemType)) ms.setTarget(TargetType.ANDROID);
            if ("ios".equals(systemType)) ms.setTarget(TargetType.IOS);
            Map<String, String> messageextras = new HashMap<String, String>();
            Map<String, String> noticeextras = new HashMap<String, String>();
            ms.addRegister(new String[] {registrationId});
            messageextras.put(untokencode.getCode(), untokencode.getMsg());
            messageextras.put(untokenmsg.getCode(), untokenmsg.getMsg());
            messageextras.put("sessionToken", entity.getToken());// 防止自己顶自己
            noticeextras = messageextras;
            ms.setMessageextras(messageextras);// 消息参数
            ms.setNoticeextras(noticeextras);
            ms.setAppMessageId(UUID.randomUUID().toString());
            ms.setProductLine(PushMessageProLine.LOGIN_OUT.name());
            ms.addGroup(entity.getPackageTag());
            PushClient.push(ms);
            // 调取退出登录接口
            BaseRequest baseRequest = new BaseRequest();
            baseRequest.setSessionToken(entity.getToken());
            baseRequest.setDeviceId(entity.getUsedDeviceId());
            UcenterService ucenterService = SpringWebContextHolder.getBean("ucenterService");
            ucenterService.loginOut(baseRequest);
        } catch (Exception e) {
            LoggerUtil.error("推送顶人消息失败", e);
        }
    }
}
