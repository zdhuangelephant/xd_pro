package com.xiaodou.server.mapi.request;

import static com.xiaodou.server.mapi.constant.SelfTaughtConstant.MODULE;

import java.util.Map;

import org.springframework.validation.Errors;

import com.alibaba.fastjson.annotation.JSONField;
import com.google.common.collect.Maps;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.server.mapi.response.ucenter.UserModelResponse;
import com.xiaodou.server.mapi.util.UserTokenWrapper;
import com.xiaodou.server.mapi.web.filter.CheckUserStatusFilter.LoginPar;
import com.xiaodou.summer.vo.in.BaseValidatorPojo;

public class MapiBaseRequest extends BaseValidatorPojo {

  public String getUid() {
    return getUserFromWrapper().getId();
  }

  // @JSONField(deserialize = false, serialize = false)
  public String getModule() {
    String module = MODULE;
    if(StringUtils.isEmpty(getVersion())) {
        return MODULE;
      }
    int compareTo = getVersion().compareTo("1.5.0");
    if (!(compareTo < 0)) {
      module = getUserFromWrapper().getRegion();
      //1、老用户用新版本
      //2、用户未登陆
      if(StringUtils.isEmpty(module)) {
        module = MODULE;
      }
    }
    return module;
  }

  public String getRegionFromUser() {
    return getUserFromWrapper().getRegion();
  }

  public String getUserType() {
    return getUserFromWrapper().getUserType();
  }

  @JSONField(deserialize = false, serialize = false)
  public String getTypeCode() {
    return getUserFromWrapper().getMajor();
  }

  public String getVersion() {
    return UserTokenWrapper.getWrapper().getHeadParam().getVersion();
  }

  public String getTraceId() {
    return UserTokenWrapper.getWrapper().getHeadParam().getTraceId();
  }

  public String getClientType() {
    return UserTokenWrapper.getWrapper().getHeadParam().getClientType();
  }


  @JSONField(deserialize = false, serialize = false)
  public String getStrUserFromWrapper() {
    UserModelResponse user = UserTokenWrapper.getWrapper().getUser();
    if (null != user && null != user.getId()) {
      return FastJsonUtil.toJson(user);
    }
    return StringUtils.EMPTY;
  }

  @JSONField(deserialize = false, serialize = false)
  public UserModelResponse getUserFromWrapper() {
    UserModelResponse baseUserModel = UserTokenWrapper.getWrapper().getUser();
    if (null != baseUserModel && null != baseUserModel.getId()) {
      return baseUserModel;
    }
    return new UserModelResponse();
  }

  public String getDeviceId() {
    return UserTokenWrapper.getWrapper().getHeadParam().getDeviceId();
  }

  public String getClientIp() {
    return UserTokenWrapper.getWrapper().getHeadParam().getClientIp();
  }

  @JSONField(deserialize = false, serialize = false)
  public Map<String, Object> getUserBaseParams() {
    Map<String, Object> params = Maps.newHashMap();
    if (StringUtils.isNotEmpty(getModule())) {
      params.put(LoginPar.module.toString(), getModule());
    }
    params.put(LoginPar.deviceId.toString(), UserTokenWrapper.getWrapper().getHeadParam()
        .getDeviceId());
    params.put(LoginPar.sessionToken.toString(), UserTokenWrapper.getWrapper().getHeadParam()
        .getSessionToken());
    params.put(LoginPar.clientIp.toString(), UserTokenWrapper.getWrapper().getHeadParam()
        .getClientIp());
    params.put(LoginPar.version.toString(), UserTokenWrapper.getWrapper().getHeadParam()
        .getVersion());
    params.put(LoginPar.clientType.toString(), UserTokenWrapper.getWrapper().getHeadParam()
        .getClientType());
    params.put(LoginPar.traceId.toString(), UserTokenWrapper.getWrapper().getHeadParam()
        .getTraceId());
    return params;
  }

  @Override
  public String toString() {
    return FastJsonUtil.toJson(this);
  }

  @Override
  public Errors validate() {
    Errors error = super.validate();
    if (StringUtils.isBlank(getDeviceId())) {
      error.rejectValue("deviceId", null, null, "deviceId is empty.");
    }
    if (StringUtils.isBlank(getClientIp())) {
      error.rejectValue("clientIp", null, null, "clientIp is empty.");
    }
    if (StringUtils.isBlank(getClientType())) {
      error.rejectValue("clientType", null, null, "clientType is empty.");
    }
    if (StringUtils.isBlank(getVersion())) {
      error.rejectValue("version", null, null, "version is empty.");
    }
    return error;
  }
}
