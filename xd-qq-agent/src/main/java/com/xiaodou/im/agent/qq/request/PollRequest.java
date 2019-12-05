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
public class PollRequest extends BaseRequest {
  @TransField("r")
  private String r;

  public String getR() {
    return r;
  }

  public void setR(String r) {
    this.r = r;
  }

  public void setR(PollRContent content) {
    String r = FastJsonUtil.toJson(content);
    try {
      r = URLEncoder.encode(r, "UTF-8");
    } catch (UnsupportedEncodingException e) {

    }
    this.r = r;
  }

  @Override
  public String getUrl() {
    String url = UrlRedisUtil.getPOLL_URL();
    if (url != null) {
      return url;
    }
    return "http://d.web2.qq.com/channel/poll2";
  }

  @Override
  public Http getHttpMethod() {
    return Http.POST;
  }

  /**
   * {
   * "ptwebqq": "62e8ef669d5816c79c054d11ec9171f067ea2abfd82df0ee",
   * "clientid": 53999199,
   * "psessionid": "8368046764001d636f6e6e7365727665725f77656271714031302e3133392e372e3136340000629200000376016e0400a39916486d0000000a40627537794f4a7868766d000000285487a486df669ac7aa0ca36feaba1bedaa886335590e741b08e8d69906025a71bb1730748ae586aa",
   * "key": ""
   * }
   */
  public static class PollRContent {
    private String ptwebqq;
    @JSONField(name = "clientid")
    private long clientId;
    @JSONField(name = "psessionid")
    private String psessionId;
    @JSONField(name = "key")
    private String key = "";

    public String getPtwebqq() {
      return ptwebqq;
    }

    public void setPtwebqq(String ptwebqq) {
      this.ptwebqq = ptwebqq;
    }

    public long getClientId() {
      return clientId;
    }

    public void setClientId(long clientId) {
      this.clientId = clientId;
    }

    public void setClientId(String clientId) {
      this.clientId = Long.parseLong(clientId);
    }

    public String getPsessionId() {
      return psessionId;
    }

    public void setPsessionId(String psessionId) {
      this.psessionId = psessionId;
    }

    public String getKey() {
      return key;
    }

    public void setKey(String key) {
      this.key = key;
    }
  }


}
