package com.xiaodou.userCenter.service.facade;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.google.common.collect.Maps;
import com.xiaodou.common.util.CommUtil;
import com.xiaodou.userCenter.dao.BlankNoticeModelDao;
import com.xiaodou.userCenter.dao.FeedBackModelDao;
import com.xiaodou.userCenter.dao.HelpDocModelDao;
import com.xiaodou.userCenter.dao.UserFriendDao;
import com.xiaodou.userCenter.dao.UserModelDao;
import com.xiaodou.userCenter.dao.UserModelVoDao;
import com.xiaodou.userCenter.dao.UserModuleInfoDao;
import com.xiaodou.userCenter.dao.UserPraiseModelDao;
import com.xiaodou.userCenter.dao.UserRelateNoticeDao;
import com.xiaodou.userCenter.model.BlankNoticeModel;
import com.xiaodou.userCenter.model.FeedBackModel;
import com.xiaodou.userCenter.model.FriendModel;
import com.xiaodou.userCenter.model.HelpDocModel;
import com.xiaodou.userCenter.model.UserModel;
import com.xiaodou.userCenter.model.UserModuleInfoModel;
import com.xiaodou.userCenter.model.UserRelateNoticeModel;
import com.xiaodou.userCenter.model.selftaught.UserPraiseModel;
import com.xiaodou.userCenter.model.vo.UserModelVo;
import com.xiaodou.userCenter.service.FriendService.FriendShip;

/**
 * @name @see
 *       com.xiaodou.userCenter.service.facade.UcenterServiceFacadeImpl.java
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

	@Resource
	private HelpDocModelDao helpDocModelDao;

	@Resource
	private UserRelateNoticeDao userRelateNoticeDao;

	@Resource
	private FeedBackModelDao feedBackModelDao;

	@Resource
	private BlankNoticeModelDao blankNoticeModelDao;

	@Resource
	private UserModelVoDao userModelVoDao;

	@Resource
	private UserModuleInfoDao userModuleInfoDao;

	@Resource
	private UserFriendDao userFriendDao;

	@Resource
	private UserPraiseModelDao userPraiseModelDao;

	@Override
	public List<UserModel> queryUserList(Map<String, Object> cond,
			Map<String, Object> briefInfo) {
		return userModelDao.queryList(cond, briefInfo);
	}

	@Override
	public List<UserModuleInfoModel> queryUserModuleInfoList(
			Map<String, Object> cond, Map<String, Object> briefInfo) {
		return userModuleInfoDao.queryList(cond, briefInfo);
	}

	@Override
	public List<UserModelVo> queryUserVoList(Map<String, Object> cond,
			Map<String, Object> briefInfo) {
		return userModelVoDao.queryList(cond, briefInfo);
	}

	@Override
	public UserModel insertUser(UserModel model) {
		return userModelDao.addEntity(model);
	}

	@Override
	public UserModuleInfoModel insertUserModuleInfo(UserModuleInfoModel model) {
		return userModuleInfoDao.addEntity(model);
	}

	@Override
	public int updateUser(Map<String, Object> queryCond, UserModel entity) {
		return userModelDao.updateEntity(queryCond, entity);
	}

	@Override
	public int updateUserModuleInfo(Map<String, Object> queryCond,
			UserModuleInfoModel entity) {
		return userModuleInfoDao.updateEntity(queryCond, entity);
	}

	@Override
	public List<HelpDocModel> queryHelpDocList(Map<String, Object> queryCond,
			Map<String, Object> allInfo) {
		return helpDocModelDao.queryList(queryCond, allInfo);
	}

	@Override
	public List<UserRelateNoticeModel> queryRelateNoticeList(
			Map<String, Object> queryCond, Map<String, Object> allInfo) {
		return userRelateNoticeDao.queryList(queryCond, allInfo);
	}

	@Override
	public UserRelateNoticeModel insertUserRelateNotice(
			UserRelateNoticeModel model) {
		model.setCreateTime(new Timestamp(System.currentTimeMillis())
				.toString());
		return userRelateNoticeDao.addEntity(model);
	}

	@Override
	public UserRelateNoticeModel findNoticeById(Integer noticeId) {
		UserRelateNoticeModel entity = new UserRelateNoticeModel();
		entity.setNoticeId(noticeId);
		return userRelateNoticeDao.findNoticeEntityById(entity);
	}

	@Override
	public UserRelateNoticeModel findUserRelateNoticeById(Long userId,
			Integer noticeId) {
		UserRelateNoticeModel entity = new UserRelateNoticeModel();
		entity.setUserId(userId);
		entity.setNoticeId(noticeId);
		// entity.setRelateId(relateId);
		return userRelateNoticeDao.findEntityById(entity);
	}

	@Override
	public int updateUserRelateNotice(Map<String, Object> queryCond,
			UserRelateNoticeModel entity) {
		return userRelateNoticeDao.updateEntity(queryCond, entity);
	}

	@Override
	public FeedBackModel insertFeedBack(FeedBackModel model) {
		return feedBackModelDao.addEntity(model);
	}

	@Override
	public List<BlankNoticeModel> queryBlackNoticeList(
			Map<String, Object> queryCond, Map<String, Object> allInfo) {
		return blankNoticeModelDao.queryList(queryCond, allInfo);
	}

	@Override
	public BlankNoticeModel findBlackNoticeById(Long noticeId) {
		BlankNoticeModel entity = new BlankNoticeModel();
		entity.setId(noticeId);
		return blankNoticeModelDao.findEntityById(entity);
	}

	@Override
	public FriendModel insertFriendModel(FriendModel model) {
		return userFriendDao.addEntity(model);
	}

	@Override
	public int updateFriendModel(FriendModel model) {
		Map<String, Object> input = Maps.newHashMap();
		input.put("userId", model.getUserId());
		input.put("targetUserId", model.getTargetUserId());
		return userFriendDao.updateEntity(input, model);
	}

	@Override
	public List<FriendModel> queryFriendList(String userId,
			FriendShip relationShip) {
		Map<String, Object> input = Maps.newHashMap();
		input.put("userId", userId);
		input.put("relationShip", relationShip.getCode());
		return userFriendDao.queryList(input,
				CommUtil.getAllField(FriendModel.class));
	}

	@Override
	public FriendModel queryFriend(String userId, String targetUserId) {
		Map<String, String> input = Maps.newHashMap();
		input.put("userId", userId);
		input.put("targetUserId", targetUserId);
		List<FriendModel> friendList = userFriendDao.queryList(input,
				CommUtil.getAllField(FriendModel.class));
		if (null != friendList && friendList.size() > 0)
			return friendList.get(0);
		return null;
	}

	@Override
	public UserPraiseModel queryUserPraiseRelate(String userId, String targetUserId) {
		UserPraiseModel model = new UserPraiseModel();
		model.setUserId(targetUserId);
		model.setTargetUserId(targetUserId);
		return userPraiseModelDao.findEntityById(model);
	}

	@Override
	public List<UserPraiseModel> queryUserPraiseList(String targetUserId) {
		Map<String, Object> input = Maps.newHashMap();
		input.put("targetUserId", targetUserId);
		return userPraiseModelDao.queryList(input,
				CommUtil.getAllField(UserPraiseModel.class));
	}

	@Override
	public UserPraiseModel insertUserPraise(UserPraiseModel model) {
		return userPraiseModelDao.addEntity(model);
	}
}
