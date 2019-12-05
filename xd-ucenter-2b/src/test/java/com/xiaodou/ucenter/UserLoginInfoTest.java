package com.xiaodou.ucenter;

import org.junit.Test;
import org.unitils.spring.annotation.SpringBean;

import com.xiaodou.userCenter.service.UserLoginInfoService;

public class UserLoginInfoTest extends BaseUnitils {

  @SpringBean("userLoginInfoService")
  UserLoginInfoService userLoginInfoService;
  @Test
  public void a() {
    userLoginInfoService.isAlarmType_9(12l, "777");
  }
}
