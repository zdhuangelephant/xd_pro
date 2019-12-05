package com.xiaodou.summer.validator;

import org.springframework.validation.Errors;

/**
 * 验证接口
 * 
 * @author zhaodan
 * @version 1.0
 * @date 2014-1-26
 */
public interface IValidator {

  /**
   * 验证方法
   * 
   * @param error 错误载体
   */
  public void validate(Errors errors);
  
  /**
   * 验证方法
   */
public Errors validate();

}
