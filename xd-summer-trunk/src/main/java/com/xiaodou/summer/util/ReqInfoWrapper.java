package com.xiaodou.summer.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 存储本地变量-用户信息
 * 
 * @author bing.cheng
 *
 */
public class ReqInfoWrapper {
	private ReqInfoWrapper() {
	}

	private static final ThreadLocal<ReqInfoWrapper> localContext = new ThreadLocal<ReqInfoWrapper>();

	static {
		initWrapper();
	}

	private HttpServletRequest req;
	
	private HttpServletResponse res;
	
	public HttpServletRequest getReq() {
		return req;
	}

	public void setReq(HttpServletRequest req) {
		this.req = req;
	}

	public HttpServletResponse getRes() {
		return res;
	}

	public void setRes(HttpServletResponse res) {
		this.res = res;
	}

	/**
	 * 构造方法
	 * 
	 */
	public static void initWrapper() {
		ReqInfoWrapper ctx = getWrapper();
		setWrapper(ctx);
	}

	/**
	 * 获取包装器
	 * 
	 * @return ErrorsWrapper
	 */
	public static ReqInfoWrapper getWrapper() {
		ReqInfoWrapper ctx = (ReqInfoWrapper) localContext.get();
		if (ctx == null) {
			ctx = new ReqInfoWrapper();
			localContext.set(ctx);
		}
		return ctx;
	}

	/**
	 * 设置包装器
	 * 
	 * @param wrapper
	 */
	public static void setWrapper(ReqInfoWrapper wrapper) {
		localContext.set(wrapper);
	}
}
