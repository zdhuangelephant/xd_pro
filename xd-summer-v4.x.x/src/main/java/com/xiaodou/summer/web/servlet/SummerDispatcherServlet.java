package com.xiaodou.summer.web.servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.DispatcherServlet;

import com.xiaodou.summer.support.SummerXmlApplicationContext;

public class SummerDispatcherServlet extends DispatcherServlet {

  /** serialVersionUID */
  private static final long serialVersionUID = 954508414859347653L;

  @Override
  public Class<?> getContextClass() {
    return SummerXmlApplicationContext.class;
  }

  @Override
  protected void noHandlerFound(HttpServletRequest request, HttpServletResponse response)
      throws Exception {
    super.noHandlerFound(request, response);
  }

}