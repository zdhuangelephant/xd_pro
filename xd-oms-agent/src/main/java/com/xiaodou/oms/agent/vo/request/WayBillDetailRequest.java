package com.xiaodou.oms.agent.vo.request;

import java.util.Map;

/**
 * WayBillDetailRequest
  * @Title: WayBillDetailRequest
  * @Desription 发票查询detail
  * @author Guanguo.Gao
  * @date 2014年12月2日 上午11:57:41
 */
public class WayBillDetailRequest {

    /** 业务线 **/
    private String productLine;
    
    /** gorderid **/
    private String gorderId;
    
    /**
     * 输出属性
     */
    private Map<String, Object> outputProperties;

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
     * getter method for outputProperties
     * @return the outputProperties
     */
    public Map<String, Object> getOutputProperties() {
        return outputProperties;
    }

    /**
     * setter method for outputProperties
     * @Description the outputProperties to set
     * @param outputProperties 
     */
    public void setOutputProperties(Map<String, Object> outputProperties) {
        this.outputProperties = outputProperties;
    }
    
}
