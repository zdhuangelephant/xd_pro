package com.xiaodou.message.service.message;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.message.dao.MessageModelDao;
import com.xiaodou.message.model.MessageModel;
import com.xiaodou.message.service.facade.IMsServiceFacade;
import com.xiaodou.message.vo.MessagePushInfo;
import com.xiaodou.message.web.response.MessageResponse;
import com.xiaodou.push.agent.enums.MessageType;
import com.xiaodou.push.agent.enums.SpreadRange;
import com.xiaodou.push.agent.enums.TargetType;
import com.xiaodou.push.agent.model.Message;
import com.xiaodou.sms.common.enums.ResultType;

@Service
public class MessageProduceService {

    @Resource
    IMsServiceFacade msServiceFacade;
    @Resource
    MessageModelDao messageMoselDao;

    public MessageResponse produce(Message messageRequest) {
        try {
            MessageResponse response = new MessageResponse(ResultType.SUCCESS);
            if (null == messageRequest.getTarget()) {
                response.setStatus(1);
                response.setStatusDesc("生成消息失败：参数为空");
                return response;
            }
            // UUID uuid = messageRequest.getId();//
            List<String> tags = messageRequest.getGroupList();// 分组列表(tag)
            List<String> alias = messageRequest.getRecieverList();// 接收者列表(alias)
            List<String> registrationIds = messageRequest.getRegisterList();// 注册列表(registrationId)
            MessageType messageType = messageRequest.getMessageType();// 消息类型
            SpreadRange spreadRange = messageRequest.getScope();
            TargetType targetType = messageRequest.getTarget();// 目标设备类型
            String messageContent = messageRequest.getMessageContent();// message - notice
            String noticeContent = messageRequest.getNoticeContent();
            Map<String, String> messageextras = messageRequest.getMessageextras();
            Map<String, String> noticeextras = messageRequest.getNoticeextras();
            // 生成发送对象
            MessagePushInfo messagePushModel = new MessagePushInfo();
            messagePushModel.setTargetType(targetType.getTypeCode());
            messagePushModel.setMessageType(messageType.getMessageType());// 2
            messagePushModel.setSpreadRange(spreadRange.getSpreadRange());// 1
            messagePushModel.setMessageContent(messageContent);
            messagePushModel.setNoticeContent(noticeContent);
            messagePushModel.setAlias(alias);
            messagePushModel.setTags(tags);
            messagePushModel.setRegistrationIds(registrationIds);
            messagePushModel.setMessageextras(messageextras);
            messagePushModel.setNoticeextras(noticeextras);
            // 生成入库对象
            MessageModel messageModel = new MessageModel();
            // 声明json对象
            JSONObject messageJson = new JSONObject();
            messageJson.put("targetType", targetType.getTypeCode());
            messageJson.put("messageType", messageType.getMessageType());
            messageJson.put("spreadRange", spreadRange.getSpreadRange());
            messageJson.put("messageContent", messageContent);
            messageJson.put("noticeContent", noticeContent);
            messageJson.put("alias", alias);
            messageJson.put("tags", tags);
            messageJson.put("registrationIds", registrationIds);
            messageJson.put("messageextras", messageextras);
            messageJson.put("noticeextras", noticeextras);
            // 把json数据加到json数组中
            messageModel.setMessage(messageJson.toJSONString());
            messageModel.setMsg("准备发送");
            messageModel.setStatus("0");// 准备发送
            messageModel.setCreateTime(new Timestamp(System.currentTimeMillis()));

            // 1、设置应用端类型；2、设置消息业务ID(UUID)
            // messageModel.setAppMessageId(uuid.toString());
            messageModel.setAppMessageId(messageRequest.getAppMessageId());
            messageModel.setProductLine(messageRequest.getProductLine());

            msServiceFacade.addMessageModelEntity(messagePushModel, messageModel);
            response.setStatus(0);
            response.setStatusDesc("生成消息成功");
            return response;
        } catch (NumberFormatException e) {
            LoggerUtil.error("service层：消息生成异常", e);
            throw e;
        }
    }

}
