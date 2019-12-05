package com.xiaodou.dashboard.dao.alarm;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.google.common.collect.Lists;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.dashboard.dao.BaseDao;
import com.xiaodou.dashboard.dao.MongoConnection;
import com.xiaodou.dashboard.model.alarm.domain.AlarmEventDo;


/**
 * @name @see com.xiaodou.dashboard.dao.alarm.EventDao.java
 * @CopyRright (c) 2017 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年6月9日
 * @description 事件模型Dao
 * @version 1.0
 */
@Repository("alarmEventDao")
public class AlarmEventDao extends BaseDao<AlarmEventDo> {

  private String dbName = "alarm_db";

  private String collName = "alarm_event";

  public List<AlarmEventDo> getAllEventModel() {
    DBCursor eventModelCursor = getDBCollection().find();
    if (null == eventModelCursor || eventModelCursor.size() == 0) return null;
    List<AlarmEventDo> eventModelList = Lists.newArrayList();
    while (eventModelCursor.hasNext()) {
      DBObject eventModel = eventModelCursor.next();
      if (null == eventModel) continue;
      try {
        eventModelList.add(dbObject2Pojo(eventModel, AlarmEventDo.class));
      } catch (InstantiationException | IllegalAccessException e) {
        LoggerUtil.error("转换eventModel异常", e);
        continue;
      }
    }
    return eventModelList;
  }

  public List<AlarmEventDo> getEventModelByType(Integer type) {
    BasicDBObject query = new BasicDBObject();
    query.append("type", type);
    DBCursor eventModelCursor = getDBCollection().find(query, null);
    if (null == eventModelCursor || eventModelCursor.size() == 0) return null;
    List<AlarmEventDo> eventModelList = Lists.newArrayList();
    while (eventModelCursor.hasNext()) {
      DBObject eventModel = eventModelCursor.next();
      if (null == eventModel) continue;
      try {
        eventModelList.add(dbObject2Pojo(eventModel, AlarmEventDo.class));
      } catch (InstantiationException | IllegalAccessException e) {
        LoggerUtil.error("转换eventModel异常", e);
        continue;
      }
    }
    return eventModelList;
  }

  public AlarmEventDo getEventModel(String module, String name) {
    BasicDBObject dbModel = new BasicDBObject("module", module).append("name", name);
    DBObject eventModel = getDBCollection().findOne(dbModel);
    if (null == eventModel) return null;
    try {
      return dbObject2Pojo(eventModel, AlarmEventDo.class);
    } catch (InstantiationException | IllegalAccessException e) {
      LoggerUtil.error("转换eventModel异常", e);
      return null;
    }
  }

  public AlarmEventDo getEventModelById(String alarmEventId) {
    BasicDBObject dbModel = new BasicDBObject().append("alarmEventId", alarmEventId);
    DBObject eventModel = getDBCollection().findOne(dbModel);
    if (null == eventModel) return null;
    try {
      return dbObject2Pojo(eventModel, AlarmEventDo.class);
    } catch (InstantiationException | IllegalAccessException e) {
      LoggerUtil.error("转换eventModel异常", e);
      return null;
    }
  }

  public void addCountEventModel(String module, String name) {
    BasicDBObject doc = new BasicDBObject();
    BasicDBObject updateValue = new BasicDBObject();
    updateValue.put("count", 1);
    doc.put("$inc", updateValue);
    getDBCollection().update(new BasicDBObject().append("module", module).append("name", name),
        doc, false, false);
  }

  public void clearEventModel(String module, String name) {
    BasicDBObject doc = new BasicDBObject();
    BasicDBObject updateValue = new BasicDBObject();
    updateValue.put("count", 0d);
    updateValue.put("systime", System.currentTimeMillis());
    doc.put("$set", updateValue);
    getDBCollection().update(new BasicDBObject("module", module).append("name", name), doc, false,
        false);
  }

  public void clearTimeEventModel(String module, String name, Long timeLimit) {
    BasicDBObject doc = new BasicDBObject();
    BasicDBObject updateValue = new BasicDBObject();
    updateValue.put("count", 0d);
    updateValue.put("systime", System.currentTimeMillis());
    doc.put("$set", updateValue);
    getDBCollection().update(
        new BasicDBObject("module", module).append("name", name).append("systime",
            new BasicDBObject("$lte", timeLimit)), doc, false, false);
  }

  /**
   * 
   * getDBCollection
   * 
   * @Title: getDBCollection
   * @Description: TODO 获取一个collection
   * @return
   */
  public DBCollection getDBCollection() {
    try {
      return MongoConnection.getDbConn(dbName, collName).getDbCollection();
    } catch (Exception e) {
      LoggerUtil.error("获取mongodb连接失败", e);
    }
    return null;
  }

  public void updateEventModelById(AlarmEventDo model) {
    BasicDBObject doc = new BasicDBObject();
    BasicDBObject updateValue = new BasicDBObject();
    updateValue.put("rate", model.getRate());
    updateValue.put("threshold", model.getThreshold());
    updateValue.put("alarmPolicyId", model.getAlarmPolicyId());
    doc.put("$set", updateValue);
    getDBCollection().update(new BasicDBObject().append("alarmEventId", model.getAlarmEventId()),
        doc);
  }

  public void deleteEventModelById(String eventId) {
    getDBCollection().remove(new BasicDBObject("alarmEventId", eventId));
  }

}
