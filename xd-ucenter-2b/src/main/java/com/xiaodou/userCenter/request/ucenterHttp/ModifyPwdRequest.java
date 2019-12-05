package com.xiaodou.userCenter.request.ucenterHttp;

import lombok.Data;

@Data
public class ModifyPwdRequest {

  private String xdUniqueId;

  private String oldPwd;

  private String newPwd;

  private String confirmNewPwd;
}
