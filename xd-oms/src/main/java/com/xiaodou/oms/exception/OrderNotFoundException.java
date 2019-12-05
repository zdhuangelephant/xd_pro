package com.xiaodou.oms.exception;


/**
 * 
 * @Title:OrderNotFoundException.java
 * 
 * @Description:订单不存在异常类
 *
 * @author  zhaoyang
 * @date    Jan 26, 2014 10:29:40 AM
 * @version V1.0
 */

public class OrderNotFoundException extends OrderException
{
	private static final long serialVersionUID = 7879414451473203097L;
	
	public OrderNotFoundException()
	{
		super();
	}
	public OrderNotFoundException(String message, Throwable cause)
	{
		super(message, cause);
	}
	public OrderNotFoundException(String message)
	{
		super(message);
	}
	public OrderNotFoundException(Throwable cause)
	{
		super(cause);
	}

}
