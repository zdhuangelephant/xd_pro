package com.xiaodou.im.agent.qq;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.xiaodou.im.agent.qq.protocol.Wrapper;
import com.xiaodou.im.agent.qq.request.GetDiscusListRequest;
import com.xiaodou.im.agent.qq.request.GetGroupListRequest;
import com.xiaodou.im.agent.qq.request.PollRequest;
import com.xiaodou.im.agent.qq.request.SendBuddyMsgRequest;
import com.xiaodou.im.agent.qq.request.SendDiscusMsgRequest;
import com.xiaodou.im.agent.qq.request.SendGroupMsgRequest;
import com.xiaodou.im.agent.qq.response.GetBuddyListResponse;
import com.xiaodou.im.agent.qq.response.GetDiscusListResponse;
import com.xiaodou.im.agent.qq.response.GetGroupListResponse;
import com.xiaodou.im.agent.qq.response.PollResponse;
import com.xiaodou.im.agent.qq.response.SendBuddyMsgResponse;
import com.xiaodou.im.agent.qq.response.SendDiscusMsgResponse;
import com.xiaodou.im.agent.qq.response.SendGroupMsgResponse;
import com.xiaodou.im.agent.qq.util.GenHashUtil;
import com.xiaodou.im.agent.qq.util.IMRedisUtil;

/**
 * Date: 2014/12/11
 * Time: 10:57
 *
 * @author Tian.Dong
 */
public class QQClient {
  private static Wrapper wrapper = new Wrapper();

  private boolean isInit = false;

  private String cookie;
  private String uin;
  private String ptwebqq;
  private String vfwebqq;
  private String psessionId;

  private int msgId;
  private String clientId = "53999199";
  private String face = "558";

  public void init() {
    cookie = IMRedisUtil.getCookie();
    if (cookie == null) {
      return;
    }
    wrapper.setCookie(cookie);

    uin = IMRedisUtil.getUin();
    ptwebqq = IMRedisUtil.getPtwebqq();
    vfwebqq = IMRedisUtil.getVfwebqq();
    psessionId = IMRedisUtil.getPsessionid();

    Integer messageId = IMRedisUtil.getMsgId();
    if (messageId == null) {
      long time = new Date().getTime();
      time = ((time - time % 1000) / 1000);
      time = time % 10000 * 10000;
      msgId = (int) time;
      IMRedisUtil.setMsgId(msgId);
    }
    if (IMRedisUtil.getClientId() != null) {
      clientId = IMRedisUtil.getClientId();
    }
    if (IMRedisUtil.getFace() != null) {
      face = IMRedisUtil.getFace();
    }

    isInit = true;
    IMRedisUtil.setIsInit("true");
  }

  public void initWithoutRedis() {
    wrapper.setCookie(cookie);
    long time = new Date().getTime();
    time = ((time - time % 1000) / 1000);
    time = time % 10000 * 10000;
    msgId = (int) time;
  }


  public GetGroupListResponse getGroupList() throws Exception {
    if (!isInit()) {
      init();
    }
    GetGroupListRequest request = new GetGroupListRequest();
    GetGroupListRequest.RContent rContent = new GetGroupListRequest.RContent();
    rContent.setVfwebqq(vfwebqq);
    rContent.setHash(GenHashUtil.hash(uin, ptwebqq));
    request.setR(rContent);
    GetGroupListResponse response = wrapper.getGroupList(request);
    return response;
  }


  public SendGroupMsgResponse sendGroupMsg(String realMsg, String groupName) throws Exception {
    if (!isInit()) {
      init();
    }
    GetGroupListResponse groupListResponse = getGroupList();
    Map<String, Long> groupMap = new HashMap<>();
    if (groupListResponse.getRetCode() == 0) {
      for (GetGroupListResponse.GetGroupListResult.GName gName : groupListResponse.getResult().getGnameList()) {
        groupMap.put(gName.getName(), gName.getGid());
      }
    }
    if (groupMap.get(groupName) == null) {
      return null;
    }
    SendGroupMsgRequest request = new SendGroupMsgRequest();
    SendGroupMsgRequest.SendGroupMsgRContent content = new SendGroupMsgRequest.SendGroupMsgRContent();
    content.setClientId(clientId);
    content.setRealContent(realMsg);
    content.setFace(face);
    content.setPsessionId(psessionId);
    int msgId = IMRedisUtil.getMsgId() == null ? this.msgId : IMRedisUtil.getMsgId();
    content.setMsgId(msgId);
    IMRedisUtil.setMsgId(msgId + 1);
    content.setGroupUin(groupMap.get(groupName));
    request.setR(content);
    SendGroupMsgResponse response = wrapper.sendGroupMsg(request);
    return response;
  }

  public GetDiscusListResponse getDiscusList() throws Exception {
    if (!isInit()) {
      init();
    }
    GetDiscusListRequest request = new GetDiscusListRequest();
    request.setClientId(clientId);
    request.setVfwebqq(vfwebqq);
    request.setPsessionId(psessionId);
    request.setT(System.currentTimeMillis() + "");
    GetDiscusListResponse response = wrapper.getDiscusList(request);

    return response;
  }

  public SendDiscusMsgResponse sendDiscusMsg(String realMsg, String discusName) throws Exception {
    if (!isInit()) {
      init();
    }
    GetDiscusListResponse discusListResponse = getDiscusList();
    Map<String, Long> discusMap = new HashMap<>();
    if (discusListResponse.getRetCode() == 0) {
      for (GetDiscusListResponse.GetDiscusListResult.DName dName : discusListResponse.getResult().getDnameList()) {
        discusMap.put(dName.getName(), dName.getDid());
      }
    }
    if (discusMap.get(discusName) == null) {
      return null;
    }
    SendDiscusMsgRequest request = new SendDiscusMsgRequest();
    SendDiscusMsgRequest.SendDiscusMsgRContent content = new SendDiscusMsgRequest.SendDiscusMsgRContent();
    content.setClientId(clientId);
    content.setRealContent(realMsg);
    content.setFace(face);
    content.setPsessionId(psessionId);
    int msgId = IMRedisUtil.getMsgId() == null ? this.msgId : IMRedisUtil.getMsgId();
    content.setMsgId(msgId);
    IMRedisUtil.setMsgId(msgId + 1);
    content.setDid(discusMap.get(discusName));
    request.setR(content);
    SendDiscusMsgResponse response = wrapper.sendDiscusMsg(request);
    return response;
  }

  public GetBuddyListResponse getBuddyList() throws Exception {
    if (!isInit()) {
      init();
    }
    GetDiscusListRequest request = new GetDiscusListRequest();
    request.setClientId(clientId);
    request.setVfwebqq(vfwebqq);
    request.setPsessionId(psessionId);
    request.setT(System.currentTimeMillis() + "");
    GetBuddyListResponse response = wrapper.getBuddyList(request);
//    if (response.getRetCode() == 0) {
//      for (GetDiscusListResponse.GetDiscusListResult.DName dName : response.getResult().getDnameList()) {
//        discusMap.put(dName.getName(), dName.getDid());
//      }
//    }
    return response;
  }

  public SendBuddyMsgResponse sendBuddyMsg(String realMsg, String buddyName) throws Exception {
    if (!isInit()) {
      init();
    }
    SendBuddyMsgRequest request = new SendBuddyMsgRequest();
    SendBuddyMsgRequest.SendBuddyMsgRContent content = new SendBuddyMsgRequest.SendBuddyMsgRContent();
    content.setClientId(clientId);
    content.setRealContent(realMsg);
    content.setFace(face);
    content.setPsessionId(psessionId);
    int msgId = IMRedisUtil.getMsgId() == null ? this.msgId : IMRedisUtil.getMsgId();
    content.setMsgId(msgId++);
    IMRedisUtil.setMsgId(msgId);
    content.setTo("");
    request.setR(content);
    SendBuddyMsgResponse response = wrapper.sendBuddyMsg(request);
    return response;
  }

  public void sendMsg() {

  }

  public PollResponse poll() throws Exception {
    if (!isInit()) {
      init();
    }
    PollRequest request = new PollRequest();
    PollRequest.PollRContent content = new PollRequest.PollRContent();
    content.setPsessionId(psessionId);
    content.setPtwebqq(ptwebqq);
    content.setClientId(clientId);
    content.setKey("");
    request.setR(content);
    PollResponse response = wrapper.poll(request);
    return response;
  }


  /*public static void main(String[] args) throws IOException {
    ServerSocket serverSocket = new ServerSocket(80);
    Socket socket = serverSocket.accept();
    InputStream inputStream = socket.getInputStream();
    BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
    String line = null;
    while ((line = br.readLine()) != null) {
      System.out.println(line);
    }
  }*/

  public void setCookie(String cookie) {
    this.cookie = cookie;
  }

  public String getCookie() {
    return cookie;
  }

  public String getUin() {
    return uin;
  }

  public void setUin(String uin) {
    this.uin = uin;
  }

  public String getPtwebqq() {
    return ptwebqq;
  }

  public void setPtwebqq(String ptwebqq) {
    this.ptwebqq = ptwebqq;
  }

  public String getVfwebqq() {
    return vfwebqq;
  }

  public void setVfwebqq(String vfwebqq) {
    this.vfwebqq = vfwebqq;
  }

  public String getPsessionId() {
    return psessionId;
  }

  public void setPsessionId(String psessionId) {
    this.psessionId = psessionId;
  }

  public boolean isInit() {
    if (isInit) {
      String flag = IMRedisUtil.getIsInit();
      //redis缓存中isInit为空，说明可能已经logout
      if (flag == null || flag.equals("false")) {
        return false;
      } else {
        return true;
      }
    }
    return false;
  }
}
