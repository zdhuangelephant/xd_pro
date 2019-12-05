package com.xiaodou.ms.web.request.admin;

import com.xiaodou.ms.web.request.BaseRequest;
import org.springframework.validation.Errors;

/**
 * Created by zyp on 14-9-26.
 */
public class ShopConfigRequest extends BaseRequest {

  /**
   * 订单金额显示位数
   */
  private String moneyDecimal;

  /**
   * 订单金额操作方式
   */
  private String moneyOperation;

  public String getMoneyDecimal() {
    return moneyDecimal;
  }

  public void setMoneyDecimal(String moneyDecimal) {
    this.moneyDecimal = moneyDecimal;
  }

  public String getMoneyOperation() {
    return moneyOperation;
  }

  public void setMoneyOperation(String moneyOperation) {
    this.moneyOperation = moneyOperation;
  }

  @Override
  public void validate(Object o, Errors errors) {
    //    super.validate(o, errors);
  }
}
