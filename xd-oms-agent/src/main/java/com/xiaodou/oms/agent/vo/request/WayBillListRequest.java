package com.xiaodou.oms.agent.vo.request;

import java.util.Map;

/**
 * WayBillListRequest
  * @Title: WayBillListRequest
  * @Desription 列表查询request
  * @author Guanguo.Gao
  * @date 2014年12月2日 下午4:47:07
 */
public class WayBillListRequest extends BaseRequest{

    //分页信息
    private Page page;
    
    // 查询参数
    private WayBillQueryParam queryParams;
    
    /** 输出参数 **/
    private Map<String, Object> outputProperties;

    /**
     * getter method for page
     * @return the page
     */
    public Page getPage() {
        return page;
    }

    /**
     * setter method for page
     * @Description the page to set
     * @param page 
     */
    public void setPage(Page page) {
        this.page = page;
    }

    /**
     * getter method for queryParams
     * @return the queryParams
     */
    public WayBillQueryParam getQueryParams() {
        return queryParams;
    }

    /**
     * setter method for queryParams
     * @Description the queryParams to set
     * @param queryParams 
     */
    public void setQueryParams(WayBillQueryParam queryParams) {
        this.queryParams = queryParams;
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
