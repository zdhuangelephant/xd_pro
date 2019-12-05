package com.xiaodou.message.test;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.junit.Test;
import org.unitils.spring.annotation.SpringBean;

import com.xiaodou.message.enums.JpushTagsEnums;
import com.xiaodou.message.service.message.MessageProduceService;
import com.xiaodou.message.web.response.MessageResponse;
import com.xiaodou.push.agent.enums.MessageType;
import com.xiaodou.push.agent.enums.PushMessageProLine;
import com.xiaodou.push.agent.enums.SpreadRange;
import com.xiaodou.push.agent.enums.TargetType;
import com.xiaodou.push.agent.model.Message;
import com.xiaodou.sms.BaseUnitils;

public class JPushTest extends BaseUnitils {

  @SpringBean("messageProduceService")
  MessageProduceService messageProduceService;


  @Test
  public void sendpush() throws InterruptedException {

    System.out.println(new Date());
    System.out.println("######################### start ########################");
    for (int i = 0; i < 1; i++) {

      Message ms = new Message();
      // ms.setMessageContent("您的账号于"+loginDate+"在另一台手机登录。如非本人操作，则密码可能已泄露，建议前往“我的->设置”修改密码");
      ms.setMessageContent("亲，小逗有新的系统通知了！");
      ms.setNoticeContent("亲，小逗有新的系统通知了！");
      new Date(System.currentTimeMillis());
      // ms.setNoticeContent("您的账号于"+loginDate+"在另一台手机登录。如非本人操作，则密码可能已泄露，建议前往“我的->设置”修改密码");
      ms.setMessageType(MessageType.ALL);
      ms.setScope(SpreadRange.ALIAS_TAG);
//      ms.setScope(SpreadRange.TAG_REGISTRATION_ID);
      ms.setTarget(TargetType.ALL);
//      ms.setTarget(TargetType.ANDROID);
      Map<String, String> messageextras1 = new HashMap<String, String>();
      Map<String, String> noticeextras1 = new HashMap<String, String>();
//       ms.addRegister(new
//       String[]{"190e35f7e0168874f68"});//090a233f752//081cacb1521//00083dc7a2d//09119110975
      //ms.addGroup(new String[] {"environmenttest"});
     // ms.addRegister(new String[]{"161a3797c80a250c4d0"});
//      messageextras1.put("retcode", "20419");
//      messageextras1.put("retdesc", "后台添加了新的系统通知");
//      noticeextras1.put("retcode", "20419");
//      noticeextras1.put("retdesc", "后台添加了新的系统通知");
//      ms.addReciever(new String[]{"18210191798"});
      ms.addReciever(new String[]{"2018060518743794"});
      ms.setMessageextras(messageextras1);// 消息参数
      ms.setNoticeextras(noticeextras1);
      ms.setAppMessageId(UUID.randomUUID().toString());
      ms.setProductLine(PushMessageProLine.SYS_MESSAGE.name());
      ms.addGroup(JpushTagsEnums.XD_TEST.getName());
//      ms.addGroup(JpushTagsEnums.XD_PRE_RELEASE.getName());
//      ms.addGroup(JpushTagsEnums.XD_DEMO.getName());
      MessageResponse messageResponse = messageProduceService.produce(ms);
      System.out.println(messageResponse.getStatus() + "" + messageResponse.getStatusDesc());
    }
    while (true) {}
     //Thread.sleep(10000l);
  }
}
