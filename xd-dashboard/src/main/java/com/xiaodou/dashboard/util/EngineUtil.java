package com.xiaodou.dashboard.util;

import java.io.File;
import java.util.List;

import com.google.common.collect.Lists;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.dashboard.prop.ExceptionMessageProp;

/**
 * @name @see com.xiaodou.dashboard.util.EngineUtil.java
 * @CopyRright (c) 2015 by XiaoDou NetWork Technology
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2015年11月24日
 * @description 解析引擎用Util方法类
 * @version 1.0
 */
public class EngineUtil {

  /**
   * 验证属性值不空
   */
  public static String validateNotBlank(String value, String errCode, String name) {
    if (StringUtils.isBlank(value))
      throw new RuntimeException(ExceptionMessageProp.getErrMessage(errCode, name));
    return value;
  }

  /**
   * 获取事件上下文列表
   * 
   * @return
   */
  public static List<String> fileList(String envPath) {
    String basePath = EngineUtil.class.getClassLoader().getResource("").getPath();
    File file = new File(basePath + envPath);
    if (!file.exists()) return null;
    if (file.isDirectory()) return Lists.newArrayList(file.list());
    if (file.isFile()) return Lists.newArrayList(file.getName());
    return null;
  }

}
