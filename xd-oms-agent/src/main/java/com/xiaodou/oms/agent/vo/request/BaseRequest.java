package com.xiaodou.oms.agent.vo.request;

/**
 * Created by zyp on 14-7-16.
 */
public class BaseRequest extends OmsMd5 {

  /** 业务线 */
  protected String productLine;

  /**
   * getter method for productLine
   * 
   * @return the productLine
   */
  public String getProductLine() {
    return productLine;
  }

  /**
   * setter method for productLine
   * 
   * @Description the productLine to set
   * @param productLine
   */
  public void setProductLine(String productLine) {
    this.productLine = productLine;
  }

  protected String operationType;

  public String getOperationType() {
    return operationType;
  }

  public void setOperationType(String operationType) {
    this.operationType = operationType;
  }
}
