package com.xiaodou._2b_dashboard_report.test;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class SubjectHandler implements InvocationHandler{

  private Subject subject;
  public SubjectHandler(Subject _subject){
    subject = _subject;
  }
  @Override
  public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
    System.out.println("预处理");
    //直接调用被代理类的方法
    Object object = method.invoke(subject, args);
    System.out.println("后处理");
    return object;
  }
  
  public static void main(String[] args) {
    Subject subject = new RealSubject();
    InvocationHandler hander = new SubjectHandler(subject);
    ClassLoader cl = subject.getClass().getClassLoader();
    Subject proxy = (Subject) Proxy.newProxyInstance(cl, subject.getClass().getInterfaces(), hander);
    proxy.request();
    
  }
  
}
