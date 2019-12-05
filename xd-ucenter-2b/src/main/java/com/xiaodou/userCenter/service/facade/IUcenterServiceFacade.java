package com.xiaodou.userCenter.service.facade;

import java.util.List;
import java.util.Map;

import com.xiaodou.summer.dao.pagination.Page;
import com.xiaodou.summer.dao.param.IQueryParam;
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
 * @name @see com.xiaodou.userCenter.service.facade.IUcenterServiceFacade.java
 * @CopyRright (c) 2015 by XiaoDou NetWork Technology
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2015年8月27日
 * @description 用户中心领域数据层Facade
 * @version 1.0
 */
public interface IUcenterServiceFacade {

  UserModel queryUser(Map<String, Object> cond, Map<String, Object> briefInfo);

  List<UserModel> queryUserList(Map<String, Object> cond, Map<String, Object> briefInfo);

  UserModel insertUser(UserModel model);
  
  UserModel insertOrUpdateEntity(UserModel model);

  UserModel insertOfficialUser(UserModel model);

  int updateUser(Map<String, Object> queryCond, UserModel entity);

  int updateUserOfficialInfo(Map<String, Object> queryCond, UserModel entity);

  List<HelpDocModel> queryHelpDocList(Map<String, Object> queryCond, Map<String, Object> allInfo);

  List<UserRelateNoticeModel> queryRelateNoticeList(Map<String, Object> queryCond,
      Map<String, Object> allInfo);

  Integer countRelateNotice(Map<String, Object> queryCond);

  Integer countRelateNoticeByUid(Map<String, Object> queryCond);

  UserRelateNoticeModel insertUserRelateNotice(UserRelateNoticeModel model);

  UserRelateNoticeModel findNoticeById(Integer noticeId);

  UserRelateNoticeModel findUserRelateNoticeById(Long userId, Integer noticeId);

  int updateUserRelateNotice(Map<String, Object> queryCond, UserRelateNoticeModel entity);

  FeedBackModel insertFeedBack(FeedBackModel model);

  List<BlankNoticeModel> queryBlackNoticeList(Map<String, Object> queryCond,
      Map<String, Object> allInfo);

  BlankNoticeModel findBlackNoticeById(Long blackNoticeId);

  /** 好友 */
  FriendModel insertFriendModel(FriendModel model);

  int updateFriendModel(FriendModel model);

  List<FriendModel> queryFriendList(String userId, FriendShip relationShip);

  List<FriendModel> queryFriendList(String userId, String targetUserId);

  FriendModel queryFriend(String userId, String targetUserId);

  /* 查看用户与目标用户的点赞记录 */
  UserPraiseModel queryUserPraiseRelate(String userId, String targetUserId);

  /* 查看目标点赞用户的赞用户 */
  List<UserPraiseModel> queryUserPraiseList(String targetUserId);

  /* 插入点赞记录 */
  UserPraiseModel insertUserPraise(UserPraiseModel model);

  /* 点赞用户列表 */
  Page<UserPraiseVo> queryUserListByPraise(Map<String, Object> inputArgument,
      Map<String, Object> outputField, Page<UserPraiseVo> page);

  /* 排行榜 */
  Page<RankUserInfo> queryPraiseListByCond(Map<String, Object> inputArgument,
      Map<String, Object> outputField, Page<RankUserInfo> page);

  Page<RankUserInfo> queryCreditListByCond(Map<String, Object> inputArgument,
      Map<String, Object> outputField, Page<RankUserInfo> page);

  List<ProductCategoryUtilModel> queryProductCategoryList(Map<String, Object> queryCond,
      Map<String, Object> allInfo);

  /**
   * 根据主键ID查询钱包商品模型
   */
  AccountWalletGood queryAccountWalletGoodById(AccountWalletGood good);

  /**
   * 根据条件查询钱包商品模型
   */
  Page<AccountWalletGood> queryAccountWalletGoodByCond(IQueryParam param);

  /* 保存登录日志 */
  LoginInfoModel saveUserLoginInfo(LoginInfoModel entity);

  Page<LoginInfoModel> listUserLoginInfo(IQueryParam param);

  AlarmRecordModel saveUserLoginAlarmRecord(AlarmRecordModel entity);

  CreditChangeLog addCreditChangeLog(CreditChangeLog log);

  Boolean deleteCreditChangeLog(String id);

  Page<CreditChangeLog> queryCreditChangeLog(IQueryParam param, Page<CreditChangeLog> pageInfo);

  /* FeedBackCategoryDO */
  FeedBackCategoryDO saveFeedBackCategory(FeedBackCategoryDO FeedBackCategory);

  Integer saveFeedBackCategoryList(Map<String, Object> column, List<FeedBackCategoryDO> value);
}
