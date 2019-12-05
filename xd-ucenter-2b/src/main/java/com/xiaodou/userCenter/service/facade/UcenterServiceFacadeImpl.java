package com.xiaodou.userCenter.service.facade;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.google.common.collect.Maps;
import com.xiaodou.common.util.CommUtil;
import com.xiaodou.summer.dao.pagination.Page;
import com.xiaodou.summer.dao.param.IQueryParam;
import com.xiaodou.userCenter.dao.AccountWalletGoodDao;
import com.xiaodou.userCenter.dao.BlankNoticeModelDao;
import com.xiaodou.userCenter.dao.CreditChangeLogDao;
import com.xiaodou.userCenter.dao.FeedBackCategoryDao;
import com.xiaodou.userCenter.dao.FeedBackModelDao;
import com.xiaodou.userCenter.dao.HelpDocModelDao;
import com.xiaodou.userCenter.dao.ProductCategoryUtilDao;
import com.xiaodou.userCenter.dao.UserFriendDao;
import com.xiaodou.userCenter.dao.UserLoginAlarmRecordDao;
import com.xiaodou.userCenter.dao.UserLoginInfoDao;
import com.xiaodou.userCenter.dao.UserModelDao;
import com.xiaodou.userCenter.dao.UserPraiseModelDao;
import com.xiaodou.userCenter.dao.UserPraiseVoDao;
import com.xiaodou.userCenter.dao.UserRankDao;
import com.xiaodou.userCenter.dao.UserRelateNoticeDao;
import com.xiaodou.userCenter.enums.FriendShip;
import com.xiaodou.userCenter.model.AccountWalletGood;
import com.xiaodou.userCenter.model.BlankNoticeModel;
import com.xiaodou.userCenter.model.CreditChangeLog;
import com.xiaodou.userCenter.model.FeedBackCategoryDO;
import com.xiaodou.userCenter.model.FeedBackModel;
import com.xiaodou.userCenter.model.FriendModel;
import com.xiaodou.userCenter.model.HelpDocModel;
import com.xiaodou.userCenter.model.ProductCategoryUtilModel;
import com.xiaodou.userCenter.model.RankUserInfo;
import com.xiaodou.userCenter.model.UserModel;
import com.xiaodou.userCenter.model.UserPraiseModel;
import com.xiaodou.userCenter.model.UserRelateNoticeModel;
import com.xiaodou.userCenter.model.alarm.AlarmRecordModel;
import com.xiaodou.userCenter.model.alarm.LoginInfoModel;
import com.xiaodou.userCenter.model.vo.UserPraiseVo;

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

  @Resource
  private HelpDocModelDao helpDocModelDao;

  @Resource
  private UserRelateNoticeDao userRelateNoticeDao;

  @Resource
  private FeedBackModelDao feedBackModelDao;

  @Resource
  private BlankNoticeModelDao blankNoticeModelDao;

  @Resource
  private UserFriendDao userFriendDao;

  @Resource
  private UserPraiseModelDao userPraiseModelDao;

  @Resource
  private UserRankDao userRankDao;

  @Resource
  private UserPraiseVoDao userPraiseVoDao;

  @Resource(name = "productCategoryUtilDao")
  private ProductCategoryUtilDao productCategoryUtilDao;

  @Resource
  AccountWalletGoodDao accountWalletGoodDao;

  @Resource
  UserLoginInfoDao userLoginInfoDao;

  @Resource
  UserLoginAlarmRecordDao userLoginAlarmRecordDao;
  
  @Resource
  CreditChangeLogDao creditChangeLogDao;

  @Resource
  FeedBackCategoryDao feedBackCategoryDao;

  @Override
  public List<ProductCategoryUtilModel> queryProductCategoryList(Map<String, Object> queryCond,
      Map<String, Object> allInfo) {
    return productCategoryUtilDao.queryList(queryCond, allInfo);
  }

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
  public UserModel insertUser(UserModel model) {
    return userModelDao.addEntity(model);
  }
  
  @Override
  public UserModel insertOrUpdateEntity(UserModel model){
    return userModelDao.insertOrUpdateEntity(model);
  }

  @Override
  public UserModel insertOfficialUser(UserModel model) {
    return userModelDao.addOfficialEntity(model);
  }

  @Override
  public int updateUser(Map<String, Object> queryCond, UserModel entity) {
    return userModelDao.updateEntity(queryCond, entity);
  }

  @Override
  public int updateUserOfficialInfo(Map<String, Object> queryCond, UserModel entity) {
    return userModelDao.updateOfficialInfo(queryCond, entity);
  }

  @Override
  public List<HelpDocModel> queryHelpDocList(Map<String, Object> queryCond,
      Map<String, Object> allInfo) {
    return helpDocModelDao.queryList(queryCond, allInfo);
  }

  @Override
  public List<UserRelateNoticeModel> queryRelateNoticeList(Map<String, Object> queryCond,
      Map<String, Object> allInfo) {
    return userRelateNoticeDao.queryList(queryCond, allInfo);
  }

  @Override
  public Integer countRelateNotice(Map<String, Object> queryCond) {
    return userRelateNoticeDao.count(queryCond);
  }

  @Override
  public Integer countRelateNoticeByUid(Map<String, Object> queryCond) {
    return userRelateNoticeDao.countByUid(queryCond);
  }

  @Override
  public UserRelateNoticeModel insertUserRelateNotice(UserRelateNoticeModel model) {
    return userRelateNoticeDao.addEntity(model);
  }

  @Override
  public UserRelateNoticeModel findNoticeById(Integer noticeId) {
    UserRelateNoticeModel entity = new UserRelateNoticeModel();
    entity.setNoticeId(noticeId);
    return userRelateNoticeDao.findNoticeEntityById(entity);
  }

  @Override
  public UserRelateNoticeModel findUserRelateNoticeById(Long userId, Integer noticeId) {
    UserRelateNoticeModel entity = new UserRelateNoticeModel();
    entity.setUserId(userId);
    entity.setNoticeId(noticeId);
    // entity.setRelateId(relateId);
    return userRelateNoticeDao.findEntityById(entity);
  }

  @Override
  public int updateUserRelateNotice(Map<String, Object> queryCond, UserRelateNoticeModel entity) {
    return userRelateNoticeDao.updateEntity(queryCond, entity);
  }

  @Override
  public FeedBackModel insertFeedBack(FeedBackModel model) {
    return feedBackModelDao.addEntity(model);
  }

  @Override
  public List<BlankNoticeModel> queryBlackNoticeList(Map<String, Object> queryCond,
      Map<String, Object> allInfo) {
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
  public List<FriendModel> queryFriendList(String userId, FriendShip relationShip) {
    Map<String, Object> input = Maps.newHashMap();
    input.put("userId", userId);
    input.put("relationShip", relationShip.getCode());
    return userFriendDao.queryList(input, CommUtil.getAllField(FriendModel.class));
  }

  @Override
  public List<FriendModel> queryFriendList(String userId, String targetUserId) {
    Map<String, Object> input = Maps.newHashMap();
    input.put("userId", userId);
    input.put("targetUserId", targetUserId);
    return userFriendDao.queryList(input, CommUtil.getAllField(FriendModel.class));
  }

  @Override
  public FriendModel queryFriend(String userId, String targetUserId) {
    Map<String, String> input = Maps.newHashMap();
    input.put("userId", userId);
    input.put("targetUserId", targetUserId);
    List<FriendModel> friendList =
        userFriendDao.queryList(input, CommUtil.getAllField(FriendModel.class));
    if (null != friendList && friendList.size() > 0) return friendList.get(0);
    return null;
  }

  @Override
  public UserPraiseModel queryUserPraiseRelate(String userId, String targetUserId) {
    UserPraiseModel model = new UserPraiseModel();
    model.setUserId(userId);
    model.setTargetUserId(targetUserId);
    return userPraiseModelDao.findEntityById(model);
  }

  @Override
  public List<UserPraiseModel> queryUserPraiseList(String targetUserId) {
    Map<String, Object> input = Maps.newHashMap();
    input.put("targetUserId", targetUserId);
    return userPraiseModelDao.queryList(input, CommUtil.getAllField(UserPraiseModel.class));
  }

  @Override
  public UserPraiseModel insertUserPraise(UserPraiseModel model) {
    return userPraiseModelDao.addEntity(model);
  }

  @Override
  public Page<UserPraiseVo> queryUserListByPraise(Map<String, Object> inputArgument,
      Map<String, Object> outputField, Page<UserPraiseVo> page) {
    return userPraiseVoDao.queryUserListByPraise(inputArgument, outputField, page);
  }

  @Override
  public Page<RankUserInfo> queryPraiseListByCond(Map<String, Object> inputArgument,
      Map<String, Object> outputField, Page<RankUserInfo> page) {
    return userRankDao.queryPraiseListByCond(inputArgument, outputField, page);
  }

  @Override
  public Page<RankUserInfo> queryCreditListByCond(Map<String, Object> inputArgument,
      Map<String, Object> outputField, Page<RankUserInfo> page) {
    return userRankDao.queryCreditListByCond(inputArgument, outputField, page);
  }

  @Override
  public AccountWalletGood queryAccountWalletGoodById(AccountWalletGood good) {
    return accountWalletGoodDao.findEntityById(good);
  }

  @Override
  public Page<AccountWalletGood> queryAccountWalletGoodByCond(IQueryParam param) {
    return accountWalletGoodDao.findEntityListByCond(param, null);
  }

  @Override
  public LoginInfoModel saveUserLoginInfo(LoginInfoModel entity) {
    return userLoginInfoDao.addEntity(entity);
  }

  @Override
  public Page<LoginInfoModel> listUserLoginInfo(IQueryParam param) {
    return userLoginInfoDao.findEntityListByCond(param, null);
  }

  @Override
  public AlarmRecordModel saveUserLoginAlarmRecord(AlarmRecordModel entity) {
    return userLoginAlarmRecordDao.addEntity(entity);
  }

  @Override
  public CreditChangeLog addCreditChangeLog(CreditChangeLog log) {
    return creditChangeLogDao.addEntity(log);
  }

  @Override
  public Boolean deleteCreditChangeLog(String id) {
    CreditChangeLog log = new CreditChangeLog();
    log.setId(id);
    return creditChangeLogDao.deleteEntityById(log);
  }

  @Override
  public FeedBackCategoryDO saveFeedBackCategory(FeedBackCategoryDO FeedBackCategory) {
    return feedBackCategoryDao.addEntity(FeedBackCategory);
  }

  @Override
  public Integer saveFeedBackCategoryList(Map<String, Object> column, List<FeedBackCategoryDO> value) {
    return feedBackCategoryDao.addEntityList(column, value);
  }

  @Override
  public Page<CreditChangeLog> queryCreditChangeLog(IQueryParam param, Page<CreditChangeLog> pageInfo) {
    return creditChangeLogDao.findEntityListByCond(param, pageInfo);
  }

}
