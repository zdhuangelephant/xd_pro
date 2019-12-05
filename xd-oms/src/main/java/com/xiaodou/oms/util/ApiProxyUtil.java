package com.xiaodou.oms.util;

import java.io.IOException;
import java.io.StringWriter;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.exception.MethodInvocationException;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;

import com.google.common.collect.Maps;
import com.xiaodou.oms.statemachine.Context;
import com.xiaodou.oms.statemachine.engine.model.api.RemoteAPI;
import com.xiaodou.oms.statemachine.engine.model.api.proxy.HttpApiBase;
import com.xiaodou.oms.statemachine.engine.model.api.proxy.JsonHttpApiBase;
import com.xiaodou.oms.statemachine.engine.model.api.proxy.SoapHttpApiBase;


/**
 * <p>
 * Api代理用工具类
 * </p>
 * 
 * @author <a href="mailto:dan.zhao@elong.corp.com">zhaodan</a>
 * @version 1.0
 * @date 2014年7月4日
 */
public class ApiProxyUtil {

  private final static Map<String, Class<? extends HttpApiBase>> invokeMapping = Maps.newHashMap();

  static {
    invokeMapping.put("json", JsonHttpApiBase.class);
    invokeMapping.put("soap", SoapHttpApiBase.class);
  }

  /**
   * 根据协议名称获取HttpProxy实例
   * 
   * @param api 所持API
   * @return 代理实例
   * @throws InstantiationException
   * @throws IllegalAccessException
   * @throws SecurityException
   * @throws NoSuchMethodException
   * @throws InvocationTargetException
   * @throws IllegalArgumentException
   */
  public static HttpApiBase getHttpProxy(RemoteAPI api) throws InstantiationException,
      IllegalAccessException, IllegalArgumentException, InvocationTargetException,
      NoSuchMethodException, SecurityException {
    return invokeMapping.get(api.getProtocol()).getConstructor(RemoteAPI.class).newInstance(api);
  }
  
  /**
   * 获取JSON串参数
   * @param context 上下文
   * @param api 远程API
   * @return 参数串
   * @throws ResourceNotFoundException
   * @throws ParseErrorException
   * @throws MethodInvocationException
   * @throws IOException
   */
  public static String getPostJson(Context context, RemoteAPI api)
      throws ResourceNotFoundException, ParseErrorException, MethodInvocationException, IOException {
    StringWriter writer = new StringWriter();
    VelocityContext vmcontext = new VelocityContext();
    vmcontext.put("req", context);
    api.getTemplate().merge(vmcontext, writer);
    return writer.toString();
  }

}
