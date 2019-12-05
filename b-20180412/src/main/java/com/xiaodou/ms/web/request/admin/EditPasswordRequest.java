package com.xiaodou.ms.web.request.admin;

import com.xiaodou.ms.web.request.BaseRequest;
import org.apache.commons.lang.StringUtils;
import org.springframework.validation.Errors;

/**
 * Created by zyp on 14-9-18.
 */
public class EditPasswordRequest extends BaseRequest {


  private String oldPassword;
  /**
   * 密码
   */
  private String newPassword;

  private String repeatNewPassword;

  public String getOldPassword() {
    return oldPassword;
  }

  public void setOldPassword(String oldPassword) {
    this.oldPassword = oldPassword;
  }

  public String getNewPassword() {
    return newPassword;
  }

  public void setNewPassword(String newPassword) {
    this.newPassword = newPassword;
  }

  public String getRepeatNewPassword() {
    return repeatNewPassword;
  }

  public void setRepeatNewPassword(String repeatNewPassword) {
    this.repeatNewPassword = repeatNewPassword;
  }

  @Override
  public void validate(Object o, Errors errors) {

    if (StringUtils.isBlank(this.oldPassword)) {
      errors.reject("oldPassword", "旧密码不能为空");
    }
    if (StringUtils.isBlank(this.newPassword) || StringUtils.isBlank(this.repeatNewPassword)) {

    } else {
      if (StringUtils.isNotBlank(this.newPassword) && StringUtils.isNotBlank(this.repeatNewPassword)
        && !this.newPassword.equals(this.repeatNewPassword)) {
        errors.reject("repeatNewPassword", "新密码和重复新密码不相同");
      }
    }
  }
}
