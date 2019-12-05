package com.xiaodou.oms.vo.input.order;

import java.util.Map;

import com.xiaodou.oms.vo.input.sign.BasePojo;
import com.xiaodou.summer.validator.annotion.NotEmpty;

/**
 * <p>获取Gorder详情参数Pojo</p>
 *
 * @author <a href="mailto:dan.zhao@elong.corp.com">zhaodan</a>
 * @version 1.0
 * @date 2014年6月27日
 */
public class GorderDetailPojo extends BasePojo {
  
  /**
   * Gorder订单号
   */
  @NotEmpty
  private String gorderId;
  
  
  /**
   * 下单账户
   */
  private String buyAccountId;
  
  /**
   * 查询返回属性
   */
  @NotEmpty
  private Map<String, Object> outputProperties;

  public String getBuyAccountId() {
    return buyAccountId;
  }

  public void setBuyAccountId(String buyAccountId) {
    this.buyAccountId = buyAccountId;
  }

  public String getGorderId() {
    return gorderId;
  }

  public void setGorderId(String gorderId) {
    this.gorderId = gorderId;
  }

  public Map<String, Object> getOutputProperties() {
    return outputProperties;
  }

  public void setOutputProperties(Map<String, Object> outputProperties) {
    this.outputProperties = outputProperties;
  }

}
