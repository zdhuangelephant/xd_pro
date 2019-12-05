package com.xiaodou.wallet.agent.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;

import com.xiaodou.summer.web.BaseController;
import com.xiaodou.wallet.agent.constants.AccountClassify;
import com.xiaodou.wallet.agent.constants.Protocol;
import com.xiaodou.wallet.agent.enums.WalletOper;
import com.xiaodou.wallet.agent.model.IGoodEntity;
import com.xiaodou.wallet.agent.request.AccountBillRequest;
import com.xiaodou.wallet.agent.request.BusinessOperateRequest;
import com.xiaodou.wallet.agent.request.CreateAccountRequest;
import com.xiaodou.wallet.agent.request.PayAmountRequest;
import com.xiaodou.wallet.agent.request.RechargeAmountRequest;
import com.xiaodou.wallet.agent.request.RechargeOrderRequest;
import com.xiaodou.wallet.agent.response.AccountAmountResponse;
import com.xiaodou.wallet.agent.response.AccountBillResponse;
import com.xiaodou.wallet.agent.response.PayAmountResponse;
import com.xiaodou.wallet.agent.response.RechargeAmountResponse;
import com.xiaodou.wallet.agent.response.RechargeOrderResponse;
import com.xiaodou.wallet.agent.response.ResponseBase;
import com.xiaodou.wallet.agent.util.SignUtil;
import com.xiaodou.wallet.agent.util.WalletConfig;


@Service("walletService")
public class WalletService extends BaseController {

  /**
   * 1.创建账户
   * 
   * @param productLine 所属产品线
   * @param uid 用户ID
   * @param businessType 要注册的业务类型
   * @return
   */
  public static ResponseBase createAccount(String productLine, String uid,
      List<Integer> businessType) {
    CreateAccountRequest request = new CreateAccountRequest();
    request.setProductLine(productLine);
    request.setUserId(uid);
    request.setBusinessType(businessType);
    String key = WalletConfig.getKey(productLine);
    String sign = SignUtil.sign(request, key);
    request.setSign(sign);
    Errors error = request.validate();
    if (error.hasErrors()) throw new IllegalArgumentException(getErrMsg(error));
    Protocol protocol = new Protocol(Protocol.POST);
    protocol.setUrl(WalletConfig.createAccountUrl());
    return FlowService.doFlow(request, ResponseBase.class, protocol);
  }

  /**
   * 2.发起支付
   * 
   * @param productLine 所属产品线
   * @param uid 用户ID
   * @param amount 支付金额
   * @param tradeNo 交易流水号
   * @return
   */
  public static PayAmountResponse payAmount(String productLine, String uid, Integer businessType,
      Short accountClassify, String subject, String desc, Double amount, String tradeNo) {
    PayAmountRequest request = new PayAmountRequest();
    request.setProductLine(productLine);
    request.setUserId(uid);
    request.setBusinessType(businessType);
    request.setAccountClassify(accountClassify);
    request.setSubject(subject);
    request.setDesc(desc);
    request.setAmount(amount);
    request.setTradeNo(tradeNo);
    String key = WalletConfig.getKey(productLine);
    String sign = SignUtil.sign(request, key);
    request.setSign(sign);
    Errors error = request.validate();
    if (error.hasErrors()) throw new IllegalArgumentException(getErrMsg(error));
    Protocol protocol = new Protocol(Protocol.POST);
    protocol.setUrl(WalletConfig.payAmountUrl());
    return FlowService.doFlow(request, PayAmountResponse.class, protocol);
  }

  /**
   * 3.生成充值订单
   * 
   * @param productLine 所属产品线
   * @param uid 用户ID
   * @param goodId 商品ID
   * @return
   */
  public static RechargeOrderResponse order(String productLine, String uid, Integer businessType,
      AccountClassify accountClassify, IGoodEntity goodEntity) {
    RechargeOrderRequest request = new RechargeOrderRequest();
    request.setProductLine(productLine);
    request.setUserId(uid);
    request.setBusinessType(businessType);
    request.setAccountClassify(accountClassify.getCode());
    request.setGoodName(goodEntity.getName());
    request.setGoodPrice(goodEntity.getAmount());
    request.setGoodCount(goodEntity.getCount());
    String key = WalletConfig.getKey(productLine);
    String sign = SignUtil.sign(request, key);
    request.setSign(sign);
    Errors error = request.validate();
    if (error.hasErrors()) throw new IllegalArgumentException(getErrMsg(error));
    Protocol protocol = new Protocol(Protocol.POST);
    protocol.setUrl(WalletConfig.orderUrl());
    return FlowService.doFlow(request, RechargeOrderResponse.class, protocol);
  }

  /**
   * 4.发起充值
   * 
   * @param productLine 所属产品线
   * @param uid 用户ID
   * @param billNo 充值账单号
   * @param clientType 设备类型
   * @return
   */
  public static RechargeAmountResponse rechargeAmount(String productLine, String uid, Long billNo,
      String clientType, Integer paymentProvider) {
    RechargeAmountRequest request = new RechargeAmountRequest();
    request.setProductLine(productLine);
    request.setUserId(uid);
    request.setBillNo(billNo);
    request.setClientType(clientType);
    request.setPaymentProvider(paymentProvider);
    String key = WalletConfig.getKey(productLine);
    String sign = SignUtil.sign(request, key);
    request.setSign(sign);
    Errors error = request.validate();
    if (error.hasErrors()) throw new IllegalArgumentException(getErrMsg(error));
    Protocol protocol = new Protocol(Protocol.POST);
    protocol.setUrl(WalletConfig.rechargeAmountUrl());
    return FlowService.doFlow(request, RechargeAmountResponse.class, protocol);
  }

  /**
   * 查询用户余额
   * 
   * @param productLine 所属产品线
   * @param uid 用户ID
   * @return
   */
  public static AccountAmountResponse queryAccountAmount(String productLine, String uid,
      Integer businessType, AccountClassify accountClassify) {
    BusinessOperateRequest request = new BusinessOperateRequest();
    request.setProductLine(productLine);
    request.setUserId(uid);
    request.setBusinessType(businessType);
    request.setAccountClassify(accountClassify.getCode());
    String key = WalletConfig.getKey(productLine);
    String sign = SignUtil.sign(request, key);
    request.setSign(sign);
    Errors error = request.validate();
    if (error.hasErrors()) throw new IllegalArgumentException(getErrMsg(error));
    Protocol protocol = new Protocol(Protocol.POST);
    protocol.setUrl(WalletConfig.queryAmountWalletUrl());
    return FlowService.doFlow(request, AccountAmountResponse.class, protocol);
  }

  /**
   * 查询用户账单
   * 
   * @param request
   * @return
   */
  public static AccountBillResponse queryAccountBill(String productLine, String uid,
      Integer businessType, AccountClassify accountClassify, List<WalletOper> operList,
      Integer pageNo, Integer pageSize) {
    AccountBillRequest request = new AccountBillRequest();
    request.setProductLine(productLine);
    request.setUserId(uid);
    request.setBusinessType(businessType);
    request.setAccountClassify(accountClassify.getCode());
    if (null != operList && operList.size() > 0) for (WalletOper oper : operList)
      request.getOperType().add(oper.getCode());
    request.setPageNo(pageNo);
    request.setPageSize(pageSize);
    String key = WalletConfig.getKey(productLine);
    String sign = SignUtil.sign(request, key);
    request.setSign(sign);
    Errors error = request.validate();
    if (error.hasErrors()) throw new IllegalArgumentException(getErrMsg(error));
    Protocol protocol = new Protocol(Protocol.POST);
    protocol.setUrl(WalletConfig.queryAccountWalletBill());
    return FlowService.doFlow(request, AccountBillResponse.class, protocol);
  }

}
