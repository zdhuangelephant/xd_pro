package com.xiaodou.message.service.facade;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.xiaodou.message.dao.MessageModelDao;
import com.xiaodou.message.model.MessageModel;
import com.xiaodou.message.service.sender.MsSenderService;
import com.xiaodou.message.vo.MessagePushInfo;
@Service("msServiceFacade")
public class MsServiceFacadeImpl implements IMsServiceFacade{
  @Resource
  MsSenderService MsSenderService;
  @Resource
  MessageModelDao messageModelDao;
  @Override
  public MessagePushInfo addMessageModelEntity(MessagePushInfo messageInfoModel,MessageModel messageModel) {
    //存入数据库
    messageModelDao.addTaskEntity(messageModel);
    messageInfoModel.setMessagedbid(messageModel.getId());
    //添加消息队列
    MsSenderService.addPushMessage(messageInfoModel);
    return messageInfoModel;
  }
  @Override
  public void updateMessageModelEntity(Map<String, Object> condition, MessageModel value) {
    messageModelDao.updateEntity(condition, value);
  }
}
