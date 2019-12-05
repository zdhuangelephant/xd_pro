package com.xiaodou.resources.request.quesbk;

import com.xiaodou.resources.request.BaseRequest;
import com.xiaodou.summer.validator.annotion.NotEmpty;

/**
 * @name ExamDetailPojo 
 * CopyRright (c) 2016 by zhaodan
 *
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2016年10月27日
 * @description 出题接口参数Pojo
 * @version 1.0
 */
public class ExamDetailPojo extends BaseRequest {
  /**
   * 课程ID
   */
  @NotEmpty
  private String productId;
  /** productItemId 产品itemID */
  @NotEmpty
  private String productItemId;
  /** recordId 测验记录ID */
  private String recordId;
  /** lastQuesId 分页用问题ID */
  private String lastQuesId;
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
  public String getRecordId() {
    return recordId;
  }
  public void setRecordId(String recordId) {
    this.recordId = recordId;
  }
  public String getLastQuesId() {
    return lastQuesId;
  }
  public void setLastQuesId(String lastQuesId) {
    this.lastQuesId = lastQuesId;
  }
}