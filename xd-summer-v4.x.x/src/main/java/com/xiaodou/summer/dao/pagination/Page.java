package com.xiaodou.summer.dao.pagination;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

/**
 * @author 开发支持平台
 * @param <T>
 */
public class Page<T> implements Serializable {

  /**
   * @Fields serialVersionUID : TODO
   */

  private static final long serialVersionUID = 1L;

  public static final int DEFAULT_PAGESIZE = 10;

  protected List<T> result = Collections.emptyList();

  protected long totalCount = 0;

  protected int pageNo = 1;

  protected int totalPage = 1;

  protected int pageSize = 0;

  public Page() {

    super();
    this.pageSize = Page.DEFAULT_PAGESIZE;
  }

  public int getPageSize() {

    return this.pageSize;
  }

  public Page(int pageSize) {

    this.pageSize = pageSize;
  }

  public Page(int pageNo, int pageSize) {

    this.pageNo = pageNo;
    this.pageSize = pageSize;
  }

  public List<T> getResult() {

    return this.result;
  }

  public void setResult(List<T> result) {
    this.result = result;
  }

  public long getTotalCount() {

    return this.totalCount;
  }

  public void setTotalCount(int totalCount) {
    this.totalCount = totalCount;
  }

  public int getPageNo() {

    if (this.pageNo <= 0) {
      this.pageNo = 1;
    }
    return this.pageNo;
  }

  public void setPageNo(int pageNo) {

    this.pageNo = pageNo;
  }

  public int getTotalPage() {

    return this.totalPage;
  }

  public void setTotalPage(int totalPage) {

    this.totalPage = totalPage;
  }

  public void setTotalCount(long totalCount) {
    this.totalCount = totalCount;
  }

  public void setPageSize(int pageSize) {
    this.pageSize = pageSize;
  }

  public int getSkipResults() {
    int skipResults = (this.getPageNo() - 1) * pageSize;
    if (this.getPageNo() < 1) {
      skipResults = 0;
    }
    return skipResults;
  }

}
