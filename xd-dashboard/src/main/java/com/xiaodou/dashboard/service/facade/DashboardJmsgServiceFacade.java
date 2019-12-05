package com.xiaodou.dashboard.service.facade;

import java.util.List;
import java.util.Map;

import com.xiaodou.dashboard.model.jsmg.JmsgMessageBody;
import com.xiaodou.dashboard.model.jsmg.JmsgMessageConfig;
import com.xiaodou.dashboard.model.jsmg.JmsgMessageConsumersConfig;
import com.xiaodou.dashboard.model.jsmg.MessageLog;
import com.xiaodou.dashboard.model.jsmg.MessageQueueSetting;
import com.xiaodou.dashboard.model.jsmg.Relation;
import com.xiaodou.dashboard.model.jsmg.ServerQueueSetting;
import com.xiaodou.summer.dao.pagination.Page;
import com.xiaodou.summer.dao.param.IQueryParam;


public interface DashboardJmsgServiceFacade {
  List<ServerQueueSetting> getAllServerQueueSettingList(Map<String, Object> inputArgument,
      Map<String, Object> outputField);

  ServerQueueSetting getServerQueueSettingListById(String id);

  void addServerQueueSetting(ServerQueueSetting s);

  void editServerQueueSetting(ServerQueueSetting s);

  void delServerQueueSetting(ServerQueueSetting s);

  List<MessageQueueSetting> getAllQueueList(Map<String, Object> inputArgument,
      Map<String, Object> outputField);

  MessageQueueSetting getQueueListById(String id);

  void addQueue(MessageQueueSetting s);

  void editQueue(MessageQueueSetting s);

  void delQueue(MessageQueueSetting s);

  List<Relation> getListByCond(Map<String, Object> inputArgument, Map<String, Object> outputField);

  void addRelation(Relation s);

  void delRelation(Map<String, Object> cond);

  List<JmsgMessageConfig> getAllMessageConfig(Map<String, Object> inputArgument,
      Map<String, Object> outputField);

  JmsgMessageConfig getMessageConfigById(String id);

  void addMessageConfig(JmsgMessageConfig s);

  void editMessageConfig(JmsgMessageConfig s);

  void delMessageConfig(JmsgMessageConfig s);

  List<JmsgMessageConsumersConfig> getAllMessageConsumersConfig(Map<String, Object> inputArgument,
      Map<String, Object> outputField);

  JmsgMessageConsumersConfig getMessageConsumersConfig(String id);

  void addMessageConsumersConfig(JmsgMessageConsumersConfig s);

  void editMessageConsumersConfig(JmsgMessageConsumersConfig s);

  void delMessageConsumersConfig(JmsgMessageConsumersConfig s);

  List<JmsgMessageBody> getAllMessageBodyList(Map<String, Object> inputArgument,
      Map<String, Object> outputField);

  Page<JmsgMessageBody> getPageMessageBody(IQueryParam param, Page<JmsgMessageBody> page);

  JmsgMessageBody getMessageBodyById(String id);


  List<MessageLog> getAllMessageLogList(Map<String, Object> inputArgument,
      Map<String, Object> outputField);

  MessageLog getMessageLogById(String id);
}
