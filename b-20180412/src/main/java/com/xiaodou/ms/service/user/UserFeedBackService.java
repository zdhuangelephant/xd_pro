package com.xiaodou.ms.service.user;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.google.common.collect.Sets;
import com.xiaodou.common.util.CommUtil;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.ms.dao.user.UserBaseInfoDao;
import com.xiaodou.ms.dao.user.UserDao;
import com.xiaodou.ms.dao.user.UserFeedBackDao;
import com.xiaodou.ms.model.user.UserBaseModel;
import com.xiaodou.ms.model.user.UserFeedBackModel;
import com.xiaodou.ms.model.user.UserModel;
import com.xiaodou.ms.web.request.user.UserFeedbackEditRequest;
import com.xiaodou.ms.web.response.BaseResponse;
import com.xiaodou.ms.web.response.ResultType;
import com.xiaodou.summer.dao.pagination.Page;
import com.xiaodou.summer.dao.param.IQueryParam;
import com.xiaodou.summer.dao.param.QueryParam;

@Service("userFeedBackService")
public class UserFeedBackService {
	@Resource
	UserFeedBackDao userFeedBackDao;
	@Resource
    UserDao userDao;
	@Resource
    UserBaseInfoDao userBaseDao;
	
	/**
	 * 根据主键查询
	 * 
	 * @param catId
	 * @return
	 */
	public UserFeedBackModel findUserById(Integer id) {
		UserFeedBackModel entity = new UserFeedBackModel();
		entity.setId(id);
		return userFeedBackDao.findEntityById(entity);
	}

	/**
	 * 根据id查询
	 * 
	 * @param id
	 * @return
	 */
	public Page<UserFeedBackModel> queryUserFeedBackByCatId(Integer id) {
		Map<String, Object> cond = new HashMap<>();
		cond.put("id", id);
		Map<String, Object> output = new HashMap<>();
		output.put("id", "");
		output.put("content", "");
		output.put("createTime", "");
		output.put("number", "");
		output.put("appVersion", "");
		return userFeedBackDao.queryListByCond0(cond, output, null);
	}

	/**
	 * 获取所有反馈内容
	 * 
	 * @return
	 */
	public Page<UserFeedBackModel> queryAllUserFeedBack(Integer pageNo,String appVersion) {
		Map<String, Object> cond = new HashMap<String, Object>();
		if(StringUtils.isNotBlank(appVersion) && !appVersion.equals("0"))
			cond.put("appVersion", appVersion);
		Map<String, Object> output = new HashMap<>();
		output.put("id", "");
		output.put("content", "");
		output.put("createTime", "");
		output.put("number", "");
		output.put("appVersion", "");
		Page<UserFeedBackModel> page = new Page<UserFeedBackModel>();
		page.setPageNo(pageNo);
		page.setPageSize(20);

		Page<UserFeedBackModel> userFeedBackList = userFeedBackDao.queryListByCond(cond, output, page);
		return userFeedBackList;
	}

	public Set<String> findAllAppVersion() {
		IQueryParam param = new QueryParam();
		param.addOutputs(CommUtil.getGeneralField(UserFeedBackModel.class));
		Page<UserFeedBackModel> page = userFeedBackDao.findEntityListByCond(param, null);
		HashSet<String> sets = Sets.newHashSet();
		if(page != null && page.getResult().size() > 0) {
			for (UserFeedBackModel ufb : page.getResult()) {
				sets.add(ufb.getAppVersion());
			}
		}
		return sets;
	}

	   /**
     * 给反馈内容增加电话号码
     * 
     * @return
     */
    public Page<UserFeedBackModel> queryFeedBackWithPhone(Integer pageNo,String appVersion,String handleStatus) {
        Map<String, Object> cond = new HashMap<String, Object>();
        if(StringUtils.isNotBlank(appVersion) && !appVersion.equals("0"))
            cond.put("appVersion", appVersion);
        if(StringUtils.isNotBlank(handleStatus) && !handleStatus.equals("0"))
          cond.put("handleStatus", handleStatus);
        Map<String, Object> output = new HashMap<>();
        output.put("id", "");
        output.put("content", "");
        output.put("userId", "");
        output.put("createTime", "");
        output.put("number", "");
        output.put("appVersion", "");
        output.put("handleStatus", "");
        Page<UserFeedBackModel> page = new Page<UserFeedBackModel>();
        page.setPageNo(pageNo);
        page.setPageSize(20);

        Page<UserFeedBackModel> userFeedBackList = userFeedBackDao.queryListByCond(cond, output, page);
        
//        IQueryParam userParam = new QueryParam();
//        Map<String, Object> userOutPut = new HashMap<>();
//        userOutPut.put("id", "");
//        userOutPut.put("xdUniqueId", "");
//        userParam.addOutputs(userOutPut);
//        Page<UserModel> userList = userDao.findEntityListByCond(userParam, null);
//        
//        IQueryParam userBaseParam = new QueryParam();
//        Map<String, Object> userBaseOutPut = new HashMap<>();
//        userBaseOutPut.put("telephone", "");
//        userBaseOutPut.put("xdUniqueId", "");
//        userBaseParam.addOutputs(userBaseOutPut);
//        
//        Page<UserBaseModel> userBaseList = userBaseDao.findEntityListByCond(userBaseParam, null);
//        
//        for (UserFeedBackModel  feedBack: userFeedBackList.getResult()) {
//          if (feedBack.getUserId() ==null) 
//            continue;
//          
//          for (UserModel user : userList.getResult()) {
////            System.out.println("first class");
//        
//            if (feedBack.getUserId() == user.getId()) {
//              System.out.println("second class");
//              feedBack.setTelephone("temple");
//              
//              for (UserBaseModel userBase : userBaseList.getResult()) {
//                if (userBase.getXdUniqueId()==null) 
//                  continue;
//                if (user.getXdUniqueId().equals(userBase.getXdUniqueId()) ) {
//                  System.out.println("tele" + userBase.getTelephone());
//                  feedBack.setTelephone(userBase.getTelephone());
//                }
//              }
//            }
//         }
//        }
        
        return userFeedBackList;
    }
	
    /**
     * 更新目录
     * 
     * @param entity
     * @return
     */
    public Boolean editFeedback(UserFeedBackModel entity) {
      Map<String, Object> cond = new HashMap<>();
      cond.put("id", entity.getId());
      userFeedBackDao.updateEntityById(entity);
      return true;
    }

    /**
     * 目录编辑
     * 
     * @param request
     * @return
     */
    public BaseResponse editFeedback(UserFeedbackEditRequest request) {
      UserFeedBackModel feedBackModel = new UserFeedBackModel();
     
      feedBackModel.setHandleStatus(request.getHandleStatus());
      feedBackModel.setHandleNote(request.getHandleNote());
      feedBackModel.setId(request.getId());
      Boolean aBoolean = this.editFeedback(feedBackModel);
      BaseResponse response = null;
      if (aBoolean) {
        response = new BaseResponse(ResultType.SUCCESS);
      } else {
        response = new BaseResponse(ResultType.SYS_FAIL);
      }
      return response;
    }
    
    
	/**
	 * 
	 * 根据id获取
	 * 
	 * @param id
	 * @return
	 */
	/*
	 * public List<UserFeedBackModel> queryAllUserFeedBackById(Integer id) {
	 * List<UserFeedBackModel> result = new ArrayList<UserFeedBackModel>();
	 * List<UserFeedBackModel> list = this.queryAllUserFeedBack();
	 * result.addAll(list); for (UserFeedBackModel model : list) {
	 * result.addAll(this.queryAllUserFeedBackById(model.getId())); } return result;
	 * }
	 */
	/**
	 * 获取所有栏目Id,map
	 * 
	 * @return
	 */

	/*
	 * public Map<String, Object> queryAllUserFeedBackMap() {
	 * List<UserFeedBackModel> userFeedBackModelList = this.queryAllUserFeedBack();
	 * // Map<Integer,Object> resultMap = new HashMap<>(); Map<String, Object>
	 * resultMap = DynamicLocalCache.getLocalCacheMap(); if (resultMap != null) {
	 * return resultMap; } else { for (UserFeedBackModel userFeedBackModel :
	 * userFeedBackModelList) {
	 * resultMap.put(String.valueOf(userFeedBackModel.getId()), userFeedBackModel);
	 * } return resultMap; }
	 * 
	 * }
	 */

	/**
	 * 下拉框目录列表
	 * 
	 * @return
	 */
	/*
	 * public List<UserFeedBackModel> userFeedBackSelect(String selectedId) {
	 * List<UserFeedBackModel> userFeedBackList = this.queryAllUserFeedBack();
	 * return userFeedBackList; }
	 */

}
