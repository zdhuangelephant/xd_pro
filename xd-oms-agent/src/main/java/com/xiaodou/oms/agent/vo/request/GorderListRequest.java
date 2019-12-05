package com.xiaodou.oms.agent.vo.request;


import java.util.Map;

/**
 * Created by zyp on 14-7-2.
 */
public class GorderListRequest extends BaseRequest {

    /**分页*/
    private Page page;

    /**gorder查询条件*/
    private GorderQueryParam queryParams;

    /**gorder输出参数*/
    private Map<String, Object> outputProperties;

    public String getProductLine() {
        return productLine;
    }

    public void setProductLine(String productLine) {
        this.productLine = productLine;
    }

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }

    public GorderQueryParam getQueryParams() {
        return queryParams;
    }

    public void setQueryParams(GorderQueryParam queryParams) {
        this.queryParams = queryParams;
    }

    public Map<String, Object> getOutputProperties() {
        return outputProperties;
    }

    public void setOutputProperties(Map<String, Object> outputProperties) {
        this.outputProperties = outputProperties;
    }
}
