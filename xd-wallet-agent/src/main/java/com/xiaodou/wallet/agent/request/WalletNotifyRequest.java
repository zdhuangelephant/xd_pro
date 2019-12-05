package com.xiaodou.wallet.agent.request;

import com.xiaodou.summer.vo.in.BaseValidatorPojo;

public class WalletNotifyRequest extends BaseValidatorPojo {

  /** resourceId 外部资源ID */
  private String resourceId;
  /** operationCode 通知操作码 */
  private Integer operationCode;
  /** operationDesc 通知操作描述 */
  private String operationDesc;

  public String getResourceId() {
    return resourceId;
  }

  public void setResourceId(String resourceId) {
    this.resourceId = resourceId;
  }

  public Integer getOperationCode() {
    return operationCode;
  }

  public void setOperationCode(Integer operationCode) {
    this.operationCode = operationCode;
  }

  public String getOperationDesc() {
    return operationDesc;
  }

  public void setOperationDesc(String operationDesc) {
    this.operationDesc = operationDesc;
  }

}
