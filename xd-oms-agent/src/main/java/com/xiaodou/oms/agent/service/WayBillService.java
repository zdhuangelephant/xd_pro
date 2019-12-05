package com.xiaodou.oms.agent.service;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.oms.agent.util.OrderConfigService;
import com.xiaodou.oms.agent.vo.request.WayBillDetailRequest;
import com.xiaodou.oms.agent.vo.request.WayBillListRequest;
import com.xiaodou.oms.agent.vo.response.WayBillDetailResponse;
import com.xiaodou.oms.agent.vo.response.WayBillListResponse;

/**
 * WillBillService
  * @Title: WillBillService
  * @Desription 发票service
  * @author Guanguo.Gao
  * @date 2014年12月2日 上午11:53:15
 */
@Service("wayBillService")
public class WayBillService extends BaseService{

    /**
     * queryWayBillDetail
      * @Title: queryWayBillDetail
      * @Description: 查询发票详情
      * @param wayBillDetailRequest
      * @return
     */
    public WayBillDetailResponse queryWayBillDetail(WayBillDetailRequest wayBillDetailRequest){
	WayBillDetailResponse wayBillDetailResponse = new WayBillDetailResponse();
	try{
	    String body = JSON.toJSONString(wayBillDetailRequest);
	    String url = OrderConfigService.getParams("oms.waybill.queryWayBillDetail");
	    String timeOut = OrderConfigService.getParams("oms.waybill.queryWayBillDetail.timeOut");
	    String retryTimes = OrderConfigService.getParams("oms.waybill.queryWayBillDetail.retry");
	    wayBillDetailResponse = sendHttp(url, body, WayBillDetailResponse.class, 
		    timeOut, retryTimes, false);
	}catch(Exception e){
	    LoggerUtil.error("[omsagent][queryWayBillDetail]", e);
	}
	return wayBillDetailResponse;
    }
    
    
    /**
     * queryWayBillList
      * @Title: queryWayBillList
      * @Description: 查询列表
      * @param request 
      * @return 发票列表
     */
    public WayBillListResponse queryWayBillList(WayBillListRequest request){
	WayBillListResponse response = null;
	try {
	    String body = JSON.toJSONString(request);
	    String url = OrderConfigService.getParams("oms.waybill.queryWayBillList");
	    String timeOut = OrderConfigService.getParams("oms.waybill.queryWayBillList.timeOut");
	    String retryTimes = OrderConfigService.getParams("oms.waybill.queryWayBillList.retry");
	    response = sendHttp(url, body, WayBillListResponse.class, timeOut, retryTimes, false);
	} catch (Exception e) {
	    LoggerUtil.error("[omsagent][queryWayBillList]", e);
	}
	return response;
    }
}
