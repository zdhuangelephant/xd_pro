package com.xiaodou.im.agent.qq.request;

import com.xiaodou.im.agent.qq.anno.TransField;
import com.xiaodou.im.agent.qq.constant.Http;
import com.xiaodou.im.agent.qq.util.UrlRedisUtil;

/**
 * Date: 2014/12/11
 * Time: 11:01
 *
 * @author Tian.Dong
 */
public class GetDiscusListRequest extends BaseRequest {
  @TransField("clientid")
  private String clientId;
  @TransField("psessionid")
  private String psessionId;
  @TransField("vfwebqq")
  private String vfwebqq;
  @TransField("t")
  private String t;

  @Override
  public String getUrl() {
    String url = UrlRedisUtil.getDISCUS_LIST_URL();
    if (url != null) {
      return url;
    }
    return "http://s.web2.qq.com/api/get_discus_list";
  }

  @Override
  public Http getHttpMethod() {
    return Http.GET;
  }

  public String getClientId() {
    return clientId;
  }

  public void setClientId(String clientId) {
    this.clientId = clientId;
  }


  public String getVfwebqq() {
    return vfwebqq;
  }

  public void setVfwebqq(String vfwebqq) {
    this.vfwebqq = vfwebqq;
  }

  public String getT() {
    return t;
  }

  public void setT(String t) {
    this.t = t;
  }

  public String getPsessionId() {
    return psessionId;
  }

  public void setPsessionId(String psessionId) {
    this.psessionId = psessionId;
  }
}
