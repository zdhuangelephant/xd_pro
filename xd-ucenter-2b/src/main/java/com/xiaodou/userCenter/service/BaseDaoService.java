package com.xiaodou.userCenter.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.util.CollectionUtils;

import com.xiaodou.common.util.CommUtil;
import com.xiaodou.userCenter.cache.UserInfoByTokenCache;
import com.xiaodou.userCenter.model.ProductCategoryUtilModel;
import com.xiaodou.userCenter.model.UserModel;
import com.xiaodou.userCenter.model.property.UserModelProperty;
import com.xiaodou.userCenter.module.selfTaught.response.StUserInfoResponse;
import com.xiaodou.userCenter.module.selfTaught.service.facade.IUcenterModelServiceFacade;
import com.xiaodou.userCenter.request.BaseRequest;
import com.xiaodou.userCenter.request.ModifyMyInfoRequest;
import com.xiaodou.userCenter.response.BaseUserModel;
import com.xiaodou.userCenter.service.facade.IUcenterServiceFacade;

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
  @Resource(name = "stUcenterModelServiceFacade")
  IUcenterModelServiceFacade stUcenterModelServiceFacade;

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
  protected BaseUserModel decideRedisExsited(BaseRequest pojo)
      throws InstantiationException, IllegalAccessException {
    StUserInfoResponse initFromUserModel = UserInfoByTokenCache.getUserInfoFromCache(pojo.getSessionToken());
    if (initFromUserModel == null || StringUtils.isEmpty(initFromUserModel.getRegion())) {
      UserModel model = null;
      if ((model = queryUser(pojo)) == null) return null;
      initFromUserModel = (StUserInfoResponse) new StUserInfoResponse().initFromUserModel(model);
      UserInfoByTokenCache.addUserInfoToCache(initFromUserModel, pojo.getSessionToken());
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
    List<UserModel> userList =
        ucenterServiceFacade.queryUserList(queryCond, UserModelProperty.getBaseInfo());
    if (null == userList || userList.size() <= 0) {
      return null;
    } else if (null != userList && userList.size() == 1) {
      UserModel model = userList.get(0);
      return model;
    } else {
      return null;
    }
  }

  protected boolean checkValidMajor(String major) {
    Map<String, Object> input = new HashMap<String, Object>();
    input.put("typeCode", major);
    Map<String, Object> output = new HashMap<String, Object>();
    CommUtil.getAllField(output, ProductCategoryUtilModel.class);
    List<ProductCategoryUtilModel> majorList =
        stUcenterModelServiceFacade.listProductCategoryJoinModuleInfo(input, output);
    if (null == majorList || majorList.size() < 1) return Boolean.FALSE;
    return Boolean.TRUE;
  }
  
  public boolean checkValidModifyUserInfo(ModifyMyInfoRequest pojo) {
      Map<String, Object> input = new HashMap<String, Object>();
      input.put("showStatus", 1);
      input.put("typeCode", pojo.getMajor());
      input.put("id", pojo.getMajorId());
      input.put("name",pojo.getMajorName());
      input.put("module",pojo.getRegion());
      input.put("moduleName",pojo.getRegionName());
      Map<String, Object> output = new HashMap<String, Object>();
      CommUtil.getAllField(output, ProductCategoryUtilModel.class);
      List<ProductCategoryUtilModel> majorList =
              stUcenterModelServiceFacade.listProductCategoryJoinModuleInfo(input, output);
      if (!CollectionUtils.isEmpty(majorList)) {
          return Boolean.TRUE;
      }
      return Boolean.FALSE;
  }

}
