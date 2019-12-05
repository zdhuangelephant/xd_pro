package com.xiaodou.common.test.util;

import org.junit.Test;

import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.common.util.log.model.InOutEntity;
import com.xiaodou.common.util.log.model.OutInEntity;

public class LoggerUtilTest {

  @Test
  public void testOutInLog() {
    OutInEntity entity = new OutInEntity();
    entity.setTargetClass("testClass");
    entity.setTargetMethod("testMethod");
    entity.setParams("testParams");
    entity.setResponseInfo("200");
    entity.setUseTime(20L);
    LoggerUtil.outInInfo(entity);
  }

  @Test
  public void testInOutLog() {
    InOutEntity entity = new InOutEntity();
    entity.setTargetUrl("testUrl");
    entity.setParams("testParams");
    entity.setResponseInfo("200");
    entity.setUseTime(20L);
    LoggerUtil.inOutInfo(entity);
  }
}
