package com.xiaodou.ms.vo.mq;

import com.xiaodou.dataclean.core.event.DCCoreEvent;
import com.xiaodou.ms.vo.mq.AddProductRelationEvent.TransferProductRelationData;

/**
 * @name DeleteProductRelationEvent
 * @CopyRright (c) 2017 by zhaodan
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年3月28日
 * @description 删除产品关系事件
 * @version 1.0
 */
public class DeleteProductRelationEvent extends DCCoreEvent<TransferProductRelationData> {
  private static final String EVENT_NAME = "deleteProductRelation";

  public DeleteProductRelationEvent() {
    setEventName(EVENT_NAME);
//    setModule(XdmsConstant.MODULE);
  }

}
