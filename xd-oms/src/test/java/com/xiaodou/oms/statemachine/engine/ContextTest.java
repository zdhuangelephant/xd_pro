package com.xiaodou.oms.statemachine.engine;

import java.util.List;

import org.junit.Test;

import com.xiaodou.oms.statemachine.engine.APIContext;
import com.xiaodou.oms.statemachine.engine.StateMachineContext;
import com.xiaodou.oms.statemachine.engine.instance.StateMachineConf;
import com.xiaodou.oms.statemachine.engine.instance.StateMachineProductLineConf;
import com.xiaodou.oms.statemachine.engine.model.APILib;
import com.xiaodou.oms.statemachine.engine.model.Template;
import com.xiaodou.oms.statemachine.engine.model.api.BaseAPI;
import com.xiaodou.oms.statemachine.engine.model.api.RemoteAPI;
import com.xiaodou.oms.statemachine.engine.model.api.proxy.IApiProxy;

public class ContextTest {

  static {
    try {
      APIContext.init("conf/core/apilib_test.xml");
      StateMachineContext.init("conf/core/statemachine_0101.xml");
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Test
  public void testApiContext() throws Exception {
    APILib apiLib = APIContext.getApiLib();
    assert (apiLib.getLocalApi("omsApi").getClassName())
        .equals("com.elong.oms.statemachine.engine.model.api.localapi.Mytest");
    assert (apiLib.getProductLineMap().size() == 1);
    Template template = apiLib.getParameterTemplate().getTemplate("default_simple");
    assert (template.getFile().equals("default_simple.vm"));
    RemoteAPI api = apiLib.getRemoteApi("0101", "carDriver");
    assert "localhost:8080/elong-oms/order/test.do".equals(api.getUrl());
  }

  @Test
  public void testStateMachineInstanceContext() throws Exception {
    StateMachineConf conf = StateMachineContext.getConf();
    StateMachineProductLineConf productLineConf = conf.getProductLineConf("0101");
    assert productLineConf.getName().equals("car");
    List<IApiProxy> doList =
        productLineConf.getTransitionConfMap().get("fromInitToPay").getDoList();
    assert doList.get(0).getApi().getName().equals("omsApi");
    BaseAPI api = APIContext.getApiLib().getOmsOrder().getLocalAPI("omsApi");
    assert doList.get(0).getApi().equals(api);
  }

}
