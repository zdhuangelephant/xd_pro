package com.xiaodou.oms.vo.result.pay;

import com.xiaodou.oms.entity.order.PayRecord;
import com.xiaodou.oms.vo.result.PageInfo;
import com.xiaodou.oms.vo.result.ResultInfo;
import com.xiaodou.oms.vo.result.ResultType;

import java.util.List;

/**
 * Date: 2014/7/3
 * Time: 14:05
 *
 * @author Tian.Dong
 */
public class PayRecordListVO extends ResultInfo {

  public PayRecordListVO(ResultType type) {
    super(type);
  }

  /**
   * 分页信息
   */
  private PageInfo page;

  /**
   * 查询结果集
   */
  private List<PayRecord> list;

  public PageInfo getPage() {
    return page;
  }

  public void setPage(PageInfo page) {
    this.page = page;
  }

  public List<PayRecord> getList() {
    return list;
  }

  public void setList(List<PayRecord> list) {
    this.list = list;
  }
}
