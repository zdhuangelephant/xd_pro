package com.xiaodou.control.dao.server;

import org.springframework.stereotype.Repository;

import com.xiaodou.control.model.server.ServerGroupModel;
import com.xiaodou.summer.dao.mongo.MongoBaseDao;

/**
 * 
 * 
 * 
 * @Title:
 * @Description: TODO serverGroupDao层
 * @author zhouhuan
 * @date 2016/7/25
 * 
 */

@Repository("serverGroupDao")
public class ServerGroupDao extends MongoBaseDao<ServerGroupModel> {


 /* *//**
   * 增加服务组
   * 
   * @author zhouhuan
   * @Time 2016-8-2
   *//*
  public void addGroup(ServerGroupRequest s) {
    DBObject log = new BasicDBObject();
    log.put("groupId", s.getGroupId());
    log.put("groupName", s.getGroupName());
    log.put("groupType", s.getGroupType());
    log.put("groupServiceType",s.getGroupServiceType());
    getDBCollection().insert(log);
  }

  *//**
   * 修改通用接口
   * 
   * @author zhouhuan
   * @Time 2016-8-2
   *//*
  public String edit(DBObject updateCondition, DBObject updatedValue) {
    DBObject updateSetValue = new BasicDBObject("$set", updatedValue);
    getDBCollection().update(updateCondition, updateSetValue, false, false);
    return "success";
  }

  *//**
   * 通用查询接口
   * 
   * @author zhouhuan
   * @Time 2016-8-2
   *//*
  public List<ServerGroupModel> getListByCondition(DBObject queryCondition, DBObject order) {
    List<ServerGroupModel> list = new ArrayList<ServerGroupModel>();
    List<DBObject> groupList = getDBCollection().find(queryCondition).sort(order).toArray();
    if (null == groupList) return null;
    try {
      for (DBObject group : groupList) {
        ServerGroupModel s = dbObject2Pojo(group, ServerGroupModel.class);
        list.add(s);
      }
      return list;
    } catch (InstantiationException | IllegalAccessException e) {
      LoggerUtil.error("转换ServerGroupModel异常", e);
      return null;
    }
  }

  *//**
   * 删除服务
   * 
   * @author zhouhuan
   * @Time 2016-8-2
   *//*
  public String delServerGroup(ServerGroupRequest s) {
    getDBCollection().remove(new BasicDBObject("groupId", s.getGroupId()));
    return "success";
  }

  *//**
   * 
   * getDBCollection
   * 
   * @Title: getDBCollection
   * @Description: TODO 获取一个collection
   * @return
   *//*
  public DBCollection getDBCollection() {
    try {
      return MongoConnection.getDbConn(dbName, collName).getDbCollection();
    } catch (Exception e) {
      LoggerUtil.error("获取mongodb连接失败", e);
    }
    return null;
  }
*/
}
