package com.xiaodou.server.mapi.response.trade;

import java.util.List;

import com.google.common.collect.Lists;
import com.xiaodou.server.mapi.response.selftaught.BaseResponse;
import com.xiaodou.summer.vo.out.ResultType;

/**
 * @name TradeDetailResponse CopyRright (c) 2017 by zhaodan
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年1月6日
 * @description 账单详情响应类
 * @version 1.0
 */
public class TradeDetailResponse extends BaseResponse {
  public TradeDetailResponse(ResultType type) {
    super(type);
  }

  public TradeDetailResponse() {}

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
