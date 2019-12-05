package com.xiaodou.ucenter.service.facade;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.summer.dao.pagination.Page;
import com.xiaodou.summer.dao.param.IQueryParam;
import com.xiaodou.summer.dao.param.QueryParam;
import com.xiaodou.ucenter.dao.BaseUserModelDao;
import com.xiaodou.ucenter.domain.model.BaseUserDO;
import com.xiaodou.ucenter.exception.UserDaoException;

@Service("ucenterServiceFacade")
public class UcenterServiceFacadeImpl implements IUcenterServiceFacade {

  @Resource
  BaseUserModelDao userModelDao;

  @Override
  public BaseUserDO getUser(Map<String, Object> input, Map<String, Object> output) throws UserDaoException {
    List<BaseUserDO> userList = this.listUser(input, output);
    if(null !=userList && userList.size()>0){
      LoggerUtil.error("用戶数据异常,有重复用户", new UserDaoException("用戶数据异常,有重复用户")); 
      throw new UserDaoException("用戶数据异常,有重复用户");
    }
    if (null == userList || userList.size() != 1) return null;
    return userList.get(0);
  }

  @Override
  public List<BaseUserDO> listUser(Map<String, Object> input, Map<String, Object> output) {
    IQueryParam param = new QueryParam();
    param.addInputs(input);
    param.addOutputs(output);
    Page<BaseUserDO> page = userModelDao.findEntityListByCond(param, null);
    if (null != page) return page.getResult();
    return null;
  }

  @Override
  public BaseUserDO saveUser(BaseUserDO model) {
    return userModelDao.addEntity(model);
  }

  @Override
  public boolean updateUser(Map<String, Object> input, BaseUserDO model) {
    return userModelDao.updateEntityByCond(input, model);
  }

}
