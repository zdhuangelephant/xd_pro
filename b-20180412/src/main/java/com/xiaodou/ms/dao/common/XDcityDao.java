package com.xiaodou.ms.dao.common;

import org.springframework.stereotype.Repository;

import com.xiaodou.ms.dao.BaseProcessDao;
import com.xiaodou.ms.model.common.XDcity;

/**
 * Created by lidehong on 15-9-19.
 */
@Repository
public class XDcityDao extends BaseProcessDao<XDcity> {
  public XDcity findEntityByCondOne(XDcity entity) {
    return (XDcity) this.getSqlSession().selectOne(
        this.getEntityClass().getSimpleName() + ".findEntityByCondOne", entity);
  }

  public XDcity findEntityByCondTwo(XDcity entity) {
    return (XDcity) this.getSqlSession().selectOne(
        this.getEntityClass().getSimpleName() + ".findEntityByCondTwo", entity);
  }
}
