package com.xiaodou.sms.dao;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.unitils.spring.annotation.SpringBean;

import com.alibaba.fastjson.JSON;
import com.xiaodou.sms.BaseUnitils;
import com.xiaodou.sms.dao.ms.SmsTaskModelDao;
import com.xiaodou.sms.dao.ms.SmsTemplateCategoryModelDao;
import com.xiaodou.sms.model.SmsTaskModel;
import com.xiaodou.sms.model.SmsTemplateCategoryModel;

public class SmsTemplateCategoryDaoTest extends BaseUnitils {

  @SpringBean("smsTemplateCategoryModelDao")
  SmsTemplateCategoryModelDao categoryModelDao;

  @SpringBean("smsTaskModelDao")
  SmsTaskModelDao smsTaskModelDao;

  @Test
  public void querySmsTemplateCategory() {
    SmsTaskModel task = new SmsTaskModel();
    Map<String, Object> variables = new HashMap<String, Object>();
    variables.put("checkcode", "123456");
    // 查询template和template_type表的渠道号和messageContent
    SmsTemplateCategoryModel category = categoryModelDao.querySmsTemplateCategoryById("5");

    String message = category.getMessageContent() + variables.get("checkcode");
    task.setMessage(message);
    task.setChannelId(category.getCateGory().getChannelIds());
    task.setTemplateId(Integer.valueOf("5"));
    task.setMobile("12345678921");
    task.setStatus(0);
    task.setCreateTime(new Timestamp(System.currentTimeMillis()));
    task.setCanRetryTime(5);
    task.setTemplateTypeId(category.getCateGory().getId());
    // 将产生的短信内容(验证码)内容写入task中
    task = smsTaskModelDao.addEntity(task);
    System.out.println(JSON.toJSONString(task));
  }
}
