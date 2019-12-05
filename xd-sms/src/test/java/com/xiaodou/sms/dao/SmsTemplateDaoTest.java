package com.xiaodou.sms.dao;

import java.sql.Timestamp;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.unitils.spring.annotation.SpringBean;

import com.xiaodou.sms.BaseUnitils;
import com.xiaodou.sms.dao.ms.SmsTemplateModelDao;
import com.xiaodou.sms.model.SmsTemplateModel;


public class SmsTemplateDaoTest extends BaseUnitils {
  @SpringBean("smsTemplateModelDao")
  SmsTemplateModelDao smsTemplateDao;
  /**
   * 按条件查询template
   */
  @Test
  public void queryTemplateList(){
    SmsTemplateModel templateModel = new SmsTemplateModel();
//    templateModel.setId(1);
    templateModel.setStatus(1);
    templateModel.setDescription("dadadwe");
    Map<String, Object> inputArgument = new HashMap<String,Object>();
    inputArgument.put("input", templateModel);
    List<SmsTemplateModel> templateList = smsTemplateDao.queryList(inputArgument, null);
    if(null==templateList||templateList.size()<=0){
      System.out.println("error");
    }
    for(SmsTemplateModel template : templateList){
      System.out.println(template);
    }
  }
  
  /**
   * 添加template
   */
  @Test
  public void addTemplate(){
    SmsTemplateModel templateModel = new SmsTemplateModel();
    templateModel.setMessageContent("asda");
    templateModel.setDescription("dadadwe");
    templateModel.setTypeId(2);
    templateModel.setCreateTime(new Timestamp(System.currentTimeMillis()));
    templateModel.setStatus(1);
    templateModel = smsTemplateDao.addEntity(templateModel);
    System.out.println(templateModel);
  }
  /**
   * 删除template
   * @param queryCond
   * @return
   */
  @Test
  public void removeTemplate(){
    SmsTemplateModel model = new SmsTemplateModel();
    model.setId(2);
    model.setMessageContent("asda");
    model.setDescription("dadadwe");
    model.setStatus(1);
    model.setTypeId(2);
    Map<String,Object> cond = new HashMap<String,Object>();
    cond.put("id", 3L);
    System.out.println(smsTemplateDao.deleteList(cond));
  }
  
  /**
   * 修改template
   * @throws ParseException 
   */
  @Test
  public void modifyTemplate() throws ParseException{
    SmsTemplateModel model = new SmsTemplateModel();
    model.setMessageContent("sadsd");
    model.setDescription("32");
    model.setCreateTime(new Timestamp(System.currentTimeMillis()));
    Map<String,Object> cond = new HashMap<String,Object>();
    cond.put("description", "3123sdsad4123");
    System.out.println(smsTemplateDao.updateEntity(cond, model));
    
  }
}
