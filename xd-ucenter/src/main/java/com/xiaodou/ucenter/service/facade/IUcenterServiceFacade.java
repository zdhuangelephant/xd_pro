package com.xiaodou.ucenter.service.facade;

import java.util.List;
import java.util.Map;

import com.xiaodou.ucenter.domain.model.BaseUserDO;
import com.xiaodou.ucenter.exception.UserDaoException;

public interface IUcenterServiceFacade {

  /* 查找用户 */
  @Deprecated
  BaseUserDO getUser(Map<String, Object> input, Map<String, Object> output) throws UserDaoException;

  /* 批量查找用户 */
  List<BaseUserDO> listUser(Map<String, Object> input, Map<String, Object> output);

  /*添加用户*/
  BaseUserDO saveUser(BaseUserDO model);

  /*更新用户信息*/
  boolean updateUser(Map<String, Object> input, BaseUserDO model);
}
