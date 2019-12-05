package com.xiaodou.message.test;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.unitils.spring.annotation.SpringBean;

import com.xiaodou.message.dao.MessageModelDao;
import com.xiaodou.message.model.MessageModel;
import com.xiaodou.sms.BaseUnitils;

public class MessageModelDaoTest extends BaseUnitils {
  @SpringBean("messageModelDao")
  MessageModelDao messageModelDao;
  /**
   * 增删改查
   */
//  
//  //添加
//  @Test
//  public void addMessageModel(){
//    MessageModel messageModel = new MessageModel();
//    messageModel.setMessage("{123}");
//    messageModel.setMsg("成功");
//    messageModel.setStatus(1);
//    messageModel.setCreateTime(new Timestamp(System.currentTimeMillis()));
//    System.out.println(JSON.toJSONString(messageModelDao.addEntity(messageModel)));
//    
//  }
//  //删除
//  @Test
//  public void removeMessageModel(){
//    //删除条件
//    Map<String,Object> cond = new HashMap<String,Object>();
//    cond.put("message","123");
//    cond.put("status",1);
//    System.out.println(messageModelDao.deleteList(cond));
//  }
  
  //更改
  @Test
  public void modifyMessageModel(){
    //更改条件
    Map<String,Object> cond = new HashMap<String,Object>();
//    cond.put("message", "123");
//    cond.put("status", 3);
    cond.put("id", 87);
    //更改内容
    MessageModel model = new MessageModel();
    model.setMsg("asdfasdfa");
    System.out.println(messageModelDao.updateEntity(cond, model));
  }
  
//  //查询
//  @Test
//  public void queryMessageModel(){
//    Map<String,Object> cond = new HashMap<String,Object>();
//    cond.put("status", 1);
//    List<MessageModel> modelList = messageModelDao.queryList(cond, null);
//    for(MessageModel model : modelList){
//      System.out.println(JSON.toJSONString(model));
//    }
//  }
}
