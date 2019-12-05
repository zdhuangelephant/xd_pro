package com.xiaodou.wallet.service.wallet;

import java.sql.Timestamp;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.xiaodou.common.util.CommUtil;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.payment.constant.PaymentConfig;
import com.xiaodou.payment.out.PaymentService;
import com.xiaodou.payment.service.PayService;
import com.xiaodou.payment.util.ClientUtil;
import com.xiaodou.payment.vo.PayMerchant;
import com.xiaodou.payment.vo.request.PayRequest;
import com.xiaodou.payment.vo.request.inner.MixPaymentDc;
import com.xiaodou.payment.vo.response.PayResponse;
import com.xiaodou.payment.vo.response.TokenResponse;
import com.xiaodou.summer.dao.pagination.Page;
import com.xiaodou.summer.dao.param.IQueryParam;
import com.xiaodou.summer.dao.param.QueryEnums.Sort;
import com.xiaodou.summer.dao.param.QueryParam;
import com.xiaodou.summer.vo.out.ResultType;
import com.xiaodou.wallet.constant.WalletConstant;
import com.xiaodou.wallet.enums.WalletOper;
import com.xiaodou.wallet.model.AccountWallet;
import com.xiaodou.wallet.model.AccountWalletBill;
import com.xiaodou.wallet.request.BusinessOperateRequest;
import com.xiaodou.wallet.request.payment.PaymentNotifyPojo;
import com.xiaodou.wallet.request.wallet.AccountBillRequest;
import com.xiaodou.wallet.request.wallet.CreateAccountRequest;
import com.xiaodou.wallet.request.wallet.PayAmountRequest;
import com.xiaodou.wallet.request.wallet.RechargeAmountRequest;
import com.xiaodou.wallet.request.wallet.RechargeOrderRequest;
import com.xiaodou.wallet.response.BaseResponse;
import com.xiaodou.wallet.response.WalletResType;
import com.xiaodou.wallet.response.wallet.AccountAmountResponse;
import com.xiaodou.wallet.response.wallet.AccountBillResponse;
import com.xiaodou.wallet.response.wallet.PayAmountResponse;
import com.xiaodou.wallet.response.wallet.RechargeAmountResponse;
import com.xiaodou.wallet.response.wallet.RechargeOrderResponse;
import com.xiaodou.wallet.service.facade.WalletServiceFacade;
import com.xiaodou.wallet.util.WalletConfig;
import com.xiaodou.wallet.vo.alarm.ConsumeAlarm;
import com.xiaodou.wallet.vo.alarm.OrderAlarm;
import com.xiaodou.wallet.vo.alarm.RechargeAlarm;

@Service("walletService")
public class WalletService {

  /** walletServiceFacade 钱包servicefacade */
  @Resource
  WalletServiceFacade walletServiceFacade;

  private AccountWalletBill fillAndSaveWalletBill(Long walletId, String tradeNo, WalletOper oper,
      String tag, Double amount, Double count) {
    AccountWalletBill bill = new AccountWalletBill();
    bill.setWalletId(walletId);
    bill.setTradeNo(tradeNo);
    bill.setOperateType(oper.getCode());
    bill.setOperateDesc(oper.getDesc());
    bill.setOperateTime(new Timestamp(System.currentTimeMillis()));
    bill.setOperateTag(tag);
    bill.setOperateAmount(amount);
    bill.setOperateCount(count);
    return walletServiceFacade.insertAccountWalletBill(bill);
  }

  /**
   * 发起支付操作
   * 
   * @param request
   * @return
   */
  public PayAmountResponse operatePay(PayAmountRequest request) {
    PayAmountResponse response = new PayAmountResponse(ResultType.SUCCESS);
    AccountWalletBill bill = new AccountWalletBill();
    bill.setTradeNo(request.getTradeNo());
    bill = walletServiceFacade.queryAccountWalletBillByTradeNo4Update(bill);
    if (null != bill && null != bill.getId()) return response;
    AccountWallet wallet = new AccountWallet();
    wallet.setUserId(request.getUserId());
    wallet.setBusinessType(request.getBusinessType());
    wallet.setAccountClassify(request.getAccountClassify());
    wallet = walletServiceFacade.queryAccountWalletBy3d4Update(wallet);
    if (null == wallet) {
      LoggerUtil.alarmInfo(new ConsumeAlarm(WalletResType.CantFindWallet.getMsg()));
      return new PayAmountResponse(WalletResType.CantFindWallet);
    }
    if (wallet.getAccountAmount() < request.getAmount())
      return new PayAmountResponse(WalletResType.BalanceNotEnough);
    fillAndSaveWalletBill(wallet.getId(), request.getTradeNo(), WalletOper.Pay,
        request.getSubject(), request.getAmount(), WalletConstant.PAY_COUNT);
    Double accountAmount = wallet.getAccountAmount() - request.getAmount();
    wallet.setAccountAmount(accountAmount);
    walletServiceFacade.updateAccountWalletById(wallet);
    return response;
  }

  /**
   * 发起充值操作
   * 
   * @param request
   * @return
   */
  public RechargeAmountResponse operateRecharge(RechargeAmountRequest request) {
    AccountWalletBill bill = new AccountWalletBill();
    bill.setId(request.getBillNo());
    bill = walletServiceFacade.queryAccountWalletBillById4Update(bill);
    if (null == bill || null == bill.getId()) {
      LoggerUtil.alarmInfo(new RechargeAlarm(WalletResType.CantFindWalletBill.getMsg()));
      return new RechargeAmountResponse(WalletResType.CantFindWalletBill);
    }
    AccountWallet wallet = new AccountWallet();
    wallet.setId(bill.getWalletId());
    wallet = walletServiceFacade.queryAccountWalletById4Update(wallet);
    if (null == wallet) {
      LoggerUtil.alarmInfo(new RechargeAlarm(WalletResType.CantFindWallet.getMsg()));
      return new RechargeAmountResponse(WalletResType.CantFindWallet);
    }
    return pay(request, bill, wallet);
  }

  /**
   * 回调完成充值操作
   * 
   * @param request
   */
  public void operateDorecharge(PaymentNotifyPojo request) {
    AccountWalletBill bill = new AccountWalletBill();
    bill.setTradeNo(request.getTradeNo());
    bill = walletServiceFacade.queryAccountWalletBillByTradeNo4Update(bill);
    if (null == bill || null == bill.getId())
      throw new RuntimeException("账单信息不存在.TradeNo:" + request.getTradeNo());
    if (bill.getOperateType() != WalletOper.Order.getCode())
      throw new RuntimeException("账单信息已过期.TradeNo:" + request.getTradeNo());
    AccountWallet wallet = new AccountWallet();
    wallet.setId(bill.getWalletId());
    wallet = walletServiceFacade.queryAccountWalletById4Update(wallet);
    Double totalAmount = 0d;
    if (WalletConstant.ACCOUNT_CLASSIFY_LOCAL.equals(wallet.getAccountClassify()))
      totalAmount = wallet.getAccountAmount() + bill.getOperateAmount();
    else
      totalAmount = wallet.getAccountAmount() + bill.getOperateCount();
    wallet.setAccountAmount(totalAmount);
    walletServiceFacade.updateAccountWalletById(wallet);
    bill.setOperateType(WalletOper.Recharge.getCode());
    bill.setOperateDesc(WalletOper.Recharge.getDesc());
    walletServiceFacade.updateAccountWalletBillById(bill);
  }

  /**
   * 发起支付请求
   * 
   * @param request
   * @return
   */
  private RechargeAmountResponse pay(RechargeAmountRequest request, AccountWalletBill bill,
      AccountWallet wallet) {
    RechargeAmountResponse response = new RechargeAmountResponse(ResultType.SUCCESS);
    PayRequest payReq = new PayRequest();
    MixPaymentDc dc = new MixPaymentDc();
    dc.setAmt(bill.getOperateAmount());
    dc.setExternalBankId(1);
    dc.setDcCurrency(PayMerchant.getCurrency());
    dc.setReturnUrl("http://www.51xiaodou.com");
    dc.setCancelUrl("http://www.51xiaodou.com");
    dc.setSubject("北京小逗网络科技有限公司");
    dc.setBody(bill.getOperateTag());
    dc.setCustomerServiceAmt(0d);
    dc.setPaymentMethod(PaymentConfig.PAYMETHOD_ACCOUNT);
    dc.setPaymentProvider(request.getPaymentProvider());
    payReq.setMixPaymentDc(dc);
    payReq.setOrderId(bill.getId().toString());
    payReq.setTradeNo(Long.parseLong(bill.getTradeNo()));
    payReq.setBusinessType(wallet.getBusinessType());
    payReq.setMerchantId(PayMerchant.getMerchantId(WalletConstant.PAYMENT_PRODUCTLINE_WALLET));
    payReq.setMixPaymentType(PaymentConfig.MIX_PAYMENT_DC);
    payReq.setOrderFrom(Integer.toString(ClientUtil.getClinetType(request.getClientType())
        .getCode()));
    payReq.setPayFrom(ClientUtil.getClinetType(request.getClientType()).getCode());
    payReq.setTotalAmt(bill.getOperateAmount());
    payReq.setNotifyUrl(WalletConfig.getPayCallbackUrl());
    PayResponse payResponse =
        PayService.payWithProductLine(payReq, WalletConstant.PAYMENT_PRODUCTLINE_WALLET);
    if (payResponse.isRetOk()) {
      response.setPayStatus(payResponse.getPayStatus());
      response.setPayInfo(payResponse.getPayInfo());
    } else {
      LoggerUtil.alarmInfo(new RechargeAlarm(WalletResType.PayFail.getMsg()));
      response = new RechargeAmountResponse(WalletResType.PayFail);
    }
    return response;
  }

  private AccountWallet queryAccountWallet(String userId, Integer businessType,
      String accountClassify) {
    AccountWallet wallet = new AccountWallet();
    wallet.setUserId(userId);
    wallet.setBusinessType(businessType);
    wallet.setAccountClassify(accountClassify);
    wallet = walletServiceFacade.queryAccountWalletBy3d4Update(wallet);
    return wallet;
  }

  /**
   * 创建账户
   * 
   * @param request
   * @return
   */
  public BaseResponse operateCreateAccount(CreateAccountRequest request) {
    BaseResponse response = new BaseResponse(ResultType.SUCCESS);
    for (Integer businessType : request.getBusinessType()) {
      createAccount(WalletConstant.ACCOUNT_CLASSIFY_LOCAL, businessType, request);
      createAccount(WalletConstant.ACCOUNT_CLASSIFY_IOS, businessType, request);
      createAccount(WalletConstant.ACCOUNT_CLASSIFY_ANDROID, businessType, request);
    }
    return response;
  }

  private void createAccount(String accountClassify, Integer businessType,
      CreateAccountRequest request) {
    AccountWallet wallet = new AccountWallet();
    wallet.setUserId(request.getUserId());
    wallet.setBusinessType(businessType);
    wallet.setAccountClassify(accountClassify);
    wallet = walletServiceFacade.queryAccountWalletBy3d4Update(wallet);
    if (null != wallet) return;
    wallet = new AccountWallet();
    wallet.setUserId(request.getUserId());
    wallet.setBusinessType(businessType);
    wallet.setAccountClassify(accountClassify);
    wallet.setCreateTime(new Timestamp(System.currentTimeMillis()));
    walletServiceFacade.insertAccountWallet(wallet);
  }

  /**
   * 创建充值订单
   * 
   * @param request
   * @return
   */
  public RechargeOrderResponse operateOrder(RechargeOrderRequest request) {
    RechargeOrderResponse response = new RechargeOrderResponse(ResultType.SUCCESS);
    AccountWallet wallet = new AccountWallet();
    wallet.setUserId(request.getUserId());
    wallet.setAccountClassify(request.getAccountClassify());
    wallet.setBusinessType(request.getBusinessType());
    wallet = walletServiceFacade.queryAccountWalletBy3d4Update(wallet);
    if (null == wallet) {
      LoggerUtil.alarmInfo(new OrderAlarm(WalletResType.CantFindWallet.getMsg()));
      return new RechargeOrderResponse(WalletResType.CantFindWallet);
    }
    TokenResponse payToken =
        PaymentService.getToken(WalletConstant.PAYMENT_PRODUCTLINE_WALLET,
            request.getBusinessType());
    if (!payToken.isRetOk() || StringUtils.isBlank(payToken.getTradeNo())) {
      LoggerUtil.alarmInfo(new OrderAlarm(WalletResType.PayFail.getMsg()));
      return new RechargeOrderResponse(WalletResType.PayFail);
    }
    String token = payToken.getTradeNo();
    AccountWalletBill bill =
        fillAndSaveWalletBill(wallet.getId(), token, WalletOper.Order, request.getGoodName(),
            request.getGoodPrice(), request.getGoodCount());
    response.setBillNo(bill.getId());
    response.setTradeNo(token);
    return response;
  }

  /**
   * 查询账户
   * 
   * @param request
   * @return
   */
  public AccountAmountResponse queryAccountWallet(BusinessOperateRequest request) {
    AccountAmountResponse response = new AccountAmountResponse(ResultType.SUCCESS);
    AccountWallet wallet =
        this.queryAccountWallet(request.getUserId(), request.getBusinessType(),
            request.getAccountClassify());
    if (null != wallet) {
      response.setAccountAmount(wallet.getAccountAmount());
    } else {
      return new AccountAmountResponse(WalletResType.CantFindWallet);
    }
    return response;
  }

  /**
   * 查询用户账单
   * 
   * @param request 查询账单列表参数
   * @return 账单列表
   */
  public AccountBillResponse queryAccountBill(AccountBillRequest request) {
    AccountBillResponse response = new AccountBillResponse(ResultType.SUCCESS);
    AccountWallet wallet = new AccountWallet();
    wallet.setUserId(request.getUserId());
    wallet.setAccountClassify(request.getAccountClassify());
    wallet.setBusinessType(request.getBusinessType());
    wallet = walletServiceFacade.queryAccountWalletBy3d4Update(wallet);
    if (null == wallet) return new AccountBillResponse(WalletResType.CantFindWallet);
    IQueryParam param = new QueryParam();
    param.addInput("walletId", wallet.getId());
    if (null != request.getOperType() && request.getOperType().size() > 0)
      param.addInput("operateTypeList", request.getOperType());
    // param.addSort("operateTime", Sort.DESC);
    param.addOutputs(CommUtil.getAllField(AccountWalletBill.class));
    param.addSort("operateTime", Sort.DESC);
    Page<AccountWalletBill> billPage = new Page<>(request.getPageNo(), request.getPageSize());
    billPage = walletServiceFacade.queryAccountWalletBillByCond(param, billPage);
    if (null == billPage || null == billPage.getResult() || billPage.getResult().size() == 0)
      return response;
    response.setBillList(billPage.getResult());
    return response;
  }

}
