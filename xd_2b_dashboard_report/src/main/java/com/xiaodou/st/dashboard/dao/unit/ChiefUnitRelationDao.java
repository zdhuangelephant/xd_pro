package com.xiaodou.st.dashboard.dao.unit;

import org.springframework.stereotype.Repository;

import com.xiaodou.st.dashboard.domain.unit.ChiefUnitRelationDO;
import com.xiaodou.summer.dao.BaseDao;

@Repository
public class ChiefUnitRelationDao extends BaseDao<ChiefUnitRelationDO> {

  public ChiefUnitRelationDO findEntityByCatId(Integer catId) {
    ChiefUnitRelationDO udo = new ChiefUnitRelationDO();
    udo.setCatId(catId);
    return (ChiefUnitRelationDO) this.getSqlSession().selectOne(
        this.getEntityClass().getSimpleName() + ".findEntityByCatId", udo);
  }
  
  public ChiefUnitRelationDO findEntityByUnitId(Long unitId) {
    ChiefUnitRelationDO udo = new ChiefUnitRelationDO();
    udo.setUnitId(unitId);
    return (ChiefUnitRelationDO) this.getSqlSession().selectOne(
        this.getEntityClass().getSimpleName() + ".findEntityByUnitId", udo);
  }

}
