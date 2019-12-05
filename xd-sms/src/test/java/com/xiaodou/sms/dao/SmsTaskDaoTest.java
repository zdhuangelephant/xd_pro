package com.xiaodou.sms.dao;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.unitils.spring.annotation.SpringBean;

import com.alibaba.fastjson.JSON;
import com.xiaodou.sms.BaseUnitils;
import com.xiaodou.sms.dao.ms.SmsTaskModelDao;
import com.xiaodou.sms.model.SmsTaskModel;


public class SmsTaskDaoTest extends BaseUnitils {
  @SpringBean("smsTaskModelDao")
  SmsTaskModelDao smsTaskDao;
  /**
   * 按条件查询task
   */
  @Test
  public void queryTaskList(){
    Map<String, Object> input = new HashMap<String,Object>();
    input.put("message", "asda123456");
    List<SmsTaskModel> taskList = smsTaskDao.queryList(input, null);
    for(SmsTaskModel task : taskList){
      System.out.println(JSON.toJSONString(task));
      System.out.println(task.getSmsChannelModel().getMerchantId());
    }
  }
  
  /**
   * 添加task
   */
  @Test
  public void addTask(){
    SmsTaskModel taskModel = new SmsTaskModel();
    taskModel.setMessage("12345");
    taskModel.setChannelId("1");
    taskModel.setTemplateId(1);
    taskModel.setMobile("15010678961");
    taskModel.setCreateTime(new Timestamp(System.currentTimeMillis()));
    taskModel.setCanRetryTime(10);
    taskModel.setTemplateTypeId(1);
    taskModel.setStatus(0);
    taskModel = smsTaskDao.addEntity(taskModel);
    System.out.println(taskModel);
  }
  /**
   * 删除task
   * @param queryCond
   * @return
   */
  @Test
  public void removeTask(){
    Map<String,Object> cond = new HashMap<String,Object>();
    cond.put("message", 1234567);
    System.out.println(smsTaskDao.deleteList(cond));
  }
  
  /**
   * 修改task
   */
  @Test
  public void modifyTask(){
    SmsTaskModel model = new SmsTaskModel();
    model.setMessage("1234567545");
    model.setChannelId("2");
    model.setTemplateId(3);
    model.setMobile("21312421");
    model.setCreateTime(new Timestamp(System.currentTimeMillis()));
    model.setCanRetryTime(3);
    model.setTemplateTypeId(2);
    model.setStatus(1);
    Map<String,Object> cond = new HashMap<String,Object>();
    cond.put("message",12345);
    System.out.println(smsTaskDao.updateEntity(cond, model));
    
  }
}
