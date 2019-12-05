package com.xiaodou.userCenter.request;

import com.xiaodou.summer.validator.annotion.NotEmpty;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class ModifyDefaultPassword extends BaseRequest{
  @NotEmpty
  private String phoneNum;
  @NotEmpty
  private String checkCode;
  @NotEmpty
  private String password;
}
