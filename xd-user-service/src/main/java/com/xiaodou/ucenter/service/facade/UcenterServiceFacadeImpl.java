package com.xiaodou.ucenter.service.facade;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.xiaodou.summer.dao.pagination.Page;
import com.xiaodou.ucenter.dao.UserModelDao;
import com.xiaodou.ucenter.model.UserModel;

/**
 * @name @see com.xiaodou.userCenter.service.facade.UcenterServiceFacadeImpl.java
 * @CopyRright (c) 2015 by XiaoDou NetWork Technology
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2015年8月27日
 * @description 用户中心领域数据层Facade实现
 * @version 1.0
 */
@Service("ucenterServiceFacade")
public class UcenterServiceFacadeImpl implements IUcenterServiceFacade {

  @Resource
  private UserModelDao userModelDao;



  @Override
  public UserModel queryUser(Map<String, Object> cond, Map<String, Object> briefInfo) {
    List<UserModel> userList = userModelDao.queryList(cond, briefInfo);
    if (null == userList || userList.size() == 0) return null;
    return userList.get(0);
  }

  @Override
  public List<UserModel> queryUserList(Map<String, Object> cond, Map<String, Object> briefInfo) {
    return userModelDao.queryList(cond, briefInfo);
  }

  @Override
  public List<UserModel> queryUserList(Map<String, Object> cond, Map<String, Object> briefInfo,
      Page<UserModel> page) {
    Page<UserModel> pageL = userModelDao.queryListByPaging(cond, briefInfo, page);
    if (null != pageL) {
      return pageL.getResult();
    }
    return null;
  }

  @Override
  public UserModel insertUser(UserModel model) {
    return userModelDao.addEntity(model);
  }

  @Override
  public int updateUser(Map<String, Object> queryCond, UserModel entity) {
    return userModelDao.updateEntity(queryCond, entity);
  }

}
