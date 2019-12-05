package com.xiaodou.ms.service.admin;

import org.springframework.security.access.SecurityMetadataSource;
import org.springframework.security.access.intercept.AbstractSecurityInterceptor;
import org.springframework.security.access.intercept.InterceptorStatusToken;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;

import javax.servlet.*;
import java.io.IOException;

/**
 * Created by zyp on 14-9-3.
 * <p/>
 * 自定义过滤器
 */
public class AdminSecurityFilterInterceptor extends AbstractSecurityInterceptor implements Filter {

  private FilterInvocationSecurityMetadataSource mySecurityMetadataSource;

  public FilterInvocationSecurityMetadataSource getMySecurityMetadataSource() {
    return mySecurityMetadataSource;
  }

  public void setMySecurityMetadataSource(FilterInvocationSecurityMetadataSource mySecurityMetadataSource) {
    this.mySecurityMetadataSource = mySecurityMetadataSource;
  }

  @Override
  public Class<?> getSecureObjectClass() {
    return FilterInvocation.class;
  }

  @Override
  public SecurityMetadataSource obtainSecurityMetadataSource() {
    return this.mySecurityMetadataSource;
  }

  @Override
  public void init(FilterConfig filterConfig) throws ServletException {

  }

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
    FilterInvocation filterInvocation = new FilterInvocation(request, response, chain);
    invoke(filterInvocation);
  }

  @Override
  public void destroy() {

  }

  private void invoke(FilterInvocation filterInvocation) throws IOException, ServletException {
    InterceptorStatusToken token = null;
    token = super.beforeInvocation(filterInvocation);
    try {
      filterInvocation
      	.getChain().
      		doFilter(
      				filterInvocation.getRequest(), 
      				filterInvocation.getResponse()
      				);
    } finally {
      super.afterInvocation(token, null);
    }
  }
}
