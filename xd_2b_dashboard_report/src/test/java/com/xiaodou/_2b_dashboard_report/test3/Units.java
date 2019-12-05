package com.xiaodou._2b_dashboard_report.test3;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import com.google.common.collect.Lists;
import com.xiaodou.st.dashboard.domain.unit.UnitDO;

public class Units {
  //获得一个泛型类的实际泛型类型
  @SuppressWarnings("unchecked")
  public static <T> Class<T> getGenricClassType(Class<?> clz){
    Type type = clz.getGenericSuperclass();
    if(type instanceof ParameterizedType){
      ParameterizedType pt = (ParameterizedType) type;
      Type[] types = pt.getActualTypeArguments();
      if(types.length>0 && types[0] instanceof Class){
        return (Class<T>) types[0];
      }
    }
    return (Class<T>) Object.class;
  }
  
  public static void main(String[] args) {
    List<UnitDO> list=Lists.newArrayList();
    System.out.println(Units.getGenricClassType(list.getClass()));;
  }
}
