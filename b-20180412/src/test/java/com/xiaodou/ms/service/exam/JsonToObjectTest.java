package com.xiaodou.ms.service.exam;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xiaodou.ms.model.score.RuleInfo;

public class JsonToObjectTest {

 /* 
  public static void main(String[] args) {
    
    List<RuleInfo> list = new ArrayList<RuleInfo>(); 
    for (int i = 0; i < 5; i++) {
      RuleInfo info = new RuleInfo();
      info.setAbtractInfo("korea");
      info.setCode((short)1);
      info.setMoreInfo("thai");
      info.setOrder(12);
      info.setWeight(12.12);
      list.add(info);
    }
    String json = JSON.toJSONString(list);  
    System.out.println( json);
    
    JSONArray jsonArray = JSONArray.parseArray(json);

    for (int i = 0; i < jsonArray.size(); i++) {
   
      Object object = jsonArray.get(i);
      System.out.println(object);
    }
  }*/
  
public static void main(String[] args) {
  System.out.println(UUID.randomUUID().toString().length());
  System.out.println(UUID.randomUUID().toString().length());
  System.out.println(UUID.randomUUID().toString().length());
  System.out.println(UUID.randomUUID().toString().length());
  System.out.println(UUID.randomUUID().toString().length());
  System.out.println(UUID.randomUUID().toString().length());
  System.out.println(UUID.randomUUID().toString().length());

}
}
