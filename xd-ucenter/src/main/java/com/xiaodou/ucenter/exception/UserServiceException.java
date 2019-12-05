package com.xiaodou.ucenter.exception;

public class UserServiceException extends Exception {

  /**
   * 
   */
  private static final long serialVersionUID = -4639247719406148500L;

  public UserServiceException() {
    super("用户中心Service层自定义异常");
  }

  public UserServiceException(String message) {
    super(String.format("用户中心Service层自定义异常:%s", message));
  }
}
