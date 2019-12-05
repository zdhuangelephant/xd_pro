package com.xiaodou.payment.vo.response.inner;


import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;
import java.util.Date;

public class MixPaymentTransOperationInfo implements Serializable {

    @JSONField(name = "operation_time")
    private Date operationTime;

    @JSONField(name = "amt")
    private Double amt;

    @JSONField(name = "result_status_cn")
    private String resultStatusCn;

    @JSONField(name = "operation_result_desc_cn")
    private String operationResultDescCn;

    @JSONField(name = "operation_result_desc_en")
    private String operationResultDescEn;

    /**
     * 支付类型
     */
    @JSONField(name = "trans_type")
    private Integer transType;

    /**
     * 支付类型详细描述
     * add by xu.ma 2014/09/23
     * @return
     */
    @JSONField(name="trans_type_desc")
    private String transTypeDesc;

    public String getTransTypeDesc() {
        return transTypeDesc;
    }

    public void setTransTypeDesc(String transTypeDesc) {
        this.transTypeDesc = transTypeDesc;
    }

    public Date getOperationTime() {
        return operationTime;
    }

    public void setOperationTime(Date operationTime) {
        this.operationTime = operationTime;
    }

    public Double getAmt() {
        return amt;
    }

    public void setAmt(Double amt) {
        this.amt = amt;
    }

    public String getOperationResultDescCn() {
        return operationResultDescCn;
    }

    public void setOperationResultDescCn(String operationResultDescCn) {
        this.operationResultDescCn = operationResultDescCn;
    }

    public String getOperationResultDescEn() {
        return operationResultDescEn;
    }

    public void setOperationResultDescEn(String operationResultDescEn) {
        this.operationResultDescEn = operationResultDescEn;
    }

    public Integer getTransType() {
        return transType;
    }

    public void setTransType(Integer transType) {
        this.transType = transType;
    }

    public String getResultStatusCn() {
        return resultStatusCn;
    }

    public void setResultStatusCn(String resultStatusCn) {
        this.resultStatusCn = resultStatusCn;
    }


    @Override
    public String toString() {
        return "MixPaymentTransOperationInfo{" +
                "operationTime=" + operationTime +
                ", amt=" + amt +
                ", resultStatusCn='" + resultStatusCn + '\'' +
                ", operationResultDescCn='" + operationResultDescCn + '\'' +
                ", operationResultDescEn='" + operationResultDescEn + '\'' +
                ", transType=" + transType +
                ", transTypeDesc='" + transTypeDesc + '\'' +
                '}';
    }
}