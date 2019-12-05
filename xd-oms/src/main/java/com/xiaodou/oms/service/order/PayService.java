package com.xiaodou.oms.service.order;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.xiaodou.common.util.FileUtil;
import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.oms.constant.order.OrderConstant;
import com.xiaodou.oms.dao.order.PayRecordDao;
import com.xiaodou.oms.dao.order.PayRequestDao;
import com.xiaodou.oms.entity.order.Gorder;
import com.xiaodou.oms.entity.order.PayRecord;
import com.xiaodou.oms.entity.order.PayRequest;
import com.xiaodou.oms.exception.OrderException;
import com.xiaodou.oms.exception.ValidationException;
import com.xiaodou.oms.util.IDGenerator;
import com.xiaodou.oms.vo.input.pay.GetTokenPojo;
import com.xiaodou.oms.vo.input.pay.QueryRecordPojo;
import com.xiaodou.oms.vo.result.PageInfo;
import com.xiaodou.oms.vo.result.ResultType;
import com.xiaodou.oms.vo.result.pay.GetTokenVO;
import com.xiaodou.oms.vo.result.pay.PayRecordListVO;
import com.xiaodou.payment.out.PaymentService;
import com.xiaodou.payment.vo.PayCreditCardInfo;
import com.xiaodou.payment.vo.input.GetPayDetailPojo;
import com.xiaodou.payment.vo.input.GetPayStatusPojo;
import com.xiaodou.payment.vo.input.GetPayTypePojo;
import com.xiaodou.payment.vo.response.PayDetailResponse;
import com.xiaodou.payment.vo.response.PayResponse;
import com.xiaodou.payment.vo.response.PayStatusResponse;
import com.xiaodou.payment.vo.response.PayTypeResponse;
import com.xiaodou.payment.vo.response.TokenResponse;
import com.xiaodou.payment.vo.response.inner.MixPaymentTransOperationInfo;
import com.xiaodou.summer.dao.pagination.Page;

/**
 * @author zhaoyang
 * @version V1.0
 * @Title:PayService.java
 * @Description:payment业务相关的service类
 * @date Mar 13, 2014 6:39:21 PM
 */
@Service("payService")
public class PayService {

  @Resource
  PayRequestDao payRequestDao;

  @Resource
  PayRecordDao payRecordDao;

  FileUtil fileUtil = FileUtil.getInstance("/conf/custom/env/red_bonuses.properties");

  /**
   * 添加payment请求
   * 
   * @param payRequest
   */
  public void addPayRequest(PayRequest payRequest) {
    payRequestDao.addEntity(payRequest);
  }

  /**
   * 查询payment记录
   * 
   * @param input
   * @param output
   * @param page
   * @return
   */
  public Page<PayRequest> queryPayRequestList(Map<String, Object> input,
      Map<String, Object> output, Page<PayRequest> page) {
    Map<String, Object> cond = new HashMap<String, Object>();
    cond.put("input", input);
    cond.put("output", output);
    return payRequestDao.findEntityListByCond(cond, page);
  }

  /**
   * 添加payment记录
   * 
   * @param payRecord
   */
  public void addPayRecord(PayRecord payRecord) {
    payRecordDao.addEntity(payRecord);
  }

  /**
   * 查询payment记录
   * 
   * @param input
   * @param output
   * @param page
   * @return
   */
  public Page<PayRecord> queryPayRecordList(Map input, Map output, Page page) {
    Map cond = new HashMap();
    cond.put("input", input);
    cond.put("output", output);
    return payRecordDao.findEntityListByCond(cond, page);
  }

  /**
   * 修改payrecord记录
   * 
   * @param condition
   * @param entity
   */
  public boolean updatePayRecord(PayRecord condition, PayRecord entity) {
    return payRecordDao.updateEntity(condition, entity);
  }

  /**
   * 修改payrequest记录
   * 
   * @param condition
   * @param entity
   */
  public boolean updatePayRequest(PayRequest condition, PayRequest entity) {
    return payRequestDao.updateEntity(condition, entity);
  }


  public PayRecord queryLastPayNo(PayRecord payRecord) {
    return payRecordDao.queryLastPayNo(payRecord);
  }

  public Integer queryPayRecordCountForContinuePay(String gorderId) {
    return payRecordDao.queryPayRecordCountForContinuePay(gorderId);
  }

  /**
   * 支付失败，用户再次支付时调用此方法写pay_record记录
   * 
   * @param gorder
   * @return
   * @throws ValidationException
   * @throws OrderException
   */
  @SuppressWarnings("unchecked")
  public int continuePay(Gorder gorder) throws ValidationException, OrderException {

    if (gorder == null) {
      return 1;
    } else if (!OrderConstant.STATUS_PAYFAILURE.equals(gorder.getGorderStatus())) {
      return 2;
    }
    return 0;
  }

  public void movePayRequestToRecord(String payRequestId, String processDays, Integer payStatus,
      String failReason) throws ValidationException, OrderException {
    if (null == failReason) {
      failReason = "";
    }
    PayRecord payRecord = new PayRecord();
    payRecord.setId(payRequestId);
    payRecord.setProcessDays(processDays);
    payRecord.setPayStatus(payStatus);
    payRecord.setSentTime(new Timestamp(System.currentTimeMillis()));
    payRecord.setFailureReason(failReason);
    payRecordDao.addEntityFromPayRequest(payRecord);

    PayRequest payRequest = new PayRequest();
    payRequest.setId(payRequestId);
    payRequestDao.deleteEntity(payRequest);

  }

  public void createPayRequest(BigDecimal amount, String buyAccountId, String productType,
      String gorderId, Integer operType, Integer payType, String callbackUrl, String uuid)
      throws ValidationException {

    if (amount != null && amount.compareTo(new BigDecimal("0")) <= 0) {
      return;
    }

    PayRecord queryParam = new PayRecord();
    queryParam.setGorderId(gorderId);
    queryParam.setOperType(PayRecord.OPER_TYPE_PAY);
    PayRecord payRecord = queryLastPayNo(queryParam);

    if (payRecord == null) {
      // throw new ValidationException("上一条payRecord记录找不到，gorderId=" + gorderId);
      return;
    }
    PayRequest payRequest = new PayRequest();
    payRequest.setId(IDGenerator.getSeqID("xd_order_sequence_id"));
    payRequest.setAmount(amount);
    payRequest.setBuyAccountId(buyAccountId);
    payRequest.setGorderId(gorderId);
    payRequest.setOperType(operType);
    payRequest.setPayType(payType);
    payRequest.setProductType(productType);
    payRequest.setPayStatus(PayRequest.PAY_STATUS_WAIT);
    payRequest.setPrePayNo(payRecord.getPayNo());
    payRequest.setClientType(payRecord.getClientType());
    payRequest.setOuterOrigin(payRecord.getOuterOrigin());
    payRequest.setCallbackUrl(callbackUrl);
    payRequest.setUuid(uuid);
    addPayRequest(payRequest);
  }


  public GetTokenVO getToken(GetTokenPojo pojo) {
    GetTokenVO vo = null;
    // 参数通过，开始获取token
    TokenResponse token = PaymentService.getToken(pojo.getProductLine());
    if (token == null) {
      vo = new GetTokenVO(ResultType.SYSFAIL);
      return vo;
    }
    if (token.getRetcode().equals("0")) {
      vo = new GetTokenVO(ResultType.SUCCESS);
    } else {
      vo = new GetTokenVO(Integer.parseInt(token.getRetcode()), token.getMessage());
    }
    vo.setToken(token.getTradeNo());
    return vo;
  }


  public PayRecordListVO queryPayRecordList(QueryRecordPojo pojo) throws ValidationException,
      OrderException {
    Map<String, Object> input = new HashMap<String, Object>();
    // input.put("productType", pojo.getProductLine());
    input.put("gorderId", pojo.getGorderId());
    Map<String, Object> output = new HashMap<String, Object>();
    output.put("id", "");
    output.put("gorderId", "");
    output.put("orderId", "");
    output.put("buyAccountId", "");
    output.put("operType", "");
    output.put("payType", "");
    output.put("amount", "");
    output.put("payNo", "");
    output.put("prePayNo", "");
    output.put("payStatus", "");
    output.put("createTime", "");
    output.put("sentTime", "");
    output.put("paymentTime", "");
    output.put("productType", "");
    output.put("note", "");
    output.put("failureReason", "");
    output.put("processDays", "");
    output.put("clientType", "");
    output.put("outerOrigin", "");
    output.put("callbackUrl", "");
    output.put("uuid", "");
    Page<PayRecord> payRecordPage = queryPayRecordList(input, output, null);
    PayRecordListVO vo = new PayRecordListVO(ResultType.SUCCESS);
    vo.setPage(new PageInfo(payRecordPage));
    vo.setList(payRecordPage.getResult());
    return vo;
  }

  /**
   * 找到gorder的第一次支付记录
   * 
   * @param gorder
   * @return
   */
  public List<PayRecord> queryFirstPayRecord(Gorder gorder) {
    Map<String, Object> input = new HashMap<String, Object>();
    input.put("gorderId", gorder.getId());
    input.put("operType", PayRecord.OPER_TYPE_PAY);
    input.put("payType", PayRecord.PAY_TYPE_ONE);
    Map<String, Object> output = new HashMap<String, Object>();
    output.put("id", "");
    output.put("gorderId", "");
    output.put("orderId", "");
    output.put("operType", "");
    output.put("payType", "");
    output.put("amount", "");
    output.put("payNo", "");
    output.put("prePayNo", "");
    output.put("payStatus", "");
    output.put("createTime", "");
    output.put("sentTime", "");
    output.put("paymentTime", "");
    output.put("productType", "");
    output.put("note", "");
    output.put("failureReason", "");
    output.put("processDays", "");
    output.put("clientType", "");
    output.put("outerOrigin", "");
    output.put("callbackUrl", "");
    output.put("uuid", "");
    Page<PayRecord> payRecordPage = queryPayRecordList(input, output, null);
    return payRecordPage.getResult();
  }

  public void movePayRequestToRecord(PayRequest payRequest, PayRecord payRecord) {
    payRecordDao.addEntityFromPayRequest(payRecord);
    payRequestDao.deleteEntity(payRequest);
  }

  /**
   * 判断payRecord生成时间（下单时间）是否符合红包活动时间
   */
  public boolean judgeIsRedBonusesActivityTime(Date date) {
    try {
      String startTime = fileUtil.getProperties("red_bonuses_start");
      String endTime = fileUtil.getProperties("red_bonuses_end");
      SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
      Date startDate = sdf.parse(startTime);
      Date endDate = sdf.parse(endTime);

      return date.after(startDate) && date.before(endDate);
    } catch (Exception e) {
      LoggerUtil.error("judgeIsActivityTime异常", e);
    }
    return false;
  }

  /**
   * 判断是否红包活动开关on
   */
  public boolean judgeIsRedBonusesActivityOn() {
    String onOff = fileUtil.getProperties("red_bonuses_switch");
    return onOff != null && onOff.equals("on");
  }

  /**
   * 找到这个会员火车票的第一次支付成功的记录
   * 
   * @param payRecord 本次支付记录
   * @return 是否会员火车票支付成功过 true 没有成功过，送红包 false 成功过，不送红包
   */
  public boolean judgeIsAppTrainFirst(PayRecord payRecord) {
    if (payRecord == null) {
      return false;
    }
    // 判断是否为火车票订单,不是则直接返回
    if (payRecord.getProductType() != null && !payRecord.getProductType().equals("0501")) {
      return false;
    }
    // 不是app 直接返回
    if (payRecord.getOuterOrigin() != null && !payRecord.getOuterOrigin().equals("app")) {
      return false;
    }

    Map<String, Object> input = new HashMap<String, Object>();
    input.put("productType", "0501");
    input.put("outerOrigin", "app");
    input.put("buyAccountId", payRecord.getBuyAccountId());
    input.put("payType", "1");
    input.put("operType", "0");
    input.put("payStatus", "2");
    Map<String, Object> output = new HashMap<String, Object>();
    output.put("id", "");
    Page<PayRecord> page = this.queryPayRecordList(input, output, null);
    List<PayRecord> list = page.getResult();
    return list == null || list.size() == 0;
  }

  public PayDetailResponse queryOrderPayDetail(Gorder gorder) {
    GetPayDetailPojo pojo = new GetPayDetailPojo();
    pojo.setGorderId(gorder.getId());
    pojo.setProductLine(gorder.getProductType().substring(0, 2));
    PayDetailResponse response = PaymentService.getPayDetail(pojo);
    return response;
  }

  public PayTypeResponse getPayType(PayRecord record) {
    String productLine = record.getProductType().substring(0, 2);
    String gorderId = record.getGorderId();
    String token = record.getPayNo();
    GetPayTypePojo getPayTypePojo = new GetPayTypePojo();
    getPayTypePojo.setProductLine(productLine);
    getPayTypePojo.setGorderId(gorderId);
    getPayTypePojo.setToken(token);
    PayTypeResponse payTypeResponse = PaymentService.getPayType(getPayTypePojo);
    return payTypeResponse;
  }

  public PayStatusResponse getPayStatus(PayRecord record) {
    String productLine = record.getProductType().substring(0, 2);
    String gorderId = record.getGorderId();
    String token = record.getPayNo();
    GetPayStatusPojo pojo = new GetPayStatusPojo();
    pojo.setProductLine(productLine);
    pojo.setGorderId(gorderId);
    pojo.setToken(token);
    PayStatusResponse payStatusResponse = PaymentService.getPayStatus(pojo);
    return payStatusResponse;
  }

  /**
   * 获取payment详情中最新的记录
   * 
   * @param mixPaymentTransOperationInfos
   * @return
   */
  public static MixPaymentTransOperationInfo getNewestRecord(
      List<MixPaymentTransOperationInfo> mixPaymentTransOperationInfos) {
    if (mixPaymentTransOperationInfos == null) return null;
    MixPaymentTransOperationInfo newestOperationInfo = null;
    for (MixPaymentTransOperationInfo paymentTransOperationInfo : mixPaymentTransOperationInfos) {
      if (newestOperationInfo == null && paymentTransOperationInfo.getOperationTime() != null) {
        newestOperationInfo = paymentTransOperationInfo;
        continue;
      }
      Date operationTime = paymentTransOperationInfo.getOperationTime();
      if (operationTime != null && operationTime.after(newestOperationInfo.getOperationTime())) {
        newestOperationInfo = paymentTransOperationInfo;
      }
    }
    return newestOperationInfo;
  }

  public static PayResponse pay(com.xiaodou.payment.vo.request.PayRequest payRequest,
      String string, boolean b) {
    // TODO Auto-generated method stub
    return null;
  }

  public static PayResponse pay(PayCreditCardInfo payCardInfo) {
    // TODO Auto-generated method stub
    return null;
  }

}
