package com.xiaodou.im.agent.qq.protocol;

import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.im.agent.qq.request.ChannelLoginRequest;
import com.xiaodou.im.agent.qq.request.CheckSigRequest;
import com.xiaodou.im.agent.qq.request.GetDiscusListRequest;
import com.xiaodou.im.agent.qq.request.GetGroupListRequest;
import com.xiaodou.im.agent.qq.request.GetImageRequest;
import com.xiaodou.im.agent.qq.request.GetVFWebQQRequest;
import com.xiaodou.im.agent.qq.request.LoginRequest;
import com.xiaodou.im.agent.qq.request.PollRequest;
import com.xiaodou.im.agent.qq.request.SendBuddyMsgRequest;
import com.xiaodou.im.agent.qq.request.SendDiscusMsgRequest;
import com.xiaodou.im.agent.qq.request.SendGroupMsgRequest;
import com.xiaodou.im.agent.qq.response.ChannelLoginResponse;
import com.xiaodou.im.agent.qq.response.CheckSigResponse;
import com.xiaodou.im.agent.qq.response.GetBuddyListResponse;
import com.xiaodou.im.agent.qq.response.GetDiscusListResponse;
import com.xiaodou.im.agent.qq.response.GetGroupListResponse;
import com.xiaodou.im.agent.qq.response.GetImageResponse;
import com.xiaodou.im.agent.qq.response.GetVFWebQQResponse;
import com.xiaodou.im.agent.qq.response.LoginResponse;
import com.xiaodou.im.agent.qq.response.PollResponse;
import com.xiaodou.im.agent.qq.response.SendBuddyMsgResponse;
import com.xiaodou.im.agent.qq.response.SendDiscusMsgResponse;
import com.xiaodou.im.agent.qq.response.SendGroupMsgResponse;

/**
 * GET https://ssl.ptlogin2.qq.com/check
 * 检查账号安全性 是否需要验证码等
 * <p/>
 * GET https://ssl.captcha.qq.com/getimage
 * 得到验证码
 * <p/>
 * GET https://ssl.ptlogin2.qq.com/login
 * set各种cookie 主要是得到 ptwebqq  并返回check_sig链接
 * <p/>
 * GET http://ptlogin4.web2.qq.com/check_sig
 * set一堆cookie 然后重定向到proxy.html
 * <p/>
 * GET http://s.web2.qq.com/api/getvfwebqq
 * 得到vfwebqq，后面获取好友列表、群列表需要
 * <p/>
 * POST http://d.web2.qq.com/channel/login2
 * 真正的登录 调完这个接口才会真正登录
 * <p/>
 * POST http://s.web2.qq.com/api/get_user_friends2
 * 顾名思义 好友列表
 * <p/>
 * POST http://s.web2.qq.com/api/get_group_name_list_mask2
 * 顾名思义 群列表
 * <p/>
 * GET http://s.web2.qq.com/api/get_discus_list
 * 顾名思义 讨论组列表
 * <p/>
 * GET http://s.web2.qq.com/api/get_self_info2
 * 个人信息
 * <p/>
 * GET http://d.web2.qq.com/channel/get_online_buddies2
 * 在线好友
 * <p/>
 * POST http://d.web2.qq.com/channel/get_recent_list2
 * 最近联系人列表
 * <p/>
 * POST http://d.web2.qq.com/channel/poll2
 * 轮询消息
 * <p/>
 * GET http://s.web2.qq.com/api/get_group_info_ext2
 * 群成员
 * <p/>
 * POST http://d.web2.qq.com/channel/send_qun_msg2
 * 发送群消息
 * <p/>
 * POST http://d.web2.qq.com/channel/send_discu_msg2
 * 发送讨论组消息
 * <p/>
 * http://d.web2.qq.com/channel/send_buddy_msg2
 * 发送好友消息
 * <p/>
 * Date: 2014/12/11
 * Time: 10:53
 *
 * @author Tian.Dong
 */
public class Wrapper extends BaseWrapper {
  public LoginResponse login(LoginRequest request) {
    return null;
  }

  public GetImageResponse getImage(GetImageRequest request) {
    return null;
  }

  public CheckSigResponse checkSig(CheckSigRequest request) {
    return null;
  }

  public GetVFWebQQResponse getVFWebQQ(GetVFWebQQRequest request) {
    return null;
  }

  public ChannelLoginResponse channelLogin(ChannelLoginRequest request) {
    return null;
  }

  public GetGroupListResponse getGroupList(GetGroupListRequest request) throws Exception {
    String res = super.http(request);
    GetGroupListResponse response = FastJsonUtil.fromJson(res, GetGroupListResponse.class);
    return response;
  }


  public GetDiscusListResponse getDiscusList(GetDiscusListRequest request) throws Exception {
    String res = super.http(request);
    GetDiscusListResponse response = FastJsonUtil.fromJson(res, GetDiscusListResponse.class);
    return response;
  }

  public GetBuddyListResponse getBuddyList(GetDiscusListRequest request) throws Exception {
    String res = super.http(request);
    GetBuddyListResponse response = FastJsonUtil.fromJson(res, GetBuddyListResponse.class);
    return response;
  }

  public PollResponse poll(PollRequest request) throws Exception {
    String res = super.http(request);
    PollResponse response = FastJsonUtil.fromJson(res, PollResponse.class);
    return response;
  }

  public SendGroupMsgResponse sendGroupMsg(SendGroupMsgRequest request) throws Exception {
    String res = super.http(request);
    SendGroupMsgResponse response = FastJsonUtil.fromJson(res, SendGroupMsgResponse.class);
    return response;
  }

  public SendDiscusMsgResponse sendDiscusMsg(SendDiscusMsgRequest request) throws Exception {
    String res = super.http(request);
    SendDiscusMsgResponse response = FastJsonUtil.fromJson(res, SendDiscusMsgResponse.class);
    return response;
  }

  public SendBuddyMsgResponse sendBuddyMsg(SendBuddyMsgRequest request) throws Exception {
    String res = super.http(request);
    SendBuddyMsgResponse response = FastJsonUtil.fromJson(res, SendBuddyMsgResponse.class);
    return response;
  }

  public static void main(String[] args) throws Exception {
    GetGroupListRequest request = new GetGroupListRequest();
    GetGroupListRequest.RContent rContent = new GetGroupListRequest.RContent();
    rContent.setVfwebqq("b299c5d905da97c68e4ce9683b81cc9d17553f63864adc7eee4d4ec6a84a6ad53c7ab807e4b9ec6f");
    rContent.setHash("5004000B000209550E05535306090606010352565456085C000401535656060B5200560D530603535503005D5251010E0450025002000101530F53535702080044554345405A4356105C46465F44");
    request.setR(rContent);
    GetGroupListResponse response = new Wrapper().getGroupList(request);
    System.out.println(FastJsonUtil.toJson(response));
  }
}
