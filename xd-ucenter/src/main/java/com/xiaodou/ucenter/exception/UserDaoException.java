package com.xiaodou.ucenter.exception;

public class UserDaoException extends Exception {

  /**
   * 序列化id
   */
  private static final long serialVersionUID = 7679420223329521141L;

  public UserDaoException() {
    super("用户中心Dao层自定义异常");
  }

  public UserDaoException(String message) {
    super(String.format("用户中心Dao层自定义异常:%s", message));
  }

}
