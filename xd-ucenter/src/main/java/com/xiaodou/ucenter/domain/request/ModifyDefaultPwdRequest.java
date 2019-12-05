package com.xiaodou.ucenter.domain.request;

import lombok.Data;

@Data
public class ModifyDefaultPwdRequest {
  private String xdUniqueId;
  private String password;
}
