package com.xiaodou.oms.agent.vo.response;

/**
 * FirstPayInfoVo
  * @Title: FirstPayInfoVo
  * @Desription 获取支付信息
  * @author Guanguo.Gao
  * @date 2014年12月25日 下午5:19:40
 */
public class FirstPayInfoResponse extends BaseResponse{
    
    /**
     * 回调地址
     */
    private String notifyUrl;
    
    /**
     * 流水号
     */
    private String tradeNo;
    
    /**
     * 支付金额
     */
    private double payAmount;

    /**
     * getter method for notifyUrl
     * @return the notifyUrl
     */
    public String getNotifyUrl() {
        return notifyUrl;
    }

    /**
     * setter method for notifyUrl
     * @Description the notifyUrl to set
     * @param notifyUrl 
     */
    public void setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
    }

    /**
     * getter method for tradeNo
     * @return the tradeNo
     */
    public String getTradeNo() {
        return tradeNo;
    }

    /**
     * setter method for tradeNo
     * @Description the tradeNo to set
     * @param tradeNo 
     */
    public void setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo;
    }

    /**
     * getter method for payAmount
     * @return the payAmount
     */
    public double getPayAmount() {
        return payAmount;
    }

    /**
     * setter method for payAmount
     * @Description the payAmount to set
     * @param payAmount 
     */
    public void setPayAmount(double payAmount) {
        this.payAmount = payAmount;
    }
    
}
