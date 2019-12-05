package com.xiaodou.ms.service.user;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.xiaodou.common.cache.local.DynamicLocalCache;
import com.xiaodou.ms.dao.user.UserHelpDao;
import com.xiaodou.ms.model.user.UserHelpModel;
import com.xiaodou.ms.web.request.user.UserHelpCreateRequest;
import com.xiaodou.ms.web.request.user.UserHelpEditRequest;
import com.xiaodou.ms.web.response.ResultType;
import com.xiaodou.ms.web.response.course.CategoryCreateResponse;
import com.xiaodou.ms.web.response.course.CategoryEditResponse;
import com.xiaodou.summer.dao.pagination.Page;

@Service("userHelpService")
public class UserHelpService {

  /**
   * 用户帮助DAO
   */
  @Resource
  UserHelpDao userHelpDao;

  /**
   * 新增用户帮助
   * 
   * @param entity
   * @return
   */
  public UserHelpModel addUserHelp(UserHelpModel entity) {
    return userHelpDao.addEntity(entity);
  }

  /**
   * 新增用户帮助
   * 
   * @param request
   * @return
   */
  public CategoryCreateResponse addUserHelp(UserHelpCreateRequest request) {
    UserHelpModel userHelpModel = new UserHelpModel();
    userHelpModel.setId(request.getId());
    userHelpModel.setTitle(request.getTitle());
    userHelpModel.setContent(request.getContent());
    userHelpModel.setCreateTime(new Timestamp(System.currentTimeMillis()));
    UserHelpModel resultModel = this.addUserHelp(userHelpModel);
    CategoryCreateResponse response = new CategoryCreateResponse(ResultType.SUCCESS);
    response.setCatId(resultModel.getId().longValue());
    return response;
  }

  /**
   * 删除用户帮助
   * 
   * @param catId
   * @return
   */
  public Boolean deleteUserHelp(Integer catId) {
    Map<String, Object> cond = new HashMap<>();
    cond.put("id", catId);
    return userHelpDao.deleteEntity(cond);
  }

  /**
   * 更新用户帮助
   * 
   * @param entity
   * @return
   */
  public Boolean editUserHelp(UserHelpModel entity) {
    Map<String, Object> cond = new HashMap<>();
    cond.put("id", entity.getId());
    return userHelpDao.updateEntity(cond, entity);
  }

  /**
   * 用户帮助编辑
   * 
   * @param request
   * @return
   */
  public CategoryEditResponse editUserHelp(UserHelpEditRequest request) {
    UserHelpModel userHelpModel = new UserHelpModel();
    userHelpModel.setTitle(request.getTitle());
    userHelpModel.setContent(request.getContent());
    userHelpModel.setId(request.getId());
    userHelpModel.setCreateTime(new Timestamp(System.currentTimeMillis()));
    Boolean aBoolean = this.editUserHelp(userHelpModel);
    CategoryEditResponse response = null;
    if (aBoolean) {
      response = new CategoryEditResponse(ResultType.SUCCESS);
    } else {
      response = new CategoryEditResponse(ResultType.SYS_FAIL);
    }
    return response;
  }

  /**
   * 根据主键查询
   * 
   * @param catId
   * @return
   */
  public UserHelpModel findUserHelpById(Integer id) {
    UserHelpModel entity = new UserHelpModel();
    entity.setId(id);
    return userHelpDao.findEntityById(entity);
  }



  /**
   * 根据id查询
   * 
   * @param id
   * @return
   */
  public Page<UserHelpModel> queryUserHelpByCatId(Integer id) {
    Map<String, Object> cond = new HashMap<>();
    cond.put("id", id);
    Map<String, Object> output = new HashMap<>();
    output.put("id", "");
    output.put("title", "");
    output.put("content", "");
    output.put("createTime", "");
    return userHelpDao.queryListByCond0(cond, output, null);
  }


  /**
   * 获取所有栏目
   * 
   * @return
   */
  public List<UserHelpModel> queryAllUserHelp() {
    Map<String, Object> cond = new HashMap<String, Object>();
    Map<String, Object> output = new HashMap<>();
    output.put("id", "");
    output.put("title", "");
    output.put("content", "");
    output.put("createTime", "");
    Page<UserHelpModel> userHelpList = userHelpDao.queryListByCond0(cond, output, null);
    return userHelpList.getResult();
  }

  /**
   * 
   * 根据id获取
   * 
   * @param id
   * @return
   */
  public List<UserHelpModel> queryAllUserHelpById(Integer id) {
    List<UserHelpModel> result = new ArrayList<UserHelpModel>();
    List<UserHelpModel> list = this.queryAllUserHelp();
    result.addAll(list);
    for (UserHelpModel model : list) {
      result.addAll(this.queryAllUserHelpById(model.getId()));
    }
    return result;
  }

  /**
   * 获取所有栏目Id,map
   * 
   * @return
   */

  public Map<String, Object> queryAllUserHelpMap() {
    List<UserHelpModel> userHelpModelList = this.queryAllUserHelp();
    // Map<Integer,Object> resultMap = new HashMap<>();
    Map<String, Object> resultMap = DynamicLocalCache.getLocalCacheMap();
    if (resultMap != null) {
      return resultMap;
    } else {
      for (UserHelpModel userHelpModel : userHelpModelList) {
        resultMap.put(String.valueOf(userHelpModel.getId()), userHelpModel);
      }
      return resultMap;
    }

  }

  /**
   * 下拉框目录列表
   * 
   * @return
   */
  public List<UserHelpModel> userHelpSelect(String selectedId) {
    List<UserHelpModel> userHelpList = this.queryAllUserHelp();
    return userHelpList;
  }

}
