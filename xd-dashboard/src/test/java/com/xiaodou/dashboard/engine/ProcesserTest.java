package com.xiaodou.dashboard.engine;

import com.xiaodou.common.util.warp.FastJsonUtil;

public class ProcesserTest {

  public void testContainer() {
    EventContainer container = EventContainer.getContainer();
    System.out.println(FastJsonUtil.toJson(container.list()));
  }

}
