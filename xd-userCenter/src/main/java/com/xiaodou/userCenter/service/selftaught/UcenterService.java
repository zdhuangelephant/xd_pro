package com.xiaodou.userCenter.service.selftaught;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.TypeReference;
import com.google.common.collect.Maps;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.dao.AppBindingModelDao;
import com.xiaodou.summer.vo.out.ResultType;
import com.xiaodou.userCenter.cache.UserInfoByTokenCache;
import com.xiaodou.userCenter.common.enums.UserPraiseEnum;
import com.xiaodou.userCenter.model.UserModel;
import com.xiaodou.userCenter.model.property.UserModelProperty;
import com.xiaodou.userCenter.model.property.UserModelVoProperty;
import com.xiaodou.userCenter.model.selftaught.UserPraiseModel;
import com.xiaodou.userCenter.model.vo.UserModelVo;
import com.xiaodou.userCenter.request.selftaught.ModifyStMyInfoRequest;
import com.xiaodou.userCenter.request.selftaught.PersonInfoRequest;
import com.xiaodou.userCenter.request.selftaught.UserPraiseRequest;
import com.xiaodou.userCenter.response.BaseResultInfo;
import com.xiaodou.userCenter.response.BaseUserModel;
import com.xiaodou.userCenter.response.ModifyMyInfoResponse;
import com.xiaodou.userCenter.response.resultype.UcenterResType;
import com.xiaodou.userCenter.response.selftaught.PersonInfoResponse;
import com.xiaodou.userCenter.response.selftaught.PersonInfoResponse.UserInfo;
import com.xiaodou.userCenter.service.BaseDaoService;
import com.xiaodou.userCenter.service.facade.IUcenterServiceFacade;
import com.xiaodou.userCenter.service.queue.QueueService;
import com.xiaodou.userCenter.util.ModuleMappingWrapper;

@Service("stUcenterService")
public class UcenterService extends BaseDaoService {

	@Resource
	IUcenterServiceFacade ucenterServiceFacade;
	@Resource
	AppBindingModelDao appBindingModelDao;
	@Resource
	QueueService queueService;

	 /**
	   * 修改个人资料service
	   * 
	   * @param pojo
	   * @return
	   * @throws Exception
	   */
	  public ModifyMyInfoResponse modifyStUser(ModifyStMyInfoRequest pojo) throws Exception {
	    try {
	      ModifyMyInfoResponse response = new ModifyMyInfoResponse(ResultType.SUCCESS);
	      if (StringUtils.isBlank(pojo.getSessionToken())) {
	        return new ModifyMyInfoResponse(UcenterResType.UnLoginUser);
	      }
	      UserModelVo user = new UserModelVo();
	      user.setToken(pojo.getSessionToken());
	      user.setModule(ModuleMappingWrapper.getWrapper().getModule().getCode());
	      Map<String, Object> cond = this.getQueryCond(null, null, user);
	      // 因为新需求的出现所以得加上获取moudelInfo的逻辑
	      List<UserModelVo> list =
	          ucenterServiceFacade.queryUserVoList(cond, UserModelVoProperty.getAllInfo());
	      UserModelVo entity;
	      if (null != list && list.size() > 0) {
	        entity = list.get(0);
	        if (StringUtils.isJsonNotBlank(entity.getModuleInfo())) {
	          String model_info = pojo.setMoudelInfo(FastJsonUtil.fromJsons(entity.getModuleInfo(),
	                  new TypeReference<Map<String, Object>>() {}));
	          if (UcenterResType.UnCompleteInfo.getCode().equals(model_info))
	            return new ModifyMyInfoResponse(UcenterResType.UnCompleteInfo);
	          user.setModuleInfo(model_info);
	        } else {
	          String model_info = pojo.getMoudelInfo();
	          if (UcenterResType.UnCompleteInfo.getCode().equals(model_info))
	            return new ModifyMyInfoResponse(UcenterResType.UnCompleteInfo);
	          user.setModuleInfo(model_info);
	        }
	      } else {
	        return new ModifyMyInfoResponse(UcenterResType.UnLoginUser);
	      }
	      // 添加新信息
	      user.setNickName(pojo.getNickName());
	      if (StringUtils.isNotBlank(pojo.getPortrait())) user.setPortrait(pojo.getPortrait()); // 头像
	      user.setGender(pojo.getGender()); // 性别
	      user.setAddress(pojo.getAddress()); // 地址

	      Integer updateFlag1 = ucenterServiceFacade.updateUser(cond, user.getUserModel());
	      UserModelVo user2 = new UserModelVo();
	      user2.setUserName(entity.getUserName());
	      user2.setModule(ModuleMappingWrapper.getWrapper().getModule().getCode());
	      Map<String, Object> cond2 = this.getQueryCond(null, null, user2);
	      Integer updateFlag2 =
	          ucenterServiceFacade.updateUserModuleInfo(cond2, user.getUserModuleInfoModel());
	      if (null != updateFlag1 && updateFlag1 == 1 && null != updateFlag2 && updateFlag2 == 1) {
	        // 删除之前的缓存
	        UserInfoByTokenCache.deleteUserInfoFromCache(pojo.getSessionToken());
	        // 从数据库里查出最新的数据
	        Map<String, Object> queryCond = Maps.newHashMap();
	        queryCond.put("token", pojo.getSessionToken());
	        UserModelVo model =
	            ucenterServiceFacade.queryUserVoList(queryCond, UserModelVoProperty.getBaseInfo()).get(
	                0);
	        // 添加最新的用户信息到缓存中
	        UserInfoByTokenCache.addUserInfoToCache((BaseUserModel) ModuleMappingWrapper.getWrapper()
	            .getModule().getResponse().newInstance().initFromUserModelVo(model),
	            pojo.getSessionToken());
	      } else {
	        return new ModifyMyInfoResponse(UcenterResType.UserUpdateFail);
	      }
	      return response;
	    } catch (Exception e) {
	      throw e;
		}
	}
	  /**
	   *  获取个人详情信息service
	   * 
	   * @param pojo
	   * @return
	   * @throws Exception
	   */
	  public PersonInfoResponse personInfo(PersonInfoRequest pojo) throws Exception {
	    try {
	    	PersonInfoResponse response = new PersonInfoResponse(ResultType.SUCCESS);
	      if (StringUtils.isBlank(pojo.getSessionToken())) {
	        return new PersonInfoResponse(UcenterResType.UnLoginUser);
	      }
	      UserModelVo user = new UserModelVo();
	      String targetUserId = pojo.getTargetUserId();
	      String userId =StringUtils.EMPTY;
	      if(StringUtils.isBlank(targetUserId))//返回用户自己的信息
	    	  user.setToken(pojo.getSessionToken());
	      else //查看目标用户的信息
	    	  user.setId(Long.valueOf(targetUserId));
	      user.setModule(ModuleMappingWrapper.getWrapper().getModule().getCode());
	      List<UserModelVo> list =
	          ucenterServiceFacade.queryUserVoList(this.getQueryCond(null, null, user), UserModelVoProperty.getAllInfo());
	      UserModelVo entity;
	      if (null != list && list.size() > 0) {
	    	  entity = list.get(0);
	    	  UserInfo userInfo = FastJsonUtil.fromJson(entity.getModuleInfo(), UserInfo.class);
	          userInfo = userInfo.getUserInfo(entity);
	          UserModelVo user1 = new UserModelVo();
      	  	user1.setToken(pojo.getSessionToken());
      	  	user1.setModule(ModuleMappingWrapper.getWrapper().getModule().getCode());
      	    List<UserModelVo> list1 =
      	          ucenterServiceFacade.queryUserVoList(this.getQueryCond(null, null, user1), UserModelVoProperty.getAllInfo());
      	    userId = list1.get(0).getId().toString();
	          if(StringUtils.isNotBlank(targetUserId)){
	          		UserPraiseModel userPraiseModel = ucenterServiceFacade.queryUserPraiseRelate(userId, targetUserId);
	          		if(null != userPraiseModel)
	          			userInfo.setIsPraised(UserPraiseEnum.isPraised.getCode());
	          		userInfo.setIsPraised(UserPraiseEnum.isNotPraised.getCode());
	          }else
	        	  userInfo.setIsPraised(UserPraiseEnum.isNotPraised.getCode()); 
	          ucenterServiceFacade.queryUserPraiseList((StringUtils.isNotBlank(targetUserId)?targetUserId:userId));
	          //userInfo.setPraiseNum(praiseNum);
	          response.setUserInfo(userInfo);
	          userId = userInfo.getId();
	          
//	          response.setCourseInfoList(courseInfoList);
//	          response.setPraiseList(praiseList);
	      } else {
	        return new PersonInfoResponse(UcenterResType.UnLoginUser);
	      }
	      return response;
	    } catch (Exception e) {
	      throw e;
		}
	}
	 
	  /**
	   *  点赞service
	   * 
	   * @param pojo
	   * @return
	   * @throws Exception
	   */
	  public BaseResultInfo praise(UserPraiseRequest pojo) throws Exception {
	    try {
	    	BaseResultInfo response = new BaseResultInfo(ResultType.SUCCESS);
	      if (StringUtils.isBlank(pojo.getSessionToken())) {
	        return new BaseResultInfo(UcenterResType.UnLoginUser);
	      }
	      UserModel user = new UserModel();
	      UserModel targetUser = new UserModel();
	      String targetUserId = pojo.getTargetUserId();
	      user.setToken(pojo.getSessionToken());
	      targetUser.setId(Long.valueOf(targetUserId));
	      List<UserModel> list =
	          ucenterServiceFacade.queryUserList(this.getQueryCond(null, null, user), UserModelProperty.getAllInfo());
	      List<UserModel> targetList =
		          ucenterServiceFacade.queryUserList(this.getQueryCond(null, null, targetUser), UserModelProperty.getAllInfo());
	      
	      if(null == list || list.size() < 1){
	    	  return new BaseResultInfo(UcenterResType.UnLoginUser);
	      }else if(null == targetList || targetList.size() < 1){
	    	  return new BaseResultInfo(UcenterResType.NoFoundUser);
	      }else{
	    	  String userId = list.get(0).getId().toString();
	    	  UserPraiseModel userPraiseModel = ucenterServiceFacade.queryUserPraiseRelate(userId, targetUserId);
	    	  if(null != userPraiseModel)
	    		  return new BaseResultInfo(UcenterResType.HasPraised);
	    	  UserPraiseModel model = new UserPraiseModel();
	    	  model.setUserId(userId);
	    	  model.setTargetUserId(targetUserId);
	    	  ucenterServiceFacade.insertUserPraise(model);
	      }
	      return response;
	    } catch (Exception e) {
	      throw e;
		}
	}

}
