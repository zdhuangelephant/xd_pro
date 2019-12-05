package com.xiaodou.oms.vo.result.order;

import com.xiaodou.oms.vo.result.ResultInfo;
import com.xiaodou.oms.vo.result.ResultType;

public class ShopTagVO extends ResultInfo{
  
  public ShopTagVO(ResultType type){
    super(type);
  }
  
  private String tag;

  public String getTag() {
    return tag;
  }

  public void setTag(String tag) {
    this.tag = tag;
  }

}
