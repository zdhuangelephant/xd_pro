package com.xiaodou.ms.vo.mq;

import lombok.Data;

import com.xiaodou.dataclean.core.event.DCCoreEvent;
import com.xiaodou.ms.model.product.ProductCategoryRelation;
import com.xiaodou.ms.vo.mq.AddProductRelationEvent.TransferProductRelationData;

/**
 * @name AddProductRelationEvent
 * @CopyRright (c) 2017 by zhaodan
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年3月28日
 * @description 添加产品关系事件
 * @version 1.0
 */
public class AddProductRelationEvent extends DCCoreEvent<TransferProductRelationData> {
  private static final String EVENT_NAME = "addProductRelation";

  public AddProductRelationEvent() {
    setEventName(EVENT_NAME);
    // setModule(XdmsConstant.MODULE);
  }

  /**
   * @name @see com.xiaodou.vo.mq.TransferProductData.java
   * @CopyRright (c) 2017 by Corp.XiaodouTech
   * 
   * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
   * @date 2017年3月31日
   * @description 传输参数类
   * @version 1.0
   */
  @Data
  public static class TransferProductRelationData {
    public TransferProductRelationData(ProductCategoryRelation model, String module) {
      this.id = model.getId();
      this.module = module;
      this.productCategoryId = model.getProductCategoryId();
      this.productId = model.getProductId();
      this.relationState = model.getRelationState();
    }

    public TransferProductRelationData(Long id, String module) {
      this.id = id;
      this.module = module;
    }

    /** id 主键ID */
    private Long id;
    /** module 地域码 */
    private String module;
    /** productCategoryId 产品分类ID */
    private Long productCategoryId;
    /** productId 产品ID */
    private Long productId;
    /** relationState 关系状态 */
    private Integer relationState;
  }
}
