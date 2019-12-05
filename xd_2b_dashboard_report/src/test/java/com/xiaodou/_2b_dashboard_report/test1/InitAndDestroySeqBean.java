package com.xiaodou._2b_dashboard_report.test1;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class InitAndDestroySeqBean implements InitializingBean,DisposableBean{

  static{
    System.out.println("static");
  }
  
  public InitAndDestroySeqBean(){
    System.out.println("执行构造方法");
  }
  
  @PostConstruct
  public void postConstruct(){
    System.out.println("postConstruct");
  }
  
  @Override
  public void afterPropertiesSet() throws Exception {
    System.out.println("afterPropertiesSet");
  }
  
  public void initMethod(){
    System.out.println("initMethod");
  }
  
  @Override
  public void destroy() throws Exception {
    System.out.println("destroy");
  }

  @PreDestroy
  public void preDestroy(){
    System.out.println("preDestroy");
  }
  
  public void destroyMethod(){
    System.out.println("destroyMethod");
  }
  
  public static void main(String[] args) {
    ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("conf/core/xd_2b_dashboard_report.xml");  
    context.close();  
  }
}
