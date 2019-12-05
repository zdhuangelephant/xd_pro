package com.xiaodou.dashboard.dao.alarm;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.dashboard.dao.BaseDao;
import com.xiaodou.dashboard.dao.MongoConnection;
import com.xiaodou.dashboard.model.alarm.domain.AlarmPolicyDo;

/**
 * @name @see com.xiaodou.dashboard.dao.alarm.AlarmPolicyDao.java
 * @CopyRright (c) 2017 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年6月9日
 * @description 报警策略模型Dao
 * @version 1.0
 */
@Service("alarmPolicyDao")
public class AlarmPolicyDao extends BaseDao<AlarmPolicyDo> {

  private String dbName = "alarm_db";

  private String collName = "alarm_policy";

  public List<AlarmPolicyDo> getAllPolicyModel() {
    DBCursor policyCursor = getDBCollection().find();
    if (null == policyCursor || policyCursor.size() == 0) return null;
    List<AlarmPolicyDo> policyList = Lists.newArrayList();
    while (policyCursor.hasNext()) {
      DBObject policyModel = policyCursor.next();
      if (null == policyModel) continue;
      try {
        policyList.add(dbObject2Pojo(policyModel, AlarmPolicyDo.class));
      } catch (InstantiationException | IllegalAccessException e) {
        LoggerUtil.error("转换policyModel异常", e);
        continue;
      }
    }
    return policyList;
  }

  @Override
  public DBCollection getDBCollection() {
    try {
      return MongoConnection.getDbConn(dbName, collName).getDbCollection();
    } catch (Exception e) {
      LoggerUtil.error("获取mongodb连接失败", e);
    }
    return null;
  }

  public AlarmPolicyDo getPolicyModelById(String policyId) {
    BasicDBObject dbModel = new BasicDBObject("alarmPolicyId", policyId);
    DBObject policyModel = getDBCollection().findOne(dbModel);
    if (null == policyModel) return null;
    try {
      return dbObject2Pojo(policyModel, AlarmPolicyDo.class);
    } catch (InstantiationException | IllegalAccessException e) {
      LoggerUtil.error("转换policyModel异常", e);
      return null;
    }
  }

  public void updatePolicyModelById(AlarmPolicyDo model) {
    BasicDBObject updateValue =
        new BasicDBObject().append("alarmPolicyId", model.getAlarmPolicyId());
    if (null != model.getAlarmPolicyName())
      updateValue.append("alarmPolicyName", model.getAlarmPolicyName());
    if (null != model.getStarttime()) updateValue.append("starttime", model.getStarttime());
    if (null != model.getEndtime()) updateValue.append("endtime", model.getEndtime());
    if (null != model.getMail()) updateValue.append("mail", model.getMail());
    if (null != model.getMessage()) updateValue.append("message", model.getMessage());
    if (null != model.getDingURL()) updateValue.append("dingURL", model.getDingURL());
    if (null != model.getGroup()) updateValue.append("group", model.getGroup());
    updateValue.append("updateTime", new Timestamp(System.currentTimeMillis()));
    getDBCollection().update(new BasicDBObject().append("alarmPolicyId", model.getAlarmPolicyId()),
        updateValue);
  }

  public void deletePolicyModelById(String policyId) {
    getDBCollection().remove(new BasicDBObject("alarmPolicyId", policyId));
  }

}
