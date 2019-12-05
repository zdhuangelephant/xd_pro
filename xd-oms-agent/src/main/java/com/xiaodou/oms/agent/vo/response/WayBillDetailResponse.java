package com.xiaodou.oms.agent.vo.response;

import java.util.Map;

/**
 * WayBillDetailResponse
  * @Title: WayBillDetailResponse
  * @Desription 发票response
  * @author Guanguo.Gao
  * @date 2014年12月2日 下午3:48:31
 */
public class WayBillDetailResponse extends BaseResponse{

    private Map<String, Object> waybill;

    /**
     * getter method for waybill
     * @return the waybill
     */
    public Map<String, Object> getWaybill() {
        return waybill;
    }

    /**
     * setter method for waybill
     * @Description the waybill to set
     * @param waybill 
     */
    public void setWaybill(Map<String, Object> waybill) {
        this.waybill = waybill;
    }
    
}
