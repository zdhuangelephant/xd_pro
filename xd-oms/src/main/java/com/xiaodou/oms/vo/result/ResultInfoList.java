package com.xiaodou.oms.vo.result;

import java.util.Collections;
import java.util.List;

public class ResultInfoList<T> extends ResultInfo {
  private int totalPage = 1;

  private int pageNo = 1;

  private long totalCount = 0;

  private List<T> list = Collections.emptyList();

  public ResultInfoList(ResultType valfail) {
    super(valfail);
  }

  public List<T> getList() {

    return this.list;
  }

  public void setList(List<T> list) {

    this.list = list;
  }

  public int getTotalPage() {
    return totalPage;
  }

  public void setTotalPage(int totalPage) {
    this.totalPage = totalPage;
  }

  public int getPageNo() {
    return pageNo;
  }

  public void setPageNo(int pageNo) {
    this.pageNo = pageNo;
  }

  public long getTotalCount() {
    return totalCount;
  }

  public void setTotalCount(long totalCount) {
    this.totalCount = totalCount;
  }
}
