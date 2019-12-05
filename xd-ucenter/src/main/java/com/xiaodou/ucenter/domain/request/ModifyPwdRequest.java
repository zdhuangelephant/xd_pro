package com.xiaodou.ucenter.domain.request;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.xiaodou.summer.validator.annotion.NotEmpty;
import com.xiaodou.summer.vo.in.BaseValidatorPojo;

@Data
@EqualsAndHashCode(callSuper=false)
public class ModifyPwdRequest extends BaseValidatorPojo {

  @NotEmpty
  private String xdUniqueId;
  
  @NotEmpty
  private String oldPwd;

  @NotEmpty
  private String newPwd;

  @NotEmpty
  private String confirmNewPwd;

}
