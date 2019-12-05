package com.xiaodou.im.agent.qq.anno;

import java.lang.annotation.*;

/**
 * Date: 2014/12/11
 * Time: 14:25
 *
 * @author Tian.Dong
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
@Documented
public @interface TransField {
  String value() default "";
}
