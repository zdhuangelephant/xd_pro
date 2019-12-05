package com.xiaodou.queue.annotation;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.ReportAsSingleViolation;
import javax.validation.constraints.Size;

import com.xiaodou.common.util.StringUtils;
import com.xiaodou.queue.enums.WeightEnum;

/**
 * @name @see com.xiaodou.queue.annotation.Processer.java
 * @CopyRright (c) 2015 by XiaoDou NetWork Technology
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2015年8月22日
 * @description 声明为一个消息处理者
 * @version 1.0
 */
@Documented
@Constraint(validatedBy = {})
@Target({TYPE})
@Retention(RUNTIME)
@ReportAsSingleViolation
@Size(min = 1)
public @interface ProcesserHandler {
  WeightEnum weight();

  String queueName() default StringUtils.EMPTY;
}
