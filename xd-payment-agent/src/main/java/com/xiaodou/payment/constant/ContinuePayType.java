package com.xiaodou.payment.constant;

/**
 * 继续支付
 * @author Kai.Chen
 * @version V1.0
 * @date 14/11/18 下午4:25
 */
public enum ContinuePayType {

    ContinuePay(1,"是"),
    NotContinuePay(0,"否");

    private ContinuePayType(int code,String desc){
        this.code = code;
        this.desc = desc;
    }
    /** 代码*/
    private int code;
    /** 描述*/
    private String desc;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
