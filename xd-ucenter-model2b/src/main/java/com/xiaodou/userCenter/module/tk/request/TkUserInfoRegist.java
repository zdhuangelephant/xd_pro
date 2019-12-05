package com.xiaodou.userCenter.module.tk.request;

import java.util.Map;

import com.xiaodou.common.util.StringUtils;
import com.xiaodou.userCenter.request.RegistAccountRequest;
import com.xiaodou.userCenter.request.UserBaseInfo;

public class TkUserInfoRegist extends RegistAccountRequest {

  public TkUserInfoRegist() {
    super(null);
  }

  public TkUserInfoRegist(UserBaseInfo info) {
    super(info);
  }

  @Override
  public String getMoudelInfo() {
    // TkUserInfo tkUserInfo = new TkUserInfo();


    return null;
  }

  @Override
  public String setMoudelInfo(Map<String, String> moudelInfoMap) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public String getMajor() {
    return StringUtils.EMPTY;
  }

}
