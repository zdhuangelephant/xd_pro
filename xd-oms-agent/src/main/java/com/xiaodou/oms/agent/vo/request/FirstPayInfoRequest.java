package com.xiaodou.oms.agent.vo.request;

/**
 * FirstPayRequest
  * @Title: FirstPayRequest
  * @Desription 查询支付信息request
  * @author Guanguo.Gao
  * @date 2014年12月25日 下午5:29:20
 */
public class FirstPayInfoRequest extends BaseRequest{

    /**
     * 订单id
     */
    private String gorderId;

    /**
     * getter method for gorderId
     * @return the gorderId
     */
    public String getGorderId() {
        return gorderId;
    }

    /**
     * setter method for gorderId
     * @Description the gorderId to set
     * @param gorderId 
     */
    public void setGorderId(String gorderId) {
        this.gorderId = gorderId;
    }
    
}
