package com.xiaodou.forum.request.forum;

import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.summer.vo.in.BaseValidatorPojo;
import com.xiaodou.ucerCenter.agent.util.UserTokenWrapper;
import com.xiaodou.userCenter.response.BaseUserModel;

/**
 * 
 * @author weichao.zhai
 * @time 2015年4月6日 下午4:52:08
 */
public class BaseRequest extends BaseValidatorPojo {

  public BaseUserModel getUserModel() {
    return UserTokenWrapper.getWrapper().getUserModel();
  }

  public String getDeviceId() {
    if (null != UserTokenWrapper.getWrapper().getUserModel())
      return UserTokenWrapper.getWrapper().getUserModel().getUsedDeviceId();
    return UserTokenWrapper.getWrapper().getDeviceId();
  }

  public String getToken() {
    return UserTokenWrapper.getWrapper().getUserToken();
  }

  public String getClientIp() {
    if (null != UserTokenWrapper.getWrapper().getUserModel())
      return UserTokenWrapper.getWrapper().getUserModel().getLatestDeviceIp();
    return UserTokenWrapper.getWrapper().getClientIp();
  }

  @Override
  public String toString() {
    return FastJsonUtil.toJson(this);
  }

}
