package com.xiaodou.course.web.request.trade;

import com.xiaodou.course.web.request.BaseRequest;
import com.xiaodou.summer.validator.annotion.LegalValue.LegalValueList;
import com.xiaodou.summer.validator.annotion.NotEmpty;

/**
 * @name TradeDetailRequest CopyRright (c) 2017 by zhaodan
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年1月6日
 * @description 查询交易记录列表参数类
 * @version 1.0
 */
public class TradeDetailRequest extends BaseRequest {

  @NotEmpty
  @LegalValueList({"ios", "android"})
  private String clientType;
  private Integer pageNo = 1;
  private Integer size = 20;

  public String getClientType() {
    return clientType;
  }

  public void setClientType(String clientType) {
    this.clientType = clientType;
  }

  public Integer getPageNo() {
    return pageNo;
  }

  public void setPageNo(Integer pageNo) {
    this.pageNo = pageNo;
  }

  public Integer getSize() {
    return size;
  }

  public void setSize(Integer size) {
    this.size = size;
  }

}
