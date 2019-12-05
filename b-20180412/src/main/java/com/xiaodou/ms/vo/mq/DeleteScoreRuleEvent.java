package com.xiaodou.ms.vo.mq;


import com.xiaodou.dataclean.core.event.DCCoreEvent;
import com.xiaodou.ms.vo.mq.AddScoreRuleEvent.TransferScoreRuleData;


/**
 * @name DeleteProductRelationEvent 
 * CopyRright (c) 2018 by <a href=mailto:zhuweijin@corp.51xiaodou.com>zhuweijin</a>
 *
 * @author <a href=mailto:zhuweijin@corp.51xiaodou.com>zhuweijin</a>
 * @date 2018年4月26日
 * @description 删除计分规则事件
 * @version 1.0
 */
public class DeleteScoreRuleEvent extends DCCoreEvent<TransferScoreRuleData> {
  private static final String EVENT_NAME = "deleteScoreRule";

  public DeleteScoreRuleEvent() {
    setEventName(EVENT_NAME);
//    setModule(XdmsConstant.MODULE);
  }
  

}
