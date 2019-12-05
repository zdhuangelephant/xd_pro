package com.xiaodou.oms.agent.service;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.oms.agent.util.OrderConfigService;
import com.xiaodou.oms.agent.vo.request.PayContinuePayRequest;
import com.xiaodou.oms.agent.vo.request.PayGetTokenRequest;
import com.xiaodou.oms.agent.vo.request.PayRecordListRequest;
import com.xiaodou.oms.agent.vo.response.PayContinuePayResponse;
import com.xiaodou.oms.agent.vo.response.PayGetTokentResponse;
import com.xiaodou.oms.agent.vo.response.PayRecordListResponse;

/**
 * Created by zyp on 14-6-18.
 */
@Service("paymentService")
public class PaymentService extends BaseService {

    /**
     * 获取支付token
     * @param payGetTokenRequest
     * @return
     */
    public PayGetTokentResponse getPayToken(PayGetTokenRequest payGetTokenRequest){
        PayGetTokentResponse payGetTokentResponse = null;
        try {
            String body = JSON.toJSONString(payGetTokenRequest);
            String url = OrderConfigService.getParams("oms.pay.getPayToken");
            String timeOut = OrderConfigService.getParams("oms.pay.getPayToken.timeOut");
            String retry = OrderConfigService.getParams("oms.pay.getPayToken.retry");
            payGetTokentResponse = sendHttp(url, body, PayGetTokentResponse.class,timeOut,retry,true);
        } catch (Exception e){
            LoggerUtil.error("[omsagent][getPayToken]",e);
        }
        return payGetTokentResponse;
    }

    /**
     * 获取支付记录
     * @param payRecordListRequest
     * @return
     */
    public PayRecordListResponse queryPayRecordList(PayRecordListRequest payRecordListRequest){
        PayRecordListResponse payRecordListResponse = null;
        try {
            String body = JSON.toJSONString(payRecordListRequest);
            String url = OrderConfigService.getParams("oms.pay.queryPayRecordList");
            String timeOut = OrderConfigService.getParams("oms.order.queryPayRecordList.timeOut");
            String retry = OrderConfigService.getParams("oms.order.queryPayRecordList.retry");
            payRecordListResponse = sendHttp(url, body, PayRecordListResponse.class,timeOut,retry,true);
        } catch (Exception e){
            LoggerUtil.error("[omsagent][queryPayRecordList]",e);
        }
        return payRecordListResponse;
    }

    /**
     * 继续支付
     * @param payContinuePayRequest
     * @return
     */
    public PayContinuePayResponse continuePay(PayContinuePayRequest payContinuePayRequest){
        PayContinuePayResponse payContinuePayResponse = null;
        try {
            String body = JSON.toJSONString(payContinuePayRequest);
            String url = OrderConfigService.getParams("oms.pay.createPayRecord");
            String timeOut = OrderConfigService.getParams("oms.order.createPayRecord.timeOut");
            String retry = OrderConfigService.getParams("oms.order.createPayRecord.retry");
            payContinuePayResponse = sendHttp(url, body, PayContinuePayResponse.class,timeOut,retry,true);
        } catch (Exception e){
            LoggerUtil.error("[omsagent][continuePay]",e);
        }
        return payContinuePayResponse;
    }


}
