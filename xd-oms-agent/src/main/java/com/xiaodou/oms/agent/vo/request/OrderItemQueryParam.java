package com.xiaodou.oms.agent.vo.request;

import java.sql.Timestamp;

/**
 * Created by zyp on 14-7-17.
 */
public class OrderItemQueryParam {
    /**订单条目主键**/
    private String id;

    /**商品id**/
    private Long prctId;

    /**小订单号**/
    private String orderId;

    /**货品id**/
    private Long subPrctId;

    /**商家商品ID**/
    private String merchantPrctId;
    
    /**
     * 商家商品名称
     */
    private String merchantPrctName;
    
    /**商家货品ID**/
    private String subMerchantPrctId;
    
    /**
     * 商家货品name
     */
    private String subMerchantPrctName;

  /**最早修改时间**/
  private Timestamp updateTimeLower;

  /**最迟修改时间**/
  private Timestamp updateTimeUpper;

  /**最早创建时间**/
  private Timestamp createTimeLower;

  /**最迟创建时间**/
  private Timestamp createTimeUpper;
  
  
  /**
   * 商品名称
   */
  private String productName;

  public Timestamp getUpdateTimeLower() {
    return updateTimeLower;
  }

  public void setUpdateTimeLower(Timestamp updateTimeLower) {
    this.updateTimeLower = updateTimeLower;
  }

  public Timestamp getUpdateTimeUpper() {
    return updateTimeUpper;
  }

  public void setUpdateTimeUpper(Timestamp updateTimeUpper) {
    this.updateTimeUpper = updateTimeUpper;
  }

  public Timestamp getCreateTimeLower() {
    return createTimeLower;
  }

  public void setCreateTimeLower(Timestamp createTimeLower) {
    this.createTimeLower = createTimeLower;
  }

  public Timestamp getCreateTimeUpper() {
    return createTimeUpper;
  }

  public void setCreateTimeUpper(Timestamp createTimeUpper) {
    this.createTimeUpper = createTimeUpper;
  }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getPrctId() {
        return prctId;
    }

    public void setPrctId(Long prctId) {
        this.prctId = prctId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Long getSubPrctId() {
        return subPrctId;
    }

    public void setSubPrctId(Long subPrctId) {
        this.subPrctId = subPrctId;
    }

    public String getMerchantPrctId() {
        return merchantPrctId;
    }

    public void setMerchantPrctId(String merchantPrctId) {
        this.merchantPrctId = merchantPrctId;
    }

    public String getSubMerchantPrctId() {
        return subMerchantPrctId;
    }

    public void setSubMerchantPrctId(String subMerchantPrctId) {
        this.subMerchantPrctId = subMerchantPrctId;
    }

    /**
     * getter method for productName
     * @return the productName
     */
    public String getProductName() {
        return productName;
    }

    /**
     * setter method for productName
     * @Description the productName to set
     * @param productName 
     */
    public void setProductName(String productName) {
        this.productName = productName;
    }

    /**
     * getter method for merchantPrctName
     * @return the merchantPrctName
     */
    public String getMerchantPrctName() {
        return merchantPrctName;
    }

    /**
     * setter method for merchantPrctName
     * @Description the merchantPrctName to set
     * @param merchantPrctName 
     */
    public void setMerchantPrctName(String merchantPrctName) {
        this.merchantPrctName = merchantPrctName;
    }

    /**
     * getter method for subMerchantPrctName
     * @return the subMerchantPrctName
     */
    public String getSubMerchantPrctName() {
        return subMerchantPrctName;
    }

    /**
     * setter method for subMerchantPrctName
     * @Description the subMerchantPrctName to set
     * @param subMerchantPrctName 
     */
    public void setSubMerchantPrctName(String subMerchantPrctName) {
        this.subMerchantPrctName = subMerchantPrctName;
    }
    
}
