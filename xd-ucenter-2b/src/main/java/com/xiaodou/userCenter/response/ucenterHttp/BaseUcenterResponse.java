package com.xiaodou.userCenter.response.ucenterHttp;

import org.apache.commons.lang.StringUtils;

import com.xiaodou.userCenter.model.http.ResponseBase;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class BaseUcenterResponse extends ResponseBase{
  
  private String xdUniqueId = StringUtils.EMPTY;

}
