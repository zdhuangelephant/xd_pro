package com.xiaodou.common.info.dao;

import org.springframework.stereotype.Repository;

import com.xiaodou.common.info.model.CommonInfo;
import com.xiaodou.common.info.model.ModuleInfo;
import com.xiaodou.summer.dao.jdbc.BaseDao;
@Repository("moduleInfoDao")
public class ModuleInfoDao extends BaseDao<ModuleInfo> {

  public CommonInfo findEntityByInfoCode(CommonInfo commonInfo) {
    return (CommonInfo) getSqlSession().selectOne(
        this.getEntityClass().getSimpleName() + ".findEntityByInfoCode", commonInfo);
  }


  public boolean updateEntityByInfoCode(CommonInfo commonInfo) {
    int result =
        this.getSqlSession().update(
            this.getEntityClass().getSimpleName() + ".updateEntityByInfoCode", commonInfo);
    return result == 1;
  }
  
}