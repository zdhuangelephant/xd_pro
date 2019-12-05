package com.xiaodou.oms.exception;

public class RefundException extends OrderRuntimeException
{

	public RefundException()
	{
		super();
	}

	public RefundException(String message, Throwable cause)
	{
		super(message, cause);
	}

	public RefundException(String message)
	{
		super(message);
	}

	public RefundException(Throwable cause)
	{
		super(cause);
	}

}
