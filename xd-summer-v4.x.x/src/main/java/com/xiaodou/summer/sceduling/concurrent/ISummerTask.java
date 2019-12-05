package com.xiaodou.summer.sceduling.concurrent;

/**
 * SummerTask接口
 * 定义Task行为
 * @author zhaodan
 *
 */
public interface ISummerTask extends Runnable {
	
	public void onException(Throwable t);
}