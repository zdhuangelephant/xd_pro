package com.xiaodou.payment.exception;

public class TokenException extends Exception {
  private static final long serialVersionUID = 1L;

  public TokenException(String exceptionInfo) {
    super(exceptionInfo);
  }
}
