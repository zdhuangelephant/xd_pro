package com.xiaodou.payment.vo.response;

import java.util.List;

import com.xiaodou.payment.vo.response.inner.MixPaymentTypeInfo;

/**
 * {
 * "rep_code": "0",
 * "error_info": "",
 * "trans_type": 3002,
 * "operation_id": 2005,
 * "result_status": -1,
 * "payment_status_desc": "开始扣除您信用卡冻结款项",
 * "payment_type_info_list": [
 * {
 * "amt": 11.11,
 * "record_id": 31524,
 * "trans_type": 3002,
 * "trans_type_desc": "信用卡",
 * "result_status": -1,
 * "result_status_desc": "正在处理"
 * }
 * ]
 * }
 * Date: 2014/7/18
 * Time: 14:52
 *
 * @author Tian.Dong
 */
public class PayTypeResponse extends ResponseBase{

  private int transType;

  private int operationId;

  private int resultStatus;

  private String paymentStatusDesc;

  private List<MixPaymentTypeInfo> mixPaymentTypeInfoList;

  public int getTransType() {
    return transType;
  }

  public void setTransType(int transType) {
    this.transType = transType;
  }

  public int getOperationId() {
    return operationId;
  }

  public void setOperationId(int operationId) {
    this.operationId = operationId;
  }

  public int getResultStatus() {
    return resultStatus;
  }

  public void setResultStatus(int resultStatus) {
    this.resultStatus = resultStatus;
  }

  public String getPaymentStatusDesc() {
    return paymentStatusDesc;
  }

  public void setPaymentStatusDesc(String paymentStatusDesc) {
    this.paymentStatusDesc = paymentStatusDesc;
  }

  public List<MixPaymentTypeInfo> getMixPaymentTypeInfoList() {
    return mixPaymentTypeInfoList;
  }

  public void setMixPaymentTypeInfoList(List<MixPaymentTypeInfo> mixPaymentTypeInfoList) {
    this.mixPaymentTypeInfoList = mixPaymentTypeInfoList;
  }
}
