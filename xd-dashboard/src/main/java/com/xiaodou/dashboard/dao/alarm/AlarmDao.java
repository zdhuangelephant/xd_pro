//package com.xiaodou.dashboard.dao.alarm;
//
//import java.util.List;
//
//import org.springframework.stereotype.Repository;
//
//import com.google.common.collect.Lists;
//import com.mongodb.BasicDBObject;
//import com.mongodb.DBCollection;
//import com.mongodb.DBCursor;
//import com.mongodb.DBObject;
//import com.xiaodou.common.util.log.LoggerUtil;
//import com.xiaodou.dashboard.dao.BaseDao;
//import com.xiaodou.dashboard.dao.MongoConnection;
//import com.xiaodou.dashboard.vo.alarm.AlarmVo;
//
///**
// * 
// * HealthCheckDao
// * 
// * @Title: HealthCheckDao
// * @Description: TODO 健康检查dao层
// * @author Bin.Tian
// * @date 2015年3月25日 下午1:30:40
// * 
// */
//
//@Repository("alarmDao")
//public class AlarmDao extends BaseDao<AlarmVo> {
//
//  private String dbName = "alarm_db";
//
//  private String collName = "alarm_record";
//
//  public List<AlarmVo> getAllAlarmVo() {
//    DBCursor alarmVoCursor = getDBCollection().find();
//    if (null == alarmVoCursor || alarmVoCursor.size() == 0) return null;
//    List<AlarmVo> alarmVoList = Lists.newArrayList();
//    while (alarmVoCursor.hasNext()) {
//      DBObject alarmVo = alarmVoCursor.next();
//      if (null == alarmVo) continue;
//      try {
//        alarmVoList.add(dbObject2Pojo(alarmVo, AlarmVo.class));
//      } catch (InstantiationException | IllegalAccessException e) {
//        LoggerUtil.error("转换alarmVo异常", e);
//        continue;
//      }
//    }
//    return alarmVoList;
//  }
//
//  public AlarmVo getAlarmVo(String module, String name) {
//    BasicDBObject dbModel = new BasicDBObject("module", module).append("name", name);
//    DBObject alarmVo = getDBCollection().findOne(dbModel);
//    if (null == alarmVo) return null;
//    try {
//      return dbObject2Pojo(alarmVo, AlarmVo.class);
//    } catch (InstantiationException | IllegalAccessException e) {
//      LoggerUtil.error("转换alarmVo异常", e);
//      return null;
//    }
//  }
//
//  public void addCountAlarmVo(String module, String name) {
//    Integer count = 1;
//    AlarmVo vo = getAlarmVo(module, name);
//    if (null == vo) return;
//    getDBCollection().update(new BasicDBObject().append("module", module).append("name", name),
//        new BasicDBObject().append("$inc", new BasicDBObject("count", count)));
//  }
//
//  public void clearAlarmVo(String module, String name) {
//    getDBCollection().update(
//        new BasicDBObject("module", module).append("name", name),
//        new BasicDBObject("module", module).append("name", name).append("count", 0)
//            .append("systime", System.currentTimeMillis()), false, false);
//  }
//
//  public void clearTimeAlarmVo(String module, String name, Long timeLimit) {
//    getDBCollection().update(
//        new BasicDBObject("module", module).append("name", name).append("systime",
//            new BasicDBObject("$lte", timeLimit)),
//        new BasicDBObject("module", module).append("name", name).append("count", 0)
//            .append("systime", System.currentTimeMillis()), false, false);
//  }
//
//  /**
//   * 
//   * getDBCollection
//   * 
//   * @Title: getDBCollection
//   * @Description: TODO 获取一个collection
//   * @return
//   */
//  public DBCollection getDBCollection() {
//    try {
//      return MongoConnection.getDbConn(dbName, collName).getDbCollection();
//    } catch (Exception e) {
//      LoggerUtil.error("获取mongodb连接失败", e);
//    }
//    return null;
//  }
//
//  /**
//   * 
//   * getCollectionName
//   * 
//   * @Title: getCollectionName
//   * @Description: TODO 获取 healthcheck_yyMMdd 格式的collName;
//   * @return
//   */
//  // private String getCollectionName(String collName) {
//  // String date = DateFormatUtils.format(new Date(), "_yyMMdd");
//  // return collName + date;
//  // }
//}
