package com.xiaodou.message.test;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.unitils.spring.annotation.SpringBean;

import com.alibaba.fastjson.JSON;
import com.xiaodou.message.dao.MessageDeviceModelDao;
import com.xiaodou.message.model.MessageDeviceModel;
import com.xiaodou.sms.BaseUnitils;
import com.xiaodou.sms.utils.TimeUtil;

public class MessageDeviceModelDaoTest extends BaseUnitils {
  @SpringBean("messageDeviceModelDao")
  MessageDeviceModelDao messageDeviceModelDao;
  /**
   * 增删改查
   */
  
  //添加消息推送的设备
  @Test
  public void addMessageDeviceModel(){
    MessageDeviceModel model = new MessageDeviceModel();
    model.setDeviceToken("dwa2d3-d3d32-32d4r3fefdf46y4s-f43f43-43ff4");
    model.setDeviceId("1,2");
    model.setUserId("2");
    model.setTelephone("15010678961");
    model.setCreateTime(new Timestamp(System.currentTimeMillis()));
    model = messageDeviceModelDao.addEntity(model);
    System.out.println(JSON.toJSONString(model));
  }
  //移除消息推送的设备
  @Test
  public void removeMessageDeviceModel(){
    String time = "2015-04-20 11:52:24";
    Map<String,Object> cond = new HashMap<String,Object>();
    cond.put("deviceToken", "dwa2d3-d3d32-32d4r3fefdf46y4s-f43f43-43ff4");
    cond.put("deviceId", "1,2");
    cond.put("userId", "2");
    cond.put("telephone", "15010678961");
    cond.put("createTime", new Timestamp(TimeUtil.parse_yyyy_MM_dd_HH_mm_ss(time).getTime()));
    System.out.println(messageDeviceModelDao.deleteList(cond));
  }
  
  //更改设备
  @Test
  public void modifyMessageDeviceModel(){
    //更改条件
    String time = "2015-04-20 12:03:12";
    Map<String,Object> cond = new HashMap<String,Object>();
    cond.put("deviceToken", "dwa2d3-d3d32-32d4r3fefdf46y4s-f43f43-43ff4");
    cond.put("deviceId", "1,2");
    cond.put("userId", "2");
    cond.put("telephone", "15010678961");
    cond.put("createTime", new Timestamp(TimeUtil.parse_yyyy_MM_dd_HH_mm_ss(time).getTime()));
    //更改内容
    MessageDeviceModel model = new MessageDeviceModel();
//    model.setDeviceToken("dwa2d3-d3d32-32d4r3fefdf46y4s-f43f43-43ff4");
    model.setDeviceId("1,2,3");
    model.setUserId("1");
    model.setTelephone("15011236535");
    model.setCreateTime(new Timestamp(System.currentTimeMillis()));
    System.out.println(messageDeviceModelDao.updateEntity(cond, model));
  }
  
  //查询设备
  @Test
  public void queryMessageDeviceModel(){
    //查询条件
    String time = "2015-04-20 12:03:12";
    Map<String,Object> cond = new HashMap<String,Object>();
    cond.put("deviceToken", "dwa2d3-d3d32-32d4r3fefdf46y4s-f43f43-43ff4");
    cond.put("deviceId", "1,2,3");
    cond.put("userId", "1");
    cond.put("telephone", "15011236535");
    cond.put("createTime", new Timestamp(TimeUtil.parse_yyyy_MM_dd_HH_mm_ss(time).getTime()));
    List<MessageDeviceModel> modelList = messageDeviceModelDao.queryList(cond, null);
    for(MessageDeviceModel model : modelList){
      System.out.println(model);
    }
  }
}
