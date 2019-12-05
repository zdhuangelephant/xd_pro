package com.xiaodou.im.agent.qq.response;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;

/**
 * {"retcode":102,"errmsg":""}
 * Date: 2014/12/11
 * Time: 11:04
 *
 * @author Tian.Dong
 */
public class PollResponse {
  @JSONField(name = "retcode")
  private int retCode;

  @JSONField(name = "errmsg")
  private String errMsg;

  public int getRetCode() {
    return retCode;
  }

  public void setRetCode(int retCode) {
    this.retCode = retCode;
  }

  public String getErrMsg() {
    return errMsg;
  }

  public void setErrMsg(String errMsg) {
    this.errMsg = errMsg;
  }

  private List<PollResult> result;

  public static class PollResult {
    @JSONField(name = "poll_type")
    private String pollType;
    private String value;
    @JSONField(name = "msg_id")
    private String msgId;
    @JSONField(name = "from_uin")
    private String fromUin;
    @JSONField(name = "to_uin")
    private String toUin;
    @JSONField(name = "msg_id2")
    private String msgId2;
    @JSONField(name = "msg_type")
    private String msgType;
    @JSONField(name = "reply_ip")
    private String replyIp;
    private String time;
    private List<Object> content;

    public String getPollType() {
      return pollType;
    }

    public void setPollType(String pollType) {
      this.pollType = pollType;
    }

    public String getValue() {
      return value;
    }

    public void setValue(String value) {
      this.value = value;
    }

    public String getMsgId() {
      return msgId;
    }

    public void setMsgId(String msgId) {
      this.msgId = msgId;
    }

    public String getFromUin() {
      return fromUin;
    }

    public void setFromUin(String fromUin) {
      this.fromUin = fromUin;
    }

    public String getToUin() {
      return toUin;
    }

    public void setToUin(String toUin) {
      this.toUin = toUin;
    }

    public String getMsgId2() {
      return msgId2;
    }

    public void setMsgId2(String msgId2) {
      this.msgId2 = msgId2;
    }

    public String getMsgType() {
      return msgType;
    }

    public void setMsgType(String msgType) {
      this.msgType = msgType;
    }

    public String getReplyIp() {
      return replyIp;
    }

    public void setReplyIp(String replyIp) {
      this.replyIp = replyIp;
    }

    public String getTime() {
      return time;
    }

    public void setTime(String time) {
      this.time = time;
    }

    public List<Object> getContent() {
      return content;
    }

    public void setContent(List<Object> content) {
      this.content = content;
    }
  }

  public List<PollResult> getResult() {
    return result;
  }

  public void setResult(List<PollResult> result) {
    this.result = result;
  }
}
