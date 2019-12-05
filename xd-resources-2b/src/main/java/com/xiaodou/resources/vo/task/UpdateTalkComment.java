package com.xiaodou.resources.vo.task;

/**
 * @name UpdateTalkComment 
 * CopyRright (c) 2016 by zhaodan
 *
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2016年11月10日
 * @description 更新讨论回复数
 * @version 1.0
 */
public class UpdateTalkComment {
  /** userId 用户ID */
  private String userId;
  /** productId 产品ID */
  private String productId;
  /** itemId 节点ID */
  private String itemId;
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
  public String getItemId() {
    return itemId;
  }
  public void setItemId(String itemId) {
    this.itemId = itemId;
  }

}
