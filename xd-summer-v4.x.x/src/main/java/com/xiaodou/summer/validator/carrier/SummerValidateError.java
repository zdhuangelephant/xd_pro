package com.xiaodou.summer.validator.carrier;

import java.util.List;

import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;

import com.xiaodou.common.util.StringUtils;

public class SummerValidateError extends BeanPropertyBindingResult {

  private String baseFieldName = StringUtils.EMPTY;

  public SummerValidateError(Object target, String objectName, String baseFieldName) {
    super(target, objectName);
    this.baseFieldName = baseFieldName;
  }

  public SummerValidateError(Object target, String objectName, boolean autoGrowNestedPaths,
      int autoGrowCollectionLimit) {
    super(target, objectName, autoGrowNestedPaths, autoGrowCollectionLimit);
  }

  /** serialVersionUID */
  private static final long serialVersionUID = 6747038830972507202L;

  @Override
  public void addAllErrors(Errors errors) {
    List<ObjectError> allErrors = errors.getAllErrors();
    if (null == allErrors || allErrors.size() == 0) return;
    for (ObjectError e : allErrors)
      addError(e);
  }

  @Override
  public void rejectValue(String field, String errorCode, String defaultMessage) {
    if (StringUtils.isNotBlank(baseFieldName))
      defaultMessage = baseFieldName + "." + defaultMessage;
    super.rejectValue(field, errorCode, defaultMessage);
  }

  @Override
  public void rejectValue(String field, String errorCode, Object[] errorArgs, String defaultMessage) {
    if (StringUtils.isNotBlank(baseFieldName))
      defaultMessage = baseFieldName + "." + defaultMessage;
    super.rejectValue(field, errorCode, errorArgs, defaultMessage);
  }

  @Override
  public void reject(String errorCode, Object[] errorArgs, String defaultMessage) {
    if (StringUtils.isNotBlank(baseFieldName))
      defaultMessage = baseFieldName + "." + defaultMessage;
    super.reject(errorCode, errorArgs, defaultMessage);
  }

}
