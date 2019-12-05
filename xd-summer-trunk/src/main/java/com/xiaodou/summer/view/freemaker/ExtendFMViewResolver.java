package com.xiaodou.summer.view.freemaker;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.springframework.web.servlet.view.AbstractUrlBasedView;
import org.springframework.web.servlet.view.freemarker.FreeMarkerView;
import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver;

import com.google.common.collect.Maps;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.summer.sceduling.common.SummerCommonScheduledExecutor;
import com.xiaodou.summer.sceduling.concurrent.SummerScheduledTask;

/**
 * 2015-01-14
 * 
 * @author zhenpu.hou
 * 
 */
public class ExtendFMViewResolver extends FreeMarkerViewResolver {

  private String dformat = "yyyyMMdd";

  private SimpleDateFormat format;

  private String viewEncoding;

  private String baseOP;

  private String baseJsOP;

  private String baseCssOP;

  private String baseImgOP;

  public String getBaseJsOP() {
    return baseJsOP;
  }

  public void setBaseJsOP(String baseJsOP) {
    this.baseJsOP = baseJsOP;
  }

  public String getBaseCssOP() {
    return baseCssOP;
  }

  public void setBaseCssOP(String baseCssOP) {
    this.baseCssOP = baseCssOP;
  }

  public String getBaseImgOP() {
    return baseImgOP;
  }

  public void setBaseImgOP(String baseImgOP) {
    this.baseImgOP = baseImgOP;
  }

  public String getDformat() {
    return dformat;
  }

  public void setDformat(String dformat) {
    this.dformat = dformat;
  }

  public String getBaseOP() {
    return baseOP;
  }

  public void setBaseOP(String baseOP) {
    this.baseOP = baseOP;
  }

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
    if (null == format) {
      format = new SimpleDateFormat(dformat);
    }
    final FreeMarkerView buildView = (FreeMarkerView) super.buildView(viewName);
    if (StringUtils.isNotBlank(viewEncoding)) buildView.setEncoding(viewEncoding);
    Map<String, String> attr = Maps.newHashMap();
    attr.put("baseOP", baseOP);
    attr.put("baseJsOP", baseJsOP);
    attr.put("baseCssOP", baseCssOP);
    attr.put("baseImgOP", baseImgOP);
    attr.put("timeStamp", format.format(new Date()));
    buildView.setAttributesMap(attr);
    SummerCommonScheduledExecutor.getExecutor().scheduleWithFixedDelay(new SummerScheduledTask() {
      @Override
      public void doMain() {
        Map<String, String> attr = Maps.newHashMap();
        attr.put("timeStamp", format.format(new Date()));
        buildView.setAttributesMap(attr);
      }

      @Override
      public void onException(Throwable t) {
        LoggerUtil.error("刷新时间戳", t);
      }
    }, 0, 5, TimeUnit.MINUTES);
    return buildView;
  }



}
