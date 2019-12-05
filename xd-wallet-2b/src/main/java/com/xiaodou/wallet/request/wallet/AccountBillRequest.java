package com.xiaodou.wallet.request.wallet;

import java.util.List;

import com.google.common.collect.Lists;
import com.xiaodou.wallet.request.BusinessOperateRequest;

/**
 * @name AccountBillRequest 
 * CopyRright (c) 2017 by zhaodan
 *
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年1月6日
 * @description 账单列表查询参数类
 * @version 1.0
 */
public class AccountBillRequest extends BusinessOperateRequest {

  private List<String> operType = Lists.newArrayList(); 
  private int pageNo = 1;
  private int pageSize = 20;
  
  public List<String> getOperType() {
    return operType;
  }
  public void setOperType(List<String> operType) {
    this.operType = operType;
  }
  public int getPageNo() {
    return pageNo;
  }
  public void setPageNo(int pageNo) {
    this.pageNo = pageNo;
  }
  public int getPageSize() {
    return pageSize;
  }
  public void setPageSize(int pageSize) {
    this.pageSize = pageSize;
  }
  
}
