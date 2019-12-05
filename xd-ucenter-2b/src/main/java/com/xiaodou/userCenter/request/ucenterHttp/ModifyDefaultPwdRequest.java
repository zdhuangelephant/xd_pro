package com.xiaodou.userCenter.request.ucenterHttp;

import lombok.Data;

@Data
public class ModifyDefaultPwdRequest {
  private String xdUniqueId;
  private String password;
}
