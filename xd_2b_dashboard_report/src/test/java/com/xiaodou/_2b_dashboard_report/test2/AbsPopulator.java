package com.xiaodou._2b_dashboard_report.test2;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.List;

import lombok.Data;

import com.google.common.collect.Lists;

public abstract class AbsPopulator {

  public final void dataInitialing() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException{
    Method[] methods = getClass().getMethods();
    for(Method method : methods){
      if(isInitDataMethod(method)){
        method.invoke(this);
      }
    }
  }
  
  private boolean isInitDataMethod(Method m){
    boolean flag = m.getName().startsWith("init") //init开始
        && Modifier.isPublic(m.getModifiers()) //公开方法
        && m.getReturnType().equals(Void.TYPE)//返回值是void
        && !m.isVarArgs()//输入参数是空
        && !Modifier.isAbstract(m.getModifiers())//不能是抽象方法
    ;
    return flag;
  }
  @Data
  static class User{
    private static Integer age;
    private String name;

  public int hashCode(){
    return 1;
  }

  }
  public static void main(String[] args) {
    List<User> list = Lists.newArrayList();
    User user = new User();
    User.age =1;
    user.setName("name1");
    System.out.println(user.hashCode());
    list.add(user);
    //user.setAge(2);
    User.age =2;
    user.setName("name2");
    System.out.println(user);
    list.add(user);
    //List<User> list1 = list.subList(0, 1);
    //user.setAge(3);
    User.age =3;
    user.setName("name3");
    list.add(user);
    for(User u:list){
      System.out.println(User.age+u.getName());
    }
  }
  
}
