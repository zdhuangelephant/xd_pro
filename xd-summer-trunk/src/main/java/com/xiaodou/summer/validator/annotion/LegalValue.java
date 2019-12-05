// $Id: NotEmpty.java 19327 2010-04-30 08:11:15Z hardy.ferentschik $
package com.xiaodou.summer.validator.annotion;

import static com.xiaodou.summer.validator.enums.ValueScope.EQ;
import static java.lang.annotation.ElementType.FIELD;
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

/**
 * 合法值标签 value指定合法的值,scope可指定范围(默认ValueScope.EQ)
 * 
 * @author <a href="mailto:dan.zhao@elong.corp.com">zhaodan</a>
 * @version 1.0
 * @date 2014年6月11日
 */
@Documented
@Constraint(validatedBy = {})
@Target({FIELD, PARAMETER})
@Retention(RUNTIME)
@ReportAsSingleViolation
@NotNull
@Size(min = 1)
public @interface LegalValue {
  String message() default "Err : UnLegalValue.";

  /**
   * @name EnumLegalValueList 
   * @CopyRright (c) 2015 by <a href=mailto:zhaodan@corp.51xiaodou.com>zhaodan</a>
   *
   * @author <a href=mailto:zhaodan@corp.51xiaodou.com>zhaodan</a>
   * @date 2015年7月20日
   * @description Add合法值列表标签,适用于合法值枚举类型的场景
   * @version 1.0
   * @tags 如果你想尝试优化这段代码,请你将浪费的时间记录在下面,让以后尝试优化的人作为一个参考.
   * @waste
   */
  @Target({FIELD, PARAMETER})
  @Retention(RUNTIME)
  @Documented
  public @interface EnumLegalValue {
    Class<? extends Enum<?>> enumt();
  }


  /**
   * 合法值列表标签,适用于合法值为一组数值的场景
   * 
   * @author <a href="mailto:dan.zhao@elong.corp.com">zhaodan</a>
   * @version 1.0
   * @date 2014年6月11日
   */
  @Target({FIELD, PARAMETER})
  @Retention(RUNTIME)
  @Documented
  public @interface LegalValueList {
    String[] value() default {};
  }

  /**
   * 合法值范围标签,适用于合法值为某一范围的场景 (主要用于场景 a<x<b)
   * 
   * @author <a href="mailto:dan.zhao@elong.corp.com">zhaodan</a>
   * @version 1.0
   * @date 2014年6月11日
   */
  @Target({FIELD, PARAMETER})
  @Retention(RUNTIME)
  @Documented
  public @interface AllLegalValueList {
    LegalValue lowerValue();

    LegalValue upperValue();
  }

  /**
   * 合法值范围标签,适用于合法值为某一范围的场景 (主要用于场景 x>a||x<b)
   * 
   * @author <a href="mailto:dan.zhao@elong.corp.com">zhaodan</a>
   * @version 1.0
   * @date 2014年6月11日
   */
  @Target({FIELD, PARAMETER})
  @Retention(RUNTIME)
  @Documented
  public @interface OrLegalValueList {
    LegalValue lowerValue();

    LegalValue upperValue();
  }

  String value() default "";

  ValueScope scope() default EQ;
}
