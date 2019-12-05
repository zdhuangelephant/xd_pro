package com.xiaodou.ms.vo.mq;


import com.xiaodou.dataclean.core.event.DCCoreEvent;
import com.xiaodou.ms.vo.mq.AddScoreRuleEvent.TransferScoreRuleData;


/**
 * @name ModifyScoreRuleEvent 
 * CopyRright (c) 2018 by <a href=mailto:zhuweijin@corp.51xiaodou.com>zhuweijin</a>
 *
 * @author <a href=mailto:zhuweijin@corp.51xiaodou.com>zhuweijin</a>
 * @date 2018年4月26日
 * @description 修改计分规则
 * @version 1.0
 */
public class ModifyScoreRuleEvent extends DCCoreEvent<TransferScoreRuleData> {
  private static final String EVENT_NAME = "modifyScoreRule";

  public ModifyScoreRuleEvent() {
    setEventName(EVENT_NAME);
//    setModule(XdmsConstant.MODULE);
  }

}
