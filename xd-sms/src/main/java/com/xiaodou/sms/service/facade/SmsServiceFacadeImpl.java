package com.xiaodou.sms.service.facade;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.xiaodou.common.util.StringUtils;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.sms.cache.SmsTemplateCache;
import com.xiaodou.sms.dao.ms.SmsChannelModelDao;
import com.xiaodou.sms.dao.ms.SmsLogModelDao;
import com.xiaodou.sms.dao.ms.SmsTaskModelDao;
import com.xiaodou.sms.dao.ms.SmsTemplateCategoryModelDao;
import com.xiaodou.sms.model.SmsChannelModel;
import com.xiaodou.sms.model.SmsLogModel;
import com.xiaodou.sms.model.SmsTaskModel;
import com.xiaodou.sms.model.SmsTemplateCategoryModel;
import com.xiaodou.sms.service.sender.SmsSenderService;

@Service("smsServiceFacade")
public class SmsServiceFacadeImpl implements ISmsServiceFacade {

  @Resource
  SmsTemplateCategoryModelDao categoryModelDao;

  @Resource
  SmsTaskModelDao smsTaskModelDao;

  @Resource
  SmsChannelModelDao smsChannelModelDao;

  @Resource
  SmsLogModelDao smsLogModelDao;

  @Resource
  SmsSenderService smsSenderService;

  @Resource(name="smsTemplateCache")
  SmsTemplateCache smsTemplateCache;

  @Override
  public SmsTemplateCategoryModel querySmsTemplateCategoryById(String templateId) {
    String sTemplate = null;
    sTemplate = smsTemplateCache.getTemplate(templateId);
    if (StringUtils.isJsonBlank(sTemplate)) {
      SmsTemplateCategoryModel template = categoryModelDao.querySmsTemplateCategoryById(templateId);
      sTemplate = FastJsonUtil.toJson(template);
      smsTemplateCache.cacheTemplate(templateId, sTemplate);
      return template;
    }
    return FastJsonUtil.fromJson(sTemplate, SmsTemplateCategoryModel.class);
  }

  @Override
  public List<SmsChannelModel> querySmsChannelModelList(Map<String, Object> queryCond,
      Map<String, Object> output) {
    return smsChannelModelDao.queryList(queryCond, output);
  }

  @Override
  public SmsTaskModel addSmsTaskModelEntity(SmsTaskModel task) {
    smsTaskModelDao.addTaskEntity(task);
    smsSenderService.addMessage(task);
    return task;
  }

  @Override
  public void deleteSmsTaskModelEntity(Long id) {
    Map<String, Object> input = new HashMap<String, Object>();
    input.put("id", id);
    deleteSmsTaskModelList(input);
  }

  @Override
  public void updateSmsTaskModelEntity(Map<String, Object> condition, SmsTaskModel value) {
    smsTaskModelDao.updateEntity(condition, value);
  }

  @Override
  public List<SmsTaskModel> querySmsTaskModelList(Map<String, Object> input,
      Map<String, Object> output) {
    return smsTaskModelDao.queryList(input, output);
  }

  @Override
  public void deleteSmsTaskModelList(Map<String, Object> input) {
    smsTaskModelDao.deleteList(input);
  }

  @Override
  public void addSmsLogModelEntity(SmsLogModel entity) {
    smsLogModelDao.addEntity(entity);
  }

  @Override
  public List<SmsChannelModel> queryNormalSmsChannelModel(String channelId) {
    Map<String, Object> queryCond = new HashMap<String, Object>();
    queryCond.put("id", channelId);
    queryCond.put("status", "1");
    return smsChannelModelDao.queryList(queryCond, null);
  }

  @Override
  public List<SmsChannelModel> queryNormalSmsChannelModel(List<String> channelId) {
    Map<String, Object> queryCond = new HashMap<String, Object>();
    queryCond.put("idList", channelId);
    queryCond.put("status", "1");
    return smsChannelModelDao.queryList(queryCond, null);
  }

}
