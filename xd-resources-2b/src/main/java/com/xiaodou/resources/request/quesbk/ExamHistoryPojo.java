package com.xiaodou.resources.request.quesbk;

import com.xiaodou.resources.request.BaseRequest;
import com.xiaodou.summer.validator.annotion.NotEmpty;

/**
 * @name ExamHistoryPojo 
 * CopyRright (c) 2016 by zhaodan
 *
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2016年10月31日
 * @description 练习历史列表
 * @version 1.0
 */
public class ExamHistoryPojo extends BaseRequest {
  /**
   * 课程ID
   */
  @NotEmpty
  private String productId;
  /** productItemId 产品itemID */
  @NotEmpty
  private String productItemId;
  public String getProductId() {
    return productId;
  }
  public void setProductId(String productId) {
    this.productId = productId;
  }
  public String getProductItemId() {
    return productItemId;
  }
  public void setProductItemId(String productItemId) {
    this.productItemId = productItemId;
  }
}