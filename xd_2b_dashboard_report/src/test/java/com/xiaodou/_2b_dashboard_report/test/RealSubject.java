package com.xiaodou._2b_dashboard_report.test;

import java.lang.reflect.Method;

import org.objectweb.asm.attrs.Annotation;

import thirdparty.net.sf.cglib.core.MethodInfo;

import com.alibaba.fastjson.util.FieldInfo;
import com.xiaodou.st.dashboard.domain.student.StudentDO;

public class RealSubject implements Subject{

  @Override
  public void request() {
    System.out.println("request");
  }

  public static void main(String[] args) {
    
  }
  
}
