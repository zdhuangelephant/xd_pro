package com.xiaodou.oms.agent.vo.request;

/**
 * Created by zyp on 14-7-16.
 */
public class OmsMd5 {

    /**签名*/
    protected String sign;

    /**json串*/
    protected String jsonStr;

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getJsonStr() {
        return jsonStr;
    }

    public void setJsonStr(String jsonStr) {
        this.jsonStr = jsonStr;
    }
}
