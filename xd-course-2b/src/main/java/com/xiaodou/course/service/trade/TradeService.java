package com.xiaodou.course.service.trade;

import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.xiaodou.course.constant.CourseConstant;
import com.xiaodou.course.service.AbstractService;
import com.xiaodou.course.web.request.trade.TradeDetailRequest;
import com.xiaodou.course.web.response.trade.TradeDetailResponse;
import com.xiaodou.course.web.response.trade.TradeDetailResponse.TradeInfo;
import com.xiaodou.summer.vo.out.ResultType;
import com.xiaodou.wallet.agent.enums.WalletOper;
import com.xiaodou.wallet.agent.model.AccountWalletBill;
import com.xiaodou.wallet.agent.response.AccountBillResponse;
import com.xiaodou.wallet.agent.service.WalletService;
import com.xiaodou.wallet.agent.util.AccountClassifyUtil;

/**
 * @name TradeService CopyRright (c) 2017 by zhaodan
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年1月6日
 * @description 交易记录操作Service
 * @version 1.0
 */
@Service("tradeService")
public class TradeService extends AbstractService {

  public TradeDetailResponse tradeDetail(TradeDetailRequest pojo) {
    TradeDetailResponse res = new TradeDetailResponse(ResultType.SUCCESS);
    AccountBillResponse response =
        WalletService.queryAccountBill(formatProductLine(), pojo.getUid(),
            CourseConstant.BUSINESS_TYPE_ZKJ,
            AccountClassifyUtil.getAccountClassifyByDesc(pojo.getClientType()),
            Lists.newArrayList(WalletOper.Pay, WalletOper.Recharge), pojo.getPageNo(),
            pojo.getSize());
    if (!response.isRetOk()) {
      res = new TradeDetailResponse(ResultType.SYSFAIL);
      res.setRetdesc(response.getRetdesc());
      return res;
    }
    if (null == response.getBillList() || response.getBillList().size() == 0) return res;
    for (AccountWalletBill bill : response.getBillList())
      res.getTradeList().add(new TradeInfo(bill));
    return res;
  }

}
