package com.xiaodou.resources.request.forum;

import com.xiaodou.resources.request.BaseRequest;
import com.xiaodou.summer.validator.annotion.NotEmpty;

/**
 * 回复帖子request
 * 
 * @author zhouhuan
 * 
 */
public class ForumReplyPostRequest extends BaseRequest {

  @NotEmpty
  private Long resourcesId;

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

public Long getResourcesId() {
	return resourcesId;
}

public void setResourcesId(Long resourcesId) {
	this.resourcesId = resourcesId;
}

}
