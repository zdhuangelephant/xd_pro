package com.xiaodou.oms.constant.order;

/**
 * @author Kai.Chen
 * @version V1.0
 * @date 14/11/27 下午12:01
 */
public class FraudConstant {
    /** 风控超时类型 风控系统*/
    public static final String FRAUD_TIMEOUT_ALARM_TYPE_FRAUD_SYSTEM = "风控系统";
    /** 风控超时类型 支付系统*/
    public static final String FRAUD_TIMEOUT_ALARM_TYPE_PAYMENT_SYSTEM="支付系统";
    /** 风控超时类型 风控人工*/
    public static final String FRAUD_TIMEOUT_ALARM_TYPE_FRAUD_MANUAL="风控人工";
    /** 风控超时类型 风控回调*/
    public static final String FRAUD_TIMEOUT_ALARM_TYPE_FRAUD_CALLBACK="风控回调";

    public static final String PRODUCT_LINE_TRAIN ="05";

    public static final String PRODUCT_LINE_IHOTEL="07";

    public static final String PRODUCT_LINE_SELF_PAY="12";
}
