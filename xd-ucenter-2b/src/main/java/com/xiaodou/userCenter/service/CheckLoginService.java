package com.xiaodou.userCenter.service;

import com.xiaodou.common.util.StringUtils;
import com.xiaodou.userCenter.model.UserModel;
import com.xiaodou.userCenter.request.BaseRequest;
import com.xiaodou.userCenter.response.BaseUserModel;
import com.xiaodou.userCenter.response.resultype.UcenterResType;

public class CheckLoginService extends BaseDaoService {

  protected CheckLoginResult<UserModel> checkLoginWithUserModel(BaseRequest pojo) throws Exception {
    CheckLoginResult<UserModel> result = new CheckLoginResult<UserModel>();
    // 判断token是否为空
    if (StringUtils.isBlank(pojo.getSessionToken())) {
      result.setResType(UcenterResType.NoTokenExisted);
    }
    result.setModel(queryUser(pojo));

    if (null == result.getModel()) {
      result.setResType(UcenterResType.UnAbleToken);
      return result;
    }
    if (StringUtils.isNotBlank(result.getModel().getUsedDeviceId())) {
      if (StringUtils.isBlank(pojo.getDeviceId())
          || !pojo.getDeviceId().equals(result.getModel().getUsedDeviceId())) {
        result.setResType(UcenterResType.UnLoginDevice);
        return result;
      }
    }
    return result;
  }

  protected CheckLoginResult<BaseUserModel> checkLoginWithBaseUserModel(BaseRequest pojo)
      throws Exception {
    CheckLoginResult<BaseUserModel> result = new CheckLoginResult<BaseUserModel>();
    // 判断token是否为空
    if (StringUtils.isBlank(pojo.getSessionToken())) {
      result.setResType(UcenterResType.NoTokenExisted);
    }
    result.setModel(decideRedisExsited(pojo));

    if (null == result.getModel()) {
      result.setResType(UcenterResType.UnAbleToken);
      return result;
    }
    if (StringUtils.isNotBlank(result.getModel().getUsedDeviceId())) {
      if (StringUtils.isBlank(pojo.getDeviceId())
          || !pojo.getDeviceId().equals(result.getModel().getUsedDeviceId())) {
        result.setResType(UcenterResType.UnLoginDevice);
        return result;
      }
    }
    return result;
  }

  public static class CheckLoginResult<T> {
    private UcenterResType resType;
    private T model;

    public boolean isRetOk() {
      return null == resType && null != model;
    }

    public UcenterResType getResType() {
      return resType;
    }

    public void setResType(UcenterResType resType) {
      this.resType = resType;
    }

    public T getModel() {
      return model;
    }

    public void setModel(T model) {
      this.model = model;
    }

  }

}
