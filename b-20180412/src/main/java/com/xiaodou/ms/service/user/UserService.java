package com.xiaodou.ms.service.user;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.xiaodou.common.util.CommUtil;
import com.xiaodou.ms.dao.user.UserDao;
import com.xiaodou.ms.model.user.UserModel;
import com.xiaodou.ms.web.request.user.UserReqeuest;
import com.xiaodou.ms.web.response.BaseResponse;
import com.xiaodou.ms.web.response.ResultType;
import com.xiaodou.ms.web.response.user.UserResponse;
import com.xiaodou.summer.dao.pagination.Page;
import com.xiaodou.summer.dao.param.IQueryParam;
import com.xiaodou.summer.dao.param.QueryEnums.Sort;
import com.xiaodou.summer.dao.param.QueryParam;

@Service("userService")
public class UserService {

	@Resource
	UserDao userDao;

	/**
	 * 根据主键查询
	 * 
	 * @param catId
	 * @return
	 */
	public UserModel findUserById(Long id) {
		UserModel entity = new UserModel();
		entity.setId(id);
		return userDao.findEntityById(entity);
	}

	/**
	 * 根据id查询
	 * 
	 * @param id
	 * @return
	 */
	public Page<UserModel> queryUserByCatId(Integer id) {
		Map<String, Object> cond = new HashMap<>();
		if (null != id) {
			cond.put("id", id);
		}
		Map<String, Object> output = CommUtil.getGeneralField(UserModel.class);
		output.remove("name");
		return userDao.queryListByCond0(cond, output, null);
	}

	/**
	 * 获取所有用户
	 * 
	 * @return
	 */
	public Page<UserModel> queryAllUser(Integer module, Integer pageNo) {
		Map<String, Object> cond = new HashMap<String, Object>();
		cond.put("module", module);
		Map<String, Object> outputs = CommUtil.getGeneralField(UserModel.class);
		Page<UserModel> page = new Page<UserModel>();
		page.setPageNo(pageNo);
		page.setPageSize(20);
		Page<UserModel> userList = userDao.queryListByCond(cond, outputs, page);
		return userList;
	}

	public List<UserModel> findListByUid(String uid) {
		IQueryParam param = new QueryParam();
		param.addOutput("userName", "");
		param.addInput("uid", uid);
		Page<UserModel> results = userDao.findEntityListByCond(param, null);
		if (results != null) {
			return results.getResult();
		}
		return null;
	}

	/**
	 * 下拉框目录列表
	 * 
	 * @return
	 *//*
		 * public List<UserModel> userSelect(String selectedId){ List<UserModel>
		 * userList = this.queryAllUser(); return userList; }
		 */

	public List<UserModel> getAvailiableEntityList() {
		IQueryParam param = new QueryParam();
		param.addOutput("xdUniqueId", "");
		Page<UserModel> results = userDao.findAvailableListByCond(param, null);
		if (results != null) {
			return results.getResult();
		}
		return null;
	}

	
	/**
	 * userType 为 -1 的是机器人
	 * @return
	 */
	public List<UserModel> queryAllRobot() {
		// user_type 为 -1
		IQueryParam param = new QueryParam();
		param.addInput("userType", "-1");
		param.addInput("gender", "1"); // 1 表示该机器人未分配 2 表示已经分配
		param.addOutput("id","");
		param.addOutput("nickName","");
		Page<UserModel> results = userDao.findEntityListByCond(param, null);
		return (results != null && results.getResult().size() != 0) ? results.getResult() : null;
	}
	/**
	 * userType 为 -1 的是机器人
	 * @param page 
	 * @return
	 */
	public Page<UserModel> queryAllRobotWithPage(Integer pageNo) {
		Page<UserModel> page = new Page<UserModel>();
		page.setPageNo(pageNo);
		page.setPageSize(Page.DEFAULT_PAGESIZE);
		// user_type 为 -1
		IQueryParam param = new QueryParam();
		param.addInput("userType", "-1");
		param.addOutput("id","");
		param.addOutput("nickName","");
		param.addOutput("createTime","");
		param.addOutput("xdUniqueId","");
		param.addOutput("module","");
		param.addSort("updateTime", Sort.DESC);
		param.addSort("createTime", Sort.DESC);
		Page<UserModel> results = userDao.findEntityListByCond(param, page);
		return results;
	}

	
	public BaseResponse doEdit(UserReqeuest request) {
		BaseResponse response = new UserResponse(ResultType.SUCCESS);
		UserModel entity = request.initModel();
		userDao.updateEntityById(entity);
	    return response;
	}

	public Boolean delete(Long id) {
		UserModel entity = new UserModel();
		entity.setId(id);
		return userDao.deleteEntityById(entity);
	}
}
