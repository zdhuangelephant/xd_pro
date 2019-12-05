package com.xiaodou.sms.service.facade;

import java.util.List;
import java.util.Map;

import com.xiaodou.sms.model.SmsChannelModel;
import com.xiaodou.sms.model.SmsLogModel;
import com.xiaodou.sms.model.SmsTaskModel;
import com.xiaodou.sms.model.SmsTemplateCategoryModel;

/**
 * @name @see com.xiaodou.sms.service.facade.ISmsServiceFacade.java
 * @CopyRright (c) 2015 by <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2015年7月31日
 * @description 短信服务Facade层门面
 * @version 1.0
 */
public interface ISmsServiceFacade {

  /**
   * 根据模板ID查询短信模板
   * 
   * @param templateId
   * @return
   */
  public SmsTemplateCategoryModel querySmsTemplateCategoryById(String templateId);

  /**
   * 根据查询条件查询短信渠道列表
   * 
   * @param queryCond
   * @param object
   * @return
   */
  public List<SmsChannelModel> querySmsChannelModelList(Map<String, Object> queryCond,
      Map<String, Object> output);

  /**
   * 添加短信任务
   * 
   * @param task
   * @return
   */
  public SmsTaskModel addSmsTaskModelEntity(SmsTaskModel task);

  /**
   * 删除短信任务
   * 
   * @param id 任务主键ID
   */
  public void deleteSmsTaskModelEntity(Long id);

  /**
   * 更新短信任务状态
   * 
   * @param condition
   * @param value
   */
  public void updateSmsTaskModelEntity(Map<String, Object> condition, SmsTaskModel value);

  /**
   * 查询短信任务列表
   * 
   * @param input
   * @param object
   * @return
   */
  public List<SmsTaskModel> querySmsTaskModelList(Map<String, Object> input,
      Map<String, Object> output);

  /**
   * 删除短信任务列表
   * 
   * @param input
   */
  public void deleteSmsTaskModelList(Map<String, Object> input);

  /**
   * 添加短信日志
   * 
   * @param entity
   */
  public void addSmsLogModelEntity(SmsLogModel entity);

  /**
   * 查询可用的短信通道
   * 
   * @param channelId 短信通道号
   * @return
   */
  public List<SmsChannelModel> queryNormalSmsChannelModel(String channelId);

  /**
   * 查询可用的短信通道
   * 
   * @param channelId 短信通道号列表
   * @return
   */
  public List<SmsChannelModel> queryNormalSmsChannelModel(List<String> channelId);

}
