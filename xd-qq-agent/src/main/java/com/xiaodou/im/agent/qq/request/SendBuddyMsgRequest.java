package com.xiaodou.im.agent.qq.request;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import com.alibaba.fastjson.annotation.JSONField;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.im.agent.qq.anno.TransField;
import com.xiaodou.im.agent.qq.constant.Http;
import com.xiaodou.im.agent.qq.util.UrlRedisUtil;

/**
 * Date: 2014/12/11
 * Time: 11:01
 *
 * @author Tian.Dong
 */
public class SendBuddyMsgRequest extends BaseRequest {
  @TransField("r")
  private String r;

  public String getR() {
    return r;
  }

  public void setR(String r) {
    this.r = r;
  }

  public void setR(SendBuddyMsgRContent content) {
    String r = FastJsonUtil.toJson(content);
    try {
      r = URLEncoder.encode(r, "UTF-8");
    } catch (UnsupportedEncodingException e) {

    }
    this.r = r;
  }

  public static class SendBuddyMsgRContent {
    private String to;
    private String content;
    private String face;
    @JSONField(name = "clientid")
    private String clientId;
    @JSONField(name = "msg_id")
    private int msgId;
    @JSONField(name = "psessionid")
    private String psessionId;

    public String getTo() {
      return to;
    }

    public void setTo(String to) {
      this.to = to;
    }

    public String getContent() {
      return content;
    }

    public void setContent(String content) {
      this.content = content;
    }

    public void setRealContent(String txt) {
      String content = "[\"%s\",[\"face\",14],\" \",[\"font\",{\"name\":\"宋体\",\"size\":10,\"style\":[0,0,0],\"color\":\"000000\"}]]";
      this.content = String.format(content, txt);
    }

    public String getFace() {
      return face;
    }

    public void setFace(String face) {
      this.face = face;
    }

    public String getClientId() {
      return clientId;
    }

    public void setClientId(String clientId) {
      this.clientId = clientId;
    }

    public int getMsgId() {
      return msgId;
    }

    public void setMsgId(int msgId) {
      this.msgId = msgId;
    }

    public String getPsessionId() {
      return psessionId;
    }

    public void setPsessionId(String psessionId) {
      this.psessionId = psessionId;
    }
  }

  @Override
  public String getUrl() {
    String url = UrlRedisUtil.getSEND_BUDDY_MSG_URL();
    if (url != null) {
      return url;
    }
    return "http://d.web2.qq.com/channel/send_buddy_msg2";
  }

  @Override
  public Http getHttpMethod() {
    return Http.POST;
  }
}
