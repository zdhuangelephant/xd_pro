package com.xiaodou.server.mapi.test.selftaught;

import javax.annotation.Resource;

import org.junit.Test;

import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.server.mapi.response.selftaught.BaseResponse;
import com.xiaodou.server.mapi.test.BaseSpringTest;
import com.xiaodou.server.mapi.web.controller.selfTaught.SelfTaughtController;

public class QuesbkTest extends BaseSpringTest {

  @Resource(name = "selfTaughtController")
  SelfTaughtController selfTaughtController;

  @Test
  public void testStoreQues() {
    String storeQues = selfTaughtController.storeQues(null);
    BaseResponse res = FastJsonUtil.fromJson(storeQues, BaseResponse.class);
    assert res.isRetOk();
  }

}