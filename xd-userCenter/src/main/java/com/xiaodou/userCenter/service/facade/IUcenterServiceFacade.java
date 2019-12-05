package com.xiaodou.userCenter.service.facade;

import java.util.List;
import java.util.Map;

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
 * @name @see com.xiaodou.userCenter.service.facade.IUcenterServiceFacade.java
 * @CopyRright (c) 2015 by XiaoDou NetWork Technology
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2015年8月27日
 * @description 用户中心领域数据层Facade
 * @version 1.0
 */
public interface IUcenterServiceFacade {

  List<UserModel> queryUserList(Map<String, Object> cond, Map<String, Object> briefInfo);

  List<UserModuleInfoModel> queryUserModuleInfoList(Map<String, Object> cond,
      Map<String, Object> briefInfo);

  List<UserModelVo> queryUserVoList(Map<String, Object> cond, Map<String, Object> briefInfo);

  UserModel insertUser(UserModel model);

  UserModuleInfoModel insertUserModuleInfo(UserModuleInfoModel model);

  int updateUser(Map<String, Object> queryCond, UserModel entity);

  int updateUserModuleInfo(Map<String, Object> queryCond, UserModuleInfoModel entity);

  List<HelpDocModel> queryHelpDocList(Map<String, Object> queryCond, Map<String, Object> allInfo);

  List<UserRelateNoticeModel> queryRelateNoticeList(Map<String, Object> queryCond,
      Map<String, Object> allInfo);

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

  FriendModel queryFriend(String userId, String targetUserId);
  /*查看用户与目标用户的点赞记录*/
  UserPraiseModel queryUserPraiseRelate(String userId,String targetUserId);
  /*查看目标点赞用户的被赞用户*/
  List<UserPraiseModel> queryUserPraiseList(String targetUserId);
  /*插入点赞记录*/
  UserPraiseModel insertUserPraise(UserPraiseModel model);
}
