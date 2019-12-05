package com.xiaodou.oms.statemachine.engine.model.api.proxy;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import com.google.common.collect.Lists;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.oms.exception.ExceptionMessageProp;
import com.xiaodou.oms.statemachine.Context;
import com.xiaodou.oms.statemachine.engine.model.api.InputParam;
import com.xiaodou.oms.statemachine.engine.model.api.LocalAPI;
import com.xiaodou.summer.util.SpringWebContextHolder;

/**
 * <p>
 * LocalApiProxy组件
 * </p>
 * 负责解析调用LocalAPI方法
 *
 * @author <a href="mailto:dan.zhao@elong.corp.com">zhaodan</a>
 * @version 1.0
 * @date 2014年6月13日
 */
@SuppressWarnings("unchecked")
public class LocalApiBase extends AbstractApiProxy<LocalAPI> {

  /**
   * 入参列表
   */
  private ArrayList<InputParam> params;

  public LocalApiBase() {

  }


  public ArrayList<InputParam> getParams() {
    return params;
  }

  public void setParams(ArrayList<InputParam> params) {
    this.params = params;
  }

  public void setParam(InputParam param) {
    if (null == params) {
      synchronized (this) {
        if (null == params) {
          params = Lists.newArrayList();
        }
      }
    }
    if (params.contains(param)) {
      throw new RuntimeException(ExceptionMessageProp.getErrMessage(
          "error.doc.loaddoc.localapi.inputparam.same", param.getName()));
    }
    params.add(param);
  }

  public LocalApiBase(LocalAPI endpoint) {
    super(endpoint);
  }

  public Object execute(Context context) throws Exception {
    // 获取入参列表-初始化入參類型、入參值
    List<InputParam> params = getParams();
    Class<?>[] paramsType = null;
    Object[] paramsValue = null;
    if (null != params) {
      paramsType = new Class[params.size()];
      paramsValue = new Object[params.size()];
      initParams(params, paramsType, paramsValue, context);
    }

    // 获取Bean实例
    Object bean = SpringWebContextHolder.getBean(api.getBeanId());

    // 获取目标方法
    Method invokeMethod = Class.forName(api.getClassName()).getMethod(api.getMethod(), paramsType);

    // 反射調用目標方法
    Object result = invokeMethod.invoke(bean, paramsValue);

    if (null != api.getReturnValueName()) {
      // 设置返回值
      context.getShares().put(api.getReturnValueName(), result);
    }

    return result;
  }

  /**
   * 初始化参数信息
   *
   * @param params      入参列表
   * @param paramsType  参数类型数组
   * @param paramsValue 参数值数组
   * @param context     上下文
   * @throws IllegalAccessException
   * @throws IllegalArgumentException
   */
  private void initParams(List<InputParam> params, Class<?>[] paramsType, Object[] paramsValue,
                          Context context) throws IllegalArgumentException, IllegalAccessException {
    for (int i = 0; i < params.size(); i++) {
      InputParam param = params.get(i);
      paramsType[i] = param.getType();
      if (null != param.getValue()) {
        try {
          paramsValue[i] =
              param.getType().getConstructor(String.class).newInstance(param.getValue().toString());
        } catch (InstantiationException | InvocationTargetException | NoSuchMethodException
            | SecurityException e) {
          throw new RuntimeException("[InvokeLocalApi][InitParam][参数类型转换失败:" + param.getName()
              + "]", e);
        }
      } else {
        paramsValue[i] = getParams(param.getName(), context);
      }
    }
  }


  /**
   * 传入一个如coreParams.gorderUpdateInfo.gpayAmount和context对象
   * 则会获取context.getCoreParams().getGorderUpdateInfo.getGpayAmount()
   * 同时也支持获取Shares Map里的值,如传入("shares.aaa",context)context.getShares().get("aaa")
   * 另外对大小写做了处理，传入coreparams.gorderupdateinfo也可以，但取shares里的东西必须大小写正确
   *
   * @param paramName 参数路径名如coreParams.payParam.amount
   * @param context   context对象
   * @return context的相应属性
   * @author tian.dong
   */
  public Object getParams(String paramName, Context context) {
    if (StringUtils.isBlank(paramName)) {
      return null;
    }

    if(paramName.toLowerCase().equals("context")) {
      return context;
    }
    String[] params = paramName.split("\\.");
    Queue<String> queue = new LinkedList<>();
    queue.addAll(Arrays.asList(params));
    return getParam(queue, context);
  }

  /**
   * 递归获取对象的属性
   *
   * @param queue paramName按照.分割后的queue
   * @param obj   任意对象
   * @return 对象的属性
   * @author tian.dong
   */
  private Object getParam(Queue<String> queue, Object obj) {
    String param = queue.poll();
    Object next = getFieldParam(obj, param);
    if (queue.size() == 0 || next == null) {
      return next;
    }
    return getParam(queue, next);
  }

  /**
   * 获取对象的属性
   * 如果对象是一个Map对象，则map.get(fieldName)
   * 如果对象是一个普通对象，则把对象所有field转成小写和传入fieldName转小写比较，返回field.get(obj)
   *
   * @param obj       任意对象，支持map
   * @param fieldName 对象属性
   * @return
   */
  @SuppressWarnings("rawtypes")
  private static Object getFieldParam(Object obj, String fieldName) {
    if (obj == null || StringUtils.isBlank(fieldName)) {
      return null;
    }
    //判断obj是否是一个Map对象
    if (Map.class.isAssignableFrom(obj.getClass())) {
      return ((Map) obj).get(fieldName);
    }

    for (Field field : obj.getClass().getDeclaredFields()) {
      try {
        if (fieldName.toLowerCase().equals(field.getName().toLowerCase())) {
          field.setAccessible(true);
          return field.get(obj);
        }
      } catch (Exception e) {
        LoggerUtil.error("LocalApiBase.getParam", e);
      }
    }
    return null;
  }

}
