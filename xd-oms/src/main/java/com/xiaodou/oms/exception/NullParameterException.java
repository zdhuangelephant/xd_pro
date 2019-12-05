package com.xiaodou.oms.exception;

public class NullParameterException extends OrderRuntimeException
{

	public NullParameterException()
	{
		super();
	}

	public NullParameterException(String message, Throwable cause)
	{
		super(message, cause);
	}

	public NullParameterException(String message)
	{
		super(message);
	}

	public NullParameterException(Throwable cause)
	{
		super(cause);
	}
}