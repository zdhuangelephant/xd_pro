package com.xiaodou._2b_dashboard_report;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.unitils.spring.annotation.SpringBean;

import com.xiaodou.st.dashboard.service.http.HttpService;

public class messageTest extends BaseUnitils {

  @SpringBean("httpService")
  HttpService httpService;
  
  @Test
  public void message() {
    //messageService.quartzStudent();
    //messageService.sendStudentMessage(null);
    //StudentMessageBody messageBody = new StudentMessageBody();
    //RabbitMQSender.getInstance().send(new StudentMessage(messageBody));
    httpService.quartzStudent();
    httpService.quartzApply();
  }


  public static void main(String[] args) {
    List<String> c = new ArrayList<String>();
    c.add("A");
    c.add("B");
    List<String> c1 = new ArrayList<String>(c);
    List<String> c2 = c.subList(0, c.size());
    c2.add("C");
    System.out.println("c=c1?" + c1.equals(c));
    System.out.println("c=c2?" + c2.equals(c));
    System.out.println();
    for (String a : c) {
      System.out.println(a);
    }
    
  }

}
