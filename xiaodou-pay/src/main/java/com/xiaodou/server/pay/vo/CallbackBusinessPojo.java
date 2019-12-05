package com.xiaodou.server.pay.vo;

import com.xiaodou.server.pay.model.PayRecord;
import com.xiaodou.server.pay.web.sign.SignMessConf;
import com.xiaodou.wallet.agent.util.SignUtil;

public class CallbackBusinessPojo {

  /** notifyUrl 回调业务系统地址 */
  private String notifyUrl;
  /** param 回调业务系统参数 */
  private CallbackBusinessParam param;

  public CallbackBusinessPojo() {}

  public CallbackBusinessPojo(PayRecord record) {
    this.notifyUrl = record.getBusinessCallbackUrl();
    this.param = new CallbackBusinessParam();
    param.setTradeNo(record.getTradeNo());
    param.setMerchantId(record.getMerchantId());
    param.setResultStatus(record.getPayStatus());
    param.setErrorInfo(record.getPayResult());
    param.setSign(SignUtil.sign(param, SignMessConf.getKey(record.getMerchantId())));
  }

  public String getNotifyUrl() {
    return notifyUrl;
  }


  public void setNotifyUrl(String notifyUrl) {
    this.notifyUrl = notifyUrl;
  }


  public CallbackBusinessParam getParam() {
    return param;
  }


  public void setParam(CallbackBusinessParam param) {
    this.param = param;
  }


  public static class CallbackBusinessParam {

    /** merchantId 商户号 */
    private String merchantId;
    /** signType 签名方式 */
    private String signType = "MD5";
    /** sign 签名 */
    private String sign;
    /** tradeNo 交易号 */
    private String tradeNo;
    /** payTime 支付时间 */
    private String payTime;
    /** resultStatus 支付状态 */
    private int resultStatus;
    /** errorInfo 支付结果详情 */
    private String errorInfo;

    public String getMerchantId() {
      return merchantId;
    }

    public void setMerchantId(String merchantId) {
      this.merchantId = merchantId;
    }

    public String getSignType() {
      return signType;
    }

    public void setSignType(String signType) {
      this.signType = signType;
    }

    public String getSign() {
      return sign;
    }

    public void setSign(String sign) {
      this.sign = sign;
    }

    public String getTradeNo() {
      return tradeNo;
    }

    public void setTradeNo(String tradeNo) {
      this.tradeNo = tradeNo;
    }

    public String getPayTime() {
      return payTime;
    }

    public void setPayTime(String payTime) {
      this.payTime = payTime;
    }

    public int getResultStatus() {
      return resultStatus;
    }

    public void setResultStatus(int resultStatus) {
      this.resultStatus = resultStatus;
    }

    public String getErrorInfo() {
      return errorInfo;
    }

    public void setErrorInfo(String errorInfo) {
      this.errorInfo = errorInfo;
    }

  }
}
