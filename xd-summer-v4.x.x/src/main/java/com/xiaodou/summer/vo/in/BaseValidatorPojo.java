package com.xiaodou.summer.vo.in;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.google.common.collect.Sets;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.summer.validator.IValidator;
import com.xiaodou.summer.validator.annotion.AddValidField;
import com.xiaodou.summer.validator.annotion.AddValidField.AddValidFieldList;
import com.xiaodou.summer.validator.annotion.LegalValue;
import com.xiaodou.summer.validator.annotion.LegalValue.AllLegalValueList;
import com.xiaodou.summer.validator.annotion.LegalValue.LegalValueList;
import com.xiaodou.summer.validator.annotion.LegalValue.OrLegalValueList;
import com.xiaodou.summer.validator.annotion.NotEmpty;
import com.xiaodou.summer.validator.annotion.NotEmpty.AllNotEmptyList;
import com.xiaodou.summer.validator.annotion.NotEmpty.OrNotEmptyList;
import com.xiaodou.summer.validator.annotion.OverComeField;
import com.xiaodou.summer.validator.annotion.OverComeField.OverComeFieldList;
import com.xiaodou.summer.validator.annotion.ValidateEntity;
import com.xiaodou.summer.validator.carrier.SummerValidateError;
import com.xiaodou.summer.validator.enums.AnnotationType;
import com.xiaodou.summer.validator.enums.ValueScope;

/**
 * BaseValidatorPojo-验证基类Pojo
 * 
 * @author <a href="mailto:dan.zhao@elong.corp.com">zhaodan</a>
 * @version 1.0
 * @date 2014年6月11日
 */
@SuppressWarnings("rawtypes")
public class BaseValidatorPojo implements Validator, IValidator {

  private enum InnerParam {
    /** _overComeMap 跳过验证属性 */
    _overComeMap,
    /** _addValidMap 追加验证属性 */
    _addValidMap;
    private static Map<String, InnerParam> _innerParamMap = new HashMap<String, InnerParam>() {
      /** serialVersionUID 序列化ID */
      private static final long serialVersionUID = 1L;

      public Map<String, InnerParam> initThis() {
        for (InnerParam _innerParam : InnerParam.values()) {
          this.put(_innerParam.toString(), _innerParam);
        }
        return this;
      }
    }.initThis();

    public static InnerParam getByName(String name) {
      return _innerParamMap.get(name);
    }
  }

  private Map<AnnotationType, Set<String>> _overComeMap =
      new HashMap<AnnotationType, Set<String>>() {
        private static final long serialVersionUID = 1L;

        public HashMap<AnnotationType, Set<String>> initThis() {
          /**
           * NotEmpty忽略验证域集合
           */
          this.put(AnnotationType.NotEmpty, new HashSet<String>());
          /**
           * LegalValue忽略验证域集合
           */
          this.put(AnnotationType.LegalValue, new HashSet<String>());
          /**
           * ValidateEntity忽略验证域集合
           */
          this.put(AnnotationType.ValidateEntity, new HashSet<String>());
          return this;
        }
      }.initThis();

  private Map<AnnotationType, Set<String>> _addValidMap =
      new HashMap<AnnotationType, Set<String>>() {
        private static final long serialVersionUID = 1L;

        public HashMap<AnnotationType, Set<String>> initThis() {
          /**
           * NotEmpty增加验证域集合
           */
          this.put(AnnotationType.NotEmpty, new HashSet<String>());
          /**
           * LegalValue增加验证域集合
           */
          this.put(AnnotationType.LegalValue, new HashSet<String>());
          /**
           * ValidateEntity增加验证域集合
           */
          this.put(AnnotationType.ValidateEntity, new HashSet<String>());
          return this;
        }
      }.initThis();

  @Override
  public boolean supports(Class<?> clazz) {
    return this.getClass().equals(clazz);
  }

  public Errors validate() {
    return validate(this, StringUtils.EMPTY);
  }

  public Errors validate(Object object, String baseFieldName) {
    return this.validate(object.getClass(), new SummerValidateError(object, object.getClass()
        .getSimpleName(), baseFieldName));
  }

  private boolean validateClassType(Class<?> clazz) {
    return null != clazz && !Object.class.equals(clazz);
  }

  /**
   * 根据异常载体进行信息有效性验证 方法不建议使用,存在隐患
   * 
   * @param errors 异常信息载体
   * @return
   */
  @Deprecated
  @Override
  public void validate(Errors errors) {
    this.validate(this.getClass(), errors);
    if (validateClassType(this.getClass().getClass().getSuperclass()))
      validate(this.getClass().getClass().getSuperclass(), errors);
  }

  private Errors validate(Class<?> clazz, Errors errors) {
    setOverCome(clazz);
    setAddValid(clazz);
    for (Field argument : clazz.getDeclaredFields()) {
      // 内置属性,直接跳过
      if (InnerParam.getByName(argument.getName()) != null) break;
      // 待验证实体校验
      validate_ValidateEntity(argument, errors);
      // 非空校验
      validate_NotEmpty(argument, errors);
      // 合法值校验
      validate_LegalValue(argument, errors);
    }
    while (validateClassType(clazz.getSuperclass()))
      return validate(clazz.getSuperclass(), errors);
    return errors;
  }

  /**
   * 根据传入对象和异常载体进行信息有效性验证 方法不建议使用,存在隐患
   */
  @Override
  @Deprecated
  public void validate(Object object, Errors errors) {
    validate(object.getClass(), errors);
  }

  /**
   * 待验证实体校验
   * 
   * @param argument 实体域
   * @param object 待验证实体
   * @param errors 异常载体
   * @return 验证结果
   */
  private void validate_ValidateEntity(Field argument, Errors errors) {
    // 设置为忽略则跳过验证
    if (getSkipSet(AnnotationType.ValidateEntity).contains(argument.getName())) return;
    if (null != argument.getAnnotation(ValidateEntity.class)
        || getAddSet(AnnotationType.ValidateEntity).contains(argument.getName())) {
      try {
        argument.setAccessible(true);
        Object targetField = errors.getFieldValue(argument.getName());
        if (null == targetField) return;
        // 如果该域是一个Collection对象, 遍历集合验证每个对象
        if (targetField instanceof Collection) {
          for (Object target : (Collection<?>) targetField) {
            errors.addAllErrors(validate(target, argument.getName()));
          }
          // 如果该域是一个Map对象, 遍历结果集验证没个value
        } else if (targetField instanceof Map) {
          for (Object target : ((Map<?, ?>) targetField).values()) {
            errors.addAllErrors(validate(target, argument.getName()));
          }
        }
        errors.addAllErrors(validate(targetField, argument.getName()));
      } catch (Exception e) {} finally {
        argument.setAccessible(false);
      }
    }
  }

  /**
   * 验证字段非空
   * 
   * @param argument 字段
   * @param errors 异常载体
   */
  private void validate_NotEmpty(Field argument, Errors errors) {
    // 设置为忽略则跳过验证
    if (getSkipSet(AnnotationType.NotEmpty).contains(argument.getName())) return;
    // 标志该字段是否需要被验证
    boolean flag = false;
    // 不需要验证 且 NotEmpty标签不为空,验证NotEmpty
    if (!flag && null != argument.getAnnotation(NotEmpty.class)) {
      NotEmpty anno = argument.getAnnotation(NotEmpty.class);
      flag = validate_NotEmpty_Boolean(anno, errors);
    }
    // 不需要验证 且 OrNotEmptyList标签不为空,验证OrNotEmptyList
    if (!flag && null != argument.getAnnotation(OrNotEmptyList.class)) {
      OrNotEmptyList anno = argument.getAnnotation(OrNotEmptyList.class);
      flag = validate_OrNotEmptyList(anno, errors);
    }
    // 不需要验证 且 NotEmpty标签不为空,验证NotEmpty
    if (!flag && null != argument.getAnnotation(AllNotEmptyList.class)) {
      AllNotEmptyList anno = argument.getAnnotation(AllNotEmptyList.class);
      flag = validate_AllNotEmptyList(anno, errors);
    }
    // 需要验证 或者 在追加验证列表中时,进行验证
    if (flag || getAddSet(AnnotationType.NotEmpty).contains(argument.getName())) {
      Set<Class<?>> fileTypeSet = Sets.newHashSet(argument.getType().getInterfaces());
      Object fieldValue = errors.getFieldValue(argument.getName());
      if (fileTypeSet.contains(Collection.class)) {
        if (null == fieldValue || ((Collection) fieldValue).size() == 0)
          errors.rejectValue(argument.getName(), null, null, argument.getName() + " is empty.");
      } else if (fileTypeSet.contains(Map.class)) {
        if (null == fieldValue || ((Map) fieldValue).size() == 0)
          errors.rejectValue(argument.getName(), null, null, argument.getName() + " is empty.");
      }
      ValidationUtils.rejectIfEmptyOrWhitespace(errors, argument.getName(), null,
          argument.getName() + " is empty.");
    }
  }

  /**
   * 验证字段非空(与关系)
   * 
   * @param argument 字段
   * @param errors 异常载体
   * @return 该字段是否需要继续验证
   */
  private boolean validate_AllNotEmptyList(AllNotEmptyList anno, Errors errors) {
    boolean flag = true;
    for (NotEmpty notEmpty : anno.value()) {
      flag = flag && validate_NotEmpty_Boolean(notEmpty, errors);
    }
    return flag;
  }

  /**
   * 验证字段非空
   * 
   * @param argument 字段
   * @param errors 异常载体
   */
  private boolean validate_NotEmpty_Boolean(NotEmpty anno, Errors errors) {
    String dependArum = anno.field();
    if (StringUtils.isNotBlank(dependArum)) {
      String dependValu = anno.value();
      Set<Class<?>> fileTypeSet = Sets.newHashSet(errors.getFieldType(dependArum).getInterfaces());
      Object dependArumValue = errors.getFieldValue(dependArum);
      // 1. 如果anno中依赖值为空,则当实际依赖field值不为空时需要进行校验
      if (StringUtils.isBlank(dependValu)) {
        if (fileTypeSet.contains(Collection.class)) {
          return null != dependArumValue && ((Collection) dependArumValue).size() > 0;
        } else if (fileTypeSet.contains(Map.class)) {
          return null != dependArumValue && ((Map) dependArumValue).size() > 0;
        }
        return null != dependArumValue && StringUtils.isNotBlank(dependArumValue.toString());
      }
      // 2. 当聲明依赖field值为null时判断依赖field,当value为null时校验
      else if ("null".equals(dependValu.toLowerCase())) {
        if (fileTypeSet.contains(Collection.class)) {
          return null == dependArumValue || ((Collection) dependArumValue).size() == 0;
        } else if (fileTypeSet.contains(Map.class)) {
          return null == dependArumValue || ((Map) dependArumValue).size() == 0;
        }
        return null == dependArumValue || StringUtils.isBlank(dependArumValue.toString());
      }
      // 3. 如果依赖字段的依赖值不为null,需要判断依赖字段的值
      else if (null != dependArumValue && StringUtils.isNotBlank(dependArumValue.toString())) {
        return anno.scope().compare(dependArumValue.toString(), dependValu);
      }
      // 否则,不需要校验
      else
        return false;
    }
    return true;
  }

  // /**
  // * 验证字段非空(或关系)
  // *
  // * @param argument 字段
  // * @param errors 异常载体
  // * @return 该字段是否需要继续验证
  // */
  // private boolean _validate_OrNotEmptyList(OrNotEmptyList anno, Field argument, Errors errors) {
  // boolean flag = false;
  // for (NotEmpty notEmpty : anno.value()) {
  // flag = flag || validate_NotEmpty_Boolean(notEmpty, errors);
  // }
  // if (flag) {
  // ValidationUtils.rejectIfEmpty(errors, argument.getName(), null, argument.getName()
  // + " is empty.");
  // }
  // return !flag;
  // }

  /**
   * 验证字段非空(或关系)
   * 
   * @param argument 字段
   * @param errors 异常载体
   * @return 该字段是否需要继续验证
   */
  private boolean validate_OrNotEmptyList(OrNotEmptyList anno, Errors errors) {
    boolean flag = false;
    for (NotEmpty notEmpty : anno.value()) {
      flag = flag || validate_NotEmpty_Boolean(notEmpty, errors);
    }
    return flag;
  }

  // /**
  // * 验证字段非空
  // *
  // * @param argument 字段
  // * @param errors 异常载体
  // * @return 该字段是否需要继续验证
  // */
  // private boolean _validate_NotEmpty(NotEmpty anno, Field argument, Errors errors) {
  // // 非空依赖字段
  // String dependArum = anno.field();
  // // 如果标志了依赖字段, 则需要校验依赖字段的值
  // if (StringUtils.isNotBlank(dependArum)) {
  // // 非空依赖字段依赖值
  // String dependValu = anno.value();
  // // 非空依赖字段实际值
  // Object dependArumValue = errors.getFieldValue(dependArum);
  // // 如果依赖字段的实际值不为null,需要判断依赖字段的值
  // if (null != dependArumValue && StringUtils.isNotBlank(dependArumValue.toString())) {
  // // 如果依赖字段依赖值为空或者实际值等于依赖值, 则需要进行非空校验
  // if (StringUtils.isBlank(dependValu)
  // || anno.scope().compare(dependArumValue.toString(), dependValu)) {
  // ValidationUtils.rejectIfEmpty(errors, argument.getName(), null, argument.getName()
  // + " is empty.");
  // return false;
  // }
  // // 如果依赖字段实际值为null, 需要判断依赖字段的依赖值, 只有当依赖值明确为null时, 则需要进行非空校验
  // } else if (StringUtils.isNotBlank(dependValu) && "null".equals(dependValu.toLowerCase())) {
  // ValidationUtils.rejectIfEmpty(errors, argument.getName(), null, argument.getName()
  // + " is empty.");
  // return false;
  // }
  // return true;
  // // 如果没有标志依赖字段, 则直接进行非空校验
  // } else {
  // ValidationUtils.rejectIfEmpty(errors, argument.getName(), null, argument.getName()
  // + " is empty.");
  // return false;
  // }
  // }

  /**
   * 验证字段合法值
   * 
   * @param argument 字段
   * @param errors 异常载体
   */
  private void validate_LegalValue(Field argument, Errors errors) {
    // 设置为忽略则跳过验证
    if (getSkipSet(AnnotationType.LegalValue).contains(argument.getName())) return;
    boolean flag = true;
    if (flag && argument.getAnnotation(LegalValueList.class) != null
        && validateLegelValue(argument, errors)) {
      LegalValueList anno = argument.getAnnotation(LegalValueList.class);
      flag = validate_LegalValueList(anno, argument, errors);
    }
    if (flag && argument.getAnnotation(AllLegalValueList.class) != null
        && validateLegelValue(argument, errors)) {
      AllLegalValueList anno = argument.getAnnotation(AllLegalValueList.class);
      flag = validate_AllLegalValueList(anno, argument, errors);
    }
    if (flag && argument.getAnnotation(OrLegalValueList.class) != null
        && validateLegelValue(argument, errors)) {
      OrLegalValueList anno = argument.getAnnotation(OrLegalValueList.class);
      flag = validate_OrLegalValueList(anno, argument, errors);
    }
    if (flag && argument.getAnnotation(LegalValue.class) != null
        && validateLegelValue(argument, errors)) {
      LegalValue anno = argument.getAnnotation(LegalValue.class);
      validate_LegalValue(anno, argument, errors);
    }
  }

  private boolean validateLegelValue(Field argument, Errors errors) {
    Object value = errors.getFieldValue(argument.getName());
    return null != value && StringUtils.isNotBlank(value.toString());
  }

  /**
   * 验证字段合法值(单LegalValue)
   * 
   * @param anno LegalValue实例
   * @param argument 字段
   * @param errors 异常载体
   */
  private boolean validate_LegalValueList(LegalValueList anno, Field argument, Errors errors) {
    boolean flag = false;
    if (null != anno.value() && anno.value().length > 0) {
      List<String> valueLst = Arrays.asList(anno.value());
      Object value = errors.getFieldValue(argument.getName());
      if (null != value) flag = flag || valueLst.contains(value.toString());
    }
    if (!flag) {
      errors.rejectValue(argument.getName(), null, null, argument.getName()
          + " has a unvalid value, plz check it.");
    }
    return flag;
  }

  /**
   * 验证字段合法值(AllLegalValueList)
   * 
   * @param anno LegalValue实例
   * @param argument 字段
   * @param errors 异常载体
   */
  private boolean validate_AllLegalValueList(final AllLegalValueList anno, Field argument,
      Errors errors) {
    boolean flag = true;
    if (null != anno.lowerValue() && null != anno.upperValue()) {
      flag =
          validate_LegalValue_Boolean(get_LegalValue_Lower(anno.lowerValue().value()), argument,
              errors)
              && validate_LegalValue_Boolean(get_LegalValue_Upper(anno.upperValue().value()),
                  argument, errors);
    }
    if (!flag) {
      errors.rejectValue(argument.getName(), null, null, argument.getName()
          + " has a unvalid value, plz check it.");
    }
    return flag;
  }

  /**
   * 验证字段合法值(OrLegalValueList)
   * 
   * @param anno LegalValue实例
   * @param argument 字段
   * @param errors 异常载体
   */
  private boolean validate_OrLegalValueList(final OrLegalValueList anno, Field argument,
      Errors errors) {
    boolean flag = true;
    if (null != anno.lowerValue() && null != anno.upperValue()) {
      flag =
          validate_LegalValue_Boolean(get_LegalValue_Lower(anno.lowerValue().value()), argument,
              errors)
              || validate_LegalValue_Boolean(get_LegalValue_Upper(anno.upperValue().value()),
                  argument, errors);
    }
    if (!flag) {
      errors.rejectValue(argument.getName(), null, null, argument.getName()
          + " has a unvalid value, plz check it.");
    }
    return flag;
  }

  private LegalValue get_LegalValue_Lower(final String value) {
    return get_LegalValue(value, ValueScope.GT);
  }

  private LegalValue get_LegalValue_Upper(final String value) {
    return get_LegalValue(value, ValueScope.LT);
  }

  private LegalValue get_LegalValue(final String value, final ValueScope scope) {
    return new LegalValue() {
      @Override
      public Class<? extends Annotation> annotationType() {
        return LegalValue.class;
      }

      @Override
      public String value() {
        return value;
      }

      @Override
      public ValueScope scope() {
        return scope;
      }

      @Override
      public String message() {
        return "Err : UnLegalValue.";
      }
    };
  }

  /**
   * 验证字段合法值(单LegalValue)
   * 
   * @param anno LegalValue实例
   * @param argument 字段
   * @param errors 异常载体
   */
  private boolean validate_LegalValue_Boolean(LegalValue anno, Field argument, Errors errors) {
    Object value = errors.getFieldValue(argument.getName());
    String legalValue = anno.value();
    if (StringUtils.isNotBlank(legalValue)) {
      return anno.scope().compare(value.toString(), legalValue);
    }
    return null == value || StringUtils.isBlank(value.toString());
  }

  /**
   * 验证字段合法值(单LegalValue)
   * 
   * @param anno LegalValue实例
   * @param argument 字段
   * @param errors 异常载体
   * @return 该字段是否需要继续验证
   */
  private boolean validate_LegalValue(LegalValue anno, Field argument, Errors errors) {
    Object value = errors.getFieldValue(argument.getName());
    String legalValue = anno.value();
    if (StringUtils.isNotBlank(legalValue)) {
      if (!anno.scope().compare(value.toString(), legalValue)) {
        errors.rejectValue(argument.getName(), null, null, argument.getName()
            + " has a unvalid value, plz check it.");
        return false;
      }
    }
    return true;
  }

  private void setOverCome(Class<?> clazz) {
    OverComeField field = clazz.getAnnotation(OverComeField.class);
    if (null != field && field.field().length > 0 && null != field.annotiation()) {
      _overComeMap.get(field.annotiation()).addAll(Sets.newHashSet(field.field()));
    }
    OverComeFieldList annotation = clazz.getAnnotation(OverComeFieldList.class);
    if (null != annotation && null != annotation.value() && annotation.value().length > 0) {
      for (OverComeField _field : annotation.value()) {
        _overComeMap.get(_field.annotiation()).addAll(Sets.newHashSet(field.field()));
      }
    }
  }

  private void setAddValid(Class<?> clazz) {
    AddValidField field = clazz.getAnnotation(AddValidField.class);
    if (null != field && field.field().length > 0 && null != field.annotiation()) {
      _addValidMap.get(field.annotiation()).addAll(Sets.newHashSet(field.field()));
    }
    AddValidFieldList annotation = clazz.getAnnotation(AddValidFieldList.class);
    if (null != annotation && null != annotation.value() && annotation.value().length > 0) {
      for (AddValidField _field : annotation.value()) {
        _addValidMap.get(_field.annotiation()).addAll(Sets.newHashSet(field.field()));
      }
    }
  }

  private Set<String> getSkipSet(AnnotationType type) {
    return _overComeMap.get(type);
  }

  private Set<String> getAddSet(AnnotationType type) {
    return _addValidMap.get(type);
  }

}
