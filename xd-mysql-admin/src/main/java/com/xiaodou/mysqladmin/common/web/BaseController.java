package com.xiaodou.mysqladmin.common.web;

import java.beans.PropertyEditorSupport;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import com.google.common.collect.Maps;
import com.xiaodou.mysqladmin.common.persistence.Page;
import com.xiaodou.mysqladmin.common.utils.DateUtils;
import com.xiaodou.mysqladmin.common.utils.StringUtils;

public class BaseController {
  @InitBinder
  public void initBinder(WebDataBinder binder) {
    binder.registerCustomEditor(String.class, new PropertyEditorSupport() {
      public void setAsText(String text) {
        setValue((text == null) ? null : StringEscapeUtils.escapeHtml4(text.trim()));
      }

      public String getAsText() {
        Object value = getValue();
        return (value != null) ? value.toString() : "";
      }
    });
    binder.registerCustomEditor(Date.class, new PropertyEditorSupport() {
      public void setAsText(String text) {
        setValue(DateUtils.parseDate(text));
      }
    });
    binder.registerCustomEditor(Timestamp.class, new PropertyEditorSupport() {
      public void setAsText(String text) {
        Date date = DateUtils.parseDate(text);
        setValue((date == null) ? null : new Timestamp(date.getTime()));
      }
    });
  }

  public <T> Page<T> getPage(HttpServletRequest request) {
    int pageNo = 1;
    int pageSize = 20;
    String orderBy = "";
    String order = "asc";
    if (StringUtils.isNotEmpty(request.getParameter("page")))
      pageNo = Integer.valueOf(request.getParameter("page")).intValue();
    if (StringUtils.isNotEmpty(request.getParameter("rows")))
      pageSize = Integer.valueOf(request.getParameter("rows")).intValue();
    if (StringUtils.isNotEmpty(request.getParameter("sort")))
      orderBy = request.getParameter("sort").toString();
    if (StringUtils.isNotEmpty(request.getParameter("order")))
      order = request.getParameter("order").toString();
    return new Page<T>(pageNo, pageSize, orderBy, order);
  }

  public <T> Map<String, Object> getEasyUIData(Page<T> paramPage) {
    HashMap<String, Object> localHashMap = Maps.newHashMap();
    localHashMap.put("rows", paramPage.getResult());
    localHashMap.put("total", Long.valueOf(paramPage.getTotalCount()));
    localHashMap.put("columns", paramPage.getColumns());
    localHashMap.put("primaryKey", paramPage.getPrimaryKey());
    return localHashMap;
  }
}
