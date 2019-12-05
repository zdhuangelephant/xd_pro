package com.xiaodou.summer.support;

import java.util.Set;

import org.springframework.beans.factory.BeanDefinitionStoreException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;

import com.google.common.collect.Sets;
import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.summer.support.expand.IRefresh;
import com.xiaodou.summer.support.expand.IShutDown;

public class SummerBeanFactory extends DefaultListableBeanFactory {

  @Override
  public void registerSingleton(String beanName, Object singletonObject)
      throws IllegalStateException {
    if (singletonObject instanceof IRefresh)
      SummerBeanExpandManager.getInstance().registRefresh(beanName);
    if (singletonObject instanceof IShutDown)
      SummerBeanExpandManager.getInstance().registShutDown(beanName);
    super.registerSingleton(beanName, singletonObject);
  }

  @Override
  public void registerBeanDefinition(String beanName, BeanDefinition beanDefinition)
      throws BeanDefinitionStoreException {
    String beanClassName = beanDefinition.getBeanClassName();
    try {
      Class<?> beanClass = getClass().getClassLoader().loadClass(beanClassName);
      Set<Class<?>> interfaceSet = Sets.newHashSet(beanClass.getInterfaces());
      if (interfaceSet.contains(IRefresh.class))
        SummerBeanExpandManager.getInstance().registRefresh(beanName);
      if (interfaceSet.contains(IShutDown.class))
        SummerBeanExpandManager.getInstance().registShutDown(beanName);
    } catch (ClassNotFoundException e) {
      LoggerUtil.error("获取注册bean类型失败.", e);
    }
    super.registerBeanDefinition(beanName, beanDefinition);
  }
}
