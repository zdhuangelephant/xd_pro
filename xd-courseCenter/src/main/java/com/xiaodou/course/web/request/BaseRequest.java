package com.xiaodou.course.web.request;

import com.xiaodou.common.util.StringUtils;
import com.xiaodou.ucerCenter.agent.util.UserTokenWrapper;
import com.xiaodou.userCenter.module.jz.response.JzUserInfoResponse;
import com.xiaodou.userCenter.response.BaseUserModel;
import org.springframework.validation.Errors;

import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.summer.vo.in.BaseValidatorPojo;

public class BaseRequest extends BaseValidatorPojo {

  /**
   * 获取用户Id
   * 
   * @return
   */
  public String getUid() {
    BaseUserModel userModel = UserTokenWrapper.getWrapper().getUserModel();
    if (null != userModel && null != userModel.getId()) return userModel.getId().toString();
    return StringUtils.EMPTY;
  }

  /**
   * 获取用户模块
   * 
   * @return
   */
  public String getModule() {
    if (null != UserTokenWrapper.getWrapper().getModule())
      return UserTokenWrapper.getWrapper().getModule();
    return StringUtils.EMPTY;
  }

  /**
   * 获取类型code
   * 
   * @return
   */
  public String getTypeCode() {
    BaseUserModel userModel = UserTokenWrapper.getWrapper().getUserModel();
    if (null != userModel && userModel instanceof JzUserInfoResponse) {
      String typeCode = ((JzUserInfoResponse) userModel).getTypeCode();
      if (StringUtils.isNotBlank(typeCode)) return typeCode;
    }
    return StringUtils.EMPTY;
  }

  @Override
  public Errors validate() {
    return super.validate();
  }

  @Override
  public String toString() {
    return FastJsonUtil.toJson(this);
  }
}
