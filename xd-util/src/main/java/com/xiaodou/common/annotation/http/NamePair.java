package com.xiaodou.common.annotation.http;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import com.xiaodou.common.util.StringUtils;

/**
 * @name @see cpm.xiaodou.push.agent.annotation.NamePair.java
 * @CopyRright (c) 2015 by XiaoDou NetWork Technology
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2015年8月16日
 * @description 标记该字段为http请求键值对字段
 * @version 1.0
 */
@Target({FIELD, PARAMETER})
@Retention(RUNTIME)
public @interface NamePair {

  /**
   * @return 字段别名
   */
  String alias() default StringUtils.EMPTY;

  /**
   * @return 排序字段
   */
  int order() default Integer.MAX_VALUE;

}
