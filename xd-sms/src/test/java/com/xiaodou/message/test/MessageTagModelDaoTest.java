package com.xiaodou.message.test;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.unitils.spring.annotation.SpringBean;

import com.alibaba.fastjson.JSON;
import com.xiaodou.message.dao.MessageTagModelDao;
import com.xiaodou.message.model.MessageTagModel;
import com.xiaodou.sms.BaseUnitils;
import com.xiaodou.sms.utils.TimeUtil;

public class MessageTagModelDaoTest extends BaseUnitils {
  @SpringBean("messageTagModelDao")
  MessageTagModelDao messageTagModelDao;
  /**
   * 增删改查
   */
  
  //添加
  @Test
  public void addMessageTagModel(){
    MessageTagModel model = new MessageTagModel();
    model.setTagName("asd");
    model.setStatus(1);
    model.setCreateTime(new Timestamp(System.currentTimeMillis()));
    System.out.println(JSON.toJSONString(messageTagModelDao.addEntity(model)));
  }
  
  //删除
  @Test
  public void removeMessageTagModel(){
    //删除条件
    String time = "2015-04-20 13:44:42";
    Map<String,Object> cond = new HashMap<String,Object>();
    cond.put("tagName","asd");
    cond.put("status",1);
    cond.put("createTime",new Timestamp(TimeUtil.parse_yyyy_MM_dd_HH_mm_ss(time).getTime()));
    System.out.println(messageTagModelDao.deleteList(cond));
  }
  
  //更改
  @Test
  public void modifyMessageTagModel(){
    //更改条件
    Map<String,Object> cond = new HashMap<String,Object>();
    cond.put("tagName", "asd");
    cond.put("status", 1);
    //更改内容
    MessageTagModel model = new MessageTagModel();
    model.setTagName("daefaf");
    model.setStatus(3);
    model.setCreateTime(new Timestamp(System.currentTimeMillis()));
    System.out.println(messageTagModelDao.updateEntity(cond, model));
  }
  
  //查询
  @Test
  public void queryMessageTagModel(){
    String time = "2015-04-20 13:52:21";
    Map<String,Object> cond = new HashMap<String,Object>();
    cond.put("tagName", "daefaf");
    cond.put("status", 3);
    cond.put("createTime", new Timestamp(TimeUtil.parse_yyyy_MM_dd_HH_mm_ss(time).getTime()));
    List<MessageTagModel> modelList = messageTagModelDao.queryList(cond, null);
    for(MessageTagModel model : modelList){
      System.out.println(JSON.toJSONString(model));
    }
  }
}
