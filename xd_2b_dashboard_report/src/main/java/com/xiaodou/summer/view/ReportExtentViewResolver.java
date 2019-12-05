package com.xiaodou.summer.view;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import lombok.Data;
import lombok.EqualsAndHashCode;

import org.springframework.web.servlet.view.AbstractUrlBasedView;
import org.springframework.web.servlet.view.freemarker.FreeMarkerView;
import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver;

import com.google.common.collect.Maps;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.summer.sceduling.common.SummerCommonScheduledExecutor;
import com.xiaodou.summer.sceduling.concurrent.SummerScheduledTask;

/**
 * @name @see com.xiaodou.summer.view.ReportExtentViewResolver.java
 * @CopyRright (c) 2018 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2018年7月25日
 * @description 慕享云测评模板页解析类
 * @version 1.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ReportExtentViewResolver extends FreeMarkerViewResolver {

  /** siteTitle 站点首页名 */
  private String siteTitle;
  /** taughtUnitName 第一级单位名称 */
  private String taughtUnitName;
  /** chiefUnitName 第二级单位名称 */
  private String chiefUnitName;
  /** pilotUnitName 第三级单位名称 */
  private String pilotUnitName;

  private String dformat = "yyyyMMdd";

  private SimpleDateFormat format;

  private String viewEncoding;

  private String baseOP;

  private String baseJsOP;

  private String baseCssOP;

  private String baseImgOP;

  public ReportExtentViewResolver() {
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
    attr.put("siteTitle", siteTitle);
    attr.put("taughtUnitName", taughtUnitName);
    attr.put("chiefUnitName", chiefUnitName);
    attr.put("pilotUnitName", pilotUnitName);
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
