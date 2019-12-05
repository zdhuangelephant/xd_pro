package com.xiaodou.userCenter.dao;

import org.springframework.stereotype.Repository;
import com.xiaodou.userCenter.model.UserRelateNoticeModel;

@Repository("userRelateNoticeDao")
public class UserRelateNoticeDao extends BaseProcessDao<UserRelateNoticeModel> {
	 /**
	   * 查找 公告，和用户无关，为了兼容老版本
	   */
	  public UserRelateNoticeModel findNoticeEntityById(UserRelateNoticeModel entity){
	    return (UserRelateNoticeModel)this.getSqlSession().selectOne(
	    		this.getEntityClass().getSimpleName() + ".findNoticeEntityById", entity);
	  }
}
