package com.xiaodou.userCenter.request;

import org.springframework.validation.Errors;

import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.summer.validator.annotion.LegalValue.LegalValueList;
import com.xiaodou.summer.validator.annotion.NotEmpty;
import com.xiaodou.summer.vo.in.BaseValidatorPojo;
import com.xiaodou.userCenter.util.SaltUtil;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
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
  
  private String traceId;

  /**
   * @description 获取地域，因为工作量太大，先占时还传递module
   * @author <a href="mailto:lidehong@corp.51xiaodou.com">lidehong</a>
   * @Date 2018年4月19日
   * @return
   */
  public String getRegion() {
    return module;
  }
  
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
