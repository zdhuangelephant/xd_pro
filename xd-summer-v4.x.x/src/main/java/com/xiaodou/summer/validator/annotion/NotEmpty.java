// $Id: NotEmpty.java 19327 2010-04-30 08:11:15Z hardy.ferentschik $
package com.xiaodou.summer.validator.annotion;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.ReportAsSingleViolation;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.xiaodou.summer.validator.enums.ValueScope;
import static com.xiaodou.summer.validator.enums.ValueScope.EQ;

/**
 * 字段非空标签
 * 指定field时,value不能为空,可指定值范围(scope)
 * 1.field为空,则此字段不能为空
 * 2.field不为空时,当field为指定值时,此字段不能为空
 * 
 * @author <a href="mailto:dan.zhao@elong.corp.com">zhaodan</a>
 * @version 1.0
 * @date 2014年6月11日
 */
@NotEmpty("Null")
@Documented
@Constraint(validatedBy = {})
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER})
@Retention(RUNTIME)
@ReportAsSingleViolation
@NotNull
@Size(min = 1)
public @interface NotEmpty {
  String message() default "Err : Can't Empty.";

  /**
   * 当规定字段所有都满足条件时,该字段不能为空
   * 
   * @author <a href="mailto:dan.zhao@elong.corp.com">zhaodan</a>
   * @version 1.0
   * @date 2014年6月11日
   */
  @Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER})
  @Retention(RUNTIME)
  @Documented
  public @interface AllNotEmptyList {
    NotEmpty[] value();
  }

  /**
   * 当规定字段中任意一个满足条件时,该字段不能为空
   * 
   * @author <a href="mailto:dan.zhao@elong.corp.com">zhaodan</a>
   * @version 1.0
   * @date 2014年6月11日
   */
  @Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER})
  @Retention(RUNTIME)
  @Documented
  public @interface OrNotEmptyList {
    NotEmpty[] value();
  }

  String field() default "";

  String value() default "";

  ValueScope scope() default EQ;

}
