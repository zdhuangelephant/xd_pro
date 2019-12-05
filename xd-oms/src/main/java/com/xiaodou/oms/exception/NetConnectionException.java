package com.xiaodou.oms.exception;

public class NetConnectionException extends OrderRuntimeException
{

	public NetConnectionException()
	{
		super();
	}

	public NetConnectionException(Exception exception)
	{
		super(exception);
	}

	public NetConnectionException(String msg)
	{
		super(msg);
	}

	public NetConnectionException(String msg, Exception e)
	{
		super(msg, e);
	}

}
