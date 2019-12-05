package com.xiaodou.ms.vo.mq;

import com.xiaodou.dataclean.core.event.DCCoreEvent;
import com.xiaodou.ms.vo.mq.AddProductEvent.TransferProductData;

/**
 * @name DeleteProductEvent
 * @CopyRright (c) 2017 by zhaodan
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年3月28日
 * @description 删除产品事件
 * @version 1.0
 */
public class DeleteProductEvent extends DCCoreEvent<TransferProductData> {
  private static final String EVENT_NAME = "deleteProduct";

  public DeleteProductEvent() {
    setEventName(EVENT_NAME);
//    setModule(XdmsConstant.MODULE);
  }

}
