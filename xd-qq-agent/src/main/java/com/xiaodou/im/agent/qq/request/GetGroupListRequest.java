package com.xiaodou.im.agent.qq.request;


import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.im.agent.qq.anno.TransField;
import com.xiaodou.im.agent.qq.constant.Http;
import com.xiaodou.im.agent.qq.util.UrlRedisUtil;


/**
 * Date: 2014/12/11
 * Time: 11:00
 *
 * @author Tian.Dong
 */
public class GetGroupListRequest extends BaseRequest {
  @TransField("r")
  private String r;

  public String getR() {
    return r;
  }

  public void setR(String r) {
    this.r = r;
  }

  public void setR(RContent content) {
    String r = FastJsonUtil.toJson(content);
    try {
      r = URLEncoder.encode(r, "UTF-8");
    } catch (UnsupportedEncodingException e) {

    }
    this.r = r;
  }

  public static class RContent {
    private String vfwebqq;
    private String hash;

    public String getVfwebqq() {
      return vfwebqq;
    }

    public void setVfwebqq(String vfwebqq) {
      this.vfwebqq = vfwebqq;
    }

    public String getHash() {
      return hash;
    }

    public void setHash(String hash) {
      this.hash = hash;
    }
  }

  @Override
  public String getUrl() {
    String url = UrlRedisUtil.getGROUP_LIST_URL();
    if (url != null) {
      return url;
    }
    return "http://s.web2.qq.com/api/get_group_name_list_mask2";
    //return "http://localhost/api/get_group_name_list_mask2";
  }

  @Override
  public Http getHttpMethod() {
    return Http.POST;
  }
}
