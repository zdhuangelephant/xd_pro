package com.xiaodou.im.agent.qq.request;


import com.xiaodou.im.agent.qq.anno.TransField;
import com.xiaodou.im.agent.qq.constant.Http;

import java.lang.reflect.Field;

/**
 * Date: 2014/12/11
 * Time: 14:05
 *
 * @author Tian.Dong
 */
public abstract class BaseRequest {
  public abstract String getUrl();

  public abstract Http getHttpMethod();

  public String getParams() throws Exception {
    StringBuilder sb = new StringBuilder();
    for (Field field : this.getClass().getDeclaredFields()) {
      TransField transField = field.getAnnotation(TransField.class);
      if (transField != null) {
        field.setAccessible(true);
        sb.append(transField.value() == null ? field.getName() : transField.value());
        sb.append("=");
        sb.append(field.get(this).toString());
        sb.append("&");
      }
    }
    if (sb.length() != 0) {
      sb.deleteCharAt(sb.length() - 1);
    }
    return sb.toString();
  }
}
