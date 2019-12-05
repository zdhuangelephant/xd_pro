package com.xiaodou.summer.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

public class CRLFFilter implements Filter {
  @Override
  public void destroy() {

  }

  @Override
  public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
      throws IOException, ServletException {

    HttpServletResponse response = new CRLFFilterResponseWrapper((HttpServletResponse) res);
    chain.doFilter(req, response);
  }

  @Override
  public void init(FilterConfig config) throws ServletException {

  }
}
