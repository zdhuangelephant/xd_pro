package com.xiaodou.thrift.client;

import org.springframework.aop.framework.ProxyFactory;
import org.springframework.beans.factory.FactoryBean;

/**
 * @name ThriftHttpProxyFactoryBean CopyRright (c) 2018 by 李德洪
 * 
 * @author 李德洪
 * @date 2018年1月7日
 * @description 接口代理工厂
 * @version 1.0
 */
public class ThriftHttpProxyFactoryBean extends ThriftClientInterceptor
    implements
      FactoryBean<Object> {

  private Object serviceProxy;

  @Override
  public void afterPropertiesSet() {
    super.afterPropertiesSet();
    this.serviceProxy =
        new ProxyFactory(getServiceInterface(), this).getProxy(getBeanClassLoader());
  }

  public Object getObject() {
    return this.serviceProxy;
  }

  public Class<?> getObjectType() {
    return getServiceInterface();
  }

  public boolean isSingleton() {
    return true;
  }
}
