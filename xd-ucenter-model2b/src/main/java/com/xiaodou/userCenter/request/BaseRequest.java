package com.xiaodou.userCenter.request;

import org.springframework.validation.Errors;

import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.summer.validator.annotion.LegalValue.LegalValueList;
import com.xiaodou.summer.validator.annotion.NotEmpty;
import com.xiaodou.summer.vo.in.BaseValidatorPojo;
import com.xiaodou.userCenter.util.SaltUtil;

public class BaseRequest extends BaseValidatorPojo {

  /** 设备号 */
  @NotEmpty
  private String deviceId;

  /** 设备ip */
  @NotEmpty
  private String clientIp;

  /** sessionToken值 */
  private String sessionToken;

  /** 所属模块 */
  @NotEmpty
  private String module;

  /** version app版本号 */
  @NotEmpty
  private String version;

  /** clientType 设备类型 */
  @NotEmpty
  @LegalValueList({"ios", "android", "web", "other"})
  private String clientType;

  public BaseRequest() {}

  public <T extends BaseRequest> BaseRequest(T t) {
    this.clientIp = t.getClientIp();
    this.clientType = t.getClientType();
    this.deviceId = t.getDeviceId();
    this.module = t.getModule();
    this.sessionToken = t.getSessionToken();
    this.version = t.getVersion();
  }

  @Override
  public Errors validate() {
    return super.validate();
  }

  public String getVersion() {
    return version;
  }

  public void setVersion(String version) {
    this.version = version;
  }

  public String getClientType() {
    return clientType;
  }

  public void setClientType(String clientType) {
    this.clientType = clientType;
  }

  public String getModule() {
    return module;
  }

  public void setModule(String module) {
    this.module = module;
  }


  public String getDeviceId() {
    return deviceId;
  }

  public void setDeviceId(String deviceId) {
    this.deviceId = deviceId;
  }

  public String getClientIp() {
    return clientIp;
  }

  public void setClientIp(String clientIp) {
    this.clientIp = clientIp;
  }

  public String getSessionToken() {
    return sessionToken;
  }

  public void setSessionToken(String sessionToken) {
    this.sessionToken = sessionToken;
  }

  public final String toString0() {
    return String
        .format(
            "{RequestPojo:{deviceId:%s,clientIp:%s,sessionToken:%s,module:%s,version:%s,clientType:%s}}",
            SaltUtil.saltInfo(deviceId), clientIp, SaltUtil.saltInfo(sessionToken), module,
            version, clientType);
  }

  @Override
  public String toString() {
    return FastJsonUtil.toJson(this);
  }
}
