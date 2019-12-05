package com.xiaodou.wallet.agent.request;

import java.util.List;

import com.google.common.collect.Lists;


/**
 * @name AccountBillRequest CopyRright (c) 2017 by zhaodan
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年1月6日
 * @description 账单列表查询参数类
 * @version 1.0
 */
public class AccountBillRequest extends BusinessOperateRequest {

  private List<Integer> operType = Lists.newArrayList();
  private Integer pageNo;
  private Integer pageSize;

  public List<Integer> getOperType() {
    return operType;
  }

  public void setOperType(List<Integer> operType) {
    this.operType = operType;
  }

  public int getPageNo() {
    return pageNo;
  }

  public void setPageNo(Integer pageNo) {
    this.pageNo = pageNo;
  }

  public int getPageSize() {
    return pageSize;
  }

  public void setPageSize(Integer pageSize) {
    this.pageSize = pageSize;
  }

}
