package com.xiaodou.oms.exception;

public class NullOrderException extends OrderRuntimeException
{

	public NullOrderException()
	{
		super();
	}

	public NullOrderException(String message, Throwable cause)
	{
		super(message, cause);
	}

	public NullOrderException(String message)
	{
		super(message);
	}

	public NullOrderException(Throwable cause)
	{
		super(cause);
	}
}