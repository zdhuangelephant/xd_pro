package com.xiaodou.oms.agent.vo.request;


import com.xiaodou.oms.agent.model.Gorder;

/**
 * Created by zyp on 14-6-24.
 */
public class NewOrderRequest extends BaseRequest {

    /**
     * 订单对象
     */
    private Gorder gorder;

    /**
     * 关联类型
     */
    private String relationType;

    /**
     * 关联关系
     */
    private String relations;
    
    /**
     * fraud业务行为描述
     */
    private String fraudJson;

    public Gorder getGorder() {
        return gorder;
    }

    public void setGorder(Gorder gorder) {
        this.gorder = gorder;
    }

    public String getRelationType() {
        return relationType;
    }

    public void setRelationType(String relationType) {
        this.relationType = relationType;
    }

    public String getProductLine() {
        return productLine;
    }

    public void setProductLine(String productLine) {
        this.productLine = productLine;
    }

    public String getRelations() {
        return relations;
    }

    public void setRelations(String relations) {
        this.relations = relations;
    }

    public String getFraudJson() {
      return fraudJson;
    }

    public void setFraudJson(String fraudJson) {
      this.fraudJson = fraudJson;
    }
}
