package com.xiaodou.oms.exception;

/**
 * 
 * @Title:OrderException.java
 * 
 * @Description:订单相关的业务逻辑异常
 *
 * @author  zhaoyang
 * @date    Jan 26, 2014 10:30:53 AM
 * @version V1.0
 */
public class OrderException extends OrderRuntimeException
{

	private static final long serialVersionUID = -8261081693335377371L;

	public OrderException()
	{
		super();
	}

	public OrderException(String message, Throwable cause)
	{
		super(message, cause);
	}

	public OrderException(String message)
	{
		super(message);
	}

	public OrderException(Throwable cause)
	{
		super(cause);
	}
}
