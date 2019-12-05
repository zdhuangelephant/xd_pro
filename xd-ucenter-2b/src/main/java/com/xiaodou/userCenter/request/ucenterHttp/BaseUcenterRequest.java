package com.xiaodou.userCenter.request.ucenterHttp;

import lombok.Data;

@Data
public class BaseUcenterRequest {
  
  private String module;
  
  private String platform;

  private String telephone;

  private String password;

  private String qq;

  private String weixin;

  private String weibo;

  private String tourist;
  
  private String xdUniqueId;
}
