package com.xiaodou.logmonitor.model;

/**
 * @name @see com.xiaodou.model.ActionModel.java
 * @CopyRright (c) 2017 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年5月24日
 * @description 日志行为模型
 * @version 1.0
 * @param <T>
 */
public class ActionModel<T> {
  /** actionInfo 行为信息 */
  private T actionInfo;
  /** hasError 是否有异常 */
  private Boolean hasError;
  /** errorMessage 异常信息 */
  private String errorMessage;

  public T getActionInfo() {
    return actionInfo;
  }

  public void setActionInfo(T actionInfo) {
    this.actionInfo = actionInfo;
  }

  public Boolean getHasError() {
    return hasError;
  }

  public void setHasError(Boolean hasError) {
    this.hasError = hasError;
  }

  public String getErrorMessage() {
    return errorMessage;
  }

  public void setErrorMessage(String errorMessage) {
    this.errorMessage = errorMessage;
  }
}
