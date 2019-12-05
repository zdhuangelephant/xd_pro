package com.xiaodou.userCenter.service;

import org.springframework.stereotype.Service;

import com.xiaodou.common.util.CommUtil;
import com.xiaodou.common.util.IPUtil;
import com.xiaodou.payment.util.PaymentProviderUtil;
import com.xiaodou.summer.dao.pagination.Page;
import com.xiaodou.summer.dao.param.IQueryParam;
import com.xiaodou.summer.dao.param.QueryParam;
import com.xiaodou.summer.vo.out.ResultType;
import com.xiaodou.userCenter.constant.UcenterConstant;
import com.xiaodou.userCenter.model.AccountWalletGood;
import com.xiaodou.userCenter.request.BaseRequest;
import com.xiaodou.userCenter.request.RechargeAmountRequest;
import com.xiaodou.userCenter.request.RechargeOrderRequest;
import com.xiaodou.userCenter.response.BaseUserModel;
import com.xiaodou.userCenter.response.GoodsListResponse;
import com.xiaodou.userCenter.response.GoodsListResponse.Good;
import com.xiaodou.userCenter.util.WalletUtil;
import com.xiaodou.userCenter.vo.WalletGood;
import com.xiaodou.wallet.agent.constants.WalletConstant;
import com.xiaodou.wallet.agent.response.AccountAmountResponse;
import com.xiaodou.wallet.agent.response.RechargeAmountResponse;
import com.xiaodou.wallet.agent.response.RechargeOrderResponse;
import com.xiaodou.wallet.agent.service.WalletService;
import com.xiaodou.wallet.agent.util.AccountClassifyUtil;

@Service("userWalletService")
public class UserWalletService extends CheckLoginService {

    /**
     * 1. 获取商品列表
     * 
     * @param request
     * @return
     * @throws Exception
     */
    public GoodsListResponse goodsList(BaseRequest pojo) throws Exception {
        CheckLoginResult<BaseUserModel> checkResult = checkLoginWithBaseUserModel(pojo);
        GoodsListResponse response = new GoodsListResponse(ResultType.SUCCESS);
        if (!checkResult.isRetOk()) {
            response.setRetcode(checkResult.getResType().getCode());
            response.setRetdesc(checkResult.getResType().getMsg());
            response.setServerIp(checkResult.getResType().getServerIp());
            return response;
        }
        BaseUserModel user = checkResult.getModel();
        AccountAmountResponse wallet = WalletService.queryAccountAmount(
                WalletUtil.formatProductLine(), user.getId(), UcenterConstant.BUSINESS_TYPE_ZKJ,
                AccountClassifyUtil.getAccountClassifyByDesc(pojo.getClientType()));
        if (wallet.isRetOk()) response.setAccountAmount(wallet.getAccountAmount());
        IQueryParam param = new QueryParam();
        param.addInput("productLine", WalletUtil.formatProductLine());
        param.addInput("goodsStatus", WalletConstant.GOOD_STATUS_UP);
        param.addOutputs(CommUtil.getAllField(AccountWalletGood.class));
        Page<AccountWalletGood> goodList = ucenterServiceFacade.queryAccountWalletGoodByCond(param);
        if (null == goodList || goodList.getResult() == null || goodList.getResult().size() == 0) {
            response.setRetcode("40101");
            response.setRetdesc("商品列表为空");
            return response;
        }
        for (AccountWalletGood good : goodList.getResult()) {
            if (null == good) continue;
            response.getGoodList().add(new Good(good));
        }
        return response;
    }

    /**
     * 3.生成充值订单
     * 
     * @param request
     * @return
     * @throws Exception
     */
    public RechargeOrderResponse order(RechargeOrderRequest pojo) throws Exception {
        CheckLoginResult<BaseUserModel> checkResult = checkLoginWithBaseUserModel(pojo);
        if (!checkResult.isRetOk()) {
            RechargeOrderResponse response = new RechargeOrderResponse();
            response.setRetcode(checkResult.getResType().getCode());
            response.setRetdesc(checkResult.getResType().getMsg());
            response.setServerIp(checkResult.getResType().getServerIp());
            return response;
        }
        AccountWalletGood good = new AccountWalletGood();
        good.setId(pojo.getGoodId());
        good = ucenterServiceFacade.queryAccountWalletGoodById(good);
        if (null == good) {
            RechargeOrderResponse response = new RechargeOrderResponse();
            response.setRetcode("40204");
            response.setRetdesc("未找到商品信息");
            response.setServerIp(IPUtil.getServerIp());
            return response;
        }
        BaseUserModel user = checkResult.getModel();
        return WalletService.order(WalletUtil.formatProductLine(), user.getId(),
                UcenterConstant.BUSINESS_TYPE_ZKJ,
                AccountClassifyUtil.getAccountClassifyByDesc(pojo.getClientType()),
                new WalletGood(good));
    }

    /**
     * 4.发起充值
     * 
     * @param request
     * @return
     * @throws Exception
     */
    public RechargeAmountResponse rechargeAmount(RechargeAmountRequest pojo) throws Exception {
        CheckLoginResult<BaseUserModel> checkResult = checkLoginWithBaseUserModel(pojo);
        if (!checkResult.isRetOk()) {
            RechargeAmountResponse response = new RechargeAmountResponse();
            response.setRetcode(checkResult.getResType().getCode());
            response.setRetdesc(checkResult.getResType().getMsg());
            response.setServerIp(checkResult.getResType().getServerIp());
            return response;
        }
        BaseUserModel user = checkResult.getModel();
        return WalletService.rechargeAmount(WalletUtil.formatProductLine(), user.getId(),
                pojo.getBillNo(), pojo.getClientType(),
                PaymentProviderUtil.getPaymentProvider(pojo.getPayMethod()));
    }


}
