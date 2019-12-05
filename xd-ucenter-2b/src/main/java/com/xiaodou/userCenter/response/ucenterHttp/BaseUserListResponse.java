package com.xiaodou.userCenter.response.ucenterHttp;

import java.util.List;

import com.xiaodou.userCenter.model.http.ResponseBase;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class BaseUserListResponse extends ResponseBase{

  private List<BaseUserInfo> userBaseInfoList;

}
