package com.xiaodou.sms.dao;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.unitils.spring.annotation.SpringBean;

import com.xiaodou.sms.BaseUnitils;
import com.xiaodou.sms.dao.ms.SmsTemplateTypeModelDao;
import com.xiaodou.sms.model.SmsTemplateTypeModel;

public class SmsTemplateTypeDaoTest extends BaseUnitils {
  @SpringBean("smsTemplateTypeModelDao")
  SmsTemplateTypeModelDao smsTemplateTypeDao;
  /**
   * 按条件查询templateType
   */
  @Test
  public void querytemplateTypeList(){
    SmsTemplateTypeModel templateTypeModel = new SmsTemplateTypeModel();
    templateTypeModel.setId(6);
    templateTypeModel.setName("lisi");
    templateTypeModel.setDescription("dsade");
    templateTypeModel.setChannelIds("231");
    templateTypeModel.setCacheTime(321);
    templateTypeModel.setRetryTime(3);
    Map<String, Object> inputArgument = new HashMap<String,Object>();
    inputArgument.put("input", templateTypeModel);
    List<SmsTemplateTypeModel> templateTypeList = smsTemplateTypeDao.queryList(inputArgument,null);
    if(null==templateTypeList||templateTypeList.size()<=0){
      System.out.println("error");
    }
    for(SmsTemplateTypeModel templateType : templateTypeList){
      System.out.println(templateType);
    }
  }
  
  /**
   * 添加templateType
   */
  @Test
  public void addtemplateType(){
    SmsTemplateTypeModel templateTypeModel = new SmsTemplateTypeModel();
    templateTypeModel.setName("lisi");
    templateTypeModel.setDescription("123456");
    templateTypeModel.setChannelIds("dada");
    templateTypeModel.setCreateTime(new Timestamp(System.currentTimeMillis()));
    templateTypeModel.setRetryTime(3);
    templateTypeModel.setCacheTime(5);
    templateTypeModel = smsTemplateTypeDao.addEntity(templateTypeModel);
    System.out.println(templateTypeModel);
  }
  /**
   * 删除templateType
   * @param queryCond
   * @return
   */
  @Test
  public void removetemplateType(){
    Map<String,Object> cond = new HashMap<String,Object>();
    cond.put("id",2L);
    System.out.println(smsTemplateTypeDao.deleteList(cond));
  }
  
  /**
   * 修改templateType
   */
  @Test
  public void modifytemplateType(){
    SmsTemplateTypeModel model = new SmsTemplateTypeModel();
    model.setName("lisi");
    model.setDescription("dsade");
    model.setChannelIds("231");
    model.setCacheTime(321);
    model.setRetryTime(3);
    model.setCreateTime(new Timestamp(System.currentTimeMillis()));
    Map<String,Object> cond = new HashMap<String,Object>();
    cond.put("channel_ids", "1,3,7");
    System.out.println(smsTemplateTypeDao.updateEntity(cond, model));
    
  }
}
