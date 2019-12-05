package com.xiaodou.ucerCenter.agent.util;

import com.xiaodou.userCenter.response.BaseUserModel;

/**
 * 存储本地变量-用户信息
 * 
 * @author bing.cheng
 * 
 */
public class UserTokenWrapper {
  private UserTokenWrapper() {}

  private static final ThreadLocal<UserTokenWrapper> localContext =
      new ThreadLocal<UserTokenWrapper>();

  static {
    initWrapper();
  }

  private BaseUserModel userModel;

  private String module;
  
  private String clientIp;
  
  private String deviceId;
  
  private String clientType;
  
  private String version;

  public String getClientIp() {
    return clientIp;
  }

  public void setClientIp(String clientIp) {
    this.clientIp = clientIp;
  }

  public String getDeviceId() {
    return deviceId;
  }

  public void setDeviceId(String deviceId) {
    this.deviceId = deviceId;
  }

  public String getClientType() {
    return clientType;
  }

  public void setClientType(String clientType) {
    this.clientType = clientType;
  }

  public String getVersion() {
    return version;
  }

  public void setVersion(String version) {
    this.version = version;
  }

  public String getModule() {
    return module;
  }

  public void setModule(String module) {
    this.module = module;
  }

  public BaseUserModel getUserModel() {
    return userModel;
  }

  public void setUserModel(BaseUserModel userModel) {
    this.userModel = userModel;
  }

  private String userToken;

  public String getUserToken() {
    return userToken;
  }

  public void setUserToken(String userToken) {
    this.userToken = userToken;
  }

  private Throwable value;

  public Throwable getValue() {
    return value;
  }

  public Throwable getAndRemove() {
    Throwable res = value;
    value = null;
    return res;
  }

  public void setValue(Throwable value) {
    this.value = value;
  }

  /**
   * 构造方法
   * 
   */
  public static void initWrapper() {
    UserTokenWrapper ctx = getWrapper();
    setWrapper(ctx);
  }

  /**
   * 获取包装器
   * 
   * @return ErrorsWrapper
   */
  public static UserTokenWrapper getWrapper() {
    UserTokenWrapper ctx = (UserTokenWrapper) localContext.get();
    if (ctx == null) {
      ctx = new UserTokenWrapper();
      localContext.set(ctx);
    }
    return ctx;
  }

  /**
   * 设置包装器
   * 
   * @param wrapper
   */
  public static void setWrapper(UserTokenWrapper wrapper) {
    localContext.set(wrapper);
  }
}
