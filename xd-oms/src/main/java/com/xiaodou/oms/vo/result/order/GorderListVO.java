package com.xiaodou.oms.vo.result.order;

import java.util.List;

import com.xiaodou.oms.entity.order.Gorder;
import com.xiaodou.oms.vo.result.PageInfo;
import com.xiaodou.oms.vo.result.ResultInfo;
import com.xiaodou.oms.vo.result.ResultType;

/**
 * <p>
 * 查询Gorder列表VO
 * </p>
 * 
 * @author <a href="mailto:dan.zhao@elong.corp.com">zhaodan</a>
 * @version 1.0
 * @date 2014年7月1日
 */
public class GorderListVO extends ResultInfo {
  
  public GorderListVO(ResultType type){
    super(type);
  }
  
  /**
   * 分页信息
   */
  private PageInfo page;
  
  /**
   * 查询结果集
   */
  private List<Gorder> list;

  public PageInfo getPage() {
    return page;
  }

  public void setPage(PageInfo page) {
    this.page = page;
  }

  public List<Gorder> getList() {
    return list;
  }

  public void setList(List<Gorder> list) {
    this.list = list;
  }

}