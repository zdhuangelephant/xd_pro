package com.xiaodou.userCenter.response.ucenterHttp;

import com.xiaodou.userCenter.model.http.ResponseBase;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class RingtalkResponse extends ResponseBase{
  private String errcode;
  private String errmsg;
}
