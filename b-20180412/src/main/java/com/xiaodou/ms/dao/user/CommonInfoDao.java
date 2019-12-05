package com.xiaodou.ms.dao.user;

import org.springframework.stereotype.Repository;

import com.xiaodou.ms.dao.BaseProcessDao;
import com.xiaodou.ms.model.user.CommonInfoModel;
@Repository("commoninfoDao")
public class CommonInfoDao extends BaseProcessDao<CommonInfoModel> {
//  public CommonInfo findEntityByInfoCode(CommonInfo commonInfo) {
//    return (CommonInfo) getSqlSession().selectOne(
//        this.getEntityClass().getSimpleName() + ".findEntityByInfoCode", commonInfo);
//  }
//
//
//  public boolean updateEntityByInfoCode(CommonInfo commonInfo) {
//    int result =
//        this.getSqlSession().update(
//            this.getEntityClass().getSimpleName() + ".updateEntityByInfoCode", commonInfo);
//    return result == 1;
//  }
  /**
   * @param condition 更新条件
   * @param value     更新的值
   * @return Integer
   * @Title: updateEntity
   * @Description:更新公共信息表数据
   */
  public Boolean updateInfoVersion() {
    return getSqlSession().update(
      this.getEntityClass().getSimpleName() + ".updateInfoVersion")>0;
  }
  
}
