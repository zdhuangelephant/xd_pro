package com.xiaodou.oms.agent.common.enums;

/**
 * WayBillProperty
  * @Title: WayBillProperty
  * @Desription 发票property
  * @author Guanguo.Gao
  * @date 2014年12月2日 上午11:42:05
 */
public enum WayBillProperty {
    
    id("id","1"),
    gorder_id("gorder_id", "1"),
    buy_account_id("buy_account_id", "1"),
    amount("amount", "1"),
    title("title", "1"),
    content("content", "1"),
    address("address", "1"),
    postcode("postcode", "1"),
    receiver_name("receiver_name", "1"),
    receiver_phone("receiver_phone", "1"),
    invoice_status("invoice_status", "1"),
    invoice_time("invoice_time", "1"),
    operator("operator", "1"),
    note("note", "1"),
    createTime("createTime", "1"),
    product_type("product_type", "1"),
    is_valid("is_valid", "1"),
    order_complete_time("order_complete_time", "1"),
    invoice_code("invoice_code", "1"),
    province("province", "1"),
    city("city", "1"),
    area("area", "1"),
    invoice_item("invoice_item", "1"),
    post_status("post_status", "1"),
    waybill_number("waybill_number", "1"),
    item_number("item_number", "1"),
    misc("misc", "1");

    /** 属性名称 **/
    private String propertyName;
    
    /** 属性类型 默认1 **/
    private String propertyType;

    /** Constructor for WayBillProperty. 
      * <p>Title: </p>
      * <p>Description: </p>
      * @param propertyName
      * @param propertyType
      */
    
    private WayBillProperty(String propertyName, String propertyType) {
	this.propertyName = propertyName;
	this.propertyType = propertyType;
    }

    /**
     * getter method for propertyName
     * @return the propertyName
     */
    public String getPropertyName() {
        return propertyName;
    }

    /**
     * setter method for propertyName
     * @Description the propertyName to set
     * @param propertyName 
     */
    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    /**
     * getter method for propertyType
     * @return the propertyType
     */
    public String getPropertyType() {
        return propertyType;
    }

    /**
     * setter method for propertyType
     * @Description the propertyType to set
     * @param propertyType 
     */
    public void setPropertyType(String propertyType) {
        this.propertyType = propertyType;
    }
    
}
