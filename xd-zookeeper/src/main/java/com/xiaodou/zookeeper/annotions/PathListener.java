package com.xiaodou.zookeeper.annotions;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.ReportAsSingleViolation;
import javax.validation.constraints.Size;

import com.xiaodou.zookeeper.enums.ListenerType;

@Documented
@Constraint(validatedBy = {})
@Target({TYPE})
@Retention(RUNTIME)
@ReportAsSingleViolation
@Size(min = 1)
public @interface PathListener {
  /**
   * 监听路径
   */
  String path();

  /**
   * 监听类型
   */
  ListenerType type() default ListenerType.Normal;

  /**
   * 是否压缩数据
   */
  boolean compressed() default false;

  /**
   * 是否缓存
   */
  boolean cached() default false;
}
