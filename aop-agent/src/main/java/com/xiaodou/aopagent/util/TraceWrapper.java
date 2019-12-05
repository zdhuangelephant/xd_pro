package com.xiaodou.aopagent.util;

import com.xiaodou.aopagent.filter.TraceFilter.TraceModel;

/**
 * 存储本地变量-进程变量
 * 
 * @author zhouhuan
 * 
 */
public class TraceWrapper {

	private TraceWrapper() {
	}

	private static final ThreadLocal<TraceWrapper> localContext = new ThreadLocal<TraceWrapper>();

	static {
		initWrapper();
	}
	private TraceModel traceParam=new TraceModel();
	private Throwable value;

	public TraceModel getTraceParam() {
		return traceParam;
	}

	public void setTraceParam(TraceModel traceParam) {
		this.traceParam = traceParam;
	}

	public Throwable getValue() {
		return value;
	}

	public void setValue(Throwable value) {
		this.value = value;
	}

	public Throwable getAndRemove() {
		Throwable res = value;
		value = null;
		return res;
	}

	/**
	 * 构造方法
	 * 
	 */
	public static void initWrapper() {
		TraceWrapper ctx = getWrapper();
		setWrapper(ctx);
	}

	/**
	 * 获取包装器
	 * 
	 * @return ErrorsWrapper
	 */
	public static TraceWrapper getWrapper() {
		TraceWrapper ctx = localContext.get();
		if (ctx == null) {
			ctx = new TraceWrapper();
			localContext.set(ctx);
		}
		return ctx;
	}

	/**
	 * 设置包装器
	 * 
	 * @param wrapper
	 */
	public static void setWrapper(TraceWrapper wrapper) {
		localContext.set(wrapper);
	}

}
