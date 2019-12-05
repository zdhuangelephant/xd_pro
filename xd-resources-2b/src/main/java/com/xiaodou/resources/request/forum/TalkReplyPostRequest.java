package com.xiaodou.resources.request.forum;

import com.xiaodou.resources.request.BaseRequest;
import com.xiaodou.summer.validator.annotion.NotEmpty;

/**
 * 回复帖子request
 * 
 * @author zhouhuan
 * 
 */
public class TalkReplyPostRequest extends BaseRequest {
  /** 讨论ID */
  @NotEmpty
  private Long itemId;

  /** 产品ID */
  @NotEmpty
  private Long productId;

  /** 评论内容 */
  @NotEmpty
  private String content;

  /** clientIp 设备IP */
  @NotEmpty
  private String clientIp;

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }


  public String getClientIp() {
    return clientIp;
  }

  public void setClientIp(String clientIp) {
    this.clientIp = clientIp;
  }

  public Long getItemId() {
    return itemId;
  }

  public void setItemId(Long itemId) {
    this.itemId = itemId;
  }

  public Long getProductId() {
    return productId;
  }

  public void setProductId(Long productId) {
    this.productId = productId;
  }



}
