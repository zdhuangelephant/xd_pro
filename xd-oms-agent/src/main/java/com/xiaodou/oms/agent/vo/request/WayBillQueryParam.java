package com.xiaodou.oms.agent.vo.request;

import java.sql.Timestamp;
import java.util.List;

/**
 * WayBillQueryParam
  * @Title: WayBillQueryParam
  * @Desription 发票查询参数
  * @author Guanguo.Gao
  * @date 2014年12月2日 下午4:48:14
 */
public class WayBillQueryParam {

    /**
     * 产品线 
     */
    private String productLine;
    
    /**
     * 业务类型/产品类型 *
     */
    private String productType;
    
    /**
     * 业务类型/产品类型 列表
     */
    private List<String> productTypeList;
    
    /**
     * 发票订单id
     */
    private String id;
    
    /**
     * 订单id列表
     */
    private List<String> listIds;
    
    /*
     * 所属大订单
     */
    private String gorderId;
    
    /**
     * 最早创建时间
     */
    private Timestamp orderTimeLower;
    
    /**
     * 最迟创建时间
     */
    private Timestamp orderTimeUpper;
    
    /**
     * 用户账户
     */
    private String buyAccountId;
    
    /**
     * 订单状态
     */
    private Integer orderStatus;
    
    /**
     * 运单号
     */
    private String waybill_number;

    /**
     * getter method for productLine
     * @return the productLine
     */
    public String getProductLine() {
        return productLine;
    }

    /**
     * setter method for productLine
     * @Description the productLine to set
     * @param productLine 
     */
    public void setProductLine(String productLine) {
        this.productLine = productLine;
    }

    /**
     * getter method for productType
     * @return the productType
     */
    public String getProductType() {
        return productType;
    }

    /**
     * setter method for productType
     * @Description the productType to set
     * @param productType 
     */
    public void setProductType(String productType) {
        this.productType = productType;
    }

    /**
     * getter method for productTypeList
     * @return the productTypeList
     */
    public List<String> getProductTypeList() {
        return productTypeList;
    }

    /**
     * setter method for productTypeList
     * @Description the productTypeList to set
     * @param productTypeList 
     */
    public void setProductTypeList(List<String> productTypeList) {
        this.productTypeList = productTypeList;
    }

    /**
     * getter method for id
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * setter method for id
     * @Description the id to set
     * @param id 
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * getter method for listIds
     * @return the listIds
     */
    public List<String> getListIds() {
        return listIds;
    }

    /**
     * setter method for listIds
     * @Description the listIds to set
     * @param listIds 
     */
    public void setListIds(List<String> listIds) {
        this.listIds = listIds;
    }

    /**
     * getter method for gorderId
     * @return the gorderId
     */
    public String getGorderId() {
        return gorderId;
    }

    /**
     * setter method for gorderId
     * @Description the gorderId to set
     * @param gorderId 
     */
    public void setGorderId(String gorderId) {
        this.gorderId = gorderId;
    }

    /**
     * getter method for orderTimeLower
     * @return the orderTimeLower
     */
    public Timestamp getOrderTimeLower() {
        return orderTimeLower;
    }

    /**
     * setter method for orderTimeLower
     * @Description the orderTimeLower to set
     * @param orderTimeLower 
     */
    public void setOrderTimeLower(Timestamp orderTimeLower) {
        this.orderTimeLower = orderTimeLower;
    }

    /**
     * getter method for orderTimeUpper
     * @return the orderTimeUpper
     */
    public Timestamp getOrderTimeUpper() {
        return orderTimeUpper;
    }

    /**
     * setter method for orderTimeUpper
     * @Description the orderTimeUpper to set
     * @param orderTimeUpper 
     */
    public void setOrderTimeUpper(Timestamp orderTimeUpper) {
        this.orderTimeUpper = orderTimeUpper;
    }

    /**
     * getter method for buyAccountId
     * @return the buyAccountId
     */
    public String getBuyAccountId() {
        return buyAccountId;
    }

    /**
     * setter method for buyAccountId
     * @Description the buyAccountId to set
     * @param buyAccountId 
     */
    public void setBuyAccountId(String buyAccountId) {
        this.buyAccountId = buyAccountId;
    }

    /**
     * getter method for orderStatus
     * @return the orderStatus
     */
    public Integer getOrderStatus() {
        return orderStatus;
    }

    /**
     * setter method for orderStatus
     * @Description the orderStatus to set
     * @param orderStatus 
     */
    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }

    /**
     * getter method for waybill_number
     * @return the waybill_number
     */
    public String getWaybill_number() {
        return waybill_number;
    }

    /**
     * setter method for waybill_number
     * @Description the waybill_number to set
     * @param waybill_number 
     */
    public void setWaybill_number(String waybill_number) {
        this.waybill_number = waybill_number;
    }
    
}
