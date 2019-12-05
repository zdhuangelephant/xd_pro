package com.xiaodou.ms.web.filter;

import com.xiaodou.common.util.StringUtils;
import org.springframework.web.servlet.view.AbstractUrlBasedView;
import org.springframework.web.servlet.view.freemarker.FreeMarkerView;
import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver;

/**
 * 2015-01-14
 * 
 * @author zhenpu.hou
 * 
 */
public class ExtendFMViewResolver extends FreeMarkerViewResolver {

  private String viewEncoding;

  public String getViewEncoding() {
    return viewEncoding;
  }

  public void setViewEncoding(String viewEncoding) {
    this.viewEncoding = viewEncoding;
  }

  public ExtendFMViewResolver() {
    setViewClass(requiredViewClass());
  }

  @Override
  protected AbstractUrlBasedView buildView(String viewName) throws Exception {
    FreeMarkerView buildView = (FreeMarkerView) super.buildView(viewName);
    if (StringUtils.isNotBlank(viewEncoding)) {
      buildView.setEncoding(viewEncoding);
    }
    return buildView;
  }

}
