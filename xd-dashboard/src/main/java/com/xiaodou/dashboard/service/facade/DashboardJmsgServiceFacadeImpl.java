package com.xiaodou.dashboard.service.facade;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.xiaodou.dashboard.dao.jmsg.JmsgMessageBodyDao;
import com.xiaodou.dashboard.dao.jmsg.JmsgMessageConfigDao;
import com.xiaodou.dashboard.dao.jmsg.JmsgMessageConsumersConfigDao;
import com.xiaodou.dashboard.dao.jmsg.MessageLogDao;
import com.xiaodou.dashboard.dao.jmsg.MessageQueueSettingDao;
import com.xiaodou.dashboard.dao.jmsg.RelationDao;
import com.xiaodou.dashboard.dao.jmsg.ServerQueueSettingDao;
import com.xiaodou.dashboard.model.jsmg.JmsgMessageBody;
import com.xiaodou.dashboard.model.jsmg.JmsgMessageConfig;
import com.xiaodou.dashboard.model.jsmg.JmsgMessageConsumersConfig;
import com.xiaodou.dashboard.model.jsmg.MessageLog;
import com.xiaodou.dashboard.model.jsmg.MessageQueueSetting;
import com.xiaodou.dashboard.model.jsmg.Relation;
import com.xiaodou.dashboard.model.jsmg.ServerQueueSetting;
import com.xiaodou.summer.dao.pagination.Page;
import com.xiaodou.summer.dao.param.IQueryParam;
import com.xiaodou.summer.dao.param.QueryParam;


@Service("dashboardJmsgServiceFacade")
public class DashboardJmsgServiceFacadeImpl implements DashboardJmsgServiceFacade {
  @Resource
  ServerQueueSettingDao serverQueueSettingDao;
  @Resource
  MessageQueueSettingDao messageQueueSetting;
  @Resource
  RelationDao relationDao;
  @Resource
  JmsgMessageConfigDao jmsgMessageConfigDao;
  @Resource
  JmsgMessageConsumersConfigDao jmsgMessageConsumersConfigDao;
  @Resource
  JmsgMessageBodyDao jmsgMessageBodyDao;
  @Resource
  MessageLogDao messageLogDao;

  @Override
  public List<ServerQueueSetting> getAllServerQueueSettingList(Map<String, Object> inputArgument,
      Map<String, Object> outputField) {
    return serverQueueSettingDao.queryList(inputArgument, outputField);
  }

  @Override
  public ServerQueueSetting getServerQueueSettingListById(String id) {
    ServerQueueSetting entity = new ServerQueueSetting();
    entity.setId(id);
    return serverQueueSettingDao.findEntityById(entity);
  }

  @Override
  public void addServerQueueSetting(ServerQueueSetting s) {
    serverQueueSettingDao.addEntity(s);
  }

  @Override
  public void editServerQueueSetting(ServerQueueSetting s) {
    Map<String, Object> cond = new HashMap<String, Object>();
    cond.put("id", s.getId());
    serverQueueSettingDao.updateEntity(cond, s);

  }

  @Override
  public void delServerQueueSetting(ServerQueueSetting s) {
    serverQueueSettingDao.deleteEntityById(s);
  }

  @Override
  public List<MessageQueueSetting> getAllQueueList(Map<String, Object> inputArgument,
      Map<String, Object> outputField) {
    return messageQueueSetting.queryList(inputArgument, outputField);
  }

  @Override
  public MessageQueueSetting getQueueListById(String id) {
    MessageQueueSetting entity = new MessageQueueSetting();
    entity.setId(id);
    return messageQueueSetting.findEntityById(entity);
  }

  @Override
  public void addQueue(MessageQueueSetting s) {
    messageQueueSetting.addEntity(s);
  }

  @Override
  public void editQueue(MessageQueueSetting s) {
    Map<String, Object> cond = new HashMap<String, Object>();
    cond.put("id", s.getId());
    messageQueueSetting.updateEntity(cond, s);

  }

  @Override
  public void delQueue(MessageQueueSetting s) {
    messageQueueSetting.deleteEntityById(s);

  }

  @Override
  public List<Relation> getListByCond(Map<String, Object> inputArgument,
      Map<String, Object> outputField) {
    return relationDao.queryList(inputArgument, outputField);
  }

  @Override
  public void addRelation(Relation s) {
    relationDao.addEntity(s);
  }

  @Override
  public void delRelation(Map<String, Object> cond) {
	  IQueryParam param = new QueryParam();
	  param.addInputs(cond);
      relationDao.deleteEntityByCond(param);
  }

  @Override
  public List<JmsgMessageConfig> getAllMessageConfig(Map<String, Object> inputArgument,
      Map<String, Object> outputField) {
    return jmsgMessageConfigDao.queryList(inputArgument, outputField);
  }

  @Override
  public JmsgMessageConfig getMessageConfigById(String id) {
    JmsgMessageConfig entity = new JmsgMessageConfig();
    entity.setId(id);
    return jmsgMessageConfigDao.findEntityById(entity);
  }

  @Override
  public void addMessageConfig(JmsgMessageConfig s) {
    jmsgMessageConfigDao.addEntity(s);
  }

  @Override
  public void editMessageConfig(JmsgMessageConfig s) {
    Map<String, Object> cond = new HashMap<String, Object>();
    cond.put("id", s.getId());
    jmsgMessageConfigDao.updateEntity(cond, s);
  }

  @Override
  public void delMessageConfig(JmsgMessageConfig s) {
    jmsgMessageConfigDao.deleteEntityById(s);

  }

  @Override
  public List<JmsgMessageConsumersConfig> getAllMessageConsumersConfig(
      Map<String, Object> inputArgument, Map<String, Object> outputField) {
    return jmsgMessageConsumersConfigDao.queryList(inputArgument, outputField);
  }

  @Override
  public JmsgMessageConsumersConfig getMessageConsumersConfig(String id) {
    JmsgMessageConsumersConfig entity = new JmsgMessageConsumersConfig();
    entity.setId(id);
    return jmsgMessageConsumersConfigDao.findEntityById(entity);
  }

  @Override
  public void addMessageConsumersConfig(JmsgMessageConsumersConfig s) {
    jmsgMessageConsumersConfigDao.addEntity(s);
  }

  @Override
  public void editMessageConsumersConfig(JmsgMessageConsumersConfig s) {
    Map<String, Object> cond = new HashMap<String, Object>();
    cond.put("id", s.getId());
    jmsgMessageConsumersConfigDao.updateEntity(cond, s);
  }

  @Override
  public void delMessageConsumersConfig(JmsgMessageConsumersConfig s) {
    jmsgMessageConsumersConfigDao.deleteEntityById(s);
  }

  @Override
  public List<JmsgMessageBody> getAllMessageBodyList(Map<String, Object> inputArgument,
      Map<String, Object> outputField) {
    return jmsgMessageBodyDao.queryList(inputArgument, outputField);
  }

  @Override
  public Page<JmsgMessageBody> getPageMessageBody(IQueryParam param, Page<JmsgMessageBody> page) {
    return jmsgMessageBodyDao.findEntityListByCond(param, page);
  }

  @Override
  public JmsgMessageBody getMessageBodyById(String id) {
    JmsgMessageBody entity = new JmsgMessageBody();
    entity.setMessageId(id);
    return jmsgMessageBodyDao.findEntityById(entity);
  }

  @Override
  public List<MessageLog> getAllMessageLogList(Map<String, Object> inputArgument,
      Map<String, Object> outputField) {
    return messageLogDao.queryList(inputArgument, outputField);
  }

  @Override
  public MessageLog getMessageLogById(String id) {
    MessageLog entity = new MessageLog();
    entity.setMessageId(id);
    return messageLogDao.findEntityById(entity);
  }

}
