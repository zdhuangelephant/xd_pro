package com.xiaodou.sms;

import java.util.Date;
import java.util.Random;

import org.junit.Test;
import org.unitils.spring.annotation.SpringBean;

import com.xiaodou.push.agent.model.ShortMessage;
import com.xiaodou.sms.dao.ms.SmsTaskModelDao;
import com.xiaodou.sms.service.sms.ShortMessageProduceService;
import com.xiaodou.sms.web.response.sms.MessageResponse;

public class SmsProduceTest extends BaseUnitils {

  @SpringBean("shortMessageProduceService")
  ShortMessageProduceService shortMessageProduceService;

  @SpringBean("smsTaskModelDao")
  SmsTaskModelDao smsTaskModelDao;

  @Test
  public void querySmsTemplateCategory() throws InterruptedException {

    System.out.println(new Date());
    System.out.println("######################### start ########################");
    for (int i = 0; i < 1; i++) {
      ShortMessage messageRequest = new ShortMessage();
      // Map<String, Object> variables = new HashMap<String, Object>();
      // variables.put("checkcode", "7281");
      String[] list = new String[1];
      // list.add("18210191798");1
      // list.add("18840824301");11
      // list.add("13237163211");11
      // list.add("13581963962");1
      // list.add("13121551578");1
      // list.add("18810496252");1
      // list.add("18785114611");
      // list.add("18064037100");
      // list.add("18107070114");
      // list.add("18895626817");
      // list.add("15638238590");
      // list.add("15910501932");
      // list.add("18234044128");
      // list.add("18732267801");
      // list.add("13260325032");
      // list.add("18911481663");
      // list.add("18157976744");
      // list.add("15591936330");
      // list.add("18109442745");
      // list.add("13079509929");
      // list.add("18308653101");
      // list.add("15311479569");
      // list.add("18267682907");
      // list.add("15720628075");
      // list.add("13718037895");
      // list.add("13822567616");
      list[0] = "15311479569";
      messageRequest.setTelephone(list);// 18840824301
      messageRequest.setTemplateId("5");
      messageRequest.setVariables("checkcode", "7281");
      messageRequest.setVariables("amount", "999");
      messageRequest.setVariables("account", "1119737151@qq.com");
      MessageResponse messageResponse = shortMessageProduceService.produce(messageRequest);
      System.out.println(messageResponse.getStatus() + ":" + messageResponse.getStatusDesc());
      Thread.sleep(1500l);
    }
    Thread.sleep(1500l);
  }

  public static int NextInt(final int min, final int max) {
    Random rand = new Random();
    int tmp = Math.abs(rand.nextInt());
    return tmp % (max - min + 1) + min;
  }
}
