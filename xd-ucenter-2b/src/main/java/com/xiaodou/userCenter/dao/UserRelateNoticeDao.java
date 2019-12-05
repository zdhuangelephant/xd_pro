package com.xiaodou.userCenter.dao;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.xiaodou.userCenter.model.UserRelateNoticeModel;

@Repository("userRelateNoticeDao")
public class UserRelateNoticeDao extends BaseProcessDao<UserRelateNoticeModel> {
  /**
   * 查找 公告，和用户无关，为了兼容老版本
   */
  public UserRelateNoticeModel findNoticeEntityById(UserRelateNoticeModel entity) {
    return (UserRelateNoticeModel) this.getSqlSession().selectOne(
        this.getEntityClass().getSimpleName() + ".findNoticeEntityById", entity);
  }

  /**
   * @Title: count
   * @Description: 根据条件 查询数量
   * @param queryCond 查询条件
   * @return int
   */
  @SuppressWarnings("rawtypes")
  public Integer countByUid(Map<String, Object> queryCond) {
    Map<String, Map> mapParam = new HashMap<String, Map>();
    mapParam.put("input", queryCond);
    return (Integer) getSqlSession().selectOne(
        this.getEntityClass().getSimpleName() + ".countByUid", mapParam);
  }

}
