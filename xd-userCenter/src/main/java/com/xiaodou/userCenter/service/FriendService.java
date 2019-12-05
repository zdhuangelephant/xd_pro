package com.xiaodou.userCenter.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.xiaodou.common.util.StringUtils;
import com.xiaodou.summer.vo.out.ResultType;
import com.xiaodou.userCenter.model.FriendModel;
import com.xiaodou.userCenter.request.FriendAddRequest;
import com.xiaodou.userCenter.request.FriendDelRequest;
import com.xiaodou.userCenter.request.FriendListRequest;
import com.xiaodou.userCenter.response.BaseResultInfo;
import com.xiaodou.userCenter.response.BaseUserModel;
import com.xiaodou.userCenter.response.FriendListResponse;
import com.xiaodou.userCenter.response.FriendListResponse.Friend;
import com.xiaodou.userCenter.response.resultype.UcenterResType;
import com.xiaodou.userCenter.service.facade.IUcenterServiceFacade;
import com.xiaodou.userCenter.util.ModuleMappingWrapper;

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
public class FriendService {

  public enum FriendShip {
    /** FRIEND 好友 */
    FRIEND((short) 0), /** BLACKLIST 黑名单 */
    BLACKLIST((short) 1), /** DELETED 已删除 */
    DELETED((short) 2), /** STRENGER 陌生人 */
    STRENGER((short) 3);
    FriendShip(Short code) {
      this.code = code;
    }

    private Short code;

    public Short getCode() {
      return code;
    }

  }

  @Resource
  UcenterService ucenterService;

  @Resource
  IUcenterServiceFacade ucenterServiceFacade;

  public BaseResultInfo addFriend(FriendAddRequest pojo) throws Exception {
    BaseResultInfo res = new BaseResultInfo(ResultType.SUCCESS);
    // 判断token是否为空
    if (StringUtils.isBlank(pojo.getSessionToken())) {
      return new BaseResultInfo(UcenterResType.NoTokenExisted);
    }
    BaseUserModel model = ucenterService.decideRedisExsited(pojo);
    if (null == model) {
      return new BaseResultInfo(UcenterResType.UnAbleToken);
    }
    FriendModel friend = ucenterServiceFacade.queryFriend(model.getId(), pojo.getTargetUserId());
    if (null == friend) {
      friend = new FriendModel();
      friend.setUserId(model.getId());
      friend.setTargetUserId(pojo.getTargetUserId());
      friend.setRelationShip(FriendShip.FRIEND.getCode());
      ucenterServiceFacade.insertFriendModel(friend);
    } else if (!FriendShip.FRIEND.getCode().equals(friend.getRelationShip())) {
      friend.setRelationShip(FriendShip.FRIEND.getCode());
      ucenterServiceFacade.updateFriendModel(friend);
    }
    return res;
  }

  public BaseResultInfo delFriend(FriendDelRequest pojo) throws Exception {
    BaseResultInfo res = new BaseResultInfo(ResultType.SUCCESS);
    // 判断token是否为空
    if (StringUtils.isBlank(pojo.getSessionToken())) {
      return new BaseResultInfo(UcenterResType.NoTokenExisted);
    }
    BaseUserModel model = ucenterService.decideRedisExsited(pojo);
    if (null == model) {
      return new BaseResultInfo(UcenterResType.UnAbleToken);
    }
    FriendModel friend = ucenterServiceFacade.queryFriend(model.getId(), pojo.getTargetUserId());
    if (null != friend && !FriendShip.DELETED.getCode().equals(friend.getRelationShip())) {
      friend.setRelationShip(FriendShip.DELETED.getCode());
      ucenterServiceFacade.updateFriendModel(friend);
    }
    return res;
  }

  public FriendListResponse listFriend(FriendListRequest pojo) throws Exception {
    FriendListResponse res = new FriendListResponse(ResultType.SUCCESS);
    // 判断token是否为空
    if (StringUtils.isBlank(pojo.getSessionToken())) {
      return new FriendListResponse(UcenterResType.NoTokenExisted);
    }
    BaseUserModel model = ucenterService.decideRedisExsited(pojo);
    if (null == model) {
      return new FriendListResponse(UcenterResType.UnAbleToken);
    }
    List<FriendModel> friendModelList =
        ucenterServiceFacade.queryFriendList(model.getId(), FriendShip.FRIEND);
    for (FriendModel friendModel : friendModelList) {
      Friend friend =
          ModuleMappingWrapper.getWrapper().getModule().getFriend().newInstance().init(friendModel);
      res.getFriendList().add(friend);
    }
    return res;
  }

}
