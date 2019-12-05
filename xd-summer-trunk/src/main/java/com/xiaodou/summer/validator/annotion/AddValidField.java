package com.xiaodou.summer.validator.annotion;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.ReportAsSingleViolation;
import javax.validation.constraints.Size;

import com.xiaodou.summer.validator.enums.AnnotationType;

/**
 * <p>
 * 标识继承自父类的某个字段需要追加某些验证处理, 优先级要低于@see {@link OverComeField}
 * </p>
 * 
 * @author <a href="mailto:dan.zhao@elong.corp.com">zhaodan</a>
 * @version 1.0
 * @date 2014年11月21日
 */
@NotEmpty("Null")
@Documented
@Constraint(validatedBy = {})
@Target({TYPE})
@Retention(RUNTIME)
@ReportAsSingleViolation
@Size(min = 1)
public @interface AddValidField {

  @Target({TYPE})
  @Retention(RUNTIME)
  @Documented
  public @interface AddValidFieldList {
    AddValidField[] value();
  }

  String[] field() default {};

  AnnotationType annotiation();
}
