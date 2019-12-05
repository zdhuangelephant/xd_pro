package com.xiaodou._2b_dashboard_report.tests4;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public class DecorateAnimal implements Animal{
  
  private Animal animal;
  
  private Class<? extends Feature> clz;
  
  public DecorateAnimal(Animal animal, Class<? extends Feature> clz) {
    super();
    this.animal = animal;
    this.clz = clz;
  }

  @Override
  public void doStuff() {
    InvocationHandler handler = new InvocationHandler(){
      @Override
      public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object obj = null;
        //设置包装条件
        if(Modifier.isPublic(method.getModifiers())){
          obj = method.invoke(clz.newInstance(), args);
        }
        animal.doStuff();
        return obj;
      }
    };
    
    //todo
  }

}
