package com.xiaodou.course.web.request.share;

import com.xiaodou.share.request.ShareRequest;
import com.xiaodou.summer.validator.annotion.NotEmpty;

/**
 * @name ResourceShareRequest CopyRright (c) 2016 by zhaodan
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2016年11月25日
 * @description 资源分享请求类
 * @version 1.0
 */
public class ResourceShareRequest extends ShareRequest {

  /** uid 用户ID */
  @NotEmpty
  private String uid;
  /** module 模块 */
  @NotEmpty
  private String module;
  /** resourceId 资源ID */
  @NotEmpty
  private String resourceId;

  public String getUid() {
    return uid;
  }

  public void setUid(String uid) {
    this.uid = uid;
  }

  public String getModule() {
    return module;
  }

  public void setModule(String module) {
    this.module = module;
  }

  public String getResourceId() {
    return resourceId;
  }

  public void setResourceId(String resourceId) {
    this.resourceId = resourceId;
  }

}