package com.xiaodou.ms.vo.mq;

import com.xiaodou.dataclean.core.event.DCCoreEvent;
import com.xiaodou.ms.vo.mq.AddProductCategoryEvent.TransferProductCategoryData;

/**
 * @name ModifyProductCategoryEvent
 * @CopyRright (c) 2017 by zhaodan
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年3月28日
 * @description 修改产品专业事件
 * @version 1.0
 */
public class ModifyProductCategoryEvent extends DCCoreEvent<TransferProductCategoryData> {
  private static final String EVENT_NAME = "modifyProductCategory";

  public ModifyProductCategoryEvent() {
    setEventName(EVENT_NAME);
//    setModule(XdmsConstant.MODULE);
  }

}