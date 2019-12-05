package com.xiaodou.resources.request;

import com.xiaodou.summer.validator.annotion.NotEmpty;
import com.xiaodou.summer.vo.in.BaseValidatorPojo;

/**
 * @name BaseRequest CopyRright (c) 2016 by zhaodan
 * 
 * @author zhaodan
 * @date 2016年9月1日
 * @description 基础请求类
 * @version 1.0
 */
public class BaseRequest extends BaseValidatorPojo {

  /** uid 用户ID */
  @NotEmpty
  private String uid;
  /** module 模块 */
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

  /**
   * 根据module格式化productLine
   * 
   * @param module
   * @return
   */
  public String formatProductLine() {
    String module = this.getModule();
    return module.length() > 2 ? module.substring(module.length() - 2) : module.length() == 1
        ? String.format("0%s", module)
        : module;
  }

}
