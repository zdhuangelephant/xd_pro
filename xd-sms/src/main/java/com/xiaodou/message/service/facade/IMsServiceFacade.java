package com.xiaodou.message.service.facade;

import java.util.Map;

import com.xiaodou.message.model.MessageModel;
import com.xiaodou.message.vo.MessagePushInfo;

public interface IMsServiceFacade {
  /**
   * 添加推送任务
   * 
   * @param messageInfoModel
   * @param messageModel
   * @return
   */
  public MessagePushInfo addMessageModelEntity(MessagePushInfo messageInfoModel,MessageModel messageModel) ;
    
  /**
   * 更新推送任务状态
   * 
   * @param condition
   * @param value
   */
  public void updateMessageModelEntity(Map<String, Object> condition, MessageModel value);
}
