package com.xiaodou.oms.vo.result.order;

import com.xiaodou.oms.entity.order.Gorder;
import com.xiaodou.oms.vo.result.ResultInfo;
import com.xiaodou.oms.vo.result.ResultType;

/**
 * <p>查询Gorder详情VO</p>
 *
 * @author <a href="mailto:dan.zhao@elong.corp.com">zhaodan</a>
 * @version 1.0
 * @date 2014年6月27日
 */
public class GorderDetailVO extends ResultInfo {
  
  /**
   * gorder对象
   */
  private Gorder gorder;

  public GorderDetailVO(ResultType type) {
    super(type);
  }

  public Gorder getGorder() {
    return gorder;
  }

  public void setGorder(Gorder gorder) {
    this.gorder = gorder;
  }

}
