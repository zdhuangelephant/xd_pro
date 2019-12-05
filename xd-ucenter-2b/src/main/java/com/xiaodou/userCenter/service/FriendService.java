package com.xiaodou.userCenter.service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.google.common.collect.Maps;
import com.xiaodou.async.model.AddFriendMessage;
import com.xiaodou.common.util.CommUtil;
import com.xiaodou.mission.engine.event.EventBuilder;
import com.xiaodou.mission.engine.event.FriendAddEvent;
import com.xiaodou.summer.vo.out.ResultType;
import com.xiaodou.userCenter.enums.FriendShip;
import com.xiaodou.userCenter.model.FriendModel;
import com.xiaodou.userCenter.model.UserModel;
import com.xiaodou.userCenter.module.selfTaught.model.StFriend;
import com.xiaodou.userCenter.request.FriendAddRequest;
import com.xiaodou.userCenter.request.FriendDelRequest;
import com.xiaodou.userCenter.request.FriendListRequest;
import com.xiaodou.userCenter.response.BaseResultInfo;
import com.xiaodou.userCenter.response.BaseUserModel;
import com.xiaodou.userCenter.response.FriendListResponse;
import com.xiaodou.userCenter.response.FriendListResponse.Friend;
import com.xiaodou.userCenter.response.resultype.UcenterResType;
import com.xiaodou.userCenter.service.facade.IUcenterServiceFacade;
import com.xiaodou.userCenter.service.queue.QueueService;

/**
 * @name @see com.xiaodou.userCenter.service.FriendService.java
 * @CopyRright (c) 2016 by XiaoDou NetWork Technology
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2016年2月1日
 * @description 好友管理service
 * @version 1.0
 */
@Service("friendService")
public class FriendService extends CheckLoginService {

  @Resource(name = "queueService")
  QueueService queueService;

  @Resource
  IUcenterServiceFacade ucenterServiceFacade;

  public BaseResultInfo addFriend(FriendAddRequest pojo) throws Exception {
    BaseResultInfo res = new BaseResultInfo(ResultType.SUCCESS);
    CheckLoginResult<BaseUserModel> result = checkLoginWithBaseUserModel(pojo);
    if (!result.isRetOk()) return new BaseResultInfo(result.getResType());
    BaseUserModel model = result.getModel();
    if (model.getUserId().equals(pojo.getTargetUserId())) {
      return new BaseResultInfo(UcenterResType.TargetIsSelf);
    }
    Map<String, Object> input = Maps.newHashMap();
    input.put("id", pojo.getTargetUserId());
    List<UserModel> userList =
        ucenterServiceFacade.queryUserList(input, CommUtil.getAllField(BaseUserModel.class));
    if (null == userList || userList.size() == 0) {
      return new BaseResultInfo(UcenterResType.TargetUserMissed);
    }
    FriendModel friend = ucenterServiceFacade.queryFriend(model.getId(), pojo.getTargetUserId());
    if (null == friend || !FriendShip.FRIEND.getCode().equals(friend.getRelationShip())) {
      AddFriendMessage message = new AddFriendMessage();
      message.setSrcUid(model.getId());
      message.setToUid(pojo.getTargetUserId());
      message.setMessageName("addfriend");
      message.send();
    } else {
      res = new BaseResultInfo(UcenterResType.HasBeenFriend);
    }
    return res;
  }

  public void doAddFriend(String srcUid, String toUid) {
    FriendModel friend = ucenterServiceFacade.queryFriend(srcUid, toUid);
    if (null == friend) {
      friend = new FriendModel();
      friend.setUserId(srcUid);
      friend.setTargetUserId(toUid);
      friend.setRelationShip(FriendShip.FRIEND.getCode());
      ucenterServiceFacade.insertFriendModel(friend);
    } else if (!FriendShip.FRIEND.getCode().equals(friend.getRelationShip())) {
      friend.setRelationShip(FriendShip.FRIEND.getCode());
      ucenterServiceFacade.updateFriendModel(friend);
    }
    sendAddFriendEvent(friend);
  }

  private void sendAddFriendEvent(FriendModel friend) {
    FriendAddEvent event = EventBuilder.buildFriendAddEvent();
    event.setUserId(friend.getUserId());
    event.setTargetUserId(friend.getTargetUserId());
    event.send();
  }

  public BaseResultInfo delFriend(FriendDelRequest pojo) throws Exception {
    BaseResultInfo res = new BaseResultInfo(ResultType.SUCCESS);
    CheckLoginResult<BaseUserModel> result = checkLoginWithBaseUserModel(pojo);
    if (!result.isRetOk()) return new BaseResultInfo(result.getResType());
    BaseUserModel model = result.getModel();
    Map<String, Object> input = Maps.newHashMap();
    input.put("id", pojo.getTargetUserId());
    List<UserModel> userList =
        ucenterServiceFacade.queryUserList(input, CommUtil.getAllField(BaseUserModel.class));
    if (null == userList || userList.size() == 0) {
      return new BaseResultInfo(UcenterResType.TargetUserMissed);
    }
    FriendModel friend = ucenterServiceFacade.queryFriend(model.getId(), pojo.getTargetUserId());
    if (null != friend && !FriendShip.DELETED.getCode().equals(friend.getRelationShip())) {
      friend.setRelationShip(FriendShip.DELETED.getCode());
      friend.setUpdateTime(new Timestamp(System.currentTimeMillis()));
      ucenterServiceFacade.updateFriendModel(friend);
    } else {
      return new BaseResultInfo(UcenterResType.NotFriend);
    }
    return res;
  }

  public FriendListResponse listFriend(FriendListRequest pojo) throws Exception {
    FriendListResponse res = new FriendListResponse(ResultType.SUCCESS);
    CheckLoginResult<BaseUserModel> result = checkLoginWithBaseUserModel(pojo);
    if (!result.isRetOk()) return new FriendListResponse(result.getResType());
    BaseUserModel model = result.getModel();
    List<FriendModel> friendModelList =
        ucenterServiceFacade.queryFriendList(model.getId(), FriendShip.FRIEND);
    for (FriendModel friendModel : friendModelList) {
      if (friendModel.getUserId().equals(friendModel.getTargetUserId())) continue;
      Friend friend = new StFriend().init(friendModel);
      res.getFriendList().add(friend);
    }
    return res;
  }

}
