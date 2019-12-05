package com.xiaodou.userCenter.dao;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.xiaodou.userCenter.model.UserModel;

@Repository("userModelDao")
public class UserModelDao extends BaseProcessDao<UserModel> {

  public UserModel addOfficialEntity(UserModel entity) {
    this.getSqlSession().insert(this.getEntityClass().getSimpleName() + ".addOfficialEntity",
        entity);
    return entity;
  }

  public int updateOfficialInfo(Map<String, Object> condition, UserModel value) {
    Map<String, Object> mapParam = new HashMap<String, Object>();
    mapParam.put("input", condition);
    mapParam.put("value", value);
    return getSqlSession().update(this.getEntityClass().getSimpleName() + ".updateOfficialInfo",
        mapParam);
  }

  public UserModel insertOrUpdateEntity(UserModel entity) {
    this.getSqlSession().insert(this.getEntityClass().getSimpleName() + ".insertOrUpdateEntity",
        entity);
    return entity;
  }
}
