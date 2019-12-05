package com.xiaodou.oms.vo.input.sign;

import org.springframework.validation.Errors;

import com.xiaodou.common.util.StringUtils;
import com.xiaodou.oms.dao.OperationType;
import com.xiaodou.oms.dao.OperationTypeWrapper;
import com.xiaodou.summer.validator.annotion.NotEmpty;
import com.xiaodou.summer.vo.in.BaseSignPojo;

/**
 * <p>
 * OMS-Md5验证Pojo基类-基于ProductLine获取key与商户号
 * </p>
 * 
 * @author <a href="mailto:dan.zhao@elong.corp.com">zhaodan</a>
 * @version 1.0
 * @date 2014年7月7日
 */
public class BasePojo extends BaseSignPojo {

  /**
   * 产品线
   */
  @NotEmpty
  protected String productLine;

  protected String operationType;

  public String getOperationType() {
    return operationType;
  }

  public void setOperationType(String operationType) {
    this.operationType = operationType;
  }

  @Override
  public Errors validate() {
    if (StringUtils.isBlank(operationType)) {
      operationType = OperationType.DEFAULT.getValue();
    }
    OperationTypeWrapper.getWrapper().setValue(OperationType.getEnumMap().get(operationType));
    return super.validate();
  }

  public String getProductLine() {
    return productLine;
  }

  public void setProductLine(String productLine) {
    this.productLine = productLine;
  }

  @Override
  public String getKey() {
    return SignMessConf.getKey(productLine);
  }
}
