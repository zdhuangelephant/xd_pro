package com.xiaodou.oms.vo.input.order;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import com.xiaodou.oms.entity.order.Order;
import com.xiaodou.oms.vo.input.sign.BasePojo;
import com.xiaodou.summer.validator.annotion.NotEmpty;

/**
 * <p>修改合作商信息参数Pojo</p>
 *
 * @author <a href="mailto:dan.zhao@elong.corp.com">zhaodan</a>
 * @version 1.0
 * @date 2014年9月1日
 */
public class UpdateMerchantPojo extends BasePojo {
  
  /**
   * 订单号
   */
  @NotEmpty
  private String orderId;
  
  /**
   * 下单账户
   */
  private String buyAccountId;
  
  /**
   * 合作商ID
   */
  @NotEmpty
  private Integer merchantId;
  
  /**
   * 合作商名称
   */
  @NotEmpty
  private String merchantName;
  
  /**
   * 合作商电话
   */
  private String merchantTel;
  
  /**
   * 客户端IP
   */
  @NotEmpty
  private String clientIp;

  public String getClientIp() {
    return clientIp;
  }

  public void setClientIp(String clientIp) {
    this.clientIp = clientIp;
  }

  public String getBuyAccountId() {
    return buyAccountId;
  }

  public void setBuyAccountId(String buyAccountId) {
    this.buyAccountId = buyAccountId;
  }

  public String getOrderId() {
    return orderId;
  }

  public void setOrderId(String orderId) {
    this.orderId = orderId;
  }

  public Integer getMerchantId() {
    return merchantId;
  }

  public void setMerchantId(Integer merchantId) {
    this.merchantId = merchantId;
  }

  public String getMerchantName() {
    try {
      return URLDecoder.decode(merchantName,"utf8");
    } catch (UnsupportedEncodingException e) {
      return merchantName;
    }
  }

  public void setMerchantName(String merchantName) {
    this.merchantName = merchantName;
  }

  public String getMerchantTel() {
    return merchantTel;
  }

  public void setMerchantTel(String merchantTel) {
    this.merchantTel = merchantTel;
  }
  
  public Order getUpdateOrder(){
    Order order = new Order();
    order.setId(orderId);
    order.setMerchantId(merchantId);
    order.setMerchantName(getMerchantName());
    order.setMerchantTel(merchantTel);
    return order;
  }

}
