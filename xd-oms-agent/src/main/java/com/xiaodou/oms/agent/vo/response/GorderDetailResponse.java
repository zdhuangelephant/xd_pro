package com.xiaodou.oms.agent.vo.response;

import com.xiaodou.oms.agent.model.Gorder;

/**
 * Created by zyp on 14-6-30.
 */
public class GorderDetailResponse extends BaseResponse {

  /** gorder信息 */
  private Gorder gorder;

  public Gorder getGorder() {
    return gorder;
  }

  public void setGorder(Gorder gorder) {
    this.gorder = gorder;
  }
}
