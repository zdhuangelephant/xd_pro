package com.xiaodou.userCenter.service.http;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.google.common.collect.Lists;
import com.xiaodou.common.cache.redis.JedisUtil;
import com.xiaodou.common.util.EmojiUtil;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.userCenter.common.Constant;
import com.xiaodou.userCenter.constant.UcenterModelConstant;
import com.xiaodou.userCenter.model.UserModel;
import com.xiaodou.userCenter.model.http.Protocol;
import com.xiaodou.userCenter.model.http.ResponseBase;
import com.xiaodou.userCenter.prop.HttpProp;
import com.xiaodou.userCenter.request.FeedBackCategoryRequest;
import com.xiaodou.userCenter.request.NewLoginRequest;
import com.xiaodou.userCenter.request.ucenterHttp.BaseUcenterRequest;
import com.xiaodou.userCenter.request.ucenterHttp.FindPasswordRequest;
import com.xiaodou.userCenter.request.ucenterHttp.FindUserRequest;
import com.xiaodou.userCenter.request.ucenterHttp.ModifyDefaultPwdRequest;
import com.xiaodou.userCenter.request.ucenterHttp.ModifyPwdRequest;
import com.xiaodou.userCenter.request.ucenterHttp.RingtalkRequest;
import com.xiaodou.userCenter.request.ucenterHttp.UserByTelAndModRequest;
import com.xiaodou.userCenter.response.ucenterHttp.BaseUcenterResponse;
import com.xiaodou.userCenter.response.ucenterHttp.BaseUserInfo;
import com.xiaodou.userCenter.response.ucenterHttp.BaseUserListResponse;
import com.xiaodou.userCenter.response.ucenterHttp.RingtalkResponse;

@Service
public class UcenterHttpService {

  public BaseUcenterResponse register(String password, String telephone, String region) {
    BaseUcenterRequest request = new BaseUcenterRequest();
    request.setPassword(password);
    request.setPlatform(UcenterModelConstant.PLATFORM_TELEPHONE);
    request.setTelephone(telephone);
    request.setModule(region);
    Protocol protocol = new Protocol(Protocol.POST);
    protocol.setUrl(HttpProp.register());
    return FlowService.doFlow(request, BaseUcenterResponse.class, protocol);
  }

  /**
   * 
   * @description 登录
   * @author 李德洪
   * @Date 2017年5月19日
   * @param srdList
   */
  public BaseUcenterResponse login(NewLoginRequest pojo) {
    BaseUcenterRequest request = new BaseUcenterRequest();
    request.setPlatform(pojo.getPlatform());
    String uniqueId = pojo.getUniqueId();
    switch (pojo.getPlatform()) {
      case UcenterModelConstant.PLATFORM_LOCAL:
      case UcenterModelConstant.PLATFORM_TELEPHONE:
        request.setPlatform(UcenterModelConstant.PLATFORM_TELEPHONE);
        break;
      case UcenterModelConstant.PLATFORM_QQ:
        request.setQq(uniqueId);
        break;
      case UcenterModelConstant.PLATFORM_WEIBO:
        request.setWeibo(uniqueId);
        break;
      case UcenterModelConstant.PLATFORM_WEIXIN:
        request.setWeixin(uniqueId);
        break;
      case UcenterModelConstant.PLATFORM_TOURIST:
      case UcenterModelConstant.PLATFORM_DEVICE:
        request.setTourist(pojo.getDeviceId());
        request.setPlatform(UcenterModelConstant.PLATFORM_TOURIST);
        break;
      default:
        break;
    }
    request.setPassword(pojo.getPwd());
    request.setTelephone(pojo.getPhoneNum());
    Protocol protocol = new Protocol(Protocol.POST);
    protocol.setUrl(HttpProp.login());
    return FlowService.doFlow(request, BaseUcenterResponse.class, protocol);
  }

  public ResponseBase findPassword(String password, String telephone) throws Exception {
    FindPasswordRequest request = new FindPasswordRequest();
    request.setPassword(password);
    request.setTelephone(telephone);
    Protocol protocol = new Protocol(Protocol.POST);
    protocol.setUrl(HttpProp.findPassword());
    return FlowService.doFlow(request, ResponseBase.class, protocol);
  }

  public ResponseBase modifyPassword(String xdUniqueId, String oldPwd, String newPwd,
      String confirmNewPwd) {
    ModifyPwdRequest request = new ModifyPwdRequest();
    request.setXdUniqueId(xdUniqueId);
    request.setOldPwd(oldPwd);
    request.setNewPwd(newPwd);
    request.setConfirmNewPwd(confirmNewPwd);
    Protocol protocol = new Protocol(Protocol.POST);
    protocol.setUrl(HttpProp.modifyPassword());
    return FlowService.doFlow(request, ResponseBase.class, protocol);
  }

  public ResponseBase modifyDefaultPassword(String xdUniqueId, String password) {
    ModifyDefaultPwdRequest request = new ModifyDefaultPwdRequest();
    request.setXdUniqueId(xdUniqueId);
    request.setPassword(password);
    Protocol protocol = new Protocol(Protocol.POST);
    protocol.setUrl(HttpProp.modifyDefaultPassword());
    return FlowService.doFlow(request, ResponseBase.class, protocol);
  }

  public List<BaseUserInfo> findUser(List<String> xdUniqueIdList) {
    FindUserRequest request = new FindUserRequest();
    request.setXdUniqueIdList(xdUniqueIdList);
    Protocol protocol = new Protocol(Protocol.POST);
    protocol.setUrl(HttpProp.findUser());
    BaseUserListResponse response =
        FlowService.doFlow(request, BaseUserListResponse.class, protocol);
    if (null != response && response.isRetOk()) {
      return response.getUserBaseInfoList();
    }
    return Lists.newArrayList();
  }

  public String findUserMainAccount(String xdUniqueId) {
    String key = Constant.LOGIN_MAIN_ACCOUNT + xdUniqueId;
    String mainAccount = JedisUtil.getStringFromJedis(key);
    if (StringUtils.isNotBlank(mainAccount)) {
      return mainAccount;
    }
    List<String> xdUniqueIdList = Lists.newArrayList();
    xdUniqueIdList.add(xdUniqueId);
    List<BaseUserInfo> list = this.findUser(xdUniqueIdList);
    if (CollectionUtils.isEmpty(list)) {
      return null;
    }
    mainAccount = list.get(0).getMainAccount();
    JedisUtil.addStringToJedis(key, mainAccount, 60 * 60 * 24);
    return mainAccount;
  }


  /**
   * 
   * @description 钉钉发送机器人消息
   * @author 李德洪
   * @Date 2017年11月16日
   * @param content
   */
  public void sendDingtalk(UserModel userModel, FeedBackCategoryRequest pojo) {
    if ("ON".equalsIgnoreCase(HttpProp.sendDingtalkMock())) {
      return;
    }
    RingtalkRequest request = new RingtalkRequest();
    RingtalkRequest.RingtalkMarkdown markDown = new RingtalkRequest.RingtalkMarkdown();
    markDown.setText(userModel.getId(), userModel.getNickName(), pojo.getCategoryDescList(),
        EmojiUtil.resolveToEmojiFromByte(pojo.getFeedContent()));
    request.setMarkdown(markDown);
    Protocol protocol = new Protocol(Protocol.POST);
    protocol.setUrl(HttpProp.sendDingtalk());
    RingtalkResponse response = FlowService.doFlow(request, RingtalkResponse.class, protocol);
    if (null == response || StringUtils.isBlank(response.getErrcode())) {
      LoggerUtil.error("http 失败", new RuntimeException());
    } else {
      if (!response.getErrcode().equals("0")) {
        LoggerUtil.error(response.getErrmsg(), new RuntimeException());
      }
    }
  }

  public Boolean checkPhoneExist(String telephone) {
    UserByTelAndModRequest request = new UserByTelAndModRequest();
    request.setTelephone(telephone);
    Protocol protocol = new Protocol(Protocol.POST);
    protocol.setUrl(HttpProp.getUserByTelAndMod());
    ResponseBase response = FlowService.doFlow(request, ResponseBase.class, protocol);
    if (null != response && response.isRetOk()) {
      return true;
    }
    return false;
  }


}
