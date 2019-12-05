package com.xiaodou.amqp.exception;

/**
 * JAMQP异常捕捉类
 * @author heshixiong
 */
public class AmqpClientException extends Exception{
	
	private static final long serialVersionUID = 1L;
	public AmqpClientException() {
		super();
	}
	public AmqpClientException(String errorMsg){
		super(errorMsg);
	}
	public AmqpClientException(String errorMsg, Throwable t){
      super(errorMsg, t);
  }
}
