package com.xiaodou.oms.agent.vo.response;

/**
 * Created by zyp on 14-7-2.
 */
public class PayGetTokentResponse extends BaseResponse {

    /**支付token*/
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
