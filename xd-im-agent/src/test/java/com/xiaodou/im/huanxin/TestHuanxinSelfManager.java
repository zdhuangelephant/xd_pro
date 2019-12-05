package com.xiaodou.im.huanxin;

import com.xiaodou.im.agent.huanxin.HxSelfManager;
import com.xiaodou.im.request.RegistUserPojo;
import com.xiaodou.im.response.IMResponse;

public class TestHuanxinSelfManager {

  private HxSelfManager manager = new HxSelfManager();

//  @Test
  public void testRegistUser() {
    RegistUserPojo pojo = new RegistUserPojo();
    pojo.setAccount("zhaodan20000");
    pojo.setPassword("123456");
    IMResponse registUser = manager.registUser(pojo);
    assert registUser.isRetOK();
  }

}
