package com.xiaodou.server.mapi.util;

import com.xiaodou.server.mapi.response.ucenter.UserModelResponse;
import com.xiaodou.server.mapi.web.filter.CheckUserStatusFilter.LoginMod;

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
  private UserModelResponse user;
  private LoginMod headParam;
  private Throwable value;

  public LoginMod getHeadParam() {
    return headParam;
  }

  public void setHeadParam(LoginMod headParam) {
    this.headParam = headParam;
  }

  public UserModelResponse getUser() {
    return user;
  }

  public void setUser(UserModelResponse user) {
    this.user = user;
  }

  public Throwable getValue() {
    return value;
  }

  public void setValue(Throwable value) {
    this.value = value;
  }

  public Throwable getAndRemove() {
    Throwable res = value;
    value = null;
    return res;
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
    UserTokenWrapper ctx = localContext.get();
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
