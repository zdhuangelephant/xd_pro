package com.xiaodou.userCenter.request.ucenterHttp;

import lombok.Data;


/**
 * 通过手机号找回密码
 * 
 * @author 李德洪
 * @date 2017年2月10日
 */
@Data
public class FindPasswordRequest{
  private String telephone;
  private String password;
}
