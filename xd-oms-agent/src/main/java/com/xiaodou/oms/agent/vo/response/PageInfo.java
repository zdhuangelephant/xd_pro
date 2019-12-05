package com.xiaodou.oms.agent.vo.response;


/**
 * <p>查询结果分页信息</p>
 *
 * @author <a href="mailto:dan.zhao@elong.corp.com">zhaodan</a>
 * @version 1.0
 * @date 2014年7月1日
 */
public class PageInfo {
  
  /**
   * 单页记录数
   */
  private Integer pageSize;
  
  /**
   * 页码
   */
  private Integer pageNo;
  
  /**
   * 总页数
   */
  private Integer totalPage;
  
  /**
   * 总记录数
   */
  private Long totalCount;

  public Integer getPageSize() {
    return pageSize;
  }

  public void setPageSize(Integer pageSize) {
    this.pageSize = pageSize;
  }

  public Integer getPageNo() {
    return pageNo;
  }

  public void setPageNo(Integer pageNo) {
    this.pageNo = pageNo;
  }

  public Integer getTotalPage() {
    return totalPage;
  }

  public void setTotalPage(Integer totalPage) {
    this.totalPage = totalPage;
  }

  public Long getTotalCount() {
    return totalCount;
  }

  public void setTotalCount(Long totalCount) {
    this.totalCount = totalCount;
  }

}
