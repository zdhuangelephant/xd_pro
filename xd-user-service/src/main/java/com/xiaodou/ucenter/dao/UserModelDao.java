package com.xiaodou.ucenter.dao;

import org.springframework.stereotype.Repository;

import com.xiaodou.ucenter.model.UserModel;

@Repository("userModelDao")
public class UserModelDao extends BaseProcessDao<UserModel> {

  public UserModel addOfficialEntity(UserModel entity) {
    this.getSqlSession().insert(this.getEntityClass().getSimpleName() + ".addOfficialEntity",
        entity);
    return entity;
  }
}
