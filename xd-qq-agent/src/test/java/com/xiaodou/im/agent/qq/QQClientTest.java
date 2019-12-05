package com.xiaodou.im.agent.qq;

import org.junit.Test;

import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.im.agent.qq.QQClient;
import com.xiaodou.im.agent.qq.response.GetDiscusListResponse;
import com.xiaodou.im.agent.qq.response.GetGroupListResponse;
import com.xiaodou.im.agent.qq.response.PollResponse;
import com.xiaodou.im.agent.qq.response.SendDiscusMsgResponse;
import com.xiaodou.im.agent.qq.response.SendGroupMsgResponse;

/**
 * Date: 2014/12/15
 * Time: 15:42
 *
 * @author Tian.Dong
 */
public class QQClientTest {
  //@Test
  public void testQQ() throws Exception {
    QQClient client = new QQClient();
    client.setCookie("ptui_loginuin=491468655; RK=svsOj9w4ch; pgv_info=ssid=s2402570268; pgv_pvid=3160709316; o_cookie=1209440675; ptisp=cnc; ptcz=cd3b0f0f4e1b0276a59539b6c7a80ff0d289f1979c7baed2a1f2031d8e92ab40; pt2gguin=o1209440675; uin=o1209440675; skey=@tqxzl9VE4; p_uin=o1209440675; p_skey=YyjZMUA0gbhQ6aHFk2NJOP8p1GhE0GhrV5teCSlUaXg_; pt4_token=n7*cDelTuEr6OiSXb0eLNw__; ptwebqq=5c5256a316af21c86ead367a485d3a09286ad87e92cb719a25a2bb9b1e882cfe");
    client.setVfwebqq("b40633c265a1f1e6d3be9bb22e6ac707014c39bd60277c1ea362b8e31e1387b3eadf8cc344ebafa4");
    client.setPtwebqq("5c5256a316af21c86ead367a485d3a09286ad87e92cb719a25a2bb9b1e882cfe");
    client.setPsessionId("8368046764001d636f6e6e7365727665725f77656271714031302e3133392e372e31363000000f1000000048016e0400a39916486d0000000a407471787a6c395645346d000000289c295728e3cf7aff4abf76ed714bd70ae5a645e52232ccc149d56b9192eab94ae56b070ee69a9b1d");
    client.setUin("1209440675");

    client.initWithoutRedis();
//    PollResponse response1 = client.poll();
//    System.out.println(FastJsonUtil.toJson(response1));
//    System.out.println(response1.getRetCode());
    GetGroupListResponse response2 = client.getGroupList();
    System.out.println(FastJsonUtil.toJson(response2));

    SendGroupMsgResponse response3 = client.sendGroupMsg("19在吗", "test群123");
    System.out.println(FastJsonUtil.toJson(response3));

    GetDiscusListResponse response4 = client.getDiscusList();
    System.out.println(FastJsonUtil.toJson(response4));

    SendDiscusMsgResponse response5 = client.sendDiscusMsg("退票超时了", "test讨论组-机器人");
    System.out.println(FastJsonUtil.toJson(response5));
  }

  //@Test
  public void testPoll() throws Exception {
    QQClient client = new QQClient();
    client.setCookie("pgv_info=ssid=s9757738917; pgv_pvid=6984056050; o_cookie=23441099; ptisp=cm; RK=MmsOidwQMD; ptcz=e8cab057677d57450cfc98ad397b3fae86f599a51f929d1302dcb1ce77ec4d98; pt2gguin=o1209440675; uin=o1209440675; skey=@FVYhBQGGT; p_uin=o1209440675; p_skey=cRpCHwYFpPg23aq4dC4pYM-dMNKM*kXDJlxvFHZpjY0_; pt4_token=SsrdHrw6F8dokI4f54C*tw__; ptwebqq=adbe06788959f79577dcac6f91c797eb45c78fb0399540a2e8d8e63730230414");
    client.setVfwebqq("46ba40f4cb48bf114794030c70eacdfccadf7a964d1dfddd8407eccc06cf6051a703d42af734a2b9");
    client.setPtwebqq("adbe06788959f79577dcac6f91c797eb45c78fb0399540a2e8d8e63730230414");
    client.setPsessionId("8368046764001d636f6e6e7365727665725f77656271714031302e3133392e372e31363000002980000005b0036e0400a39916486d0000000a404656596842514747546d00000028a7cad492fba4ec211ba059d3921ff74dc8d6f501d5e8326e587d3a4c2117e52d1592d35dcaa47e5b");
    client.setUin("120944067");

    client.init();
    PollResponse response = client.poll();
    System.out.println(FastJsonUtil.toJson(response));
  }

  @Test
  public void test() {

  }
}
