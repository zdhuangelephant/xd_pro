package com.xiaodou.oms.vo.input.order;

import java.util.Map;

import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.oms.statemachine.param.CoreParams;
import com.xiaodou.oms.vo.input.sign.BasePojo;
import com.xiaodou.summer.validator.annotion.NotEmpty;

/**
 * <p>触发状态机事件Pojo对象</p>
 *
 * @author <a href="mailto:dan.zhao@elong.corp.com">zhaodan</a>
 * @version 1.0
 * @date 2014年6月27日
 */
public class FirePojo extends BasePojo {
  
  @NotEmpty
  private CoreParams coreParams;
  
  private Map<String, Object> otherParams;

  public CoreParams getCoreParams() {
    return coreParams;
  }

  public void setCoreParams(CoreParams coreParams) {
    this.coreParams = coreParams;
  }

  public Map<String, Object> getOtherParams() {
    return otherParams;
  }

  public void setOtherParams(Map<String, Object> otherParams) {
    this.otherParams = otherParams;
  }

  @Override
  public String toString() {
    return FastJsonUtil.toJson(this);
  }
}
