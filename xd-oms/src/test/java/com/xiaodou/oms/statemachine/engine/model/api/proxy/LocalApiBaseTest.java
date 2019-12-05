package com.xiaodou.oms.statemachine.engine.model.api.proxy;

import com.xiaodou.oms.entity.order.Gorder;
import com.xiaodou.oms.statemachine.Context;
import com.xiaodou.oms.statemachine.engine.model.api.proxy.LocalApiBase;
import com.xiaodou.oms.statemachine.param.CoreParams;
import com.xiaodou.oms.statemachine.param.PayParam;

import org.junit.Test;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * Date: 2015/3/20
 * Time: 9:56
 *
 * @author Tian.Dong
 */
public class LocalApiBaseTest {
  @Test
  public void testGetParamsPayParam() throws Exception {
    Context context = new Context();
    CoreParams coreParams = new CoreParams();
    PayParam payParam = new PayParam();
    payParam.setAmount(new BigDecimal("80.0"));
    coreParams.setPayParam(payParam);
    context.setCoreParams(coreParams);

    System.out.println(getParams("coreParams.payParam.amount", context));
  }

  @Test
   public void testGetParamsCoreParamsGorderUpdateInfo() throws Exception {
    Context context = new Context();
    CoreParams coreParams = new CoreParams();
    Gorder gorderUpdateInfo = new Gorder();
    gorderUpdateInfo.setGpayAmount(new BigDecimal("90.888"));
    coreParams.setGorderUpdateInfo(gorderUpdateInfo);
    context.setCoreParams(coreParams);

    System.out.println(getParams("coreParams.gorderUpdateInfo.gpayAmount", context));
  }

  @Test
  public void testGetParamsCoreParamsGorderUpdateInfo2() throws Exception {
    Context context = new Context();
    CoreParams coreParams = new CoreParams();
    Gorder gorderUpdateInfo = new Gorder();
    gorderUpdateInfo.setGpayAmount(new BigDecimal("90.888"));
    coreParams.setGorderUpdateInfo(gorderUpdateInfo);
    context.setCoreParams(coreParams);

    System.out.println(getParams("coreparams.gorderupdateInfo.gpayAmount", context));
  }

  @Test
  public void testGetParamsSharesMap() throws Exception {
    Context context = new Context();
    CoreParams coreParams = new CoreParams();
    Map<String, Object> shares = new HashMap<>();
    shares.put("aaa", "bbb");
    context.setShares(shares);

    System.out.println(getParams("shares.aaa", context));
  }

  private Object getParams(String s, Context context) {
    return new LocalApiBase().getParams(s,context);
  }

  /*public Object getParams(String paramName, Context context) {
    String[] params = paramName.split("\\.");
    Queue<String> queue = new LinkedList<>();
    queue.addAll(Arrays.asList(params));
    return getParams(queue, context);
  }

  private Object getParams(Queue<String> queue, Object obj) {
    String param = queue.poll();
    Object next = getParam(obj, param);
    if (queue.size() == 0 || next == null) {
      return next;
    }
    return getParams(queue, next);
  }

  private static Object getParam(Object obj, String fieldName) {
    if (obj == null) {
      return null;
    }
    if (Map.class.isAssignableFrom(obj.getClass())) {
      return ((Map) obj).get(fieldName);
    }
    if (StringUtils.isBlank(fieldName)) {
      return null;
    }
    try {
      for (Field field : obj.getClass().getDeclaredFields()) {
        if (fieldName.toLowerCase().equals(field.getName().toLowerCase())) {
          field.setAccessible(true);
          return field.get(obj);
        }
      }
    } catch (Exception e) {
      LoggerUtil.error("LocalApiBase.getParam", e);
    }
    return null;
  }*/

  /*public Object getParams(String paramName, Context context) {
    String[] params = paramName.split("\\.");
    Queue<String> queue = new LinkedList<>();
    queue.addAll(Arrays.asList(params));
    return getParams(queue, context);
  }


  private Object getParams(Queue<String> queue, Object obj) {
    String param = queue.poll();
    Object next = getParam(obj, param);
    if (queue.size() == 0 || next == null) {
      return next;
    }
    return getParams(queue, next);
  }

  private static Object getParam(Object obj, String fieldName) {
    if (Map.class.isAssignableFrom(obj.getClass())) {
      return ((Map) obj).get(fieldName);
    }
    try {
      Field field = obj.getClass().getDeclaredField(fieldName);
      field.setAccessible(true);
      return field.get(obj);
    } catch (Exception e) {
      LoggerUtil.error("LocalApiBase.getParam", e);
    }
    return null;
  }*/
}
