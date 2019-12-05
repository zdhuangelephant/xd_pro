package com.xiaodou.ms.web.request.product;

import com.xiaodou.ms.web.request.BaseRequest;
import com.xiaodou.summer.validator.annotion.NotEmpty;

/**
 * Created by zyp on 15/4/19.
 */
public class ProductModuleCreateRequest extends BaseRequest {

  // 课程分类名称
  @NotEmpty
  private String name;

  // 课程分类详细介绍
  private String detail;

  /** module APP模块ID */
  private String module;

  public String getModule() {
    return module;
  }

  public void setModule(String module) {
    this.module = module;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDetail() {
    return detail;
  }

  public void setDetail(String detail) {
    this.detail = detail;
  }
}
