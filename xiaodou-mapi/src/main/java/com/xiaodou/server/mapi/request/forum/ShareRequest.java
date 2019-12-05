package com.xiaodou.server.mapi.request.forum;

import com.xiaodou.server.mapi.request.MapiBaseRequest;
import com.xiaodou.summer.validator.annotion.NotEmpty;

/**
 * @name ShareRequest CopyRright (c) 2016 by zhaodan
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2016年11月24日
 * @description 分享请求pojo
 * @version 1.0
 */
public class ShareRequest extends MapiBaseRequest {
  /** shareType 分享类型 */
  @NotEmpty
  private String shareType;
  /** resourceId 资源ID */
  @NotEmpty
  private String resourceId;

  public String getShareType() {
    return shareType;
  }

  public void setShareType(String shareType) {
    this.shareType = shareType;
  }

  public String getResourceId() {
    return resourceId;
  }

  public void setResourceId(String resourceId) {
    this.resourceId = resourceId;
  }

}
