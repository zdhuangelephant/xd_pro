package com.xiaodou.st.dashboard.web.filter;

import org.springframework.web.servlet.view.AbstractUrlBasedView;
import org.springframework.web.servlet.view.freemarker.FreeMarkerView;

import com.xiaodou.common.util.StringUtils;
import com.xiaodou.summer.view.freemaker.ExtendFMViewResolver;

/**
 * 2016-08-02 15:12:22
 * 
 * @author lidehong
 * 
 */
public class ExtendFMViewResolverT extends ExtendFMViewResolver {

  private String viewEncoding;

  public String getViewEncoding() {
    return viewEncoding;
  }

  public void setViewEncoding(String viewEncoding) {
    this.viewEncoding = viewEncoding;
  }

  public ExtendFMViewResolverT() {
    setViewClass(requiredViewClass());
  }

  @Override
  protected AbstractUrlBasedView buildView(String viewName) throws Exception {
    FreeMarkerView buildView = (FreeMarkerView) super.buildView(viewName);
    if (StringUtils.isNotBlank(viewEncoding)) buildView.setEncoding(viewEncoding);
    return buildView;
  }

}
