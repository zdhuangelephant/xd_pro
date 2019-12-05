package com.xiaodou.oms.exception;

/**
 * 
 * @Title:ValidationException.java
 * 
 * @Description:参数验证失败异常类
 *
 * @author  zhaoyang
 * @date    Jan 26, 2014 10:23:33 AM
 * @version V1.0
 */
public class ValidationException extends OrderRuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8259354166816776843L;

	
	public ValidationException()
	{
		super();
	}
	public ValidationException(String message, Throwable cause)
	{
		super(message, cause);
	}
	public ValidationException(String message)
	{
		super(message);
	}
	public ValidationException(Throwable cause)
	{
		super(cause);
	}

	
 
}
