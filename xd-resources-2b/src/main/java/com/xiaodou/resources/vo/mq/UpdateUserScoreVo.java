package com.xiaodou.resources.vo.mq;

/**
 * @name UpdateUserScoreVo 
 * CopyRright (c) 2016 by zhaodan
 *
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2016年11月3日
 * @description 更新用户成绩参数类
 * @version 1.0
 */
public class UpdateUserScoreVo {
  /** userId 用户ID */
  private String userId;
  /** productId 产品ID */
  private String productId;
  public String getUserId() {
    return userId;
  }
  public void setUserId(String userId) {
    this.userId = userId;
  }
  public String getProductId() {
    return productId;
  }
  public void setProductId(String productId) {
    this.productId = productId;
  }
}