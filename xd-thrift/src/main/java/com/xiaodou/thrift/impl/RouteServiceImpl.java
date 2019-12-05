package com.xiaodou.thrift.impl;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

import org.apache.thrift.TException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.google.common.collect.Maps;
import com.xiaodou.common.util.ScanPath;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.summer.util.SpringWebContextHolder;
import com.xiaodou.thrift.annotion.ThriftMethod;
import com.xiaodou.thrift.annotion.ThriftService;
import com.xiaodou.thrift.iface.RouteService;
import com.xiaodou.thrift.model.DefaultModel;
import com.xiaodou.thrift.model.TransferParam;
import com.xiaodou.thrift.util.ThriftUtil;

public class RouteServiceImpl extends ScanPath implements RouteService.Iface {

  static class Wrapper {
    private static final Map<String, ClassWrapper> _thriftClassMap = Maps.newHashMap();
    private static final Map<String, Class<?>[]> _paramType = Maps.newHashMap();
  }

  /**
   * 从包package中获取所有的Class
   * 
   * @param pack
   * @return
   */
  public RouteServiceImpl(String scanPath) {
    super(scanPath);
  }
  
  @Override
  protected void processClass(Class<?> clazz) {
    if (null == clazz) return;
    if (clazz.getAnnotation(ThriftService.class) == null) return;
    ClassWrapper classWrapper = new ClassWrapper(clazz);
    Wrapper._thriftClassMap.put(clazz.getSimpleName(), classWrapper);
    for (Method method : clazz.getDeclaredMethods()) {
      processMethod(classWrapper, method);
    }
  }

  private void processMethod(ClassWrapper classWrapper, Method method) {
    if (null == method) return;
    if (method.getAnnotation(ThriftMethod.class) == null) return;
    method.setAccessible(true);
    Wrapper._paramType.put(ThriftUtil.getParamterKey(classWrapper.getClazz(), method),
        method.getParameterTypes());
  }

  @Override
  public DefaultModel routeService(String className, String funName, String paramValue)
      throws TException {
    try {
      ClassWrapper classWrapper = Wrapper._thriftClassMap.get(className);
      Object impl = null;
      if (null == classWrapper) return null;
      if (classWrapper.isBean())
        impl = SpringWebContextHolder.getBean(classWrapper.getBeanName());
      else
        impl = classWrapper.getClazz().newInstance();
      if (null == impl)
        throw new RuntimeException(String.format("[Miss service{class:%s}]", className));
      Class<?>[] parameterTypes =
          Wrapper._paramType.get(ThriftUtil.getParamterKey(classWrapper.getClazz(), funName));
      if (null == parameterTypes)
        throw new RuntimeException(String.format("[Miss parameterType{class:%s,method:%s}]", impl
            .getClass().getSimpleName(), funName));
      Method methd = impl.getClass().getDeclaredMethod(funName, parameterTypes);
      if (null == methd) throw new RuntimeException("Miss method");
      Object[] argments = null;
      if (parameterTypes.length > 0 && StringUtils.isNotBlank(paramValue)) {
        TransferParam transferParam = FastJsonUtil.fromJson(paramValue, TransferParam.class);
        argments = new Object[parameterTypes.length];
        for (int i = 0; i < parameterTypes.length; i++)
          if (i < transferParam.size())
            argments[i] = ThriftUtil.changeParam(transferParam.get(i), parameterTypes[i]);
          else
            argments[i] = null;
      }
      methd.setAccessible(true);
      return new DefaultModel(ThriftUtil.changeResult(methd.invoke(impl, argments),
          methd.getReturnType()));
    } catch (NoSuchMethodException e) {
      LoggerUtil.error("NoSuchMethodException", e);
    } catch (SecurityException e) {
      LoggerUtil.error("SecurityException", e);
    } catch (IllegalAccessException e) {
      LoggerUtil.error("IllegalAccessException", e);
    } catch (IllegalArgumentException e) {
      LoggerUtil.error("IllegalArgumentException", e);
    } catch (InvocationTargetException e) {
      LoggerUtil.error("InvocationTargetException", e);
    } catch (InstantiationException e) {
      LoggerUtil.error("InstantiationException", e);
    }
    return null;
  }

  public static String captureFirstLowerCase(String str){
    char[] chars = new char[1];
    chars[0] = str.charAt(0);
    String temp = new String(chars);
    if (chars[0] >= 'A' && chars[0] <= 'Z') {//当为字母时，则转换为小写
        return str.replaceFirst(temp, temp.toLowerCase());
    }
    return str;
  }
  
  private static class ClassWrapper {
    public ClassWrapper(Class<?> clazz) {
      this.clazz = clazz;
      this.beanName = captureFirstLowerCase(clazz.getSimpleName());
      if (clazz.isAnnotationPresent(Repository.class)) {
        this.bean = true;
        Repository repository = clazz.getAnnotation(Repository.class);
        if (StringUtils.isNotBlank(repository.value())) this.beanName = repository.value();
      } else if (clazz.isAnnotationPresent(Component.class)) {
        this.bean = true;
        Component component = clazz.getAnnotation(Component.class);
        if (StringUtils.isNotBlank(component.value())) this.beanName = component.value();
      } else if (clazz.isAnnotationPresent(Controller.class)) {
        this.bean = true;
        Controller controller = clazz.getAnnotation(Controller.class);
        if (StringUtils.isNotBlank(controller.value())) this.beanName = controller.value();
      } else if (clazz.isAnnotationPresent(Service.class)) {
        this.bean = true;
        Service service = clazz.getAnnotation(Service.class);
        if (StringUtils.isNotBlank(service.value())) this.beanName = service.value();
      }
    }

    private Class<?> clazz;
    private boolean bean = false;
    private String beanName;

    public Class<?> getClazz() {
      return clazz;
    }

    public boolean isBean() {
      return bean;
    }

    public String getBeanName() {
      return beanName;
    }

  }

  public void init() throws TException {}

}
