package com.xiaodou.message.test;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.unitils.spring.annotation.SpringBean;

import com.alibaba.fastjson.JSON;
import com.xiaodou.message.dao.MessageDeviceTagModelDao;
import com.xiaodou.message.model.MessageDeviceTagModel;
import com.xiaodou.sms.BaseUnitils;

public class MessageDeviceTagModelDaoTest extends BaseUnitils {
  @SpringBean("messageDeviceTagModelDao")
  MessageDeviceTagModelDao messageDeviceTagModelDao;
  /**
   * 增删改查
   */
  
  //添加
  @Test
  public void addMessageDeviceTagModel(){
    MessageDeviceTagModel model = new MessageDeviceTagModel();
    model.setTagId("1");
    model.setDeviceId(1);
    model.setCreateTime(new Timestamp(System.currentTimeMillis()));
    System.out.println(JSON.toJSONString(messageDeviceTagModelDao.addEntity(model)));
  }
  
  //删除
  @Test
  public void removeMessageDeviceTagModel(){
    //删除条件
    Map<String, Object> map = new HashMap<String,Object>();
    map.put("tagId", "1");
    map.put("deviceId", 1);
    System.out.println(messageDeviceTagModelDao.deleteList(map));
  }
  
  //更改
  @Test
  public void modifyMessageDeviceTagModel(){
    //更改条件
    Map<String, Object> map = new HashMap<String,Object>();
    map.put("tagId", "1");
    map.put("deviceId", 1);
    MessageDeviceTagModel model = new MessageDeviceTagModel();
    model.setTagId("3");
    model.setDeviceId(3);
    model.setCreateTime(new Timestamp(System.currentTimeMillis()));
    System.out.println(messageDeviceTagModelDao.updateEntity(map, model));
  }
  
  //查询
  @Test
  public void queryMessageDeviceTagModel(){
  //查询条件
    Map<String, Object> map = new HashMap<String,Object>();
    map.put("tagId", "2");
    map.put("deviceId", 2);
    List<MessageDeviceTagModel> modelList = messageDeviceTagModelDao.queryList(map, null);
    for(MessageDeviceTagModel model : modelList){
      System.out.println(JSON.toJSONString(model));
    }
  }
}
