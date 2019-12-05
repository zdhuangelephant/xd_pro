package com.xiaodou.oms.constant.order;

/**
 * @author Kai.Chen
 * @version V1.0
 * @date 14/11/23 下午10:05
 */
public class FraudState {
    /** 查询风控，返回状态：成功*/
    public static final String QUERY_FRAUD_RESPONSE_OK = "200";

    /** 推送状态：oms和pmt都推送成功*/
    public static final String PUSH_STATE_BOTH_OMS_AND_PMT = "1";
    /** 推送状态：仅oms推送成功*/
    public static final String PUSH_STATE_ONLY_OMS = "2";
    /** 推送状态：仅pmt推送成功*/
    public static final String PUSH_STATE_ONLY_PMT = "3";
    /** 推送状态：oms和pmt都没推送成功*/
    public static final String PUSH_STATE_NEITHER_OMS_AND_PMT = "4";
    /** 风控状态：可疑*/
    public static final int FRAUD_STATE_DOUBTFUL = 3;
    /** 风控状态：通过*/
    public static final int FRAUD_STATE_PASS = 1;
    /** 风控状态：拒绝*/
    public static final int FRAUD_STATE_REFUSE = 2;
    /** 风控状态：初始或未处理*/
    public static final int FRAUD_STATE_INIT=0;
    /** 通过*/
    public static final String FRAUD_SWITCH_PASS = "pass";
    /** 拒绝*/
    public static final String FRAUD_SWITCH_NO_PASS = "nopass";
    /** 不做处理*/
    public static final String FRAUD_SWITCH_NOT_DO = "donot";
    /** 人工处理*/
    public static final String HANDLE_TYPE_MAN="1";
    /** 系统处理*/
    public static final String HANDLE_TYPE_SYS="0";
    /** 开*/
    public static final String FRAUD_SWITCH_ON = "on";
    /** 关*/
    public static final String FRAUD_SWITCH_OFF = "off";
}
