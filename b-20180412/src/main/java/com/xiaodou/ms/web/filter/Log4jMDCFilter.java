package com.xiaodou.ms.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.MDC;

import com.xiaodou.common.util.WebUtils;

/**
 * 从web环境中获取用户信息，供log4j输出使用
 * 
 */

public class Log4jMDCFilter implements Filter {

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {

		HttpServletRequest req = (HttpServletRequest) request;

		MDC.put("clientIP", WebUtils.getRemoteIpAddress(req));
		chain.doFilter(request, response);
	}

	public void init(FilterConfig Config) throws ServletException {

	}

	public void destroy() {
	}
}
