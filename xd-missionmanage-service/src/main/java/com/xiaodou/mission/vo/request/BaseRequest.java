package com.xiaodou.mission.vo.request;

import com.xiaodou.summer.validator.annotion.NotEmpty;
import com.xiaodou.summer.vo.in.BaseValidatorPojo;

/**
 * @name @see com.xiaodou.mission.vo.request.BaseRequest.java
 * @CopyRright (c) 2017 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年12月26日
 * @description 基础请求类
 * @version 1.0
 */
public class BaseRequest extends BaseValidatorPojo {
  /** uid 用户ID */
  @NotEmpty
  private String uid;
  /** module 所属模块 */
  @NotEmpty
  private String module;

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
}
