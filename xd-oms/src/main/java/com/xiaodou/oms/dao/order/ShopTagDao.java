package com.xiaodou.oms.dao.order;

import org.springframework.stereotype.Repository;

import com.xiaodou.oms.entity.order.ShopTag;
import com.xiaodou.summer.dao.BaseDao;

@Repository
public class ShopTagDao extends BaseDao<ShopTag>{
  
  public ShopTag findEntityByTagForUpdate(ShopTag entity){
    return (ShopTag) super.getSqlSession().selectOne( "ShopTag.findEntityByTagForUpdate", entity);
  }
  
}
