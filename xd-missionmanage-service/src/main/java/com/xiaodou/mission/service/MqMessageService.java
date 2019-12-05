package com.xiaodou.mission.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.xiaodou.common.cache.redis.JedisUtil;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.jmsg.entity.JMSGPojo;
import com.xiaodou.jmsg.entity.MessageRemoteResult;
import com.xiaodou.jmsg.entity.MessageRemoteResult.MessageRemoteResultType;
import com.xiaodou.mission.vo.request.EventRequest;
import com.xiaodou.mission.vo.request.ReceiveAward;

/**
 * @name @see com.xiaodou.mission.service.MqMessageService.java
 * @CopyRright (c) 2017 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年12月26日
 * @description MQ消息处理Service
 * @version 1.0
 */
@Service("mqMessageService")
public class MqMessageService {

  @Resource
  EventProcessService eventProcessService;

  /**
   * 处理事件方法
   */
  public MessageRemoteResult onEvent(JMSGPojo pojo) throws Exception {
    EventRequest request = FastJsonUtil.fromJson(pojo.getMessage(), EventRequest.class);
    String requestJson = JedisUtil.getStringFromJedis(request.getEventTag());
    if (StringUtils.isNotBlank(requestJson)) {
      return new MessageRemoteResult(MessageRemoteResultType.SUCCESS);
    }
    JedisUtil.addStringToJedis(request.getEventTag(), request.getEventTag(), 300);
    return eventProcessService.onEvent(request);
  }

  /**
   * 刷新 -> 接受任务奖励状态
   */
  public MessageRemoteResult receiveTaskAward(JMSGPojo pojo) {
    ReceiveAward request = FastJsonUtil.fromJson(pojo.getMessage(), ReceiveAward.class);
    return eventProcessService.receiveTaskAward(request);
  }

}
