package com.xiaodou.ucenter.service.facade;

import java.util.List;
import java.util.Map;

import com.xiaodou.summer.dao.pagination.Page;
import com.xiaodou.ucenter.model.UserModel;

/**
 * @name @see com.xiaodou.userCenter.service.facade.IUcenterServiceFacade.java
 * @CopyRright (c) 2015 by XiaoDou NetWork Technology
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2015年8月27日
 * @description 用户中心领域数据层Facade
 * @version 1.0
 */
public interface IUcenterServiceFacade {

  UserModel queryUser(Map<String, Object> cond, Map<String, Object> briefInfo);

  List<UserModel> queryUserList(Map<String, Object> cond, Map<String, Object> briefInfo);

  List<UserModel> queryUserList(Map<String, Object> cond, Map<String, Object> briefInfo,
      Page<UserModel> page);

  UserModel insertUser(UserModel model);

  int updateUser(Map<String, Object> queryCond, UserModel entity);

}
