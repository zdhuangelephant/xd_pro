package com.xiaodou.course.web.response.trade;

import java.util.List;

import com.google.common.collect.Lists;
import com.xiaodou.common.util.DateUtil;
import com.xiaodou.course.common.enums.NotesResType;
import com.xiaodou.course.constant.CourseConstant;
import com.xiaodou.course.web.response.BaseResponse;
import com.xiaodou.course.web.response.resultType.ProductResType;
import com.xiaodou.summer.vo.out.ResultType;
import com.xiaodou.wallet.agent.enums.WalletOper;
import com.xiaodou.wallet.agent.model.AccountWalletBill;

/**
 * @name TradeDetailResponse CopyRright (c) 2017 by zhaodan
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年1月6日
 * @description 账单详情响应类
 * @version 1.0
 */
public class TradeDetailResponse extends BaseResponse {

  public TradeDetailResponse(ResultType resultType) {
    super(resultType);
  }

  public TradeDetailResponse(ProductResType resultType) {
    super(resultType);
  }

  public TradeDetailResponse(NotesResType resultType) {
    super(resultType);
  }

  /** tradeList 交易列表 */
  private List<TradeInfo> tradeList = Lists.newArrayList();


  public List<TradeInfo> getTradeList() {
    return tradeList;
  }

  public void setTradeList(List<TradeInfo> tradeList) {
    this.tradeList = tradeList;
  }

  /**
   * @name TradeInfo CopyRright (c) 2017 by zhaodan
   * 
   * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
   * @date 2017年1月6日
   * @description 交易信息
   * @version 1.0
   */
  public static class TradeInfo {
    public TradeInfo(AccountWalletBill bill) {
      if (WalletOper.Recharge.getCode() == bill.getOperateType()) {
        this.tradeType = CourseConstant.TRADE_TYPE_INCOME;
        this.tradeDesc = bill.getOperateDesc();
        this.tradeAmount = CourseConstant.D_FORMAT.format(bill.getOperateCount());
      } else if (WalletOper.Pay.getCode() == bill.getOperateType()) {
        this.tradeType = CourseConstant.TRADE_TYPE_OUTCOME;
        this.tradeDesc = bill.getOperateTag();
        this.tradeAmount = CourseConstant.D_FORMAT.format(bill.getOperateAmount());
      }
      this.billNo = bill.getId().toString();
      this.tradeNo = bill.getTradeNo();
      this.tradeTime = DateUtil.SDF_FULL.format(bill.getOperateTime());
    }

    private String billNo;
    private String tradeNo;
    private String tradeType;
    private String tradeAmount;
    private String tradeDesc;
    private String tradeTime;

    public String getBillNo() {
      return billNo;
    }

    public void setBillNo(String billNo) {
      this.billNo = billNo;
    }

    public String getTradeNo() {
      return tradeNo;
    }

    public void setTradeNo(String tradeNo) {
      this.tradeNo = tradeNo;
    }

    public String getTradeType() {
      return tradeType;
    }

    public void setTradeType(String tradetType) {
      this.tradeType = tradetType;
    }

    public String getTradeAmount() {
      return tradeAmount;
    }

    public void setTradeAmount(String tradeAmount) {
      this.tradeAmount = tradeAmount;
    }

    public String getTradeDesc() {
      return tradeDesc;
    }

    public void setTradeDesc(String tradeDesc) {
      this.tradeDesc = tradeDesc;
    }

    public String getTradeTime() {
      return tradeTime;
    }

    public void setTradeTime(String tradeTime) {
      this.tradeTime = tradeTime;
    }

  }


}
