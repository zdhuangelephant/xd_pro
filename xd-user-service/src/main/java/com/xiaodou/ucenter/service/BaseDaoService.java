package com.xiaodou.ucenter.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.xiaodou.common.util.CommUtil;
import com.xiaodou.ucenter.cache.UserInfoByTokenCache;
import com.xiaodou.ucenter.model.UserModel;
import com.xiaodou.ucenter.module.ModuleWrapper;
import com.xiaodou.ucenter.request.BaseRequest;
import com.xiaodou.ucenter.service.facade.IUcenterServiceFacade;

/**
 * 
 * 表操作基本抽象service类
 * 
 * 2015年3月26日 下午4:25:55 weirong.li 1.0
 * 
 * @author weirong.li
 * @version 1.0
 * @since JDK1.7
 */
public abstract class BaseDaoService {

  @Resource
  IUcenterServiceFacade ucenterServiceFacade;

  public void deleteById(List<Long> listId) {}

  /**
   * 
   * 构建查询条件
   * 
   * @param queryIdList
   * @param listSequence
   * @param queryCond
   * @return
   */
  protected Map<String, Object> getQueryCond(List<Long> queryIdList, List<String> listSequence,
      Object queryCond) {
    Map<String, Object> input = new HashMap<String, Object>();
    if (queryIdList != null && queryIdList.size() > 0) {
      input.put("listId", queryIdList);
    }

    if (listSequence != null && listSequence.size() > 0) {
      input.put("listSequence", listSequence);
    }

    if (queryCond != null) {
      CommUtil.transferFromVO2Map(input, queryCond);
    }

    return input;
  }

  /**
   * 判断token是否存在（从redis再到db的判断） 1 先从缓存中获取用户信息，查看用户信息是否存在，若存在则返回用户信息， 2 若不存在再从db中查看，若存在添加至缓存，不存在给出提示
   * 
   * @throws IllegalAccessException
   * @throws InstantiationException
   */
  protected UserModel decideRedisExsited(BaseRequest pojo) throws InstantiationException,
      IllegalAccessException {
    UserModel initFromUserModel =
        UserInfoByTokenCache.getUserInfoFromCache(pojo.getSessionToken(),ModuleWrapper.getWrapper().getModule());
    if (initFromUserModel == null) {
      UserModel model = null;
      if ((model = queryUser(pojo)) == null) return null;
      model.setPassword(null);
      UserInfoByTokenCache.addUserInfoToCache(model, pojo.getSessionToken(),ModuleWrapper.getWrapper().getModule());
    }
    return initFromUserModel;
  }

  /**
   * 从数据库中查询userModel
   * 
   * @param pojo
   * @return
   */
  protected UserModel queryUser(BaseRequest pojo) {
    // 从db查是否存在
    Map<String, Object> queryCond = new HashMap<String, Object>();
    queryCond.put("token", pojo.getSessionToken());
    queryCond.put("module", ModuleWrapper.getWrapper().getModule());
    List<UserModel> userList =
        ucenterServiceFacade.queryUserList(queryCond, CommUtil.getAllField(UserModel.class));
    if (null == userList || userList.size() <= 0) {
      return null;
    } else if (null != userList && userList.size() == 1) {
      UserModel model = userList.get(0);
      model.setPassword(null);
      return model;
    } else {
      return null;
    }
  }

  /**
   * 根据用户id从数据库中查询userModel
   * 
   * @param pojo
   * @return
   */
  protected UserModel queryUserById(String uid) {
    // 从db查是否存在
    Map<String, Object> queryCond = new HashMap<String, Object>();
    queryCond.put("id", uid);
    queryCond.put("module", ModuleWrapper.getWrapper().getModule());
    List<UserModel> userList =
        ucenterServiceFacade.queryUserList(queryCond, CommUtil.getAllField(UserModel.class));
    if (null == userList || userList.size() <= 0) {
      return null;
    } else if (null != userList && userList.size() == 1) {
      UserModel model = userList.get(0);
      model.setPassword(null);
      return model;
    } else {
      return null;
    }
  }
}
